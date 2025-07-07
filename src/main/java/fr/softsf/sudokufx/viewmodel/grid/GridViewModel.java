/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.grid;

import java.util.stream.Collectors;
import java.util.*;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.sudoku.IGridMaster;

/**
 * ViewModel component managing a 9x9 Sudoku grid. Provides observable cell view models and methods
 * to manipulate grid data.
 */
@Component
public class GridViewModel {

    private static final int GRID_SIZE = 9;

    private final List<GridCellViewModel> cellViewModels = new ArrayList<>(GRID_SIZE * GRID_SIZE);

    private final IGridMaster iGridMaster;

    public GridViewModel(IGridMaster iGridMaster) {
        this.iGridMaster = iGridMaster;
    }

    /**
     * Creates 81 GridCellViewModels with unique ids and grid coordinates. Called once after bean
     * construction to initialize the grid.
     */
    public void init() {
        int id = 1;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                GridCellViewModel cellVM = new GridCellViewModel(id++, row, col);
                cellViewModels.add(cellVM);
            }
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
        return cellViewModels.stream()
                .map(vm -> vm.rawTextProperty().get())
                .toList();
    }

    /**
     * Sets the text values for all cells.
     *
     * @param values list of exactly 81 values
     * @throws IllegalArgumentException if values is null, size is not 81, or contains null elements
     */
    public void setValues(List<String> values) {
        Optional.ofNullable(values)
                .filter(v -> v.size() == 81)
                .filter(v -> v.stream().allMatch(Objects::nonNull))
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        "Grid must have exactly 81 non-null values"));
        for (int i = 0; i < 81; i++) {
            cellViewModels.get(i).rawTextProperty().set(values.get(i));
        }
    }

    /**
     * Applies the given difficulty level by generating a new grid, updating the cell values
     * accordingly, and returning the stars completion percentage.
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
        int[][] grid;
        switch (level) {
            case EASY -> {
                grid = iGridMaster.creerLesGrilles(1);
                setValues(Arrays.stream(grid[1]).mapToObj(String::valueOf).toList());
                return grid[2][0];
            }
            case MEDIUM -> {
                grid = iGridMaster.creerLesGrilles(2);
                setValues(Arrays.stream(grid[1]).mapToObj(String::valueOf).toList());
                return grid[2][0];
            }
            case DIFFICULT -> {
                grid = iGridMaster.creerLesGrilles(3);
                setValues(Arrays.stream(grid[1]).mapToObj(String::valueOf).toList());
                return grid[2][0];
            }
            default -> {
                return 100;
            }
        }
    }
}
