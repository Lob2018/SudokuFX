/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridConverterUTest {

    private final IGridConverter iConverter = new GridConverter();

    @Test
    void givenValidListOfValues_whenListToGridValue_thenReturnsCsv() {
        List<String> values = new ArrayList<>(Collections.nCopies(81, "1"));
        values.set(0, "123");
        String result = iConverter.listToGridValue(values);
        assertTrue(result.startsWith("123,1,1"));
        assertEquals(81, result.split(",").length);
    }

    @Test
    void givenEmptyOrNullCells_whenListToGridValue_thenNormalizedToZero() {
        List<String> values = new ArrayList<>(Collections.nCopies(81, ""));
        values.set(0, null);
        String result = iConverter.listToGridValue(values);
        String[] cells = result.split(",");
        assertEquals("0", cells[0]);
        assertEquals(81, cells.length);
        Arrays.stream(cells).forEach(c -> assertEquals("0", c));
    }

    @Test
    void givenRepeatedDigitsInCell_whenListToGridValue_thenThrowsRepeatedDigitException() {
        List<String> values = new ArrayList<>(Collections.nCopies(81, "1"));
        values.set(0, "112");
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> iConverter.listToGridValue(values));
        assertTrue(
                ex.getMessage().contains("Repeated digit in cell"),
                "Expected exception for repeated digits, but got: " + ex.getMessage());
    }

    @Test
    void givenInvalidDigitInCell_whenListToGridValue_thenThrowsInvalidDigitException() {
        List<String> values = new ArrayList<>(Collections.nCopies(81, "1"));
        values.set(0, "1a2");
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> iConverter.listToGridValue(values));
        assertTrue(
                ex.getMessage().contains("Invalid digit in cell"),
                "Expected exception for invalid digit, but got: " + ex.getMessage());
    }

    @Test
    void givenIncorrectListSize_whenListToGridValue_thenThrowsException() {
        List<String> values = new ArrayList<>(Collections.nCopies(80, "1"));
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> iConverter.listToGridValue(values));
        assertTrue(ex.getMessage().contains("must contain exactly 81 values"));
    }

    @Test
    void givenBlankOrNullGridValue_whenGridValueToList_thenReturnsEmptyList() {
        assertEquals(0, iConverter.gridValueToList("").size());
        assertEquals(0, iConverter.gridValueToList(null).size());
    }

    @Test
    void givenValidCsv_whenGridValueToList_thenReturnsList() {
        String csv = "1,2,3," + String.join(",", Collections.nCopies(78, "0"));
        List<String> list = iConverter.gridValueToList(csv);
        assertEquals(81, list.size());
        assertEquals("1", list.get(0));
        assertEquals("0", list.get(3));
    }

    @Test
    void givenBlankOrNullDefaultGrid_whenDefaultGridValueToList_thenReturnsEmptyList() {
        assertEquals(0, iConverter.defaultGridValueToList("").size());
        assertEquals(0, iConverter.defaultGridValueToList(null).size());
    }

    @Test
    void givenValidDefaultGrid_whenDefaultGridValueToList_thenReturnsList() {
        String grid = "123456789".repeat(9);
        List<String> list = iConverter.defaultGridValueToList(grid);
        assertEquals(81, list.size());
        assertEquals("1", list.get(0));
        assertEquals("9", list.get(8));
    }

    @Test
    void givenIntArray_whenConvertToDefaultGridAndBack_thenWorksCorrectly() {
        int[] array = new int[81];
        Arrays.fill(array, 5);
        String defaultGrid = iConverter.intArrayToDefaultGridValue(array);
        assertEquals(81, defaultGrid.length());
        int[] backArray = iConverter.listToIntArray(iConverter.intArrayToList(array));
        assertEquals(81, backArray.length);
        Arrays.stream(backArray).forEach(v -> assertEquals(5, v));
        List<String> listFromIntArray = iConverter.intArrayToList(array);
        assertEquals(81, listFromIntArray.size());
        assertEquals("5", listFromIntArray.getFirst());
    }

    @Test
    void givenDefaultGridWithNewlines_whenListToGridValue_thenNormalizesCells() {
        List<String> values = new ArrayList<>(Collections.nCopies(81, "123\n456\n789"));
        String result = iConverter.listToGridValue(values);
        String[] cells = result.split(",");
        assertEquals(81, cells.length);
        Arrays.stream(cells).forEach(c -> assertEquals("123456789", c));
    }

    @Test
    void givenValidGridValue_whenRenderGridValueAsFormattedSudoku_thenReturnsFormattedString() {
        String csv =
                "0,1,2,7,5,3,6,4,9,"
                        + "9,4,3,6,8,2,1,7,5,"
                        + "6,7,5,4,9,1,2,8,3,"
                        + "1,5,4,2,3,7,8,9,6,"
                        + "3,6,9,8,4,5,7,2,1,"
                        + "2,8,7,1,6,9,5,3,4,"
                        + "5,2,1,9,7,4,3,6,8,"
                        + "4,3,8,5,2,6,9,1,7,"
                        + "7,9,6,3,1,8,4,5,2";

        String expected = "012|753|649\n943|682|175\n675|491|283...";

        String result = iConverter.renderGridValueFormattedAsThreeLinesOfSudoku(csv);
        assertEquals(expected, result);
    }

    @Test
    void givenInvalidGridValueLength_whenRenderGridValueAsFormattedSudoku_thenThrowsException() {
        String invalidCsv = "1,2,3";
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> iConverter.renderGridValueFormattedAsThreeLinesOfSudoku(invalidCsv));
        assertTrue(ex.getMessage().contains("must contain exactly 81 values"));
    }

    @Test
    void givenBlankOrNullGridValue_whenRenderGridValueAsFormattedSudoku_thenReturnsEmpty() {
        assertEquals("", iConverter.renderGridValueFormattedAsThreeLinesOfSudoku(""));
        assertEquals("", iConverter.renderGridValueFormattedAsThreeLinesOfSudoku(null));
    }
}
