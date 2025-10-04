/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.I18n;

import static org.junit.jupiter.api.Assertions.*;

class MyDateTimeUTest {

    private String originalLang;
    private static final ZoneId PARIS_ZONE = ZoneId.of("Europe/Paris");

    @BeforeEach
    void setUp() {
        originalLang = I18n.INSTANCE.getLanguage();
        MyDateTime.INSTANCE.resetClock();
    }

    @AfterEach
    void tearDown() {
        if ("fr".equalsIgnoreCase(originalLang)) {
            I18n.INSTANCE.setLocaleBundle("FR");
        } else if ("en".equalsIgnoreCase(originalLang)) {
            I18n.INSTANCE.setLocaleBundle("EN");
        } else {
            I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
        }
        MyDateTime.INSTANCE.resetClock();
    }

    @Test
    void givenNow_whenGetFormattedCurrentTime_thenReturnsFormattedTime() {
        String formatted = MyDateTime.INSTANCE.getFormattedCurrentTime();
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void givenFixedClock_whenGetFormattedCurrentTime_thenReturnsExpectedTime() {
        Instant fixedInstant = Instant.parse("2024-06-24T12:30:45Z");
        Clock fixedClock = Clock.fixed(fixedInstant, PARIS_ZONE);
        MyDateTime.INSTANCE.setClock(fixedClock);
        String formatted = MyDateTime.INSTANCE.getFormattedCurrentTime();
        assertEquals("14:30:45", formatted);
    }

    @Test
    void givenFrenchLocale_whenGetFormattedWithInstant_thenReturnsFrenchFormat() {
        I18n.INSTANCE.setLocaleBundle("FR");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 24, 14, 30);
        Instant instant = localDateTime.atZone(PARIS_ZONE).toInstant();
        MyDateTime.INSTANCE.setClock(Clock.system(PARIS_ZONE));
        String formatted = MyDateTime.INSTANCE.getFormatted(instant);
        assertEquals("24/06/24 14:30", formatted);
    }

    @Test
    void givenEnglishLocale_whenGetFormattedWithInstant_thenReturnsEnglishFormat() {
        I18n.INSTANCE.setLocaleBundle("EN");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 6, 24, 14, 30);
        Instant instant = localDateTime.atZone(PARIS_ZONE).toInstant();
        MyDateTime.INSTANCE.setClock(Clock.system(PARIS_ZONE));
        String formatted = MyDateTime.INSTANCE.getFormatted(instant);
        assertEquals("06/24/24 14:30", formatted);
    }

    @Test
    void givenNull_whenGetFormattedWithInstant_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MyDateTime.INSTANCE.getFormatted((Instant) null));
        assertTrue(exception.getMessage().contains("timestamp mustn't be null"));
    }

    @Test
    void givenFrenchLocale_whenGetFormattedLocalWithInstant_thenReturnsFrenchFormat() {
        I18n.INSTANCE.setLocaleBundle("FR");
        Instant dateTime =
                LocalDateTime.of(2024, 6, 24, 14, 30).atZone(ZoneId.systemDefault()).toInstant();
        String formatted = MyDateTime.INSTANCE.getFormatted(dateTime);
        assertEquals("24/06/24 14:30", formatted);
    }

    @Test
    void givenEnglishLocale_whenGetFormattedLocalWithInstant_thenReturnsEnglishFormat() {
        I18n.INSTANCE.setLocaleBundle("EN");
        Instant dateTime =
                LocalDateTime.of(2024, 6, 24, 14, 30).atZone(ZoneId.systemDefault()).toInstant();
        String formatted = MyDateTime.INSTANCE.getFormatted(dateTime);
        assertEquals("06/24/24 14:30", formatted);
    }

    @Test
    void givenGetCurrentInstant_whenCalled_thenReturnsNonNullInstant() {
        Instant result = MyDateTime.INSTANCE.getCurrentInstant();
        assertNotNull(result);
        Instant now = Instant.now();
        long diffSeconds = Math.abs(now.getEpochSecond() - result.getEpochSecond());
        assertTrue(diffSeconds < 5, "Instant should be within 5 seconds of now");
    }
}
