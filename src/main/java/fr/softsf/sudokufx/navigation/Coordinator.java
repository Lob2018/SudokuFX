/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.navigation;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.interfaces.ISceneProvider;
import fr.softsf.sudokufx.utils.DynamicFontSize;

/**
 * Coordinator is a Spring-managed component responsible for controlling navigation and dynamic UI
 * updates in a JavaFX application using the MVVM-C architecture.
 *
 * <p>It handles loading and switching between different FXML views at runtime, and optionally
 * applies dynamic font resizing through the DynamicFontSize utility.
 */
@Component
public class Coordinator implements ISceneProvider {

    private static final Logger log = LoggerFactory.getLogger(Coordinator.class);

    private final Scene scene = getScene();
    private final FXMLLoader fxmlLoader;
    private DynamicFontSize dynamicFontSize;

    /**
     * Constructs the Coordinator with an injected FXMLLoader instance. The Scene is retrieved from
     * the current JavaFX application context.
     *
     * @param fxmlLoader the FXMLLoader used to load FXML files
     */
    public Coordinator(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Initializes the dynamic font size handler for the scene. Should be called before using {@link
     * #setRootByFXMLName(String)} if dynamic resizing is needed.
     */
    public void setDynamicFontSize() {
        this.dynamicFontSize = new DynamicFontSize();
    }

    /**
     * Loads an FXML file by its name and sets it as the root of the scene. Also triggers dynamic
     * font size adjustment if initialized.
     *
     * @param fxml the base name of the FXML file to load (excluding the .fxml extension)
     * @throws IllegalArgumentException if the FXML file is not found
     * @throws RuntimeException exits the application if loading fails
     */
    public void setRootByFXMLName(final String fxml) {
        String path = Paths.RESOURCES_FXML_PATH.getPath() + fxml + ".fxml";
        try {
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation(getClass().getResource(path));
            scene.setRoot(fxmlLoader.load());
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
     * @param <T> the expected type of the controller
     * @return the controller instance
     * @throws IllegalStateException if no FXML file has been loaded yet
     */
    public <T> T getController() {
        return fxmlLoader.getController();
    }
}
