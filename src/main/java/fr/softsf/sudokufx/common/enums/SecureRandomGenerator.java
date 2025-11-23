/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility enum for generating secure random numbers using Java's {@link SecureRandom} class. This
 * enum provides methods to generate random integers securely.
 */
public enum SecureRandomGenerator {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(SecureRandomGenerator.class);

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
            LOG.error("██ Exception caught from nextInt(bound) : {}", e.getMessage(), e);
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
            LOG.error("██ Exception caught from nextInt(origin,bound) : {}", e.getMessage(), e);
            throw e;
        }
        return secureRandom.nextInt(bound - origin) + origin;
    }
}
