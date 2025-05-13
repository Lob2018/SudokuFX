/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.utils;

import javafx.scene.Scene;

/**
 * Manages dynamic font sizing based on the dimensions of a JavaFX Scene. This class automatically
 * adjusts the font size of the Scene's root node when the Scene's dimensions change.
 */
public final class DynamicFontSize {

    private final Scene scene;

    /** The current font size calculated based on the Scene's dimensions. */
    private double currentFontSize;

    public double getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Constructs a DynamicFontSize instance and initializes Scene listeners for testing purposes
     * only.
     */
    public DynamicFontSize(Scene scene) {
        this.scene = scene;
        initialize();
    }

    /**
     * Initializes the dynamic font size functionality. This method sets up listeners for the
     * scene's width and height properties. When the scene's dimensions change, the {@link
     * #updateFontSize()} method is called to adjust the font size dynamically.
     */
    private void initialize() {
        scene.widthProperty().addListener((obs, oldW, newW) -> updateFontSize());
        scene.heightProperty().addListener((obs, oldH, newH) -> updateFontSize());
    }

    /**
     * Updates the font size based on the current Scene dimensions. The font size is calculated as
     * 2.19% of the smaller dimension (width or height) of the Scene. This method is called
     * automatically when the Scene's dimensions change.
     */
    public void updateFontSize() {
        currentFontSize = Math.min(scene.getWidth(), scene.getHeight()) * 0.0219;
        scene.getRoot().setStyle("-fx-font-size: " + currentFontSize + "px;");
    }
}
