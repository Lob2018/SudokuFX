/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.sudoku.GrilleResolue;
import fr.softsf.sudokufx.common.util.sudoku.GrillesCrees;
import fr.softsf.sudokufx.common.util.sudoku.IGridMaster;

/**
 * ViewModel component managing a 9x9 Sudoku grid. Provides observable cell view models and methods
 * to manipulate grid data.
 */
@Component
public class GridViewModel {

    private static final int GRID_SIZE = 9;
    private static final int TOTAL_CELLS = GRID_SIZE * GRID_SIZE;
    private final List<GridCellViewModel> cellViewModels = new ArrayList<>(GRID_SIZE * GRID_SIZE);

    @Autowired private IGridMaster iGridMaster;

    /**
     * Creates 81 GridCellViewModels with unique ids and grid coordinates. Called once after bean
     * construction to initialize the grid.
     */
    public void init() {
        int id = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                GridCellViewModel cellVM = new GridCellViewModel(id++, row, col);
                cellVM.getTextArea()
                        .textProperty()
                        .addListener(
                                (obs, oldText, newText) -> {
                                    verifyGrid();
                                });
                cellViewModels.add(cellVM);
            }
        }
    }

    private void verifyGrid() {
        List<String> values = getAllValues();
        int[] grilleInt =
                values.stream()
                        .mapToInt(
                                val -> {
                                    if (val == null || val.isBlank()) {
                                        return 0;
                                    }
                                    if (val.length() == 1 && Character.isDigit(val.charAt(0))) {
                                        return Integer.parseInt(val);
                                    }
                                    return 0;
                                })
                        .toArray();
        GrilleResolue grilleResolue = iGridMaster.resoudreLaGrille(grilleInt);
        boolean solved = grilleResolue.solved();
        int[] solvedGrid = grilleResolue.solvedGrid();
        int percentage = grilleResolue.possibilityPercentage();
        System.out.println("\n\nsolvedGrid:" + Arrays.toString(solvedGrid));
        System.out.println("Solved : " + solved + "\nPourcentage : " + percentage + "%\n\n");
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
     * Sets the text values and editability of all cells. Cells are editable if their value is "0",
     * non-editable otherwise.
     *
     * @param values list of exactly 81 non-null strings
     * @throws IllegalArgumentException if values is null, has incorrect size (81), or contains
     *     nulls
     */
    public void setValues(List<String> values) {
        Optional.ofNullable(values)
                .filter(v -> v.size() == TOTAL_CELLS)
                .filter(v -> v.stream().allMatch(Objects::nonNull))
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        "Grid must have exactly 81 non-null values"));
        for (int i = 0; i < TOTAL_CELLS; i++) {
            String value = values.get(i);
            GridCellViewModel cell = cellViewModels.get(i);
            cell.rawTextProperty().set(value);
            cell.editableProperty().set("0".equals(value));
        }
    }

    /**
     * Applies the given difficulty level by generating a new grid (using level-specific
     * parameters), updating the cell values accordingly, and returning the stars completion
     * percentage.
     *
     * @param level the difficulty level to apply; must not be null
     * @return the stars completion percentage associated with the applied level
     * @throws IllegalArgumentException if the difficulty level is null
     */
    public int applyLevel(DifficultyLevel level) {
        // TODO stocker la nouvelle grille en base
        if (Objects.isNull(level)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Difficulty level cannot be null");
        }
        GrillesCrees grillesCrees = iGridMaster.creerLesGrilles(level.toGridNumber());
        setValues(Arrays.stream(grillesCrees.grilleAResoudre()).mapToObj(String::valueOf).toList());
        return grillesCrees.pourcentageDesPossibilites();
    }
}
