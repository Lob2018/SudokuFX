/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {

    @Test
    void givenMessage_whenCreateAndLogIllegalArgument_thenReturnsExceptionWithMessage() {
        String message = "Invalid argument passed";
        IllegalArgumentException ex = ExceptionTools.INSTANCE.createAndLogIllegalArgument(message);
        assertNotNull(ex);
        assertEquals(message, ex.getMessage());
    }

    @Test
    void givenBlankString_whenLogAndThrowIllegalArgumentIfBlank_thenThrowsException() {
        String message = "String must not be blank";
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                                        " ", message));
        assertEquals(message, ex.getMessage());
    }

    @Test
    void givenNullString_whenLogAndThrowIllegalArgumentIfBlank_thenThrowsException() {
        String message = "Null is not allowed";
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                                        null, message));
        assertEquals(message, ex.getMessage());
    }

    @Test
    void givenValidString_whenLogAndThrowIllegalArgumentIfBlank_thenDoesNotThrow() {
        assertDoesNotThrow(
                () ->
                        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                                "hello", "message"));
    }

    @Test
    void givenExceptionChainWithSQLInvalidAuthorizationSpecException_whenGet_thenReturnsIt() {
        SQLInvalidAuthorizationSpecException target =
                new SQLInvalidAuthorizationSpecException("auth failed");
        RuntimeException wrapped = new RuntimeException(new RuntimeException(target));
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(wrapped);
        assertNotNull(result);
        assertSame(target, result);
    }

    @Test
    void givenExceptionChainWithoutSQLInvalidAuthorizationSpecException_whenGet_thenReturnsNull() {
        RuntimeException wrapped =
                new RuntimeException(new IllegalStateException("no SQL exception"));
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(wrapped);
        assertNull(result);
    }
}
