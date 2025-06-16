/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces;

/** Interface defining method to open the main stage. */
public interface IMainView {

    /**
     * Opens the main stage
     *
     * @param iSplashScreenView The splash screen view interface in order to hide it
     */
    void openingMainStage(ISplashScreenView iSplashScreenView);
}
