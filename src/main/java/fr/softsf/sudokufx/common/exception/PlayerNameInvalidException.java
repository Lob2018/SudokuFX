/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

/** Exception thrown when a player's name is null, empty, or blank. */
public class PlayerNameInvalidException extends IllegalArgumentException {
    public PlayerNameInvalidException(String message) {
        super(message);
    }
}
