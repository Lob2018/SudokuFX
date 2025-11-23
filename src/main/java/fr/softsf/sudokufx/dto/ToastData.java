/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import fr.softsf.sudokufx.common.enums.ToastLevels;

/** A data transfer object representing a toast notification. */
public record ToastData(
        String visibleText, String detailedText, ToastLevels level, boolean requestFocus) {}
