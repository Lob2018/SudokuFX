/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.model.Player;

/**
 * Utility class providing custom mapping methods for use with MapStruct.
 *
 * <p>Includes helper methods to map between entity IDs and partial entity instances, facilitating
 * reference mapping without full entity loading.
 */
public class MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

    /**
     * Converts a Player ID to a Player entity instance with only the ID set.
     *
     * <p>This is useful for mapping DTO references to entities without fetching full data.
     *
     * @param playerId the ID of the Player; may be null.
     * @return a Player entity with the given ID set, or null if the input ID is null.
     */
    @Named("mapPlayerIdToPlayer")
    public static Player mapPlayerIdToPlayer(Long playerId) {
        if (playerId == null) {
            log.warn("▓▓ Player ID is null, returning null Player entity.");
            return null;
        }
        return Player.builder().playerid(playerId).build();
    }
}
