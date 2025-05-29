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

@Service
public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;
    private final IPlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, IPlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Transactional(readOnly = true)
    public Optional<PlayerDto> getPlayer() {
        try {
            return playerRepository
                    .findFirstSelectedPlayer()
                    .map(playerMapper::mapPlayerToDto)
                    .or(
                            () -> {
                                log.error("██ No player found.");
                                return Optional.empty();
                            });
        } catch (Exception e) {
            log.error("██ Exception retrieving software : {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
