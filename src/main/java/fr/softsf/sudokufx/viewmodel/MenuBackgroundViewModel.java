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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.util.ImageUtils;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

/**
 * ViewModel managing menu background UI state and accessibility strings.
 *
 * <p>Provides localized StringBindings for button labels, tooltips, and roles, updating
 * automatically on locale changes. Supports background color and image initialization and updates,
 * including asynchronous image loading with user feedback.
 */
@Component
public class MenuBackgroundViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(MenuBackgroundViewModel.class);
    private static final int HEX_RADIX = 16;

    private final ImageUtils imageUtils;

    private static final String ROLE_CLOSED = "menu.accessibility.role.description.closed";
    private static final String ROLE_OPENED = "menu.accessibility.role.description.opened";
    private static final String ROLE_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";

    private final StringBinding backgroundMenuMaxiAccessibleText;
    private final StringBinding backgroundMenuMaxiTooltip;
    private final StringBinding backgroundMenuMaxiRoleDescription;
    private final StringBinding backgroundMenuMaxiText;

    private final StringBinding backgroundReduceAccessibleText;
    private final StringBinding backgroundReduceTooltip;
    private final StringBinding backgroundReduceText;

    private final StringBinding backgroundAccessibleText;
    private final StringBinding backgroundTooltip;
    private final StringBinding backgroundRoleDescription;
    private final StringBinding backgroundText;

    private final StringBinding backgroundImageAccessibleText;
    private final StringBinding backgroundImageTooltip;
    private final StringBinding backgroundImageRoleDescription;
    private final StringBinding backgroundImageText;

    private final StringBinding backgroundOpacityAccessibleText;
    private final StringBinding backgroundOpacityTooltip;
    private final StringBinding backgroundOpacityRoleDescription;
    private final StringBinding backgroundOpacityText;

    private final StringBinding backgroundColorAccessibleText;
    private final StringBinding backgroundColorTooltip;
    private final StringBinding backgroundColorRoleDescription;

    private boolean isOpaqueMode = false;

    public MenuBackgroundViewModel() {
        this.imageUtils = new ImageUtils();
        backgroundMenuMaxiAccessibleText =
                createStringBinding("menu.maxi.button.background.accessibility");
        backgroundMenuMaxiTooltip =
                createFormattedBinding("menu.maxi.button.background.accessibility", ROLE_CLOSED);
        backgroundMenuMaxiRoleDescription = createStringBinding(ROLE_CLOSED);
        backgroundMenuMaxiText = createStringBinding("menu.maxi.button.background.text");
        backgroundReduceAccessibleText =
                createStringBinding("menu.background.button.reduce.accessibility");
        backgroundReduceTooltip =
                createStringBinding("menu.background.button.reduce.accessibility");
        backgroundReduceText = createStringBinding("menu.background.button.reduce.text");
        backgroundAccessibleText =
                createStringBinding("menu.background.button.background.accessibility");
        backgroundTooltip =
                createFormattedBinding(
                        "menu.background.button.background.accessibility", ROLE_OPENED);
        backgroundRoleDescription = createStringBinding(ROLE_OPENED);
        backgroundText = createStringBinding("menu.background.button.background.text");
        backgroundImageAccessibleText =
                createStringBinding("menu.background.button.image.accessibility");
        backgroundImageTooltip =
                createFormattedBinding(
                        "menu.background.button.image.accessibility", ROLE_SUBMENU_OPTION);
        backgroundImageRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        backgroundImageText = createStringBinding("menu.background.button.image.text");
        backgroundOpacityAccessibleText =
                createStringBinding("menu.background.button.opacity.accessibility");
        backgroundOpacityTooltip =
                createFormattedBinding(
                        "menu.background.button.opacity.accessibility", ROLE_SUBMENU_OPTION);
        backgroundOpacityRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        backgroundOpacityText = createStringBinding("menu.background.button.opacity.text");
        backgroundColorAccessibleText =
                createStringBinding("menu.background.button.color.accessibility");
        backgroundColorTooltip =
                createFormattedBinding(
                        "menu.background.button.color.accessibility", ROLE_SUBMENU_OPTION);
        backgroundColorRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
    }

    /**
     * Creates a StringBinding that returns the localized string for the given key, updating
     * automatically when the locale changes.
     *
     * @param key the i18n translation key
     * @return a StringBinding with the localized string value
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a StringBinding that returns the concatenation of two localized strings (main key and
     * suffix key), updating automatically when the locale changes.
     *
     * @param key the main i18n translation key
     * @param suffixKey the suffix i18n translation key to append
     * @return a StringBinding with the concatenated localized string value
     */
    private StringBinding createFormattedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key) + I18n.INSTANCE.getValue(suffixKey),
                I18n.INSTANCE.localeProperty());
    }

    public StringBinding backgroundMenuMaxiAccessibleTextProperty() {
        return backgroundMenuMaxiAccessibleText;
    }

    public StringBinding backgroundMenuMaxiTooltipProperty() {
        return backgroundMenuMaxiTooltip;
    }

    public StringBinding backgroundMenuMaxiRoleDescriptionProperty() {
        return backgroundMenuMaxiRoleDescription;
    }

    public StringBinding backgroundMenuMaxiTextProperty() {
        return backgroundMenuMaxiText;
    }

    public StringBinding backgroundReduceAccessibleTextProperty() {
        return backgroundReduceAccessibleText;
    }

    public StringBinding backgroundReduceTooltipProperty() {
        return backgroundReduceTooltip;
    }

    public StringBinding backgroundReduceTextProperty() {
        return backgroundReduceText;
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

    public StringBinding backgroundImageAccessibleTextProperty() {
        return backgroundImageAccessibleText;
    }

    public StringBinding backgroundImageTooltipProperty() {
        return backgroundImageTooltip;
    }

    public StringBinding backgroundImageRoleDescriptionProperty() {
        return backgroundImageRoleDescription;
    }

    public StringBinding backgroundImageTextProperty() {
        return backgroundImageText;
    }

    public StringBinding backgroundOpacityAccessibleTextProperty() {
        return backgroundOpacityAccessibleText;
    }

    public StringBinding backgroundOpacityTooltipProperty() {
        return backgroundOpacityTooltip;
    }

    public StringBinding backgroundOpacityRoleDescriptionProperty() {
        return backgroundOpacityRoleDescription;
    }

    public StringBinding backgroundOpacityTextProperty() {
        return backgroundOpacityText;
    }

    public StringBinding backgroundColorAccessibleTextProperty() {
        return backgroundColorAccessibleText;
    }

    public StringBinding backgroundColorTooltipProperty() {
        return backgroundColorTooltip;
    }

    public StringBinding backgroundColorRoleDescriptionProperty() {
        return backgroundColorRoleDescription;
    }

    /**
     * Initializes the GridPane background with settings from database. Loads and applies saved
     * background configuration including color, image, and grid transparency.
     *
     * <p>This method performs the following initialization steps:
     *
     * <ul>
     *   <li>Retrieves and applies saved background color from database
     *   <li>Loads and applies saved background image if configured
     *   <li>Sets grid transparency mode based on user preferences
     *   <li>Updates UI components (ColorPicker, etc.) with current values
     * </ul>
     *
     * @param sudokuFX The GridPane to initialize with background settings
     * @param menuBackgroundButtonColor The ColorPicker to sync with current color value
     * @param toaster The toaster component for user notifications during image loading
     * @param spinner The spinner component to indicate loading state during operations
     * @see #setColorFromModel(GridPane, ColorPicker, String)
     * @see #handleFileImageChooser(File, ToasterVBox, SpinnerGridPane, GridPane)
     * @see #setOpaqueMode(boolean)
     */
    public void init(
            GridPane sudokuFX,
            ColorPicker menuBackgroundButtonColor,
            ToasterVBox toaster,
            SpinnerGridPane spinner) {
        // TODO: SERVICE GET & SET COLOR OR IMAGE AND GRID TRANSPARENCY
        // IF COLOR
        setColorFromModel(sudokuFX, menuBackgroundButtonColor, "99b3ffcd");
        // IF IMAGE
        handleFileImageChooser(new File("C:\\Users"), toaster, spinner, sudokuFX);
        // IF GRID TRANSPARENCY
        setOpaqueMode(true);
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
     * Opens file chooser dialog for background image selection.
     *
     * @param primaryStage parent stage for the dialog
     * @return selected image file or null if cancelled/error
     */
    public File chooseBackgroundImage(Stage primaryStage) {
        try {
            FileChooser fileChooser = createImageFileChooser();
            return fileChooser.showOpenDialog(primaryStage);
        } catch (Exception e) {
            LOG.error("Error choosing background image: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Creates configured FileChooser for image files.
     *
     * @return FileChooser with image filters and home directory set
     */
    private FileChooser createImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                I18n.INSTANCE.getValue("filechooser.extension"),
                                "*.jpg",
                                "*.jpeg",
                                "*.png",
                                "*.bmp"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        return fileChooser;
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
            LOG.error("██ Exception handleFileImageChooser : {}", errorMessage);
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
        Color color =
                imageUtils.intToColor(Integer.parseUnsignedInt(colorValueFromModel, HEX_RADIX));
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
                                LOG.error(
                                        "██ Exception resizedImage.isError() : {}",
                                        resizedImage.getException().getMessage(),
                                        resizedImage.getException());
                                return null;
                            }
                            return imageUtils.createBackgroundImage(resizedImage);
                        } catch (Exception e) {
                            LOG.error("██ Exception loadImage call() : {}", e.getMessage(), e);
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
    void onImageTaskError(WorkerStateEvent e, ToasterVBox toaster, SpinnerGridPane spinner) {
        Throwable exception = e.getSource().getException();
        Platform.runLater(
                () -> {
                    toaster.removeToast();
                    LOG.error(
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

    /**
     * Toggles grid opacity mode and returns the new state.
     *
     * @return true if opaque mode is now active, false if transparent
     */
    public boolean toggleGridOpacity() {
        isOpaqueMode = !isOpaqueMode;
        return isOpaqueMode;
    }

    /**
     * Sets the grid opacity mode.
     *
     * @param opaque true for opaque mode, false for transparent
     */
    private void setOpaqueMode(boolean opaque) {
        this.isOpaqueMode = opaque;
    }

    /** Gets the grid opacity mode. */
    public boolean getOpaqueMode() {
        return this.isOpaqueMode;
    }
}
