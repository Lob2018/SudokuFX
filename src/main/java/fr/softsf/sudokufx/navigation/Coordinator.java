/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.navigation;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.utils.DynamicFontSize;

/**
 * Coordinator is a Spring-managed component responsible for navigation and UI updates in a JavaFX
 * application following the MVVM-C architecture.
 *
 * <p>It handles FXML view loading, dynamic font resizing, and language switching.
 */
@Component
public class Coordinator {

    private static final Logger log = LoggerFactory.getLogger(Coordinator.class);

    /** The default JavaFX Scene managed by this coordinator. */
    private Scene defaultScene;

    /** Shared FXMLLoader for loading FXML files and injecting controllers. */
    private final FXMLLoader fxmlLoader;

    /** Utility for adjusting font sizes dynamically based on scene dimensions. */
    private DynamicFontSize dynamicFontSize;

    /**
     * Constructs the Coordinator with a shared {@code FXMLLoader}.
     *
     * @param fxmlLoader the loader used to load FXML files and their controllers
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
                    "██ Exception caught when setting root by FXML name: {} █ The FXML path was:"
                            + " {}",
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
}
