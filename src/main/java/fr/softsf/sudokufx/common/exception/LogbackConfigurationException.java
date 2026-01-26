/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.exception;

/**
 * Runtime exception thrown when Logback configuration fails.
 *
 * <p>Used to wrap errors occurring during initialization or application of a Logback configuration
 * file.
 */
public class LogbackConfigurationException extends RuntimeException {

    /**
     * Constructs a new LogbackConfigurationException with the specified detail message and cause.
     *
     * @param message descriptive message of the failure; must not be null
     * @param cause the underlying cause of the exception; may be null
     */
    public LogbackConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
