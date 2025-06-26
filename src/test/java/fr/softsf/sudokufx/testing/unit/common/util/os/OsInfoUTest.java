/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util.os;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.OsName;

import static fr.softsf.sudokufx.common.enums.Paths.USER_HOME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OsInfoUTest {

    @Test
    void givenUserHomePath_whenGetPath_thenPathMatchesSystemProperty() {
        assertEquals(System.getProperty("user.home").replace("\\", "/"), USER_HOME.getPath());
    }

    @Test
    void givenOsName_whenGetOs_thenOsMatchesSystemProperty() {
        String expectedLowercaseOsName = System.getProperty("os.name").toLowerCase();
        assertTrue(
                expectedLowercaseOsName.contains(OsName.detect().getOs()),
                "L'OS détecté doit contenir : " + OsName.detect().getOs());
    }
}
