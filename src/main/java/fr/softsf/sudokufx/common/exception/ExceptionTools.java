/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.exception;

import java.sql.SQLInvalidAuthorizationSpecException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for handling and analyzing exceptions. This enum provides methods to search for
 * specific exception types within exception chains.
 */
public enum ExceptionTools {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionTools.class);

    /**
     * Logs the given message and returns an instance of {@link IllegalArgumentException} with it.
     *
     * @param message the error message
     * @return the IllegalArgumentException instance (not thrown)
     */
    public IllegalArgumentException logAndInstantiateIllegalArgument(String message) {
        String safeMessage = StringUtils.isBlank(message) ? "No message provided" : message;
        IllegalArgumentException exception = new IllegalArgumentException(safeMessage);
        LOG.error("██ Exception : {}", safeMessage, exception);
        return exception;
    }

    /**
     * Logs the given message and throws an {@link IllegalArgumentException} if the given string is
     * {@code null}, empty, or blank (only whitespace).
     *
     * @param value the string to check
     * @param message the error message
     * @throws IllegalArgumentException if the string is blank or null
     */
    public void logAndThrowIllegalArgumentIfBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            IllegalArgumentException exception = new IllegalArgumentException(message);
            LOG.error("██ Exception : {}", message, exception);
            throw exception;
        }
    }

    /**
     * Searches recursively through the exception chain to find the first {@link
     * SQLInvalidAuthorizationSpecException}, starting from the given Throwable.
     *
     * @param e the Throwable to inspect (may be null)
     * @return the first SQLInvalidAuthorizationSpecException found, or {@code null} if not found
     */
    public SQLInvalidAuthorizationSpecException findSQLInvalidAuthException(Throwable e) {
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
