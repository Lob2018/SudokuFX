/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.exception;

/**
 * Thrown to indicate that the database state is inconsistent with the application's requirements,
 * rendering the system unable to operate safely.
 */
public class DatabaseIntegrityException extends RuntimeException {
    /**
     * Constructs a new DatabaseIntegrityException with the specified detail message and cause.
     *
     * @param message the detail message explaining the integrity violation.
     * @param cause the underlying cause (e.g., a SQLException).
     */
    public DatabaseIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
