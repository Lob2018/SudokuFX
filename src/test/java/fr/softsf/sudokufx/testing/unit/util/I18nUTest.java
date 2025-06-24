/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.util;

import java.util.Locale;

import org.junit.jupiter.api.*;

import fr.softsf.sudokufx.enums.I18n;

import static org.junit.jupiter.api.Assertions.assertEquals;

class I18nUTest {

    private String originalLang;
    private Locale originalDefault;

    @BeforeEach
    void saveOriginalLanguageAndLocale() {
        originalLang = I18n.INSTANCE.getLanguage();
        originalDefault = Locale.getDefault();
    }

    @AfterEach
    void restoreOriginalLanguageAndLocale() {
        if ("fr".equalsIgnoreCase(originalLang)) {
            I18n.INSTANCE.setLocaleBundle("FR");
        } else if ("en".equalsIgnoreCase(originalLang)) {
            I18n.INSTANCE.setLocaleBundle("EN");
        } else {
            I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        }
        Locale.setDefault(originalDefault);
    }

    @Test
    void givenFrenchLocale_whenGetLanguage_thenReturnsFrench() {
        I18n.INSTANCE.setLocaleBundle("FR");
        String lang = I18n.INSTANCE.getLanguage();
        assertEquals("fr", lang);
    }

    @Test
    void givenFrenchLocale_whenGetValue_thenReturnsFrenchValue() {
        I18n.INSTANCE.setLocaleBundle("FR");
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    void givenEnglishLocale_whenGetValue_thenReturnsEnglishValue() {
        I18n.INSTANCE.setLocaleBundle("EN");
        String testEN = I18n.INSTANCE.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    void givenEnglishLocale_whenGetLanguage_thenReturnsEnglish() {
        I18n.INSTANCE.setLocaleBundle("EN");
        String lang = I18n.INSTANCE.getLanguage();
        assertEquals("en", lang);
    }

    @Test
    void givenEmptyLocale_whenGetValue_thenReturnsFrenchValue() {
        I18n.INSTANCE.setLocaleBundle("");
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    void givenHostEnvironmentWithEnglishLocale_whenGetValue_thenReturnsEnglishValue() {
        Locale.setDefault(Locale.ENGLISH);
        I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        String testEN = I18n.INSTANCE.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    void givenHostEnvironmentWithFrenchLocale_whenGetValue_thenReturnsFrenchValue() {
        Locale.setDefault(Locale.FRENCH);
        I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        String testFR = I18n.INSTANCE.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    void givenNullKey_whenGetValue_thenReturnsErrorString() {
        String result = I18n.INSTANCE.getValue(null);
        assertEquals("???null???", result);
    }

    @Test
    void givenMissingKey_whenGetValue_thenReturnsMissingKeyString() {
        String result = I18n.INSTANCE.getValue("missingKey");
        assertEquals("???missing:missingKey???", result);
    }
}
