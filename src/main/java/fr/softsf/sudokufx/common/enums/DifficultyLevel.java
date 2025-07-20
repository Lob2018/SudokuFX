/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/** Represents the difficulty levels of a Sudoku grid */
public enum DifficultyLevel {
    EASY,
    MEDIUM,
    DIFFICULT;

    /**
     * Converts the difficulty level to its corresponding numeric level.
     *
     * @return an integer representing the numeric level for this difficulty
     */
    public int toGridNumber() {
        return switch (this) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case DIFFICULT -> 3;
        };
    }
}
