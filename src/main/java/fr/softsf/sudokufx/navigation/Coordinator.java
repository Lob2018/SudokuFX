/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.navigation;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.utils.DynamicFontSize;

import static fr.softsf.sudokufx.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;

/**
 * Coordinator is a Spring-managed component that handles navigation and UI logic in a JavaFX
 * application following the MVVM-C architecture.
 *
 * <p>Responsibilities include FXML view loading, scene switching, dynamic font scaling, and
 * language toggling.
 */
@Component
public class Coordinator {

    private static final Logger log = LoggerFactory.getLogger(Coordinator.class);

    /** The main JavaFX scene managed by this coordinator. */
    private Scene defaultScene;

    /** Shared FXMLLoader for FXML view loading and controller injection. */
    private final FXMLLoader fxmlLoader;

    /** Utility for responsive font resizing based on scene dimensions. */
    private DynamicFontSize dynamicFontSize;

    /** Provides access to system-level features like opening URLs in a browser. */
    private HostServices hostServices;

    /**
     * Constructs the Coordinator with a shared {@code FXMLLoader}.
     *
     * @param fxmlLoader shared loader for FXML views and controllers
     */
    public Coordinator(FXMLLoader fxmlLoader) {
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
     * Sets the JavaFX default scene.
     *
     * @param scene the scene to set as default
     */
    public void setDefaultScene(Scene scene) {
        this.defaultScene = scene;
    }

    /**
     * Sets the dynamic font resizing utility.
     *
     * @param dynamicFontSize the utility used to update font sizes
     */
    public void setDynamicFontSize(DynamicFontSize dynamicFontSize) {
        this.dynamicFontSize = dynamicFontSize;
    }

    /**
     * Loads the given FXML view, sets it as the root of the default scene, and returns its
     * controller.
     *
     * <p>Resets the {@code FXMLLoader}, loads the view from resources, updates the scene root, and
     * adjusts the font size if applicable.
     *
     * <p>On failure, logs the error, exits the app, and returns {@code null}.
     *
     * @param <T> the controller type
     * @param fxml the FXML base name (without extension)
     * @return the controller instance, or {@code null} if loading fails
     */
    public <T> T setRootByFXMLName(final String fxml) {
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
        } catch (Exception e) {
            log.error(
                    "██ Exception caught when setting root by FXML name: {} █ The FXML path was"
                            + " (triggering Platform.exit()): {}",
                    e.getMessage(),
                    path,
                    e);
        }
        Platform.exit();
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
        }
    }

    /** Injects the HostServices instance from the main application. */
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
