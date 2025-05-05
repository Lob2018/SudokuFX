package fr.softsf.sudokufx.common.unit.enums;

import fr.softsf.sudokufx.enums.DifficultyLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void givenEnumValues_whenAccessed_thenCorrectLengthAndValues() {
        DifficultyLevel[] values = DifficultyLevel.values();
        assertEquals(3, values.length);
        assertArrayEquals(new DifficultyLevel[]{DifficultyLevel.EASY, DifficultyLevel.MEDIUM, DifficultyLevel.DIFFICULT}, values);
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

