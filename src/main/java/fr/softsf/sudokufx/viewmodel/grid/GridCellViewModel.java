/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.util.Locale;
import java.util.Set;
import java.util.function.UnaryOperator;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;

import io.micrometer.common.util.StringUtils;

/**
 * ViewModel representing a single editable cell in a 9x9 Sudoku grid.
 *
 * <p>Maintains observable properties (ID, raw input text) and associated JavaFX UI controls (Label
 * for display, TextArea for input). Handles input formatting, visual styling, and interaction logic
 * to switch between read and edit modes.
 *
 * <p>Ensures input filtering (digits 1–9), formatting into multiline strings, and bidirectional
 * binding between UI and data properties.
 */
public class GridCellViewModel {

    private static final String EM_FORMAT = "%.3fem";
    private static final double SCALE_SINGLE_CHAR = 0.35;
    private static final double BORDER_THICK_BASE = 0.2;
    private static final double BORDER_THIN_UNFOCUSED = 0.05;
    private static final String SUDOKU_FX_GRID_CELL_LARGE_FONT = "sudokuFXGridCellLargeFont";
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty rawText = new SimpleStringProperty("");
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
        label.getStyleClass().add("sudokuFXGridCell");
        textArea.getStyleClass().add("sudokuFXGridCell");
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
     * Configures all UI event listeners and text formatting logic for the grid cell:
     *
     * <ul>
     *   <li>Applies dynamic border styling on label focus.
     *   <li>Allows switching to edit mode via mouse click or specific key presses (ENTER, SPACE,
     *       1–9).
     *   <li>Adjusts font size based on label content length.
     *   <li>Filters input to digits 1–9, removes duplicates, sorts, and limits to 9 digits.
     *   <li>Applies 3×3 multiline formatting inside the TextArea (via TextFormatter).
     *   <li>Returns to label mode on focus loss or key validation (ENTER, ESC, TAB, SPACE).
     * </ul>
     */
    private void setupListeners() {
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
                                // Le texte est déjà dans textProperty via le binding bidirectionnel
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

    /** Switches from label (read-only) to editable TextArea mode and focuses it. */
    private void switchToEditMode() {
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

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty rawTextProperty() {
        return rawText;
    }
}
