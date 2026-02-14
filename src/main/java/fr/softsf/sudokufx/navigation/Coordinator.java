/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.navigation;

import java.io.File;
import java.util.Objects;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.AppPaths;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.DynamicFontSize;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.service.business.PlayerLanguageService;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;
import jakarta.annotation.PostConstruct;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;
import static fr.softsf.sudokufx.common.enums.Urls.MY_WEBSITE_URL;

/**
 * Coordinator is a Spring-managed component handling navigation and UI logic in a JavaFX
 * application (MVVM-C pattern).
 *
 * <p>Key responsibilities:
 *
 * <ul>
 *   <li>Load FXML views and set them as the scene root with Spring-managed controllers.
 *   <li>Switch scenes and manage dynamic font resizing.
 *   <li>Manage player's language and provide host system integrations (URLs, local files).
 * </ul>
 */
@Component
public class Coordinator {

    @Autowired private ApplicationContext applicationContext;

    @Autowired private PlayerService playerService;
    @Autowired private PlayerLanguageService playerLanguageService;
    @Autowired private PlayerStateHolder playerStateHolder;
    @Autowired private ToasterService toasterService;

    private static final Logger LOG = LoggerFactory.getLogger(Coordinator.class);

    private Scene defaultScene;

    /** Shared FXMLLoader for FXML view loading and controller injection. */
    private final FXMLLoader fxmlLoader;

    /** Utility for responsive font resizing based on scene dimensions. */
    private DynamicFontSize dynamicFontSize;

    /** Provides access to system-level features like opening URLs in a browser. */
    private HostServices hostServices;

    /**
     * Constructs the coordinator.
     *
     * @param fxmlLoader shared loader
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "FXMLLoader must be stored by reference; defensive copies are impossible and"
                            + " break JavaFX/Spring controller injection.")
    public Coordinator(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Validates the component state after injection.
     *
     * @throws IllegalArgumentException if the FXML loader is null
     */
    @PostConstruct
    public void validateConfiguration() {
        if (Objects.isNull(fxmlLoader)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "FxmlLoader must not be null");
        }
    }

    /**
     * Returns the JavaFX default scene managed by this coordinator.
     *
     * @return the current default scene
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX Scene must be returned by reference; no defensive copy is possible.")
    public Scene getDefaultScene() {
        return defaultScene;
    }

    /**
     * Sets the default JavaFX scene.
     *
     * @param scene non-null scene
     * @throws IllegalArgumentException if null
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "JavaFX Scene must be stored by reference; defensive copies are impossible and"
                            + " break UI behavior.")
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
     * Loads the specified FXML file, sets it as the root of the current JavaFX scene, and returns
     * its controller.
     *
     * <p>This method uses {@link FXMLLoader} together with Spring's {@link ApplicationContext} to
     * instantiate the controller and inject Spring-managed beans when the context is available.
     *
     * <p><b>Initialization requirements:</b> {@link #setDefaultScene(Scene)} must have been called
     * before invoking this method. If the default scene or the shared {@link FXMLLoader} is not
     * initialized, an {@link IllegalStateException} is thrown immediately rather than catching a
     * {@link NullPointerException}.
     *
     * <p>If the Spring application context is not yet initialized, the controller is instantiated
     * by {@link FXMLLoader} alone, without Spring injection.
     *
     * <p>If {@link #dynamicFontSize} is set, font sizes are updated after loading.
     *
     * <p>Any exception during loading (e.g., FXML not found, controller instantiation failure) is
     * logged, and the application exits via {@link Platform#exit()}.
     *
     * @param <T> the controller type
     * @param fxml the FXML filename; must not be null or blank
     * @return the controller instance, or {@code null} if loading fails
     * @throws IllegalArgumentException if {@code fxml} is null or blank
     * @throws IllegalStateException if required components (scene or loader) are not initialized
     */
    public <T> T setRootByFXMLName(final String fxml) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                fxml, "Fxml must not be null or blank, but was " + fxml);
        if (defaultScene == null) {
            throw new IllegalStateException(
                    "defaultScene must be set before calling setRootByFXMLName()");
        }
        if (fxmlLoader == null) {
            throw new IllegalStateException(
                    "fxmlLoader must not be null before calling setRootByFXMLName()");
        }
        String path = AppPaths.RESOURCES_FXML_PATH.getPath() + fxml;
        try {
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(Coordinator.class.getResource(path));
            if (applicationContext != null) {
                fxmlLoader.setControllerFactory(clazz -> applicationContext.getBean(clazz));
            }
            defaultScene.setRoot(fxmlLoader.load());
            if (dynamicFontSize != null) {
                dynamicFontSize.updateFontSize();
            }
            return fxmlLoader.getController();
        } catch (Exception e) {
            LOG.error(
                    "██ Exception caught when setting root by FXML name: {} █ The FXML path was"
                            + " (triggering Platform.exit()): {}",
                    e.getMessage(),
                    path,
                    e);
            exitPlatform();
            return null;
        }
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
     * a toast notification via the {@link ToasterService}.
     *
     * <p>This method no longer requires an explicit {@link ToasterVBox} parameter since error
     * handling is delegated to the injected {@link ToasterService}.
     *
     * @see ToasterService#showError(String, String)
     */
    public void toggleLanguage() {
        String iso = I18n.INSTANCE.getLanguage().equals("fr") ? "EN" : "FR";
        try {
            updatePlayerLanguage(iso);
            I18n.INSTANCE.setLocaleBundle(iso);
        } catch (Exception e) {
            LOG.error("██ Exception ToggleLanguage failed: {}", e.getMessage(), e);
            toasterService.showError(
                    I18n.INSTANCE.getValue("toast.error.coordinator.toggleLanguage"),
                    Objects.toString(e.getMessage(), ""));
        }
    }

    /**
     * Updates the current player's language and refreshes the player state.
     *
     * <p>Called internally by {@link #toggleLanguage()}. May throw exceptions if the language does
     * not exist or the player update fails.
     *
     * @param iso the ISO code of the target language ("FR" or "EN")
     * @throws IllegalArgumentException if {@code iso} is null or blank
     * @throws RuntimeException if updating the player fails
     */
    private void updatePlayerLanguage(String iso) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                iso, "iso must not be null or blank, but was " + iso);
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

    /** Opens my website's page in the user's default web browser. */
    public void openMyWebsiteUrl() {
        if (hostServices != null) {
            hostServices.showDocument(MY_WEBSITE_URL.getUrl());
        } else {
            LOG.warn(
                    "▓▓ openMyWebsiteUrl hostServices not set yet: cannot open my website"
                            + " URL");
        }
    }

    /** Opens the given local file in the user's default web browser. */
    public void openLocalFile(File file) {
        if (hostServices != null) {
            if (file != null && file.exists()) {
                hostServices.showDocument(file.toURI().toString());
            } else {
                LOG.warn("▓▓ openLocalFile file is null or does not exist: cannot open");
            }
        } else {
            LOG.warn("▓▓ openLocalFile hostServices not set yet: cannot open local file");
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

    /** For testing only: injects a {@link ToasterService}. */
    void setToasterService(ToasterService toasterService) {
        this.toasterService = toasterService;
    }
}
