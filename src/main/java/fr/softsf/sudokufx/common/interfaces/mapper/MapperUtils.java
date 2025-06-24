/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import java.util.Objects;
import java.util.Set;

import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

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
     * Maps a Player ID to a Player entity with only the ID set. Returns null if the ID is null.
     *
     * @param playerId the Player ID, may be null
     * @return Player entity or null
     */
    @Named("mapPlayerIdToPlayer")
    public static Player mapPlayerIdToPlayer(Long playerId) {
        if (Objects.isNull(playerId)) {
            LOG.warn("▓▓ Player ID is null, returning null Player entity.");
            return null;
        }
        return Player.builder().playerid(playerId).build();
    }

    /**
     * Returns the first Game from the set or null if null/empty. Order is not guaranteed.
     *
     * @param games set of Game entities, may be null or empty
     * @return first Game or null
     */
    @Named("mapSelectedGame")
    public static Game mapSelectedGame(Set<Game> games) {
        if (CollectionUtils.isEmpty(games)) {
            LOG.warn("▓▓ No selected games found (input is null or empty). Returning null.");
            return null;
        }
        return games.iterator().next();
    }
}
