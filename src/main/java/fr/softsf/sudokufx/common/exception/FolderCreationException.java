/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

/**
 * Runtime exception thrown when a folder cannot be created.
 *
 * <p>Can be used for any scenario where a folder creation fails due to IO, security, or other
 * reasons.
 */
public class FolderCreationException extends RuntimeException {

    /**
     * Constructs a new FolderCreationException with the specified detail message.
     *
     * @param message descriptive message of the failure; must not be null
     */
    public FolderCreationException(String message) {
        super(message);
    }

    /**
     * Constructs a new FolderCreationException with the specified detail message and cause.
     *
     * @param message descriptive message of the failure; must not be null
     * @param cause the underlying cause of the exception; may be null
     */
    public FolderCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
