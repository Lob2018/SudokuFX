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
     * Sets up UI interaction and formatting listeners: - Visual focus styling for the label. -
     * Keyboard and mouse interaction to switch to edit mode. - Input filtering (only digits 1–9,
     * unique, sorted, limited to 9). - Auto-formatting multiline display in the TextArea. -
     * Automatic return to label mode on focus loss or key validation (ENTER, ESC, etc.).
     */
    private void setupListeners() {
        label.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (newV) {
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
                                if (!label.getStyleClass().contains("sudokuFXGridCellLargeFont")) {
                                    label.getStyleClass().add("sudokuFXGridCellLargeFont");
                                    label.setStyle(getBorderStyle(label.getText().length(), false));
                                }
                            } else {
                                label.getStyleClass().remove("sudokuFXGridCellLargeFont");
                                label.setStyle(getBorderStyle(label.getText().length(), false));
                            }
                        });
        UnaryOperator<TextFormatter.Change> filter =
                change -> {
                    String filtered =
                            change.getControlNewText()
                                    .chars()
                                    .filter(ch -> ch >= '1' && ch <= '9')
                                    .distinct()
                                    .sorted()
                                    .limit(9)
                                    .collect(
                                            StringBuilder::new,
                                            StringBuilder::appendCodePoint,
                                            StringBuilder::append)
                                    .toString();
                    StringBuilder sb = new StringBuilder();
                    int lineBreaks = 0;
                    for (int i = 0; i < filtered.length(); i++) {
                        sb.append(filtered.charAt(i));
                        if ((i + 1) % 3 == 0 && i != filtered.length() - 1 && lineBreaks < 2) {
                            sb.append('\n');
                            lineBreaks++;
                        }
                    }
                    change.setText(sb.toString());
                    change.setRange(0, change.getControlText().length());
                    return change;
                };
        textArea.setTextFormatter(new TextFormatter<>(filter));
        textArea.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (!newV) {
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
        double scale = nbOfChar == 1 ? .35 : 1;
        double baseThick = 0.2;
        double baseThin = focusedBorder ? 0.2 : 0.05;
        String color =
                focusedBorder
                        ? "-fx-border-color: radial-gradient(center 50% 150%, radius"
                                + " 100%, derive(#0C8CE9, -90%), derive(#0C8CE9, 55%));"
                        : "black";
        String top = String.format(Locale.US, "%.3fem", (row == 0 ? baseThick : baseThin) * scale);
        String right =
                String.format(
                        Locale.US, "%.3fem", ((col + 1) % 3 == 0 ? baseThick : baseThin) * scale);
        String bottom =
                String.format(
                        Locale.US, "%.3fem", ((row + 1) % 3 == 0 ? baseThick : baseThin) * scale);
        String left = String.format(Locale.US, "%.3fem", (col == 0 ? baseThick : baseThin) * scale);
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
     * Formats a raw digit string (1–9) into a 3-line spaced grid display. Removes duplicates and
     * sorts digits before formatting.
     *
     * @param text Raw unformatted input text
     * @return Formatted multiline string (e.g., for label display)
     */
    public String formatText(String text) {
        if (StringUtils.isEmpty(text)) return "";
        String filtered =
                text.chars()
                        .filter(ch -> ch >= '1' && ch <= '9')
                        .distinct()
                        .sorted()
                        .limit(9)
                        .collect(
                                StringBuilder::new,
                                StringBuilder::appendCodePoint,
                                StringBuilder::append)
                        .toString();
        StringBuilder sb = new StringBuilder();
        int lineBreaks = 0;
        for (int i = 0; i < filtered.length(); i++) {
            sb.append(filtered.charAt(i));
            if ((i + 1) % 3 == 0 && i != filtered.length() - 1 && lineBreaks < 2) {
                sb.append('\n');
                lineBreaks++;
            }
        }
        String[] lines = sb.toString().split("\n", -1);
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
            if (i < lines.length - 1) finalText.append('\n');
        }
        return finalText.toString();
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
