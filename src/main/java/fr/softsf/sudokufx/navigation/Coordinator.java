/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.navigation;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.utils.DynamicFontSize;

/**
 * Coordinator is a Spring-managed component responsible for controlling navigation and dynamic UI
 * updates in a JavaFX application using the MVVM-C architecture.
 *
 * <p>It handles the loading and switching between different FXML views at runtime, and optionally
 * applies dynamic font resizing based on the dimensions of the scene through the DynamicFontSize
 * utility.
 */
@Component
public class Coordinator {

    private static final Logger log = LoggerFactory.getLogger(Coordinator.class);

    /** The default JavaFX Scene managed by the Coordinator. */
    private Scene defaultScene;

    /** Shared FXMLLoader used to load FXML files and inject controllers. */
    private final FXMLLoader fxmlLoader;

    /** Utility for dynamically resizing font sizes based on the scene dimensions. */
    private DynamicFontSize dynamicFontSize;

    /**
     * Constructs the Coordinator with an injected FXMLLoader instance. The Scene is expected to be
     * set from the current JavaFX application context.
     *
     * @param fxmlLoader the FXMLLoader used to load and inject FXML files and their controllers
     */
    public Coordinator(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Returns the current JavaFX default Scene managed by the Coordinator.
     *
     * <p>The Scene is the main container for the UI elements and serves as the root of the
     * application. It is used to update and display different views by setting the root node using
     * FXML files.
     *
     * @return the current JavaFX default Scene
     */
    public Scene getDefaultScene() {
        return defaultScene;
    }

    /**
     * Sets the JavaFX default Scene managed by the Coordinator.
     *
     * <p>This method allows the Coordinator to manage the main scene of the application. The scene
     * is the container for all the UI elements, and this method ensures it is properly initialized
     * and ready for loading views.
     *
     * @param scene the Scene to set as the default Scene
     */
    public void setDefaultScene(Scene scene) {
        this.defaultScene = scene;
    }

    /**
     * Sets the dynamic font size handler for the scene. This method should be called before {@link
     * #setRootByFXMLName(String)} if dynamic font resizing is required.
     *
     * <p>The font size handler adjusts the font size of UI elements based on the current size of
     * the scene. This allows for a more responsive and adaptable user interface by scaling the font
     * size as the scene size changes.
     *
     * @param dynamicFontSize The {@link DynamicFontSize} object that manages the dynamic resizing
     *     of font sizes.
     */
    public void setDynamicFontSize(DynamicFontSize dynamicFontSize) {
        this.dynamicFontSize = dynamicFontSize;
    }

    /**
     * Loads an FXML file by its name and sets it as the root of the scene. Also triggers dynamic
     * font size adjustment if the dynamic font size handler is initialized.
     *
     * @param fxml the base name of the FXML file to load (excluding the .fxml extension)
     * @throws IllegalArgumentException if the FXML file cannot be found at the specified location
     * @throws RuntimeException if loading the FXML fails, the application exits
     */
    public void setRootByFXMLName(final String fxml) {
        String path = Paths.RESOURCES_FXML_PATH.getPath() + fxml + ".fxml";
        try {
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(getClass().getResource(path));
            defaultScene.setRoot(fxmlLoader.load());
            if (dynamicFontSize != null) {
                dynamicFontSize.updateFontSize();
            }
        } catch (Exception e) {
            log.error(
                    "██ Exception caught when setting root by FXML name: {} █ The FXML path was:"
                            + " {}",
                    e.getMessage(),
                    path,
                    e);
            Platform.exit();
        }
    }

    /**
     * Returns the controller associated with the currently loaded FXML.
     *
     * <p>The controller manages the interactions between the UI and the underlying logic of the
     * view. This method allows access to the controller once the FXML file has been loaded.
     *
     * @param <T> the expected type of the controller
     * @return the controller instance
     * @throws IllegalStateException if no FXML file has been loaded yet, or if the controller
     *     cannot be found
     */
    public <T> T getController() {
        return fxmlLoader.getController();
    }
}
