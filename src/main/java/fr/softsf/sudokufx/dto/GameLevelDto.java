/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
public record GameLevelDto(Byte levelid, @Min(1) @Max(3) byte level) {

    /**
     * Returns a new GameLevelDto with the specified levelid, keeping the current level.
     *
     * @param levelid the new levelid
     * @return a new GameLevelDto with updated levelid
     */
    public GameLevelDto withLevelid(Byte levelid) {
        return new GameLevelDto(levelid, this.level);
    }

    /**
     * Returns a new GameLevelDto with the specified level, keeping the current levelid.
     *
     * @param level the new level (1-3)
     * @return a new GameLevelDto with updated level
     */
    public GameLevelDto withLevel(byte level) {
        return new GameLevelDto(this.levelid, level);
    }
}
