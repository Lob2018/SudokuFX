/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.exception;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Objects;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for handling and analyzing exceptions. This enum provides methods to search for
 * specific exception types within exception chains and centralizes error logging.
 */
public enum ExceptionTools {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionTools.class);
    public static final String EXCEPTION = "Exception";

    /**
     * Internal generic helper to centralize logging logic and exception instantiation.
     *
     * @param <T> the type of the exception to instantiate
     * @param message custom context message; if null or blank, a default message is used
     * @param cause original exception cause; may be null
     * @param factory functional interface to create the exception (e.g., Exception::new)
     * @param defaultMsg default message if the provided one is blank
     * @param logPrefix specific prefix for the log message
     * @return the instantiated exception
     */
    private <T extends Throwable> T logAndInstantiate(
            String message,
            Throwable cause,
            BiFunction<String, Throwable, T> factory,
            String defaultMsg,
            String logPrefix) {
        String safeMessage = StringUtils.isBlank(message) ? defaultMsg : message;
        if (Objects.isNull(cause)) {
            LOG.error("██ {}: {}. Cause: null", logPrefix, safeMessage);
        } else {
            LOG.error("██ {}: {}. Cause: {}", logPrefix, safeMessage, cause.getMessage(), cause);
        }
        return factory.apply(safeMessage, cause);
    }

    /**
     * Logs the given message and returns an instance of {@link IllegalArgumentException} with it.
     *
     * @param message the error message
     * @return the IllegalArgumentException instance (not thrown)
     */
    public IllegalArgumentException logAndInstantiateIllegalArgument(String message) {
        return logAndInstantiate(
                message,
                null,
                (m, c) -> new IllegalArgumentException(m),
                "No message provided",
                EXCEPTION);
    }

    /**
     * Logs the message and cause, then returns an {@link IllegalArgumentException}.
     *
     * @param message the error message
     * @param cause the original exception that triggered the failure; may be null
     * @return the IllegalArgumentException instance (not thrown)
     */
    public IllegalArgumentException logAndInstantiateIllegalArgument(
            String message, Throwable cause) {
        return logAndInstantiate(
                message, cause, IllegalArgumentException::new, "No message provided", EXCEPTION);
    }

    /**
     * Logs an error and returns an {@link IllegalStateException}.
     *
     * @param message descriptive message
     * @return a new IllegalStateException instance
     */
    public IllegalStateException logAndInstantiateIllegalState(String message) {
        return logAndInstantiate(
                message,
                null,
                (m, c) -> new IllegalStateException(m),
                "Illegal state reached",
                EXCEPTION);
    }

    /**
     * Logs an error and returns an {@link UnsupportedOperationException}.
     *
     * @param message descriptive message
     * @param cause the original exception; may be null
     * @return a new UnsupportedOperationException instance
     */
    public UnsupportedOperationException logAndInstantiateUnsupportedOperation(
            String message, Throwable cause) {
        return logAndInstantiate(
                message,
                cause,
                UnsupportedOperationException::new,
                "Operation not supported",
                "Unsupported Operation");
    }

    /**
     * Logs an error and returns an {@link UnsupportedOperationException} without a cause.
     *
     * @param message descriptive message
     * @return a new UnsupportedOperationException instance
     */
    public UnsupportedOperationException logAndInstantiateUnsupportedOperation(String message) {
        return logAndInstantiateUnsupportedOperation(message, null);
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
            throw logAndInstantiateIllegalArgument(message);
        }
    }

    /**
     * Logs an error and creates a {@link ResourceLoadException} for any resource (audio, image,
     * etc.). Handles null or blank messages and a null cause safely.
     *
     * @param message descriptive message of the failure; may be null or blank
     * @param cause the original exception that triggered the failure; may be null
     * @return a new {@link ResourceLoadException} instance
     */
    public ResourceLoadException logAndInstantiateResourceLoad(String message, Throwable cause) {
        return logAndInstantiate(
                message,
                cause,
                ResourceLoadException::new,
                "Resource load failed with no message",
                "Exception Failed to load resource");
    }

    /**
     * Logs an error and returns a {@link LogbackConfigurationException}.
     *
     * @param message descriptive message of the failure; may be null or blank
     * @param cause the original exception that triggered the failure; may be null
     * @return a new {@link LogbackConfigurationException} instance
     */
    public LogbackConfigurationException logAndInstantiateLogbackConfig(
            String message, Throwable cause) {
        return logAndInstantiate(
                message,
                cause,
                LogbackConfigurationException::new,
                "Logback configuration failed with no message",
                "Logback configuration failed");
    }

    /**
     * Logs an error and creates a {@link FolderCreationException} with an optional cause.
     *
     * @param message descriptive message of the failure; if null or blank, a default message is
     *     used
     * @param cause the underlying exception that triggered the failure; may be null
     * @return a new {@link FolderCreationException} instance
     */
    public FolderCreationException logAndInstantiateFolderCreation(
            String message, Throwable cause) {
        return logAndInstantiate(
                message,
                cause,
                FolderCreationException::new,
                "Folder creation failed with no message",
                "Folder creation failed");
    }

    /**
     * Logs an error and returns an {@link UncheckedIOException}.
     *
     * @param message descriptive message
     * @param cause the original IOException
     * @return a new UncheckedIOException instance
     */
    public UncheckedIOException logAndInstantiateUncheckedIO(String message, IOException cause) {
        return logAndInstantiate(
                message,
                cause,
                (m, c) -> new UncheckedIOException(m, (IOException) c),
                "I/O error occurred",
                EXCEPTION);
    }

    /**
     * Logs an error and creates a {@link FolderCreationException} without a cause.
     *
     * @param message descriptive message of the failure; if null or blank, a default message is
     *     used
     * @return a new {@link FolderCreationException} instance
     */
    public FolderCreationException logAndInstantiateFolderCreation(String message) {
        return logAndInstantiateFolderCreation(message, null);
    }

    /**
     * Searches recursively through the exception chain to find the first {@link
     * SQLInvalidAuthorizationSpecException}, starting from the given Throwable.
     *
     * @param e the Throwable to inspect (may be null)
     * @return the first SQLInvalidAuthorizationSpecException found, or {@code null} if not found
     */
    public SQLInvalidAuthorizationSpecException findSQLInvalidAuthException(Throwable e) {
        Throwable current = e;
        while (current != null) {
            if (current instanceof SQLInvalidAuthorizationSpecException sqlEx) {
                return sqlEx;
            }
            current = current.getCause();
        }
        return null;
    }
}
