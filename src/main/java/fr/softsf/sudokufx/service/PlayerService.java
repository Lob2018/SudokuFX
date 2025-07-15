/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.repository.PlayerRepository;
import jakarta.validation.ConstraintViolationException;

/**
 * Service class providing business logic related to Player entities. It interacts with the
 * PlayerRepository to retrieve Player data, uses IPlayerMapper to convert entities to PlayerDto
 * objects, and performs Jakarta Bean Validation on results.
 *
 * <p>All method parameters and return values in this package are non-null by default, thanks to the
 * {@code @NonNullApi} annotation at the package level.
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final IPlayerMapper playerMapper;
    private final JakartaValidator jakartaValidator;

    public PlayerService(
            PlayerRepository playerRepository,
            IPlayerMapper playerMapper,
            JakartaValidator jakartaValidator) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
        this.jakartaValidator = jakartaValidator;
    }

    /**
     * Retrieves and validates the first player with a selected game.
     *
     * <p>The player must have a non-null selected game and pass all Jakarta Bean Validation
     * constraints.
     *
     * @return a validated PlayerDto
     * @throws IllegalArgumentException if no matching player is found
     * @throws ConstraintViolationException if validation fails on the mapped PlayerDto
     */
    @Transactional(readOnly = true)
    public PlayerDto getPlayer() {
        return playerRepository.findSelectedPlayerWithSelectedGame().stream()
                .findFirst()
                .map(playerMapper::mapPlayerToDto)
                .filter(dto -> dto.selectedGame() != null)
                .map(jakartaValidator::validateOrThrow)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        "No selected player with selected game found."));
    }
}
