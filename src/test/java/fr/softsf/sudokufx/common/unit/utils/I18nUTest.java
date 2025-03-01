package fr.softsf.sudokufx.common.unit.utils;

import fr.softsf.sudokufx.utils.I18n;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class I18nUTest {

    @Test
    @Order(0)
    void givenDefaultLocale_whenGetLanguage_thenReturnsFrench() {
        String fr = I18n.getLanguage();
        assertEquals("fr", fr);
    }

    @Test
    @Order(1)
    void givenDefaultLocale_whenGetValue_thenReturnsFrenchValue() {
        String testFR = I18n.getValue("test");
        assertEquals("testFR", testFR);
    }


    @Test
    @Order(2)
    void givenEnglishLocale_whenGetValue_thenReturnsEnglishValue() {
        I18n.setLocaleBundle("EN");
        String testEN = I18n.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    @Order(3)
    void givenEnglishLocale_whenGetLanguage_thenReturnsEnglish() {
        String en = I18n.getLanguage();
        assertEquals("en", en);
    }

    @Test
    @Order(4)
    void givenEmptyLocale_whenGetValue_thenReturnsFrenchValue() {
        I18n.setLocaleBundle("");
        String testFR = I18n.getValue("test");
        assertEquals("testFR", testFR);
    }

    @Test
    @Order(5)
    void givenHostEnvironmentWithEnglishLocale_whenGetValue_thenReturnsEnglishValu() {
        Locale.setDefault(Locale.ENGLISH);
        I18n.setLanguageBasedOnTheHostEnvironment();
        String testEN = I18n.getValue("test");
        assertEquals("testUS", testEN);
    }

    @Test
    @Order(6)
    void givenHostEnvironmentWithFrenchLocale_whenGetValue_thenReturnsFrenchValue() {
        Locale.setDefault(Locale.FRENCH);
        I18n.setLanguageBasedOnTheHostEnvironment();
        String testFR = I18n.getValue("test");
        assertEquals("testFR", testFR);
    }
}
