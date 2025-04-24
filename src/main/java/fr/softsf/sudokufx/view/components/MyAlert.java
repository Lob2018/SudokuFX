package fr.softsf.sudokufx.view.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

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
    }
}
