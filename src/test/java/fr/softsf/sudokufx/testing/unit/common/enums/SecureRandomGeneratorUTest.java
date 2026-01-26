/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.common.enums.SecureRandomGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String lastLogMessage = logWatcher.list.getFirst().getFormattedMessage();
        String expectedLogFragment = "██ Exception caught from nextInt(bound) :";
        assertTrue(
                lastLogMessage.contains(expectedLogFragment),
                "Expected log message to contain: \""
                        + expectedLogFragment
                        + "\"\nActual message: \""
                        + lastLogMessage
                        + "\"");
    }

    @Test
    void givenOriginEqualsBound_whenNextInt_thenErrorLogged() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    SecureRandomGenerator.INSTANCE.nextInt(1, 1);
                },
                "The nextInt origin must be less than bound");
        String firstLogMessage = logWatcher.list.getFirst().getFormattedMessage();
        String expectedLogFragment = "██ Exception caught from nextInt(origin,bound) :";
        assertTrue(
                firstLogMessage.contains(expectedLogFragment),
                "Expected log message to contain: \""
                        + expectedLogFragment
                        + "\"\nActual message: \""
                        + firstLogMessage
                        + "\"");
    }

    @Test
    void givenOriginIsSuperiorBound_whenNextInt_thenErrorLogged() {
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    SecureRandomGenerator.INSTANCE.nextInt(2, 1);
                },
                "The nextInt origin must be less than bound");
        String firstLogMessage = logWatcher.list.getFirst().getFormattedMessage();
        String expectedLogFragment = "██ Exception caught from nextInt(origin,bound) :";
        assertTrue(
                firstLogMessage.contains(expectedLogFragment),
                "Expected log message to contain: \""
                        + expectedLogFragment
                        + "\"\nActual message: \""
                        + firstLogMessage
                        + "\"");
    }
}
