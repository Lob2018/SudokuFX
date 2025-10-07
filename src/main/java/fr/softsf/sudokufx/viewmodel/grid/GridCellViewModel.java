/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;

import org.apache.commons.lang3.StringUtils;

import fr.softsf.sudokufx.common.enums.I18n;

/**
 * ViewModel for an editable cell in a 9x9 Sudoku grid.
 *
 * <p>Manages cell state and UI representation with:
 *
 * <ul>
 *   <li>Observable properties: {@code id}, {@code rawText}, {@code editable}.
 *   <li>JavaFX controls: {@link Label} for display and {@link TextArea} for editing.
 *   <li>Dynamic styling based on content length, focus, and editable state.
 *   <li>Text formatting: filters digits 1–9, removes duplicates, sorts, and formats into a 3×3
 *       multiline grid.
 *   <li>Accessibility: {@link Label#accessibleTextProperty()} bound to i18n string reflecting
 *       formatted value and 1-based row/column.
 *   <li>Edit mode switching via mouse click or key input, with bidirectional binding to {@code
 *       rawText}.
 * </ul>
 */
public class GridCellViewModel {

    private static final double SCALE_SINGLE_CHAR = .38;
    private static final double BORDER_THICK_BASE = .2;
    private static final double BORDER_THIN_UNFOCUSED = .05;
    private static final double BLOCK_SPACING = .3;
    private static final String SUDOKU_FX_GRID_CELL_LARGE_FONT = "sudokuFXGridCellLargeFont";
    private static final String SUDOKU_FX_GRID_CELL_NON_EDITABLE = "sudokuFXGridCellNonEditable";
    private static final String SUDOKU_FX_GRID_CELL = "sudokuFXGridCell";
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty rawText = new SimpleStringProperty("");
    private final BooleanProperty editable = new SimpleBooleanProperty(true);
    private final Label label = new Label();
    private final TextArea textArea = new TextArea();
    private final int row;
    private final int col;

    public GridCellViewModel(int id, int row, int col) {
        this.id.set(id);
        this.row = row;
        this.col = col;
        label.setId(String.valueOf(id));
        textArea.setId(String.valueOf(id));
        String borderStyle = getBorderStyle(label.getText().length(), false);
        label.setStyle(borderStyle);
        textArea.setStyle(borderStyle);
        textArea.setPrefRowCount(3);
        textArea.setWrapText(true);
        label.setFocusTraversable(true);
        textArea.setVisible(false);
        textArea.setFocusTraversable(true);
        StringBinding formattedText =
                Bindings.createStringBinding(() -> formatText(rawText.get()), rawText);
        label.textProperty().bind(formattedText);
        textArea.textProperty().bindBidirectional(rawText);
        setLabelAccessibleText();
        setupListeners();
    }

    /**
     * Binds the label's accessible text to a localized, formatted string reflecting the cell's
     * value and 1-based row/column indices. Updates automatically on locale or cell content
     * changes.
     *
     * <p>Uses i18n key {@code grid.cell.accessibility}: "{0}, ligne {1}, colonne {2}", where: {0} →
     * formatted cell text, {1} → row + 1, {2} → col + 1.
     */
    private void setLabelAccessibleText() {
        label.accessibleTextProperty()
                .bind(
                        Bindings.createStringBinding(
                                () ->
                                        MessageFormat.format(
                                                I18n.INSTANCE.getValue("grid.cell.accessibility"),
                                                formatText(rawText.get()),
                                                row + 1,
                                                col + 1),
                                I18n.INSTANCE.localeProperty(),
                                rawText));
    }

