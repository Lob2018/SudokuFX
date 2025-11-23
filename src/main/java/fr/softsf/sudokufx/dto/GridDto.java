/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a grid entity.
 *
 * @param gridid the unique identifier of the grid
 * @param defaultgridvalue the default grid value string, max length 81; must not be null
 * @param gridvalue the current grid value string, max length 810; must not be null
 * @param possibilities the percentage of possibilities (0 to 100)
 */
public record GridDto(
        Long gridid,
        @Nonnull @NotNull @Size(max = 81) String defaultgridvalue,
        @Nonnull @NotNull @Size(max = 810) String gridvalue,
        @Min(0) @Max(100) byte possibilities) {

    /** Returns a new instance with the given grid ID. */
    public GridDto withGridid(Long newGridid) {
        return new GridDto(newGridid, defaultgridvalue, gridvalue, possibilities);
    }

    /** Returns a new instance with the given default grid value. */
    public GridDto withDefaultgridvalue(String newDefaultgridvalue) {
        return new GridDto(gridid, newDefaultgridvalue, gridvalue, possibilities);
    }

    /** Returns a new instance with the given current grid value. */
    public GridDto withGridvalue(String newGridvalue) {
        return new GridDto(gridid, defaultgridvalue, newGridvalue, possibilities);
    }

    /** Returns a new instance with the given possibilities percentage. */
    public GridDto withPossibilities(byte newPossibilities) {
        return new GridDto(gridid, defaultgridvalue, gridvalue, newPossibilities);
    }
}
