/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.io.File;
import java.util.Objects;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Utility class providing helper methods for common audio-related operations.
 *
 * <p>Includes support for:
 *
 * <ul>
 *   <li>Validating audio file formats
 * </ul>
 *
 * <p>All methods throw {@link IllegalArgumentException} when passed {@code null} arguments.
 *
 * <p>This class is intended to be used statically.
 */
public class AudioUtils {

    /**
     * Determines if the given file has a supported audio extension.
     *
     * <p>Accepted audio formats: MP3, WAV, AAC, M4A, AIF, AIFF (case-insensitive).
     *
     * @param file the file to check; must not be {@code null}
     * @return {@code true} if the file has a valid audio extension; {@code false} otherwise
     * @throws IllegalArgumentException if {@code file} is {@code null}
     */
    public boolean isValidAudio(File file) {
        if (Objects.isNull(file)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The audio file mustn't be null");
        }
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".mp3")
                || fileName.endsWith(".wav")
                || fileName.endsWith(".aac")
                || fileName.endsWith(".m4a")
                || fileName.endsWith(".aif")
                || fileName.endsWith(".aiff");
    }
}
