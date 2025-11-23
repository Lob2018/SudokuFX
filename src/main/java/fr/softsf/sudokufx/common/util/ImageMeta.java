/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

/**
 * Represents basic metadata of an image (width, height) along with the scale factor needed to fit
 * the image into the target grid pane.
 */
public record ImageMeta(int width, int height, double scaleFactor) {}
