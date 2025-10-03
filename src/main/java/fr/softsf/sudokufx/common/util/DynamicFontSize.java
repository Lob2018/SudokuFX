/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.scene.Scene;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.math.NumberUtils;

/**
 * Dynamically adjusts the root font size of a JavaFX Scene based on its dimensions.
 *
 * <p>Uses a debounced dynamic stylesheet to minimize memory allocations and reduce GC pressure.
 * Updates occur only when the font size changes significantly, leveraging JavaFX's stylesheet
 * caching.
 */
public final class DynamicFontSize {

    private static final double FONT_SIZE_RATIO = 0.0219;
    private static final String ROOT_FONT_CSS_PREFIX = "data:text/css,.root { -fx-font-size: ";

    private final Scene scene;
    private double currentFontSize = -1;
    private int dynamicStylesheetIndex = -1;

    /**
     * Returns the current calculated font size in pixels.
     *
     * @return current font size
     */
    public double getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Constructs a DynamicFontSize instance for the specified Scene.
     *
     * <p>Attaches listeners to the scene's width and height properties to automatically update the
     * font size when the scene is resized.
     *
     * @param scene the JavaFX Scene to observe; must not be null
     * @throws IllegalArgumentException if the scene is null, with logging via ExceptionTools
     */
    public DynamicFontSize(Scene scene) {
        this.scene =
                Objects.requireNonNull(
                        scene,
                        () -> {
                            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                    "The scene mustn't be null");
                        });
        initialize();
    }

    /**
     * Sets up dynamic font resizing by adding listeners to the scene's width and height.
     *
     * <p>An initial font size update is performed immediately after attaching the listeners.
     */
    private void initialize() {
        InvalidationListener listener = obs -> updateFontSize();
        scene.widthProperty().addListener(listener);
        scene.heightProperty().addListener(listener);
        updateFontSize();
    }

    /**
     * Updates the font size of the scene's root node based on its current dimensions.
     *
     * <p>The font size scales with the smaller dimension of the scene using {@link
     * #FONT_SIZE_RATIO}. Updates are applied only if the size changes significantly, reducing
     * unnecessary stylesheet refreshes and memory allocations. A dynamic stylesheet is created or
     * updated in the scene's stylesheets, leveraging JavaFX's internal caching.
     */
    public void updateFontSize() {
        double fontSize = Math.min(scene.getWidth(), scene.getHeight()) * FONT_SIZE_RATIO;
        if (NumberUtils.INSTANCE.areDoublesEqualCenti(fontSize, currentFontSize)) {
            return;
        }
        currentFontSize = fontSize;
        var stylesheets = scene.getStylesheets();
        String css = ROOT_FONT_CSS_PREFIX + fontSize + "px; }";
        if (dynamicStylesheetIndex >= 0 && dynamicStylesheetIndex < stylesheets.size()) {
            stylesheets.set(dynamicStylesheetIndex, css);
        } else {
            stylesheets.add(css);
            dynamicStylesheetIndex = stylesheets.size() - 1;
        }
    }
}
