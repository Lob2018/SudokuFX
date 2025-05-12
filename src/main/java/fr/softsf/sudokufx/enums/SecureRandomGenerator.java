/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.enums;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for generating secure random numbers using Java's {@link SecureRandom} class. This
 * enum provides methods to generate random integers securely.
 */
public enum SecureRandomGenerator {
    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(SecureRandomGenerator.class);

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Generates a random integer between {@code 0} (inclusive) and the specified {@code bound}
     * (exclusive).
     *
     * @param bound The upper limit (exclusive) for generating the random number. Must be positive.
     * @return A random integer between {@code 0} (inclusive) and {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code bound} is not positive.
     */
    public int nextInt(int bound) {
        if (bound <= 0) {
            IllegalArgumentException e =
                    new IllegalArgumentException("The nextInt bound must be positive");
            log.error("██ Exception caught from nextInt(bound) : {}", e.getMessage(), e);
            throw e;
        }
        return secureRandom.nextInt(bound);
    }

    /**
     * Generates a random integer between {@code origin} (inclusive) and {@code bound} (exclusive).
     *
     * @param origin The lower limit (inclusive) for generating the random number.
     * @param bound The upper limit (exclusive) for generating the random number.
     * @return A random integer between {@code origin} (inclusive) and {@code bound} (exclusive).
     * @throws IllegalArgumentException if {@code origin} is greater than or equal to {@code bound}.
     */
    public int nextInt(int origin, int bound) {
        if (origin >= bound) {
            IllegalArgumentException e =
                    new IllegalArgumentException("The nextInt origin must be less than bound");
            log.error("██ Exception caught from nextInt(origin,bound) : {}", e.getMessage(), e);
            throw e;
        }
        return secureRandom.nextInt(bound - origin) + origin;
    }
}
