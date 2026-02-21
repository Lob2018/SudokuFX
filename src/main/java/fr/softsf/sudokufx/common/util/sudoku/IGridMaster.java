/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import jakarta.validation.ConstraintViolationException;

/** Interface defining methods to generate and solve Sudoku puzzles */
public sealed interface IGridMaster permits GridMaster {

    int POURCENTAGE_MAX = 100;
    int DESIRED_POSSIBILITIES_STEP = 10;

    int MIN_POSSIBILITES = 4800;
    int MOYEN_MIN_POSSIBILITES = 10500;
    int MOYEN_MAX_POSSIBILITES = 12500;
    int MAX_POSSIBILITES = 33000;
    // Nombre de cases à cacher en fonction du niveau
    int FACILE_MIN_CACHEES = 34;
    int MOYEN_MIN_CACHEES = 39;
    int MOYEN_MAX_CACHEES = 44;
    int DIFFICILE_MAX_CACHEES = 48;
    // Pourcentage de possibilités par niveau
    int FACILE_MIN_PERCENT = 0;
    int FACILE_MAX_PERCENT = 19;
    int MOYEN_MIN_PERCENT = 20;
    int MOYEN_MAX_PERCENT = 26;
    int DIFFICILE_MIN_PERCENT = 27;
    int DIFFICILE_MAX_PERCENT = 100;

    /**
     * Calcule le pourcentage de difficultés basé sur la somme des possibilités.
     *
     * @param sommeDesPossibilites La somme brute calculée.
     * @return Le pourcentage entre 0 et 100.
     */
    default int getPourcentageDepuisPossibilites(int sommeDesPossibilites) {
        int pourcentage =
                ((sommeDesPossibilites - MIN_POSSIBILITES) * POURCENTAGE_MAX)
                        / (MAX_POSSIBILITES - MIN_POSSIBILITES);
        return Math.clamp(pourcentage, 0, POURCENTAGE_MAX);
    }

    /**
     * Calcule la somme des possibilités basée sur un pourcentage de difficulté.
     *
     * @param pourcentage Le pourcentage de difficulté (0 à 100).
     * @return La somme brute des possibilités correspondante.
     */
    default int getPossibilitesDepuisPourcentage(int pourcentage) {
        int somme =
                MIN_POSSIBILITES
                        + (pourcentage * (MAX_POSSIBILITES - MIN_POSSIBILITES)) / POURCENTAGE_MAX;
        return Math.clamp(somme, MIN_POSSIBILITES, MAX_POSSIBILITES);
    }

    /**
     * Récupère l'intervalle de pourcentages autorisé pour un niveau de difficulté donné.
     *
     * @param level le niveau de difficulté cible
     * @return un objet {@link LevelPossibilityBounds} définissant le min et le max du niveau
     */
    default LevelPossibilityBounds getIntervallePourcentageNiveau(DifficultyLevel level) {
        return switch (level) {
            case EASY -> new LevelPossibilityBounds(FACILE_MIN_PERCENT, FACILE_MAX_PERCENT);
            case MEDIUM -> new LevelPossibilityBounds(MOYEN_MIN_PERCENT, MOYEN_MAX_PERCENT);
            case DIFFICULT ->
                    new LevelPossibilityBounds(DIFFICILE_MIN_PERCENT, DIFFICILE_MAX_PERCENT);
        };
    }

    /**
     * Calcule la valeur supérieure du segment actuel en fonction du pas d'incrémentation.
     *
     * @param bounds l'intervalle min/max du niveau
     * @param current la valeur de départ du segment
     * @return la borne supérieure calculée, bridée par le maximum de l'intervalle
     */
    default int calculerValeurSuperieureDuSegment(LevelPossibilityBounds bounds, int current) {
        int next = ((current / DESIRED_POSSIBILITIES_STEP) + 1) * DESIRED_POSSIBILITIES_STEP;
        return Math.min(next, bounds.max());
    }

    /**
     * Creates Sudoku grids (solved and unsolved) based on a level and a target difficulty
     * percentage (-1 for level's default possibilities).
     *
     * <p>Difficulty levels:
     *
     * <ul>
     *   <li><b>Easy (1)</b>: 34-39 hidden cells (Practical possibilities: 4,800 - 10,500)
     *   <li><b>Medium (2)</b>: 39-44 hidden cells (Practical possibilities: 10,500 - 12,500)
     *   <li><b>Difficult (3)</b>: 44-48 hidden cells (Practical possibilities: 12,500 - 33,000)
     * </ul>
     *
     * <p>Percentage is mapped from the range 4,800 (0%) to 33,000 (100%).
     *
     * @param niveau difficulty level (1 to 3)
     * @param pourcentageDesire target minimum possibility percentage (0-100), or -1 for default
     *     logic
     * @return a {@link GrillesCrees} object containing:
     *     <ul>
     *       <li>{@code grilleResolue}: the completed grid
     *       <li>{@code grilleAResoudre}: the initial puzzle grid
     *       <li>{@code pourcentageDesPossibilites}: the actual calculated difficulty percentage
     *     </ul>
     *
     * @throws IllegalArgumentException if niveau is outside [1, 3]
     * @throws ConstraintViolationException if returned data violates validation constraints
     */
    GrillesCrees creerLesGrilles(final int niveau, final int pourcentageDesire);

    /**
     * Résout une grille de Sudoku en remplissant les cases vides avec des valeurs valides.
     *
     * <p>Le processus de résolution inclut les étapes suivantes :
     *
     * <ol>
     *   <li>Vérifie que la grille n'est pas {@code null} et contient exactement 81 cases.
     *   <li>Calcule les possibilités pour chaque case de la grille.
     *   <li>Vérifie que les valeurs initiales respectent les règles du Sudoku.
     *   <li>Si la grille ne contient aucun zéro, elle est considérée comme déjà résolue.
     *   <li>Sinon, tente de remplir la grille en utilisant une approche récursive :
     *       <ul>
     *         <li>Identifie la case avec le moins d'options disponibles.
     *         <li>Teste chaque valeur possible pour cette case.
     *         <li>Met à jour les possibilités des cases adjacentes en fonction des valeurs placées.
     *         <li>Continue le processus jusqu'à ce que la grille soit complètement remplie ou
     *             qu'aucune solution ne soit trouvée.
     *       </ul>
     * </ol>
     *
     * @param grille Tableau représentant la grille initiale de Sudoku. Il doit comporter exactement
     *     81 éléments et ne doit pas être {@code null}, sous peine de lever une {@link
     *     IllegalArgumentException}.
     * @return un objet {@link GrilleResolue} contenant :
     *     <ul>
     *       <li>{@code estResolue} : {@code true} si la grille est correcte et a été entièrement
     *           remplie, sinon {@code false}
     *       <li>{@code grilleResolue} : la grille résolue si une solution complète a été trouvée,
     *           ou la grille d’origine (partiellement remplie ou incohérente) dans le cas contraire
     *       <li>{@code pourcentageDesPossibilites} : pourcentage des possibilités, compris entre 0
     *           et 100, permettant d’estimer la difficulté ou la cohérence de la grille
     *     </ul>
     *
     * @throws IllegalArgumentException si {@code grille} est {@code null} ou de taille différente
     *     de 81.
     */
    GrilleResolue resoudreLaGrille(final int[] grille);
}
