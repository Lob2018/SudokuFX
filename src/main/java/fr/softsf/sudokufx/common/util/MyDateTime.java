/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Enum singleton working with date and time operations. Handles timezone conversions and daylight
 * saving time automatically.
 */
public enum MyDateTime {
    INSTANCE;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter frenchFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yy HH:mm", Locale.FRENCH);
    private final DateTimeFormatter englishFormatter =
            DateTimeFormatter.ofPattern("MM/dd/yy HH:mm", Locale.ENGLISH);
    private Clock clock = Clock.systemDefaultZone();

    /**
     * Sets the clock (useful for testing). Package-private to restrict usage to test classes.
     *
     * @param clock the clock to use; must not be {@code null}
     * @throws NullPointerException if {@code clock} is {@code null}
     */
    void setClock(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "Clock must not be null");
    }

    /**
     * Resets the clock to system default zone. Package-private to restrict usage to test classes.
     */
    void resetClock() {
        this.clock = Clock.systemDefaultZone();
    }

    /**
     * Returns the current instant (UTC timestamp). No ambiguity with daylight saving time changes.
     *
     * @return the current instant
     */
    public Instant getCurrentInstant() {
        return Instant.now(clock);
    }

    /**
     * Returns the current time formatted as "HH:mm:ss" in the system timezone. Automatically
     * handles daylight saving time transitions.
     *
     * @return A string representing the current time in "HH:mm:ss" format.
     */
    public String getFormattedCurrentTime() {
        return ZonedDateTime.now(clock).format(TIME_FORMATTER);
    }

    /**
     * Formats the given {@link Instant} based on the current locale. Uses the clock's timezone for
     * conversion (defaults to system timezone). Automatically handles daylight saving time
     * transitions.
     *
     * @param timestamp the timestamp to format; must not be {@code null}
     * @return the formatted date-time string
     * @throws IllegalArgumentException if {@code timestamp} is {@code null}
     */
    public String getFormatted(Instant timestamp) {
        if (Objects.isNull(timestamp)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The timestamp mustn't be null");
        }
        DateTimeFormatter formatter = getLocaleFormatter();
        ZoneId zone = clock.getZone();
        return timestamp.atZone(zone).format(formatter);
    }

    /**
     * Returns the appropriate formatter based on current locale.
     *
     * @return DateTimeFormatter for French or English
     */
    private DateTimeFormatter getLocaleFormatter() {
        return I18n.INSTANCE.getLanguage().equals("fr") ? frenchFormatter : englishFormatter;
    }
}
