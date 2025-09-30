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

/**
 * Handles dynamic font resizing for a JavaFX Scene using debounced stylesheet updates. This
 * approach minimizes memory allocations by leveraging JavaFX's stylesheet caching instead of inline
 * style modifications.
 */
public final class DynamicFontSize {

    private static final double FONT_SIZE_RATIO = 0.0219;
    private static final String ROOT_FONT_CSS_PREFIX = "data:text/css,.root { -fx-font-size: ";
    private static final double EPSILON = 0.01;
    private final Scene scene;
    private double currentFontSize = -1;
    private int dynamicStylesheetIndex = -1;

    public double getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Creates a DynamicFontSize instance and attaches it to the specified Scene. Listeners are set
     * up to automatically update the font size on scene resize.
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
     * Sets up dynamic font resizing for the scene. Adds listeners to the scene's width and height
     * properties to trigger {@link #updateFontSize()} whenever the dimensions change. Also performs
     * an initial font size update.
     */
    private void initialize() {
        InvalidationListener listener = obs -> updateFontSize();
        scene.widthProperty().addListener(listener);
        scene.heightProperty().addListener(listener);
        updateFontSize();
    }

    /**
     * Updates the root font size of the scene using a dynamic stylesheet. Applies the new font size
     * only if it differs significantly from the current size. Leveraging JavaFX stylesheet caching,
     * this approach minimizes memory allocations compared to inline style updates.
     */
    public void updateFontSize() {
        double fontSize = Math.min(scene.getWidth(), scene.getHeight()) * FONT_SIZE_RATIO;
        if (Math.abs(fontSize - currentFontSize) < EPSILON) {
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
