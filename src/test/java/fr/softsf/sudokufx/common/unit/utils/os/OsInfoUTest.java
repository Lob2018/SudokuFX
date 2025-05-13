/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.unit.utils.os;

import org.junit.jupiter.api.Test;

import static fr.softsf.sudokufx.enums.OsName.OS_NAME;
import static fr.softsf.sudokufx.enums.Paths.USER_HOME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OsInfoUTest {

    @Test
    void givenUserHomePath_whenGetPath_thenPathMatchesSystemProperty() {
        assertEquals(System.getProperty("user.home").replace("\\", "/"), USER_HOME.getPath());
    }

    @Test
    void givenOsName_whenGetOs_thenOsMatchesSystemProperty() {
        String expectedLowercaseOsName = System.getProperty("os.name").toLowerCase();
        assertEquals(expectedLowercaseOsName, OS_NAME.getOs());
    }
}
