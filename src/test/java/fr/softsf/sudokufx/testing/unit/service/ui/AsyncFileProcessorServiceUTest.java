/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
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

import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.service.ui.AsyncFileProcessorService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncFileProcessorServiceUTest {

    @Mock private File mockFile;
    @Mock private SpinnerGridPane mockSpinner;
    @Mock private ToasterVBox mockToaster;
    @Mock private Function<File, String> mockTaskFunction;
    @Mock private Consumer<String> mockOnSuccess;

    private AsyncFileProcessorService service;

    @BeforeEach
    void setUp() {
        service = new AsyncFileProcessorService();
    }

    @Test
    void givenNullFile_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        null,
                                        mockSpinner,
                                        mockToaster,
                                        mockTaskFunction,
                                        mockOnSuccess));
        assertEquals("selectedFile must not be null", exception.getMessage());
    }

    @Test
    void givenNullSpinner_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile,
                                        null,
                                        mockToaster,
                                        mockTaskFunction,
                                        mockOnSuccess));
        assertEquals("spinner must not be null", exception.getMessage());
    }

    @Test
    void givenNullToaster_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile,
                                        mockSpinner,
                                        null,
                                        mockTaskFunction,
                                        mockOnSuccess));
        assertEquals("toaster must not be null", exception.getMessage());
    }

    @Test
    void givenNullTaskFunction_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile, mockSpinner, mockToaster, null, mockOnSuccess));
        assertEquals("taskFunction must not be null", exception.getMessage());
    }

    @Test
    void givenNullOnSuccess_whenProcessFileAsync_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () ->
                                service.processFileAsync(
                                        mockFile,
                                        mockSpinner,
                                        mockToaster,
                                        mockTaskFunction,
                                        null));
        assertEquals("onSuccess must not be null", exception.getMessage());
    }

    @Test
    void givenValidFile_whenProcessFileAsync_thenShowsSpinnerAndInfoToast() throws Exception {
        String fileUri = "file://test.txt";
        when(mockFile.toURI()).thenReturn(new java.net.URI(fileUri));
        service.processFileAsync(
                mockFile, mockSpinner, mockToaster, mockTaskFunction, mockOnSuccess);
        verify(mockSpinner).showSpinner(true);
        verify(mockToaster)
                .addToast(any(String.class), eq(fileUri), eq(ToastLevels.INFO), eq(false));
    }

    @Test
    void givenValidFile_whenProcessFileAsync_thenDoesNotBlockCallingThread() throws Exception {
        when(mockFile.toURI()).thenReturn(java.net.URI.create("file://test.txt"));
        long startTime = System.currentTimeMillis();
        service.processFileAsync(
                mockFile, mockSpinner, mockToaster, mockTaskFunction, mockOnSuccess);
        long endTime = System.currentTimeMillis();
        assertTrue((endTime - startTime) < 100, "Method should not block the calling thread");
        verify(mockSpinner).showSpinner(true);
    }
}
