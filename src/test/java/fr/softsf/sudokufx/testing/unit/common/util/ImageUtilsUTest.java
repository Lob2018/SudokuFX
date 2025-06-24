/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util;

import java.io.File;
import java.util.concurrent.TimeoutException;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import fr.softsf.sudokufx.common.util.ImageUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ImageUtilsUTest {

    private ImageUtils imageUtils;

    @Start
    private void start(Stage stage) {
        stage.setScene(new Scene(new javafx.scene.layout.VBox(), 500, 500));
        stage.show();
        imageUtils = new ImageUtils();
    }

    @Test
    void givenColorInt_whenIntToColor_thenReturnsExpectedColor() {
        int colorInt = 0x11223344; // R=0x11, G=0x22, B=0x33, A=0x44
        var color = imageUtils.intToColor(colorInt);
        assertEquals(0x11 / 255.0, color.getRed(), 0.01);
        assertEquals(0x22 / 255.0, color.getGreen(), 0.01);
        assertEquals(0x33 / 255.0, color.getBlue(), 0.01);
        assertEquals(0x44 / 255.0, color.getOpacity(), 0.01);
    }

    @Test
    void givenValidFile_whenIsValidImage_thenReturnsTrueForSupportedExtensions() {
        assertTrue(imageUtils.isValidImage(new File("photo.jpg")));
        assertTrue(imageUtils.isValidImage(new File("picture.JPEG")));
        assertTrue(imageUtils.isValidImage(new File("image.PNG")));
        assertTrue(imageUtils.isValidImage(new File("bitmap.bmp")));
    }

    @Test
    void givenInvalidFile_whenIsValidImage_thenReturnsFalse() {
        assertFalse(imageUtils.isValidImage(new File("document.pdf")));
        assertFalse(imageUtils.isValidImage(new File("archive.zip")));
        assertFalse(imageUtils.isValidImage(new File("image.gif")));
        assertFalse(imageUtils.isValidImage(new File("file.")));
    }

    @Test
    void givenNullFile_whenIsValidImage_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            imageUtils.isValidImage(null);
                        });
        assertTrue(ex.getMessage().contains("The file mustn't be null"));
    }

    @Test
    void givenValidImage_whenCalculateImageScaleFactor_thenReturnsPositiveScale() {
        Image image =
                new Image(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Hopetoun_falls.jpg/320px-Hopetoun_falls.jpg");
        double scale = imageUtils.calculateImageScaleFactor(image);
        assertTrue(scale > 0);
    }

    @Test
    void givenNullImage_whenCalculateImageScaleFactor_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            imageUtils.calculateImageScaleFactor(null);
                        });
        assertTrue(ex.getMessage().contains("The image to be resized mustn't be null"));
    }

    @Test
    void givenValidImage_whenCreateBackgroundImage_thenReturnsNonNullBackgroundImage() {
        Image image =
                new Image(
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/36/Hopetoun_falls.jpg/320px-Hopetoun_falls.jpg");
        BackgroundImage bgImage = imageUtils.createBackgroundImage(image);
        assertNotNull(bgImage);
        assertEquals(image, bgImage.getImage());
        assertEquals(image.getWidth() / 3, bgImage.getSize().getWidth(), 0.01);
        assertEquals(image.getHeight() / 3, bgImage.getSize().getHeight(), 0.01);
    }

    @Test
    void givenNullImage_whenCreateBackgroundImage_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            imageUtils.createBackgroundImage(null);
                        });
        assertTrue(ex.getMessage().contains("The background image mustn't be null"));
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
}
