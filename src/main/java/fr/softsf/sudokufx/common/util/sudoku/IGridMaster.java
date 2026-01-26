/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import jakarta.validation.ConstraintViolationException;

/** Interface defining methods to generate and solve Sudoku puzzles */
public sealed interface IGridMaster permits GridMaster {
    /**
     * Crée les grilles de Sudoku (résolue et à résoudre) selon un niveau de difficulté donné.
     *
     * <p>Les niveaux disponibles sont :
     *
     * <ul>
     *   <li><b>Facile (1)</b> : 35 à 45 cases cachées<br>
     *       - Possibilités théoriques : 0 à 13797<br>
     *       - Possibilités pratiques : 4800 à 16533
     *   <li><b>Moyen (2)</b> : 45 à 49 cases cachées<br>
     *       - Possibilités théoriques : 13797 à 27594<br>
     *       - Possibilités pratiques : 16533 à 28266
     *   <li><b>Difficile (3)</b> : 49 à 59 cases cachées<br>
     *       - Possibilités théoriques : 27594 à 41391<br>
     *       - Possibilités pratiques : 28266 à 40000
     * </ul>
     *
     * <p>Le pourcentage de possibilités est calculé à partir de la plage 4800 (0%) à 40000 (100%),
     * afin d’estimer la difficulté réelle de la grille générée.
     *
     * @param niveau le niveau de difficulté à appliquer (1 à 3)
     * @return un objet {@link GrillesCrees} contenant :
     *     <ul>
     *       <li>la grille résolue ({@code grilleResolue})
     *       <li>la grille à résoudre ({@code grilleAResoudre})
     *       <li>le pourcentage des possibilités ({@code pourcentageDesPossibilites})
     *     </ul>
     *
     * @throws IllegalArgumentException si {@code niveau} est en dehors de l’intervalle [1, 3]
     * @throws ConstraintViolationException si les données retournées ne respectent pas les
     *     contraintes de validation
     */
    GrillesCrees creerLesGrilles(final int niveau);

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
