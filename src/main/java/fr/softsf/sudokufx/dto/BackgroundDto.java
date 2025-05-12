/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BackgroundDto(
        Long backgroundid,
        @NotNull @Size(max = 8) String hexcolor,
        @Size(max = 260) String imagepath,
        @NotNull Boolean isimage) {}
