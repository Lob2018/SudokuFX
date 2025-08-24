/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object representing a player.
 *
 * @param playerid the unique identifier of the player
 * @param playerlanguageidDto the player's language data, must not be null
 * @param optionsidDto the player's options data, must not be null
 * @param menuidDto the menu associated with the player, must not be null
 * @param selectedGame the currently selected game for the player, can be null
 * @param name the player's name, must not be null and maximum length 256 characters
 * @param isselected indicates whether this player is selected
 * @param createdat the creation timestamp, must not be null
 * @param updatedat the last update timestamp, must not be null
 */
public record PlayerDto(
        Long playerid,
        @Nonnull @NotNull PlayerLanguageDto playerlanguageidDto,
        @Nonnull @NotNull OptionsDto optionsidDto,
        @Nonnull @NotNull MenuDto menuidDto,
        @Nullable GameDto selectedGame,
        @Nonnull @NotNull @Size(max = 256) String name,
        boolean isselected,
        @Nonnull @NotNull LocalDateTime createdat,
        @Nonnull @NotNull LocalDateTime updatedat) {}
