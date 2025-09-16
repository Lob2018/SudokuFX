/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.navigation;

import java.util.Objects;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.Paths;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.DynamicFontSize;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.service.business.PlayerLanguageService;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;
import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_SPONSOR_URL;

/**
 * Coordinator is a Spring-managed component handling navigation and UI logic in a JavaFX
 * application following the MVVM-C pattern.
 *
 * <p>Responsibilities include loading FXML views, switching scenes, dynamic font scaling, language
 * toggling, and integration with system services.
 */
@Component
public class Coordinator {

    @Autowired private PlayerService playerService;
    @Autowired private PlayerLanguageService playerLanguageService;
    @Autowired private PlayerStateHolder playerStateHolder;

    private static final Logger LOG = LoggerFactory.getLogger(Coordinator.class);
    public static final String FXML_EXTENSION = ".fxml";

    /** The main JavaFX scene managed by this coordinator. */
    private Scene defaultScene;

    /** Shared FXMLLoader for FXML view loading and controller injection. */
    private final FXMLLoader fxmlLoader;

    /** Utility for responsive font resizing based on scene dimensions. */
    private DynamicFontSize dynamicFontSize;

    /** Provides access to system-level features like opening URLs in a browser. */
    private HostServices hostServices;

    /**
     * Creates a Coordinator with the given non-null FXMLLoader.
     *
     * @param fxmlLoader shared FXML loader
     * @throws IllegalArgumentException if null
     */
    public Coordinator(FXMLLoader fxmlLoader) {
        if (Objects.isNull(fxmlLoader)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "FxmlLoader must not be null");
        }
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Returns the JavaFX default scene managed by this coordinator.
     *
     * @return the current default scene
     */
    public Scene getDefaultScene() {
        return defaultScene;
    }

    /**
     * Sets the default JavaFX scene.
     *
     * @param scene non-null scene
     * @throws IllegalArgumentException if null
     */
    public void setDefaultScene(Scene scene) {
        if (Objects.isNull(scene)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Scene must not be null");
        }
        this.defaultScene = scene;
    }

