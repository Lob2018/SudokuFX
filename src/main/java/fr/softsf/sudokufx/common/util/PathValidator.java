/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Centralized validator for file system paths.
 *
 * <p>Provides fail-fast validation methods using {@link ExceptionTools} to ensure consistency
 * across the application.
 */
public enum PathValidator {
    INSTANCE;

    /**
     * Validates that a path is not null and exists in the file system. * @param path the path to
     * validate; must not be null
     *
     * @return the validated {@link File} object
     * @throws IllegalArgumentException if the path is null
     * @throws IllegalStateException if the path does not exist
     */
    public File validateExists(Path path) {
        if (Objects.isNull(path)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The path mustn't be null");
        }
        File file = path.toFile();
        if (!file.exists()) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalState(
                    "Path does not exist: " + path);
        }
        return file;
    }

    /**
     * Validates that a path exists and points to a directory. * @param path the directory path to
     * validate; must not be null
     *
     * @return the validated {@link File} object representing a directory
     * @throws IllegalStateException if the path is not a directory
     */
    public File validateDirectory(Path path) {
        File file = validateExists(path);
        if (!file.isDirectory()) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalState(
                    "Path is not a directory: " + path);
        }
        return file;
    }
}
