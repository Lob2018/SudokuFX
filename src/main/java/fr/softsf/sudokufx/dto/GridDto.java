/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
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
        @Min(0) @Max(100) byte possibilities) {}
