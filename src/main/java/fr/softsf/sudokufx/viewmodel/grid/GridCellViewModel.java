/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

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

import io.micrometer.common.util.StringUtils;

/**
 * ViewModel representing an editable cell in a 9x9 Sudoku grid.
 *
 * <p>Maintains observable properties (id, raw text, editable) and JavaFX controls (Label for
 * display, TextArea for input). Manages text formatting, visual styling, and interaction logic for
 * toggling between read-only and edit modes.
 *
 * <p>Filters input to digits 1–9, removes duplicates, sorts, and formats text into a 3×3 multiline
 * grid display. Binds UI components bidirectionally to the data properties.
 */
public class GridCellViewModel {

    private static final String EM_FORMAT = "%.3fem";
    private static final double SCALE_SINGLE_CHAR = 0.35;
    private static final double BORDER_THICK_BASE = 0.2;
    private static final double BORDER_THIN_UNFOCUSED = 0.05;
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
        setupListeners();
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
        editable.addListener(
                (obs, wasEditable, isNowEditable) ->  setEditableStyle(isNowEditable));
        label.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (Boolean.TRUE.equals(newV)) {
                                label.setStyle(getBorderStyle(label.getText().length(), true));
                            } else {
                                label.setStyle(getBorderStyle(label.getText().length(), false));
                            }
                        });
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
                        (obs, oldText, newText) -> {
                            if (newText != null && newText.replace("\n", "").length() == 1) {
                                if (!label.getStyleClass()
                                        .contains(SUDOKU_FX_GRID_CELL_LARGE_FONT)) {
                                    label.getStyleClass().add(SUDOKU_FX_GRID_CELL_LARGE_FONT);
                                    label.setStyle(getBorderStyle(label.getText().length(), false));
                                }
                            } else {
                                label.getStyleClass().remove(SUDOKU_FX_GRID_CELL_LARGE_FONT);
                                label.setStyle(getBorderStyle(label.getText().length(), false));
                            }
                        });
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
            if (!node.getStyleClass().contains(toAdd)) {
                node.getStyleClass().add(toAdd);
            }
        }
    }

    /**
     * Computes the dynamic CSS border style for a Sudoku grid cell based on its position, content
     * size, and focus state.
     *
     * @param nbOfChar Number of characters in the cell (used to scale the border).
     * @param focusedBorder Whether the cell is currently focused (affects color and thickness).
     * @return A CSS style string for -fx-border-color and -fx-border-width.
     */
    private String getBorderStyle(int nbOfChar, boolean focusedBorder) {
        double scale = nbOfChar == 1 ? SCALE_SINGLE_CHAR : 1;
        double baseThick = BORDER_THICK_BASE;
        double baseThin = focusedBorder ? BORDER_THICK_BASE : BORDER_THIN_UNFOCUSED;
        String color =
                focusedBorder
                        ? "-fx-border-color: radial-gradient(center 50% 150%, radius"
                                + " 100%, derive(#0C8CE9, -90%), derive(#0C8CE9, 55%));"
                        : "black";
        String top = String.format(Locale.US, EM_FORMAT, (row == 0 ? baseThick : baseThin) * scale);
        String right =
                String.format(
                        Locale.US, EM_FORMAT, ((col + 1) % 3 == 0 ? baseThick : baseThin) * scale);
        String bottom =
                String.format(
                        Locale.US, EM_FORMAT, ((row + 1) % 3 == 0 ? baseThick : baseThin) * scale);
        String left =
                String.format(Locale.US, EM_FORMAT, (col == 0 ? baseThick : baseThin) * scale);
        return String.format(
                "-fx-border-color: %s; -fx-border-width: %s %s %s %s;",
                color, top, right, bottom, left);
    }

    /**
     * Switches from read-only mode (Label visible) to edit mode (TextArea visible), focusing the
     * TextArea and positioning the caret at the end.
     */
    private void switchToEditMode() {
        if (!isEditable()) {
            return;
        }
        textArea.setText(rawText.get().replace("\n", ""));
        label.setVisible(false);
        textArea.setVisible(true);
        textArea.requestFocus();
        textArea.positionCaret(textArea.getText().length());
    }

    /**
     * Formats a raw digit string (1–9) into a 3-line spaced grid display. Removes duplicates, sorts
     * digits, and inserts spaces for visual alignment.
     *
     * @param text Raw unformatted input text (may include invalid characters)
     * @return Formatted multiline string (e.g., for label display in grid)
     */
    public String formatText(String text) {
        if (StringUtils.isEmpty(text)) {
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
     * @param input Any user input (possibly null or empty)
     * @return Cleaned and ordered digit string (max 9 characters), or empty string if input is
     *     null/empty
     */
    private static String normalizeInput(String input) {
        if (StringUtils.isEmpty(input)) {
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
     * @param filtered Cleaned input containing only digits 1–9 (may be null or empty)
     * @return Multiline string with up to 3 rows of digits, or empty string if input is null/empty
     */
    private static String formatMultiline(String filtered) {
        if (StringUtils.isEmpty(filtered)) {
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
