/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
        @NotNull Set<GameDto> gamesDto,
        @NotNull @Size(max = 256) String name,
        @NotNull Boolean isselected,
        @NotNull LocalDateTime createdat,
        @NotNull LocalDateTime updatedat) {
    public PlayerDto {
        if (gamesDto == null) {
            gamesDto = new LinkedHashSet<>();
        }
    }
}
