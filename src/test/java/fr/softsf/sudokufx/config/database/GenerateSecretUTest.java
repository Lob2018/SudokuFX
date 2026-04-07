/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.util.MyRegex;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GenerateSecretUTest {

    private final GenerateSecret generateSecret;

    @Autowired
    public GenerateSecretUTest(GenerateSecret generateSecret) {
        this.generateSecret = generateSecret;
    }

    @Test
    void givenPassayGenerator_whenGeneratePassaySecret_thenSecretIsValid() {
        String secret = generateSecret.generatePassaySecret();
        assertTrue(
                secret.length() >= 24 && secret.length() <= 32,
                "Le secret doit avoir une longueur comprise entre 24 et 32. Actuelle : "
                        + secret.length());
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(secret, secretPattern),
                "Generated secret should be valid according to secretPattern: " + secret);
    }

    @Test
    void givenInvalidSecrets_whenIsValidatedByRegex_thenReturnsFalse() {
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        String[] invalidSecrets = {
            "L".repeat(24), // Pas de minuscules/chiffres/spéciaux
            "l".repeat(32), // Pas de majuscules/chiffres/spéciaux
            "@".repeat(28), // Pas de lettres/chiffres
            "1".repeat(24), // Pas de lettres/spéciaux
            "9uCQD1x$^UeWfn#OAb!Y1YF", // Trop court (23 caractères)
            "9uCQD1x$^UeWfn#OAb!Y1YFH1AABBC123", // Trop long (plus de 32)
            "uCQD1x$^UeWfn#OAb!YjYFHo", // Manque un chiffre
            "-CQD1x$^UeWfn#OAb!Y1YFH1", // Caractère interdit '-' (si votre regex l'exclut)
            "9uCQD1xi^UeWfntOAbmY1YFH" // Manque un caractère spécial
        };

        for (String secret : invalidSecrets) {
            assertFalse(
                    MyRegex.INSTANCE.isValidatedByRegex(secret, secretPattern),
                    "Secret should be invalid: " + secret);
        }
    }
}
