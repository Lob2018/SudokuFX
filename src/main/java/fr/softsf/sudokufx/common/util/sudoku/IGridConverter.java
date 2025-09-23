/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.List;

/**
 * Interface defining methods to convert between different representations of a Sudoku grid.
 *
 * <ul>
 *   <li>{@code gridValue} : player's input as a comma-separated String of 81 values
 *   <li>{@code defaultGridValue} : default grid as an 81-character String
 *   <li>{@code int[]} : numerical representation of the grid
 *   <li>{@code List<String>} : list of 81 cell values as Strings; for {@code gridValue}, each cell
 *       may be:
 *       <ul>
 *         <li>a combination of digits 1-9 (without repetition within the cell)
 *         <li>or "0" / empty string if the cell is empty
 *       </ul>
 * </ul>
 */
public sealed interface IGridConverter permits GridConverter {

    /**
     * Converts a list of 81 cell values into a comma-separated String suitable for {@code
     * gridValue}. Empty or blank cells are normalized to "0". Each non-empty cell must contain
     * digits 1-9 without repetitions.
     */
    String listToGridValue(List<String> values);

    /**
     * Converts a comma-separated {@code gridValue} String into a list of 81 cell values. Empty
     * cells are represented as "0".
     */
    List<String> gridValueToList(String gridValue);

    /**
     * Converts a {@code defaultGridValue} String (81 characters) into a list of 81 single-character
     * cell values.
     */
    List<String> defaultGridValueToList(String defaultGridValue);

    /**
     * Converts an int array (81 elements) into a {@code defaultGridValue} String (81 characters).
     */
    String intArrayToDefaultGridValue(int[] grid);

    /** Converts an int array (81 elements) into a list of 81 Strings. */
    List<String> intArrayToDefaultGridValueList(int[] grid);

    /**
     * Converts a list of 81 cell values into an int array. Non-digit or empty cells are converted
     * to 0.
     */
    int[] listToIntArray(List<String> values);

    /** Converts an int array (81 elements) into a list of 81 Strings. */
    List<String> intArrayToList(int[] grid);
}
