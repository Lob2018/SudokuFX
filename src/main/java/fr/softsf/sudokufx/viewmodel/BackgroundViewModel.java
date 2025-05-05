package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.enums.ScreenSize;
import fr.softsf.sudokufx.enums.ToastLevels;
import fr.softsf.sudokufx.view.components.SpinnerGridPane;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.springframework.stereotype.Component;

import java.io.File;

import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * ViewModel responsible for setting and applying a background image to the GridPane.
 * It handles image file validation, loading, resizing, and ensures the image fits the available space.
 * Notifications are displayed during the loading process using a toaster and spinner.
 * The image is loaded asynchronously to avoid blocking the UI thread.
 */
@Component
public class BackgroundViewModel {

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
