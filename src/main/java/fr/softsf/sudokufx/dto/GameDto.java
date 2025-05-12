/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
