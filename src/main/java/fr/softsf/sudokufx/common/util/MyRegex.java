/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.nio.CharBuffer;
import java.util.Objects;
import java.util.regex.Pattern;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Utility class for managing and validating text against precompiled regular expressions. This
 * class includes methods for password validation, semantic versioning, alphanumeric string
 * validation, and flexible contextual validations with error handling.
 *
 * <p>The class is implemented as a Singleton using an enum to ensure a single shared instance. It
 * uses the SLF4J logging framework for error reporting.
 */
public enum MyRegex {
    INSTANCE;

    private static final String SPECIAL_CHARACTERS = "@#$%^&()!";

    private static final Pattern ZERO_COMMA_GRID_PATTERN = Pattern.compile("^[0,]*$");

    private static final Pattern SECRET_PATTERN =
            Pattern.compile(
                    "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*["
                            + Pattern.quote(SPECIAL_CHARACTERS)
                            + "])"
                            + "[a-zA-Z0-9"
                            + Pattern.quote(SPECIAL_CHARACTERS)
                            + "]{24,32}$");

    private static final Pattern PLAYER_NAME_PATTERN =
            Pattern.compile("^[A-Za-z]+(?:\\s[A-Za-z]+)*+\\s*+$|^\\s*+$");

    private static final Pattern VERSION_PATTERN =
            Pattern.compile(
                    "^(0|[1-9]\\d{0,8})\\.(0|[1-9]\\d{0,8})\\.(0|[1-9]\\d{0,8})(?:\\.(0|[1-9]\\d{0,8}))?$");

    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s.]+$");

    /** Returns the allowed special characters for password validation. */
    public String getSpecialChars() {
        return SPECIAL_CHARACTERS;
    }

    /**
     * Validates the given password character array against strict security rules. Utilizes a
     * character array to ensure **memory security** for sensitive data by avoiding String pool
     * persistence.
     *
     * @param secret the password character array to validate; must not be {@code null} or empty
     * @return {@code true} if the secret meets all criteria; {@code false} otherwise
     * @throws IllegalArgumentException if {@code secret} is {@code null}, empty, or blank
     */
    public boolean isValidSecret(final char[] secret) {
        if (secret == null || secret.length == 0) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The secret must not be null or empty");
        }
        final CharBuffer buffer = CharBuffer.wrap(secret);
        if (buffer.chars().allMatch(Character::isWhitespace)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The secret must not be blank");
        }
        return isValidPassword(buffer);
    }

    public boolean isValidPlayerName(final String playerName) {
        return isValidatedByRegex(playerName, PLAYER_NAME_PATTERN, false);
    }

    public boolean isValidZeroCommaGrid(final String gridText) {
        return isValidatedByRegex(gridText, ZERO_COMMA_GRID_PATTERN, true);
    }

    public boolean isValidVersion(final String version) {
        return isValidatedByRegex(version, VERSION_PATTERN, true);
    }

    public boolean isValidAlphanumeric(final String text) {
        return isValidatedByRegex(text, ALPHANUMERIC_PATTERN, true);
    }

    /**
     * Validates the given string against the specified regex pattern.
     *
     * @param text the string to validate
     * @param pattern the regex pattern to validate against; must not be {@code null}
     * @param textMustNotBeNullOrBlank if {@code true}, throws an exception when {@code text} is
     *     null, empty, or blank
     * @return {@code true} if the text matches the pattern; {@code false} otherwise
     * @throws IllegalArgumentException if {@code textMustNotBeNullOrBlank} is {@code true} and
     *     {@code text} is {@code null}, empty, or blank, or if {@code pattern} is {@code null}
     */
    private boolean isValidatedByRegex(
            final String text, final Pattern pattern, boolean textMustNotBeNullOrBlank) {
        if (textMustNotBeNullOrBlank) {
            ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                    text, "The text to validate must not be null or blank, but was " + text);
        }
        if (Objects.isNull(pattern)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The pattern must not be null");
        }
        return pattern.matcher(text).matches();
    }

    /**
     * Validates a password character buffer against strict security rules:
     *
     * <ul>
     *   <li>Must be between 24 and 32 characters long.
     *   <li>Contains only letters (uppercase and lowercase), digits, and special characters
     *       {@code @#$%^&()!}.
     *   <li>Includes at least 2 lowercase letters.
     *   <li>Includes at least 2 uppercase letters.
     *   <li>Includes at least 2 digits.
     *   <li>Includes at least 2 special characters from {@code @#$%^&()!}.
     * </ul>
     *
     * @param buffer the password character buffer to validate
     * @return {@code true} if the password meets all criteria, {@code false} otherwise
     */
    private boolean isValidPassword(final CharBuffer buffer) {
        if (SECRET_PATTERN.matcher(buffer).matches()) {
            long lowerCaseCount = buffer.chars().filter(Character::isLowerCase).count();
            long upperCaseCount = buffer.chars().filter(Character::isUpperCase).count();
            long digitCount = buffer.chars().filter(Character::isDigit).count();
            long specialCharCount =
                    buffer.chars().filter(c -> SPECIAL_CHARACTERS.indexOf(c) >= 0).count();
            return lowerCaseCount >= 2
                    && upperCaseCount >= 2
                    && digitCount >= 2
                    && specialCharCount >= 2;
        }
        return false;
    }
}
