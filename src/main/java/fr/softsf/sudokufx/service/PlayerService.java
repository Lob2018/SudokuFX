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

import fr.softsf.sudokufx.common.exception.PlayerNameInvalidException;
import fr.softsf.sudokufx.common.exception.SelectedPlayerWithSelectedGameNotFoundException;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.repository.PlayerRepository;
import io.micrometer.common.util.StringUtils;

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
     * Returns the first selected player with a selected game, mapped to a PlayerDto.
     *
     * <p>Validates that the player's name is non-null and not blank. Logs and throws if invalid or
     * if no matching player is found.
     *
     * @return a valid PlayerDto with a selected game and non-blank name
     * @throws SelectedPlayerWithSelectedGameNotFoundException if no matching player is found
     * @throws PlayerNameInvalidException if the player's name is null, empty, or blank
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .filter(this::hasSelectedGameAndValidName)
                .orElseThrow(
                        () -> {
                            LOG.error("██ No selected player with selected game found.");
                            return new SelectedPlayerWithSelectedGameNotFoundException(
                                    "No selected player with selected game found");
                        });
    }

    /**
     * Checks that the player has a selected game and a valid (non-blank) name.
     *
     * <p>Logs and throws a PlayerNameInvalidException if the name is invalid.
     *
     * @param dto the PlayerDto to validate
     * @return true if the player has a selected game and a valid name
     * @throws PlayerNameInvalidException if the name is null, empty, or blank
     */
    private boolean hasSelectedGameAndValidName(PlayerDto dto) {
        if (dto.selectedGame() == null) return false;
        if (StringUtils.isBlank(dto.name())) {
            LOG.error("██ The player name cannot be null, empty or blank.");
            throw new PlayerNameInvalidException("The player name cannot be blank");
        }
        return true;
    }
}
