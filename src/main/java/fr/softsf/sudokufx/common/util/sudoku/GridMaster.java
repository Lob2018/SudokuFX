/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import javafx.concurrent.Task;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;

/**
 * Fournit les fonctionnalités essentielles pour générer et résoudre des grilles de Sudoku.
 *
 * <p>Cette classe implémente un algorithme de backtracking optimisé utilisant :
 *
 * <ul>
 *   <li><b>La propagation de contraintes :</b> Réduction immédiate du domaine des cases adjacentes
 *       lors d'une affectation.
 *   <li><b>L'heuristique MRV (Minimum Remaining Values) :</b> Priorisation des cases à plus faible
 *       entropie pour maximiser l'élagage de l'arbre de recherche.
 *   <li><b>Opérations bit à bit :</b> Gestion des ensembles de possibilités via des masques
 *       binaires pour des performances accrues.
 *   <li><b>Tables de pré-calcul (Look-up Tables) :</b> Accès instantané aux voisinages (NEIGHBORS)
 *       sans calcul de coordonnées.
 * </ul>
 *
 * <p>La génération de grilles repose sur une <b>recherche stochastique récursive</b>. Elle garantit
 * l'<b>unicité absolue</b> de la solution et le respect d'un niveau de difficulté via un prédicat
 * de validation. Le processus est sécurisé par un <b>watchdog temporel par itération</b> et un
 * <b>limiteur de tentatives globales</b> (fail-safe) pour assurer la réactivité du système.
 */
@Component
public final class GridMaster implements IGridMaster {

    private static final int ORDRE = 3;
    private static final int DIMENSION = ORDRE * ORDRE;
    private static final int NOMBRE_CASES = DIMENSION * DIMENSION;

    /** Masque représentant toutes les possibilités (511 en décimal, 111111111 en binaire). */
    private static final int ALL_POSSIBILITIES = (1 << DIMENSION) - 1;

    private static final int[] DEFAULT_INDICES = IntStream.range(0, NOMBRE_CASES).toArray();

    /**
     * Table de pré-calcul des masques binaires pour les valeurs 1 à 9.
     *
     * <p>Optimisation : Permet un accès direct au masque binaire d'une valeur sans recalculer le
     * décalage de bits (1 << (valeur - 1)) à chaque opération. Exemple : BIT_MASKS[1] = 1,
     * BIT_MASKS[9] = 256.
     */
    private static final int[] BIT_MASKS = new int[DIMENSION + 1];

    /**
     * Table de correspondance (Look-up Table) des voisins (Peers) pour chaque case.
     *
     * <p>Pour chaque case (index 0 à 80), ce tableau contient les indices pré-calculés des 20
     * autres cases qui appartiennent à la même ligne, à la même colonne ou au même carré.
     *
     * <p>Optimisation : Remplace le calcul coûteux des coordonnées et les boucles imbriquées lors
     * de la propagation des contraintes.
     */
    private static final int[][] NEIGHBORS = new int[NOMBRE_CASES][20];

    /** Durée maximale d'une recherche de configuration valide (fail safe) pour un essai donné. */
    private static final int DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS = 300;

    /** Nombre de tentatives de génération globale avant abandon (sécurité récursion). */
    private static final int MAX_ESSAIS_POUR_GENERATION_DE_GRILLE = 10;

    private final JakartaValidator jakartaValidator;

    /*
     * Bloc d'initialisation statique pour les structures de données constantes.
     */
    static {
        // Initialisation des masques de bits
        for (int i = 1; i <= DIMENSION; i++) {
            BIT_MASKS[i] = 1 << (i - 1);
        }
        // Initialisation de la table des voisins
        for (int i = 0; i < NOMBRE_CASES; i++) {
            int row = i / DIMENSION;
            int col = i % DIMENSION;
            int boxRow = (row / ORDRE) * ORDRE;
            int boxCol = (col / ORDRE) * ORDRE;
            int count = 0;
            // Démarrage des méthodes déléguées
            count = addLinearNeighbors(i, row, col, count);
            addBoxNeighbors(i, row, col, boxRow, boxCol, count);
        }
    }

