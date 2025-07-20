/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.benchmark;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import fr.softsf.sudokufx.common.enums.SecureRandomGenerator;

/**
 * Classe de benchmark pour mesurer les performances des opérations sur la grille Sudoku,
 * notamment le masquage de cases dans une grille résolue.
 * Utilise JMH pour exécuter des tests de performance en mesurant le temps moyen d'exécution.
 */
@BenchmarkMode(Mode.AverageTime) // Mesure le temps moyen d'exécution de la méthode benchmarkée
@OutputTimeUnit(TimeUnit.NANOSECONDS) // Affiche les résultats en nanosecondes pour une précision élevée
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS) // 3 itérations d'échauffement de 5 secondes chacune
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS) // 5 itérations de mesure de 10 secondes chacune
@Threads(1) // Exécute le benchmark avec 1 thread pour simplifier l'analyse des résultats
@Fork(2) // Utilise 2 forks pour améliorer la fiabilité des résultats
public class BenchGridMaster {

    private static final int[] DEFAULT_INDICES = IntStream.range(0, 81).toArray();
    private static final int NOMBRE_CASES = 81;
    private static final int[] grilleResolue = {
        3, 5, 9, 4, 6, 7, 2, 8, 1,
        7, 6, 8, 5, 2, 1, 9, 4, 3,
        4, 2, 1, 9, 8, 3, 5, 7, 6,
        8, 3, 7, 1, 5, 2, 4, 6, 9,
        2, 4, 6, 7, 3, 9, 8, 1, 5,
        1, 9, 5, 8, 4, 6, 7, 3, 2,
        6, 8, 4, 3, 9, 5, 1, 2, 7,
        9, 7, 3, 2, 1, 8, 6, 5, 4,
        5, 1, 2, 6, 7, 4, 3, 9, 8
    };

    /**
     * Cache un nombre spécifié de cases dans la grille en les remplaçant par zéro.
     *
     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille (au maximum 81 cases).
     * @param grilleAResoudre Le tableau représentant la grille, où les cases sélectionnées seront
     *     mises à 0.
     */
    private Object cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, NOMBRE_CASES);
        int[] indices = Arrays.copyOf(DEFAULT_INDICES, NOMBRE_CASES);
        for (int i = 0; i < nombreDeCasesACacher; i++) {
            int randomIndex = SecureRandomGenerator.INSTANCE.nextInt(NOMBRE_CASES - i);
            int indexToHide = indices[randomIndex];
            grilleAResoudre[indexToHide] = 0;
            indices[randomIndex] = indices[NOMBRE_CASES - 1 - i];
        }
        return null;
    }

    @Benchmark
    public void measureSudo(Blackhole bh) {
        bh.consume(cacherLesCases(50, Arrays.copyOf(grilleResolue, NOMBRE_CASES)));
    }
}
