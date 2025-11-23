/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import fr.softsf.sudokufx.common.enums.MyRegex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class MyRegexUTest {

    private final Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
    private final Pattern alphaNumPattern = MyRegex.INSTANCE.getAlphanumericPattern();

    private void assertThrowsWithMessage(Executable executable, String expectedMessage) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, executable);
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void givenNullOrBlankText_whenIsValidatedByRegex_thenThrowsIllegalArgumentException() {
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidatedByRegex(null, secretPattern),
                "The text to validate must not be null or blank, but was null");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidatedByRegex("", secretPattern),
                "The text to validate must not be null or blank, but was ");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidatedByRegex("   ", secretPattern),
                "The text to validate must not be null or blank, but was    ");
    }

    @Test
    void givenNullPattern_whenIsValidatedByRegex_thenThrowsIllegalArgumentException() {
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidatedByRegex("someText", null),
                "The pattern must not be null");
    }

    @Test
    void givenSecretPattern_whenIsValidatedByRegex_thenUsesStrictPasswordValidation() {
        // valid password example (24 chars, with at least 2 lowercase, 2 uppercase, 2 digits, 2
        // special chars)
        String validPassword = "Ab1@Cd2#Ef3$Gh4%Ij5&Kl6!";
        assertTrue(MyRegex.INSTANCE.isValidatedByRegex(validPassword, secretPattern));
        // invalid password: missing special chars
        String invalidPassword = "Ab1Cd2Ef3Gh4Ij5Kl6Mn7Op8";
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex(invalidPassword, secretPattern));
    }

    @Test
    void givenOtherPattern_whenIsValidatedByRegex_thenUsesPatternMatching() {
        assertTrue(MyRegex.INSTANCE.isValidatedByRegex("Hello World.", alphaNumPattern));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("Hello@World!", alphaNumPattern));
    }
}
