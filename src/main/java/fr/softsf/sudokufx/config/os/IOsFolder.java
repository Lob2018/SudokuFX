/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.os;

/** Interface defining methods to access OS-specific folder paths. */
public sealed interface IOsFolder permits OsInitializedFolders {

    /**
     * Retrieves the path to the OS-specific data folder.
     *
     * @return A String representing the path to the data folder
     */
    String getOsDataFolderPath();

    /**
     * Retrieves the path to the OS-specific logs folder.
     *
     * @return A String representing the path to the logs folder
     */
    String getOsLogsFolderPath();
}
