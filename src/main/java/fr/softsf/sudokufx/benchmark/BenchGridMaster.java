package fr.softsf.sudokufx.benchmark;

import fr.softsf.sudokufx.enums.SecureRandomGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime) // Mesure le temps moyen d'exécution de la méthode benchmarkée
@OutputTimeUnit(TimeUnit.NANOSECONDS) // Affiche les résultats en nanosecondes pour une précision élevée
@Warmup(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
// 3 itérations d'échauffement de 10 secondes chacune pour stabiliser les performances
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
// 5 itérations de mesure de 10 secondes chacune pour obtenir des résultats fiables
@Threads(1) // Exécute le benchmark avec 1 thread pour simplifier l'analyse des résultats
@Fork(1) // Utilise 1 fork pour améliorer la fiabilité des résultats en isolant l'exécution
public class BenchGridMaster {

    private static final int[] DEFAULT_INDICES = IntStream.range(0, 81).toArray();

    private static final int[] grilleResolue =
            {
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

//    /**
//     * Cache un nombre spécifié de cases dans la grille en les remplaçant par zéro.
//     *
//     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille (au maximum 81 cases).
//     * @param grilleAResoudre      Le tableau représentant la grille, où les cases sélectionnées seront mises à 0.
//     * @return
//     */
//    private Object cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
//        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, grilleAResoudre.length);
//        int[] indices = Arrays.copyOf(DEFAULT_INDICES, 81);
//        for (int i = 0; i < nombreDeCasesACacher; i++) {
//            int randomIndex = SecureRandomGenerator.INSTANCE.nextInt(grilleAResoudre.length - i);
//            int indexToHide = indices[randomIndex];
//            grilleAResoudre[indexToHide] = 0;
//            indices[randomIndex] = indices[grilleAResoudre.length - 1 - i];
//        }
//        return null;
//    }

    /**
     * Cache un nombre spécifié de cases dans la grille en les remplaçant par zéro.
     *
     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille (au maximum 81 cases).
     * @param grilleAResoudre      Le tableau représentant la grille, où les cases sélectionnées seront mises à 0.
     */
    private Object cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, grilleAResoudre.length);
        int[] indices = IntStream.range(0, grilleAResoudre.length).toArray();
        for (int i = 0; i < nombreDeCasesACacher; i++) {
            int randomIndex = SecureRandomGenerator.INSTANCE.nextInt(grilleAResoudre.length - i);
            int indexToHide = indices[randomIndex];
            grilleAResoudre[indexToHide] = 0;
            indices[randomIndex] = indices[grilleAResoudre.length - 1 - i];
        }
        return null;
    }

    @Benchmark
    public void measureSudo(Blackhole bh) {
        bh.consume(cacherLesCases(50, Arrays.copyOf(grilleResolue, 81)));
    }
}
