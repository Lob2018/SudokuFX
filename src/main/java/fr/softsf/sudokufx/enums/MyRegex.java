package fr.softsf.sudokufx.enums;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for managing and validating text against precompiled regular expressions.
 * This class includes methods for password validation, semantic versioning, alphanumeric string validation,
 * and flexible contextual validations with error handling.
 *
 * <p>The class is implemented as a Singleton using an enum to ensure a single shared instance.
 * It uses the SLF4J logging framework for error reporting.</p>
 */
public enum MyRegex {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(MyRegex.class);

    /**
     * Allowed special characters for password validation.
     */
    private static final String SPECIAL_CHARACTERS = "@#$%^&()!";

    /**
     * Precompiled regex pattern for validating passwords.
     * Requirements:
     * - Only letters (lowercase/uppercase), digits, and special characters @#$%^&()! are allowed.
     * - Exactly 24 characters in total.
     */
    private static final Pattern secretPattern = Pattern.compile(
            "^[a-zA-Z0-9" + SPECIAL_CHARACTERS + "]{24}$"
    );

    public Pattern getSecretPattern() {
        return secretPattern;
    }

    /**
     * Precompiled regex pattern for semantic versioning (e.g., X.Y.Z format).
     * Requirements:
     * - Major, minor, and patch versions must be non-negative integers.
     * - Each version component must be separated by a '.' character.
     */
    private static final Pattern versionPattern = Pattern.compile(
            "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$"
    );

    public Pattern getVersionPattern() {
        return versionPattern;
    }

    /**
     * Precompiled regex pattern for validating alphanumeric strings.
     * Requirements:
     * - Only letters, digits, spaces, and dots are allowed.
     */
    private static final Pattern alphanumericPattern = Pattern.compile("^[a-zA-Z0-9\\s.]+$");

    public Pattern getAlphanumericPattern() {
        return alphanumericPattern;
    }

    /**
     * Validates the given text against the specified regular expression pattern.
     * For stricter cases like password validation, the method delegates validation to {@code isValidPassword}.
     * If the {@code pattern} matches the {@code secretPattern}, this method performs
     * stricter password validation with additional rules. Otherwise, it uses the provided
     * {@code pattern} for generic regex validation.
     *
     * @param text    The text to validate (must not be null).
     * @param pattern The regular expression pattern for validation (must not be null).
     * @return {@code true} if the text matches the regex pattern; {@code false} otherwise.
     * @throws NullPointerException if the {@code text} to validate is null.
     */
    public boolean isValidatedByRegex(@NotNull final String text, @NotNull final Pattern pattern) {
        try {
            if (text == null) throw new NullPointerException("The text to validate must not be null.");
            if (pattern == secretPattern) {
                return isValidPassword(text);
            } else {
                Matcher matcher = pattern.matcher(text);
                return matcher.matches();
            }
        } catch (Exception ex) {
            log.error("██ Exception caught inside isValidatedByRegex, text is {}, pattern is {}, regex is {} : {}",
                    text, pattern, (pattern == null) ? null : pattern.pattern(), ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * Validates a secure password based on strict requirements:
     * - At least 2 lowercase characters.
     * - At least 2 uppercase characters.
     * - At least 2 numeric digits.
     * - At least 2 special characters from @#$%^&()!.
     * - Exactly 24 characters in total.
     *
     * @param password The password to validate (must not be null).
     * @return {@code true} if the password meets all security criteria; {@code false} otherwise.
     */
    private boolean isValidPassword(@NotNull final String password) {
        if (!secretPattern.matcher(password).matches()) return false;
        long lowerCaseCount = password.chars().filter(Character::isLowerCase).count();
        long upperCaseCount = password.chars().filter(Character::isUpperCase).count();
        long digitCount = password.chars().filter(Character::isDigit).count();
        long specialCharCount = password.chars()
                .filter(c -> SPECIAL_CHARACTERS.indexOf(c) >= 0)
                .count();
        return lowerCaseCount >= 2 && upperCaseCount >= 2 && digitCount >= 2 && specialCharCount >= 2;
    }
}
