package fr.softsf.sudokufx.exceptions;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLInvalidAuthorizationSpecException;

/**
 * Utility enum for handling and analyzing exceptions. This enum provides
 * methods to search for specific exception types within exception chains.
 */
@Slf4j
public enum ExceptionTools {

    INSTANCE;

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
     * Recursively searches for a SQLInvalidAuthorizationSpecException in the
     * exception chain. This method traverses the exception hierarchy starting
     * from the given Throwable, looking for an instance of
     * SQLInvalidAuthorizationSpecException.
     *
     * @param e The Throwable to start the search from. This can be any
     *          exception or error.
     * @return The first SQLInvalidAuthorizationSpecException found in the
     * exception chain, or null if no such exception is found.
     */
    public SQLInvalidAuthorizationSpecException getSQLInvalidAuthorizationSpecException(Throwable e) {
        while (e != null) {
            if (e instanceof SQLInvalidAuthorizationSpecException sqlinvalidauthorizationspecexception) {
                return sqlinvalidauthorizationspecexception;
            }
            e = e.getCause();
        }
        return null;
    }
}

