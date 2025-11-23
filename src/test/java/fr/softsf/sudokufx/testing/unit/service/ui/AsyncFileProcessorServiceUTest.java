/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.service.ui;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.softsf.sudokufx.service.ui.AsyncFileProcessorService;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncFileProcessorServiceUTest {

    @Mock private File mockFile;
    @Mock private SpinnerGridPane mockSpinner;
    @Mock private Function<File, String> mockTaskFunction;
    @Mock private Consumer<String> mockOnSuccess;
    @Mock private ToasterService toasterService;

    private AsyncFileProcessorService service;

    @BeforeEach
    void setUp() {
        service = new AsyncFileProcessorService(toasterService);
    }

    @Test
    void givenNullFile_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        null, mockSpinner, mockTaskFunction, mockOnSuccess));
        assertEquals("selectedFile must not be null", exception.getMessage());
    }

    @Test
    void givenNullSpinner_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile, null, mockTaskFunction, mockOnSuccess));
        assertEquals("spinner must not be null", exception.getMessage());
    }

    @Test
    void givenNullTaskFunction_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> service.processFileAsync(mockFile, mockSpinner, null, mockOnSuccess));
        assertEquals("taskFunction must not be null", exception.getMessage());
    }

    @Test
    void givenNullOnSuccess_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile, mockSpinner, mockTaskFunction, null));
        assertEquals("onSuccess must not be null", exception.getMessage());
    }

    @Test
    void givenValidFile_whenProcessFileAsync_thenShowsSpinnerAndInfoToast() {
        when(mockFile.toURI()).thenReturn(java.net.URI.create("file://test.txt"));
        service.processFileAsync(mockFile, mockSpinner, f -> "result", result -> {});
        verify(mockSpinner).showSpinner(true);
        verify(toasterService).showInfo(anyString(), eq("file://test.txt"));
    }

    @Test
    void givenValidFile_whenProcessFileAsync_thenDoesNotBlockCallingThread() {
        when(mockFile.toURI()).thenReturn(java.net.URI.create("file://test.txt"));
        long startTime = System.currentTimeMillis();
        service.processFileAsync(mockFile, mockSpinner, mockTaskFunction, mockOnSuccess);
        long endTime = System.currentTimeMillis();
        assertTrue((endTime - startTime) < 100, "Method should not block the calling thread");
        verify(mockSpinner).showSpinner(true);
    }
}
