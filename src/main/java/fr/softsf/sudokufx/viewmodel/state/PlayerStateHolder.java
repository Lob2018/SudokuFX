/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
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
 * Holds the current {@link PlayerDto} for the application.
 *
 * <p>Provides an observable property for UI bindings and propagates updates to all observers.
 *
 * <p>Responsibilities:
 *
 * <ul>
 *   <li>Store the current {@link PlayerDto} in memory.
 *   <li>Expose an observable property for ViewModels and Views.
 *   <li>Reload the current player passively from the database via {@link #refreshCurrentPlayer()}.
 * </ul>
 *
 * <p>Note: If initialization or refresh fails, the application exits gracefully.
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
            refreshCurrentPlayer();
        }
    }

    /**
     * Reloads the current player from the database. Observers are notified of any change.
     *
     * <p>This method is passive: it does not modify the database but simply reloads the current
     * player state via {@link PlayerService#getPlayer()}.
     *
     * @throws IllegalStateException if reloading the player fails
     */
    public void refreshCurrentPlayer() {
        try {
            PlayerDto player = playerService.getPlayer();
            currentPlayer.set(player);
            PlayerDto maskedPlayer =
                    player.withOptions(
                            player.optionsidDto().withImagepath("...").withSongpath("..."));
            LOG.info("Player refreshed from database: {}", maskedPlayer);
        } catch (IllegalStateException e) {
            LOG.error(
                    "██ Exception error refreshing player: {}, triggering Platform.exit()",
                    e.getMessage(),
                    e);
            exitPlatform();
        }
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
