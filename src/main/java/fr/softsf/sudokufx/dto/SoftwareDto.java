/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SoftwareDto(
        Long softwareid,
        @NotNull @Size(max = 128) String currentversion,
        @NotNull @Size(max = 128) String lastversion,
        @NotNull LocalDateTime createdat,
        @NotNull LocalDateTime updatedat) {}
