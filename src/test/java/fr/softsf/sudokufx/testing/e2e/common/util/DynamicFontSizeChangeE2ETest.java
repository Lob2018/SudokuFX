/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.e2e.common.util;

import java.util.concurrent.TimeoutException;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import fr.softsf.sudokufx.common.util.DynamicFontSize;

import static fr.softsf.sudokufx.common.enums.ScreenSize.DISPOSABLE_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class DynamicFontSizeChangeE2ETest {

    private DynamicFontSize dynamicFontSize;
    private Stage stage;

    @Start
    private void start(Stage primarystage) {
        stage = primarystage;
        Scene scene = new Scene(new VBox(), 500, 500);
        stage.setScene(scene);
        dynamicFontSize = new DynamicFontSize(scene);
        stage.show();
    }

    @Test
    void givenStageResized_whenUpdateFontSize_thenFontSizeAdjustedCorrectly(FxRobot robot) {
        robot.interact(
                () -> {
                    stage.setWidth(300);
                    stage.setHeight(350);
                });
        assertTrue(Double.isFinite(DISPOSABLE_SIZE.getSize()));
        assertEquals(6.57, dynamicFontSize.getCurrentFontSize(), 0.01);
    }

    @Test
    void givenNullScene_whenConstructDynamicFontSize_thenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            new DynamicFontSize(null);
                        });
        String expectedMessage = "The scene mustn't be null";
        String actualMessage = thrown.getMessage();
        assertTrue(
                actualMessage.contains(expectedMessage),
                "Expected exception message to contain: \""
                        + expectedMessage
                        + "\"\nActual message: \""
                        + actualMessage
                        + "\"");
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
