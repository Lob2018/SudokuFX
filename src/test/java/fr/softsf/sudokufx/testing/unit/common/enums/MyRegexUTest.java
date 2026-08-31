/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import fr.softsf.sudokufx.common.util.MyRegex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyRegexUTest {

    private void assertThrowsWithMessage(Executable executable, String expectedMessage) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, executable);
        assertEquals(expectedMessage, ex.getMessage());
    }

    @Test
    void givenNullOrBlankText_whenIsValidAlphanumeric_thenThrowsIllegalArgumentException() {
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidAlphanumeric(null),
                "The text to validate must not be null or blank, but was null");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidAlphanumeric(""),
                "The text to validate must not be null or blank, but was ");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidAlphanumeric("   "),
                "The text to validate must not be null or blank, but was    ");
    }

    @Test
    void
            givenNullOrEmptyOrBlankCharArrayText_whenIsValidSecret_thenThrowsIllegalArgumentException() {
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidSecret(null), "The secret must not be null or empty");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidSecret(new char[0]),
                "The secret must not be null or empty");
        assertThrowsWithMessage(
                () -> MyRegex.INSTANCE.isValidSecret(new char[] {' ', ' '}),
                "The secret must not be blank");
    }

    @Test
    void givenSecretPattern_whenIsValidSecret_thenUsesStrictPasswordValidation() {
        String validPassword = "Ab1@Cd2#Ef3$Gh4%Ij5&Kl6!";
        assertTrue(MyRegex.INSTANCE.isValidSecret(validPassword.toCharArray()));

        String invalidPassword = "Ab1Cd2Ef3Gh4Ij5Kl6Mn7Op8";
        assertFalse(MyRegex.INSTANCE.isValidSecret(invalidPassword.toCharArray()));
    }

    @Test
    void givenOtherPattern_whenIsValidAlphanumeric_thenUsesPatternMatching() {
        assertTrue(MyRegex.INSTANCE.isValidAlphanumeric("Hello World."));
        assertFalse(MyRegex.INSTANCE.isValidAlphanumeric("Hello@World!"));
    }
}
