/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
        Throwable result = ExceptionTools.INSTANCE.findCriticalDatabaseException(t);
        assertInstanceOf(SQLInvalidAuthorizationSpecException.class, result);
        SQLInvalidAuthorizationSpecException sqlEx = (SQLInvalidAuthorizationSpecException) result;
        assertNotNull(sqlEx);
    }

    @Test
    void givenThrowable_whenFindCriticalDatabaseException_thenReturnsNull() {
        Throwable t = new Throwable(new Exception());
        Throwable result = ExceptionTools.INSTANCE.findCriticalDatabaseException(t);
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
