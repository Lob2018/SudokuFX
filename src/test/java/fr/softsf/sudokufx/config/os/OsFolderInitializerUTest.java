/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import fr.softsf.sudokufx.common.exception.FolderCreationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OsFolderInitializerUTest {

    @TempDir Path tempDir;

    private static Stream<Arguments> invalidPathsProvider() {
        return Stream.of(
                Arguments.of(null, "validLogsPath", "dataFolderPath"),
                Arguments.of("validDataPath", null, "logsFolderPath"),
                Arguments.of("   ", "validLogsPath", "dataFolderPath"),
                Arguments.of("validDataPath", " ", "logsFolderPath"));
    }

    @Test
    void givenNoFileButExistentFolder_whenCreateFolder_thenFolderCreated() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenReturn(true);
        OsFolderInitializer.INSTANCE.createFolder(mockFolder);
        verify(mockFolder).exists();
        verify(mockFolder).mkdirs();
    }

    @Test
    void givenNoFileAndNonExistentFolder_whenCreateFolderFails_thenRuntimeExceptionThrown() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenReturn(false);
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> OsFolderInitializer.INSTANCE.createFolder(mockFolder));
        assertEquals("Error when creating folder: " + mockFolder.getPath(), exception.getMessage());
        verify(mockFolder).exists();
        verify(mockFolder).mkdirs();
    }

    @Test
    void givenExistingFolder_whenCreateFolder_thenNoCreationAttempted() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(true);
        OsFolderInitializer.INSTANCE.createFolder(mockFolder);
        verify(mockFolder).exists();
        verify(mockFolder, never()).mkdirs();
    }

    @Test
    void givenNonExistentFolder_whenCreateFolder_thenSecurityException() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenThrow(new SecurityException("Security violation"));
        when(mockFolder.getAbsolutePath()).thenReturn("/fake/path");
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> OsFolderInitializer.INSTANCE.createFolder(mockFolder));
        assertTrue(
                exception.getMessage().contains("Security error")
                        || exception.getMessage().contains("folder"),
                "Expected message to indicate security error or folder creation issue");
        verify(mockFolder).exists();
        verify(mockFolder).mkdirs();
    }

    @Test
    void givenNonExistentFolder_whenCreateFolder_thenFolderCreationException() {
        File mockFolder = mock(File.class);
        when(mockFolder.exists()).thenReturn(false);
        when(mockFolder.mkdirs()).thenThrow(new RuntimeException("General error"));
        when(mockFolder.getAbsolutePath()).thenReturn("/fake/path");
        FolderCreationException exception =
                assertThrows(
                        FolderCreationException.class,
                        () -> OsFolderInitializer.INSTANCE.createFolder(mockFolder));
        assertTrue(
                exception.getMessage().contains("Folder creation failed")
                        || exception.getMessage().contains("/fake/path"),
                "Exception message should indicate folder creation failure");
        verify(mockFolder).exists();
        verify(mockFolder).mkdirs();
    }

    @Test
    void givenNullFolder_whenCreateFolder_thenIllegalArgumentException() {
        File nullFolder = null;

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> OsFolderInitializer.INSTANCE.createFolder(nullFolder));

        assertTrue(exception.getMessage().contains("mustn't be null"));
    }

    @ParameterizedTest(
            name =
                    "given \"{0}\" and \"{1}\" when initializeFolders then IllegalArgumentException"
                            + " on {2}")
    @MethodSource("invalidPathsProvider")
    void givenInvalidPaths_whenInitializeFolders_thenIllegalArgumentException(
            String dataPath, String logsPath, String expectedInMessage) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> OsFolderInitializer.INSTANCE.initializeFolders(dataPath, logsPath));
        assertTrue(exception.getMessage().contains(expectedInMessage));
    }

    @Test
    void givenValidPaths_whenInitializeFolders_thenFoldersCreated() {
        String dataPath = tempDir.resolve("testDataFolder").toString();
        String logsPath = tempDir.resolve("testLogsFolder").toString();
        OsInitializedFolders osInitializedFolders =
                OsFolderInitializer.INSTANCE.initializeFolders(dataPath, logsPath);
        assertEquals(dataPath, osInitializedFolders.dataFolderPath());
        assertEquals(logsPath, osInitializedFolders.logsFolderPath());
        assertTrue(new File(dataPath).exists());
        assertTrue(new File(logsPath).exists());
    }
}
