/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.MyRegex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyRegexUTest {
    @Test
    void givenNullTextOrPattern_whenIsValidatedByRegex_thenThrowsNullPointerException() {
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        NullPointerException ex1 =
                assertThrows(
                        NullPointerException.class,
                        () -> MyRegex.INSTANCE.isValidatedByRegex(null, secretPattern));
        assertEquals(
                "MyRegex>isValidatedByRegex : The text to validate must not be null.",
                ex1.getMessage());
        NullPointerException ex2 =
                assertThrows(
                        NullPointerException.class,
                        () -> MyRegex.INSTANCE.isValidatedByRegex("someText", null));
        assertEquals(
                "MyRegex>isValidatedByRegex : The pattern must not be null.", ex2.getMessage());
        NullPointerException ex3 =
                assertThrows(
                        NullPointerException.class,
                        () -> MyRegex.INSTANCE.isValidatedByRegex(null, null));
        assertEquals(
                "MyRegex>isValidatedByRegex : The text to validate must not be null.",
                ex3.getMessage());
    }
}
