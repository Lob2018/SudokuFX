/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.DifficultyLevel;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GridMasterUTest {

    @Autowired private GridMaster gridMaster;

    @Test
    void givenPossibilitiesSum_whenGetPourcentage_thenReturnsClampedValue() {
        assertEquals(0, gridMaster.getPourcentageDepuisPossibilites(IGridMaster.MIN_POSSIBILITES));
        assertEquals(
                100, gridMaster.getPourcentageDepuisPossibilites(IGridMaster.MAX_POSSIBILITES));
        assertEquals(0, gridMaster.getPourcentageDepuisPossibilites(0));
        assertEquals(100, gridMaster.getPourcentageDepuisPossibilites(50000));
        int middleSum = (IGridMaster.MIN_POSSIBILITES + IGridMaster.MAX_POSSIBILITES) / 2;
        assertEquals(50, gridMaster.getPourcentageDepuisPossibilites(middleSum));
    }

    @ParameterizedTest
    @EnumSource(DifficultyLevel.class)
    void givenLevel_whenGetIntervalle_thenReturnsCorrectBounds(DifficultyLevel level) {
        LevelPossibilityBounds bounds = gridMaster.getIntervallePourcentageNiveau(level);
        switch (level) {
            case EASY -> {
                assertEquals(IGridMaster.FACILE_MIN_PERCENT, bounds.min());
                assertEquals(IGridMaster.FACILE_MAX_PERCENT, bounds.max());
            }
            case MEDIUM -> {
                assertEquals(IGridMaster.MOYEN_MIN_PERCENT, bounds.min());
                assertEquals(IGridMaster.MOYEN_MAX_PERCENT, bounds.max());
            }
            case DIFFICULT -> {
                assertEquals(IGridMaster.DIFFICILE_MIN_PERCENT, bounds.min());
                assertEquals(IGridMaster.DIFFICILE_MAX_PERCENT, bounds.max());
            }
        }
    }

    @Test
    void givenCurrentValue_whenCalculerValeurSuperieureDuSegment_thenReturnsCorrectStepOrMax() {
        LevelPossibilityBounds bounds = new LevelPossibilityBounds(20, 26);
        assertEquals(26, gridMaster.calculerValeurSuperieureDuSegment(bounds, 20));
        LevelPossibilityBounds largeBounds = new LevelPossibilityBounds(27, 100);
        assertEquals(40, gridMaster.calculerValeurSuperieureDuSegment(largeBounds, 30));
        assertEquals(100, gridMaster.calculerValeurSuperieureDuSegment(largeBounds, 95));
    }

    private void assertGrillesCreesValides(GrillesCrees grillesCrees) {
        assertNotNull(grillesCrees);
        assertNotNull(grillesCrees.grilleResolue());
        assertNotNull(grillesCrees.grilleAResoudre());
        assertEquals(81, grillesCrees.grilleResolue().length);
        assertEquals(81, grillesCrees.grilleAResoudre().length);
        long countZerosResolved =
                Arrays.stream(grillesCrees.grilleResolue()).filter(v -> v == 0).count();
        assertEquals(0, countZerosResolved);
        long countZerosPuzzle =
                Arrays.stream(grillesCrees.grilleAResoudre()).filter(v -> v == 0).count();
        assertTrue(countZerosPuzzle > 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 300})
    void givenInvalidLevel_whenCreateGrids_thenThrowsIllegalArgumentException(int level) {
        assertThrows(IllegalArgumentException.class, () -> gridMaster.creerLesGrilles(level));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenValidLevel_whenCreateGrids_thenGridsGeneratedSuccessfully(int level) {
        GrillesCrees grillesCrees = gridMaster.creerLesGrilles(level);
        assertGrillesCreesValides(grillesCrees);
    }

    @Test
    void givenNullGrid_whenResoudreLaGrille_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> gridMaster.resoudreLaGrille(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100})
    void givenInvalidGridSize_whenResoudreLaGrille_thenThrowsIllegalArgumentException(int size) {
        int[] grille = new int[size];
        assertThrows(IllegalArgumentException.class, () -> gridMaster.resoudreLaGrille(grille));
    }

    @Test
    void givenValidGrid_whenResolveGrid_thenGridResolvedSuccessfully() {
        int[] toBeResolvedGrid =
                new int[] {
                    5, 3, 4, 6, 7, 8, 9, 0, 2,
                    6, 7, 2, 1, 9, 5, 3, 4, 8,
                    1, 9, 8, 3, 4, 2, 5, 6, 7,
                    8, 5, 9, 7, 6, 1, 4, 2, 3,
                    4, 2, 6, 8, 5, 3, 0, 0, 1,
                    7, 1, 3, 9, 2, 4, 8, 5, 6,
                    9, 6, 1, 5, 3, 7, 2, 8, 4,
                    2, 8, 7, 4, 1, 9, 6, 3, 5,
                    3, 4, 5, 2, 8, 6, 0, 0, 9
                };
        GrilleResolue result = gridMaster.resoudreLaGrille(toBeResolvedGrid);
        assertTrue(result.solved());
        assertEquals(0, result.possibilityPercentage());

        int[] expectedSolution =
                new int[] {
                    5, 3, 4, 6, 7, 8, 9, 1, 2,
                    6, 7, 2, 1, 9, 5, 3, 4, 8,
                    1, 9, 8, 3, 4, 2, 5, 6, 7,
                    8, 5, 9, 7, 6, 1, 4, 2, 3,
                    4, 2, 6, 8, 5, 3, 7, 9, 1,
                    7, 1, 3, 9, 2, 4, 8, 5, 6,
                    9, 6, 1, 5, 3, 7, 2, 8, 4,
                    2, 8, 7, 4, 1, 9, 6, 3, 5,
                    3, 4, 5, 2, 8, 6, 1, 7, 9
                };
        assertArrayEquals(expectedSolution, result.solvedGrid());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenImpossiblePossibilities_whenCreateGrids_thenReturnsEmptyGridAfterFailSafe(int level) {
        switch (level) {
            case 2 -> gridMaster.setAverageImpossiblePossibilitiesForTests();
            case 3 -> gridMaster.setDifficultImpossiblePossibilitiesForTests();
            default -> gridMaster.setEasyImpossiblePossibilitiesForTests();
        }
        GrillesCrees grillesCrees = gridMaster.creerLesGrilles(level);
        long countZerosPuzzle =
                Arrays.stream(grillesCrees.grilleAResoudre()).filter(v -> v == 0).count();
        assertEquals(81, countZerosPuzzle);
    }
}
