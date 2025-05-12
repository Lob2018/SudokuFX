/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.enums;

import javafx.stage.Screen;

/** Utility enum for screen size */
public enum ScreenSize {
    DISPOSABLE_SIZE(
            Math.min(
                    Screen.getPrimary().getVisualBounds().getWidth(),
                    Screen.getPrimary().getVisualBounds().getHeight())),
    VISUAL_WIDTH(Screen.getPrimary().getVisualBounds().getWidth()),
    VISUAL_HEIGHT(Screen.getPrimary().getVisualBounds().getHeight());

    private final Double size;

    ScreenSize(Double size) {
        this.size = size;
    }

    public Double getSize() {
        return size;
    }
}
