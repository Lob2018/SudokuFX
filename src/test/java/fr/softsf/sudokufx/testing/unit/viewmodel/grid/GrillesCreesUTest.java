/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel.grid;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.util.sudoku.GrillesCrees;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GrillesCreesUTest {

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
        GrillesCrees grilles = new GrillesCrees(VALID_GRID, VALID_GRID, 50);
        Set<ConstraintViolation<GrillesCrees>> violations = validator.validate(grilles);
        assertTrue(violations.isEmpty());
    }

    @Test
    void givenInvalidGrilleResolueLength_whenValidate_thenViolation() {
        int[] shortGrid = new int[80];
        GrillesCrees grilles = new GrillesCrees(shortGrid, VALID_GRID, 50);
        Set<ConstraintViolation<GrillesCrees>> violations = validator.validate(grilles);
        assertFalse(violations.isEmpty());
        assertThat(violations.iterator().next().getMessage())
                .contains("must contain exactly 81 elements");
    }

    @Test
    void givenInvalidGrilleAResoudreLength_whenValidate_thenViolation() {
        int[] longGrid = new int[82];
        GrillesCrees grilles = new GrillesCrees(VALID_GRID, longGrid, 50);
        Set<ConstraintViolation<GrillesCrees>> violations = validator.validate(grilles);
        assertFalse(violations.isEmpty());
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenPourcentageDesPossibilitesBelowZero_whenValidate_thenViolation() {
        GrillesCrees grilles = new GrillesCrees(VALID_GRID, VALID_GRID, -1);
        Set<ConstraintViolation<GrillesCrees>> violations = validator.validate(grilles);
        assertFalse(violations.isEmpty());
        assertThat(violations.iterator().next().getMessage()).contains(">= 0");
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenPourcentageDesPossibilitesAboveHundred_whenValidate_thenViolation() {
        GrillesCrees grilles = new GrillesCrees(VALID_GRID, VALID_GRID, 101);
        Set<ConstraintViolation<GrillesCrees>> violations = validator.validate(grilles);
        assertFalse(violations.isEmpty());
        assertThat(violations.iterator().next().getMessage()).contains("<= 100");
    }

    @Test
    void givenSameValues_whenEqualsAndHashCode_thenReturnTrue() {
        GrillesCrees g1 = new GrillesCrees(VALID_GRID, VALID_GRID, 50);
        GrillesCrees g2 = new GrillesCrees(VALID_GRID, VALID_GRID, 50);
        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void givenDifferentValues_whenEquals_thenReturnFalse() {
        GrillesCrees g1 = new GrillesCrees(VALID_GRID, VALID_GRID, 50);
        GrillesCrees g2 = new GrillesCrees(OTHER_GRID, VALID_GRID, 50);
        assertNotEquals(g1, g2);
    }

    @Test
    void givenValidRecord_whenToString_thenContainsFields() {
        GrillesCrees grilles = new GrillesCrees(VALID_GRID, VALID_GRID, 50);
        String str = grilles.toString();
        assertTrue(str.contains("grilleResolue"));
        assertTrue(str.contains("grilleAResoudre"));
        assertTrue(str.contains("pourcentageDesPossibilites"));
    }
}
