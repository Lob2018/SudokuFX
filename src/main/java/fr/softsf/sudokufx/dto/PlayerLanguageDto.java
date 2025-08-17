/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Data Transfer Object representing a player's language.
 *
 * @param playerlanguageid the unique identifier of the player language
 * @param iso the ISO code; must be "FR" or "EN" and not null
 */
public record PlayerLanguageDto(
        Long playerlanguageid,
        @Nonnull
                @NotNull @Pattern(regexp = "^(FR|EN)$", message = "iso must be either 'FR' or 'EN'")
                String iso) {}
