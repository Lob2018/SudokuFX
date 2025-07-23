/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.SecureRandomGenerator;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.JakartaValidator;

/** Provides essential functionalities for generating and solving Sudoku puzzles. */
@Component
final class GridMaster implements IGridMaster {

    private static final int ORDRE = 3;
    private static final int DIMENSION = ORDRE * ORDRE;
    private static final int NOMBRE_CASES = DIMENSION * DIMENSION;
    // Nombre de cases à cacher en fonction du niveau
    private static final int FACILE_MIN_CACHEES = 35;
    private static final int MOYEN_MIN_CACHEES = 45;
    private static final int FACILE_MOY_CACHEES = (FACILE_MIN_CACHEES + MOYEN_MIN_CACHEES) / 2;
    private static final int MOYEN_MAX_CACHEES = 50;
    private static final int MOYEN_MOY_CACHEES = (MOYEN_MIN_CACHEES + MOYEN_MAX_CACHEES) / 2;
    private static final int DIFFICILE_MAX_CACHEES = 59;
    private static final int DIFFICILE_MOY_CACHEES =
            (MOYEN_MAX_CACHEES + DIFFICILE_MAX_CACHEES) / 2;
    private static final int[] DEFAULT_INDICES = IntStream.range(0, NOMBRE_CASES).toArray();
    private static final int MIN_POSSIBILITES = 4800;
    private static final int MAX_POSSIBILITES = 40000;
    private static final int POURCENTAGE_MAX = 100;
    private static final int TEST_PSSIBILITE_MOYENNE_IMPOSSIBLE = 50000;
    // Possibilités (théorique 0 à 41391, pratique 4800 à 40000) de la grille en fonction du niveau
    private int moyenMinPossibilites = 16533;
    // Durée entre les demandes
    private static final int DUREE_MAXIMALE_POUR_MASQUE_PRECIS = 500;
    private static final int DUREE_MAXIMALE_POUR_MASQUE_ALEATOIRE = 1000;

    private final JakartaValidator jakartaValidator;

    public GridMaster(JakartaValidator jakartaValidator) {
        this.jakartaValidator = jakartaValidator;
    }

    public int getMoyenMinPossibilites() {
        return moyenMinPossibilites;
    }

    private int moyenMaxPossibilites = 28266;

    public int getMoyenMaxPossibilites() {
        return moyenMaxPossibilites;
    }

    private LocalDateTime derniereDemande = LocalDateTime.now();

    /**
     * Calcule la somme des possibilités de toutes les cases de la grille de Sudoku.
     *
     * @param possibilites Tableau des possibilités par case (511 = toutes possibilités).
     * @return La somme des possibilités.
     */
    private static int sommeDesPossibilitesDeLaGrille(final int[] possibilites) {
        int somme = 0;
        for (int possibilite : possibilites) {
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
        // Chaque possibilité est de 1 à 9, soit 511 ou en binaire (111111111)
        Arrays.fill(possibilites, (1 << DIMENSION) - 1);
        // Élimine les possibilités basées sur les valeurs existantes dans la grille
        for (int ligne = 0; ligne < DIMENSION; ligne++) {
            for (int colonne = 0; colonne < DIMENSION; colonne++) {
                int valeur = grille[ligne * DIMENSION + colonne];
                if (valeur != 0) {
                    eliminerPossibilite(possibilites, ligne, colonne, valeur);
                }
            }
        }
    }

    /**
     * Élimine la valeur des possibilités des cases alentours.
     *
     * @param possibilites Tableau des possibilités.
     * @param ligne Ligne de la case.
     * @param colonne Colonne de la case.
     * @param valeur Valeur à éliminer.
     */
    private static void eliminerPossibilite(
            final int[] possibilites, final int ligne, final int colonne, final int valeur) {
        int masque = ~(1 << (valeur - 1));
        int sauvegarde = possibilites[ligne * DIMENSION + colonne];
        // Élimination de la valeur sur la ligne
        for (int i = 0; i < DIMENSION; i++) {
            possibilites[ligne * DIMENSION + i] &= masque;
        }
        // Élimination de la valeur sur la colonne
        for (int i = 0; i < DIMENSION; i++) {
            possibilites[i * DIMENSION + colonne] &= masque;
        }
        // Élimination de la valeur dans le carré
        int debutX = (colonne / ORDRE) * ORDRE;
        int debutY = (ligne / ORDRE) * ORDRE;
        for (int i = 0; i < ORDRE; i++) {
            for (int j = 0; j < ORDRE; j++) {
                possibilites[(debutY + i) * DIMENSION + (debutX + j)] &= masque;
            }
        }
        // Restaure les possibilités originales de la case
        possibilites[ligne * DIMENSION + colonne] = sauvegarde;
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
            if (valeur != 0 && (possibilites[i] & (1 << (valeur - 1))) == 0) {
                return -1; // Incohérence détectée
            }
        }
        return 0; // Vérification réussie
    }

