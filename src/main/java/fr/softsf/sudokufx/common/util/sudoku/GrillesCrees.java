/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.Objects;

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
public record GrillesCrees(
        @NotNull @Size(
                        min = 81,
                        max = 81,
                        message = "grilleResolue must contain exactly 81 elements")
                int[] grilleResolue,
        @NotNull @Size(
                        min = 81,
                        max = 81,
                        message = "grilleAResoudre must contain exactly 81 elements")
                int[] grilleAResoudre,
        @Min(value = 0, message = "pourcentageDesPossibilites must be >= 0") @Max(value = 100, message = "pourcentageDesPossibilites must be <= 100") int pourcentageDesPossibilites) {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrillesCrees(int[] resolue, int[] aResoudre, int desPossibilites))) {
            return false;
        }
        return pourcentageDesPossibilites == desPossibilites
                && Arrays.equals(grilleResolue, resolue)
                && Arrays.equals(grilleAResoudre, aResoudre);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(pourcentageDesPossibilites);
        result = 31 * result + Arrays.hashCode(grilleResolue);
        result = 31 * result + Arrays.hashCode(grilleAResoudre);
        return result;
    }

    @Override
    public String toString() {
        return "GrillesCrees["
                + "grilleResolue="
                + Arrays.toString(grilleResolue)
                + ", grilleAResoudre="
                + Arrays.toString(grilleAResoudre)
                + ", pourcentageDesPossibilites="
                + pourcentageDesPossibilites
                + ']';
    }
}
