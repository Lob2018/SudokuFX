/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

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

    private final String borderStyle;

    public GridCellViewModel(int id, int row, int col) {
        this.id.set(id);
        this.row = row;
        this.col = col;
        label.setId(String.valueOf(id));
        textArea.setId(String.valueOf(id));
        // Init styles
        label.getStyleClass().add("sudokuFXGridCell");
        textArea.getStyleClass().add("sudokuFXGridCell");
        textArea.setPrefRowCount(3);
        textArea.setWrapText(true);
        String thick = "0.2em", thin = "0.05em";
        String top = (row == 0) ? thick : thin;
        String left = (col == 0) ? thick : thin;
        String bottom = ((row + 1) % 3 == 0) ? thick : thin;
        String right = ((col + 1) % 3 == 0) ? thick : thin;
        this.borderStyle =
                String.format(
                        "-fx-border-color: black black black black; -fx-border-width: %s %s %s %s;",
                        top, right, bottom, left);
        label.setStyle(borderStyle);
        textArea.setStyle(borderStyle);
        // Formatted display text derived from raw input (e.g., "123456789" -> multiline with
        // spacing)
        StringBinding formattedText =
                Bindings.createStringBinding(() -> formatText(rawText.get()), rawText);
        // Bind label text au texte formaté automatiquement
        label.textProperty().bind(formattedText);
        // Bind bidirectionnel du TextArea vers la propriété text (texte brut)
        textArea.textProperty().bindBidirectional(rawText);
        label.setFocusTraversable(true);
        textArea.setVisible(false);
        textArea.setFocusTraversable(true);
        setupListeners();
    }

    /**
     * Sets up UI interaction and formatting listeners: - Visual focus styling for the label. -
     * Keyboard and mouse interaction to switch to edit mode. - Input filtering (only digits 1–9,
     * unique, sorted, limited to 9). - Auto-formatting multiline display in the TextArea. -
     * Automatic return to label mode on focus loss or key validation (ENTER, ESC, etc.).
     */
    private void setupListeners() {
        // Focus styling
        label.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (newV) {
                                label.setStyle(
                                        "-fx-border-color: radial-gradient(center 50% 150%, radius"
                                            + " 100%, derive(#0C8CE9, -90%), derive(#0C8CE9, 55%));"
                                            + " -fx-border-width: 0.2em;");
                            } else {
                                label.setStyle(borderStyle);
                            }
                        });
        // Label click or key press to edit
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
        // Text input filter
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
        // Quand on perd le focus sur textArea, revenir en mode label visible
        textArea.focusedProperty()
                .addListener(
                        (obs, oldV, newV) -> {
                            if (!newV) {
                                // Le texte est déjà dans textProperty via le binding bidirectionnel
                                textArea.setVisible(false);
                                label.setVisible(true);
                            }
                        });
        // Gérer validation clavier dans textArea
        textArea.setOnKeyPressed(
                e -> {
                    switch (e.getCode()) {
                        case ENTER, ESCAPE, TAB, SPACE -> {
                            e.consume();
                            textArea.setVisible(false);
                            label.setVisible(true);
                            label.requestFocus();
                        }
                        default -> {}
                    }
                });

        // Vous pouvez réactiver le listener pour la taille de police si besoin
        //        label.textProperty()
        //                .addListener(
        //                        (obs, oldText, newText) -> {
        //                            if (newText != null && newText.replace("\n", "").length() ==
        // 1) {
        //                                if
        // (!label.getStyleClass().contains("sudokuFXGridCellLargeFont")) {
        //
        // label.getStyleClass().add("sudokuFXGridCellLargeFont");
        //                                }
        //                            } else {
        //                                label.getStyleClass().remove("sudokuFXGridCellLargeFont");
        //                            }
        //                        });
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
