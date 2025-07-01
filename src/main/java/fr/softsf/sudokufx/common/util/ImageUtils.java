/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.io.File;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;

import fr.softsf.sudokufx.common.enums.ScreenSize;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Utility class providing helper methods for common image-related operations in JavaFX.
 *
 * <p>Includes support for:
 *
 * <ul>
 *   <li>Converting 32-bit integer colors to JavaFX {@link Color}
 *   <li>Validating image file formats
 *   <li>Calculating scale factors to resize images to fit layout constraints
 *   <li>Creating {@link BackgroundImage} instances with custom layout properties
 * </ul>
 *
 * <p>All methods throw {@link IllegalArgumentException} when passed {@code null} arguments.
 *
 * <p>This class is not instantiable and is intended to be used statically.
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
     * Determines if the given file has a supported image extension.
     *
     * <p>Accepted formats: JPG, JPEG, PNG, BMP (case-insensitive).
     *
     * @param file the file to check; must not be {@code null}
     * @return {@code true} if the file has a valid image extension; {@code false} otherwise
     * @throws IllegalArgumentException if {@code file} is {@code null}
     */
    public boolean isValidImage(File file) {
        if (Objects.isNull(file)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The file mustn't be null");
        }
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".bmp");
    }

    /**
     * Calculates the scale factor needed to resize the given image so it fits within a predefined
     * grid pane area (3 times the visual width and height).
     *
     * @param tempImage the image to resize; must not be {@code null}
     * @return the scale factor to apply for resizing the image to fit within the target grid pane
     * @throws IllegalArgumentException if {@code tempImage} is {@code null}
     */
    public double calculateImageScaleFactor(Image tempImage) {
        if (Objects.isNull(tempImage)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The image to be resized mustn't be null");
        }
        double imageWidth = tempImage.getWidth();
        double imageHeight = tempImage.getHeight();
        double gridPaneWidth = ScreenSize.VISUAL_WIDTH.getSize() * 3;
        double gridPaneHeight = ScreenSize.VISUAL_HEIGHT.getSize() * 3;
        return Math.max(gridPaneWidth / imageWidth, gridPaneHeight / imageHeight);
    }

    /**
     * Creates a JavaFX {@link BackgroundImage} from the provided image.
     *
     * <p>The background image is centered, non-repeating, and sized to one-third of the original
     * image's width and height.
     *
     * @param resizedImage the image to use as the background; must not be {@code null}
     * @return a {@link BackgroundImage} configured with the given image
     * @throws IllegalArgumentException if {@code resizedImage} is {@code null}
     */
    public BackgroundImage createBackgroundImage(Image resizedImage) {
        if (Objects.isNull(resizedImage)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The background image mustn't be null");
        }
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
