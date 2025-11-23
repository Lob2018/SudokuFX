/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
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

/**
 * Utility class providing custom mapping methods for use with MapStruct.
 *
 * <p>These methods are used to simplify object mapping, particularly to avoid full graph traversal
 * and handle reference cycles or partial entity instantiations.
 */
public final class MapperUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MapperUtils.class);

    private MapperUtils() {
        LOG.error("██ Attempted to instantiate MapperUtils utility class.");
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Maps a player ID from a DTO to a minimal Player entity with only the ID set.
     *
     * <p>Used by MapStruct to avoid full Player mapping and break potential circular references.
     *
     * @param playerId the Player ID, may be null
     * @return a Player entity with only the ID set, or null if input is null
     */
    @Named("mapPlayeridDtoToPlayer")
    public static Player mapPlayeridDtoToPlayer(Long playerId) {
        if (Objects.isNull(playerId)) {
            LOG.warn("▓▓ Player ID is null, returning null Player entity.");
            return null;
        }
        return Player.builder().playerid(playerId).build();
    }

    /**
     * Selects and returns the first Game from the given set, or null if the set is null or empty.
     *
     * <p>Used by MapStruct to simplify mapping of Player → PlayerDto by avoiding full game set
     * mapping. This helps prevent circular references and reduces DTO complexity.
     *
     * <p>Note: The order is not guaranteed; selection is non-deterministic.
     *
     * @param games the set of Game entities, may be null or empty
     * @return one Game from the set, or null if none
     */
    @Named("mapSelectedGameToDto")
    public static Game mapSelectedGameToDto(Set<Game> games) {
        if (CollectionUtils.isEmpty(games)) {
            LOG.warn("▓▓ No selected games found (input is null or empty). Returning null.");
            return null;
        }
        return games.iterator().next();
    }
}
