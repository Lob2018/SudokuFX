/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces;

import javafx.scene.Scene;

/** Provides basic control over the splash screen visibility and access to its scene. */
public interface ISplashScreenView {

    /** Displays the splash screen. */
    void showSplashScreen();

    /** Hides and closes the splash screen. */
    void hideSplashScreen();

    /** Returns the JavaFX scene used by the splash screen. */
    Scene getSplashScreenScene();
}
