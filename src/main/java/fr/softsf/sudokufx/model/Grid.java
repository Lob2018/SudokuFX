/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.util.Objects;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents a Sudoku grid with default values, current values, and the number of possibilities.
 *
 * <p>Provides getters, setters, validation, and a builder for fluent construction.
 */
@Entity
@Table(name = "grid")
public class Grid {

    private static final String EMPTY_GRID = "";
    private static final String DEFAULTGRIDVALUE_MUST_NOT_BE_NULL =
            "defaultgridvalue must not be null";
    private static final String GRIDVALUE_MUST_NOT_BE_NULL = "gridvalue must not be null";

    /** Unique identifier of the grid (primary key). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gridid", nullable = false)
    private Long gridid;

    /** Default values of the grid (length up to 81). */
    @Nonnull
    @NotNull @Size(max = 81) @Column(name = "defaultgridvalue", nullable = false, length = 81)
    private String defaultgridvalue = EMPTY_GRID;

    /** Current values of the grid (length up to 810). */
    @Nonnull
    @NotNull @Size(max = 810) @Column(name = "gridvalue", nullable = false, length = 810)
    private String gridvalue = EMPTY_GRID;

    /** Percentage of possibilities for this grid, between 0 and 100. */
    @Min(0) @Max(100) @Column(name = "possibilities", nullable = false)
    private byte possibilities;

    /** Protected default constructor for JPA. */
    protected Grid() {}

    /**
     * Full constructor to initialize a grid with all fields.
     *
     * @param gridid the unique ID of the grid
     * @param defaultgridvalue the default grid values
     * @param gridvalue the current grid values
     * @param possibilities the percentage of possibilities (0–100)
     */
    public Grid(
            Long gridid,
            @Nonnull @NotNull String defaultgridvalue,
            @Nonnull @NotNull String gridvalue,
            byte possibilities) {
        this.gridid = gridid;
        this.defaultgridvalue = validateDefaultgridvalue(defaultgridvalue);
        this.gridvalue = validateGridvalue(gridvalue);
        this.possibilities = possibilities;
    }

    /**
     * Validates that the default grid value is not null.
     *
     * @param defaultgridvalue the default grid value to validate
     * @return the validated default grid value
     * @throws NullPointerException if the default grid value is null
     */
    private static String validateDefaultgridvalue(String defaultgridvalue) {
        return Objects.requireNonNull(defaultgridvalue, DEFAULTGRIDVALUE_MUST_NOT_BE_NULL);
    }

    /**
     * Validates that the grid value is not null.
     *
     * @param gridvalue the grid value to validate
     * @return the validated grid value
     * @throws NullPointerException if the grid value is null
     */
    private static String validateGridvalue(String gridvalue) {
        return Objects.requireNonNull(gridvalue, GRIDVALUE_MUST_NOT_BE_NULL);
    }

    /** Returns the unique identifier of the grid. */
    public Long getGridid() {
        return gridid;
    }

    /** Returns the default grid values. */
    @Nonnull
    public String getDefaultgridvalue() {
        return defaultgridvalue;
    }

    /** Returns the current grid values. */
    @Nonnull
    public String getGridvalue() {
        return gridvalue;
    }

    /** Returns the percentage of possibilities for this grid. */
    public byte getPossibilities() {
        return possibilities;
    }

    /** Sets the default grid values. */
    public void setDefaultgridvalue(@Nonnull String defaultgridvalue) {
        this.defaultgridvalue = validateDefaultgridvalue(defaultgridvalue);
    }

    /** Sets the current grid values. */
    public void setGridvalue(@Nonnull String gridvalue) {
        this.gridvalue = validateGridvalue(gridvalue);
    }

    /** Sets the percentage of possibilities for this grid. */
    public void setPossibilities(byte possibilities) {
        this.possibilities = possibilities;
    }

    /** Creates a new {@link GridBuilder} for fluent construction of Grid instances. */
    public static GridBuilder builder() {
        return new GridBuilder();
    }

    /**
     * Builder class for creating {@link Grid} instances.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code Grid}.
     * Validation occurs at build() to ensure non-null fields.
     */
    public static class GridBuilder {
        private Long gridid;
        private String defaultgridvalue = EMPTY_GRID;
        private String gridvalue = EMPTY_GRID;
        private byte possibilities;

        /** Sets the unique ID for the grid. */
        public GridBuilder gridid(Long gridid) {
            this.gridid = gridid;
            return this;
        }

        /** Sets the default grid values. */
        public GridBuilder defaultgridvalue(@Nonnull String defaultgridvalue) {
            this.defaultgridvalue = defaultgridvalue;
            return this;
        }

        /** Sets the current grid values. */
        public GridBuilder gridvalue(@Nonnull String gridvalue) {
            this.gridvalue = gridvalue;
            return this;
        }

        /** Sets the percentage of possibilities for the grid. */
        public GridBuilder possibilities(byte possibilities) {
            this.possibilities = possibilities;
            return this;
        }

        /**
         * Builds a new {@link Grid} instance with validation.
         *
         * @return a fully constructed and validated {@code Grid} instance
         * @throws NullPointerException if required fields are null
         */
        public Grid build() {
            return new Grid(gridid, defaultgridvalue, gridvalue, possibilities);
        }
    }

    /** Compares this grid with another object for equality based on all fields. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Grid other) {
            return possibilities == other.possibilities
                    && Objects.equals(gridid, other.gridid)
                    && Objects.equals(defaultgridvalue, other.defaultgridvalue)
                    && Objects.equals(gridvalue, other.gridvalue);
        }
        return false;
    }

    /** Computes the hash code based on all fields. */
    @Override
    public int hashCode() {
        return Objects.hash(gridid, defaultgridvalue, gridvalue, possibilities);
    }

    /** Returns a string representation of the grid. */
    @Override
    public String toString() {
        return String.format(
                "Grid{gridid=%s, defaultgridvalue='%s', gridvalue='%s', possibilities=%d}",
                gridid, defaultgridvalue, gridvalue, possibilities);
    }
}
