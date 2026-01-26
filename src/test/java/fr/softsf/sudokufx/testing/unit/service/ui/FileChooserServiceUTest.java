/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.service.ui;

import java.io.File;
import java.util.Optional;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.service.ui.FileChooserService;
import fr.softsf.sudokufx.service.ui.FileChooserService.FileType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileChooserServiceUTest {

    private FileChooserService service;

    @BeforeEach
    void setUp() {
        service = new FileChooserService();
    }

    @Test
    void givenNullStage_whenChooseFile_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class, () -> service.chooseFile(null, FileType.IMAGE));
        assertEquals("Stage must not be null", exception.getMessage());
    }

    @Test
    void givenNullFileType_whenChooseFile_thenThrowsNullPointerException() {
        Stage mockStage = mock(Stage.class);
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> service.chooseFile(mockStage, null));
        assertEquals("FileType must not be null", exception.getMessage());
    }

    @Test
    void givenValidStageAndFileType_whenChooseFile_thenReturnsOptional() {
        Stage mockStage = mock(Stage.class);
        Optional<File> result = service.chooseFile(mockStage, FileType.IMAGE);
        assertNotNull(result);
    }
}
