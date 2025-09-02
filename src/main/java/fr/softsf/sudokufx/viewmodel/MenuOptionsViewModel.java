/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.util.ImageMeta;
import fr.softsf.sudokufx.common.util.ImageUtils;
import fr.softsf.sudokufx.service.AudioService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

/**
 * ViewModel managing menu options UI state and accessibility strings.
 *
 * <p>Provides localized StringBindings for button labels, tooltips, and roles, updating
 * automatically on locale changes. Supports options color and image initialization and updates,
 * including asynchronous image loading with user feedback.
 */
@Component
public class MenuOptionsViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(MenuOptionsViewModel.class);
    private static final int HEX_RADIX = 16;

    private final AudioService audioService;
    private final ImageUtils imageUtils;

    private static final String ROLE_CLOSED = "menu.accessibility.role.description.closed";
    private static final String ROLE_OPENED = "menu.accessibility.role.description.opened";
    private static final String ROLE_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";

    private final StringBinding optionsMenuMaxiAccessibleText;
    private final StringBinding optionsMenuMaxiTooltip;
    private final StringBinding optionsMenuMaxiRoleDescription;
    private final StringBinding optionsMenuMaxiText;

    private final StringBinding optionsReduceAccessibleText;
    private final StringBinding optionsReduceTooltip;
    private final StringBinding optionsReduceText;

    private final StringBinding optionsAccessibleText;
    private final StringBinding optionsTooltip;
    private final StringBinding optionsRoleDescription;
    private final StringBinding optionsText;

    private final StringBinding optionsImageAccessibleText;
    private final StringBinding optionsImageTooltip;
    private final StringBinding optionsImageRoleDescription;
    private final StringBinding optionsImageText;

    private final StringBinding optionsColorAccessibleText;
    private final StringBinding optionsColorTooltip;
    private final StringBinding optionsColorRoleDescription;

    private final BooleanProperty gridOpacityProperty = new SimpleBooleanProperty(false);
    private final StringBinding optionsOpacityAccessibleText;
    private final StringBinding optionsOpacityTooltip;
    private final StringBinding optionsOpacityRoleDescription;
    private final StringBinding optionsOpacityText;
    private final StringBinding optionsOpacityIcon;

    private static final String ICON_OPACITY_ON = "\ue891";
    private static final String ICON_OPACITY_OFF = "\ue0c4";

    private final BooleanProperty muteProperty = new SimpleBooleanProperty(true);
    private final StringBinding optionsMuteAccessibleText;
    private final StringBinding optionsMuteTooltip;
    private final StringBinding optionsMuteRoleDescription;
    private final StringBinding optionsMuteText;
    private final StringBinding optionsMuteIcon;

    private static final String ICON_MUTE_ON = "\ue050";
    private static final String ICON_MUTE_OFF = "\ue04f";

    public MenuOptionsViewModel(AudioService audioService) {
        this.imageUtils = new ImageUtils();
        this.audioService = audioService;
        optionsMenuMaxiAccessibleText =
                createStringBinding("menu.maxi.button.options.accessibility");
        optionsMenuMaxiTooltip =
                createFormattedBinding("menu.maxi.button.options.accessibility", ROLE_CLOSED);
        optionsMenuMaxiRoleDescription = createStringBinding(ROLE_CLOSED);
        optionsMenuMaxiText = createStringBinding("menu.maxi.button.options.text");
        optionsReduceAccessibleText =
                createStringBinding("menu.options.button.reduce.accessibility");
        optionsReduceTooltip = createStringBinding("menu.options.button.reduce.accessibility");
        optionsReduceText = createStringBinding("menu.options.button.reduce.text");
        optionsAccessibleText = createStringBinding("menu.options.button.options.accessibility");
        optionsTooltip =
                createFormattedBinding("menu.options.button.options.accessibility", ROLE_OPENED);
        optionsRoleDescription = createStringBinding(ROLE_OPENED);
        optionsText = createStringBinding("menu.options.button.options.text");
        optionsImageAccessibleText = createStringBinding("menu.options.button.image.accessibility");
        optionsImageTooltip =
                createFormattedBinding(
                        "menu.options.button.image.accessibility", ROLE_SUBMENU_OPTION);
        optionsImageRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsImageText = createStringBinding("menu.options.button.image.text");
        optionsColorAccessibleText = createStringBinding("menu.options.button.color.accessibility");
        optionsColorTooltip =
                createFormattedBinding(
                        "menu.options.button.color.accessibility", ROLE_SUBMENU_OPTION);
        optionsColorRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsOpacityAccessibleText =
                createFormattedBinding(
                        "menu.options.button.opacity.accessibility",
                        () -> gridOpacityText(!gridOpacityProperty.get()),
                        gridOpacityProperty);
        optionsOpacityTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.options.button.opacity.accessibility",
                        () -> gridOpacityText(!gridOpacityProperty.get()),
                        ROLE_SUBMENU_OPTION,
                        gridOpacityProperty);
        optionsOpacityRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsOpacityText =
                Bindings.createStringBinding(
                        () -> gridOpacityText(gridOpacityProperty.get()),
                        gridOpacityProperty,
                        I18n.INSTANCE.localeProperty());
        optionsOpacityIcon =
                Bindings.createStringBinding(
                        () -> gridOpacityProperty.get() ? ICON_OPACITY_ON : ICON_OPACITY_OFF,
                        gridOpacityProperty);

        optionsMuteAccessibleText =
                createFormattedBinding(
                        "menu.options.button.mute.accessibility",
                        () -> muteInfo(muteProperty.get()),
                        muteProperty);
        optionsMuteTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.options.button.mute.accessibility",
                        () -> muteInfo(muteProperty.get()),
                        ROLE_SUBMENU_OPTION,
                        muteProperty);
        optionsMuteRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsMuteText =
                Bindings.createStringBinding(
                        () -> muteText(muteProperty.get()),
                        muteProperty,
                        I18n.INSTANCE.localeProperty());
        optionsMuteIcon =
                Bindings.createStringBinding(
                        () -> muteProperty.get() ? ICON_MUTE_ON : ICON_MUTE_OFF, muteProperty);
    }

    /**
     * Returns the localized text corresponding to the grid opacity state.
     *
     * @param isOpaque true for "opaque" text, false for "transparent" text
     * @return localized string for the given opacity state
     */
    private String gridOpacityText(boolean isOpaque) {
        String stateKey =
                isOpaque
                        ? "menu.options.button.opacity.text.opaque"
                        : "menu.options.button.opacity.text.transparent";
        return I18n.INSTANCE.getValue(stateKey);
    }

    /**
     * Returns the localized text corresponding to the mute state.
     *
     * @param isMuted true for "muted" audio, false for "unmuted" audio
     * @return localized string for the given mute state
     */
    private String muteText(boolean isMuted) {
        String stateKey =
                isMuted
                        ? "menu.options.button.mute.text.muted"
                        : "menu.options.button.mute.text.unmuted";
        return I18n.INSTANCE.getValue(stateKey);
    }

    /**
     * Returns the localized information about the current mute state.
     *
     * <p>This text is intended for tooltips or accessibility purposes, indicating whether the audio
     * is currently muted or unmuted.
     *
     * @param isMuted true if audio is currently muted, false if unmuted
     * @return localized string representing the current audio state
     */
    private String muteInfo(boolean isMuted) {
        String key =
                isMuted
                        ? "menu.options.button.mute.text.muted.info"
                        : "menu.options.button.mute.text.unmuted.info";
        return I18n.INSTANCE.getValue(key);
    }

    /**
     * Creates a formatted localized binding with one dynamic argument. Updates when locale or any
     * of the given dependencies change.
     */
    private StringBinding createFormattedBinding(
            String key, Supplier<String> argSupplier, Observable... dependencies) {
        return Bindings.createStringBinding(
                () -> MessageFormat.format(I18n.INSTANCE.getValue(key), argSupplier.get()),
                concatDependencies(dependencies));
    }

    /**
     * Creates a localized binding combining a formatted message and a suffix. Updates when locale
     * or any of the given dependencies change.
     */
    private StringBinding createFormattedAndConcatenatedBinding(
            String key,
            Supplier<String> argSupplier,
            String suffixKey,
            Observable... dependencies) {
        return Bindings.createStringBinding(
                () ->
                        MessageFormat.format(I18n.INSTANCE.getValue(key), argSupplier.get())
                                + I18n.INSTANCE.getValue(suffixKey),
                concatDependencies(dependencies));
    }

    /** Utility: always observe the locale in addition to custom dependencies. */
    private Observable[] concatDependencies(Observable... dependencies) {
        Observable[] base = new Observable[] {I18n.INSTANCE.localeProperty()};
        if (dependencies == null || dependencies.length == 0) {
            return base;
        }
        Observable[] result = new Observable[base.length + dependencies.length];
        System.arraycopy(base, 0, result, 0, base.length);
        System.arraycopy(dependencies, 0, result, base.length, dependencies.length);
        return result;
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

    public BooleanProperty gridOpacityProperty() {
        return gridOpacityProperty;
    }

    public StringBinding optionsMenuMaxiAccessibleTextProperty() {
        return optionsMenuMaxiAccessibleText;
    }

    public StringBinding optionsMenuMaxiTooltipProperty() {
        return optionsMenuMaxiTooltip;
    }

    public StringBinding optionsMenuMaxiRoleDescriptionProperty() {
        return optionsMenuMaxiRoleDescription;
    }

    public StringBinding optionsMenuMaxiTextProperty() {
        return optionsMenuMaxiText;
    }

    public StringBinding optionsReduceAccessibleTextProperty() {
        return optionsReduceAccessibleText;
    }

    public StringBinding optionsReduceTooltipProperty() {
        return optionsReduceTooltip;
    }

    public StringBinding optionsReduceTextProperty() {
        return optionsReduceText;
    }

    public StringBinding optionsAccessibleTextProperty() {
        return optionsAccessibleText;
    }

    public StringBinding optionsTooltipProperty() {
        return optionsTooltip;
    }

    public StringBinding optionsRoleDescriptionProperty() {
        return optionsRoleDescription;
    }

    public StringBinding optionsTextProperty() {
        return optionsText;
    }

    public StringBinding optionsImageAccessibleTextProperty() {
        return optionsImageAccessibleText;
    }

    public StringBinding optionsImageTooltipProperty() {
        return optionsImageTooltip;
    }

    public StringBinding optionsImageRoleDescriptionProperty() {
        return optionsImageRoleDescription;
    }

    public StringBinding optionsImageTextProperty() {
        return optionsImageText;
    }

    public StringBinding optionsColorAccessibleTextProperty() {
        return optionsColorAccessibleText;
    }

    public StringBinding optionsColorTooltipProperty() {
        return optionsColorTooltip;
    }

    public StringBinding optionsColorRoleDescriptionProperty() {
        return optionsColorRoleDescription;
    }

    public StringBinding optionsOpacityAccessibleTextProperty() {
        return optionsOpacityAccessibleText;
    }

    public StringBinding optionsOpacityTooltipProperty() {
        return optionsOpacityTooltip;
    }

    public StringBinding optionsOpacityRoleDescriptionProperty() {
        return optionsOpacityRoleDescription;
    }

    public StringBinding optionsOpacityTextProperty() {
        return optionsOpacityText;
    }

    public StringBinding optionsOpacityIconProperty() {
        return optionsOpacityIcon;
    }

    public StringBinding optionsMuteAccessibleTextProperty() {
        return optionsMuteAccessibleText;
    }

    public StringBinding optionsMuteTooltipProperty() {
        return optionsMuteTooltip;
    }

    public StringBinding optionsMuteRoleDescriptionProperty() {
        return optionsMuteRoleDescription;
    }

    public StringBinding optionsMuteTextProperty() {
        return optionsMuteText;
    }

    public StringBinding optionsMuteIconProperty() {
        return optionsMuteIcon;
    }

    /**
     * Initializes the menu options UI state, including:
     *
     * <ul>
     *   <li>Background color from database
     *   <li>Background image if configured
     *   <li>Grid opacity mode
     *   <li>Audio mute state
     * </ul>
     *
     * @param sudokuFX The GridPane to apply background settings
     * @param menuOptionsButtonColor The ColorPicker to synchronize with the current color
     * @param toaster The toaster component for user notifications during image loading
     * @param spinner The spinner component to indicate loading state
     * @see #setColorFromModel(GridPane, ColorPicker, String)
     * @see #loadBackgroundImage(File, ToasterVBox, SpinnerGridPane, GridPane)
     */
    public void init(
            GridPane sudokuFX,
            ColorPicker menuOptionsButtonColor,
            ToasterVBox toaster,
            SpinnerGridPane spinner) {
        // TODO: SERVICE GET & SET COLOR OR IMAGE AND GRID TRANSPARENCY
        // IF COLOR
        setColorFromModel(sudokuFX, menuOptionsButtonColor, "99b3ffcd");
        // IF IMAGE
        loadBackgroundImage(new File("C:\\Users"), toaster, spinner, sudokuFX);
        // IF GRID ISOPAQUE
        gridOpacityProperty.set(true);
        // IF MUTE ISMUTED
        initializeAudio(true);
    }

    /**
     * Configures the audio service and updates the ViewModel mute property.
     *
     * @param muted true to start muted, false for unmuted
     */
    private void initializeAudio(boolean muted) {
        audioService.setMuted(muted);
        muteProperty.set(muted);
    }

    /**
     * Sets the background color of the given GridPane.
     *
     * @param sudokuFX The GridPane to update.
     * @param color The color to apply as the background.
     */
    public void updateOptionsColorAndApply(GridPane sudokuFX, Color color) {
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
    public void loadBackgroundImage(
            File selectedFile, ToasterVBox toaster, SpinnerGridPane spinner, GridPane sudokuFX) {
        if (selectedFile != null && imageUtils.isValidImage(selectedFile)) {
            loadImage(selectedFile, toaster, spinner, sudokuFX);
        } else {
            String errorMessage =
                    I18n.INSTANCE.getValue("toast.error.optionsviewmodel.handlefileimagechooser");
            LOG.error("██ Exception handleFileImageChooser : {}", errorMessage);
            toaster.addToast(errorMessage, "", ToastLevels.ERROR, true);
        }
    }

    /**
     * Applies a background color to the GridPane and sets it in the ColorPicker.
     *
     * @param sudokuFX The GridPane to update.
     * @param menuOptionsButtonColor The ColorPicker to update.
     * @param colorValueFromModel The hex color string (e.g., "99b3ffcd").
     */
    private void setColorFromModel(
            GridPane sudokuFX, ColorPicker menuOptionsButtonColor, String colorValueFromModel) {
        Color color =
                imageUtils.intToColor(Integer.parseUnsignedInt(colorValueFromModel, HEX_RADIX));
        menuOptionsButtonColor.setValue(color);
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Starts the asynchronous image loading and resizing task.
     *
     * <p>The task runs on a separate daemon thread, so it does not block application shutdown.
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
                I18n.INSTANCE.getValue("toast.msg.optionsviewmodel.loadImage"),
                fileUri,
                ToastLevels.INFO,
                false);
        Task<BackgroundImage> backgroundTask =
                new Task<>() {
                    @Override
                    protected BackgroundImage call() {
                        try {
                            ImageMeta meta = imageUtils.getImageMeta(selectedFile);
                            Image resizedImage =
                                    new Image(
                                            fileUri,
                                            meta.width() * meta.scaleFactor(),
                                            meta.height() * meta.scaleFactor(),
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
        Thread loaderThread = new Thread(backgroundTask, "ImageLoaderThread");
        loaderThread.setDaemon(true);
        loaderThread.start();
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
                                        "toast.error.optionsviewmodel.onimagetaskcomplete"),
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
                            I18n.INSTANCE.getValue("toast.error.optionsviewmodel.onimagetaskerror"),
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
        gridOpacityProperty.set(!gridOpacityProperty.get());
        return gridOpacityProperty.get();
    }

    /** Toggles the audio mute state. Updates the AudioService and the muteProperty accordingly. */
    public void toggleMute() {
        boolean newState = !muteProperty.get();
        audioService.setMuted(newState);
        muteProperty.set(newState);
    }
}
