/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
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
