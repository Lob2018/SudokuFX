/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import fr.softsf.sudokufx.common.exception.ExceptionTools;

import static fr.softsf.sudokufx.common.enums.AppPaths.DATA_FOLDER;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileSystemManagerUTest {
    private final String suffix = DATA_FOLDER.getPath();
    private final LocalUserDataPurger fileSystemManager = new LocalUserDataPurger();
    @TempDir Path tempDir;
    private Path path1;
    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(LocalUserDataPurger.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(LocalUserDataPurger.class)).detachAndStopAllAppenders();
    }

    @BeforeEach
    void setUp() {
        try {
            Files.createDirectories(tempDir.resolve("testFolder/" + suffix + "/toto.txt"));
            path1 = tempDir.resolve("testFolder/" + suffix + "/toto.txt");
        } catch (InvalidPathException | IOException _) {
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
    void
            givenInvalidDirectoryPath_whenDeleteDataFolderRecursively_thenIllegalStateExceptionThrown() {
        ((Logger) LoggerFactory.getLogger(ExceptionTools.class)).addAppender(logWatcher);
        Path base = Paths.get("root", "segment1", "segment2");
        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        () -> fileSystemManager.deleteDataFolderRecursively(base));
        assertTrue(exception.getMessage().contains("Path does not exist"));
        assertTrue(
                logWatcher
                        .list
                        .getLast()
                        .getFormattedMessage()
                        .contains("██ Exception: Path does not exist"));
    }

    @Test
    @SuppressWarnings("resource")
    void givenNullPointerException_whenFilesWalk_thenErrorHandled() {
        boolean result;
        try (MockedStatic<Files> mocked = Mockito.mockStatic(Files.class)) {
            mocked.when(() -> Files.walk(path1.getParent()))
                    .thenThrow(new NullPointerException("Test NullPointerException"));
            fileSystemManager.deleteDataFolderRecursively(path1.getParent());
            result =
                    logWatcher
                            .list
                            .getLast()
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
        ((Logger) LoggerFactory.getLogger(ExceptionTools.class)).addAppender(logWatcher);
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> fileSystemManager.deleteDataFolderRecursively(null));
        assertTrue(exception.getMessage().contains("mustn't be null"));
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains("Exception: The path mustn't be null")),
                "The error should be logged by ExceptionTools");
    }

    @Test
    void givenIOException_whenDeleteFile_thenExceptionReturnedAndLogged() throws IOException {
        ((Logger) LoggerFactory.getLogger(LocalUserDataPurger.class)).addAppender(logWatcher);
        Path realPath = tempDir.resolve("toDelete.txt");
        Files.createFile(realPath);
        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles
                    .when(() -> Files.delete(realPath))
                    .thenThrow(new IOException("Test IOException"));
            Object result = fileSystemManager.deleteFile(realPath);
            assertInstanceOf(IOException.class, result);
            assertTrue(
                    logWatcher.list.stream()
                            .anyMatch(event -> event.getFormattedMessage().contains("IO failure")),
                    "Log message should contain 'IO failure'");
        }
    }
}
