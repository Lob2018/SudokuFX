/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import java.util.Set;

import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.model.Game;
import fr.softsf.sudokufx.model.Player;

/** Utility class providing custom mapping methods for use with MapStruct. */
public final class MapperUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MapperUtils.class);

    private MapperUtils() {
        LOG.error("██ Attempted to instantiate MapperUtils utility class.");
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

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
            LOG.warn("▓▓ Player ID is null, returning null Player entity.");
            return null;
        }
        return Player.builder().playerid(playerId).build();
    }

    /**
     * Returns the first Game from the given Set assumed to contain only selected games. Returns
     * null if the Set is null or empty. Ordering is not guaranteed.
     *
     * @param games Set of selected Game entities, may be null or empty
     * @return first Game in the Set, or null
     */
    @Named("mapSelectedGame")
    public static Game mapSelectedGame(Set<Game> games) {
        if (games == null || games.isEmpty()) {
            LOG.warn("▓▓ No selected games found (input is null or empty). Returning null.");
            return null;
        }
        return games.iterator().next();
    }
}
