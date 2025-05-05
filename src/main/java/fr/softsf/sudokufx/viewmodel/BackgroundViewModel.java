package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.enums.ScreenSize;
import fr.softsf.sudokufx.enums.ToastLevels;
import fr.softsf.sudokufx.view.components.SpinnerGridPane;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

import java.io.File;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * ViewModel for managing and applying background (color and image) to a GridPane.
 * - Sets and applies background color.
 * - Handles image file selection, validation, and asynchronous loading.
 * - Resizes images to fit the available space while maintaining aspect ratio.
 * - Displays notifications and a loading spinner during image loading.
 * - Handles success and error cases during image loading.
 */
@Component
public class BackgroundViewModel {

    /**
     * Initializes the GridPane background with either a predefined color or image.
     * If a color is defined, applies it and updates the ColorPicker.
     * If an image path is defined, loads and applies the image asynchronously.
     *
     * @param sudokuFX The GridPane to update.
     * @param menuBackgroundButtonColor The ColorPicker to update with the color value.
     * @param toaster The toaster for user notifications.
     * @param spinner The spinner to show loading state.
     */
    public void init(GridPane sudokuFX, ColorPicker menuBackgroundButtonColor, ToasterVBox toaster, SpinnerGridPane spinner) {
        // TODO: SERVICE GET & SET COLOR OR IMAGE
        // IF COLOR
        setColorFromModel(sudokuFX, menuBackgroundButtonColor, "99b3ffcd");
        // IF IMAGE
        handleFileImageChooser(new File("C:\\Users"),toaster,spinner,sudokuFX);
    }

