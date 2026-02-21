/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.benchmark;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import fr.softsf.sudokufx.common.util.sudoku.GridMaster;

/**
 * Classe de benchmark pour mesurer les performances des méthodes principales de la classe
 * GridMaster (génération et résolution de grilles de Sudoku).
 */
// Mesure le temps moyen d'exécution de la méthode benchmarkée
@BenchmarkMode(Mode.AverageTime)
// Affiche les résultats en microsecondes pour des opérations potentiellement longues
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// 3 itérations d'échauffement de 5 secondes chacune
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
// 5 itérations de mesure de 10 secondes chacune
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
// Exécute le benchmark avec 1 thread pour simplifier l'analyse des résultats
@Threads(1)
// Utilise 2 forks pour améliorer la fiabilité des résultats
@Fork(2)
public class BenchGridMaster {

    private static final int NOMBRE_CASES = 81;
    private static final int NIVEAU_DIFFICULTE_MOYEN = 2;

    /**
     * Grille spécifique fournie pour un benchmark reproductible. Cette grille contient déjà 70
     * cases vides (marquées par 0).
     */
    private static final int[] GRILLE_A_BENCHMARKER = {
        0, 0, 0, 0, 0, 0, 0, 0, 2,
        0, 0, 0, 0, 0, 0, 0, 0, 8,
        0, 0, 0, 0, 0, 0, 0, 0, 7,
        0, 0, 0, 0, 0, 0, 0, 0, 3,
        0, 0, 0, 0, 0, 0, 0, 0, 1,
        0, 0, 0, 0, 0, 0, 0, 5, 6,
        0, 0, 0, 0, 0, 0, 0, 0, 4,
        0, 0, 0, 0, 0, 0, 0, 3, 5,
        0, 0, 0, 0, 0, 0, 0, 0, 9
    };

    /**
     * Contient l'état de la grille et l'instance de GridMaster pour le benchmark. Cette classe est
     * instanciée une seule fois par thread de benchmark.
     */
    @State(Scope.Benchmark)
    public static class GridState {
        public int[] initialGrid;
        public GridMaster gridMaster;

        /**
         * Configure l'état du benchmark avant chaque essai complet (Level.Trial). * Démarche pour
         * reproduire un setup avec validation (si besoin de tester l'impact du validateur) : 1.
         * Rendre le constructeur de GridMaster public. 2. Instancier GridMaster avec un
         * JakartaValidator encapsulant un Validator "No-Op". 3. Implémenter les méthodes de
         * l'interface Validator (Set.of() pour les validations, null pour le reste). * Puis la
         * grille est chargée directement.
         */
        @Setup(Level.Trial)
        public void setup() {
            // Chargement direct de la grille à partir de la constante de référence
            initialGrid = Arrays.copyOf(GRILLE_A_BENCHMARKER, NOMBRE_CASES);
        }
    }

    /**
     * Mesure la performance de la méthode resoudreLaGrille. Utilise la grille fixe pour un test
     * reproductible.
     *
     * @param state l'état de la grille et l'instance de GridMaster.
     * @param bh le Blackhole pour consommer le résultat.
     */
    @Benchmark
    public void measureResoudreLaGrille(GridState state, Blackhole bh) {
        bh.consume(state.gridMaster.resoudreLaGrille(state.initialGrid));
    }

    /**
     * Mesure la performance de la méthode creerLesGrilles.
     *
     * @param state l'état de la grille et l'instance de GridMaster.
     * @param bh le Blackhole pour consommer le résultat.
     */
    @Benchmark
    public void measureCreerLesGrilles(GridState state, Blackhole bh) {
        bh.consume(state.gridMaster.creerLesGrilles(NIVEAU_DIFFICULTE_MOYEN, -1));
    }
}
