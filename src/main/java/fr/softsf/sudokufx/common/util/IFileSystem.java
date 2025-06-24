/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Path;

/** Defines file system operations. */
public sealed interface IFileSystem permits FileSystemManager {

    /**
     * Recursively deletes the application's data folder and all its contents.
     *
     * @param folderPath the path to the application's data folder
     * @return {@code true} if the folder was successfully deleted; {@code false} otherwise
     */
    boolean deleteDataFolderRecursively(Path folderPath);
}
