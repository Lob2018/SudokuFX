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
import fr.softsf.sudokufx.service.business.PlayerService;

/**
 * State holder for the current {@link PlayerDto} in the application.
 *
 * <p>This component stores the current player and exposes it via a JavaFX {@link ObjectProperty}
 * for ViewModels and Views to bind to. The player is initialized at creation using {@link
 * PlayerService}. Updates to the player should go through this state holder to ensure all observers
 * are notified.
 *
 * <p>Responsibilities:
 *
 * <ul>
 *   <li>Hold the current {@link PlayerDto} in memory.
 *   <li>Expose an observable property for UI bindings.
 *   <li>Allow manual updates after persistence.
 * </ul>
 *
 * <p>Note: If initialization fails, the application will exit.
 */
@Component
public class PlayerStateHolder {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerStateHolder.class);

    private final ObjectProperty<PlayerDto> currentPlayer = new SimpleObjectProperty<>();
    private final PlayerService playerService;

    /**
     * Returns the property holding the current player for JavaFX bindings.
     *
     * @return the observable {@link ObjectProperty} of {@link PlayerDto}
     */
    public ObjectProperty<PlayerDto> currentPlayerProperty() {
        return currentPlayer;
    }

    /**
     * Constructs the state holder and initializes the current player from {@link PlayerService}.
     *
     * @param playerService the service used to fetch the player
     */
    public PlayerStateHolder(PlayerService playerService) {
        this.playerService = playerService;
        if (Objects.isNull(currentPlayer.get())) {
            initializePlayer();
        }
    }

    /**
     * Initializes the player by fetching it from {@link PlayerService}. Exits the application on
     * failure.
     */
    private void initializePlayer() {
        try {
            PlayerDto player = playerService.getPlayer();
            currentPlayer.set(player);
        } catch (IllegalStateException e) {
            LOG.error(
                    "Error initializing player: {}, triggering Platform.exit()", e.getMessage(), e);
            exitPlatform();
        }
    }

    /**
     * Manually updates the current player. Observers will be notified automatically.
     *
     * @param dto the new {@link PlayerDto} to store
     */
    public void setCurrentPlayer(PlayerDto dto) {
        currentPlayer.set(dto);
    }

    /**
     * Returns the currently stored player.
     *
     * @return the current {@link PlayerDto}
     */
    public PlayerDto getCurrentPlayer() {
        return currentPlayer.get();
    }

    /** Terminates the JavaFX application. */
    void exitPlatform() {
        Platform.exit();
    }
}
