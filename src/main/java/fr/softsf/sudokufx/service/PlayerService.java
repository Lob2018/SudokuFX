/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.SelectedPlayerWithSelectedGameNotFoundException;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.repository.PlayerRepository;

/**
 * Service class providing business logic related to Player entities. It interacts with the
 * PlayerRepository to retrieve Player data and uses IPlayerMapper to convert Player entities to
 * PlayerDto objects.
 */
@Service
public class PlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final IPlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, IPlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    /**
     * Retrieves the first Player with a selected game and maps it to a PlayerDto.
     *
     * <p>This method loads all players with selected games, then returns the first one
     * mapped to a PlayerDto. If none is found, it logs an error and throws a
     * SelectedPlayerWithSelectedGameNotFoundException.
     *
     * <p>@Transactional(readOnly = true) ensures lazy-loaded associations are initialized during mapping.
     *
     * @return the PlayerDto of the first selected Player with a selected game.
     * @throws SelectedPlayerWithSelectedGameNotFoundException if no such Player is found.
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .orElseThrow(
                        () -> {
                            LOG.error("██ No selected player with selected game found.");
                            return new SelectedPlayerWithSelectedGameNotFoundException(
                                    "No selected player with selected game found");
                        });
    }
}
