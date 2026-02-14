/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.viewmodel.grid;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.util.sudoku.GrilleResolue;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.assertions.api.Assertions.assertThat;

@SpringBootTest(classes = {SudoMain.class})
class GrilleResolueUTest {

    @Autowired private Validator validator;

    private static final int[] VALID_GRID;

    static {
        VALID_GRID = new int[81];
        Arrays.fill(VALID_GRID, 1);
    }

    private static final int[] OTHER_GRID;

    static {
        OTHER_GRID = new int[81];
        Arrays.fill(OTHER_GRID, 2);
    }

    @Test
    void givenValidRecord_whenValidate_thenNoViolations() {
        GrilleResolue grille = new GrilleResolue(true, VALID_GRID, 50);
        Set<ConstraintViolation<GrilleResolue>> violations = validator.validate(grille);
        assertTrue(violations.isEmpty());
    }

    @Test
    void givenInvalidSolvedGridLength_whenConstruct_thenThrowException() {
        int[] shortGrid = new int[80];
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new GrilleResolue(true, shortGrid, 50));
        assertThat(exception.getMessage())
                .contains(
                        "Invalid solvedGrid: must contain exactly "
                                + GrilleResolue.CASES_NUMBER
                                + " elements (found: "
                                + shortGrid.length
                                + ")");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenNullSolvedGrid_whenConstruct_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new GrilleResolue(true, null, 50));
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenPossibilityPercentageBelowZero_whenValidate_thenViolation() {
        GrilleResolue grille = new GrilleResolue(true, VALID_GRID, -1);
        Set<ConstraintViolation<GrilleResolue>> violations = validator.validate(grille);
        assertFalse(violations.isEmpty());
        assertThat(violations.iterator().next().getMessage()).contains(">= 0");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenPossibilityPercentageAboveHundred_whenValidate_thenViolation() {
        GrilleResolue grille = new GrilleResolue(true, VALID_GRID, 101);
        Set<ConstraintViolation<GrilleResolue>> violations = validator.validate(grille);
        assertFalse(violations.isEmpty());
        assertThat(violations.iterator().next().getMessage()).contains("<= 100");
    }

    @Test
    void givenSameValues_whenEqualsAndHashCode_thenReturnTrue() {
        GrilleResolue g1 = new GrilleResolue(true, VALID_GRID, 50);
        GrilleResolue g2 = new GrilleResolue(true, VALID_GRID, 50);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void givenDifferentValues_whenEquals_thenReturnFalse() {
        GrilleResolue g1 = new GrilleResolue(true, VALID_GRID, 50);
        GrilleResolue g2 = new GrilleResolue(false, OTHER_GRID, 50);
        assertNotEquals(g1, g2);
    }

    @Test
    void givenValidRecord_whenToString_thenContainsFields() {
        GrilleResolue grille = new GrilleResolue(true, VALID_GRID, 50);
        String str = grille.toString();
        assertTrue(str.contains("solved="));
        assertTrue(str.contains("solvedGrid="));
        assertTrue(str.contains("possibilityPercentage="));
    }
}
