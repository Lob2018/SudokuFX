/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridUTest {

    @Test
    @DisplayName("Should create Grid with constructor")
    void givenValidParameters_whenCreateGridWithConstructor_thenGridIsCreatedCorrectly() {
        Long gridId = 1L;
        String defaultGridValue = "123456789";
        String gridValue = "987654321";
        byte possibilities = 50;
        Grid grid = new Grid(gridId, defaultGridValue, gridValue, possibilities);
        assertEquals(gridId, grid.getGridid());
        assertEquals(defaultGridValue, grid.getDefaultgridvalue());
        assertEquals(gridValue, grid.getGridvalue());
        assertEquals(possibilities, grid.getPossibilities());
    }

    @Test
    @DisplayName("Should create Grid with default constructor")
    void givenNothing_whenCreateGridWithDefaultConstructor_thenGridHasDefaultValues() {
        Grid grid = new Grid();
        assertNull(grid.getGridid());
        assertEquals("", grid.getDefaultgridvalue());
        assertEquals("", grid.getGridvalue());
        assertEquals(0, grid.getPossibilities());
    }

    @Test
    @DisplayName("Should create Grid with builder")
    void givenAllFields_whenCreateGridWithBuilder_thenGridIsCreatedCorrectly() {
        Long gridId = 5L;
        String defaultGridValue = "123456789";
        String gridValue = "987654321";
        byte possibilities = 25;
        Grid grid =
                Grid.builder()
                        .gridid(gridId)
                        .defaultgridvalue(defaultGridValue)
                        .gridvalue(gridValue)
                        .possibilities(possibilities)
                        .build();
        assertEquals(gridId, grid.getGridid());
        assertEquals(defaultGridValue, grid.getDefaultgridvalue());
        assertEquals(gridValue, grid.getGridvalue());
        assertEquals(possibilities, grid.getPossibilities());
    }

    @Test
    @DisplayName("Should throw NullPointerException when defaultgridvalue is null")
    @SuppressWarnings("DataFlowIssue")
    void givenNullDefaultgridvalue_whenCreateGrid_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class, () -> new Grid(1L, null, "grid", (byte) 50));
        assertEquals("defaultgridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NullPointerException when gridvalue is null")
    @SuppressWarnings("DataFlowIssue")
    void givenNullGridvalue_whenCreateGrid_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class, () -> new Grid(1L, "default", null, (byte) 50));
        assertEquals("gridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should update values using setters")
    void givenExistingGrid_whenUpdateValues_thenValuesAreUpdated() {
        Grid grid = new Grid(1L, "original", "original", (byte) 10);
        String newDefaultGridValue = "updated";
        String newGridValue = "updated";
        byte newPossibilities = 75;
        grid.setDefaultgridvalue(newDefaultGridValue);
        grid.setGridvalue(newGridValue);
        grid.setPossibilities(newPossibilities);
        assertEquals(newDefaultGridValue, grid.getDefaultgridvalue());
        assertEquals(newGridValue, grid.getGridvalue());
        assertEquals(newPossibilities, grid.getPossibilities());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null values")
    @SuppressWarnings("DataFlowIssue")
    void givenExistingGrid_whenSetNullValues_thenThrowsNullPointerException() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 10);
        assertThrows(NullPointerException.class, () -> grid.setDefaultgridvalue(null));
        assertThrows(NullPointerException.class, () -> grid.setGridvalue(null));
    }

    @Test
    @DisplayName("Should implement equals correctly")
    @SuppressWarnings({"EqualsWithItself"})
    void givenTwoGrids_whenCompareWithEquals_thenEqualsWorksCorrectly() {
        Grid grid1 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid2 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid3 = new Grid(1L, "different", "grid", (byte) 50);
        Grid grid4 = new Grid(2L, "default", "grid", (byte) 50);
        assertEquals(grid1, grid2);
        assertNotEquals(grid1, grid3);
        assertNotEquals(grid1, grid4);
        assertEquals(grid1, grid1);
        assertNotEquals(null, grid1);
        boolean equalsObject = grid1.equals(new Object());
        assertFalse(equalsObject);
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void givenGridsWithSameAndDifferentValues_whenGetHashCode_thenHashCodeIsConsistent() {
        Grid grid1 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid2 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid3 = new Grid(1L, "different", "grid", (byte) 50);
        assertEquals(grid1.hashCode(), grid2.hashCode());
        assertNotEquals(grid1.hashCode(), grid3.hashCode());
        assertEquals(grid1.hashCode(), grid1.hashCode());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void givenGridWithValues_whenCallToString_thenCorrectStringRepresentationIsReturned() {
        Grid grid = new Grid(5L, "default123", "grid456", (byte) 75);
        String result = grid.toString();
        assertEquals(
                "Grid{gridid=5, defaultgridvalue='default123', gridvalue='grid456',"
                        + " possibilities=75}",
                result);
    }

    @Test
    @DisplayName("Should handle null gridid")
    void givenNullGridid_whenCreateGrid_thenNullGrididIsHandled() {
        Grid grid = new Grid(null, "default", "grid", (byte) 25);
        assertNull(grid.getGridid());
        assertEquals("default", grid.getDefaultgridvalue());
        assertEquals("grid", grid.getGridvalue());
        assertEquals(25, grid.getPossibilities());
        assertEquals(
                "Grid{gridid=null, defaultgridvalue='default', gridvalue='grid', possibilities=25}",
                grid.toString());
    }

    @Test
    @DisplayName("Should handle boundary values")
    void givenBoundaryValues_whenCreateGrid_thenBoundaryValuesAreAccepted() {
        assertDoesNotThrow(
                () -> {
                    new Grid(1L, "", "", (byte) 0);
                    new Grid(2L, "A".repeat(81), "B".repeat(810), (byte) 100);
                    new Grid(Long.MAX_VALUE, "test", "value", Byte.MAX_VALUE);
                });
    }

    @Test
    @DisplayName("Should handle builder with partial fields")
    void givenBuilderWithPartialFields_whenBuild_thenCreatesGridWithDefaults() {
        Grid grid = Grid.builder().possibilities((byte) 30).build();
        assertNull(grid.getGridid());
        assertEquals("", grid.getDefaultgridvalue());
        assertEquals("", grid.getGridvalue());
        assertEquals(30, grid.getPossibilities());
    }

    @Test
    @DisplayName("Should throw exception when builder has null defaultgridvalue")
    @SuppressWarnings("DataFlowIssue")
    void givenBuilderWithNullDefaultgridvalue_whenBuild_thenThrowsNullPointerException() {
        Grid.GridBuilder builder = Grid.builder().defaultgridvalue(null).gridvalue("value");
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    @DisplayName("Should throw exception when builder has null gridvalue")
    @SuppressWarnings("DataFlowIssue")
    void givenBuilderWithNullGridvalue_whenBuild_thenThrowsNullPointerException() {
        Grid.GridBuilder builder = Grid.builder().defaultgridvalue("default").gridvalue(null);
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    @DisplayName("Should not have gridid setter")
    @SuppressWarnings("JavaReflectionMemberAccess")
    void givenGridClass_whenCheckForGrididSetter_thenSetterDoesNotExist() {
        assertThrows(
                NoSuchMethodException.class, () -> Grid.class.getMethod("setGridid", Long.class));
    }
}
