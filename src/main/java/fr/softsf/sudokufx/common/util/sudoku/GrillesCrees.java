/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Record representing the result of a grid generation: the solved grid, the puzzle grid, and the
 * estimated possibility percentage.
 *
 * <p>Line="33" is excluded from Checkstyle's LineLength check (see checkstyle-suppressions.xml).
 */
@SuppressWarnings("java:S6218")
public record GrillesCrees(
        @Nonnull
                @NotNull(message = "grilleResolue must not be null") @Size(
                        min = 81,
                        max = 81,
                        message = "grilleResolue must contain exactly 81 elements")
                int[] grilleResolue,
        @Nonnull
                @NotNull(message = "grilleAResoudre must not be null") @Size(
                        min = 81,
                        max = 81,
                        message = "grilleAResoudre must contain exactly 81 elements")
                int[] grilleAResoudre,
        @Min(value = 0, message = "pourcentageDesPossibilites must be >= 0") @Max(value = 100, message = "pourcentageDesPossibilites must be <= 100") int pourcentageDesPossibilites) {}