    /**
     * Ajoute les voisins de ligne et de colonne pour l'index donné dans la table NEIGHBORS.
     *
     * @param index L'index de la case de référence.
     * @param row La ligne de la case.
     * @param col La colonne de la case.
     * @param count Le nombre actuel de voisins déjà ajoutés.
     * @return Le nouveau nombre de voisins après l'ajout des voisins linéaires.
     */
    private static int addLinearNeighbors(int index, int row, int col, int count) {
        // 1. Voisins de Ligne
        for (int c = 0; c < DIMENSION; c++) {
            if (c != col) {
                NEIGHBORS[index][count++] = row * DIMENSION + c;
            }
        }
        // 2. Voisins de Colonne
        for (int r = 0; r < DIMENSION; r++) {
            if (r != row) {
                NEIGHBORS[index][count++] = r * DIMENSION + col;
            }
        }
        return count;
    }

    /**
     * Ajoute les voisins du carré (box) pour l'index donné dans la table NEIGHBORS.
     *
     * @param index L'index de la case de référence.
     * @param row La ligne de la case.
     * @param col La colonne de la case.
     * @param boxRow La ligne de début du carré.
     * @param boxCol La colonne de début du carré.
     * @param count Le nombre actuel de voisins déjà ajoutés.
     */
    private static void addBoxNeighbors(
            int index, int row, int col, int boxRow, int boxCol, int count) {
        for (int r = boxRow; r < boxRow + ORDRE; r++) {
            for (int c = boxCol; c < boxCol + ORDRE; c++) {
                // On évite ceux déjà ajoutés par ligne/colonne
                if (r != row && c != col) {
                    NEIGHBORS[index][count++] = r * DIMENSION + c;
                }
            }
        }
    }

    GridMaster(JakartaValidator jakartaValidator) {
        this.jakartaValidator = jakartaValidator;
    }

    /**
     * Calcule le nombre total de candidats (entropie) sur l'ensemble de la grille.
     *
     * <p>Utilise {@link Integer#bitCount(int)} pour garantir un poids linéaire à chaque candidat,
     * s'alignant ainsi sur les seuils de difficulté définis dans {@link IGridMaster}.
     *
     * @param possibilites Tableau de 81 masques de bits (bitmasks).
     * @return Somme totale des candidats restants. La plage pratique pour les grilles valides
     *     s'étend de {@value IGridMaster#MIN_POSSIBILITES} à {@value IGridMaster#MAX_POSSIBILITES}.
     */
    private static int sommeDesPossibilitesDeLaGrille(final int[] possibilites) {
        int somme = 0;
        for (int possibilite : possibilites) {
            somme += Integer.bitCount(possibilite);
        }
        return somme;
    }

    /**
     * Calcule les possibilités pour chaque case de la grille de Sudoku.
     *
     * @param grille La grille de Sudoku actuelle.
     * @return Tableau des possibilités par case (511 = toutes possibilités).
     */
    private static int[] getPossibilites(final int[] grille) {
        int[] possibilites = new int[NOMBRE_CASES];
        initialiserPossibilites(grille, possibilites);
        return possibilites;
    }

    /**
     * Initialise le tableau des possibilités pour chaque case.
     *
     * @param grille Grille Sudoku actuelle.
     * @param possibilites Tableau à initialiser avec les possibilités.
     */
    private static void initialiserPossibilites(final int[] grille, final int[] possibilites) {
        // Remplissage initial avec toutes les possibilités
        Arrays.fill(possibilites, ALL_POSSIBILITIES);
        // Élimine les possibilités basées sur les valeurs existantes dans la grille
        for (int i = 0; i < NOMBRE_CASES; i++) {
            final int valeur = grille[i];
            if (valeur != 0) {
                eliminerPossibilite(possibilites, i, valeur);
            }
        }
    }

    /**
     * Élimine une valeur des candidats des cellules voisines (ligne, colonne, bloc).
     *
     * <p>Les voisins affectés incluent toutes les cellules partageant la même ligne, colonne ou
     * région 3x3, telles que définies dans la table pré-calculée {@code NEIGHBORS}.
     *
     * @param possibilities Tableau des 81 masques de bits à mettre à jour.
     * @param index Index à plat de la cellule (0-80) venant d'être assignée.
     * @param value La valeur (chiffre du Sudoku) à retirer des voisins.
     */
    private static void eliminerPossibilite(
            final int[] possibilities, final int index, final int value) {
        // Masque inverse : tous les bits à 1 sauf celui de la valeur
        final int mask = ~BIT_MASKS[value];
        // Parcours du tableau pré-calculé (beaucoup plus rapide que les boucles imbriquées)
        for (int neighborIndex : NEIGHBORS[index]) {
            possibilities[neighborIndex] &= mask;
        }
    }

