/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

/** Interface defining methods to generate and solve Sudoku puzzles */
public sealed interface IGridMaster permits GridMaster {
    /**
     * Crée les grilles de Sudoku (résolue et à résoudre) en fonction du niveau de difficulté.
     * Niveaux de difficulté :
     *
     * <ul>
     *   <li><b>Facile (1)</b> : 35-45 cases cachées, possibilités théoriques de 0 à 13 797,
     *       possibilités pratiques de 4 800 à 16 533.
     *   <li><b>Moyen (2)</b> : 45-49 cases cachées, possibilités théoriques de 13 797 à 27 594,
     *       possibilités pratiques de 16 533 à 28 266.
     *   <li><b>Difficile (3)</b> : 49-59 cases cachées, possibilités théoriques de 27 594 à 41 391,
     *       possibilités pratiques de 28 266 à 40 000.
     * </ul>
     *
     * Le pourcentage de possibilités est calculé sur la base d'une plage allant de 4 800 (0%) à 40
     * 000 (100%), indiquant la difficulté estimée de la grille à résoudre.
     *
     * @param niveau Le niveau de difficulté (1 : facile, 2 : moyen, 3 : difficile).
     * @return Un tableau à trois dimensions contenant :
     *     <ol>
     *       <li>La grille résolue (tableau d'entiers de taille NOMBRE_CASES).
     *       <li>La grille à résoudre avec les cases cachées (tableau d'entiers de taille
     *           NOMBRE_CASES).
     *       <li>Un tableau d'un seul entier représentant le pourcentage des possibilités de la
     *           grille à résoudre.
     *     </ol>
     *
     * @throws IllegalArgumentException si le niveau n’est pas compris entre 1 et 3 inclus.
     */
    int[][] creerLesGrilles(final int niveau);

    /**
     * Résout une grille de Sudoku en remplissant les cases vides avec des valeurs valides.
     *
     * <p>Le processus de résolution inclut les étapes suivantes :
     *
     * <ol>
     *   <li>Vérifie que la grille n'est pas {@code null} et contient exactement 81 cases.
     *   <li>Calcule les possibilités pour chaque case de la grille.
     *   <li>Remplit la grille en utilisant une approche récursive :
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
     * @return Un tableau contenant deux éléments :
     *     <ul>
     *       <li>Le premier élément est 0 si la grille est générée avec succès et est cohérente,
     *           sinon -1.
     *       <li>Le deuxième élément est le pourcentage des possibilités, compris entre 0 et 100, ou
     *           0 si la grille n'est pas cohérente.
     *     </ul>
     *
     * @throws IllegalArgumentException si {@code grille} est {@code null} ou de taille différente
     *     de 81.
     */
    int[] resoudreLaGrille(final int[] grille);
}
