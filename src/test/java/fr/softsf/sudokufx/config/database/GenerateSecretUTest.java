/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.MyRegex;

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
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(secret, secretPattern),
                "Generated secret should be valid according to secretPattern");
    }

    @Test
    void givenInvalidSecrets_whenIsValidatedByRegex_thenReturnsFalse() {
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        String[] invalidSecrets = {
            "",
            "LLLLLLLLLLLLLLLLLLLLLLLL",
            "llllllllllllllllllllllll",
            "@@@@@@@@@@@@@@@@@@@@@@@@",
            "111111111111111111111111",
            "                        ",
            "uCQD1x$^UeWfn#OAb!YjYFHo",
            "-CQD1x$^UeWfn#OAb!Y1YFH1",
            "9uCQD1xi^UeWfntOAbmY1YFH",
            "9uCQD1x$^UeWfn#OAb!Y1YFH1",
            "9uCQD1x$^UeWfn#OAb!Y1YF"
        };
        for (String secret : invalidSecrets) {
            assertFalse(
                    MyRegex.INSTANCE.isValidatedByRegex(secret, secretPattern),
                    "Secret should be invalid: " + secret);
        }
    }
}
