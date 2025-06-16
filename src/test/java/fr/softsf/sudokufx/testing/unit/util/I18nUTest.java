/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.util;

import java.util.Locale;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import fr.softsf.sudokufx.enums.I18n;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class I18nUTest {

    @Test
    @Order(0)
    void givenDefaultLocale_whenGetLanguage_thenReturnsFrench() {
        String fr = I18n.INSTANCE.getLanguage();
        assertEquals("fr", fr);
    }

    @Test
    @Order(1)
    void givenDefaultLocale_whenGetValue_thenReturnsFrenchValue() {
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    @Order(2)
    void givenEnglishLocale_whenGetValue_thenReturnsEnglishValue() {
        I18n.INSTANCE.setLocaleBundle("EN");
        String testEN = I18n.INSTANCE.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    @Order(3)
    void givenEnglishLocale_whenGetLanguage_thenReturnsEnglish() {
        String en = I18n.INSTANCE.getLanguage();
        assertEquals("en", en);
    }

    @Test
    @Order(4)
    void givenEmptyLocale_whenGetValue_thenReturnsFrenchValue() {
        I18n.INSTANCE.setLocaleBundle("");
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    @Order(5)
    void givenHostEnvironmentWithEnglishLocale_whenGetValue_thenReturnsEnglishValu() {
        Locale.setDefault(Locale.ENGLISH);
        I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        String testEN = I18n.INSTANCE.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    @Order(6)
    void givenHostEnvironmentWithFrenchLocale_whenGetValue_thenReturnsFrenchValue() {
        Locale.setDefault(Locale.FRENCH);
        I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    @Order(7)
    void givenNullKey_whenGetValue_thenReturnsErrorString() {
        String result = I18n.INSTANCE.getValue(null);
        assertEquals("???null???", result);
    }

    @Test
    @Order(8)
    void givenMissingKey_whenGetValue_thenReturnsMissingKeyString() {
        String result = I18n.INSTANCE.getValue("missingKey");
        assertEquals("???missing:missingKey???", result);
    }
}
