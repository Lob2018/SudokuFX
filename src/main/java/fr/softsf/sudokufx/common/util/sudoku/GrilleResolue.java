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
 * Immutable record representing a Sudoku grid resolution result.
 *
 * <p>Ensures data integrity through a fail-fast canonical constructor and defensive cloning to
 * prevent representation exposure (SpotBugs EI_EXPOSE_REP).
 *
 * @param solved True if the resolution was successful.
 * @param solvedGrid Non-null array of {@value #CASES_NUMBER} elements [0-9].
 * @param possibilityPercentage Resolution progress estimate (0-100).
 */
public record GrilleResolue(
        boolean solved,
        @Nonnull
                @NotNull(message = "solvedGrid must not be null") @Size(
                        min = CASES_NUMBER,
                        max = CASES_NUMBER,
                        message = "solvedGrid must contain exactly 81 elements")
                int[] solvedGrid,
        @Min(value = 0, message = "possibilityPercentage must be >= 0") @Max(value = 100, message = "possibilityPercentage must be <= 100") int possibilityPercentage) {

    private static final Logger LOG = LoggerFactory.getLogger(GrilleResolue.class);
    public static final int CASES_NUMBER = 81;

    /**
     * Canonical constructor with deep validation and defensive cloning.
     *
     * <p>Performs a fail-fast check on {@code solvedGrid} integrity. Any violation (nullity,
     * length, or range) is logged with a "██" marker before throwing an {@link
     * IllegalArgumentException}.
     *
     * @param solved Resolution status.
     * @param solvedGrid The grid array to be cloned.
     * @param possibilityPercentage Progress percentage (0-100).
     * @throws IllegalArgumentException if {@code solvedGrid} is null, has an invalid length, or
     *     contains values outside the [0-9] range.
     */
    public GrilleResolue(boolean solved, int[] solvedGrid, int possibilityPercentage) {
        this.solved = solved;
        this.possibilityPercentage = possibilityPercentage;
        Optional.ofNullable(solvedGrid)
                .map(
                        g ->
                                (g.length != CASES_NUMBER)
                                        ? Optional.of(
                                                "must contain exactly "
                                                        + CASES_NUMBER
                                                        + " elements (found: "
                                                        + g.length
                                                        + ")")
                                        : Arrays.stream(g)
                                                .filter(v -> v < 0 || v > 9)
                                                .mapToObj(v -> "contains invalid value: " + v)
                                                .findFirst())
                .orElseGet(() -> Optional.of("must not be null"))
                .ifPresent(
                        error -> {
                            IllegalArgumentException exc =
                                    new IllegalArgumentException("Invalid solvedGrid: " + error);
                            LOG.error("██ Exception in GrilleResolue: {}", exc.getMessage(), exc);
                            throw exc;
                        });
        this.solvedGrid = requireNonNull(solvedGrid).clone();
    }

    /**
     * Returns a defensive copy of the solved grid.
     *
     * @return A clone of {@code solvedGrid}.
     */
    @Override
    public int[] solvedGrid() {
        return solvedGrid.clone();
    }

    /**
     * Compares equality based on components and array content via {@link Arrays#equals}.
     *
     * @param o The object to compare.
     * @return True if all components and grid contents are equal.
     */
    @Override
    public boolean equals(Object o) {
        return this == o
                || (o instanceof GrilleResolue(boolean s, int[] g, int p)
                        && solved == s
                        && possibilityPercentage == p
                        && Arrays.equals(solvedGrid, g));
    }

    /**
     * Returns a hash code based on all components and array content.
     *
     * @return Content-based hash code.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(solved, possibilityPercentage);
        result = 31 * result + Arrays.hashCode(solvedGrid);
        return result;
    }

    /**
     * Returns a string representation of the record, including the content of the grid.
     *
     * @return String representation with array contents.
     */
    @Nonnull
    @Override
    public String toString() {
        return "GrilleResolue["
                + "solved="
                + solved
                + ", "
                + "solvedGrid="
                + Arrays.toString(solvedGrid)
                + ", "
                + "possibilityPercentage="
                + possibilityPercentage
                + ']';
    }
}
