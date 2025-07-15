/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GridMasterUTest {

    @Autowired private GridMaster gridMaster;

    private int gridMasterLastLevel = -1;

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 300})
    void givenInvalidLevel_whenCreateGrids_thenThrowsIllegalArgumentException(int level) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> gridMaster.creerLesGrilles(level));
        assertTrue(exception.getMessage().contains("The grid level must be between 1 and 3"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenValidLevel_whenCreateGridsQuickly_thenGridsGeneratedSuccessfully(int level) {
        GrillesCrees grillesCrees = gridMaster.creerLesGrilles(level);
        assertNotNull(grillesCrees);
        assertNotNull(grillesCrees.grilleResolue());
        assertNotNull(grillesCrees.grilleAResoudre());
        assertEquals(81, grillesCrees.grilleResolue().length);
        long countForResolvedGrid =
                Arrays.stream(grillesCrees.grilleResolue()).filter(v -> v == 0).count();
        assertEquals(0, countForResolvedGrid);
        assertEquals(81, grillesCrees.grilleAResoudre().length);
        long countForToBeResolvedGrid =
                Arrays.stream(grillesCrees.grilleAResoudre()).filter(v -> v == 0).count();
        assertNotEquals(0, countForToBeResolvedGrid);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 1, 2, 2, 3, 3})
    void givenValidLevel_whenCreateGridsNormally_thenGridsGeneratedSuccessfully(int level) {
        GrillesCrees grillesCrees = gridMaster.creerLesGrilles(level);
        assertNotNull(grillesCrees);
        assertNotNull(grillesCrees.grilleResolue());
        assertNotNull(grillesCrees.grilleAResoudre());
        assertEquals(81, grillesCrees.grilleResolue().length);
        long countForResolvedGrid =
                Arrays.stream(grillesCrees.grilleResolue()).filter(v -> v == 0).count();
        assertEquals(0, countForResolvedGrid);
        assertEquals(81, grillesCrees.grilleAResoudre().length);
        long countForToBeResolvedGrid =
                Arrays.stream(grillesCrees.grilleAResoudre()).filter(v -> v == 0).count();
        assertNotEquals(0, countForToBeResolvedGrid);
        if (gridMasterLastLevel != level) {
            gridMasterLastLevel = level;
            Awaitility.await()
                    .atMost(650, TimeUnit.MILLISECONDS)
                    .pollDelay(600, TimeUnit.MILLISECONDS)
                    .until(() -> true);
        }
    }

    @Test
    void givenNullGrid_whenResoudreLaGrille_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> gridMaster.resoudreLaGrille(null));
        assertTrue(exception.getMessage().contains("null"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 100})
    void givenInvalidGridSize_whenResoudreLaGrille_thenThrowsIllegalArgumentException(int size) {
        int[] grille = new int[size];
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> gridMaster.resoudreLaGrille(grille));
        assertTrue(exception.getMessage().contains(String.valueOf(size)));
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
        int[] result = gridMaster.resoudreLaGrille(toBeResolvedGrid);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
        assertArrayEquals(
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
                },
                toBeResolvedGrid);
    }

    @Test
    void givenInvalidGrid_whenResolveGrid_thenResolutionFails() {
        int[] toBeResolvedGrid =
                new int[] {
                    0, 0, 4, 6, 7, 8, 9, 0, 0,
                    6, 0, 2, 1, 9, 5, 0, 4, 0,
                    1, 9, 0, 3, 4, 2, 5, 6, 0,
                    8, 5, 9, 7, 0, 1, 4, 2, 0,
                    4, 0, 6, 8, 0, 3, 0, 0, 0,
                    0, 1, 3, 9, 0, 4, 8, 5, 0,
                    0, 6, 1, 5, 3, 7, 2, 8, 0,
                    2, 0, 7, 4, 1, 9, 6, 3, 0,
                    0, 4, 5, 2, 8, 6, 0, 8, 0
                };
        int[] result = gridMaster.resoudreLaGrille(toBeResolvedGrid);
        assertEquals(-1, result[0]);
        assertEquals(0, result[1]);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void givenImpossiblePossibilitiesForLevels_whenCreateGrids_thenGridsGeneratedAnyway(int level) {
        switch (level) {
            case 2 -> {
                gridMaster.setAverageImpossiblePossibilitiesForTests();
                assertEquals(50000, gridMaster.getMoyenMinPossibilites());
                assertEquals(-1, gridMaster.getMoyenMaxPossibilites());
            }
            case 3 -> {
                gridMaster.setDifficultImpossiblePossibilitiesForTests();
                assertEquals(50000, gridMaster.getMoyenMaxPossibilites());
            }
            default -> {
                gridMaster.setEasyImpossiblePossibilitiesForTests();
                assertEquals(-1, gridMaster.getMoyenMinPossibilites());
            }
        }
        GrillesCrees grillesCrees = gridMaster.creerLesGrilles(level);
        assertNotNull(grillesCrees);
        assertNotNull(grillesCrees.grilleResolue());
        assertNotNull(grillesCrees.grilleAResoudre());
        assertEquals(81, grillesCrees.grilleResolue().length);
        long countForResolvedGrid =
                Arrays.stream(grillesCrees.grilleResolue()).filter(v -> v == 0).count();
        assertEquals(0, countForResolvedGrid);
        assertEquals(81, grillesCrees.grilleAResoudre().length);
        long countForToBeResolvedGrid =
                Arrays.stream(grillesCrees.grilleAResoudre()).filter(v -> v == 0).count();
        assertNotEquals(0, countForToBeResolvedGrid);
    }
}
