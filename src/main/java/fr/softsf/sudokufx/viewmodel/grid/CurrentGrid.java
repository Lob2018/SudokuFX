/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Represents the current loaded Sudoku grid, including its difficulty level and the percentage of
 * possibilities associated with it.
 */
public record CurrentGrid(
        @Nonnull @NotNull(message = "level must not be null") DifficultyLevel level,
        @Min(value = 0, message = "percentage must be >= 0") @Max(value = 100, message = "percentage must be <= 100") int percentage) {}
