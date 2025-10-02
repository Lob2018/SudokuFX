/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.component;

import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.AppPaths;

import static fr.softsf.sudokufx.common.enums.AppPaths.RESOURCES_CSS_ALERT_PATH;

/**
 * A styled JavaFX alert dialog with optional sizing and custom appearance. Applies a radial
 * gradient background, white text, and a custom window icon.
 */
public class MyAlert extends Alert {

    private static final String ALERT_FONT_CSS =
            Objects.requireNonNull(SudoMain.class.getResource(RESOURCES_CSS_ALERT_PATH.getPath()))
                    .toExternalForm();

    /**
     * Creates a styled alert with automatic sizing.
     *
     * @param alertType the alert type (e.g., INFORMATION, CONFIRMATION)
     */
    public MyAlert(AlertType alertType) {
        this(alertType, null);
    }

    /**
     * Creates a styled alert with a fixed size.
     *
     * @param alertType the alert type (e.g., INFORMATION, CONFIRMATION)
     * @param size the fixed width and height for the dialog pane
     */
    public MyAlert(AlertType alertType, Double size) {
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
        dialogPane.getStylesheets().add(ALERT_FONT_CSS);
        dialogPane.getStyleClass().add("my-alert");
        Label contentLabel = (Label) dialogPane.lookup(".content");
        if (contentLabel != null) {
            contentLabel.setWrapText(true);
            contentLabel.setMaxWidth(Double.MAX_VALUE);
            contentLabel.getStyleClass().add("my-alert-content");
        }
        dialogPane
                .getButtonTypes()
                .forEach(
                        bt -> {
                            Button btn = (Button) dialogPane.lookupButton(bt);
                            if (btn != null) {
                                btn.getStyleClass().add("my-alert-button");
                            }
                        });
        Stage alertStage = (Stage) dialogPane.getScene().getWindow();
        alertStage
                .getIcons()
                .add(
                        new Image(
                                Objects.requireNonNull(
                                                SudoMain.class.getResource(
                                                        AppPaths.LOGO_SUDO_PNG_PATH.getPath()))
                                        .toExternalForm()));
    }
}
