/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.os;

/**
 * * Defines the contract for accessing operating system specific storage paths. *
 *
 * <p>Implementations must provide standardized paths for application data and logging based on the
 * host environment.
 */
public sealed interface IOsFolder permits OsInitializedFolders {

    /**
     * Returns the absolute path to the application's data directory.
     *
     * @return The data folder path as a String.
     */
    String getOsDataFolderPath();

    /**
     * Returns the absolute path to the application's logs directory.
     *
     * @return The logs folder path as a String.
     */
    String getOsLogsFolderPath();
}
