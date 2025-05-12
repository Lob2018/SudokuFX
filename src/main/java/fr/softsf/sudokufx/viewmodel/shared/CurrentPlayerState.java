/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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
