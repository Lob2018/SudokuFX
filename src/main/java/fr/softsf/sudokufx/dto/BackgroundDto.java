package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BackgroundDto(
        Long backgroundid,
        @NotNull
        @Size(max = 8)
        String hexcolor,
        @Size(max = 260)
        String imagepath,
        @NotNull
        Boolean isimage
) {
}
