/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import fr.softsf.sudokufx.enums.I18n;

/** Enum singleton working with date and time operations. */
public enum MyDateTime {
    INSTANCE;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Clock CLOCK = Clock.systemDefaultZone();

    private final DateTimeFormatter frenchFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm", Locale.FRENCH);
    private final DateTimeFormatter englishFormatter =
            DateTimeFormatter.ofPattern("MM/dd/yy HH:mm", Locale.ENGLISH);

    /**
     * Returns the current time formatted as "HH:mm:ss".
     *
     * @return A string representing the current time in "HH:mm:ss" format.
     */
    public String getFormattedCurrentTime() {
        return LocalDateTime.now(CLOCK).format(TIME_FORMATTER);
    }

    /**
     * Formats the given LocalDateTime according to the current language setting.
     *
     * @param updatedAt The LocalDateTime object to be formatted.
     * @return A string representing the formatted date and time based on the current locale.
     */
    public String getFormatted(LocalDateTime updatedAt) {
        DateTimeFormatter formatter =
                I18n.INSTANCE.getLanguage().equals("fr") ? frenchFormatter : englishFormatter;
        return updatedAt.format(formatter);
    }
}
