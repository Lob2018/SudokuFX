/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object representing a menu entity.
 *
 * @param menuid the unique identifier of the menu
 * @param mode the mode of the menu, must be between 1 and 3 (inclusive); must not be null
 */
public record MenuDto(Byte menuid, @NotNull @Min(1) @Max(3) Byte mode) {}
