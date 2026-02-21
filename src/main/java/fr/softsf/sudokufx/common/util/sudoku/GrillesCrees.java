/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static java.util.Objects.requireNonNull;

/**
 * Immutable record representing the dual output of a Sudoku grid generation.
 *
 * <p>Holds both the complete solution and the initial puzzle state. Ensures state integrity through
 * a fail-fast canonical constructor and defensive cloning of internal arrays to prevent
 * representation exposure (SpotBugs EI_EXPOSE_REP).
 *
 * @param grilleResolue The fully solved Sudoku grid (81 elements, values 0-9).
 * @param grilleAResoudre The initial puzzle grid with holes (81 elements, values 0-9).
 * @param pourcentageDesPossibilites Resolution progress or difficulty estimate (0-100), or -1 if
 *     the grid generation failed (e.g., watchdog timeout).
 */
public record GrillesCrees(
        @Nonnull @NotNull(message = "grilleResolue must not be null") @Size(min = 81, max = 81) int[] grilleResolue,
        @Nonnull @NotNull(message = "grilleAResoudre must not be null") @Size(min = 81, max = 81) int[] grilleAResoudre,
        @Min(value = -1, message = "possibilityPercentage must be >= -1") @Max(value = 100, message = "possibilityPercentage must be <= 100") int pourcentageDesPossibilites) {

    private static final Logger LOG = LoggerFactory.getLogger(GrillesCrees.class);
    private static final int GRID_SIZE = 81;

    /**
     * Canonical constructor with deep validation and defensive cloning.
     *
     * <p>Validates both grids for nullity, length, and value ranges. Logs failures with a "██"
     * marker before throwing {@link IllegalArgumentException}.
     *
     * @param grilleResolue The solved grid to be cloned.
     * @param grilleAResoudre The puzzle grid to be cloned.
     * @param pourcentageDesPossibilites Progress percentage.
     * @throws IllegalArgumentException if any grid validation fails.
     */
    public GrillesCrees(
            int[] grilleResolue, int[] grilleAResoudre, int pourcentageDesPossibilites) {
        this.pourcentageDesPossibilites = pourcentageDesPossibilites;
        validateGrid(grilleResolue, "grilleResolue");
        validateGrid(grilleAResoudre, "grilleAResoudre");
        this.grilleResolue = requireNonNull(grilleResolue).clone();
        this.grilleAResoudre = requireNonNull(grilleAResoudre).clone();
    }

    /**
     * Performs fail-fast validation on a grid array.
     *
     * @param grid The array to validate.
     * @param fieldName Name of the field for error reporting.
     */
    private void validateGrid(int[] grid, String fieldName) {
        Optional.ofNullable(grid)
                .map(
                        g ->
                                (g.length != GRID_SIZE)
                                        ? Optional.of(
                                                "must contain exactly " + GRID_SIZE + " elements")
                                        : Arrays.stream(g)
                                                .filter(v -> v < 0 || v > 9)
                                                .mapToObj(v -> "contains invalid value: " + v)
                                                .findFirst())
                .orElseGet(() -> Optional.of("must not be null"))
                .ifPresent(
                        error -> {
                            IllegalArgumentException exc =
                                    new IllegalArgumentException(
                                            "Invalid " + fieldName + ": " + error);
                            LOG.error("██ Exception in GrillesCrees: {}", exc.getMessage(), exc);
                            throw exc;
                        });
    }

    /**
     * Returns a defensive copy of the solved grid.
     *
     * @return A clone of {@code grilleResolue}.
     */
    @Override
    public int[] grilleResolue() {
        return grilleResolue.clone();
    }

    /**
     * Returns a defensive copy of the puzzle grid.
     *
     * @return A clone of {@code grilleAResoudre}.
     */
    @Override
    public int[] grilleAResoudre() {
        return grilleAResoudre.clone();
    }

    /**
     * Compares equality based on components and array contents via {@link Arrays#equals}.
     *
     * @param o The object to compare with this instance.
     * @return {@code true} if both records hold equivalent data and array contents.
     */
    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof GrillesCrees that
                        && pourcentageDesPossibilites == that.pourcentageDesPossibilites
                        && Arrays.equals(grilleResolue, that.grilleResolue)
                        && Arrays.equals(grilleAResoudre, that.grilleAResoudre));
    }

    /**
     * Returns a hash code based on all components and array contents.
     *
     * @return Content-based hash code.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(pourcentageDesPossibilites);
        result = 31 * result + Arrays.hashCode(grilleResolue);
        result = 31 * result + Arrays.hashCode(grilleAResoudre);
        return result;
    }

    /**
     * Returns a string representation including array contents via {@link Arrays#toString}.
     *
     * @return Formatted string containing all fields and grid values.
     */
    @Nonnull
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
