/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.model.Game;
import fr.softsf.sudokufx.model.Player;

public class MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

    @Named("mapPlayerIdToPlayer")
    public static Player mapPlayerIdToPlayer(Long playerId) {
        if (playerId == null) {
            log.warn("▓▓ Player ID is null, returning null Player entity.");
            return null;
        }
        return Player.builder().playerid(playerId).build();
    }

    @Named("extractGameIds")
    public static Set<Long> extractGameIds(Set<Game> games) {
        if (games == null) {
            log.warn("▓▓ Game IDs set is null, returning empty set of game IDs.");
            return new LinkedHashSet<>();
        }
        return games.stream()
                .map(Game::getGameid)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Named("mapGameIdsToGames")
    public static Set<Game> mapGameIdsToGames(Set<Long> gameIds) {
        if (gameIds == null) {
            log.warn("▓▓ Game IDs set is null, returning empty set of Game entities.");
            return new LinkedHashSet<>();
        }
        return gameIds.stream()
                .filter(Objects::nonNull)
                .map(id -> Game.builder().gameid(id).build())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
