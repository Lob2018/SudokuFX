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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IGameMapper;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.model.Game;
import fr.softsf.sudokufx.model.GameLevel;
import fr.softsf.sudokufx.model.Grid;
import fr.softsf.sudokufx.model.Player;
import fr.softsf.sudokufx.repository.GameRepository;
import fr.softsf.sudokufx.repository.GridRepository;
import fr.softsf.sudokufx.repository.PlayerRepository;
import fr.softsf.sudokufx.repository.util.RepositoryTools;

/** Service for managing Game entities. */
@Service
public class GameService {

    public static final String GAME_NOT_FOUND = "Game not found: ";
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GridRepository gridRepository;
    private final IGameMapper gameMapper;
    private final GridService gridService;
    private final GameLevelService gameLevelService;
    private final JakartaValidator jakartaValidator;

    public GameService(
            GameRepository gameRepository,
            PlayerRepository playerRepository,
            GridRepository gridRepository,
            IGameMapper gameMapper,
            GridService gridService,
            GameLevelService gameLevelService,
            JakartaValidator jakartaValidator) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.gridRepository = gridRepository;
        this.gameMapper = gameMapper;
        this.gridService = gridService;
        this.gameLevelService = gameLevelService;
        this.jakartaValidator = jakartaValidator;
    }

    /**
     * Retrieves all non-selected games for a given player, sorted by update date in descending
     * order.
     *
     * @param playerId the unique identifier of the player.
     * @return a sorted, validated collection of {@link GameDto}.
     * @throws jakarta.validation.ConstraintViolationException if validation fails.
     */
    @Transactional(readOnly = true)
    public Collection<GameDto> getGames(long playerId) {
        return Optional.ofNullable(
                        gameRepository.findAllUnselected(
                                playerId, Sort.by(Sort.Order.desc("updatedat"))))
                .filter(items -> !CollectionUtils.isEmpty(items))
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(gameMapper::mapGameToDto)
                .map(jakartaValidator::validateOrThrow)
                .toList();
    }

    /**
     * Creates a new game by duplicating the grid of the source game.
     *
     * <p>The process clones the {@link Grid} resource, associates it with the specified {@link
     * Player} and {@link GameLevel}, and initializes a new game instance.
     *
     * <p>Business operations performed:
     *
     * <ul>
     *   <li>Validates source {@link GameDto}.
     *   <li>Duplicates the {@link Grid} via {@link GridService}.
     *   <li>Resolves existing {@link Player} and {@link GameLevel} by ID.
     *   <li>Constructs a new {@link Game} with initialized timestamps.
     *   <li>Persists the new {@link Game}.
     * </ul>
     *
     * <p>This method is fully transactional: all operations succeed or fail as a unit.
     *
     * @param sourceDto the source game DTO; must not be null
     * @param playerId the identifier of the owner player
     * @throws IllegalArgumentException if required entities are not found in the database
     * @throws jakarta.validation.ConstraintViolationException if validation fails on the resulting
     *     entities
     */
    @Transactional
    public void createNewGameWithCurrent(GameDto sourceDto, long playerId) {
        GameDto validatedSource = jakartaValidator.validateOrThrow(sourceDto);
        Instant now = MyDateTime.INSTANCE.getCurrentInstant();
        Game newGame =
                Game.builder()
                        .gridid(
                                RepositoryTools.INSTANCE.findOrThrow(
                                        gridRepository,
                                        gridService
                                                .duplicateGrid(validatedSource.grididDto().gridid())
                                                .gridid(),
                                        "Grid"))
                        .playerid(
                                RepositoryTools.INSTANCE.findOrThrow(
                                        playerRepository, playerId, "Player"))
                        .levelid(
                                gameLevelService.findByLevelOrThrow(
                                        validatedSource.levelidDto().level()))
                        .selected(false)
                        .createdat(now)
                        .updatedat(now)
                        .build();
        gameRepository.save(newGame);
    }

    /** Deletes a game entity. */
    @Transactional
    public void deleteGame(long gameId) {
        Game game =
                gameRepository
                        .findById(gameId)
                        .orElseThrow(
                                () ->
                                        ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                                GAME_NOT_FOUND + gameId));
        gameRepository.delete(game);
    }
}
