/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
