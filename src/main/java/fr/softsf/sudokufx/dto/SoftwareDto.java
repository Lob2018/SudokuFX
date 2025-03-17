package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record SoftwareDto(
        Long softwareid,
        @NotNull
        @Size(max = 128)
        String currentversion,
        @NotNull
        @Size(max = 128)
        String lastversion,
        @NotNull
        LocalDateTime createdat,
        @NotNull
        LocalDateTime updatedat
) {
}
