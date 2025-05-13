/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.components.toaster;

import javafx.scene.control.Button;

/** A custom button with a display text and a full detail text. */
public class ToasterButton extends Button {

    private String fullDetailText;

    public String getFullDetailText() {
        return fullDetailText;
    }

    public void setFullDetailText(String fullDetailText) {
        this.fullDetailText = fullDetailText;
    }

    /**
     * Creates a ToasterButton.
     *
     * @param text Text displayed on the button.
     * @param fullDetailText Separate, complete detailed text.
     */
    public ToasterButton(String text, String fullDetailText) {
        super(text);
        this.fullDetailText = fullDetailText;
    }
}
