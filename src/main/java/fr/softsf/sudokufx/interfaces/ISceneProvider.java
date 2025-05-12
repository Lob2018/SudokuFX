/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces;

import javafx.scene.Scene;

import fr.softsf.sudokufx.SudoMain;

/**
 * Interface ISceneProvider.
 *
 * <p>Provides standardized access to the main scene of a JavaFX application. Classes implementing
 * this interface can use the default method to obtain the scene without directly depending on the
 * main application class.
 */
public interface ISceneProvider {

    /**
     * Retrieves the current scene of the application.
     *
     * @return The active scene, obtained via SudoMain.
     */
    default Scene getScene() {
        return SudoMain.getScene();
    }
}
