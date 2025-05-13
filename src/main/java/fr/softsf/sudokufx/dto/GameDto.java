/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record GameDto(
        Long gameid,
        @NotNull GridDto grididDto,
        @NotNull PlayerDto playeridDto,
        @NotNull GameLevelDto levelidDto,
        @NotNull Boolean isselected,
        @NotNull LocalDateTime createdat,
        @NotNull LocalDateTime updatedat) {}
