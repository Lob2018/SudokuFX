/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

public enum DifficultyLevel {
    EASY,
    MEDIUM,
    DIFFICULT;

    public int toGridNumber() {
        return switch (this) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case DIFFICULT -> 3;
        };
    }
}
