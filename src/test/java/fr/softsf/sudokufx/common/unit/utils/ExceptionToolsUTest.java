/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.common.unit.utils;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.exceptions.ExceptionTools;

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
