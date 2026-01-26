/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the difficulty levels of a Sudoku grid and provides conversions between enum values and
 * their numeric/byte representations.
 */
public enum DifficultyLevel {
    EASY,
    MEDIUM,
    DIFFICULT;

    private static final Logger LOG = LoggerFactory.getLogger(DifficultyLevel.class);

    /**
     * Converts this difficulty level to its numeric representation.
     *
     * @return {@code 1} for EASY, {@code 2} for MEDIUM, {@code 3} for DIFFICULT
     */
    public int toGridNumber() {
        return switch (this) {
            case EASY -> 1;
            case MEDIUM -> 2;
            case DIFFICULT -> 3;
        };
    }

    /**
     * Resolves a {@link DifficultyLevel} from its byte representation.
     *
     * @param number the byte value ({@code 1}, {@code 2}, {@code 3})
     * @return the corresponding {@link DifficultyLevel}
     * @throws IllegalArgumentException if the value does not match any level
     */
    public static DifficultyLevel fromGridByte(byte number) {
        return switch (number) {
            case 1 -> EASY;
            case 2 -> MEDIUM;
            case 3 -> DIFFICULT;
            default -> {
                IllegalArgumentException e =
                        new IllegalArgumentException(
                                "Invalid grid byte for DifficultyLevel: " + number);
                LOG.error("██ Exception fromGridByte failed. Cause: {}", e.getMessage(), e);
                throw e;
            }
        };
    }
}
