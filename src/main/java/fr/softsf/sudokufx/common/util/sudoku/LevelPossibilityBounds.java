/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

/**
 * Immutable container for level-specific possibility boundaries.
 *
 * @param min the lower bound of the current level
 * @param max the upper bound of the current level
 */
public record LevelPossibilityBounds(int min, int max) {}
