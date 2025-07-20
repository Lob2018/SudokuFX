/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

/**
 * Holds the initialized paths for data and logs folders.
 *
 * @param dataFolderPath the path to the data folder
 * @param logsFolderPath the path to the logs folder
 */
public record OsInitializedFolders(String dataFolderPath, String logsFolderPath) {}
