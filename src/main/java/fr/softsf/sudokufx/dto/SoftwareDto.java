/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
