/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Locale;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    private static final int RED_SHIFT = 24;
    private static final int GREEN_SHIFT = 16;
    private static final int BLUE_SHIFT = 8;
    private static final int ALPHA_SHIFT = 0;
    private static final int BYTE_MASK = 0xFF;
    private static final double ALPHA_NORMALIZATION = 255.0;

    /**
     * Converts a 32-bit integer color value in the format 0xRRGGBBAA into a JavaFX {@link Color}
     * object.
     *
     * @param colorValue The color as an integer in hexadecimal format (0xRRGGBBAA).
     * @return A {@link Color} instance representing the given color and alpha.
     */
    public Color intToColor(int colorValue) {
        return Color.rgb(
                (colorValue >> RED_SHIFT) & BYTE_MASK,
                (colorValue >> GREEN_SHIFT) & BYTE_MASK,
                (colorValue >> BLUE_SHIFT) & BYTE_MASK,
                ((colorValue >> ALPHA_SHIFT) & BYTE_MASK) / ALPHA_NORMALIZATION);
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
                    "The image file mustn't be null");
        }
        String fileName = file.getName().toLowerCase(Locale.ROOT);
        return fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".bmp");
    }

    /**
     * Reads image metadata (width, height) and calculates the scale factor needed to resize the
     * image so it fits within a predefined grid pane area (3 times the visual width and height),
     * without fully loading the image in memory.
     *
     * @param file the image file; must not be {@code null}
     * @return ImageMeta containing width, height and scaleFactor
     * @throws IllegalArgumentException if file is {@code null}, cannot be opened as image stream,
     *     has unsupported format, invalid structure, or zero dimensions
     * @throws UncheckedIOException if an I/O error occurs while reading the image file
     */
    public ImageMeta getImageMeta(File file) {
        if (file == null) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The image file mustn't be null");
        }
        try (ImageInputStream in = ImageIO.createImageInputStream(file)) {
            if (in == null) {
                throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                        "Cannot open image stream for: " + file.getName());
            }
            var readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    int imageWidth = reader.getWidth(0);
                    int imageHeight = reader.getHeight(0);
                    double gridPaneWidth = ScreenSize.VISUAL_WIDTH.getSize() * 3;
                    double gridPaneHeight = ScreenSize.VISUAL_HEIGHT.getSize() * 3;
                    double scaleFactor =
                            Math.max(gridPaneWidth / imageWidth, gridPaneHeight / imageHeight);
                    return new ImageMeta(imageWidth, imageHeight, scaleFactor);
                } finally {
                    reader.dispose();
                }
            }
            String msg = "Unsupported image format: " + file.getName();
            LOG.error("██ {}", msg);
            throw new IllegalArgumentException(msg);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to read image: " + file.getName() + " - " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to process image: " + file.getName() + " - " + e.getMessage(), e);
        }
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
