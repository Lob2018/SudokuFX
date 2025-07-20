/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a player's language.
 *
 * @param playerlanguageid the unique identifier of the player language
 * @param iso the ISO code of the language, must not be null and maximum length 2 characters
 */
public record PlayerLanguageDto(Long playerlanguageid, @NotNull @Size(max = 2) String iso) {}
