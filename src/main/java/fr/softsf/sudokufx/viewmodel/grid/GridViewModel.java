/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.PathValidator;
import fr.softsf.sudokufx.common.util.sudoku.GrilleResolue;
import fr.softsf.sudokufx.common.util.sudoku.GrillesCrees;
import fr.softsf.sudokufx.common.util.sudoku.IGridConverter;
import fr.softsf.sudokufx.common.util.sudoku.IGridMaster;
import fr.softsf.sudokufx.common.util.sudoku.LevelPossibilityBounds;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.GameLevelDto;
import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

/**
 * ViewModel responsible for managing a 9x9 Sudoku grid. Provides observable cell view models,
 * persistence hooks, and methods for applying difficulty levels, validating completion, and
 * handling game state updates.
 */
@Component
public class GridViewModel {

    private static final Logger LOG = LoggerFactory.getLogger(GridViewModel.class);
    private static final int GRID_SIZE = 9;
    private static final int TOTAL_CELLS = GRID_SIZE * GRID_SIZE;
    private static final String GAME_DTO_MUSTN_T_BE_NULL = "gameDto mustn't be null";
    private static final int DEFAULT_DESIRED_POSSIBILITIES = -1;

    private final IGridMaster iGridMaster;
    private final ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel;
    private final AudioService audioService;
    private final PlayerStateHolder playerStateHolder;
    private final PlayerService playerService;
    private final IGridConverter iGridConverter;
    private final ToasterService toasterService;

    private final List<GridCellViewModel> cellViewModels = new ArrayList<>(TOTAL_CELLS);
    private boolean initialized = false;
    private boolean suppressCellsListeners = false;
    private int desiredPossibilities = DEFAULT_DESIRED_POSSIBILITIES;

    private final BooleanProperty victory = new SimpleBooleanProperty(false);

    /**
     * Returns the victory status property for binding the firework animation.
     *
     * @return the victory property
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public ReadOnlyBooleanProperty victoryProperty() {
        return victory;
    }

    /** Resets the desired possibilities to the default state. */
    public void resetDesiredPossibilities() {
        desiredPossibilities = DEFAULT_DESIRED_POSSIBILITIES;
    }

    /**
     * Returns the user's desired completion percentage (0-100). A value of -1 indicates that the
     * default generation logic should be used.
     *
     * @return the current desired possibilities value
     */
    public int getDesiredPossibilities() {
        return desiredPossibilities;
    }

    /**
     * Increments the starting point of the difficulty bracket cyclically.
     *
     * @param level the difficulty level providing the percentage boundaries
     */
    public void incrementDesiredPossibilities(DifficultyLevel level) {
        LevelPossibilityBounds bounds = iGridMaster.getIntervallePourcentageNiveau(level);
        if (desiredPossibilities == DEFAULT_DESIRED_POSSIBILITIES
                || iGridMaster.calculerValeurSuperieureDuSegment(bounds, desiredPossibilities)
                        >= bounds.max()) {
            desiredPossibilities = bounds.min();
        } else {
            desiredPossibilities =
                    ((desiredPossibilities / IGridMaster.DESIRED_POSSIBILITIES_STEP) + 1)
                            * IGridMaster.DESIRED_POSSIBILITIES_STEP;
        }
    }

    /**
     * Formats and pushes the current difficulty bracket to the notification service. * @param level
     * the current difficulty level
     */
    public void notifyLevelPossibilityBounds(DifficultyLevel level) {
        LevelPossibilityBounds bounds = iGridMaster.getIntervallePourcentageNiveau(level);
        int minVal = desiredPossibilities;
        int maxVal = iGridMaster.calculerValeurSuperieureDuSegment(bounds, minVal);
        toasterService.requestRemoveToast();
        toasterService.showInfo(
                MessageFormat.format(
                        I18n.INSTANCE.getValue(
                                "toast.msg.levelInteractionHandler.desiredpossibilities"),
                        String.valueOf(minVal),
                        String.valueOf(maxVal)),
                "");
    }

