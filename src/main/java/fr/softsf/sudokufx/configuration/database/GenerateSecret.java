/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.configuration.database;

import java.util.ArrayList;
import java.util.List;

import org.passay.CharacterData;
import org.passay.*;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

/** Utility class for generating secure passwords using the Passay library. */
@Component
final class GenerateSecret {

    /**
     * Define the secret special characters
     *
     * @return The special characters
     */
    private static CharacterData createSpecialChars() {
        return new CharacterData() {
            @ExcludedFromCoverageReportGenerated
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&()";
            }
        };
    }

    /**
     * Generate Passay secret
     *
     * @return The Passay secret
     */
    public String generatePassaySecret() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        CharacterData specialChars = createSpecialChars();
        CharacterRule specialCharsRule = new CharacterRule(specialChars);
        specialCharsRule.setNumberOfCharacters(2);
        List<Rule> rules = new ArrayList<>();
        rules.add(specialCharsRule);
        rules.add(lowerCaseRule);
        rules.add(upperCaseRule);
        rules.add(digitRule);
        return gen.generatePassword(24, rules);
    }
}
