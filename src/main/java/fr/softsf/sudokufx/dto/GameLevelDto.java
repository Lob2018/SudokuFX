/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Data Transfer Object representing a game level entity.
 *
 * @param levelid the unique identifier of the game level
 * @param level the difficulty level, constrained between 1 and 3 (inclusive)
 */
public record GameLevelDto(Byte levelid, @Min(1) @Max(3) byte level) {}
