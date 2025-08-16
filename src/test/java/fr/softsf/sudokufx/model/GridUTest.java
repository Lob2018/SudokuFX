/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridUTest {

    @Test
    @DisplayName("Given valid parameters when creating Grid then should set correct values")
    void givenValidParameters_whenCreatingGrid_thenShouldSetCorrectValues() {
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
    @DisplayName("Given default constructor when creating Grid then should have default values")
    void givenDefaultConstructor_whenCreatingGrid_thenShouldHaveDefaultValues() {
        Grid grid = new Grid();
        assertNull(grid.getGridid());
        assertEquals("", grid.getDefaultgridvalue());
        assertEquals("", grid.getGridvalue());
        assertEquals(0, grid.getPossibilities());
    }

    @Test
    @DisplayName(
            "Given null defaultgridvalue when creating Grid then should throw NullPointerException")
    @SuppressWarnings("DataFlowIssue") // Nécessaire pour tester la validation null
    void givenNullDefaultgridvalue_whenCreatingGrid_thenShouldThrowNullPointerException() {
        Long gridId = 1L;
        String defaultGridValue = null;
        String gridValue = "987654321";
        byte possibilities = 50;
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> new Grid(gridId, defaultGridValue, gridValue, possibilities));
        assertEquals("defaultgridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Given null gridvalue when creating Grid then should throw NullPointerException")
    @SuppressWarnings("DataFlowIssue") // Nécessaire pour tester la validation null
    void givenNullGridvalue_whenCreatingGrid_thenShouldThrowNullPointerException() {
        Long gridId = 1L;
        String defaultGridValue = "123456789";
        String gridValue = null;
        byte possibilities = 50;
        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> new Grid(gridId, defaultGridValue, gridValue, possibilities));
        assertEquals("gridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName(
            "Given existing Grid when setting new defaultgridvalue then should update correctly")
    void givenExistingGrid_whenSettingNewDefaultgridvalue_thenShouldUpdateCorrectly() {
        Grid grid = new Grid(1L, "original", "grid", (byte) 10);
        String newDefaultGridValue = "updated";
        grid.setDefaultgridvalue(newDefaultGridValue);
        assertEquals(newDefaultGridValue, grid.getDefaultgridvalue());
    }

    @Test
    @DisplayName("Given existing Grid when setting new gridvalue then should update correctly")
    void givenExistingGrid_whenSettingNewGridvalue_thenShouldUpdateCorrectly() {
        Grid grid = new Grid(1L, "default", "original", (byte) 10);
        String newGridValue = "updated";
        grid.setGridvalue(newGridValue);
        assertEquals(newGridValue, grid.getGridvalue());
    }

    @Test
    @DisplayName("Given existing Grid when setting new possibilities then should update correctly")
    void givenExistingGrid_whenSettingNewPossibilities_thenShouldUpdateCorrectly() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 10);
        byte newPossibilities = 75;
        grid.setPossibilities(newPossibilities);
        assertEquals(newPossibilities, grid.getPossibilities());
    }

    @Test
    @DisplayName(
            "Given Grid when setting null defaultgridvalue then should throw NullPointerException")
    @SuppressWarnings("DataFlowIssue")
    void givenGrid_whenSettingNullDefaultgridvalue_thenShouldThrowNullPointerException() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 10);
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> grid.setDefaultgridvalue(null));
        assertEquals("defaultgridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Given Grid when setting null gridvalue then should throw NullPointerException")
    @SuppressWarnings("DataFlowIssue")
    void givenGrid_whenSettingNullGridvalue_thenShouldThrowNullPointerException() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 10);
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> grid.setGridvalue(null));
        assertEquals("gridvalue must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Given builder with all fields when building then should create complete Grid")
    void givenBuilderWithAllFields_whenBuilding_thenShouldCreateCompleteGrid() {
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
    @DisplayName(
            "Given builder with partial fields when building then should create Grid with defaults")
    void givenBuilderWithPartialFields_whenBuilding_thenShouldCreateGridWithDefaults() {
        byte possibilities = 30;
        Grid grid = Grid.builder().possibilities(possibilities).build();
        assertNull(grid.getGridid());
        assertEquals("", grid.getDefaultgridvalue());
        assertEquals("", grid.getGridvalue());
        assertEquals(possibilities, grid.getPossibilities());
    }

    @Test
    @DisplayName("Given builder methods when chaining then should not throw exception")
    void givenBuilderMethods_whenChaining_thenShouldNotThrowException() {
        assertDoesNotThrow(
                () -> {
                    Grid.GridBuilder builder =
                            Grid.builder()
                                    .gridid(1L)
                                    .defaultgridvalue("test")
                                    .gridvalue("value")
                                    .possibilities((byte) 50);
                    assertNotNull(builder);
                });
    }

    @Test
    @DisplayName(
            "Given builder with null values when building then should throw NullPointerException")
    @SuppressWarnings("DataFlowIssue") // Nécessaire pour tester la validation null
    void givenBuilderWithNullValues_whenBuilding_thenShouldThrowNullPointerException() {
        Grid.GridBuilder builderWithNullDefault =
                Grid.builder().defaultgridvalue(null).gridvalue("value");
        assertThrows(NullPointerException.class, builderWithNullDefault::build);
        Grid.GridBuilder builderWithNullGrid =
                Grid.builder().defaultgridvalue("default").gridvalue(null);
        assertThrows(NullPointerException.class, builderWithNullGrid::build);
    }

    @Test
    @DisplayName("Given same Grid reference when comparing with equals then should return true")
    @SuppressWarnings("EqualsWithItself") // Nécessaire pour la couverture de code
    void givenSameGridReference_whenComparingWithEquals_thenShouldReturnTrue() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 50);
        assertEquals(grid, grid);
    }

    @Test
    @DisplayName(
            "Given two Grids with same values when comparing with equals then should return true")
    void givenTwoGridsWithSameValues_whenComparingWithEquals_thenShouldReturnTrue() {
        Grid grid1 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid2 = new Grid(1L, "default", "grid", (byte) 50);
        assertEquals(grid1, grid2);
    }

    @Test
    @DisplayName(
            "Given two Grids with different values when comparing with equals then should return"
                    + " false")
    void givenTwoGridsWithDifferentValues_whenComparingWithEquals_thenShouldReturnFalse() {
        Grid grid1 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid2 = new Grid(1L, "different", "grid", (byte) 50);
        Grid grid3 = new Grid(1L, "default", "different", (byte) 50);
        Grid grid4 = new Grid(1L, "default", "grid", (byte) 75);
        Grid grid5 = new Grid(2L, "default", "grid", (byte) 50);
        assertNotEquals(grid1, grid2);
        assertNotEquals(grid1, grid3);
        assertNotEquals(grid1, grid4);
        assertNotEquals(grid1, grid5);
    }

    @Test
    @DisplayName("Given null object when comparing with equals then should return false")
    void givenNullObject_whenComparingWithEquals_thenShouldReturnFalse() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 50);
        assertNotNull(grid);
    }

    @Test
    @DisplayName("Given different type object when comparing with equals then should return false")
    void givenDifferentTypeObject_whenComparingWithEquals_thenShouldReturnFalse() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 50);
        boolean result = grid.equals(new Object());
        assertFalse(result);
    }

    @Test
    @DisplayName(
            "Given same Grid when calling hashCode multiple times then should return consistent"
                    + " values")
    void givenSameGrid_whenCallingHashCodeMultipleTimes_thenShouldReturnConsistentValues() {
        Grid grid = new Grid(1L, "default", "grid", (byte) 50);
        int hash1 = grid.hashCode();
        int hash2 = grid.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Given two equal Grids when calling hashCode then should return equal values")
    void givenTwoEqualGrids_whenCallingHashCode_thenShouldReturnEqualValues() {
        Grid grid1 = new Grid(1L, "default", "grid", (byte) 50);
        Grid grid2 = new Grid(1L, "default", "grid", (byte) 50);
        assertEquals(grid1.hashCode(), grid2.hashCode());
    }

    @Test
    @DisplayName(
            "Given Grid with values when calling toString then should contain all field values")
    void givenGridWithValues_whenCallingToString_thenShouldContainAllFieldValues() {
        Long gridId = 5L;
        String defaultGridValue = "default123";
        String gridValue = "grid456";
        byte possibilities = 75;
        Grid grid = new Grid(gridId, defaultGridValue, gridValue, possibilities);
        String result = grid.toString();
        assertTrue(result.contains("gridid=5"));
        assertTrue(result.contains("defaultgridvalue='default123'"));
        assertTrue(result.contains("gridvalue='grid456'"));
        assertTrue(result.contains("possibilities=75"));
        assertTrue(result.contains("Grid"));
    }

    @Test
    @DisplayName(
            "Given Grid with null gridid when calling toString then should handle null correctly")
    void givenGridWithNullGridid_whenCallingToString_thenShouldHandleNullCorrectly() {
        Grid grid = new Grid(null, "default", "grid", (byte) 25);
        String result = grid.toString();
        assertTrue(result.contains("gridid=null"));
        assertTrue(result.contains("defaultgridvalue='default'"));
        assertTrue(result.contains("gridvalue='grid'"));
        assertTrue(result.contains("possibilities=25"));
    }

    @Test
    @DisplayName("Given valid boundary values when creating Grid then should not throw exception")
    void givenValidBoundaryValues_whenCreatingGrid_thenShouldNotThrowException() {
        assertDoesNotThrow(
                () -> {
                    new Grid(1L, "", "", (byte) 0);
                    new Grid(2L, "test", "value", (byte) 50);
                    new Grid(3L, "boundary", "test", (byte) 100);
                });
    }

    @Test
    @DisplayName("Given builder with extreme values when building then should handle correctly")
    void givenBuilderWithExtremeValues_whenBuilding_thenShouldHandleCorrectly() {
        Grid grid =
                Grid.builder()
                        .gridid(Long.MAX_VALUE)
                        .defaultgridvalue("A".repeat(81))
                        .gridvalue("B".repeat(810))
                        .possibilities(Byte.MAX_VALUE)
                        .build();
        assertEquals(Long.MAX_VALUE, grid.getGridid());
        assertEquals("A".repeat(81), grid.getDefaultgridvalue());
        assertEquals("B".repeat(810), grid.getGridvalue());
        assertEquals(Byte.MAX_VALUE, grid.getPossibilities());
    }

    @Test
    @DisplayName("Given constructed Grid when trying to access gridid setter then should not exist")
    void givenConstructedGrid_whenTryingToAccessGrididSetter_thenShouldNotExist() {
        Long originalId = 10L;
        Grid grid = new Grid(originalId, "default", "grid", (byte) 25);
        assertEquals(originalId, grid.getGridid());
        assertThrows(
                NoSuchMethodException.class,
                () -> {
                    Grid.class.getMethod("setGridid", Long.class);
                });
    }
}
