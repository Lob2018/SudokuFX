/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.utils;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.exceptions.ExceptionTools;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {

    @Test
    void
            givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsSQLInvalidAuthorizationSpecException() {
        Throwable t = new Throwable(new SQLInvalidAuthorizationSpecException());
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
    }

    @Test
    void givenThrowable_whenGetSQLInvalidAuthorizationSpecException_thenReturnsNull() {
        Throwable t = new Throwable(new Exception());
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(t);
        assertNull(result);
    }

    @Test
    void
            givenMessage_whenLogAndThrowIllegalArgument_thenThrowsIllegalArgumentExceptionWithMessage() {
        String message = "Invalid argument provided";
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ExceptionTools.INSTANCE.logAndThrowIllegalArgument(message));
        assertEquals(message, exception.getMessage());
    }
}
