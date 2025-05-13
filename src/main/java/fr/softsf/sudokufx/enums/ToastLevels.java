/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.enums;

/** Utility enum for toast notification levels. */
public enum ToastLevels {
    INFO("toast-info"),
    WARN("toast-warn"),
    ERROR("toast-error");

    private final String level;

    ToastLevels(final String level_) {
        level = level_;
    }

    public final String getLevel() {
        return level;
    }
}