    /**
     * Trouve la case vide avec le moins de possibilités.
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
            // case vide
            if (grille[i] == 0) {
                // compte le nombre de bits à 1 (possibilités restantes)
                int score = compterBits(possibilites[i]);
                // mise à jour si la case moins de possibilités
                if (score < meilleurScore) {
                    meilleurIndex = i;
                    meilleurScore = score;
                }
            }
        }
        return meilleurIndex;
    }

    /**
     * Compte le nombre de bits à 1 dans un entier.
     *
     * @param x Entier à analyser.
     * @return Le nombre de bits à 1.
     */
    private static int compterBits(final int x) {
        return Integer.bitCount(x);
    }

    /**
     * Génère une grille de Sudoku à résoudre en cachant des cases selon le niveau de difficulté. La
     * stratégie de génération ajuste le nombre de cases cachées en fonction du temps écoulé depuis
     * la dernière génération. Si une nouvelle grille est demandée très rapidement (moins de 500ms),
     * le nombre de cases cachées est intentionnellement ajusté pour standardiser la grille.
     *
     * @param niveau Le niveau de difficulté : 1 (et autres valeurs) : facile, 2 : moyen, 3 :
     *     difficile).
     * @param grilleResolue La grille résolue à partir de laquelle les cases seront cachées.
     * @param grilleAResoudre La grille à résoudre, avec ses cases cachées à 0. IMPORTANT : Ce
     *     tableau est modifié directement par la fonction.
     * @return La somme du nombre de possibilités pour chaque case non résolue dans la grille à
     *     résoudre.
     * @implNote La méthode utilise une approche itérative pour cacher des cases et évaluer les
     *     possibilités de la grille résultante jusqu'à ce qu'elle corresponde au niveau de
     *     difficulté souhaité. Elle met également à jour l'horodatage interne derniereDemande.
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
     * Génère une grille de Sudoku avec un nombre de cases cachées selon les critères spécifiés.
     *
     * @param grilleResolue La grille résolue à partir de laquelle les cases seront cachées.
     * @param grilleAResoudre La grille à résoudre, avec ses cases cachées (valeur à zéro).
     * @param casesAPrecis Nombre de cases à cacher si la durée est inférieure au minimum.
     * @param casesAMin Nombre minimum de cases à cacher en mode aléatoire.
     * @param casesAMax Nombre maximum de cases à cacher en mode aléatoire.
     * @param conditionValidation Prédicat pour valider la somme des possibilités.
     * @return La somme des possibilités dans la grille à résoudre.
     */
    private int genererGrilleAvecCasesCachees(
            int[] grilleResolue,
            int[] grilleAResoudre,
            int casesAPrecis,
            int casesAMin,
            int casesAMax,
            IntPredicate conditionValidation) {
        int sommeDesPossibilites;
        int nombreDeCasesACacher =
                dureeEnMs() < DUREE_MAXIMALE_POUR_MASQUE_PRECIS
                        ? casesAPrecis
                        : nombreAleatoire(casesAMin, casesAMax);
        derniereDemande = LocalDateTime.now();
        do {
            sommeDesPossibilites =
                    getPossibilitesGrilleWhileNok(
                            grilleResolue, grilleAResoudre, nombreDeCasesACacher);
            if (dureeEnMs() > DUREE_MAXIMALE_POUR_MASQUE_ALEATOIRE) {
                break;
            }
        } while (!conditionValidation.test(sommeDesPossibilites));
        derniereDemande = LocalDateTime.now();
        return sommeDesPossibilites;
    }

