/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.integration.common.enums;

import java.util.concurrent.TimeoutException;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static fr.softsf.sudokufx.common.enums.ScreenSize.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class ScreenSizeITest {

    @Start
    private void start(Stage primarystage) {
        Scene scene = new Scene(new VBox(), 500, 500);
        primarystage.setScene(scene);
        primarystage.show();
    }

    @Test
    void givenScreenMinimumSize_whenGetSize_thenReturnFiniteFloatingPointValue() {
        assertTrue(
                Double.isFinite(DISPOSABLE_SIZE.getSize()),
                "DISPOSABLE_SIZE should return a finite value.");
    }

    @Test
    void givenScreenDisposableSize_whenGetSize_thenReturnExpectedMinimumSize() {
        double expectedDisposableSize =
                Math.min(
                        Screen.getPrimary().getVisualBounds().getWidth(),
                        Screen.getPrimary().getVisualBounds().getHeight());
        assertEquals(
                expectedDisposableSize,
                DISPOSABLE_SIZE.getSize(),
                "DISPOSABLE_SIZE should match the expected minimum size.");
    }

    @Test
    void givenScreenVisualWidth_whenGetSize_thenReturnFiniteFloatingPointValue() {
        assertTrue(
                Double.isFinite(VISUAL_WIDTH.getSize()),
                "VISUAL_WIDTH should return a finite value.");
    }

    @Test
    void givenScreenVisualHeight_whenGetSize_thenReturnFiniteFloatingPointValue() {
        assertTrue(
                Double.isFinite(VISUAL_HEIGHT.getSize()),
                "VISUAL_HEIGHT should return a finite value.");
    }

    @Test
    void givenScreenVisualWidth_whenGetSize_thenReturnExpectedWidth() {
        double expectedWidth = Screen.getPrimary().getVisualBounds().getWidth();
        assertEquals(
                expectedWidth,
                VISUAL_WIDTH.getSize(),
                "VISUAL_WIDTH should match the screen's visual bounds width.");
    }

    @Test
    void givenScreenVisualHeight_whenGetSize_thenReturnExpectedHeight() {
        double expectedHeight = Screen.getPrimary().getVisualBounds().getHeight();
        assertEquals(
                expectedHeight,
                VISUAL_HEIGHT.getSize(),
                "VISUAL_HEIGHT should match the screen's visual bounds height.");
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
