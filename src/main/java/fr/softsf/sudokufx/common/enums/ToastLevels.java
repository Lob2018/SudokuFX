/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum for toast notification levels. */
public enum ToastLevels {
    INFO("toastInfo"),
    WARN("toastWarn"),
    ERROR("toastError");

    private final String level;

    ToastLevels(final String level) {
        this.level = level;
    }

    public final String getLevel() {
        return level;
    }
}
