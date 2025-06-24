/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.util;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.util.MyDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MyDateTimeUTest {

    private String originalLang;

    @BeforeEach
    void setUp() {
        originalLang = I18n.INSTANCE.getLanguage();
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
    }

    @Test
    void givenNow_whenGetFormattedCurrentTime_thenReturnsFormattedTime() {
        String formatted = MyDateTime.INSTANCE.getFormattedCurrentTime();
        assertNotNull(formatted);
        assertTrue(formatted.matches("\\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    void givenFrenchLocale_whenGetFormatted_thenReturnsFrenchFormat() {
        I18n.INSTANCE.setLocaleBundle("FR");
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 24, 14, 30);
        String formatted = MyDateTime.INSTANCE.getFormatted(dateTime);
        assertEquals("24/06/24 14:30", formatted);
    }

    @Test
    void givenEnglishLocale_whenGetFormatted_thenReturnsEnglishFormat() {
        I18n.INSTANCE.setLocaleBundle("EN");
        LocalDateTime dateTime = LocalDateTime.of(2024, 6, 24, 14, 30);
        String formatted = MyDateTime.INSTANCE.getFormatted(dateTime);
        assertEquals("06/24/24 14:30", formatted);
    }

    @Test
    void givenNull_whenGetFormatted_thenThrowsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> MyDateTime.INSTANCE.getFormatted(null));
        assertTrue(exception.getMessage().contains("The updatedAt mustn't be null"));
    }
}
