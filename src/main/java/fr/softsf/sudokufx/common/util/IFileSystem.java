/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Path;

/**
 * Interface defining a method to recursively deletes a folder and its contents. The folder path
 * must end with the specified String.
 */
public sealed interface IFileSystem permits FileSystemManager {
    /**
     * Recursively deletes a folder and its contents if the folder path ends with the specified
     * string.
     *
     * @param folderPath The path of the folder to be deleted.
     * @param mustEndWithThat The string that the folder path must end with for deletion to proceed.
     * @return true if the folder was successfully deleted, false otherwise.
     */
    boolean deleteFolderRecursively(Path folderPath, String mustEndWithThat);
}