    /**
     * Initializes the GridViewModel with essential domain services and reactive state holders.
     *
     * <p>This constructor orchestrates dependencies for Sudoku generation, validation, player
     * persistence, and UI feedback.
     *
     * @param iGridMaster service for Sudoku grid generation and solving algorithms
     * @param activeMenuOrSubmenuViewModel view model for tracking the active UI context
     * @param audioService service for managing game sound effects and music
     * @param playerStateHolder global holder for the current player's state
     * @param playerService business service for player and game persistence
     * @param iGridConverter utility for converting between grid formats (arrays, strings, lists)
     * @param toasterService service for pushing notifications to the user interface
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "Domain services and state holders must be stored by reference to ensure"
                            + " cross-layer reactivity.")
    public GridViewModel(
            IGridMaster iGridMaster,
            ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel,
            AudioService audioService,
            PlayerStateHolder playerStateHolder,
            PlayerService playerService,
            IGridConverter iGridConverter,
            ToasterService toasterService) {
        this.iGridMaster = iGridMaster;
        this.activeMenuOrSubmenuViewModel = activeMenuOrSubmenuViewModel;
        this.audioService = audioService;
        this.playerStateHolder = playerStateHolder;
        this.playerService = playerService;
        this.iGridConverter = iGridConverter;
        this.toasterService = toasterService;
    }

    /**
     * Initializes the 9x9 Sudoku grid by creating 81 {@link GridCellViewModel} instances with
     * unique IDs and row/column coordinates. Each cell's text property is attached to a listener
     * that calls {@link #handleCellTextChange()} whenever the text changes.
     *
     * <p>Must be called before accessing or manipulating grid cells; otherwise, {@link
     * IllegalStateException} may be thrown.
     */
    public void init() {
        int id = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                GridCellViewModel cellVM = new GridCellViewModel(id++, row, col);
                cellVM.getTextArea()
                        .textProperty()
                        .addListener((obs, oldText, newText) -> handleCellTextChange());
                cellViewModels.add(cellVM);
            }
        }
        initialized = true;
    }

    /**
     * Handles updates to a cell's text when the user modifies the grid.
     *
     * <p>Skips processing if listeners are suppressed. Depending on the active menu and grid state,
     * this method may persist the grid, verify it, or stop background audio.
     */
    private void handleCellTextChange() {
        if (suppressCellsListeners) {
            return;
        }
        boolean isSolveMenu =
                ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE.equals(
                        activeMenuOrSubmenuViewModel.getActiveMenu().getValue());
        if (isSolveMenu) {
            victory.set(false);
            audioService.stopSong();
            return;
        }
        if (hasDefaultGrid()) {
            persistGridValue();
        }
        if (isCompletelyCompleted()) {
            verifyGrid();
        } else {
            victory.set(false);
            audioService.stopSong();
        }
    }

    /**
     * Returns whether the current game has a non-empty default grid.
     *
     * @return {@code true} if a default grid is defined, {@code false} otherwise
     */
    private boolean hasDefaultGrid() {
        GameDto game = playerStateHolder.getCurrentPlayer().selectedGame();
        if (game == null) {
            return false;
        }
        String defaultGrid = game.grididDto().defaultgridvalue();
        return StringUtils.isNotBlank(defaultGrid);
    }

    /**
     * Persists the current grid state for the active player’s selected game. Updates the {@link
     * GridDto} only if the grid values have changed.
     */
    private void persistGridValue() {
        PlayerDto currentPlayer = playerStateHolder.getCurrentPlayer();
        GameDto gameDto = currentPlayer.selectedGame();
        Objects.requireNonNull(gameDto, GAME_DTO_MUSTN_T_BE_NULL);
        String result = iGridConverter.listToGridValue(getAllValues());
        String modelGridValue = gameDto.grididDto().gridvalue();
        if (modelGridValue.equals(result)) {
            return;
        }
        GridDto toSaveGridDto = gameDto.grididDto().withGridvalue(result);
        PlayerDto toSavePlayer =
                currentPlayer.withSelectedGame(
                        currentPlayer
                                .selectedGame()
                                .withGrididDto(toSaveGridDto)
                                .withUpdatedat(Instant.now()));
        playerService.updatePlayer(toSavePlayer);
        playerStateHolder.refreshCurrentPlayer();
    }

    /**
     * Loads the current Sudoku grid from the model into the ViewModel.
     *
     * <p>This method performs the following steps:
     *
     * <ol>
     *   <li>Checks if a default grid exists for the selected game; returns empty if not.
     *   <li>Reads the difficulty level and completion percentage directly from the model.
     *   <li>Loads the default grid values into the ViewModel (with editable or not).
     *   <li>Loads the current grid values into the ViewModel.
     * </ol>
     *
     * @return an {@link Optional} containing a {@link CurrentGrid} with the difficulty level and
     *     completion percentage if a grid exists, otherwise {@link Optional#empty()}.
     * @throws NullPointerException if the selected game or its grid data is {@code null}
     */
    public Optional<CurrentGrid> getCurrentGridFromModel() {
        checkInitialized();
        GameDto gameDto = playerStateHolder.getCurrentPlayer().selectedGame();
        Objects.requireNonNull(gameDto, GAME_DTO_MUSTN_T_BE_NULL);
        String defaultGridValueFromBase = gameDto.grididDto().defaultgridvalue();
        if (StringUtils.isBlank(defaultGridValueFromBase)) {
            return Optional.empty();
        }
        DifficultyLevel level = DifficultyLevel.fromGridByte(gameDto.levelidDto().level());
        int percentage = gameDto.grididDto().possibilities();
        setValues(iGridConverter.defaultGridValueToList(defaultGridValueFromBase), true);
        setValues(iGridConverter.gridValueToList(gameDto.grididDto().gridvalue()), false);
        return Optional.of(new CurrentGrid(level, percentage));
    }

    /**
     * Loads the solved Sudoku grid from the model into the ViewModel.
     *
     * <p>This method performs the following steps:
     *
     * <ol>
     *   <li>Checks if a default grid exists for the selected game; returns empty if not.
     *   <li>Solves the current player grid if solvable; otherwise falls back to solving the default
     *       grid.
     *   <li>Reads the difficulty level directly from the model.
     *   <li>Loads the default grid values into the ViewModel (editable).
     *   <li>Loads the solved grid values into the ViewModel (non-editable).
     * </ol>
     *
     * @return an {@link Optional} containing a {@link CurrentGrid} with the difficulty level and a
     *     completion percentage of {@code 0} if a grid exists, otherwise {@link Optional#empty()}.
     * @throws NullPointerException if the selected game or its grid data is {@code null}
     */
    public Optional<CurrentGrid> getCurrentSolvedGridFromModel() {
        checkInitialized();
        GameDto gameDto = playerStateHolder.getCurrentPlayer().selectedGame();
        Objects.requireNonNull(gameDto, GAME_DTO_MUSTN_T_BE_NULL);
        String defaultGridValue = gameDto.grididDto().defaultgridvalue();
        if (StringUtils.isBlank(defaultGridValue)) {
            return Optional.empty();
        }
        List<String> defaultGridList = iGridConverter.defaultGridValueToList(defaultGridValue);
        int[] defaultGridArray = iGridConverter.listToIntArray(defaultGridList);
        GrilleResolue currentSolution =
                getCurrentSolution(iGridConverter.listToIntArray(getAllValues()));
        int[] solvedGrid =
                currentSolution.solved()
                        ? currentSolution.solvedGrid()
                        : iGridMaster.resoudreLaGrille(defaultGridArray).solvedGrid();
        setValues(defaultGridList, true);
        setValues(iGridConverter.intArrayToList(solvedGrid), false);
        DifficultyLevel level = DifficultyLevel.fromGridByte(gameDto.levelidDto().level());
        return Optional.of(new CurrentGrid(level, 0));
    }

    /**
     * Checks if all 81 cells contain valid single-digit values (1-9).
     *
     * @return true if grid is completely filled with valid digits, false otherwise
     */
    private boolean isCompletelyCompleted() {
        List<String> values = getAllValues();
        return values != null
                && values.size() == TOTAL_CELLS
                && values.stream()
                        .allMatch(
                                v ->
                                        v != null
                                                && v.length() == 1
                                                && Character.isDigit(v.charAt(0))
                                                && v.charAt(0) != '0');
    }

    /**
     * Validates the grid by attempting to solve it. If solved and matching the completed state,
     * triggers victory handling.
     */
    private void verifyGrid() {
        int[] grilleInt = iGridConverter.listToIntArray(getAllValues());
        GrilleResolue result = getCurrentSolution(grilleInt);
        if (result.solved()
                && Arrays.equals(grilleInt, result.solvedGrid())
                && isCompletelyCompleted()) {
            celebrateVictory();
        }
    }

    /**
     * Solves the given 9x9 Sudoku grid.
     *
     * @param grilleInt an array of 81 integers representing the grid (must not be {@code null})
     * @return a {@link GrilleResolue} containing the solution
     * @throws NullPointerException if {@code grilleInt} is {@code null}
     * @throws IllegalArgumentException if {@code grilleInt} length is not 81
     */
    private GrilleResolue getCurrentSolution(int[] grilleInt) {
        Objects.requireNonNull(grilleInt, "grilleInt mustn't be null");
        if (grilleInt.length != TOTAL_CELLS) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Grid must contain exactly 81 integers");
        }
        return iGridMaster.resoudreLaGrille(grilleInt);
    }

    /**
     * Handles all actions when a grid is successfully completed. Displays a victory toast to the
     * player and optionally plays their configured victory audio.
     */
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional in UI victory handling: any audio or file error must"
                        + " be logged and surfaced as a toast without crashing the application.")
    private void celebrateVictory() {
        victory.set(true);
        toasterService.showInfo(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("toast.msg.gridviewmodel.completed"),
                        playerStateHolder.getCurrentPlayer().name()),
                "");
        try {
            audioService.playSong(
                    PathValidator.INSTANCE.validateExists(
                            Path.of(
                                    playerStateHolder
                                            .getCurrentPlayer()
                                            .optionsidDto()
                                            .songpath())));
        } catch (Exception e) {
            String title = I18n.INSTANCE.getValue("toast.error.optionsviewmodel.audioerror");
            LOG.error("██ Exception - {}: {}", title, e.getMessage(), e);
            toasterService.showError(
                    title,
                    e.getClass().getSimpleName() + ": " + Objects.toString(e.getMessage(), ""));
        }
    }

    /** Returns an unmodifiable list of all cell view models. */
    public List<GridCellViewModel> getCellViewModels() {
        checkInitialized();
        return Collections.unmodifiableList(cellViewModels);
    }

    /** Clears the text in all cells. */
    public void clearGrid() {
        // TODO WILL BE USED FOR SOLVE CLEAR BUTTON
        checkInitialized();
        cellViewModels.forEach(vm -> vm.rawTextProperty().set("0"));
        setValues(getAllValues(), true);
    }

    /** Gets a list of all cell text values in row-major order. */
    public List<String> getAllValues() {
        checkInitialized();
        return cellViewModels.stream().map(vm -> vm.rawTextProperty().get()).toList();
    }

    /**
     * Sets all 81 cell values and optionally their editability.
     *
     * <ul>
     *   <li>When {@code checkEditable} is {@code true}, cells with value "0" are editable, others
     *       are not.
     *   <li>When {@code checkEditable} is {@code false}, editability is left unchanged (used for
     *       player-entered grids).
     *   <li>Listeners are suppressed for all cells during bulk updates.
     * </ul>
     *
     * @param values list of 81 non-null strings
     * @param checkEditable whether to update cell editability
     * @throws IllegalArgumentException if {@code values} is null, not size 81, or contains nulls
     */
    public void setValues(List<String> values, boolean checkEditable) {
        checkInitialized();
        Optional.ofNullable(values)
                .filter(v -> v.size() == TOTAL_CELLS)
                .filter(v -> v.stream().allMatch(Objects::nonNull))
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        "Grid must have exactly 81 non-null values"));
        suppressCellsListeners = true;
        for (int i = 0; i < TOTAL_CELLS; i++) {
            String value = values.get(i);
            GridCellViewModel cell = cellViewModels.get(i);
            cell.rawTextProperty().set(value);
            if (checkEditable) {
                cell.editableProperty().set("0".equals(value));
            }
        }
        suppressCellsListeners = false;
    }

    /**
     * Generates a new grid for the given difficulty level, updates the cells, and returns the
     * associated completion percentage.
     *
     * @param level the difficulty level (must not be {@code null})
     * @return the percentage of possibilities for the generated grid
     * @throws NullPointerException if {@code level} is {@code null}
     */
    // TODO : Overload creerLesGrillesTo with desiredPossibilities
    public int setCurrentGridWithLevel(DifficultyLevel level) {
        checkInitialized();
        Objects.requireNonNull(level, "level mustn't be null");
        GrillesCrees grillesCrees = iGridMaster.creerLesGrilles(level.toGridNumber());
        persistNewGame(level, grillesCrees);
        setValues(iGridConverter.intArrayToList(grillesCrees.grilleAResoudre()), true);
        return grillesCrees.pourcentageDesPossibilites();
    }

    /**
     * Persists a newly generated game for the current player, updating the selected game with
     * grids, difficulty level, possibility percentage, and last modified timestamp.
     *
     * @param level the difficulty level (must not be {@code null})
     * @param grillesCrees the generated grids and possibility percentage (must not be {@code null})
     * @throws NullPointerException if {@code level}, {@code grillesCrees}, or the selected game is
     *     {@code null}
     */
    private void persistNewGame(DifficultyLevel level, GrillesCrees grillesCrees) {
        Objects.requireNonNull(level, "level mustn't be null");
        Objects.requireNonNull(grillesCrees, "grillesCrees mustn't be null");
        PlayerDto currentPlayer = playerStateHolder.getCurrentPlayer();
        String defaultGrid =
                iGridConverter.intArrayToDefaultGridValue(grillesCrees.grilleAResoudre());
        String gridValue =
                iGridConverter.listToGridValue(
                        iGridConverter.intArrayToList(grillesCrees.grilleAResoudre()));
        Objects.requireNonNull(
                currentPlayer.selectedGame(), "currentPlayer.selectedGame() mustn't be null");
        GameLevelDto toSaveGameLevelDto =
                currentPlayer.selectedGame().levelidDto().withLevel((byte) level.toGridNumber());
        GridDto toSaveGridDto =
                currentPlayer
                        .selectedGame()
                        .grididDto()
                        .withGridvalue(gridValue)
                        .withDefaultgridvalue(defaultGrid)
                        .withPossibilities((byte) grillesCrees.pourcentageDesPossibilites());
        PlayerDto toSavePlayer =
                currentPlayer.withSelectedGame(
                        currentPlayer
                                .selectedGame()
                                .withUpdatedat(Instant.now())
                                .withGrididDto(toSaveGridDto)
                                .withLevelidDto(toSaveGameLevelDto));
        playerService.updatePlayer(toSavePlayer);
        playerStateHolder.refreshCurrentPlayer();
    }

    /**
     * Checks whether the {@link GridViewModel} has been initialized via {@link #init()}.
     *
     * <p>If the grid has not been initialized, this method throws an exception to prevent
     * operations on an uninitialized state.
     *
     * @throws IllegalStateException if {@link #init()} has not been called
     */
    private void checkInitialized() {
        if (!initialized) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalState(
                    "GridViewModel not initialized. Call init() first.");
        }
    }
}
