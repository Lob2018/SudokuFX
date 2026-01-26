/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.*;

import fr.softsf.sudokufx.common.enums.OsName;

import static org.junit.jupiter.api.Assertions.*;

class OsNameUTest {

    private String originalOsName;

    @BeforeEach
    void saveOriginalOsName() {
        originalOsName = System.getProperty("os.name");
    }

    @AfterEach
    void restoreOriginalOsName() {
        if (originalOsName != null) {
            System.setProperty("os.name", originalOsName);
        } else {
            System.clearProperty("os.name");
        }
    }

    @Test
    void givenWindowsOsName_whenDetect_thenReturnWindows() {
        System.setProperty("os.name", "Windows 10");
        assertEquals(OsName.WINDOWS, OsName.detect());
    }

    @Test
    void givenLinuxOsName_whenDetect_thenReturnLinux() {
        System.setProperty("os.name", "Linux");
        assertEquals(OsName.LINUX, OsName.detect());
    }

    @Test
    void givenMacOsName_whenDetect_thenReturnMac() {
        System.setProperty("os.name", "Mac OS X");
        assertEquals(OsName.MAC, OsName.detect());
    }

    @Test
    void givenBlankOsName_whenDetect_thenThrowIllegalArgumentException() {
        System.setProperty("os.name", "");
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, OsName::detect);
        assertTrue(thrown.getMessage().contains("Operating system must not be null or blank"));
    }

    @Test
    void givenUnsupportedOsName_whenDetect_thenThrowIllegalArgumentException() {
        System.setProperty("os.name", "Solaris");
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, OsName::detect);
        assertTrue(thrown.getMessage().contains("Unsupported OS"));
    }
}