    /**
     * Vérifie que chaque valeur dans la grille est bien une possibilité respectant les règles.
     *
     * <p>Cette méthode s'assure que chaque valeur non nulle dans la grille est bien présente dans
     * les possibilités correspondantes.
     *
     * @param grille La grille Sudoku actuelle.
     * @param possibilites Le tableau des possibilités pour chaque case.
     * @return 0 si la vérification est réussie, -1 si une incohérence est détectée.
     */
    private static int verifieQueChaqueValeurEstUnePossibilite(
            final int[] grille, final int[] possibilites) {
        for (int i = 0; i < NOMBRE_CASES; i++) {
            int valeur = grille[i];
            // Vérifie si le bit correspondant à la valeur est à 0 dans les possibilités
            if (valeur != 0 && (possibilites[i] & BIT_MASKS[valeur]) == 0) {
                return -1; // Incohérence détectée
            }
        }
        return 0; // Vérification réussie
    }

    /**
     * Trouve la case vide avec le moins de possibilités.
     *
     * <p>Utilise l'heuristique MRV (Minimum Remaining Values) : choisir la case la plus contrainte
     * permet de réduire l'arbre de recherche et de provoquer les échecs (backtracking) plus tôt.
     *
     * @param grille Grille Sudoku actuelle.
     * @param possibilites Tableau des possibilités.
     * @return L'index de la case trouvée, ou -1 si la grille est complète.
     */
    private static int laCaseVideAvecLeMoinsDePossibilites(
            final int[] grille, final int[] possibilites) {
        int meilleurIndex = -1;
        int meilleurScore = Integer.MAX_VALUE;
        for (int i = 0; i < NOMBRE_CASES; i++) {
            if (grille[i] == 0) {
                int score = Integer.bitCount(possibilites[i]);
                if (score < meilleurScore) {
                    meilleurScore = score;
                    meilleurIndex = i;
                    // Optimisation : Si on trouve une case avec 0 ou 1 possibilité,
                    // on ne trouvera pas mieux, on retourne tout de suite.
                    if (score <= 1) {
                        return i;
                    }
                }
            }
        }
        return meilleurIndex;
    }

    /**
     * Génère une grille de Sudoku à résoudre en masquant des cases selon le niveau de difficulté ou
     * un pourcentage cible.
     *
     * <p>Cette méthode agit comme un <b>dispatcheur de difficulté</b>. Elle construit dynamiquement
     * un {@link IntPredicate} de validation basé soit sur les seuils globaux du niveau (clic
     * simple), soit sur une plage d'entropie restreinte calculée par segments de 10% (clic sur
     * curseur).
     *
     * <p>Le processus repose sur un masquage stochastique (aléatoire) calibré pour respecter
     * simultanément l'<b>unicité de la solution</b> et la <b>somme des candidats</b> (entropie)
     * définie par {@link IGridMaster#MIN_POSSIBILITES} et {@link IGridMaster#MAX_POSSIBILITES}.
     *
     * <p><b>Gestion des échecs :</b> En cas de timeout (watchdog) ou si le nombre maximal d'essais
     * est atteint sans satisfaire la condition d'unicité/difficulté, la grille est remise à zéro.
     *
     * @param niveau Le niveau de difficulté cible : 1 (EASY), 2 (MEDIUM), 3 (DIFFICULT).
     * @param pourcentageDesire Le pourcentage de difficulté (0 à 100), ou -1 pour utiliser
     *     l'intégralité de la plage du niveau.
     * @param grilleResolue La grille complète servant de matrice source.
     * @param grilleAResoudre Le tableau de destination (81 cases) modifié par effet de bord.
     * @return La somme finale des possibilités (entropie), ou -1 en cas d'échec de génération.
     */
    private int genererLaGrilleAResoudre(
            final int niveau,
            final int pourcentageDesire,
            final int[] grilleResolue,
            final int[] grilleAResoudre) {
        final DifficultyLevel level = DifficultyLevel.fromGridByte((byte) niveau);
        IntPredicate condition;
        if (pourcentageDesire == -1) {
            final LevelPossibilityBounds bounds = getIntervallePourcentageNiveau(level);
            final int minBrut = getPossibilitesDepuisPourcentage(bounds.min());
            final int maxBrut = getPossibilitesDepuisPourcentage(bounds.max());
            condition = somme -> somme >= minBrut && somme <= maxBrut;
        } else {
            final int min = getPossibilitesDepuisPourcentage(pourcentageDesire);
            final LevelPossibilityBounds bounds = getIntervallePourcentageNiveau(level);
            final int max =
                    getPossibilitesDepuisPourcentage(
                            calculerValeurSuperieureDuSegment(bounds, pourcentageDesire));
            condition = somme -> somme >= min && somme <= max;
        }
        return getPossibilitesGrilleAResoudre(condition, grilleResolue, grilleAResoudre);
    }