    /**
     * Génère une grille de Sudoku de niveau facile. Le nombre de cases masquées dépend du temps
     * écoulé depuis la dernière demande. Si ce temps est inférieur à la durée minimale définie, un
     * nombre fixe de cases est masqué (FACILE_MOY_CACHEES). Sinon, ce nombre est aléatoire, compris
     * entre FACILE_MIN_CACHEES et MOYEN_MIN_CACHEES.
     *
     * @param grilleResolue Grille complètement remplie servant de base.
     * @param grilleAResoudre Grille à remplir avec les cases masquées (modifiée par la méthode).
     * @return Somme des possibilités restantes dans la grille à résoudre.
     */
    private int getPossibilitesGrilleAResoudreFacile(int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue,
                grilleAResoudre,
                FACILE_MOY_CACHEES,
                FACILE_MIN_CACHEES,
                MOYEN_MIN_CACHEES,
                somme -> somme <= moyenMinPossibilites);
    }

    /**
     * Génère une grille de Sudoku de niveau moyen. Le nombre de cases masquées dépend du temps
     * écoulé depuis la dernière demande. Si ce temps est inférieur à la durée minimale définie, un
     * nombre fixe de cases est masqué (MOYEN_MOY_CACHEES). Sinon, ce nombre est aléatoire, compris
     * entre MOYEN_MIN_CACHEES et MOYEN_MAX_CACHEES.
     *
     * @param grilleResolue Grille complètement remplie servant de base.
     * @param grilleAResoudre Grille à remplir avec les cases masquées (modifiée par la méthode).
     * @return Somme des possibilités restantes dans la grille à résoudre.
     */
    private int getPossibilitesGrilleAResoudreMoyenne(int[] grilleResolue, int[] grilleAResoudre) {
        return genererGrilleAvecCasesCachees(
                grilleResolue,
                grilleAResoudre,
                MOYEN_MOY_CACHEES,
                MOYEN_MIN_CACHEES,
                MOYEN_MAX_CACHEES,
                somme -> somme >= moyenMinPossibilites && somme <= moyenMaxPossibilites);
    }

    /**
     * Génère une grille de Sudoku de niveau difficile. Le nombre de cases masquées dépend du temps
     * écoulé depuis la dernière demande. Si ce temps est inférieur à la durée minimale définie, un
     * nombre fixe de cases est masqué (DIFFICILE_MOY_CACHEES). Sinon, ce nombre est aléatoire,
     * compris entre MOYEN_MAX_CACHEES et DIFFICILE_MAX_CACHEES.
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
                DIFFICILE_MOY_CACHEES,
                MOYEN_MAX_CACHEES,
                DIFFICILE_MAX_CACHEES,
                somme -> somme >= moyenMaxPossibilites);
    }

    /**
     * Cache des cases dans la grille et calcule la somme des possibilités restantes. Copie la
     * grille résolue, cache un nombre de cases spécifié, puis calcule la somme des possibilités
     * pour la grille à résoudre.
     *
     * @param grilleResolue La grille de Sudoku résolue (source).
     * @param grilleAResoudre La grille de Sudoku à résoudre (destination), MODIFIÉE par la méthode.
     * @param nombreDeCasesACacher Nombre de cases à cacher.
     * @return La somme des possibilités après avoir caché des cases.
     * @implNote Méthode utilisée dans une boucle pour ajuster la difficulté de la grille en
     *     fonction des possibilités.
     */
    private int getPossibilitesGrilleWhileNok(
            int[] grilleResolue, int[] grilleAResoudre, int nombreDeCasesACacher) {
        System.arraycopy(grilleResolue, 0, grilleAResoudre, 0, NOMBRE_CASES);
        cacherLesCases(nombreDeCasesACacher, grilleAResoudre);
        return sommeDesPossibilitesDeLaGrille(getPossibilites(grilleAResoudre));
    }

    /**
     * Calcule la durée de temps écoulé entre la dernière et la nouvelle demande de grille en
     * millisecondes
     *
     * @return Durée en millisecondes
     */
    private long dureeEnMs() {
        LocalDateTime nouvelleDemande = LocalDateTime.now();
        return Duration.between(derniereDemande, nouvelleDemande).toMillis();
    }

    /**
     * Cache un nombre spécifié de cases dans la grille en les remplaçant par zéro.
     *
     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille (au maximum 81 cases).
     * @param grilleAResoudre Le tableau représentant la grille, où les cases sélectionnées seront
     *     mises à 0.
     */
    private void cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, NOMBRE_CASES);
        int[] indices = Arrays.copyOf(DEFAULT_INDICES, NOMBRE_CASES);
        for (int i = 0; i < nombreDeCasesACacher; i++) {
            int randomIndex = SecureRandomGenerator.INSTANCE.nextInt(NOMBRE_CASES - i);
            int indexToHide = indices[randomIndex];
            grilleAResoudre[indexToHide] = 0;
            indices[randomIndex] = indices[NOMBRE_CASES - 1 - i];
        }
    }

    /**
     * Génère un nombre entier aléatoire dans un intervalle spécifié.
     *
     * @param minInclus La valeur minimale incluse du nombre aléatoire à générer.
     * @param maxExclus La valeur maximale exclue du nombre aléatoire à générer.
     * @return Un entier aléatoire compris entre minInclus (inclus) et maxExclus (exclus).
     */
    private int nombreAleatoire(final int minInclus, final int maxExclus) {
        return SecureRandomGenerator.INSTANCE.nextInt(minInclus, maxExclus);
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
            // Retire cette valeur des possibilités de la case
            possibilitesDeLaCase &= ~(1 << (valeur - 1));
            // Place la valeur dans la grille
            grille[index] = valeur;
            // Crée une copie des possibilités pour la récursion
            int[] nouvellesPossibilites = Arrays.copyOf(possibilites, NOMBRE_CASES);
            // Élimine la valeur choisie des possibilités des cases affectées
            eliminerPossibilite(
                    nouvellesPossibilites, index / DIMENSION, index % DIMENSION, valeur);
            // Appel récursif pour remplir le reste de la grille
            if (remplirLaGrille(grille, nouvellesPossibilites)) {
                return true;
            }
        }
        // Si aucune valeur ne fonctionne, réinitialise la case et retourne false
        grille[index] = 0;
        return false;
    }

    /**
     * Choisit une valeur aléatoire parmi les possibilités données.
     *
     * @param possibilitesDeLaCase Entier représentant les valeurs possibles.
     * @return Une valeur choisie aléatoirement.
     */
    private int choisirValeurAleatoire(final int possibilitesDeLaCase) {
        int nombrePossibilites = compterBits(possibilitesDeLaCase);
        // Génère un index aléatoire parmi les possibilités
        int choix = SecureRandomGenerator.INSTANCE.nextInt(nombrePossibilites);
        // Parcourt les bits de possibilitesDeLaCase
        for (int i = 0; i < DIMENSION; i++) {
            // Vérifie si le bit i est à 1 (donc si i+1 est une possibilité)
            if ((possibilitesDeLaCase & (1 << i)) != 0) {
                if (choix == 0) {
                    return i + 1;
                }
                choix--;
            }
        }
        return 0;
    }

    /**
     * Calcule le pourcentage de possibilités estimé en fonction de la somme des possibilités
     * fournies. Le pourcentage est calculé selon la formule suivante : (sommeDesPossibilites -
     * 4800) * 100 / (40000 - 4800) Le résultat est ensuite limité à une plage de 0 à 100 pour
     * garantir qu'il représente un pourcentage valide.
     *
     * @param sommeDesPossibilites La somme des possibilités à partir de laquelle le pourcentage est
     *     calculé.
     * @return Le pourcentage des possibilités, compris entre 0 et 100.
     */
    private int getPourcentageDesPossibilites(int sommeDesPossibilites) {
        // le pourcentage de possibilités estimé
        int pourcentageDesPossibilites =
                ((sommeDesPossibilites - MIN_POSSIBILITES) * POURCENTAGE_MAX)
                        / (MAX_POSSIBILITES - MIN_POSSIBILITES);
        // Limiter le pourcentage entre 0 et 100
        pourcentageDesPossibilites =
                pourcentageDesPossibilites < 0
                        ? 0
                        : Math.min(pourcentageDesPossibilites, POURCENTAGE_MAX);
        return pourcentageDesPossibilites;
    }

    @Override
    public GrilleResolue resoudreLaGrille(final int[] grille) {
        // Lever une exception si la grille est null, ou si sa taille est différente de 81
        if (grille == null || grille.length != NOMBRE_CASES) {
            String taille = (grille == null) ? "null" : String.valueOf(grille.length);
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The grid must not be null and must contain exactly 81 cells, but was "
                            + taille);
        }
        // Calcul des possibilités pour chaque case
        int[] possibilites = getPossibilites(grille);
        // Vérifie que les valeurs de la grille respectent les règles
        int reglesOk = verifieQueChaqueValeurEstUnePossibilite(grille, possibilites);
        // Les règles sont-elles respectées
        if (reglesOk == -1) {
            return new GrilleResolue(false, grille, 0);
        }
        // La grille ne contient pas de 0
        if (Arrays.stream(grille).noneMatch(val -> val == 0)) {
            return new GrilleResolue(true, grille, 0);
        }
        // Remplir la grille
        boolean grilleRemplie = remplirLaGrille(grille, possibilites);
        // La grille n'est pas remplie
        if (!grilleRemplie) {
            return new GrilleResolue(false, grille, 0);
        }
        return new GrilleResolue(
                true,
                grille,
                getPourcentageDesPossibilites(sommeDesPossibilitesDeLaGrille(possibilites)));
    }

    @Override
    public GrillesCrees creerLesGrilles(final int niveau) {
        // Lever une exception si le niveau n'existe pas
        if (niveau < 1 || niveau > 3) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The grid level must be between 1 and 3, but was " + niveau);
        }
        // Initialiser la grille résolue
        int[] grilleResolue = new int[NOMBRE_CASES];
        // Résoudre la grille
        resoudreLaGrille(grilleResolue);
        // Initialiser la grille à résoudre
        int[] grilleAResoudre = new int[NOMBRE_CASES];
        // En fonction du niveau, cacher un certain nombre de cases et tenir compte des possibilités
        int sommeDesPossibilites = genererLaGrilleAResoudre(niveau, grilleResolue, grilleAResoudre);
        // Récupérer le pourcentage de possibilités estimé
        int pourcentageDesPossibilites = getPourcentageDesPossibilites(sommeDesPossibilites);
        // Crée un record GrillesCrees structuré contenant les deux grilles et le pourcentage
        GrillesCrees grillesCrees =
                new GrillesCrees(grilleResolue, grilleAResoudre, pourcentageDesPossibilites);
        // Valide l'intégrité du record
        jakartaValidator.validateOrThrow(grillesCrees);
        return grillesCrees;
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
        this.moyenMinPossibilites = TEST_PSSIBILITE_MOYENNE_IMPOSSIBLE;
        this.moyenMaxPossibilites = -1;
    }

    /**
     * Définit une possibilité difficile inaccessible, pour générer une grille par défaut après une
     * seconde. Cette méthode est uniquement utilisée pour les tests.
     */
    void setDifficultImpossiblePossibilitiesForTests() {
        this.moyenMaxPossibilites = TEST_PSSIBILITE_MOYENNE_IMPOSSIBLE;
    }
}
