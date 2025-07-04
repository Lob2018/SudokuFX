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
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.Paths;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.DynamicFontSize;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;

/**
 * Coordinator is a Spring-managed component handling navigation and UI logic in a JavaFX
 * application following the MVVM-C pattern.
 *
 * <p>Responsibilities include loading FXML views, switching scenes, dynamic font scaling, language
 * toggling, and integration with system services.
 */
@Component
public class Coordinator {

    private static final Logger LOG = LoggerFactory.getLogger(Coordinator.class);

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
        String path = Paths.RESOURCES_FXML_PATH.getPath() + fxml + ".fxml";
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
     * Switches the application language between French and English.
     *
     * <p>Note: ViewModels must be notified manually to refresh bound properties.
     */
    public void toggleLanguage() {
        I18n.INSTANCE.setLocaleBundle(I18n.INSTANCE.getLanguage().equals("fr") ? "EN" : "FR");
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
}
