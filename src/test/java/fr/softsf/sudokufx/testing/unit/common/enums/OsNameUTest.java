/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.*;

import fr.softsf.sudokufx.common.enums.OsName;

import static org.junit.jupiter.api.Assertions.*;

public class OsNameUTest {

    private String originalOsName;

    @BeforeEach
    public void saveOriginalOsName() {
        originalOsName = System.getProperty("os.name");
    }

    @AfterEach
    public void restoreOriginalOsName() {
        if (originalOsName != null) {
            System.setProperty("os.name", originalOsName);
        } else {
            System.clearProperty("os.name");
        }
    }

    @Test
    public void givenWindowsOsName_whenDetect_thenReturnWindows() {
        System.setProperty("os.name", "Windows 10");
        assertEquals(OsName.WINDOWS, OsName.detect());
    }

    @Test
    public void givenLinuxOsName_whenDetect_thenReturnLinux() {
        System.setProperty("os.name", "Linux");
        assertEquals(OsName.LINUX, OsName.detect());
    }

    @Test
    public void givenMacOsName_whenDetect_thenReturnMac() {
        System.setProperty("os.name", "Mac OS X");
        assertEquals(OsName.MAC, OsName.detect());
    }

    @Test
    public void givenBlankOsName_whenDetect_thenThrowIllegalArgumentException() {
        System.setProperty("os.name", "");
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> OsName.detect());
        assertTrue(thrown.getMessage().contains("Operating system must not be null or blank"));
    }

    @Test
    public void givenUnsupportedOsName_whenDetect_thenThrowIllegalArgumentException() {
        System.setProperty("os.name", "Solaris");
        IllegalArgumentException thrown =
                assertThrows(IllegalArgumentException.class, () -> OsName.detect());
        assertTrue(thrown.getMessage().contains("Unsupported OS"));
    }
}
