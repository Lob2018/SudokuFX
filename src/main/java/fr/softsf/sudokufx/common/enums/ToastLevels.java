/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
