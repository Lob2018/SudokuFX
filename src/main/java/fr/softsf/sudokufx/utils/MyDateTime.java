/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

/** Component working with date and time operations. */
@Component
public final class MyDateTime {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final Clock clock = Clock.systemDefaultZone();

    /**
     * Returns the current time formatted as "HH:mm:ss".
     *
     * @return A string representing the current time in "HH:mm:ss" format.
     */
    public String getFormattedCurrentTime() {
        return LocalDateTime.now(clock).format(TIME_FORMATTER);
    }
}
