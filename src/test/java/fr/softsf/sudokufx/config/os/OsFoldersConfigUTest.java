/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.os;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import fr.softsf.sudokufx.common.enums.OsName;

import static fr.softsf.sudokufx.common.enums.OsName.EMPTY_OS_FOR_TESTS;
import static fr.softsf.sudokufx.common.enums.OsName.WRONG_OS_FOR_TESTS;
import static org.junit.jupiter.api.Assertions.*;

class OsFoldersConfigUTest {
    private OsFoldersConfig osFoldersConfig;
    private IOsFolder iCurrentIOsFolder;

    static Stream<OsName> provideInvalidOperatingSystems() {
        return Stream.of(EMPTY_OS_FOR_TESTS, WRONG_OS_FOR_TESTS);
    }

    @BeforeEach
    void setup() {
        osFoldersConfig = new OsFoldersConfig();
        iCurrentIOsFolder = osFoldersConfig.iOsFolderFactory();
    }

    @Test
    void givenInvalidOs_whenCreateOsFolderFactory_thenIllegalArgumentExceptionThrown() {
        osFoldersConfig.setWrongOsForTests();
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    osFoldersConfig.iOsFolderFactory();
                },
                "Unsupported OS: ");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidOperatingSystems")
    void givenNullOrEmptyOs_whenCreateOsFolderFactory_thenIllegalArgumentExceptionTh(OsName os) {
        if (os == EMPTY_OS_FOR_TESTS) {
            osFoldersConfig.setEmptyOsForTests();
        } else if (os == WRONG_OS_FOR_TESTS) {
            osFoldersConfig.setWrongOsForTests();
        }
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, osFoldersConfig::iOsFolderFactory);
        assertTrue(exception.getMessage().contains("Unsupported OS: " + os));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Windows", "Linux", "MacOS"})
    void givenValidOs_whenCreateOsFolderFactory_thenCorrectPathsReturned(String osType) {
        switch (osType) {
            case "Windows" ->
                    osFoldersConfig.setWindowsOsForTests(
                            iCurrentIOsFolder.getOsDataFolderPath(),
                            iCurrentIOsFolder.getOsLogsFolderPath());
            case "Linux" ->
                    osFoldersConfig.setLinuxOsForTests(
                            iCurrentIOsFolder.getOsDataFolderPath(),
                            iCurrentIOsFolder.getOsLogsFolderPath());
            case "MacOS" ->
                    osFoldersConfig.setMacOSForTests(
                            iCurrentIOsFolder.getOsDataFolderPath(),
                            iCurrentIOsFolder.getOsLogsFolderPath());
            default -> throw new IllegalArgumentException("Unknown OS type: " + osType);
        }
        IOsFolder factory = osFoldersConfig.iOsFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), iCurrentIOsFolder.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), iCurrentIOsFolder.getOsLogsFolderPath());
    }
}