    /**
     * Vérifie l'unicité par backtracking en utilisant la propagation de contraintes.
     *
     * @param grille État de la grille.
     * @param possibilites Masques de possibilités actuels.
     * @param nbSolutions Nombre de solutions trouvées.
     * @return Total de solutions (capé à 2).
     */
    private int verifierUnicite(int[] grille, int[] possibilites, int nbSolutions) {
        // Sélection de la cellule via l'heuristique MRV pour réduire l'arborescence de recherche
        int index = laCaseVideAvecLeMoinsDePossibilites(grille, possibilites);
        // Condition de sortie : aucune case vide trouvée signifie qu'une solution complète est
        // validée
        if (index < 0) {
            return nbSolutions + 1;
        }
        int possDeLaCase = possibilites[index];
        // Exploration récursive tant qu'il reste des candidats et que l'unicité n'est pas déjà
        // invalidée
        while (possDeLaCase != 0 && nbSolutions < 2) {
            // Extraction d'une valeur candidate et mise à jour du masque local pour l'itération
            // suivante
            int valeur = choisirValeurAleatoire(possDeLaCase);
            possDeLaCase &= ~BIT_MASKS[valeur];
            // Application de la valeur et isolation de l'état pour la branche de recherche actuelle
            grille[index] = valeur;
            int[] nouvellesPoss = Arrays.copyOf(possibilites, NOMBRE_CASES);
            // Propagation des contraintes : élimination de la valeur chez les voisins
            eliminerPossibilite(nouvellesPoss, index, valeur);
            // Descente récursive pour explorer les conséquences de ce choix
            nbSolutions = verifierUnicite(grille, nouvellesPoss, nbSolutions);
            // Restauration de l'état (Backtrack) pour tester les autres candidats potentiels
            grille[index] = 0;
        }
        return nbSolutions;
    }

    /**
     * Génère une grille de Sudoku avec un masquage aléatoire borné selon la difficulté.
     *
     * <p>L'algorithme garantit l'<b>unicité absolue</b> de la solution par une validation
     * exhaustive via backtracking. Il itère jusqu'à ce que la <b>somme des possibilités</b>
     * satisfasse le prédicat de difficulté.
     *
     * <p><b>Optimisations et Sécurités :</b>
     *
     * <ul>
     *   <li><b>Interruption :</b> Vérifie {@link Thread#isInterrupted()} pour stopper le calcul
     *       immédiatement si la {@link Task} est annulée.
     *   <li><b>Watchdog :</b> Limite chaque essai à {@link #DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS}.
     *   <li><b>Récursion :</b> Relance une génération globale jusqu'à {@link
     *       #MAX_ESSAIS_POUR_GENERATION_DE_GRILLE} en cas de timeout de l'essai actuel.
     * </ul>
     *
     * @param grilleResolue La grille complète servant de base.
     * @param grilleAResoudre La structure de destination pour la grille à trous (modifiée).
     * @param casesAMin Borne inférieure (incluse) du nombre de cases à masquer.
     * @param casesAMax Borne supérieure (exclue) du nombre de cases à masquer.
     * @param conditionValidation Prédicat de validation de la difficulté.
     * @param compteurEssais Le numéro de la tentative actuelle pour le suivi récursif.
     * @return La somme des possibilités de la grille finale, ou -1 en cas d'échec (timeout, nombre
     *     max d'essais atteint ou interruption du thread).
     */
    @SuppressWarnings("java:S2245")
    private int genererGrilleAvecCasesCachees(
            int[] grilleResolue,
            int[] grilleAResoudre,
            int casesAMin,
            int casesAMax,
            IntPredicate conditionValidation,
            int compteurEssais) {
        int sommeDesPossibilites;
        boolean estValide = false;
        var random = ThreadLocalRandom.current();
        int nombreDeCasesACacher = random.nextInt(casesAMin, casesAMax);
        long debutAppel = System.currentTimeMillis();
        do {
            if (Thread.currentThread().isInterrupted()) {
                Arrays.fill(grilleAResoudre, 0);
                return -1;
            }
            sommeDesPossibilites =
                    getPossibilitesGrilleWhileNok(
                            grilleResolue, grilleAResoudre, nombreDeCasesACacher);
            // Vérification Unicité
            if (verifierUnicite(grilleAResoudre.clone(), getPossibilites(grilleAResoudre), 0)
                    == 1) {
                // Vérification Difficulté
                if (conditionValidation.test(sommeDesPossibilites)) {
                    estValide = true;
                } else {
                    nombreDeCasesACacher = random.nextInt(casesAMin, casesAMax);
                }
            }
            // Vérifier validité et durée de génération
        } while (!estValide
                && (System.currentTimeMillis() - debutAppel)
                        <= DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS);
        if (!estValide) {
            compteurEssais++;
            // Vérifier le nombre de tentatives
            if (compteurEssais >= MAX_ESSAIS_POUR_GENERATION_DE_GRILLE) {
                Arrays.fill(grilleAResoudre, 0);
                return -1;
            }
            // Réinitialisation
            return genererGrilleAvecCasesCachees(
                    grilleResolue,
                    grilleAResoudre,
                    casesAMin,
                    casesAMax,
                    conditionValidation,
                    compteurEssais);
        }
        return sommeDesPossibilites;
    }

