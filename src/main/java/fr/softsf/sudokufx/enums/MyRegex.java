/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.enums;

import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(MyRegex.class);

    /** Allowed special characters for password validation. */
    private static final String SPECIAL_CHARACTERS = "@#$%^&()!";

    /**
     * Precompiled regex pattern for validating passwords. Requirements: - Only letters
     * (lowercase/uppercase), digits, and special characters @#$%^&()! are allowed. - Exactly 24
     * characters in total.
     */
    private static final Pattern secretPattern =
            Pattern.compile("^[a-zA-Z0-9" + SPECIAL_CHARACTERS + "]{24}$");

    public Pattern getSecretPattern() {
        return secretPattern;
    }

    /**
     * Precompiled regex pattern for semantic versioning (e.g., X.Y.Z format). Requirements: -
     * Major, minor, and patch versions must be non-negative integers. - Each version component must
     * be separated by a '.' character.
     */
    private static final Pattern versionPattern =
            Pattern.compile("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$");

    public Pattern getVersionPattern() {
        return versionPattern;
    }

    /**
     * Precompiled regex pattern for validating alphanumeric strings. Requirements: - Only letters,
     * digits, spaces, and dots are allowed.
     */
    private static final Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9\\s.]+$");

    public Pattern getAlphanumericPattern() {
        return alphanumericPattern;
    }

    /**
     * Validates the given text against the specified regular expression pattern. If the provided
     * {@code pattern} is equal to the internal {@code secretPattern}, this method performs a
     * stricter password validation by delegating to {@link #isValidPassword(String)}. Otherwise, it
     * performs a generic regex match using the provided pattern.
     *
     * @param text The text to validate; must not be {@code null}.
     * @param pattern The regular expression pattern to validate against; must not be {@code null}.
     * @return {@code true} if the text matches the regex pattern or meets the password criteria;
     *     {@code false} otherwise.
     * @throws NullPointerException if {@code text} or {@code pattern} is {@code null}.
     */
    public boolean isValidatedByRegex(final String text, final Pattern pattern) {
        Objects.requireNonNull(
                text, "MyRegex>isValidatedByRegex : The text to validate must not be null.");
        Objects.requireNonNull(
                pattern, "MyRegex>isValidatedByRegex : The pattern must not be null.");
        if (pattern.equals(secretPattern)) {
            return isValidPassword(text);
        }
        return pattern.matcher(text).matches();
    }

    /**
     * Validates a secure password against strict security criteria:
     *
     * <ul>
     *   <li>Exactly 24 characters in length.
     *   <li>Contains only letters (uppercase and lowercase), digits, and allowed special characters
     *       {@code @#$%^&()!}.
     *   <li>At least 2 lowercase letters.
     *   <li>At least 2 uppercase letters.
     *   <li>At least 2 digits.
     *   <li>At least 2 special characters from {@code @#$%^&()!}.
     * </ul>
     *
     * @param password The password string to validate; must not be {@code null}.
     * @return {@code true} if the password meets all the above criteria; {@code false} otherwise.
     * @throws NullPointerException if {@code password} is {@code null}.
     */
    private boolean isValidPassword(final String password) {
        Objects.requireNonNull(password, "MyRegex>isValidPassword : Password must not be null.");
        if (!secretPattern.matcher(password).matches()) return false;
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
}
