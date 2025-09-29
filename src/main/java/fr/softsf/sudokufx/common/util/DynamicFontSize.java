/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.util.Duration;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Manages dynamic font sizing for a JavaFX Scene with debounce. Adjusts the font size of the
 * Scene's root node when the Scene's dimensions change, consolidating rapid updates to reduce
 * memory pressure. Updates are only applied if the style differs from the last applied value.
 */
public final class DynamicFontSize {

    private static final double FONT_SIZE_RATIO = 0.0219;
    private static final Duration DEBOUNCE_DELAY = Duration.millis(500);
    private final Scene scene;
    private final PauseTransition debounce;
    private double currentFontSize;
    private String lastStyle = "";

    public double getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Constructs a DynamicFontSize instance and attaches listeners to the given Scene.
     *
     * @param scene the JavaFX Scene to observe; must not be null
     * @throws IllegalArgumentException if the scene is null, with logging performed via
     *     ExceptionTools
     */
    public DynamicFontSize(Scene scene) {
        if (Objects.isNull(scene)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The scene mustn't be null");
        }
        this.scene = scene;
        debounce = new PauseTransition(DEBOUNCE_DELAY);
        debounce.setOnFinished(e -> applyFontSize());
        initialize();
    }

    /**
     * Initializes the dynamic font size functionality. This method sets up listeners for the
     * scene's width and height properties. When the scene's dimensions change, the {@link
     * #updateFontSize()} method is called to schedule a font size update.
     */
    private void initialize() {
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Schedules a font size update based on the current Scene dimensions. This version uses a
     * debounce mechanism: rapid consecutive calls will delay the actual update, applying it only
     * after {@link #DEBOUNCE_DELAY} milliseconds have passed since the last call. This avoids
     * excessive memory allocation when resizing the window.
     */
    public void updateFontSize() {
        debounce.stop();
        debounce.playFromStart();
    }

    /**
     * Calculates and applies the new font size to the Scene's root node. If the resulting CSS style
     * is identical to the last applied one, no update occurs. This caching mechanism reduces
     * unnecessary Node.setStyle calls and mitigates Old Gen pressure during rapid resizing.
     */
    private void applyFontSize() {
        double fontSize = Math.min(scene.getWidth(), scene.getHeight()) * FONT_SIZE_RATIO;
        String style = "-fx-font-size: " + fontSize + "px;";
        if (style.equals(lastStyle)) {
            return;
        }
        lastStyle = style;
        scene.getRoot().setStyle(style);
        currentFontSize = fontSize;
    }
}
