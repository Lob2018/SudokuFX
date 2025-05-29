/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlayerDto(
        Long playerid,
        @NotNull PlayerLanguageDto playerlanguageidDto,
        @NotNull BackgroundDto backgroundidDto,
        @NotNull MenuDto menuidDto,
        @NotNull Set<Long> gamesid,
        @NotNull @Size(max = 256) String name,
        @NotNull Boolean isselected,
        @NotNull LocalDateTime createdat,
        @NotNull LocalDateTime updatedat) {
    public PlayerDto {
        if (gamesid == null) {
            gamesid = new LinkedHashSet<>();
        }
    }
}
