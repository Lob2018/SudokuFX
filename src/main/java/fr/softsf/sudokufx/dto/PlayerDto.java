package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public record PlayerDto(
        Long playerid,
        @NotNull
        PlayerLanguageDto playerlanguageidDto,
        @NotNull
        BackgroundDto backgroundidDto,
        @NotNull
        MenuDto menuidDto,
        @NotNull
        Set<GameDto> gamesDto,
        @NotNull
        @Size(max = 256)
        String name,
        @NotNull
        Boolean isselected,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
    public PlayerDto {
        if (gamesDto == null) {
            gamesDto = new LinkedHashSet<>();
        }
    }
}
