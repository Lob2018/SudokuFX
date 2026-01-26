/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Component providing conversions between different Sudoku grid representations:
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
 * and no repeated numbers. Empty or blank cells are normalized to "0". Null arguments will throw
 * {@link NullPointerException}.
 */
@Component
public final class GridConverter implements IGridConverter {

    private static final int TOTAL_CELLS = 81;
    private static final int MAXIMUM_GRID_VALUE_LENGTH = TOTAL_CELLS * 10;
    private static final String GRID_ARRAY_MUST_NOT_BE_NULL = "Grid array must not be null";

    @Override
    public String listToGridValue(List<String> values) {
        Objects.requireNonNull(values, "The list of entered values must not be null");
        if (values.size() != TOTAL_CELLS) {
            throw new IllegalArgumentException(
                    "The list of entered values must contain exactly " + TOTAL_CELLS + " values");
        }
        StringBuilder sb = new StringBuilder(MAXIMUM_GRID_VALUE_LENGTH);
        for (int i = 0; i < TOTAL_CELLS; i++) {
            String cell = values.get(i);
            cell = cell == null || cell.isBlank() ? "0" : cell.strip().replace("\n", "");
            if (!"0".equals(cell)) {
                validateDigitsAndNoRepeats(cell);
            }
            sb.append(cell);
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /** Validates that the cell contains only digits 1-9 with no repeats. */
    private void validateDigitsAndNoRepeats(String cell) {
        if (cell.length() > 9) {
            throw new IllegalArgumentException("Cell contains too many digits: " + cell);
        }
        int seen = 0;
        for (int i = 0; i < cell.length(); i++) {
            char c = cell.charAt(i);
            if (c < '1' || c > '9') {
                throw new IllegalArgumentException("Invalid digit in cell: " + cell);
            }
            int mask = 1 << (c - '1');
            if ((seen & mask) != 0) {
                throw new IllegalArgumentException("Repeated digit in cell: " + cell);
            }
            seen |= mask;
        }
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
        Objects.requireNonNull(grid, GRID_ARRAY_MUST_NOT_BE_NULL);
        return Arrays.stream(grid).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public int[] listToIntArray(List<String> values) {
        Objects.requireNonNull(values, "The list of entered values must not be null");
        return values.stream()
                .mapToInt(v -> (v != null && v.matches("\\d")) ? Integer.parseInt(v) : 0)
                .toArray();
    }

    @Override
    public List<String> intArrayToList(int[] grid) {
        Objects.requireNonNull(grid, GRID_ARRAY_MUST_NOT_BE_NULL);
        return Arrays.stream(grid).mapToObj(String::valueOf).toList();
    }
}
