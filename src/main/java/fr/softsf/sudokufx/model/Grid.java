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

@Entity
@Table(name = "grid")
public class Grid {

    private static final String EMPTY_GRID = "";
    private static final String DEFAULTGRIDVALUE_MUST_NOT_BE_NULL =
            "defaultgridvalue must not be null";
    private static final String GRIDVALUE_MUST_NOT_BE_NULL = "gridvalue must not be null";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gridid", nullable = false)
    private Long gridid;

    @Nonnull
    @NotNull @Size(max = 81) @Column(name = "defaultgridvalue", nullable = false, length = 81)
    private String defaultgridvalue = EMPTY_GRID;

    @Nonnull
    @NotNull @Size(max = 810) @Column(name = "gridvalue", nullable = false, length = 810)
    private String gridvalue = EMPTY_GRID;

    @Min(0) @Max(100) @Column(name = "possibilities", nullable = false)
    private byte possibilities;

    protected Grid() {}

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

    public Long getGridid() {
        return gridid;
    }

    @Nonnull
    public String getDefaultgridvalue() {
        return defaultgridvalue;
    }

    @Nonnull
    public String getGridvalue() {
        return gridvalue;
    }

    public byte getPossibilities() {
        return possibilities;
    }

    public void setDefaultgridvalue(@Nonnull String defaultgridvalue) {
        this.defaultgridvalue = validateDefaultgridvalue(defaultgridvalue);
    }

    public void setGridvalue(@Nonnull String gridvalue) {
        this.gridvalue = validateGridvalue(gridvalue);
    }

    public void setPossibilities(byte possibilities) {
        this.possibilities = possibilities;
    }

    public static GridBuilder builder() {
        return new GridBuilder();
    }

    /**
     * Builder class for creating instances of {@link Grid}.
     *
     * <p>Provides a fluent API to set all fields before constructing an instance of {@code Grid}.
     * Validation occurs at build() to avoid exceptions during construction.
     */
    public static class GridBuilder {
        private Long gridid;
        private String defaultgridvalue = EMPTY_GRID;
        private String gridvalue = EMPTY_GRID;
        private byte possibilities;

        public GridBuilder gridid(Long gridid) {
            this.gridid = gridid;
            return this;
        }

        public GridBuilder defaultgridvalue(@Nonnull String defaultgridvalue) {
            this.defaultgridvalue = defaultgridvalue;
            return this;
        }

        public GridBuilder gridvalue(@Nonnull String gridvalue) {
            this.gridvalue = gridvalue;
            return this;
        }

        public GridBuilder possibilities(byte possibilities) {
            this.possibilities = possibilities;
            return this;
        }

        /**
         * Creates Grid instance with parameter validation.
         *
         * @return new validated Grid instance
         * @throws NullPointerException if required parameters are null
         */
        public Grid build() {
            return new Grid(gridid, defaultgridvalue, gridvalue, possibilities);
        }
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(gridid, defaultgridvalue, gridvalue, possibilities);
    }

    @Override
    public String toString() {
        return String.format(
                "Grid{gridid=%s, defaultgridvalue='%s', gridvalue='%s', possibilities=%d}",
                gridid, defaultgridvalue, gridvalue, possibilities);
    }
}
