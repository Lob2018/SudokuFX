/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GridDto(
        Long gridid,
        @NotNull @Size(max = 81) String defaultgridvalue,
        @NotNull @Size(max = 810) String gridvalue,
        @NotNull @Min(0) @Max(100) Byte possibilities) {}
