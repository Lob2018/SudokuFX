/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

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
     * Returns the first selected Player with a non-null selected game, mapped to a PlayerDto.
     *
     * <p>Throws an exception if no such Player is found. Logs the error before throwing.
     *
     * <p>@Transactional(readOnly = true) ensures proper initialization of lazy-loaded associations.
     *
     * @return the PlayerDto of the selected Player with a selected game
     * @throws SelectedPlayerWithSelectedGameNotFoundException if no matching Player is found
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .filter(dto -> dto.selectedGame() != null)
                .orElseThrow(
                        () -> {
                            LOG.error("██ No selected player with selected game found.");
                            return new SelectedPlayerWithSelectedGameNotFoundException(
                                    "No selected player with selected game found");
                        });
    }
}
