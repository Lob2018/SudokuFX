/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces;

/** Interface defining method to open the main stage. */
public interface IMainView {

    /**
     * Opens the main stage
     *
     * @param iSplashScreenView The splash screen view interface in order to hide it
     */
    void openingMainStage(ISplashScreenView iSplashScreenView);
}
