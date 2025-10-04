/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.Instant;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object representing a game entity.
 *
 * @param gameid the unique identifier of the game
 * @param grididDto the associated grid data transfer object; must not be null
 * @param playerid the identifier of the player linked to the game; must not be null
 * @param levelidDto the associated game level data transfer object; must not be null
 * @param selected flag indicating if the game is selected
 * @param createdat the timestamp when the game was created; must not be null
 * @param updatedat the timestamp when the game was last updated; must not be null
 */
public record GameDto(
        Long gameid,
        @Nonnull @NotNull GridDto grididDto,
        @Nonnull @NotNull Long playerid,
        @Nonnull @NotNull GameLevelDto levelidDto,
        boolean selected,
        @Nonnull @NotNull Instant createdat,
        @Nonnull @NotNull Instant updatedat) {

    /** Returns a new instance with the given game ID. */
    public GameDto withGameid(Long newGameid) {
        return new GameDto(
                newGameid, grididDto, playerid, levelidDto, selected, createdat, updatedat);
    }

    /** Returns a new instance with the given grid DTO. */
    public GameDto withGrididDto(GridDto newGrididDto) {
        return new GameDto(
                gameid, newGrididDto, playerid, levelidDto, selected, createdat, updatedat);
    }

    /** Returns a new instance with the given player ID. */
    public GameDto withPlayerid(Long newPlayerid) {
        return new GameDto(
                gameid, grididDto, newPlayerid, levelidDto, selected, createdat, updatedat);
    }

    /** Returns a new instance with the given level DTO. */
    public GameDto withLevelidDto(GameLevelDto newLevelidDto) {
        return new GameDto(
                gameid, grididDto, playerid, newLevelidDto, selected, createdat, updatedat);
    }

    /** Returns a new instance with the given selected flag. */
    public GameDto withSelected(boolean newSelected) {
        return new GameDto(
                gameid, grididDto, playerid, levelidDto, newSelected, createdat, updatedat);
    }

    /** Returns a new instance with the given creation timestamp. */
    public GameDto withCreatedat(Instant newCreatedat) {
        return new GameDto(
                gameid, grididDto, playerid, levelidDto, selected, newCreatedat, updatedat);
    }

    /** Returns a new instance with the given update timestamp. */
    public GameDto withUpdatedat(Instant newUpdatedat) {
        return new GameDto(
                gameid, grididDto, playerid, levelidDto, selected, createdat, newUpdatedat);
    }
}
