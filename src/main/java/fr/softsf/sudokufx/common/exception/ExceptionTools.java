/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.exception;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Objects;

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

    /**
     * Logs an error and creates a {@link ResourceLoadException} for any resource (audio, image,
     * etc.). Handles null or blank messages and a null cause safely.
     *
     * @param message descriptive message of the failure; may be null or blank
     * @param cause the original exception that triggered the failure; may be null
     * @return a new {@link ResourceLoadException} instance
     */
    public ResourceLoadException logAndInstantiateResourceLoad(String message, Throwable cause) {
        String safeMessage =
                StringUtils.isBlank(message) ? "Resource load failed with no message" : message;
        if (Objects.isNull(cause)) {
            LOG.error("██ Exception Failed to load resource: {}. Cause: null", safeMessage);
            return new ResourceLoadException(safeMessage, null);
        } else {
            LOG.error(
                    "██ Exception Failed to load resource: {}. Cause: {}",
                    safeMessage,
                    cause.getMessage(),
                    cause);
            return new ResourceLoadException(safeMessage, cause);
        }
    }

    /**
     * Logs an error and returns a {@link LogbackConfigurationException}.
     *
     * <p>The exception is instantiated (not thrown) to allow flexible handling by the caller.
     *
     * @param message descriptive message of the failure; may be null or blank
     * @param cause the original exception that triggered the failure; may be null
     * @return a new {@link LogbackConfigurationException} instance
     */
    public LogbackConfigurationException logAndInstantiateLogbackConfig(
            String message, Throwable cause) {
        String safeMessage =
                StringUtils.isBlank(message)
                        ? "Logback configuration failed with no message"
                        : message;
        if (Objects.isNull(cause)) {
            LOG.error("██ Logback configuration failed: {}. Cause: null", safeMessage);
        } else {
            LOG.error(
                    "██ Logback configuration failed: {}. Cause: {}",
                    safeMessage,
                    cause.getMessage(),
                    cause);
        }
        return new LogbackConfigurationException(safeMessage, cause);
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
        String safeMessage =
                StringUtils.isBlank(message) ? "Folder creation failed with no message" : message;
        LOG.error(
                "██ Folder creation failed: {}. Cause: {}",
                safeMessage,
                cause == null ? "null" : cause.getMessage(),
                cause);
        return new FolderCreationException(safeMessage, cause);
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
}
