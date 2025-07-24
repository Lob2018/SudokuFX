/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Record representing the result of solving a Sudoku grid.
 *
 * <p>Contains:
 *
 * <ul>
 *   <li>{@code solved}: whether the grid was successfully solved
 *   <li>{@code solvedGrid}: the solved grid or partially filled grid if no complete solution was
 *       found
 *   <li>{@code possibilityPercentage}: percentage of possibilities, between 0 and 100, estimating
 *       coherence or difficulty
 * </ul>
 */
public record GrilleResolue(
        boolean solved,
        @NotNull(message = "solvedGrid must not be null") @Size(min = 81, max = 81, message = "solvedGrid must contain exactly 81 elements") int[] solvedGrid,
        @Min(value = 0, message = "possibilityPercentage must be >= 0") @Max(value = 100, message = "possibilityPercentage must be <= 100") int possibilityPercentage) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o
                instanceof
                GrilleResolue(boolean solved, int[] solvedGrid, int possibilityPercentage))) {
            return false;
        }
        return this.solved == solved
                && this.possibilityPercentage == possibilityPercentage
                && Arrays.equals(this.solvedGrid, solvedGrid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(solved, possibilityPercentage);
        result = 31 * result + Arrays.hashCode(solvedGrid);
        return result;
    }

    @Override
    public @Nonnull String toString() {
        return "SolvedGrid["
                + "solved="
                + solved
                + ", solvedGrid="
                + Arrays.toString(solvedGrid)
                + ", possibilityPercentage="
                + possibilityPercentage
                + ']';
    }
}
