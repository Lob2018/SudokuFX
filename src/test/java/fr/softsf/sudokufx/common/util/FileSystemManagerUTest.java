/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static fr.softsf.sudokufx.enums.Paths.DATA_FOLDER;
import static org.junit.jupiter.api.Assertions.*;

class FileSystemManagerUTest {
    private final String suffix = DATA_FOLDER.getPath();
    private final FileSystemManager fileSystemManager = new FileSystemManager();
    @TempDir Path tempDir;
    private Path path1;
    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(FileSystemManager.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(FileSystemManager.class)).detachAndStopAllAppenders();
    }

    @BeforeEach
    void setUp() {
        try {
            Files.createDirectories(tempDir.resolve("testFolder/" + suffix + "/toto.txt"));
            path1 = tempDir.resolve("testFolder/" + suffix + "/toto.txt");
        } catch (InvalidPathException | IOException ipe) {
            System.err.println(
                    "error creating temporary test file in " + getClass().getSimpleName());
        }
    }

    @Test
    void givenValidFolder_whenDeleteDataFolderRecursively_thenDeletionSucceeds() {
        boolean result = fileSystemManager.deleteDataFolderRecursively(path1.getParent());
        assertTrue(result);
    }

    @Test
    void givenInvalidDirectoryPath_whenDeleteDataFolderRecursively_thenDeletionFails() {
        boolean result =
                fileSystemManager.deleteDataFolderRecursively(path1.getParent().subpath(0, 5));
        assertFalse(result);
    }

    @Test
    void givenNullPointerException_whenFilesWalk_thenErrorHandled() {
        boolean result;
        try (MockedStatic<Files> mocked = Mockito.mockStatic(Files.class)) {
            mocked.when(() -> Files.walk(path1.getParent()))
                    .thenThrow(new NullPointerException("Test NullPointerException"));
            fileSystemManager.deleteDataFolderRecursively(path1.getParent());
            result =
                    logWatcher
                            .list
                            .get(logWatcher.list.size() - 1)
                            .getFormattedMessage()
                            .contains("Test NullPointerException");
        }
        assertTrue(result);
    }

    @Test
    void givenNullPath_whenDeleteFile_thenIllegalArgumentExceptionThrown() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> fileSystemManager.deleteFile(null));
        assertTrue(exception.getMessage().contains("The path mustn't be null"));
    }

    @Test
    void givenNullPath_whenDeleteDataFolderRecursively_thenIllegalArgumentExceptionThrown() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> fileSystemManager.deleteDataFolderRecursively(null));
        assertTrue(exception.getMessage().contains("The folderPath mustn't be null"));
    }
}