    /**
     * Sets the dynamic font resizing utility.
     *
     * @param dynamicFontSize non-null font size updater
     * @throws IllegalArgumentException if null
     */
    public void setDynamicFontSize(DynamicFontSize dynamicFontSize) {
        if (Objects.isNull(dynamicFontSize)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "DynamicFontSize must not be null");
        }
        this.dynamicFontSize = dynamicFontSize;
    }

    /**
     * Loads the specified FXML (without extension), sets it as the scene root, and returns its
     * controller.
     *
     * <p>Logs errors and calls {@link Platform#exit()} on failure.
     *
     * <p>If {@code dynamicFontSize} is set, updates font size after loading.
     *
     * @param <T> the controller type
     * @param fxml the FXML filename without extension; must not be null or blank
     * @return the controller instance, or null if loading fails
     * @throws IllegalArgumentException if {@code fxml} is null or blank
     */
    public <T> T setRootByFXMLName(final String fxml) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                fxml, "Fxml must not be null or blank, but was " + fxml);
        String path = Paths.RESOURCES_FXML_PATH.getPath() + fxml + FXML_EXTENSION;
        try {
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(getClass().getResource(path));
            defaultScene.setRoot(fxmlLoader.load());
            if (dynamicFontSize != null) {
                dynamicFontSize.updateFontSize();
            }
            return fxmlLoader.getController();
        } catch (NullPointerException npe) {
            LOG.error(
                    "██ Exception NullPointerException caught in setRootByFXMLName.Verify that"
                            + " setDefaultScene, setDynamicFontSize, and setHostServices have been"
                            + " called with non-null arguments (triggering Platform.exit(). {}",
                    npe.getMessage(),
                    npe);
        } catch (Exception e) {
            LOG.error(
                    "██ Exception caught when setting root by FXML name: {} █ The FXML path was"
                            + " (triggering Platform.exit()): {}",
                    e.getMessage(),
                    path,
                    e);
        }
        exitPlatform();
        return null;
    }

    /**
     * Returns the ISO code of the current player's language.
     *
     * <p>Use this value to initialize the application's locale at startup.
     *
     * @return the current player's language ISO code, e.g., "FR" or "EN"
     */
    public String getCurrentPlayerLanguageIso() {
        return playerStateHolder.getCurrentPlayer().playerlanguageidDto().iso();
    }

    /**
     * Toggles the application's language between French ("FR") and English ("EN").
     *
     * <p>Updates the current player's language, refreshes the player state, and switches the
     * translation bundle. Exceptions from updating the player are caught, logged, and displayed as
     * a toast via the provided {@link ToasterVBox}.
     *
     * @param toaster the {@link ToasterVBox} used to display error messages; must not be null
     * @throws NullPointerException if {@code toaster} is null
     */
    public void toggleLanguage(ToasterVBox toaster) {
        Objects.requireNonNull(toaster, "toaster must not be null");
        String iso = I18n.INSTANCE.getLanguage().equals("fr") ? "EN" : "FR";
        try {
            updatePlayerLanguage(iso);
            I18n.INSTANCE.setLocaleBundle(iso);
        } catch (Exception e) {
            LOG.error("██ Exception ToggleLanguage failed: {}", e.getMessage(), e);
            toaster.addToast(
                    I18n.INSTANCE.getValue("toast.error.coordinator.toggleLanguage"),
                    Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }

    /**
     * Updates the current player's language and refreshes the player state.
     *
     * <p>Called internally by {@link #toggleLanguage(ToasterVBox)}. May throw exceptions if the
     * language does not exist or the player update fails.
     *
     * @param iso the ISO code of the target language ("FR" or "EN")
     * @throws IllegalArgumentException if the language is not found
     * @throws RuntimeException if updating the player fails
     */
    private void updatePlayerLanguage(String iso) {
        PlayerLanguageDto toSaveLanguage = playerLanguageService.getByIso(iso);
        PlayerDto currentPlayer = playerStateHolder.getCurrentPlayer();
        PlayerDto toSavePlayer = currentPlayer.withPlayerLanguage(toSaveLanguage);
        playerService.updatePlayer(toSavePlayer);
        playerStateHolder.refreshCurrentPlayer();
    }

    /** Opens the GitHub repository releases page in the user's default web browser. */
    public void openGitHubRepositoryReleaseUrl() {
        if (hostServices != null) {
            hostServices.showDocument(GITHUB_REPOSITORY_RELEASES_URL.getUrl());
        } else {
            LOG.warn(
                    "▓▓ openGitHubRepositoryReleaseUrl hostServices not set yet: cannot open GitHub"
                            + " releases URL");
        }
    }

    /** Opens the GitHub Sponsor page in the user's default web browser. */
    public void openGitHubSponsorUrl() {
        if (hostServices != null) {
            hostServices.showDocument(GITHUB_SPONSOR_URL.getUrl());
        } else {
            LOG.warn(
                    "▓▓ openGitHubSponsorUrl hostServices not set yet: cannot open GitHub sponsor"
                            + " URL");
        }
    }

    /**
     * Injects the non-null {@link HostServices} instance provided by the JavaFX application.
     *
     * @param hostServices the non-null {@code HostServices} instance
     * @throws IllegalArgumentException if {@code hostServices} is {@code null}
     */
    public void setHostServices(HostServices hostServices) {
        if (Objects.isNull(hostServices)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "HostServices must not be null");
        }
        this.hostServices = hostServices;
    }

    /**
     * Package-private method to exit the JavaFX platform.
     *
     * <p>Overridable in tests to avoid actual exit and allow exception handling verification.
     */
    void exitPlatform() {
        Platform.exit();
    }

    /** For testing only: injects a {@link PlayerService}. */
    void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    /** For testing only: injects a {@link PlayerLanguageService}. */
    void setPlayerLanguageService(PlayerLanguageService service) {
        this.playerLanguageService = service;
    }

    /** For testing only: injects a {@link PlayerStateHolder}. */
    void setPlayerStateHolder(PlayerStateHolder holder) {
        this.playerStateHolder = holder;
    }
}
