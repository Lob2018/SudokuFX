/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.business;

import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import fr.softsf.sudokufx.common.enums.PlayerConstants;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.GameLevelDto;
import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.model.Game;
import fr.softsf.sudokufx.model.GameLevel;
import fr.softsf.sudokufx.model.Grid;
import fr.softsf.sudokufx.model.Menu;
import fr.softsf.sudokufx.model.Options;
import fr.softsf.sudokufx.model.Player;
import fr.softsf.sudokufx.model.PlayerLanguage;
import fr.softsf.sudokufx.repository.GameRepository;
import fr.softsf.sudokufx.repository.GridRepository;
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
    private final GameRepository gameRepository;
    private final GridRepository gridRepository;
    private final GameLevelService gameLevelService;
    private final IPlayerMapper playerMapper;
    private final OptionsService optionsService;
    private final GridService gridService;

    private final JakartaValidator jakartaValidator;

    /**
     * Constructs a new {@code PlayerService} with the required infrastructure, service, and mapping
     * dependencies.
     *
     * @param playerRepository the repository for {@link Player} entities
     * @param playerLanguageRepository the repository for {@link PlayerLanguage} entities
     * @param optionsRepository the repository for {@link Options} entities
     * @param menuRepository the repository for {@link Menu} entities
     * @param gameRepository the repository for {@link Game} entities
     * @param gridRepository the repository for {@link Grid} entities
     * @param playerMapper the mapper used to convert {@link Player} entities to {@link PlayerDto}
     * @param optionsService the service providing {@link Options} duplication logic
     * @param gridService the service providing {@link Grid} duplication logic
     * @param gameLevelService the service providing {@link GameLevel} duplication logic
     * @param jakartaValidator the validator used to ensure data integrity of processed DTOs
     */
    public PlayerService(
            PlayerRepository playerRepository,
            PlayerLanguageRepository playerLanguageRepository,
            OptionsRepository optionsRepository,
            MenuRepository menuRepository,
            GameRepository gameRepository,
            GridRepository gridRepository,
            IPlayerMapper playerMapper,
            OptionsService optionsService,
            GridService gridService,
            GameLevelService gameLevelService,
            JakartaValidator jakartaValidator) {
        this.playerRepository = playerRepository;
        this.playerLanguageRepository = playerLanguageRepository;
        this.optionsRepository = optionsRepository;
        this.menuRepository = menuRepository;
        this.gameRepository = gameRepository;
        this.gridRepository = gridRepository;
        this.playerMapper = playerMapper;
        this.optionsService = optionsService;
        this.gridService = gridService;
        this.gameLevelService = gameLevelService;
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
     * Updates an existing player and its associated nested entities (language, options, menu, and
     * game state).
     *
     * <p>Performs validation on the input DTO, retrieves required associations, and applies updates
     * to the persistent entity. Uses JPA dirty checking within a single transaction to ensure
     * atomicity. If a {@code selectedGame} is provided, associated game, level, and grid state are
     * also synchronized.
     *
     * @param dto the player data containing updated fields and nested DTOs; must not be null
     * @return the persisted and validated {@link PlayerDto} representing the updated state
     * @throws NullPointerException if {@code dto} is null
     * @throws IllegalArgumentException if the player or any required nested entity is missing
     * @throws ConstraintViolationException if post-update validation fails
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
        existing.setSelected(validatedDto.selected());
        existing.setUpdatedat(validatedDto.updatedat());
        existing.setPlayerlanguageid(pl);
        existing.setOptionsid(op);
        existing.setMenuid(menu);
        if (validatedDto.selectedGame() != null) {
            GameDto gameDto = validatedDto.selectedGame();
            Game existingGame =
                    gameRepository
                            .findById(gameDto.gameid())
                            .orElseThrow(
                                    () ->
                                            ExceptionTools.INSTANCE
                                                    .logAndInstantiateIllegalArgument(
                                                            "Game not found: " + gameDto.gameid()));
            GameLevel gameLevel = gameLevelService.findByLevelOrThrow(gameDto.levelidDto().level());
            existingGame.setLevelid(gameLevel);
            existingGame.setUpdatedat(gameDto.updatedat());
            Grid grid = existingGame.getGridid();
            GridDto gridDto = gameDto.grididDto();
            grid.setDefaultgridvalue(gridDto.defaultgridvalue());
            grid.setGridvalue(gridDto.gridvalue());
            grid.setPossibilities(gridDto.possibilities());
        }
        Player saved = playerRepository.save(existing);
        return jakartaValidator.validateOrThrow(playerMapper.mapPlayerToDto(saved));
    }

    /**
     * Retrieves and validates all unselected players with a selected game.
     *
     * <p>The players are sorted alphabetically by name. Each player must pass all Jakarta Bean
     * Validation constraints.
     *
     * @return a sorted collection of validated PlayerDto elements, or an empty collection if none
     *     found
     * @throws ConstraintViolationException if validation fails on any mapped PlayerDto
     */
    @Transactional(readOnly = true)
    public Collection<PlayerDto> getPlayers() {
        return Optional.ofNullable(
                        playerRepository.findAllUnselectedWithSelectedGame(
                                Sort.by(Sort.Order.asc("name").ignoreCase())))
                .filter(items -> !CollectionUtils.isEmpty(items))
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(playerMapper::mapPlayerToDto)
                .filter(Objects::nonNull)
                .map(jakartaValidator::validateOrThrow)
                .toList();
    }

    /**
     * Creates a new player by duplicating the configuration and current game state of the source
     * player.
     *
     * <p>The process clones {@link Options} and {@link Grid} resources, creates a new {@link
     * Player} instance, and initializes a new {@link Game} linked to the original game level. The
     * source player is then marked as unselected.
     *
     * <p>Business operations performed:
     *
     * <ul>
     *   <li>Validates source {@link PlayerDto} and its {@link GameDto}.
     *   <li>Duplicates configurations via {@link OptionsService} and {@link GridService}.
     *   <li>Resolves existing {@link Menu} and {@link PlayerLanguage} by ID.
     *   <li>Resolves the existing {@link GameLevel} by its business level value.
     *   <li>Constructs a new {@link Player} with initialized timestamps.
     *   <li>Persists the new {@link Player} and its associated {@link Game}.
     *   <li>Unselects the previous player.
     * </ul>
     *
     * <p>This method is fully transactional: all operations succeed or fail as a unit.
     *
     * @param dto the source player DTO; must not be null
     * @param newName the name for the new player; must not be null or blank
     * @throws IllegalArgumentException if {@code newName} is null/blank, or if required entities
     *     are not found in the database
     * @throws jakarta.validation.ConstraintViolationException if validation fails on the resulting
     *     entities
     */
    @Transactional
    public void createNewPlayerWithCurrent(PlayerDto dto, String newName) {
        PlayerDto currentPlayerDto = jakartaValidator.validateOrThrow(dto);
        GameDto currentGameDto = jakartaValidator.validateOrThrow(currentPlayerDto.selectedGame());
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                newName, "newName must not be null or blank");
        Instant now = MyDateTime.INSTANCE.getCurrentInstant();
        Player newPlayer =
                Player.builder()
                        .playerlanguageid(
                                findOrThrow(
                                        playerLanguageRepository,
                                        currentPlayerDto.playerlanguageidDto().playerlanguageid(),
                                        "Language"))
                        .optionsid(
                                findOrThrow(
                                        optionsRepository,
                                        optionsService
                                                .duplicateOptions(
                                                        currentPlayerDto.optionsidDto().optionsid())
                                                .optionsid(),
                                        "Options"))
                        .menuid(
                                findOrThrow(
                                        menuRepository,
                                        currentPlayerDto.menuidDto().menuid(),
                                        "Menu"))
                        .name(newName)
                        .selected(true)
                        .createdat(now)
                        .updatedat(now)
                        .build();
        GameLevelDto gameLevelDto =
                jakartaValidator.validateOrThrow(currentPlayerDto.selectedGame().levelidDto());
        GameLevel gameLevel = gameLevelService.findByLevelOrThrow(gameLevelDto.level());
        Game newGame =
                Game.builder()
                        .gridid(
                                findOrThrow(
                                        gridRepository,
                                        gridService
                                                .duplicateGrid(currentGameDto.grididDto().gridid())
                                                .gridid(),
                                        "Grid"))
                        .playerid(newPlayer)
                        .levelid(gameLevel)
                        .selected(true)
                        .createdat(now)
                        .updatedat(now)
                        .build();
        newPlayer.getGames().add(newGame);
        updatePlayerSelection(currentPlayerDto.playerid(), false);
        jakartaValidator.validateOrThrow(
                playerMapper.mapPlayerToDto(playerRepository.save(newPlayer)));
    }

    /**
     * Retrieves an entity by its identifier from the provided repository or throws an exception if
     * not found.
     *
     * <p>This utility method standardizes error handling across the service layer when resolving
     * database dependencies. It ensures that required entities are managed within the current
     * transaction and throws a consistent exception if they are missing.
     *
     * @param <T> the type of the entity
     * @param <K> the type of the entity identifier
     * @param repo the {@link CrudRepository} used to query the entity
     * @param k the identifier of the entity to retrieve
     * @param entityName a descriptive name of the entity, used for error message construction
     * @return the managed entity instance
     * @throws IllegalArgumentException if the entity with the specified identifier does not exist
     */
    private <T, K> T findOrThrow(CrudRepository<T, K> repo, K k, String entityName) {
        return repo.findById(k)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        entityName + " not found: " + k));
    }

    /**
     * Internal utility to update player selection state and persist changes.
     *
     * @param playerId the identifier of the player to update
     * @param selected the new selection state
     * @throws IllegalArgumentException if the player does not exist
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    private void updatePlayerSelection(long playerId, boolean selected) {
        Player player =
                playerRepository
                        .findById(playerId)
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Player not found: " + playerId));
        player.setSelected(selected);
        player.setUpdatedat(MyDateTime.INSTANCE.getCurrentInstant());
        Player saved = playerRepository.save(player);
        jakartaValidator.validateOrThrow(playerMapper.mapPlayerToDto(saved));
    }

    /**
     * Atomically switches the selected player status.
     *
     * <p>Ensures data consistency by unselecting the previous player and selecting the new one
     * within a single database transaction.
     *
     * @param oldPlayerId the identifier of the player to be unselected
     * @param newPlayerId the identifier of the player to be selected
     * @throws IllegalArgumentException if either player is not found
     * @throws jakarta.validation.ConstraintViolationException if validation fails
     */
    @Transactional
    public void switchAndSelectNewPlayer(long oldPlayerId, long newPlayerId) {
        updatePlayerSelection(oldPlayerId, false);
        updatePlayerSelection(newPlayerId, true);
    }

    @Transactional
    public void deletePlayer(long playerIdToDelete) {
        Player playerToDelete =
                playerRepository
                        .findById(playerIdToDelete)
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                "Player not found: " + playerIdToDelete));
        if (PlayerConstants.ANONYMOUS_NAME.getValue().equals(playerToDelete.getName())) {
            return;
        }
        playerRepository.delete(playerToDelete);
        if (playerToDelete.getSelected()) {
            Player anonymousPlayer =
                    playerRepository
                            .findByName(PlayerConstants.ANONYMOUS_NAME.getValue())
                            .orElseThrow(
                                    () ->
                                            ExceptionTools.INSTANCE
                                                    .logAndInstantiateIllegalArgument(
                                                            "Anonymous player not found"));
            updatePlayerSelection(anonymousPlayer.getPlayerid(), true);
        }
    }
}
