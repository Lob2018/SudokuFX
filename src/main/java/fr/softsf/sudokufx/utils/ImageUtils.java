/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.utils;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

import fr.softsf.sudokufx.enums.ScreenSize;

/**
 * Utility class for image-related operations, including color conversion, image validation,
 * resizing calculation, and creating JavaFX BackgroundImage objects.
 */
public class ImageUtils {

    /**
     * Converts a 32-bit integer color value in the format 0xRRGGBBAA into a JavaFX {@link Color}
     * object.
     *
     * @param colorValue The color as an integer in hexadecimal format (0xRRGGBBAA).
     * @return A {@link Color} instance representing the given color and alpha.
     */
    public Color intToColor(int colorValue) {
        return Color.rgb(
                (colorValue >> 24) & 0xFF,
                (colorValue >> 16) & 0xFF,
                (colorValue >> 8) & 0xFF,
                (colorValue & 0xFF) / 255.0);
    }

    /**
     * Checks whether the given file is a valid image based on its extension. Supported formats are
     * JPG, JPEG, PNG, and BMP (case-insensitive).
     *
     * @param file The file to validate.
     * @return {@code true} if the file has a valid image extension; {@code false} otherwise.
     */
    public boolean isValidImage(File file) {
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".bmp");
    }

    /**
     * Calculates the scale factor to resize an image so that it fits within a predefined grid pane
     * size (3 times the visual width and height).
     *
     * @param tempImage The image to be resized.
     * @return The scale factor to apply for resizing.
     */
    public double calculateImageScaleFactor(Image tempImage) {
        double imageWidth = tempImage.getWidth();
        double imageHeight = tempImage.getHeight();
        double gridPaneWidth = ScreenSize.VISUAL_WIDTH.getSize() * 3;
        double gridPaneHeight = ScreenSize.VISUAL_HEIGHT.getSize() * 3;
        return Math.max(gridPaneWidth / imageWidth, gridPaneHeight / imageHeight);
    }

    /**
     * Creates a JavaFX {@link BackgroundImage} from the provided resized image. The background
     * image is centered and set to not repeat, with sizing based on one-third of the image
     * dimensions.
     *
     * @param resizedImage The image to use as the background.
     * @return A {@link BackgroundImage} configured with the given image.
     */
    public BackgroundImage createBackgroundImage(Image resizedImage) {
        return new BackgroundImage(
                resizedImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        resizedImage.getWidth() / 3,
                        resizedImage.getHeight() / 3,
                        false,
                        false,
                        false,
                        false));
    }
}
