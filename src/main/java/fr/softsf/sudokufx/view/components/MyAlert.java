/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.components;

import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.Paths;

/**
 * A styled JavaFX alert dialog with optional sizing and custom appearance. Applies a radial
 * gradient background, white text, and a custom window icon.
 */
public class MyAlert extends Alert {

    /**
     * Creates a styled alert with automatic sizing.
     *
     * @param alertType the alert type (e.g., INFORMATION, CONFIRMATION)
     */
    public MyAlert(AlertType alertType) {
        super(alertType);
        initDialog(null);
    }

    /**
     * Creates a styled alert with a fixed size.
     *
     * @param alertType the alert type (e.g., INFORMATION, CONFIRMATION)
     * @param size the fixed width and height for the dialog pane
     */
    public MyAlert(AlertType alertType, double size) {
        super(alertType);
        initDialog(size);
    }

    /**
     * Initializes the alert with optional size and visual styling.
     *
     * @param size the optional size; if null, the dialog uses default sizing
     */
    private void initDialog(Double size) {
        DialogPane dialogPane = getDialogPane();
        if (size != null) {
            dialogPane.setMinWidth(size);
            dialogPane.setMinHeight(size);
        }
        dialogPane.setStyle(
                "-fx-background-color: radial-gradient(center 50% 150%, radius 100%,"
                        + " #A83449, #12020B);");
        Label contentLabel = (Label) dialogPane.lookup(".content");
        if (contentLabel != null) {
            contentLabel.setTextFill(Color.WHITE);
            contentLabel.setWrapText(true);
            contentLabel.setMaxWidth(Double.MAX_VALUE);
        }
        Stage alertStage = (Stage) dialogPane.getScene().getWindow();
        alertStage
                .getIcons()
                .add(
                        new Image(
                                Objects.requireNonNull(
                                                SudoMain.class.getResource(
                                                        Paths.LOGO_SUDO_PNG_PATH.getPath()))
                                        .toExternalForm()));
    }
}
