/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Path;

/** Defines file system operations related to the application's data management. */
public sealed interface IFileSystem permits FileSystemManager {

    /**
     * Recursively deletes the application's data folder and all its contents.
     *
     * <p>Validates the given path before deletion. Returns {@code true} if deletion succeeds;
     * {@code false} otherwise.
     *
     * @param folderPath the path to the application's data folder; must not be {@code null}
     * @return {@code true} if the folder and its contents were successfully deleted; {@code false}
     *     otherwise
     * @throws IllegalArgumentException if {@code folderPath} is {@code null}
     */
    boolean deleteDataFolderRecursively(Path folderPath);
}
