/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.cache;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.PlayerService;

/**
 * Component responsible for caching and providing the current PlayerDto instance. It initializes
 * the cached player on creation by fetching from PlayerService. Provides a JavaFX ObjectProperty to
 * allow UI bindings and supports manual updates. On failure to initialize (player not found), it
 * logs the error and terminates the application.
 */
@Component
public class CachedPlayer {

    private static final Logger LOG = LoggerFactory.getLogger(CachedPlayer.class);

    private final ObjectProperty<PlayerDto> currentPlayer = new SimpleObjectProperty<>();
    private final PlayerService playerService;

    /**
     * Returns the currentPlayer property for JavaFX binding.
     *
     * @return the ObjectProperty holding the current PlayerDto.
     */
    public ObjectProperty<PlayerDto> currentPlayerProperty() {
        return currentPlayer;
    }

    /**
     * Constructor that initializes the cached player by loading it from the PlayerService.
     *
     * @param playerService the service to fetch PlayerDto data.
     */
    public CachedPlayer(PlayerService playerService) {
        this.playerService = playerService;
        if (currentPlayer.get() == null) {
            initializePlayer();
        }
    }

    /**
     * Loads the current player from PlayerService and sets it in the cache. If the player is not
     * found during initialization, logs the error and exits the application.
     */
    private void initializePlayer() {
        try {
            PlayerDto player = playerService.getPlayer();
            currentPlayer.set(player);
        } catch (IllegalStateException e) {
            LOG.error(
                    "██ Error initializing player: {}, triggering Platform.exit()",
                    e.getMessage(),
                    e);
            Platform.exit();
        }
    }

    /**
     * Updates the cached current player with the given PlayerDto.
     *
     * @param dto the PlayerDto to cache.
     */
    public void setCurrentPlayer(PlayerDto dto) {
        currentPlayer.set(dto);
    }

    /**
     * Retrieves the cached current PlayerDto.
     *
     * @return the currently cached PlayerDto.
     */
    public PlayerDto getCurrentPlayer() {
        return currentPlayer.get();
    }
}
