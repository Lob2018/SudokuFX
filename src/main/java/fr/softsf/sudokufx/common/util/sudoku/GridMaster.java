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

import org.springframework.stereotype.Component;

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

    private static final int TEST_POSSIBILITE_MOYENNE_IMPOSSIBLE = 50000;

    /** Durée maximale d'une recherche de configuration valide (fail safe) pour un essai donné. */
    private static final int DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS = 300;

    /** Nombre de tentatives de génération globale avant abandon (sécurité récursion). */
    private static final int MAX_ESSAIS_POUR_GENERATION_DE_GRILLE = 10;

    private final JakartaValidator jakartaValidator;
    private int moyenMinPossibilites = MOYEN_MIN_POSSIBILITES;
    private int moyenMaxPossibilites = MOYEN_MAX_POSSIBILITES;

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
     * Calcule la somme des possibilités de toutes les cases de la grille de Sudoku.
     *
     * @param possibilites Tableau des possibilités par case (511 = toutes possibilités).
     * @return La somme des possibilités.
     */
    private static int sommeDesPossibilitesDeLaGrille(final int[] possibilites) {
        int somme = 0;
        for (int possibilite : possibilites) {
            // On somme la valeur du masque (ex: 511) pour rester cohérent avec les constantes de
            // seuil (MIN_POSSIBILITES, etc.)
            somme += possibilite;
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
     * Élimine la valeur des possibilités des cases voisines (ligne, colonne, carré).
     *
     * <p>Cette méthode utilise la table de correspondance {@link #NEIGHBORS} pour identifier
     * instantanément les cases à mettre à jour, sans recalculer de coordonnées.
     *
     * @param possibilites Tableau des possibilités.
     * @param index L'index plat de la case (0-80) dont la valeur vient d'être fixée.
     * @param valeur Valeur à éliminer des voisins.
     */
    private static void eliminerPossibilite(
            final int[] possibilites, final int index, final int valeur) {
        // Masque inverse : tous les bits à 1 sauf celui de la valeur
        final int masque = ~BIT_MASKS[valeur];
        // Parcours du tableau pré-calculé (beaucoup plus rapide que les boucles imbriquées)
        for (int neighborIndex : NEIGHBORS[index]) {
            possibilites[neighborIndex] &= masque;
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
     * Génère une grille de Sudoku à résoudre en masquant des cases selon le niveau de difficulté.
     *
     * <p>Cette méthode fait office de dispatcheur vers les implémentations spécifiques (Facile,
     * Moyen, Difficile). Le masquage est désormais purement stochastique, basé sur les plages de
     * cellules à cacher définies pour chaque niveau.
     *
     * <p><b>Gestion des échecs :</b> Si la génération échoue (difficulté non atteinte ou unicité
     * non garantie après le nombre maximal d'essais), la méthode peut retourner une grille
     * réinitialisée à zéro.
     *
     * @param niveau Le niveau de difficulté : 1 (facile), 2 (moyen), 3 (difficile).
     * @param grilleResolue La grille complète servant de référence pour le masquage.
     * @param grilleAResoudre Le tableau de destination qui recevra la grille à trous. <b>IMPORTANT
     *     :</b> Ce tableau est modifié par effet de bord.
     * @return La somme des possibilités (score de difficulté) de la grille finale, ou -1 en cas
     *     d'échec critique de génération.
     */
    private int genererLaGrilleAResoudre(
            final int niveau, final int[] grilleResolue, final int[] grilleAResoudre) {
        return switch (niveau) {
            case 2 -> getPossibilitesGrilleAResoudreMoyenne(grilleResolue, grilleAResoudre);
            case 3 -> getPossibilitesGrilleAresoudreDifficile(grilleResolue, grilleAResoudre);
            default -> getPossibilitesGrilleAResoudreFacile(grilleResolue, grilleAResoudre);
        };
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
     * satisfasse le prédicat de difficulté. *
     *
     * <p><b>Optimisations et Sécurités :</b>
     *
     * <ul>
     *   <li><b>Watchdog :</b> Limite chaque essai à {@link #DUREE_MAX_PAR_GENERATION_DE_GRILLE_MS}.
     *   <li><b>Récursion :</b> Relance une génération globale jusqu'à {@link
     *       #MAX_ESSAIS_POUR_GENERATION_DE_GRILLE}.
     * </ul>
     *
     * @param grilleResolue La grille complète servant de base.
     * @param grilleAResoudre La structure de destination pour la grille à trous (modifiée).
     * @param casesAMin Borne inférieure (incluse) du nombre de cases à masquer.
     * @param casesAMax Borne supérieure (exclue) du nombre de cases à masquer.
     * @param conditionValidation Prédicat de validation de la difficulté.
     * @param compteurEssais Le numéro de la tentative actuelle pour le suivi récursif.
     * @return La somme des possibilités de la grille finale, ou -1 en cas d'échec.
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
                System.out.println(
                        "Échec critique : Nombre max d'essais atteint ("
                                + MAX_ESSAIS_POUR_GENERATION_DE_GRILLE
                                + ")");
                // Retourner une grille vide avec 0 possibilités
                Arrays.fill(grilleAResoudre, 0);
                return -1;
            }
            System.out.println(
                    "Timeout 1s atteint sans succès. Relance globale..."
                            + (System.currentTimeMillis() - debutAppel)
                            + "ms");
            // Réinitialisation
            return genererGrilleAvecCasesCachees(
                    grilleResolue,
                    grilleAResoudre,
                    casesAMin,
                    casesAMax,
                    conditionValidation,
                    compteurEssais);
        }
        System.out.println(
                "Fin durée="
                        + (System.currentTimeMillis() - debutAppel)
                        + "ms | Valide="
                        + estValide
                        + " | "
                        + compteurEssais
                        + " essais\n");
        return sommeDesPossibilites;
    }

    /**
     * Génère une grille de Sudoku de niveau facile.
     *
     * @param grilleResolue Grille complètement remplie servant de base.
     * @param grilleAResoudre Grille à remplir avec les cases masquées (modifiée par la méthode).
     * @return Somme des possibilités restantes dans la grille à résoudre.
     */
    private int getPossibilitesGrilleAResoudreFacile(int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue,
                grilleAResoudre,
                FACILE_MIN_CACHEES,
                MOYEN_MIN_CACHEES,
                somme -> somme <= moyenMinPossibilites,
                1);
    }

    /**
     * Génère une grille de Sudoku de niveau moyen.
     *
     * @param grilleResolue Grille complètement remplie servant de base.
     * @param grilleAResoudre Grille à remplir avec les cases masquées (modifiée par la méthode).
     * @return Somme des possibilités restantes dans la grille à résoudre.
     */
    private int getPossibilitesGrilleAResoudreMoyenne(int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue,
                grilleAResoudre,
                MOYEN_MIN_CACHEES,
                MOYEN_MAX_CACHEES,
                somme -> somme >= moyenMinPossibilites && somme <= moyenMaxPossibilites,
                1);
    }

    /**
     * Génère une grille de Sudoku de niveau difficile.
     *
     * @param grilleResolue Grille complètement remplie servant de base.
     * @param grilleAResoudre Grille à remplir avec les cases masquées (modifiée par la méthode).
     * @return Somme des possibilités restantes dans la grille à résoudre.
     */
    private int getPossibilitesGrilleAresoudreDifficile(
            int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue,
                grilleAResoudre,
                MOYEN_MAX_CACHEES,
                DIFFICILE_MAX_CACHEES,
                somme -> somme >= moyenMaxPossibilites,
                1);
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
     * Crée une paire de grilles (résolue et à résoudre) en fonction du niveau de difficulté
     * spécifié.
     *
     * <p>L'algorithme génère une grille complète, puis applique un masquage stochastique jusqu'à
     * satisfaire l'unicité de la solution et le prédicat de difficulté. *
     *
     * <p><b>Sécurité et Fail-safe :</b> La génération est protégée par un watchdog temporel et une
     * limite de tentatives récursives. En cas d'échec critique à générer une grille valide dans les
     * limites imparties, une grille réinitialisée (vide) est retournée.
     *
     * @param niveau Niveau de difficulté désiré : 1 (Facile), 2 (Moyen), 3 (Difficile).
     * @return Un enregistrement {@code GrillesCrees} contenant la grille résolue, la grille à
     *     résoudre, et le pourcentage de difficulté estimé (score de complexité).
     * @throws IllegalArgumentException si le niveau n'est pas compris entre 1 et 3.
     */
    @Override
    public GrillesCrees creerLesGrilles(final int niveau) {
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

        // En fonction du niveau, cacher un certain nombre de cases et tenir compte des possibilités
        int sommeDesPossibilites = genererLaGrilleAResoudre(niveau, grilleResolue, grilleAResoudre);

        // Récupérer le pourcentage de possibilités estimé
        int pourcentageDesPossibilites = getPourcentageDepuisPossibilites(sommeDesPossibilites);
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

    /**
     * Définit une possibilité facile inaccessible, pour générer une grille par défaut après une
     * seconde. Cette méthode est uniquement utilisée pour les tests.
     */
    void setEasyImpossiblePossibilitiesForTests() {
        this.moyenMinPossibilites = -1;
    }

    /**
     * Définit une possibilité moyenne inaccessible, pour générer une grille par défaut après une
     * seconde. Cette méthode est uniquement utilisée pour les tests.
     */
    void setAverageImpossiblePossibilitiesForTests() {
        this.moyenMinPossibilites = TEST_POSSIBILITE_MOYENNE_IMPOSSIBLE;
        this.moyenMaxPossibilites = -1;
    }

    /**
     * Définit une possibilité difficile inaccessible, pour générer une grille par défaut après une
     * seconde. Cette méthode est uniquement utilisée pour les tests.
     */
    void setDifficultImpossiblePossibilitiesForTests() {
        this.moyenMaxPossibilites = TEST_POSSIBILITE_MOYENNE_IMPOSSIBLE;
    }
}
