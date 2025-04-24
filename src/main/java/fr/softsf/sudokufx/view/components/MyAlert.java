package fr.softsf.sudokufx.view.components;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.Paths;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * A custom alert class that extends the standard JavaFX Alert.
 * Provides additional styling to the dialog, such as:
 * - A radial gradient background.
 * - White text color for the content.
 */
public class MyAlert extends Alert {

    /**
     * Constructs a custom alert dialog with specific styling.
     *
     * @param alertType The type of alert (e.g., INFORMATION, CONFIRMATION, etc.)
     */
    public MyAlert(AlertType alertType) {
        super(alertType);
        getDialogPane().setStyle(
                "-fx-background-color: radial-gradient(center 50% 150%, radius 100%, #A83449, #12020B);"
        );
        Label contentLabel = (Label) getDialogPane().lookup(".content");
        if (contentLabel != null) {
            contentLabel.setTextFill(Color.WHITE);
        }
        Stage alertStage = (Stage) getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(
                Objects.requireNonNull(SudoMain.class.getResource(Paths.LOGO_SUDO_PNG_PATH.getPath())).toExternalForm()
        ));
    }
}
