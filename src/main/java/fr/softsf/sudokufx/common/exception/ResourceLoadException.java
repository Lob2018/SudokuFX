/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.exception;

/**
 * Checked exception thrown when a resource fails to load.
 *
 * <p>Can be used for audio, image, or any other resource type that cannot be loaded or processed.
 */
public class ResourceLoadException extends Exception {

    /**
     * Constructs a new ResourceLoadException with the specified detail message.
     *
     * @param message descriptive message of the failure; must not be null
     */
    public ResourceLoadException(String message) {
        super(message);
    }

    /**
     * Constructs a new ResourceLoadException with the specified detail message and cause.
     *
     * @param message descriptive message of the failure; must not be null
     * @param cause the underlying cause of the exception; may be null
     */
    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
