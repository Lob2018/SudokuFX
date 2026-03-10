/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.AppIcons;

import static org.junit.jupiter.api.Assertions.*;

class AppIconsUTest {
    @Test
    void givenEnumValues_whenAccessed_thenCorrectLengthAndValues() {
        AppIcons[] values = AppIcons.values();
        assertEquals(1, values.length);
        assertArrayEquals(new AppIcons[] {AppIcons.LOGO}, values);
    }

    @Test
    void givenLogoIcon_whenGetPathCalled_thenPathIsNotEmpty() {
        String path = AppIcons.LOGO.getPath();
        assertNotNull(path);
        assertFalse(path.isBlank());
        assertTrue(path.startsWith("M"));
    }

    @Test
    void givenValidEnumString_whenValueOfCalled_thenCorrectEnum() {
        assertEquals(AppIcons.LOGO, AppIcons.valueOf("LOGO"));
    }

    @Test
    void givenInvalidEnumString_whenValueOfCalled_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> AppIcons.valueOf("INVALID_ICON"));
    }
}
