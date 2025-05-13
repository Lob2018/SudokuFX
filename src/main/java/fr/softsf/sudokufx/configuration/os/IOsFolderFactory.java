/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.configuration.os;

/** Interface defining methods to access OS-specific folder paths. */
public sealed interface IOsFolderFactory
        permits LinuxFolderFactory, MacosFolderFactory, WindowsFolderFactory {
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
