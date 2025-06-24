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
     * Retrieves the first player with a selected game and a valid (non-blank) name.
     *
     * <p>Throws an {@link IllegalArgumentException} if no such player is found.
     *
     * @return a valid PlayerDto with a selected game
     * @throws IllegalArgumentException if no matching player is found
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .filter(this::hasSelectedGameAndValidName)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.createAndLogIllegalArgument(
                                        "No selected player with selected game found."));
    }

    /**
     * Checks if the player has a selected game and a valid (non-blank) name.
     *
     * <p>Throws a {@link IllegalArgumentException} via ExceptionTools if the name is null, empty,
     * or blank.
     *
     * @param dto the PlayerDto to validate
     * @return true if the player has a selected game and a valid name; false otherwise
     */
    private boolean hasSelectedGameAndValidName(PlayerDto dto) {
        if (dto.selectedGame() == null) return false;
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                dto.name(), "The player name cannot be null, empty or blank.");
        return true;
    }
}