    /**
     * Configures UI listeners and formatting logic:
     *
     * <ul>
     *   <li>Updates styles based on editable state.
     *   <li>Applies dynamic border styling on label focus.
     *   <li>Enables switch to edit mode on mouse click or specific key presses.
     *   <li>Adjusts font size based on content length.
     *   <li>Filters input to digits 1–9, removing duplicates and sorting.
     *   <li>Formats input into a multiline 3×3 grid inside the TextArea.
     *   <li>Switches back to label mode on focus loss or certain key presses.
     * </ul>
     */
    private void setupListeners() {
        setEditableStyle(true);
        editable.addListener((obs, wasEditable, isNowEditable) -> setEditableStyle(isNowEditable));
        label.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> applyBorderStyle(label, Boolean.TRUE.equals(newV)));
        label.setOnMouseClicked(e -> switchToEditMode());
        label.setOnKeyPressed(
                e -> {
                    if (e.getCode() == KeyCode.ENTER
                            || e.getCode() == KeyCode.SPACE
                            || e.getText().matches("[1-9]")) {
                        switchToEditMode();
                        e.consume();
                    }
                });
        label.textProperty()
                .addListener(
                        (obs, oldText, newText) ->
                                updateLabelFontSizeAndBorderOnTextChange(newText));
        UnaryOperator<TextFormatter.Change> filter =
                change -> {
                    String filtered = normalizeInput(change.getControlNewText());
                    String multiline = formatMultiline(filtered);
                    change.setText(multiline);
                    change.setRange(0, change.getControlText().length());
                    return change;
                };
        textArea.setTextFormatter(new TextFormatter<>(filter));
        textArea.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (Boolean.FALSE.equals(newV)) {
                                textArea.setVisible(false);
                                label.setVisible(true);
                            }
                        });
        Set<KeyCode> keys = Set.of(KeyCode.ENTER, KeyCode.ESCAPE, KeyCode.TAB, KeyCode.SPACE);
        textArea.setOnKeyPressed(
                e -> {
                    if (keys.contains(e.getCode())) {
                        e.consume();
                        textArea.setVisible(false);
                        label.setVisible(true);
                        label.requestFocus();
                    }
                });
    }

    /**
     * Updates the label's font size CSS class and border style based on the current text content.
     *
     * <p>If the new text (with line breaks removed) is exactly one character long, it adds a CSS
     * class for a larger font and updates the border style accordingly. Otherwise, it removes the
     * large font CSS class and resets the border style.
     *
     * @param newText the updated text content of the label, may be {@code null}
     */
    private void updateLabelFontSizeAndBorderOnTextChange(String newText) {
        if (newText != null && newText.replace("\n", "").length() == 1) {
            if (label.getStyleClass().contains(SUDOKU_FX_GRID_CELL_LARGE_FONT)) {
                return;
            }
            label.getStyleClass().add(SUDOKU_FX_GRID_CELL_LARGE_FONT);
            applyBorderStyle(label, false);
        } else {
            label.getStyleClass().remove(SUDOKU_FX_GRID_CELL_LARGE_FONT);
            applyBorderStyle(label, false);
        }
    }

    /**
     * Applies a computed border style to a JavaFX node, based on content length, focus state, and
     * grid position. Skips updating if the style is unchanged.
     *
     * @param node the Region to style (e.g., Label)
     * @param focusedBorder true if the node is focused
     */
    private void applyBorderStyle(Region node, boolean focusedBorder) {
        String newStyle = getBorderStyle(label.getText().length(), focusedBorder);
        if (newStyle.equals(node.getStyle())) {
            return;
        }
        node.setStyle(newStyle);
    }

    /**
     * Updates CSS style classes on label and text area to reflect the editable state.
     *
     * @param isNowEditable {@code true} to apply editable styles; {@code false} for non-editable
     *     styles.
     */
    private void setEditableStyle(Boolean isNowEditable) {
        boolean isEditable = Boolean.TRUE.equals(isNowEditable);
        String toRemove = isEditable ? SUDOKU_FX_GRID_CELL_NON_EDITABLE : SUDOKU_FX_GRID_CELL;
        String toAdd = isEditable ? SUDOKU_FX_GRID_CELL : SUDOKU_FX_GRID_CELL_NON_EDITABLE;
        for (Region node : List.of(label, textArea)) {
            node.getStyleClass().remove(toRemove);
            if (node.getStyleClass().contains(toAdd)) {
                continue;
            }
            node.getStyleClass().add(toAdd);
        }
    }

    /**
     * Builds the CSS style for a Sudoku grid cell.
     *
     * <p>The style adapts to:
     *
     * <ul>
     *   <li>Cell content size (scales borders and spacing if single char)
     *   <li>Focus state (highlight border color/thickness)
     *   <li>Grid position (cumulative translation to form 3×3 blocks)
     * </ul>
     *
     * @param nbOfChar number of characters in the cell (affects scaling)
     * @param focusedBorder true if the cell is focused (stronger border)
     * @return a CSS style string with border, insets, and translation
     */
    private String getBorderStyle(int nbOfChar, boolean focusedBorder) {
        double scale = (nbOfChar == 1) ? SCALE_SINGLE_CHAR : 1;
        double borderWidth = (focusedBorder ? BORDER_THICK_BASE : BORDER_THIN_UNFOCUSED) * scale;
        String borderColor =
                focusedBorder
                        ? "radial-gradient(center 50% 150%, radius 100%, derive(#0C8CE9, -90%),"
                                + " derive(#0C8CE9, 55%))"
                        : "transparent";
        int blocksLeft = col / 3;
        int blocksAbove = row / 3;
        double translateX = blocksLeft * BLOCK_SPACING * scale;
        double translateY = blocksAbove * BLOCK_SPACING * scale;
        return String.format(
                Locale.US,
                "-fx-border-color: %s; -fx-border-width: %.3fem; -fx-background-insets: %.3fem;"
                        + " -fx-translate-x: %.3fem; -fx-translate-y: %.3fem;",
                borderColor,
                borderWidth,
                borderWidth,
                translateX,
                translateY);
    }

    /**
     * Switches from read-only mode (Label visible) to edit mode (TextArea visible), focusing the
     * TextArea and positioning the caret at the end.
     */
    private void switchToEditMode() {
        if (isEditable()) {
            textArea.setText(rawText.get().replace("\n", ""));
            label.setVisible(false);
            textArea.setVisible(true);
            textArea.requestFocus();
            textArea.positionCaret(textArea.getText().length());
        }
    }

    /**
     * Formats a raw digit string (1–9) into a 3-line spaced grid display. Removes duplicates, sorts
     * digits, and inserts spaces for visual alignment.
     *
     * @param text Raw unformatted input text (may include invalid characters)
     * @return Formatted multiline string (e.g., for label display in grid)
     */
    public String formatText(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        String filtered = normalizeInput(text);
        String[] lines = formatMultiline(filtered).split("\n", -1);
        StringBuilder finalText = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.length() <= 1) {
                finalText.append(line);
            } else {
                finalText.append(line.charAt(0));
                for (int j = 1; j < line.length(); j++) {
                    finalText.append(' ').append(line.charAt(j));
                }
            }
            if (i < lines.length - 1) {
                finalText.append('\n');
            }
        }
        return finalText.toString();
    }

    /**
     * Filters the input string to digits 1–9, removes duplicates, sorts ascending, and limits to 9
     * characters maximum. Returns an empty string if input is null or empty.
     *
     * @param input Any user input (possibly null or blank)
     * @return Cleaned and ordered digit string (max 9 characters), or empty string if input is
     *     null/blank
     */
    private static String normalizeInput(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        return input.chars()
                .filter(ch -> ch >= '1' && ch <= '9')
                .distinct()
                .sorted()
                .limit(9)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Inserts line breaks every 3 digits in the filtered string, with a maximum of two line breaks,
     * to create a 3×3 visual grid layout. Returns an empty string if input is null or empty.
     *
     * @param filtered Cleaned input containing only digits 1–9 (may be null or blank)
     * @return Multiline string with up to 3 rows of digits, or empty string if input is null/blank
     */
    private static String formatMultiline(String filtered) {
        if (StringUtils.isBlank(filtered)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int lineBreaks = 0;
        for (int i = 0; i < filtered.length(); i++) {
            sb.append(filtered.charAt(i));
            if ((i + 1) % 3 == 0 && i != filtered.length() - 1 && lineBreaks < 2) {
                sb.append('\n');
                lineBreaks++;
            }
        }
        return sb.toString();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Label getLabel() {
        return label;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public int getId() {
        return id.get();
    }

    public boolean isEditable() {
        return editable.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty rawTextProperty() {
        return rawText;
    }

    public BooleanProperty editableProperty() {
        return editable;
    }
}
