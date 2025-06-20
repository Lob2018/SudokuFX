/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.configuration.os;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OsFolderFactoryManagerUTest {
    private OsFolderFactoryManager osFolderFactoryManager;
    private IOsFolderFactory iCurrentIOsFolderFactory;

    static Stream<String> provideInvalidOperatingSystems() {
        return Stream.of(null, "");
    }

    @BeforeEach
    void setup() {
        osFolderFactoryManager = new OsFolderFactoryManager();
        iCurrentIOsFolderFactory = osFolderFactoryManager.iOsFolderFactory();
    }

    @Test
    void givenInvalidOs_whenCreateOsFolderFactory_thenIllegalArgumentExceptionThrown() {
        osFolderFactoryManager.setWrongOsForTests();
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    osFolderFactoryManager.iOsFolderFactory();
                },
                "Unsupported OS: ");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidOperatingSystems")
    void givenNullOrEmptyOs_whenCreateOsFolderFactory_thenIllegalArgumentExceptionTh(String os) {
        if (os == null) {
            osFolderFactoryManager.setNullOsForTests();
        } else if (os.isEmpty()) {
            osFolderFactoryManager.setEmptyOsForTests();
        }
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, osFolderFactoryManager::iOsFolderFactory);
        assertTrue(exception.getMessage().contains("Operating system is not specified or null."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Windows", "Linux", "MacOS"})
    void givenValidOs_whenCreateOsFolderFactory_thenCorrectPathsReturned(String osType) {
        switch (osType) {
            case "Windows" ->
                    osFolderFactoryManager.setWindowsOsForTests(
                            iCurrentIOsFolderFactory.getOsDataFolderPath(),
                            iCurrentIOsFolderFactory.getOsLogsFolderPath());
            case "Linux" ->
                    osFolderFactoryManager.setLinuxOsForTests(
                            iCurrentIOsFolderFactory.getOsDataFolderPath(),
                            iCurrentIOsFolderFactory.getOsLogsFolderPath());
            case "MacOS" ->
                    osFolderFactoryManager.setMacOSForTests(
                            iCurrentIOsFolderFactory.getOsDataFolderPath(),
                            iCurrentIOsFolderFactory.getOsLogsFolderPath());
            default -> throw new IllegalArgumentException("Unknown OS type: " + osType);
        }
        IOsFolderFactory factory = osFolderFactoryManager.iOsFolderFactory();
        assertEquals(factory.getOsDataFolderPath(), iCurrentIOsFolderFactory.getOsDataFolderPath());
        assertEquals(factory.getOsLogsFolderPath(), iCurrentIOsFolderFactory.getOsLogsFolderPath());
    }
}
