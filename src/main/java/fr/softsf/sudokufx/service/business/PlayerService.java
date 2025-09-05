/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.model.Menu;
import fr.softsf.sudokufx.model.Options;
import fr.softsf.sudokufx.model.Player;
import fr.softsf.sudokufx.model.PlayerLanguage;
import fr.softsf.sudokufx.repository.MenuRepository;
import fr.softsf.sudokufx.repository.OptionsRepository;
import fr.softsf.sudokufx.repository.PlayerLanguageRepository;
import fr.softsf.sudokufx.repository.PlayerRepository;
import jakarta.validation.ConstraintViolationException;

/**
 * Service for managing Player entities.
 *
 * <p>Provides retrieval and update operations on players, mapping entities to {@link PlayerDto} and
 * validating DTOs with {@link JakartaValidator} before and after persistence.
 *
 * <p>Methods:
 *
 * <ul>
 *   <li>{@link #getPlayer()}: retrieves the first selected player with a selected game, read-only
 *       transactional.
 *   <li>{@link #updatePlayer(PlayerDto)}: updates an existing player, validates input and output
 *       DTOs, fully transactional.
 * </ul>
 *
 * <p>Throws {@link NullPointerException} for null DTOs, {@link IllegalArgumentException} if
 * entities are missing, and {@link jakarta.validation.ConstraintViolationException} on validation
 * failures.
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerLanguageRepository playerLanguageRepository;
    private final OptionsRepository optionsRepository;
    private final MenuRepository menuRepository;
    private final IPlayerMapper playerMapper;
    private final JakartaValidator jakartaValidator;

    public PlayerService(
            PlayerRepository playerRepository,
            PlayerLanguageRepository playerLanguageRepository,
            OptionsRepository optionsRepository,
            MenuRepository menuRepository,
            IPlayerMapper playerMapper,
            JakartaValidator jakartaValidator) {
        this.playerRepository = playerRepository;
        this.playerLanguageRepository = playerLanguageRepository;
        this.optionsRepository = optionsRepository;
        this.menuRepository = menuRepository;
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

    /**
     * Updates an existing player in the database with the values from the given {@link PlayerDto}.
     *
     * <p>This method is transactional: if any referenced entity (Player, PlayerLanguage, Options,
     * Menu) is missing, the transaction is rolled back.
     *
     * <p>Validation is applied both before updating (on the incoming DTO) and after saving (on the
     * resulting DTO) using {@link JakartaValidator}.
     *
     * @param dto the player data to update; must not be null
     * @return the updated and validated {@link PlayerDto}
     * @throws NullPointerException if {@code dto} is null
     * @throws IllegalArgumentException if the player, player language, options, or menu does not
     *     exist
     * @throws ConstraintViolationException if validation fails on the DTO
     */
    @Transactional
    public PlayerDto updatePlayer(PlayerDto dto) {
        PlayerDto validatedDto = jakartaValidator.validateOrThrow(dto);
        Player existing =
                playerRepository
                        .findById(validatedDto.playerid())
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Player not found: " + validatedDto.playerid()));
        PlayerLanguage pl =
                playerLanguageRepository
                        .findById(validatedDto.playerlanguageidDto().playerlanguageid())
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "PlayerLanguage not found"));
        Options op =
                optionsRepository
                        .findById(validatedDto.optionsidDto().optionsid())
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Options not found"));
        Menu menu =
                menuRepository
                        .findById(validatedDto.menuidDto().menuid())
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Menu not found"));
        existing.setName(validatedDto.name());
        existing.setIsselected(validatedDto.isselected());
        existing.setUpdatedat(validatedDto.updatedat());
        existing.setPlayerlanguageid(pl);
        existing.setOptionsid(op);
        existing.setMenuid(menu);
        Player saved = playerRepository.save(existing);
        PlayerDto result = playerMapper.mapPlayerToDto(saved);
        return jakartaValidator.validateOrThrow(result);
    }
}
