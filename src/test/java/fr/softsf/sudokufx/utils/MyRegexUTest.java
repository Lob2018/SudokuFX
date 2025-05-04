package fr.softsf.sudokufx.utils;

import fr.softsf.sudokufx.enums.MyRegex;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyRegexUTest {
    @Test
    void givenNullTextOrPattern_whenIsValidatedByRegex_thenThrowsNullPointerException() {
        Pattern secretPattern = MyRegex.INSTANCE.getSecretPattern();
        NullPointerException ex1 = assertThrows(NullPointerException.class, () ->
                MyRegex.INSTANCE.isValidatedByRegex(null, secretPattern));
        assertEquals("MyRegex>isValidatedByRegex : The text to validate must not be null.", ex1.getMessage());
        NullPointerException ex2 = assertThrows(NullPointerException.class, () ->
                MyRegex.INSTANCE.isValidatedByRegex("someText", null));
        assertEquals("MyRegex>isValidatedByRegex : The pattern must not be null.", ex2.getMessage());
        NullPointerException ex3 = assertThrows(NullPointerException.class, () ->
                MyRegex.INSTANCE.isValidatedByRegex(null, null));
        assertEquals("MyRegex>isValidatedByRegex : The text to validate must not be null.", ex3.getMessage());
    }
}