    /**
     * Sets the background color of the specified GridPane.
     *
     * @param sudokuFX The GridPane to update.
     * @param color    The color to apply as the background.
     */
    public void updateBackgroundColorAndApply(GridPane sudokuFX, Color color) {
        // TODO: SERVICE SET
        System.out.println("The color to store is :" + color.toString().substring(2));
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Handles image file selection, validating the file format and initiating the image loading process.
     *
     * @param selectedFile The selected image file.
     * @param toaster      The toaster component for displaying notifications.
     * @param spinner      The spinner component for showing loading state.
     * @param sudokuFX     The GridPane where the image background will be applied.
     */
    public void handleFileImageChooser(File selectedFile, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        if (selectedFile != null && isValidImage(selectedFile)) {
            loadImage(selectedFile, toaster, spinner, sudokuFX);
        } else {
            toaster.addToast("The selected file is not a valid image format.", "", ToastLevels.ERROR, true);
        }
    }

    /**
     * Applies a background color to the GridPane and sets it in the ColorPicker.
     *
     * @param sudokuFX The GridPane to update.
     * @param menuBackgroundButtonColor The ColorPicker to update.
     * @param colorValueFromModel Hex color string (e.g., "99b3ffcd").
     */
    private void setColorFromModel(GridPane sudokuFX, ColorPicker menuBackgroundButtonColor, String colorValueFromModel) {
        Color color = intToColor(Integer.parseUnsignedInt(colorValueFromModel, 16));
        menuBackgroundButtonColor.setValue(color);
        System.out.println("The color from the store is :" + color.toString().substring(2));
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Converts a 32-bit integer (0xRRGGBBAA) into a JavaFX Color object.
     *
     * @param colorValue The color value in hexadecimal format (0xRRGGBBAA).
     * @return A JavaFX Color object.
     */
    private Color intToColor(int colorValue) {
        return Color.rgb(
                (colorValue >> 24) & 0xFF,
                (colorValue >> 16) & 0xFF,
                (colorValue >> 8) & 0xFF,
                (colorValue & 0xFF) / 255.0
        );
    }

    /**
     * Validates if the given file is a valid image format.
     *
     * @param file The file to check.
     * @return True if the file is a valid image format (jpg, jpeg, png, bmp), otherwise false.
     */
    private boolean isValidImage(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".bmp");
    }

    /**
     * Initiates the image loading and resizing process in a background task.
     *
     * @param selectedFile The selected image file.
     * @param toaster      The toaster component for displaying notifications.
     * @param spinner      The spinner component for showing loading state.
     * @param sudokuFX     The GridPane where the image background will be applied.
     */
    private void loadImage(File selectedFile, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        String fileUri = selectedFile.toURI().toString();
        spinner.showSpinner(true);
        toaster.addToast("Image loading in progress...", fileUri, ToastLevels.INFO, false);
        Task<BackgroundImage> backgroundTask = new Task<>() {
            @Override
            protected BackgroundImage call() {
                try {
                    Image tempImage = new Image(fileUri, false);
                    double scaleFactor = calculateImageScaleFactor(tempImage);
                    Image resizedImage = new Image(fileUri, tempImage.getWidth() * scaleFactor, tempImage.getHeight() * scaleFactor, true, true);
                    if (resizedImage.isError()) {
                        System.out.println("Error: " + resizedImage.getException().getMessage());
                        return null;
                    }
                    return createBackgroundImage(resizedImage);
                } catch (Exception e) {
                    System.out.println("Task error: " + e.getMessage());
                    return null;
                }
            }
        };
        backgroundTask.setOnSucceeded(e -> onImageTaskComplete(backgroundTask, toaster, spinner, sudokuFX));
        backgroundTask.setOnFailed(e -> onImageTaskError(e, toaster, spinner));
        new Thread(backgroundTask).start();
    }

    /**
     * Handles the success case of the image loading task.
     *
     * @param backgroundTask The background task.
     * @param toaster        The toaster component for displaying notifications.
     * @param spinner        The spinner component for showing loading state.
     * @param sudokuFX       The GridPane where the image background will be applied.
     */
    private void onImageTaskComplete(Task<BackgroundImage> backgroundTask, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        Platform.runLater(() -> {
            toaster.removeToast();
            BackgroundImage backgroundImage = backgroundTask.getValue();
            if (backgroundImage != null) {
                // TODO: SERVICE SET
                sudokuFX.setBackground(new Background(backgroundImage));
            } else {
                toaster.addToast("Error while loading the image.", "", ToastLevels.ERROR, true);
            }
            spinner.showSpinner(false);
        });
    }

    /**
     * Handles the failure case of the image loading task.
     *
     * @param e       The event that contains the failure information.
     * @param toaster The toaster component for displaying notifications.
     * @param spinner The spinner component for showing loading state.
     */
    private void onImageTaskError(WorkerStateEvent e, ToasterVBox toaster, SpinnerGridPane spinner) {
        Throwable exception = e.getSource().getException();
        Platform.runLater(() -> {
            toaster.removeToast();
            toaster.addToast("Unexpected error during image loading.", (exception == null ? "" : exception.getMessage()), ToastLevels.ERROR, true);
            spinner.showSpinner(false);
        });
    }

    /**
     * Calculates the scale factor to resize the image based on the available grid dimensions.
     *
     * @param tempImage The temporary image to be resized.
     * @return The scale factor.
     */
    private double calculateImageScaleFactor(Image tempImage) {
        double imageWidth = tempImage.getWidth();
        double imageHeight = tempImage.getHeight();
        double gridPaneWidth = ScreenSize.VISUAL_WIDTH.getSize() * 3;
        double gridPaneHeight = ScreenSize.VISUAL_HEIGHT.getSize() * 3;
        return Math.max(gridPaneWidth / imageWidth, gridPaneHeight / imageHeight);
    }

    /**
     * Creates a BackgroundImage object using the resized image.
     *
     * @param resizedImage The resized image.
     * @return The created BackgroundImage.
     */
    private BackgroundImage createBackgroundImage(Image resizedImage) {
        return new BackgroundImage(
                resizedImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        resizedImage.getWidth() / 3,
                        resizedImage.getHeight() / 3,
                        false, false, false, false
                )
        );
    }
}
