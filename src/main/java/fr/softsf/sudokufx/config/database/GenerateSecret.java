/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.util.ArrayList;
import java.util.List;

import org.passay.data.CharacterData;
import org.passay.data.EnglishCharacterData;
import org.passay.generate.PasswordGenerator;
import org.passay.rule.CharacterRule;
import org.passay.rule.Rule;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.annotation.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.common.util.SecureRandomGenerator;

import static org.passay.rule.IllegalCharacterRule.ERROR_CODE;

/** Utility class for generating secure passwords using the Passay library. */
@Component
public class GenerateSecret {

    private static final int MIN_SECRET_LENGTH = 24;
    private static final int MAX_SECRET_LENGTH = 32;

    /**
     * Define the secret special characters
     *
     * @return The special characters
     */
    private static CharacterData createSpecialChars() {
        return new CharacterData() {
            @Override
            @ExcludedFromCoverageReportGenerated
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&()";
            }
        };
    }

    /**
     * Generate Passay secret with random length using SecureRandomGenerator.
     *
     * @return The Passay secret
     */
    public String generatePassaySecret() {
        int randomLength =
                SecureRandomGenerator.INSTANCE.nextInt(MIN_SECRET_LENGTH, MAX_SECRET_LENGTH + 1);
        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase, 2);
        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase, 2);
        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit, 2);
        CharacterRule specialCharsRule = new CharacterRule(createSpecialChars(), 2);
        List<Rule> rules = new ArrayList<>();
        rules.add(specialCharsRule);
        rules.add(lowerCaseRule);
        rules.add(upperCaseRule);
        rules.add(digitRule);
        PasswordGenerator gen = new PasswordGenerator(randomLength, rules);
        return gen.generate().toString();
    }
}
