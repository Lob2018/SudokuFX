/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.exceptions;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for handling and analyzing exceptions. This enum provides methods to search for
 * specific exception types within exception chains.
 */
public enum ExceptionTools {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(ExceptionTools.class);

    /**
     * Logs the given message and throws an {@link IllegalArgumentException} with it.
     *
     * @param message the error message
     * @throws IllegalArgumentException always thrown with the given message
     */
    public void logAndThrowIllegalArgument(String message) {
        IllegalArgumentException exception = new IllegalArgumentException(message);
        log.error("██ Exception : {}", message, exception);
        throw exception;
    }

    /**
     * Recursively searches for a SQLInvalidAuthorizationSpecException in the exception chain. This
     * method traverses the exception hierarchy starting from the given Throwable, looking for an
     * instance of SQLInvalidAuthorizationSpecException.
     *
     * @param e The Throwable to start the search from. This can be any exception or error.
     * @return The first SQLInvalidAuthorizationSpecException found in the exception chain, or null
     *     if no such exception is found.
     */
    public SQLInvalidAuthorizationSpecException getSQLInvalidAuthorizationSpecException(
            Throwable e) {
        while (e != null) {
            if (e
                    instanceof
                    SQLInvalidAuthorizationSpecException sqlinvalidauthorizationspecexception) {
                return sqlinvalidauthorizationspecexception;
            }
            e = e.getCause();
        }
        return null;
    }
}
