/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BackgroundDto(
        Long backgroundid,
        @NotNull @Size(max = 8) String hexcolor,
        @Size(max = 260) String imagepath,
        @NotNull Boolean isimage) {}
