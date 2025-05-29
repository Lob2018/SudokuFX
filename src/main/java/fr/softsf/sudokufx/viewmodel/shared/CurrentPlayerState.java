/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.shared;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.PlayerService;

@Component
public class CurrentPlayerState {

    private static final Logger log = LoggerFactory.getLogger(CurrentPlayerState.class);

    private final ObjectProperty<PlayerDto> currentPlayer = new SimpleObjectProperty<>();
    private final PlayerService playerService;

    public ObjectProperty<PlayerDto> currentPlayerProperty() {
        return currentPlayer;
    }

    public CurrentPlayerState(PlayerService playerService) {
        this.playerService = playerService;
        if (currentPlayer.get() == null) {
            initializePlayer();
        }
    }

    private void initializePlayer() {
        try {
            if (currentPlayer.get() == null) {
                PlayerDto player =
                        playerService
                                .getPlayer()
                                .orElseThrow(
                                        () ->
                                                new IllegalStateException(
                                                        "Player not found during initialization"));
                currentPlayer.set(player);
                System.out.println("####################");
                System.out.println(player);
                System.out.println("####################");
            }
        } catch (IllegalStateException e) {
            log.error(
                    "██ Error initializing player: {}, triggering Platform.exit()",
                    e.getMessage(),
                    e);
            Platform.exit();
        }
    }

    public void setCurrentPlayer(PlayerDto dto) {
        currentPlayer.set(dto);
    }

    public PlayerDto getCurrentPlayer() {
        return currentPlayer.get();
    }
}
