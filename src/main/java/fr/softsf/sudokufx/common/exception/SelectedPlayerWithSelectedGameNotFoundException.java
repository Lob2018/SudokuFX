/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

/** Exception thrown when no selected Player with a selected Game is found. */
public class SelectedPlayerWithSelectedGameNotFoundException extends RuntimeException {
    public SelectedPlayerWithSelectedGameNotFoundException(String message) {
        super(message);
    }
}
