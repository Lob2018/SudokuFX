/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object representing a game entity.
 *
 * @param gameid the unique identifier of the game
 * @param grididDto the associated grid data transfer object; must not be null
 * @param playerid the identifier of the player linked to the game; must not be null
 * @param levelidDto the associated game level data transfer object; must not be null
 * @param isselected flag indicating if the game is selected; must not be null
 * @param createdat the timestamp when the game was created; must not be null
 * @param updatedat the timestamp when the game was last updated; must not be null
 */
public record GameDto(
        Long gameid,
        @NotNull GridDto grididDto,
        @NotNull Long playerid,
        @NotNull GameLevelDto levelidDto,
        @NotNull Boolean isselected,
        @NotNull LocalDateTime createdat,
        @NotNull LocalDateTime updatedat) {}
