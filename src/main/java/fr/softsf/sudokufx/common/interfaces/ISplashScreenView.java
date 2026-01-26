/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.interfaces;

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
