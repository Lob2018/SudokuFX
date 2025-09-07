/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOG = LoggerFactory.getLogger(MyRegex.class);

    /** Allowed special characters for password validation. */
    private static final String SPECIAL_CHARACTERS = "@#$%^&()!";

    /**
     * Precompiled regex pattern for validating passwords. Requirements: - Only letters
     * (lowercase/uppercase), digits, and special characters @#$%^&()! are allowed. - Exactly 24
     * characters in total.
     */
    private static final Pattern SECRET_PATTERN =
            Pattern.compile("^[a-zA-Z0-9" + SPECIAL_CHARACTERS + "]{24}$");

    public Pattern getSecretPattern() {
        return SECRET_PATTERN;
    }

    /**
     * Precompiled regex pattern for semantic versioning (e.g., X.Y.Z format). Requirements: -
     * Major, minor, and patch versions must be non-negative integers. - Each version component must
     * be separated by a '.' character.
     */
    private static final Pattern VERSION_PATTERN =
            Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$");

    public Pattern getVersionPattern() {
        return VERSION_PATTERN;
    }

    /**
     * Precompiled regex pattern for validating alphanumeric strings. Requirements: - Only letters,
     * digits, spaces, and dots are allowed.
     */
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9\\s.]+$");

    public Pattern getAlphanumericPattern() {
        return ALPHANUMERIC_PATTERN;
    }

    /**
     * Validates the given text against the specified regex pattern. If {@code pattern} equals the
     * internal secret pattern, a stricter password validation is performed via {@link
     * #isValidPassword(String)}.
     *
     * @param text the text to validate; must not be {@code null} or blank
     * @param pattern the regex pattern to validate against; must not be {@code null}
     * @return {@code true} if the text matches the pattern or meets password criteria; {@code
     *     false} otherwise
     * @throws IllegalArgumentException if {@code text} is blank or {@code pattern} is {@code null}
     */
    public boolean isValidatedByRegex(final String text, final Pattern pattern) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                text, "The text to validate must not be null or blank, but was " + text);
        if (Objects.isNull(pattern)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The pattern must not be null");
        }
        if (pattern.equals(SECRET_PATTERN)) {
            return isValidPassword(text);
        }
        return pattern.matcher(text).matches();
    }

    /**
     * Validates a password string against strict security rules:
     *
     * <ul>
     *   <li>Must be exactly 24 characters long.
     *   <li>Contains only letters (uppercase and lowercase), digits, and special characters
     *       {@code @#$%^&()!}.
     *   <li>Includes at least 2 lowercase letters.
     *   <li>Includes at least 2 uppercase letters.
     *   <li>Includes at least 2 digits.
     *   <li>Includes at least 2 special characters from {@code @#$%^&()!}.
     * </ul>
     *
     * @param password the password to validate; must not be {@code null} or blank
     * @return {@code true} if the password meets all criteria, {@code false} otherwise
     * @throws IllegalArgumentException if {@code password} is {@code null} or blank
     */
    private boolean isValidPassword(final String password) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                password, "Password must not be null or blank, but was " + password);
        if (SECRET_PATTERN.matcher(password).matches()) {
            long lowerCaseCount = password.chars().filter(Character::isLowerCase).count();
            long upperCaseCount = password.chars().filter(Character::isUpperCase).count();
            long digitCount = password.chars().filter(Character::isDigit).count();
            long specialCharCount =
                    password.chars().filter(c -> SPECIAL_CHARACTERS.indexOf(c) >= 0).count();
            return lowerCaseCount >= 2
                    && upperCaseCount >= 2
                    && digitCount >= 2
                    && specialCharCount >= 2;
        }
        return false;
    }
}
