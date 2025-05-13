/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.shared;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.PlayerService;

@Component
public class CurrentPlayerState {

    private final ObjectProperty<PlayerDto> currentPlayer = new SimpleObjectProperty<>();
    private final PlayerService playerService;

    public CurrentPlayerState(PlayerService playerService) {
        this.playerService = playerService;
        if (currentPlayer.get() == null) {
            initializePlayer();
        }
    }

    public ObjectProperty<PlayerDto> currentPlayerProperty() {
        return currentPlayer;
    }

    private void initializePlayer() {
        if (currentPlayer.get() == null) {
            PlayerDto player = playerService.getPlayer();
            currentPlayer.set(player);
        }
    }

    public void setCurrentPlayer(PlayerDto dto) {
        currentPlayer.set(dto);
    }

    public PlayerDto getCurrentPlayer() {
        return currentPlayer.get();
    }
}
