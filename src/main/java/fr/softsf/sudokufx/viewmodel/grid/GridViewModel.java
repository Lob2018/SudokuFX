/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.io.File;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.util.sudoku.GrilleResolue;
import fr.softsf.sudokufx.common.util.sudoku.GrillesCrees;
import fr.softsf.sudokufx.common.util.sudoku.IGridConverter;
import fr.softsf.sudokufx.common.util.sudoku.IGridMaster;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.GameLevelDto;
import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
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

    private final IGridMaster iGridMaster;
    private final ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel;
    private final AudioService audioService;
    private final PlayerStateHolder playerStateHolder;
    private final PlayerService playerService;
    private final IGridConverter iGridConverter;

    private final List<GridCellViewModel> cellViewModels = new ArrayList<>(TOTAL_CELLS);
    private boolean suppressCellsListeners = false;
    private ToasterVBox toaster;

    public GridViewModel(
            IGridMaster iGridMaster,
            ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel,
            AudioService audioService,
            PlayerStateHolder playerStateHolder,
            PlayerService playerService,
            IGridConverter iGridConverter) {
        this.iGridMaster = iGridMaster;
        this.activeMenuOrSubmenuViewModel = activeMenuOrSubmenuViewModel;
        this.audioService = audioService;
        this.playerStateHolder = playerStateHolder;
        this.playerService = playerService;
        this.iGridConverter = iGridConverter;
    }

    /**
     * Initializes the 9x9 Sudoku grid by creating 81 {@link GridCellViewModel} instances with
     * unique IDs and row/column coordinates. Each cell's text property is attached to a listener
     * that:
     *
     * <ul>
     *   <li>Ignores changes when {@link #suppressCellsListeners} is {@code true} (used for bulk
     *       updates).
     *   <li>Automatically verifies the grid in {@code SOLVE} mode or when the grid is fully filled.
     *   <li>Stops background audio when appropriate.
     *   <li>Persists updated grid values if the player-modifiable grid differs from the default.
     * </ul>
     *
     * <p>Listeners handle both default grids (editable according to initial values) and
     * player-entered grids. The {@code ToasterVBox} is used to display notifications if needed.
     *
     * @param toaster the {@link ToasterVBox} instance used for notifications
     */
    public void init(ToasterVBox toaster) {
        this.toaster = toaster;
        int id = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                GridCellViewModel cellVM = new GridCellViewModel(id++, row, col);
                cellVM.getTextArea()
                        .textProperty()
                        .addListener(
                                (obs, oldText, newText) -> {
                                    if (suppressCellsListeners) {
                                        return;
                                    }
                                    if (activeMenuOrSubmenuViewModel.getActiveMenu().get()
                                            != ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE) {
                                        if (StringUtils.isNotBlank(
                                                Objects.requireNonNull(
                                                                playerStateHolder
                                                                        .getCurrentPlayer()
                                                                        .selectedGame())
                                                        .grididDto()
                                                        .defaultgridvalue())) {
                                            persistGridValue();
                                        }
                                        if (isCompletelyCompleted()) {
                                            verifyGrid();
                                        } else {
                                            audioService.stopSong();
                                        }
                                    } else {
                                        audioService.stopSong();
                                    }
                                });
                cellViewModels.add(cellVM);
            }
        }
    }

    /**
     * Persists the current grid state for the active player’s selected game. Updates the {@link
     * GridDto} only if the grid values have changed.
     */
    private void persistGridValue() {
        PlayerDto currentPlayer = playerStateHolder.getCurrentPlayer();
        GameDto gameDto = currentPlayer.selectedGame();
        Objects.requireNonNull(gameDto, "gameDto mustn't be null");
        String result = iGridConverter.listToGridValue(getAllValues());
        String modelGridValue = gameDto.grididDto().gridvalue();
        if (modelGridValue.equals(result)) {
            return;
        }
        GridDto toSaveGridDto = gameDto.grididDto().withGridvalue(result);
        PlayerDto toSavePlayer =
                currentPlayer.withSelectedGame(
                        currentPlayer.selectedGame().withGrididDto(toSaveGridDto));
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
     */
    public Optional<CurrentGrid> getCurrentGridFromModel() {
        GameDto gameDto = playerStateHolder.getCurrentPlayer().selectedGame();
        Objects.requireNonNull(gameDto, "gameDto mustn't be null");
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
        GrilleResolue grilleResolue = iGridMaster.resoudreLaGrille(grilleInt);
        boolean solved = grilleResolue.solved();
        int[] solvedGrid = grilleResolue.solvedGrid();
        int percentage = grilleResolue.possibilityPercentage();
        System.out.println(
                "\n\n--solvedGrid:"
                        + Arrays.toString(solvedGrid)
                        + "\nSolved : "
                        + solved
                        + "\nPercentage : "
                        + percentage
                        + "%\n\n");
        if (solved && Arrays.equals(grilleInt, solvedGrid) && isCompletelyCompleted()) {
            celebrateVictory();
        }
    }

    /**
     * Handles all actions when a grid is successfully completed. Displays a victory toast to the
     * player and optionally plays their configured victory audio.
     */
    private void celebrateVictory() {
        toaster.addToast(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("toast.msg.gridviewmodel.completed"),
                        playerStateHolder.getCurrentPlayer().name()),
                "",
                ToastLevels.INFO,
                false);
        try {
            String path = playerStateHolder.getCurrentPlayer().optionsidDto().songpath();
            if (StringUtils.isNotEmpty(path)) {
                File file = new File(path);
                if (file.exists()) {
                    audioService.playSong(file);
                } else {
                    throw new IllegalStateException("Audio file not found: " + path);
                }
            }
        } catch (Exception e) {
            String title = I18n.INSTANCE.getValue("toast.error.optionsviewmodel.audioerror");
            LOG.error("██ Exception - {}: {}", title, e.getMessage(), e);
            toaster.addToast(
                    title,
                    e.getClass().getSimpleName() + ": " + Objects.toString(e.getMessage(), ""),
                    ToastLevels.ERROR,
                    true);
        }
    }

    /** Returns an unmodifiable list of all cell view models. */
    public List<GridCellViewModel> getCellViewModels() {
        return Collections.unmodifiableList(cellViewModels);
    }

    /**
     * Finds a cell view model by its unique id.
     *
     * @param id cell id
     * @return Optional containing the cell VM if found
     */
    public Optional<GridCellViewModel> getCellViewModelById(int id) {
        return cellViewModels.stream().filter(vm -> vm.getId() == id).findFirst();
    }

    /** Clears the text in all cells. */
    public void clearGrid() {
        cellViewModels.forEach(vm -> vm.rawTextProperty().set(""));
    }

    /** Gets a list of all cell text values in row-major order. */
    public List<String> getAllValues() {
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
     * Applies the given difficulty level by generating a new grid, updating the cells, and
     * returning the associated completion percentage.
     *
     * @param level the difficulty level to apply (must not be null)
     * @return the percentage of possibilities for the applied level
     * @throws IllegalArgumentException if {@code level} is null
     */
    public int setCurrentGridWithLevel(DifficultyLevel level) {
        if (level == null) {
            throw new IllegalArgumentException("Difficulty level cannot be null");
        }
        GrillesCrees grillesCrees = iGridMaster.creerLesGrilles(level.toGridNumber());
        persistNewGame(level, grillesCrees);
        setValues(iGridConverter.intArrayToList(grillesCrees.grilleAResoudre()), true);
        return grillesCrees.pourcentageDesPossibilites();
    }

    /**
     * Persists a newly generated game for the current player, updating the selected game with the
     * difficulty level, generated grids, possibility percentage, and last modified timestamp.
     *
     * @param level the {@link DifficultyLevel} of the new game
     * @param grillesCrees the {@link GrillesCrees} containing generated grids and possibility
     *     percentage
     */
    private void persistNewGame(DifficultyLevel level, GrillesCrees grillesCrees) {
        PlayerDto currentPlayer = playerStateHolder.getCurrentPlayer();
        String defaultGrid = iGridConverter.intArrayToDefaultGridValue(grillesCrees.grilleAResoudre());
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
                                .withUpdatedat(LocalDateTime.now())
                                .withGrididDto(toSaveGridDto)
                                .withLevelidDto(toSaveGameLevelDto));
        playerService.updatePlayer(toSavePlayer);
        playerStateHolder.refreshCurrentPlayer();
    }
}
