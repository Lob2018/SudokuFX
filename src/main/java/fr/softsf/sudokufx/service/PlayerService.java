/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.interfaces.mapper.IPlayerMapper;
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
     * Retrieves the first selected Player and maps it to a PlayerDto.
     *
     * <p>@Transactional(readOnly = true) ensures lazy-loaded games are fetched during mapping.
     *
     * @return an Optional containing the mapped PlayerDto if a Player is found; otherwise, empty.
     */
    @Transactional(readOnly = true)
    public Optional<PlayerDto> getPlayer() {
        try {
            return playerRepository.findPlayersWithSelectedGames().stream()
                    .findFirst()
                    .map(playerMapper::mapPlayerToDto)
                    .or(
                            () -> {
                                LOG.error("██ No player found.");
                                return Optional.empty();
                            });
        } catch (Exception e) {
            LOG.error("██ Exception retrieving player: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
