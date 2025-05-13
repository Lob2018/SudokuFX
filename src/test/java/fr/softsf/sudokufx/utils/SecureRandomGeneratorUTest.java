/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.enums.SecureRandomGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SecureRandomGeneratorUTest {

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(SecureRandomGenerator.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(SecureRandomGenerator.class)).detachAndStopAllAppenders();
    }

    @Test
    void givenNegativeBound_whenNextInt_thenErrorLogged() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    SecureRandomGenerator.INSTANCE.nextInt(-1);
                },
                "The nextInt bound must be positive");
        assert (logWatcher.list.getFirst().getFormattedMessage())
                .contains("██ Exception caught from nextInt(bound) :");
    }

    @Test
    void givenOriginEqualsBound_whenNextInt_thenErrorLogged() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    SecureRandomGenerator.INSTANCE.nextInt(1, 1);
                },
                "The nextInt origin must be less than bound");
        assert (logWatcher.list.getFirst().getFormattedMessage())
                .contains("██ Exception caught from nextInt(origin,bound) :");
    }

    @Test
    void givenOriginIsSuperiorBound_whenNextInt_thenErrorLogged() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    SecureRandomGenerator.INSTANCE.nextInt(2, 1);
                },
                "The nextInt origin must be less than bound");
        assert (logWatcher.list.getFirst().getFormattedMessage())
                .contains("██ Exception caught from nextInt(origin,bound) :");
    }
}
