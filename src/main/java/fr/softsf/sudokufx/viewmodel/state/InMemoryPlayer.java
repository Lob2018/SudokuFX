/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.state;

import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.PlayerService;

/**
 * Component responsible for holding the current PlayerDto instance in memory. Initializes the value
 * on creation using PlayerService. Exposes a JavaFX ObjectProperty for UI bindings and allows
 * manual updates. Terminates the application if initialization fails.
 */
@Component
public class InMemoryPlayer {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryPlayer.class);

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
     * Initializes the in-memory player from the PlayerService.
     *
     * @param playerService the service used to fetch PlayerDto.
     */
    public InMemoryPlayer(PlayerService playerService) {
        this.playerService = playerService;
        if (Objects.isNull(currentPlayer.get())) {
            initializePlayer();
        }
    }

    /** Loads the current player from PlayerService. Exits the app on failure. */
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
     * Manually updates the in-memory PlayerDto.
     *
     * @param dto the new PlayerDto to store.
     */
    public void setCurrentPlayer(PlayerDto dto) {
        currentPlayer.set(dto);
    }

    /**
     * Returns the currently stored PlayerDto.
     *
     * @return the current PlayerDto.
     */
    public PlayerDto getCurrentPlayer() {
        return currentPlayer.get();
    }
}
