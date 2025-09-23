/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Component that provides conversions between different Sudoku grid representations: /** Component
 * providing conversions between different Sudoku grid representations:
 *
 * <ul>
 *   <li>{@code gridValue} : player's input as a comma-separated String of 81 values
 *   <li>{@code defaultGridValue} : default grid as an 81-character String
 *   <li>{@code int[]} : numerical representation of the grid
 *   <li>{@code List<String>} : list of 81 cell values as Strings; for {@code gridValue}, each cell
 *       may be:
 *       <ul>
 *         <li>a combination of digits 1-9 (no repetitions within the cell)
 *         <li>or "0" / empty string if the cell is empty
 *       </ul>
 * </ul>
 *
 * <p>Validation ensures that each cell in {@link #listToGridValue(List)} contains only valid digits
 * and no repeated numbers. Empty or blank cells are normalized to "0".
 */
@Component
public final class GridConverter implements IGridConverter {

    private static final int TOTAL_CELLS = 81;

    @Override
    public String listToGridValue(List<String> values) {
        if (values == null || values.size() != TOTAL_CELLS) {
            throw new IllegalArgumentException(
                    "The list of entered values must contain exactly " + TOTAL_CELLS + " values");
        }
        return values.stream()
                .map(
                        v -> {
                            String cell = (v == null || v.isBlank()) ? "0" : v.strip();
                            if ("0".equals(cell)) {
                                return cell;
                            }
                            if (cell.chars().allMatch(c -> c >= '1' && c <= '9')
                                    && cell.chars().distinct().count() == cell.length()) {
                                return cell;
                            }
                            throw new IllegalArgumentException(
                                    "Invalid or repeated digits in cell: " + cell);
                        })
                .collect(Collectors.joining(","));
    }

    @Override
    public List<String> gridValueToList(String gridValue) {
        if (StringUtils.isBlank(gridValue)) {
            return List.of();
        }
        return Arrays.asList(gridValue.split(","));
    }

    @Override
    public List<String> defaultGridValueToList(String defaultGridValue) {
        if (StringUtils.isBlank(defaultGridValue)) {
            return List.of();
        }
        return Arrays.stream(defaultGridValue.split("")).toList();
    }

    @Override
    public String intArrayToDefaultGridValue(int[] grid) {
        return Arrays.stream(grid).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public List<String> intArrayToDefaultGridValueList(int[] grid) {
        return Arrays.stream(grid).mapToObj(String::valueOf).toList();
    }

    @Override
    public int[] listToIntArray(List<String> values) {
        return values.stream()
                .mapToInt(v -> (v != null && v.matches("\\d")) ? Integer.parseInt(v) : 0)
                .toArray();
    }

    @Override
    public List<String> intArrayToList(int[] grid) {
        return Arrays.stream(grid).mapToObj(String::valueOf).toList();
    }
}
