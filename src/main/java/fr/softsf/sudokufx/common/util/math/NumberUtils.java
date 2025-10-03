/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.math;

import java.util.OptionalDouble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility singleton for safe and approximate numeric operations.
 *
 * <p>Implemented as an {@code enum} singleton to ensure thread-safety and single-instance semantics
 * without relying on dependency injection frameworks. This guarantees availability even in
 * non-managed environments.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * OptionalDouble ratio = NumberUtils.INSTANCE.safeDivide(10.0, 2.0);
 * boolean equal = NumberUtils.INSTANCE.areDoublesEqual(0.1 + 0.2, 0.3);
 * }</pre>
 */
public enum NumberUtils {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(NumberUtils.class);
    private static final double DEFAULT_DOUBLE_EPSILON = PowerOfTen.DOUBLE_EPSILON.getValue();
    private static final double DEFAULT_CENTI_EPSILON = PowerOfTen.CENTI_EPSILON.getValue();

    /**
     * Safely divides two doubles, treating near-zero denominators as invalid.
     *
     * <p>Returns an empty {@link OptionalDouble} if the denominator is zero or its absolute value
     * is strictly less than the internal epsilon threshold, or if the result is NaN or Infinity.
     * Errors are logged.
     *
     * @param numerator the numerator
     * @param denominator the denominator
     * @return an {@link OptionalDouble} containing the result, or empty if invalid
     */
    public OptionalDouble safeDivide(double numerator, double denominator) {
        double epsilon = DEFAULT_DOUBLE_EPSILON;
        if (Math.abs(denominator) < epsilon) {
            LOG.error(
                    "SafeDivide denominator too small: numerator={} / denominator={} (epsilon={})",
                    numerator,
                    denominator,
                    epsilon);
            return OptionalDouble.empty();
        }
        double result = numerator / denominator;
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            LOG.error(
                    "SafeDivide invalid division result: numerator={} / denominator={}",
                    numerator,
                    denominator);
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(result);
    }

    /**
     * Compares two doubles for approximate equality using the default epsilon threshold.
     *
     * <p>Returns {@code true} if the absolute difference between {@code a} and {@code b} is less
     * than or equal to {@code DEFAULT_DOUBLE_EPSILON}, currently sourced from {@link
     * PowerOfTen#DOUBLE_EPSILON}.
     *
     * @param a the first value
     * @param b the second value
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public boolean areDoublesEqual(double a, double b) {
        return Math.abs(a - b) <= DEFAULT_DOUBLE_EPSILON;
    }

    /**
     * Compares two doubles for approximate equality using a relaxed epsilon threshold.
     *
     * <p>Returns {@code true} if the absolute difference between {@code a} and {@code b} is less
     * than or equal to {@code DEFAULT_CENTI_EPSILON}, currently sourced from {@link
     * PowerOfTen#CENTI_EPSILON}.
     *
     * @param a the first value
     * @param b the second value
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public boolean areDoublesEqualCenti(double a, double b) {
        return Math.abs(a - b) <= DEFAULT_CENTI_EPSILON;
    }
}
