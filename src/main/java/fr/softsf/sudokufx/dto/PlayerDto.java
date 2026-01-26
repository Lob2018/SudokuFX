/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.dto;

import java.time.Instant;

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
 * @param selected indicates whether this player is selected
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
        boolean selected,
        @Nonnull @NotNull Instant createdat,
        @Nonnull @NotNull Instant updatedat) {

    /** Returns a new instance with the given player language. */
    public PlayerDto withPlayerLanguage(PlayerLanguageDto newPlayerLanguage) {
        return new PlayerDto(
                playerid,
                newPlayerLanguage,
                optionsidDto,
                menuidDto,
                selectedGame,
                name,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given options. */
    public PlayerDto withOptions(OptionsDto newOptions) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                newOptions,
                menuidDto,
                selectedGame,
                name,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given menu. */
    public PlayerDto withMenu(MenuDto newMenu) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                newMenu,
                selectedGame,
                name,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given selected game. */
    public PlayerDto withSelectedGame(GameDto newSelectedGame) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                menuidDto,
                newSelectedGame,
                name,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given name. */
    public PlayerDto withName(String newName) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                menuidDto,
                selectedGame,
                newName,
                selected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given selected flag. */
    public PlayerDto withSelected(boolean newSelected) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                menuidDto,
                selectedGame,
                name,
                newSelected,
                createdat,
                updatedat);
    }

    /** Returns a new instance with the given creation timestamp. */
    public PlayerDto withCreatedAt(Instant newCreatedAt) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                menuidDto,
                selectedGame,
                name,
                selected,
                newCreatedAt,
                updatedat);
    }

    /** Returns a new instance with the given update timestamp. */
    public PlayerDto withUpdatedAt(Instant newUpdatedAt) {
        return new PlayerDto(
                playerid,
                playerlanguageidDto,
                optionsidDto,
                menuidDto,
                selectedGame,
                name,
                selected,
                createdat,
                newUpdatedAt);
    }
}