    /**
     * Génère une grille de Sudoku.
     *
     * <p>Cette méthode sert de point d'entrée simplifié en déléguant la logique de masquage et de
     * validation d'unicité à {@link #genererGrilleAvecCasesCachees}.
     *
     * @param conditionValidation Prédicat définissant les bornes de difficulté acceptables.
     * @param grilleResolue Grille complète servant de matrice source.
     * @param grilleAResoudre Grille de destination pour le puzzle (modifiée par la méthode).
     * @return Somme des possibilités (entropie) de la grille finale, ou -1 en cas d'échec.
     */
    private int getPossibilitesGrilleAResoudre(
            IntPredicate conditionValidation, int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue, grilleAResoudre, MIN_CACHEES, MAX_CACHEES, conditionValidation, 1);
    }

    /**
     * Cache des cases dans la grille et calcule la somme des possibilités restantes.
     *
     * @param grilleResolue La grille de Sudoku résolue (source).
     * @param grilleAResoudre La grille de Sudoku à résoudre (destination), MODIFIÉE par la méthode.
     * @param nombreDeCasesACacher Nombre de cases à cacher.
     * @return La somme des possibilités après avoir caché des cases.
     */
    private int getPossibilitesGrilleWhileNok(
            int[] grilleResolue, int[] grilleAResoudre, int nombreDeCasesACacher) {
        System.arraycopy(grilleResolue, 0, grilleAResoudre, 0, NOMBRE_CASES);
        cacherLesCases(nombreDeCasesACacher, grilleAResoudre);
        return sommeDesPossibilitesDeLaGrille(getPossibilites(grilleAResoudre));
    }

    /**
     * Cache un nombre spécifié de cases dans la grille en les remplaçant par zéro. Utilise un
     * mélange de Fisher-Yates partiel pour sélectionner les cases aléatoirement.
     *
     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille.
     * @param grilleAResoudre Le tableau représentant la grille.
     */
    @SuppressWarnings("java:S2245")
    private void cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, NOMBRE_CASES);
        int[] indices = Arrays.copyOf(DEFAULT_INDICES, NOMBRE_CASES);
        // Utilisation de ThreadLocalRandom pour éviter la contention et améliorer la perf
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < nombreDeCasesACacher; i++) {
            int max = NOMBRE_CASES - i;
            int randomIndex = random.nextInt(max);
            int indexToHide = indices[randomIndex];
            grilleAResoudre[indexToHide] = 0;
            // Swap rapide (Fisher-Yates partiel)
            indices[randomIndex] = indices[max - 1];
        }
    }

    /**
     * Remplit récursivement la grille Sudoku (backtracking).
     *
     * @param grille Grille Sudoku à remplir.
     * @param possibilites Tableau des possibilités pour chaque case.
     * @return true si la grille est remplie avec succès, false sinon.
     */
    private boolean remplirLaGrille(final int[] grille, final int[] possibilites) {
        int index = laCaseVideAvecLeMoinsDePossibilites(grille, possibilites);
        if (index < 0) {
            return true;
        }
        int possibilitesDeLaCase = possibilites[index];
        while (possibilitesDeLaCase != 0) {
            // Choisit une valeur aléatoire parmi les possibilités restantes
            int valeur = choisirValeurAleatoire(possibilitesDeLaCase);
            // Retire cette valeur des possibilités de la case locale pour la boucle while
            possibilitesDeLaCase &= ~BIT_MASKS[valeur];
            // Place la valeur dans la grille
            grille[index] = valeur;
            // Crée une copie des possibilités pour la récursion
            int[] nouvellesPossibilites = Arrays.copyOf(possibilites, NOMBRE_CASES);
            // Élimine la valeur choisie des possibilités des cases voisines
            eliminerPossibilite(nouvellesPossibilites, index, valeur);
            // Appel récursif
            if (remplirLaGrille(grille, nouvellesPossibilites)) {
                return true;
            }
        }
        // Backtracking : si aucune valeur ne convient, on remet la case à 0
        grille[index] = 0;
        return false;
    }

    /**
     * Choisit une valeur aléatoire parmi les possibilités données.
     *
     * @param possibilitesDeLaCase Entier représentant les valeurs possibles (masque de bits).
     * @return Une valeur choisie aléatoirement parmi les bits à 1.
     */
    @SuppressWarnings("java:S2245")
    private int choisirValeurAleatoire(final int possibilitesDeLaCase) {
        int nombrePossibilites = Integer.bitCount(possibilitesDeLaCase);
        if (nombrePossibilites == 0) {
            return 0;
        }
        // ThreadLocalRandom est plus performant ici que SecureRandom
        int choix = ThreadLocalRandom.current().nextInt(nombrePossibilites);
        int temp = possibilitesDeLaCase;
        for (int i = 0; i < DIMENSION; i++) {
            if ((temp & 1) == 1) {
                if (choix == 0) {
                    return i + 1;
                }
                choix--;
            }
            temp >>>= 1; // Décalage logique pour vérifier le bit suivant
        }
        return 0;
    }

    /**
     * Tente de résoudre la grille de Sudoku fournie en utilisant un algorithme de backtracking
     * optimisé (propagation de contraintes et heuristique MRV).
     *
     * @param grille Tableau d'entiers représentant la grille de Sudoku (81 cases). Les cases vides
     *     doivent être représentées par 0.
     * @return Un enregistrement {@code GrilleResolue} contenant l'état de la résolution
     *     (succès/échec), la grille complétée, et un score de difficulté (pourcentage de
     *     possibilités).
     *     <p>**Score de difficulté :** Ce score représente la complexité théorique de la grille
     *     basée sur la **somme des possibilités binaires** (masques) pour toutes les cases vides
     *     initiales. Une somme élevée indique un plus grand nombre d'options par case, ce qui
     *     augmente le facteur de branchement et est corrélé à une difficulté perçue plus élevée. Le
     *     pourcentage est normalisé entre 0 (très facile) et 100 (très difficile).
     * @throws IllegalArgumentException si la grille est {@code null} ou n'a pas 81 éléments.
     */
    @Override
    public GrilleResolue resoudreLaGrille(final int[] grille) {
        // Lever une exception si la grille est null, ou si sa taille est différente de 81
        if (grille == null || grille.length != NOMBRE_CASES) {
            String taille = (grille == null) ? "null" : String.valueOf(grille.length);
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The grid must not be null and must contain exactly 81 cells, but was "
                            + taille);
        }
        // Calcul des possibilités initiales
        int[] possibilites = getPossibilites(grille);
        // Vérifie que les valeurs de la grille respectent les règles
        int reglesOk = verifieQueChaqueValeurEstUnePossibilite(grille, possibilites);
        if (reglesOk == -1) {
            return new GrilleResolue(false, grille, 0);
        }
        // Vérifie si la grille est déjà complète
        boolean estComplete = true;
        for (int v : grille) {
            if (v == 0) {
                estComplete = false;
                break;
            }
        }
        if (estComplete) {
            return new GrilleResolue(true, grille, 0);
        }
        // Tente de remplir la grille
        boolean grilleRemplie = remplirLaGrille(grille, possibilites);
        GrilleResolue grilleResolue;
        if (grilleRemplie) {
            int scorePossibilites = sommeDesPossibilitesDeLaGrille(possibilites);
            grilleResolue =
                    new GrilleResolue(
                            true, grille, getPourcentageDepuisPossibilites(scorePossibilites));
            // Valide l'intégrité du record
            jakartaValidator.validateOrThrow(grilleResolue);
            return grilleResolue;
        }
        grilleResolue = new GrilleResolue(false, grille, 0);
        jakartaValidator.validateOrThrow(grilleResolue);
        return grilleResolue;
    }

    /**
     * Crée une paire de grilles (résolue et à résoudre) en fonction du niveau de difficulté et du
     * pourcentage de possibilités désirés (-1 pour les possibilités correspondantes au niveau
     * choisit).
     *
     * <p>L'algorithme génère une grille complète, puis applique un masquage stochastique jusqu'à
     * satisfaire l'unicité de la solution et le prédicat de difficulté, potentiellement contraint
     * par le pourcentage désiré.
     *
     * <p><b>Sécurité et Fail-safe :</b> La génération est protégée par un watchdog temporel et une
     * limite de tentatives récursives. En cas d'échec critique à générer une grille valide dans les
     * limites imparties, une grille réinitialisée (vide) est retournée.
     *
     * @param niveau Niveau de difficulté désiré : 1 (Facile), 2 (Moyen), 3 (Difficile).
     * @param pourcentageDesire Pourcentage minimum de possibilités souhaité (0-100), ou -1.
     * @return Un enregistrement {@code GrillesCrees} contenant la grille résolue, la grille à
     *     résoudre, et le pourcentage de difficulté estimé (score de complexité).
     * @throws IllegalArgumentException si le niveau n'est pas compris entre 1 et 3.
     */
    @Override
    public GrillesCrees creerLesGrilles(final int niveau, final int pourcentageDesire) {
        // Lever une exception si le niveau n'existe pas
        if (niveau < 1 || niveau > 3) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The grid level must be between 1 and 3, but was " + niveau);
        }
        // Initialiser la grille résolue
        int[] grilleResolue = new int[NOMBRE_CASES];
        // Initialiser la première ligne aléatoirement pour garantir la diversité
        remplirPremiereLigneAleatoirement(grilleResolue);
        // Résoudre la grille pour obtenir une grille complète valide
        resoudreLaGrille(grilleResolue);
        // Initialiser la grille à résoudre (vide au départ)
        int[] grilleAResoudre = new int[NOMBRE_CASES];
        // En fonction du niveau, cacher un nombre de cases et tenir compte de pourcentage de
        // possibilités desiré
        int sommeDesPossibilites =
                genererLaGrilleAResoudre(niveau, pourcentageDesire, grilleResolue, grilleAResoudre);
        // Récupérer le pourcentage de possibilités estimé
        int pourcentageDesPossibilites =
                sommeDesPossibilites == -1
                        ? -1
                        : getPourcentageDepuisPossibilites(sommeDesPossibilites);
        // Crée un record GrillesCrees structuré contenant les deux grilles et le pourcentage
        GrillesCrees grillesCrees =
                new GrillesCrees(grilleResolue, grilleAResoudre, pourcentageDesPossibilites);
        // Valide l'intégrité du record
        jakartaValidator.validateOrThrow(grillesCrees);
        return grillesCrees;
    }

    /**
     * Remplit aléatoirement la première ligne de la grille avec les chiffres de 1 à 9.
     *
     * <p>Cette méthode est utilisée avant le lancement du solveur sur une grille vide pour garantir
     * que la grille générée ne soit pas déterministe (toujours la même solution pour une grille
     * vide) et pour augmenter l'entropie initiale.
     *
     * @param grille La grille dont la première ligne doit être initialisée.
     */
    @SuppressWarnings("java:S2245")
    private void remplirPremiereLigneAleatoirement(int[] grille) {
        int[] valeurs = IntStream.rangeClosed(1, 9).toArray();
        // Mélange de Fisher-Yates pour garantir une permutation aléatoire uniforme
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = valeurs.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = valeurs[index];
            valeurs[index] = valeurs[i];
            valeurs[i] = a;
        }
        System.arraycopy(valeurs, 0, grille, 0, 9);
    }
}
