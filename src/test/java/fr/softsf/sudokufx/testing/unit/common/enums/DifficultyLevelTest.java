/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void givenEnumValues_whenAccessed_thenCorrectLengthAndValues() {
        DifficultyLevel[] values = DifficultyLevel.values();
        assertEquals(3, values.length);
        assertArrayEquals(
                new DifficultyLevel[] {
                    DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.DIFFICULT
                },
                values);
    }

    @Test
    void givenValidEnumString_whenValueOfCalled_thenCorrectEnum() {
        assertEquals(DifficultyLevel.EASY, DifficultyLevel.valueOf("EASY"));
        assertEquals(DifficultyLevel.MEDIUM, DifficultyLevel.valueOf("MEDIUM"));
        assertEquals(DifficultyLevel.DIFFICULT, DifficultyLevel.valueOf("DIFFICULT"));
    }

    @Test
    void givenInvalidEnumString_whenValueOfCalled_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> DifficultyLevel.valueOf("HARD"));
    }
}
