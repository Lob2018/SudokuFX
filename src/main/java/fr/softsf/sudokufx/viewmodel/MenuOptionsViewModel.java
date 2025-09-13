/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Supplier;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.AudioUtils;
import fr.softsf.sudokufx.common.util.ImageMeta;
import fr.softsf.sudokufx.common.util.ImageUtils;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.service.business.OptionsService;
import fr.softsf.sudokufx.service.ui.AsyncFileProcessorService;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;
import jakarta.annotation.Nonnull;

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
    public static final String SUDOKU_FX_MUST_NOT_BE_NULL = "sudokuFX must not be null";
    public static final int HEXCOLOR_END_INDEX = 10;
    public static final int HEXCOLOR_BEGIN_INDEX = 2;

    private final AudioService audioService;
    private final AsyncFileProcessorService asyncFileProcessorService;
    private final ImageUtils imageUtils;
    private final AudioUtils audioUtils;
    private final PlayerStateHolder playerStateHolder;
    private final OptionsService optionsService;

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

    private final SimpleStringProperty songProperty = new SimpleStringProperty("");
    private final BooleanProperty songIsBlankProperty = new SimpleBooleanProperty(true);
    private final StringBinding optionsSongAccessibleText;
    private final StringBinding optionsSongTooltip;
    private final StringBinding optionsSongRoleDescription;
    private final StringBinding optionsSongText;

    private final StringBinding optionsClearSongAccessibleText;
    private final StringBinding optionsClearSongRoleDescription;
    private final StringBinding optionsClearSongTooltip;

    private ToasterVBox toaster;

    public MenuOptionsViewModel(
            AudioService audioService,
            AsyncFileProcessorService asyncFileProcessorService,
            PlayerStateHolder playerStateHolder,
            OptionsService optionsService) {
        this.imageUtils = new ImageUtils();
        this.audioUtils = new AudioUtils();
        this.audioService = audioService;
        this.optionsService = optionsService;
        this.asyncFileProcessorService = asyncFileProcessorService;
        this.playerStateHolder = playerStateHolder;
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
        optionsOpacityAccessibleText = initOpacityAccessibleText();
        optionsOpacityTooltip = initOpacityTooltip();
        optionsOpacityRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsOpacityText = initOpacityText();
        optionsOpacityIcon = initOpacityIcon();
        optionsMuteAccessibleText = initMuteAccessibleText();
        optionsMuteTooltip = initMuteTooltip();
        optionsMuteRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsMuteText = initMuteText();
        optionsMuteIcon = initMuteIcon();
        songIsBlankProperty.bind(
                Bindings.createBooleanBinding(
                        () -> StringUtils.isBlank(songProperty.get()), songProperty));
        optionsSongAccessibleText = initSongAccessibleText();
        optionsSongTooltip = initSongTooltip();
        optionsSongText = initSongText();
        optionsSongRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
        optionsClearSongAccessibleText =
                createFormattedBinding(
                        () -> "menu.options.button.clear.song.accessibility",
                        songProperty::get,
                        songProperty);
        optionsClearSongTooltip =
                createFormattedAndConcatenatedBinding(
                        () -> "menu.options.button.clear.song.accessibility",
                        songProperty::get,
                        ROLE_SUBMENU_OPTION,
                        songProperty);
        optionsClearSongRoleDescription = createStringBinding(ROLE_SUBMENU_OPTION);
    }

    /**
     * Creates the accessibility label for the opacity button, based on the inverse of the current
     * grid opacity state.
     */
    private StringBinding initOpacityAccessibleText() {
        return createFormattedBinding(
                () -> "menu.options.button.opacity.accessibility",
                () -> gridOpacityText(!gridOpacityProperty.get()),
                gridOpacityProperty);
    }

    /** Creates the tooltip for the opacity button, combining dynamic label and submenu role. */
    private StringBinding initOpacityTooltip() {
        return createFormattedAndConcatenatedBinding(
                () -> "menu.options.button.opacity.accessibility",
                () -> gridOpacityText(!gridOpacityProperty.get()),
                ROLE_SUBMENU_OPTION,
                gridOpacityProperty);
    }

    /**
     * Creates the visible label for the opacity button, reflecting the current grid opacity value.
     */
    private StringBinding initOpacityText() {
        return Bindings.createStringBinding(
                () -> gridOpacityText(gridOpacityProperty.get()),
                gridOpacityProperty,
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates the icon name for the opacity button, depending on whether opacity is enabled or not.
     */
    private StringBinding initOpacityIcon() {
        return Bindings.createStringBinding(
                () -> gridOpacityProperty.get() ? ICON_OPACITY_ON : ICON_OPACITY_OFF,
                gridOpacityProperty);
    }

    /** Creates the accessibility label for the mute button, based on the current mute state. */
    private StringBinding initMuteAccessibleText() {
        return createFormattedBinding(
                () -> "menu.options.button.mute.accessibility",
                () -> muteInfo(muteProperty.get()),
                muteProperty);
    }

    /** Creates the tooltip for the mute button, combining dynamic mute info and submenu role. */
    private StringBinding initMuteTooltip() {
        return createFormattedAndConcatenatedBinding(
                () -> "menu.options.button.mute.accessibility",
                () -> muteInfo(muteProperty.get()),
                ROLE_SUBMENU_OPTION,
                muteProperty);
    }

    /** Creates the visible label for the mute button, reflecting the current mute state. */
    private StringBinding initMuteText() {
        return Bindings.createStringBinding(
                () -> muteText(muteProperty.get()), muteProperty, I18n.INSTANCE.localeProperty());
    }

    /** Creates the icon name for the mute button, depending on whether mute is active or not. */
    private StringBinding initMuteIcon() {
        return Bindings.createStringBinding(
                () -> muteProperty.get() ? ICON_MUTE_ON : ICON_MUTE_OFF, muteProperty);
    }

    /**
     * Creates the accessibility label for the song button, depending on whether the song field is
     * empty.
     */
    private StringBinding initSongAccessibleText() {
        return createFormattedBinding(
                () ->
                        songIsBlankProperty.get()
                                ? "menu.options.button.song.empty.accessibility"
                                : "menu.options.button.song.accessibility",
                songProperty::get,
                songProperty);
    }

    /** Creates the tooltip for the song button, combining dynamic song info and submenu role. */
    private StringBinding initSongTooltip() {
        return createFormattedAndConcatenatedBinding(
                () ->
                        songIsBlankProperty.get()
                                ? "menu.options.button.song.empty.accessibility"
                                : "menu.options.button.song.accessibility",
                songProperty::get,
                ROLE_SUBMENU_OPTION,
                songProperty);
    }

    /**
     * Creates the visible label for the song button, adapting to whether a song is present or not.
     */
    private StringBinding initSongText() {
        return createFormattedBinding(
                () ->
                        songIsBlankProperty.get()
                                ? "menu.options.button.song.empty.text"
                                : "menu.options.button.song.text",
                songProperty::get,
                songProperty);
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
            Supplier<String> keySupplier,
            Supplier<String> argSupplier,
            Observable... dependencies) {
        return Bindings.createStringBinding(
                () ->
                        MessageFormat.format(
                                I18n.INSTANCE.getValue(keySupplier.get()), argSupplier.get()),
                concatDependencies(dependencies));
    }

    /**
     * Creates a localized binding combining a formatted message and a suffix. Updates when locale
     * or any of the given dependencies change.
     */
    private StringBinding createFormattedAndConcatenatedBinding(
            Supplier<String> keySupplier,
            Supplier<String> argSupplier,
            String suffixKey,
            Observable... dependencies) {
        return Bindings.createStringBinding(
                () ->
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(keySupplier.get()),
                                        argSupplier.get())
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

    public StringBinding optionsSongAccessibleTextProperty() {
        return optionsSongAccessibleText;
    }

    public StringBinding optionsSongTooltipProperty() {
        return optionsSongTooltip;
    }

    public StringBinding optionsSongRoleDescriptionProperty() {
        return optionsSongRoleDescription;
    }

    public StringBinding optionsSongTextProperty() {
        return optionsSongText;
    }

    public StringBinding optionsClearSongRoleDescriptionProperty() {
        return optionsClearSongRoleDescription;
    }

    public StringBinding optionsClearSongTooltipProperty() {
        return optionsClearSongTooltip;
    }

    public StringBinding optionsClearSongAccessibleTextProperty() {
        return optionsClearSongAccessibleText;
    }

    public BooleanProperty songIsBlankProperty() {
        return songIsBlankProperty;
    }

    /**
     * Initializes the menu options UI state for the current player, including:
     *
     * <ul>
     *   <li>Background color or image
     *   <li>Grid opacity mode
     *   <li>Audio mute state
     *   <li>Selected song
     * </ul>
     *
     * <p>This method synchronizes the UI components with the current player's options and prepares
     * the necessary properties for bindings.
     *
     * @param sudokuFX the GridPane to apply background settings
     * @param menuOptionsButtonColor the ColorPicker to synchronize with the current color
     * @param toaster the toaster component for user notifications during image loading
     * @param spinner the spinner component to indicate loading state
     */
    public void init(
            GridPane sudokuFX,
            ColorPicker menuOptionsButtonColor,
            ToasterVBox toaster,
            SpinnerGridPane spinner) {
        this.toaster = toaster;
        OptionsDto optionsDto = playerStateHolder.currentPlayerProperty().get().optionsidDto();
        if (optionsDto.imagepath().isEmpty()) {
            setColorFromModel(sudokuFX, menuOptionsButtonColor, optionsDto.hexcolor());
        } else {
            applyAndPersistBackgroundImage(new File(optionsDto.imagepath()), spinner, sudokuFX);
        }
        gridOpacityProperty.set(optionsDto.opaque());
        audioService.setMuted(optionsDto.muted());
        muteProperty.set(optionsDto.muted());
        if (optionsDto.songpath().isEmpty()) {
            return;
        }
        songProperty.set(Paths.get(optionsDto.songpath()).getFileName().toString());
    }

    /**
     * Resets the song path by clearing the current selection, persisting the change, and showing a
     * toast notification.
     */
    public void resetSongPath() {
        persistSongPath("", songProperty.get());
    }

    /**
     * Saves the provided song file path to the database and shows a confirmation toast.
     *
     * <p>The method validates that the file is non-null, exists, and has a supported audio format
     * (MP3, WAV, AAC, M4A, AIF, AIFF). If valid, it updates the database and {@code songProperty};
     * otherwise, it logs an error and shows an error toast via {@link ToasterVBox}.
     *
     * @param file the audio file to save; must not be {@code null}, must exist, and be a valid
     *     audio file
     */
    public void saveSong(File file) {
        if (file != null && audioUtils.isValidAudio(file) && file.exists()) {
            persistSongPath(file.getAbsolutePath(), file.getName());
            return;
        }
        String errorMessage =
                I18n.INSTANCE.getValue("toast.error.optionsviewmodel.handlefileaudiochooser");
        LOG.error("██ Exception handleFileAudioChooser : {}", errorMessage);
        toaster.addToast(errorMessage, "", ToastLevels.ERROR, true);
    }

    /**
     * Persists the audio file path (songPath) in the current player's options.
     *
     * <p>A copy of the current {@link OptionsDto} is created with the new path and persisted via
     * {@link OptionsService#updateOptions(OptionsDto)}. On success, the current player is
     * refreshed, {@code songProperty} is updated, and an info toast is shown. On failure, the
     * exception is logged and an error toast with details is displayed.
     *
     * @param absolutePath the new audio file absolute path
     * @param name the file name, used in toast messages
     */
    private void persistSongPath(String absolutePath, String name) {
        boolean clear = StringUtils.isEmpty(absolutePath);
        OptionsDto currentOptions = playerStateHolder.getCurrentPlayer().optionsidDto();
        OptionsDto toSaveOptions = currentOptions.withSongpath(absolutePath);
        try {
            optionsService.updateOptions(toSaveOptions);
            playerStateHolder.refreshCurrentPlayer();
            songProperty.set(clear ? "" : name);
            toaster.addToast(
                    MessageFormat.format(
                            I18n.INSTANCE.getValue(
                                    clear
                                            ? "toast.msg.optionsviewmodel.disabled"
                                            : "toast.msg.optionsviewmodel.saved"),
                            name),
                    absolutePath,
                    ToastLevels.INFO,
                    false);
        } catch (Exception e) {
            LOG.error("██ Exception PersistSongPath failed: {}", e.getMessage(), e);
            toaster.addToast(
                    MessageFormat.format(
                            I18n.INSTANCE.getValue("toast.error.optionsviewmodel.ontaskerror"),
                            name),
                    Objects.toString(absolutePath + "\n" + e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }

    /**
     * Toggles the audio mute state, persists the new value in {@link OptionsDto}, updates the
     * {@link AudioService}, and reflects the change in {@code muteProperty}.
     *
     * <p>On success, a user-friendly toast indicates the new mute state. On failure, the exception
     * is logged and an error toast with details is displayed.
     */
    public void toggleMuteAndPersist() {
        boolean newState = !muteProperty.get();
        OptionsDto currentOptions = playerStateHolder.getCurrentPlayer().optionsidDto();
        OptionsDto toSaveOptions = currentOptions.withMuted(newState);
        try {
            optionsService.updateOptions(toSaveOptions);
            playerStateHolder.refreshCurrentPlayer();
            audioService.setMuted(newState);
            muteProperty.set(newState);
            toaster.addToast(
                    I18n.INSTANCE.getValue(
                            newState
                                    ? "toast.msg.optionsviewmodel.sound.off"
                                    : "toast.msg.optionsviewmodel.sound.on"),
                    "",
                    ToastLevels.INFO,
                    false);
        } catch (Exception e) {
            LOG.error("██ Exception ToggleMuteAndPersist failed: {}", e.getMessage(), e);
            toaster.addToast(
                    I18n.INSTANCE.getValue("toast.error.optionsviewmodel.sounderror"),
                    Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }

    /**
     * Toggles the grid opacity mode, persists the new state in the database, updates {@code
     * gridOpacityProperty}, and returns the actual updated value.
     *
     * <p>On success, an info toast indicates the new state. On failure, the exception is logged, an
     * error toast is shown, and the property is restored to its previous value.
     *
     * @return {@code true} if opaque mode is now active, {@code false} if transparent
     */
    public boolean toggleGridOpacityAndPersist() {
        boolean previousState = gridOpacityProperty.get();
        boolean newState = !previousState;
        OptionsDto currentOptions = playerStateHolder.getCurrentPlayer().optionsidDto();
        OptionsDto toSaveOptions = currentOptions.withOpaque(newState);
        try {
            optionsService.updateOptions(toSaveOptions);
            playerStateHolder.refreshCurrentPlayer();
            gridOpacityProperty.set(newState);
            toaster.addToast(
                    I18n.INSTANCE.getValue(
                            newState
                                    ? "toast.msg.optionsviewmodel.opaque.on"
                                    : "toast.msg.optionsviewmodel.opaque.off"),
                    "",
                    ToastLevels.INFO,
                    false);
            return newState;
        } catch (Exception e) {
            LOG.error("██ Exception ToggleGridOpacityAndPersist failed: {}", e.getMessage(), e);
            gridOpacityProperty.set(previousState);
            toaster.addToast(
                    I18n.INSTANCE.getValue("toast.error.optionsviewmodel.opacityerror"),
                    Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
            return previousState;
        }
    }

    /**
     * Sets the background color of the GridPane and updates the ColorPicker based on the color
     * value retrieved from the model.
     *
     * @param sudokuFX the GridPane to update (must not be null)
     * @param menuOptionsButtonColor the ColorPicker to update (must not be null)
     * @param colorValueFromModel the hex color string from the model (must not be null or blank,
     *     e.g., "99b3ffcd")
     * @throws NullPointerException if any parameter is null
     * @throws IllegalArgumentException if {@code colorValueFromModel} is blank
     * @throws NumberFormatException if {@code colorValueFromModel} is not a valid hex number or too
     *     large for an int
     */
    private void setColorFromModel(
            GridPane sudokuFX, ColorPicker menuOptionsButtonColor, String colorValueFromModel) {
        Objects.requireNonNull(sudokuFX, SUDOKU_FX_MUST_NOT_BE_NULL);
        Objects.requireNonNull(menuOptionsButtonColor, "menuOptionsButtonColor must not be null");
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                colorValueFromModel, "colorValueFromModel must not be null or blank");
        int colorInt = Integer.parseUnsignedInt(colorValueFromModel, HEX_RADIX);
        Color color = imageUtils.intToColor(colorInt);
        menuOptionsButtonColor.setValue(color);
        sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    /**
     * Applies the given color as the background of the specified {@link GridPane}, persists the new
     * color (including alpha) in the current player's options, and updates the UI.
     *
     * <p>A copy of the current {@link OptionsDto} is created with the new color in hex format
     * including alpha (RRGGBBAA) and an empty image path, then persisted via {@link
     * OptionsService#updateOptions(OptionsDto)}. On success, the GridPane background is updated and
     * a success toast is shown. On failure, the exception is logged and an error toast is
     * displayed.
     *
     * <p>The color is converted from a {@link Color} to a hex string suitable for persistence in
     * the DTO.
     *
     * @param sudokuFX the {@link GridPane} to update (must not be null)
     * @param color the color to apply and persist (must not be null)
     * @throws NullPointerException if {@code sudokuFX} or {@code color} is null
     */
    public void applyAndPersistOptionsColor(GridPane sudokuFX, Color color) {
        Objects.requireNonNull(sudokuFX, SUDOKU_FX_MUST_NOT_BE_NULL);
        Objects.requireNonNull(color, "color must not be null");
        String hexColor = color.toString().substring(HEXCOLOR_BEGIN_INDEX, HEXCOLOR_END_INDEX);
        OptionsDto currentOptions = playerStateHolder.getCurrentPlayer().optionsidDto();
        OptionsDto toSaveOptions = currentOptions.withImagepath("").withHexcolor(hexColor);
        try {
            optionsService.updateOptions(toSaveOptions);
            playerStateHolder.refreshCurrentPlayer();
            sudokuFX.setBackground(new Background(new BackgroundFill(color, null, null)));
        } catch (Exception e) {
            LOG.error("██ Exception ApplyAndPersistOptionsColor failed: {}", e.getMessage(), e);
            toaster.addToast(
                    I18n.INSTANCE.getValue("toast.error.optionsviewmodel.colorerror"),
                    Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }

    /**
     * Applies a background image to the specified {@link GridPane} and persists its path in the
     * current player's options.
     *
     * <p>If a valid image file is provided, it is loaded, resized, and converted asynchronously via
     * {@link AsyncFileProcessorService}, applied to the GridPane, and persisted. An info toast is
     * shown on success.
     *
     * <p>If the file is null, invalid, or missing, no background is applied, an empty path is
     * persisted, and an error toast is displayed.
     *
     * @param selectedFile the image file to load; may be null
     * @param spinner the spinner to indicate loading; must not be null
     * @param sudokuFX the {@link GridPane} to update; must not be null
     * @throws NullPointerException if {@code spinner} or {@code sudokuFX} is null
     */
    public void applyAndPersistBackgroundImage(
            File selectedFile, SpinnerGridPane spinner, GridPane sudokuFX) {
        Objects.requireNonNull(sudokuFX, SUDOKU_FX_MUST_NOT_BE_NULL);
        Objects.requireNonNull(spinner, "spinner must not be null");
        if (selectedFile != null
                && imageUtils.isValidImage(selectedFile)
                && selectedFile.exists()) {
            asyncFileProcessorService.processFileAsync(
                    selectedFile,
                    spinner,
                    toaster,
                    file -> {
                        ImageMeta meta = imageUtils.getImageMeta(file);
                        Image resizedImage =
                                new Image(
                                        file.toURI().toString(),
                                        meta.width() * meta.scaleFactor(),
                                        meta.height() * meta.scaleFactor(),
                                        true,
                                        true);
                        return imageUtils.createBackgroundImage(resizedImage);
                    },
                    backgroundImage -> {
                        sudokuFX.setBackground(new Background(backgroundImage));
                        persistImagePath(selectedFile.getAbsolutePath());
                    });
            return;
        }
        persistImagePath("");
        String errorMessage =
                I18n.INSTANCE.getValue("toast.error.optionsviewmodel.handlefileimagechooser");
        LOG.error("██ Exception handleFileImageChooser : {}", errorMessage);
        toaster.addToast(errorMessage, "", ToastLevels.ERROR, true);
    }

    /**
     * Persists the given image path in the current player's {@link OptionsDto} and refreshes the
     * player.
     *
     * <p>If persisting the path succeeds, the current player is refreshed. If an exception occurs,
     * the error is logged and an error toast is displayed to the user.
     *
     * @param imagePath the image path to persist; may be empty to indicate no background image
     */
    private void persistImagePath(@Nonnull String imagePath) {
        Objects.requireNonNull(imagePath, "imagePath must not be null");
        OptionsDto currentOptions = playerStateHolder.getCurrentPlayer().optionsidDto();
        OptionsDto toSaveOptions = currentOptions.withImagepath(imagePath);
        try {
            optionsService.updateOptions(toSaveOptions);
            playerStateHolder.refreshCurrentPlayer();
        } catch (Exception e) {
            LOG.error("PersistImagePath failed: {}", e.getMessage(), e);
            String i18nKey =
                    imagePath.isEmpty()
                            ? "toast.error.optionsviewmodel.imagepathclearerror"
                            : "toast.error.optionsviewmodel.imagepathsavererror";
            toaster.addToast(
                    I18n.INSTANCE.getValue(i18nKey),
                    Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }
}
