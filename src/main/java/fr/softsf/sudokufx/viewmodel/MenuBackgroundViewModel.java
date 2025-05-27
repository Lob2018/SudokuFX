/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.ToastLevels;
import fr.softsf.sudokufx.utils.ImageUtils;
import fr.softsf.sudokufx.view.components.SpinnerGridPane;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;

/**
 * ViewModel managing menu background UI state and accessibility strings.
 *
 * <p>Provides localized StringBindings for button labels, tooltips, and roles, updating
 * automatically on locale changes. Supports background color and image initialization and updates,
 * including asynchronous image loading with user feedback.
 */
@Component
public class MenuBackgroundViewModel {

    private static final Logger log = LoggerFactory.getLogger(MenuBackgroundViewModel.class);

    private final ImageUtils imageUtils;

    private static final String ROLE_CLOSED = "menu.accessibility.role.description.closed";
    private static final String ROLE_OPENED = "menu.accessibility.role.description.opened";
    private static final String ROLE_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";
    private final StringBinding menuMaxiAccessibleText =
            createStringBinding("menu.maxi.button.background.accessibility");
    private final StringBinding menuMaxiTooltip =
            createFormattedBinding("menu.maxi.button.background.accessibility", ROLE_CLOSED);
    private final StringBinding menuMaxiRoleDescription = createStringBinding(ROLE_CLOSED);
    private final StringBinding menuMaxiText =
            createStringBinding("menu.maxi.button.background.text");
    private final StringBinding reduceAccessibleText =
            createStringBinding("menu.background.button.reduce.accessibility");
    private final StringBinding reduceTooltip =
            createStringBinding("menu.background.button.reduce.accessibility");
    private final StringBinding reduceText =
            createStringBinding("menu.background.button.reduce.text");
    private final StringBinding backgroundAccessibleText =
            createStringBinding("menu.background.button.background.accessibility");
    private final StringBinding backgroundTooltip =
            createFormattedBinding("menu.background.button.background.accessibility", ROLE_OPENED);
    private final StringBinding backgroundRoleDescription = createStringBinding(ROLE_OPENED);
    private final StringBinding backgroundText =
            createStringBinding("menu.background.button.background.text");
    private final StringBinding imageAccessibleText =
            createStringBinding("menu.background.button.image.accessibility");
    private final StringBinding imageTooltip =
            createFormattedBinding(
                    "menu.background.button.image.accessibility", ROLE_SUBMENU_OPTION);
    private final StringBinding imageRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
    private final StringBinding imageText =
            createStringBinding("menu.background.button.image.text");
    private final StringBinding colorAccessibleText =
            createStringBinding("menu.background.button.color.accessibility");
    private final StringBinding colorTooltip =
            createFormattedBinding(
                    "menu.background.button.color.accessibility", ROLE_SUBMENU_OPTION);
    private final StringBinding colorRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);

    public MenuBackgroundViewModel() {
        this.imageUtils = new ImageUtils();
    }

    /** Creates a localized StringBinding for the given key, updated on locale changes. */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /** Creates a localized StringBinding by concatenating two keys, updated on locale changes. */
    private StringBinding createFormattedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key) + I18n.INSTANCE.getValue(suffixKey),
                I18n.INSTANCE.localeProperty());
    }

    public StringBinding menuMaxiAccessibleTextProperty() {
        return menuMaxiAccessibleText;
    }

    public StringBinding menuMaxiTooltipProperty() {
        return menuMaxiTooltip;
    }

    public StringBinding menuMaxiRoleDescriptionProperty() {
        return menuMaxiRoleDescription;
    }

    public StringBinding menuMaxiTextProperty() {
        return menuMaxiText;
    }

    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    public StringBinding reduceTextProperty() {
        return reduceText;
    }

    public StringBinding backgroundAccessibleTextProperty() {
        return backgroundAccessibleText;
    }

    public StringBinding backgroundTooltipProperty() {
        return backgroundTooltip;
    }

    public StringBinding backgroundRoleDescriptionProperty() {
        return backgroundRoleDescription;
    }

    public StringBinding backgroundTextProperty() {
        return backgroundText;
    }

    public StringBinding imageAccessibleTextProperty() {
        return imageAccessibleText;
    }

    public StringBinding imageTooltipProperty() {
        return imageTooltip;
    }

    public StringBinding imageRoleDescriptionProperty() {
        return imageRoleDescription;
    }

    public StringBinding imageTextProperty() {
        return imageText;
    }

    public StringBinding colorAccessibleTextProperty() {
        return colorAccessibleText;
    }

    public StringBinding colorTooltipProperty() {
        return colorTooltip;
    }

    public StringBinding colorRoleDescriptionProperty() {
        return colorRoleDescription;
    }

    /// ///////////////////////////////////////

    /**
     * Initializes the GridPane background with either a predefined color or image. If a color is
     * defined, applies it and updates the ColorPicker. If an image path is defined, loads and
     * applies the image asynchronously.
     *
     * @param sudokuFX The GridPane to update.
     * @param menuBackgroundButtonColor The ColorPicker to update with the color value.
     * @param toaster The toaster for user notifications.
     * @param spinner The spinner to indicate loading state.
     */
    public void init(
            GridPane sudokuFX,
            ColorPicker menuBackgroundButtonColor,
            ToasterVBox toaster,
            SpinnerGridPane spinner) {
        // TODO: SERVICE GET & SET COLOR OR IMAGE
        // IF COLOR
        setColorFromModel(sudokuFX, menuBackgroundButtonColor, "99b3ffcd");
        // IF IMAGE
        handleFileImageChooser(new File("C:\\Users"), toaster, spinner, sudokuFX);
    }

    /**
     * Sets the background color of the given GridPane.
     *
     * @param sudokuFX The GridPane to update.
     * @param color The color to apply as the background.
     */
    public void updateBackgroundColorAndApply(GridPane sudokuFX, Color color) {
        // TODO: SERVICE STORE COLOR AS color.toString().substring(2)
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Handles image file selection by validating the file format and starting the image loading
     * process.
     *
     * @param selectedFile The selected image file.
     * @param toaster The toaster component for user notifications.
     * @param spinner The spinner component to indicate loading.
     * @param sudokuFX The GridPane where the background image will be applied.
     */
    public void handleFileImageChooser(
            File selectedFile, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        if (selectedFile != null && imageUtils.isValidImage(selectedFile)) {
            loadImage(selectedFile, toaster, spinner, sudokuFX);
        } else {
            String errorMessage =
                    I18n.INSTANCE.getValue(
                            "toast.error.backgroundviewmodel.handlefileimagechooser");
            log.error("██ Exception handleFileImageChooser : {}", errorMessage);
            toaster.addToast(errorMessage, "", ToastLevels.ERROR, true);
        }
    }

    /**
     * Applies a background color to the GridPane and sets it in the ColorPicker.
     *
     * @param sudokuFX The GridPane to update.
     * @param menuBackgroundButtonColor The ColorPicker to update.
     * @param colorValueFromModel The hex color string (e.g., "99b3ffcd").
     */
    private void setColorFromModel(
            GridPane sudokuFX, ColorPicker menuBackgroundButtonColor, String colorValueFromModel) {
        Color color = imageUtils.intToColor(Integer.parseUnsignedInt(colorValueFromModel, 16));
        menuBackgroundButtonColor.setValue(color);
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Starts the asynchronous image loading and resizing task.
     *
     * @param selectedFile The selected image file.
     * @param toaster The toaster component for user notifications.
     * @param spinner The spinner component to indicate loading.
     * @param sudokuFX The GridPane where the background image will be applied.
     */
    private void loadImage(
            File selectedFile, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        String fileUri = selectedFile.toURI().toString();
        spinner.showSpinner(true);
        toaster.addToast(
                I18n.INSTANCE.getValue("toast.msg.backgroundviewmodel.loadImage"),
                fileUri,
                ToastLevels.INFO,
                false);
        Task<BackgroundImage> backgroundTask =
                new Task<>() {
                    @Override
                    protected BackgroundImage call() {
                        try {
                            Image tempImage = new Image(fileUri, false);
                            double scaleFactor = imageUtils.calculateImageScaleFactor(tempImage);
                            Image resizedImage =
                                    new Image(
                                            fileUri,
                                            tempImage.getWidth() * scaleFactor,
                                            tempImage.getHeight() * scaleFactor,
                                            true,
                                            true);
                            if (resizedImage.isError()) {
                                log.error(
                                        "██ Exception resizedImage.isError() : {}",
                                        resizedImage.getException().getMessage(),
                                        resizedImage.getException());
                                return null;
                            }
                            return imageUtils.createBackgroundImage(resizedImage);
                        } catch (Exception e) {
                            log.error("██ Exception loadImage call() : {}", e.getMessage(), e);
                            return null;
                        }
                    }
                };
        backgroundTask.setOnSucceeded(
                e -> onImageTaskComplete(backgroundTask, toaster, spinner, sudokuFX));
        backgroundTask.setOnFailed(e -> onImageTaskError(e, toaster, spinner));
        new Thread(backgroundTask).start();
    }

    /**
     * Called upon successful completion of the image loading task. Applies the loaded background
     * image or shows an error toast if loading failed.
     *
     * @param backgroundTask The completed background task.
     * @param toaster The toaster component for user notifications.
     * @param spinner The spinner component to hide loading state.
     * @param sudokuFX The GridPane where the background image will be applied.
     */
    private void onImageTaskComplete(
            Task<BackgroundImage> backgroundTask,
            ToasterVBox toaster,
            SpinnerGridPane spinner,
            GridPane sudokuFX) {
        Platform.runLater(
                () -> {
                    toaster.removeToast();
                    BackgroundImage backgroundImage = backgroundTask.getValue();
                    if (backgroundImage != null) {
                        // TODO: SERVICE SET
                        sudokuFX.setBackground(new Background(backgroundImage));
                    } else {
                        toaster.addToast(
                                I18n.INSTANCE.getValue(
                                        "toast.error.backgroundviewmodel.onimagetaskcomplete"),
                                "",
                                ToastLevels.ERROR,
                                true);
                    }
                    spinner.showSpinner(false);
                });
    }

    /**
     * Called when the image loading task fails. Logs the error and shows an error toast to the
     * user.
     *
     * @param e The failure event.
     * @param toaster The toaster component for user notifications.
     * @param spinner The spinner component to hide loading state.
     */
    private void onImageTaskError(
            WorkerStateEvent e, ToasterVBox toaster, SpinnerGridPane spinner) {
        Throwable exception = e.getSource().getException();
        Platform.runLater(
                () -> {
                    toaster.removeToast();
                    log.error(
                            "██ Exception onImageTaskError : {}",
                            exception.getMessage(),
                            exception);
                    toaster.addToast(
                            I18n.INSTANCE.getValue("error.backgroundviewmodel.onimagetaskerror"),
                            Objects.toString(exception.getMessage(), ""),
                            ToastLevels.ERROR,
                            true);
                    spinner.showSpinner(false);
                });
    }
}
