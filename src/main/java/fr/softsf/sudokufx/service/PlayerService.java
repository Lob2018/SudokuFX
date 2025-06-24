/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.repository.PlayerRepository;

/**
 * Service class providing business logic related to Player entities. It interacts with the
 * PlayerRepository to retrieve Player data and uses IPlayerMapper to convert Player entities to
 * PlayerDto objects.
 *
 * <p>All method parameters and return values in this package are non-null by default, thanks to the
 * {@code @NonNullApi} annotation at the package level.
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final IPlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, IPlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    /**
     * Retrieves the first player with a selected game and a valid name.
     *
     * @return a valid PlayerDto
     * @throws IllegalArgumentException if no player with a selected game and valid name is found
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .filter(dto -> dto.selectedGame() != null)
                .map(this::validatePlayerNameOrThrow)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.createAndLogIllegalArgument(
                                        "No selected player with selected game found."));
    }

    /**
     * Validates that the player's name is not null, empty, or blank.
     *
     * @param dto the PlayerDto to validate
     * @return the same PlayerDto if valid
     * @throws IllegalArgumentException if the player's name is invalid
     */
    private PlayerDto validatePlayerNameOrThrow(PlayerDto dto) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                dto.name(), "The player name cannot be null, empty or blank.");
        return dto;
    }
}
