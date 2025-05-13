/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.utils.sudoku;

/** Interface defining methods to generate and solve Sudoku puzzles */
public sealed interface IGridMaster permits GridMaster {
    /**
     * Crée les grilles de Sudoku (résolue et à résoudre) en fonction du niveau de difficulté.
     * Facile : 35-45 cases cachées, possibilités théoriques de 0 à 13797, et pratique de 4800 à
     * 16533 Moyen : 45-39 cases cachées, possibilités théoriques de 13797 à 27594, et pratique de
     * 16533 à 28266 Difficile : 49 à 59 cases cachées, possibilités théoriques de 27594 à 41391, et
     * pratique de 28266 à 40000
     *
     * @param niveau Le niveau de difficulté (1 : facile, 2 : moyen, 3 : difficile).
     * @return Un tableau à trois dimensions contenant : La grille résolue. La grille à résoudre
     *     avec les cases cachées. Le pourcentage de possibilités de la grille à résoudre (la plage
     *     retenue va de 4800 0% à 40000 100%).
     */
    int[][] creerLesGrilles(final int niveau);

    /**
     * Résout une grille de Sudoku en remplissant les cases vides avec des valeurs valides. Le
     * processus de résolution inclut les étapes suivantes : 1. Calcule les possibilités pour chaque
     * case de la grille. 2. Remplit la grille en utilisant une approche récursive : - Identifie la
     * case avec le moins d'options disponibles. - Teste chaque valeur possible pour cette case. -
     * Met à jour les possibilités des cases adjacentes en fonction des valeurs placées. - Continue
     * le processus jusqu'à ce que la grille soit complètement remplie ou qu'aucune solution ne soit
     * trouvée.
     *
     * @param grille Tableau représentant la grille de Sudoku initiale, qui peut être vide ou
     *     partiellement remplie.
     * @return Un tableau contenant deux éléments : - Le premier élément est 0 si la grille est
     *     générée avec succès et est cohérente, sinon -1. - Le deuxième élément est le pourcentage
     *     des possibilités, compris entre 0 et 100, ou 0 si la grille n'est pas cohérente.
     */
    int[] resoudreLaGrille(final int[] grille);
}
