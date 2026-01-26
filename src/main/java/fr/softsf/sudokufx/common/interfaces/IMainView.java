/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
