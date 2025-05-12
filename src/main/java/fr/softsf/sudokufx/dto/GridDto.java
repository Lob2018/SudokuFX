/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
