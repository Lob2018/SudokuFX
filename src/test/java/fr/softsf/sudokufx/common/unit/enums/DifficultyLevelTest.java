/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.common.unit.enums;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.enums.DifficultyLevel;

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
