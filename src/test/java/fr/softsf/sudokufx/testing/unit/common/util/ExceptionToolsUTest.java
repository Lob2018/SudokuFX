/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionToolsUTest {

    @Test
    void
            givenThrowable_whenFindSQLInvalidAuthorizationSpecException_thenReturnsSQLInvalidAuthException() {
        Throwable t = new Throwable(new SQLInvalidAuthorizationSpecException());
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.findSQLInvalidAuthException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
    }

    @Test
    void givenThrowable_whenFindSQLInvalidAuthException_thenReturnsNull() {
        Throwable t = new Throwable(new Exception());
        SQLInvalidAuthorizationSpecException result =
                ExceptionTools.INSTANCE.findSQLInvalidAuthException(t);
        assertNull(result);
    }

    @Test
    void
            givenMessage_whenLogAndInstantiateIllegalArgument_thenReturnsIllegalArgumentExceptionWithMessage() {
        String message = "Invalid argument provided";
        IllegalArgumentException exception =
                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(message);
        assertNotNull(exception);
        assertEquals(message, exception.getMessage());

        IllegalArgumentException thrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            throw exception;
                        });
        assertEquals(message, thrown.getMessage());
    }
}
