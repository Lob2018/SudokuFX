/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Singleton utility for safe and approximate numeric operations. */
public enum NumberUtils {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(NumberUtils.class);
    private static final double DEFAULT_DOUBLE_EPSILON = PowerOfTen.DOUBLE_EPSILON.getValue();

    /**
     * Safely divides two doubles, treating near-zero denominators as invalid.
     *
     * <p>Returns {@link Double#NaN} if the denominator is zero, too small, or if the division
     * produces NaN or Infinity. Errors are logged.
     *
     * @param numerator the numerator
     * @param denominator the denominator
     * @return the division result, or {@link Double#NaN} if invalid
     */
    public double safeDivide(double numerator, double denominator) {
        double epsilon = DEFAULT_DOUBLE_EPSILON;
        if (Math.abs(denominator) < epsilon) {
            LOG.error(
                    "SafeDivide denominator too small: numerator={} / denominator={} (epsilon={})",
                    numerator,
                    denominator,
                    epsilon);
            return Double.NaN;
        }
        double result = numerator / denominator;
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            LOG.error(
                    "SafeDivide invalid division result: numerator={} / denominator={}",
                    numerator,
                    denominator);
            return Double.NaN;
        }
        return result;
    }

    /**
     * Compares two doubles using one ULP tolerance (very strict). Equivalent to Apache's equals(x,
     * y).
     */
    public boolean areDoublesEqual(double x, double y) {
        return equalsUlps(x, y, 1);
    }

    /**
     * Compares two doubles with a given decimal tolerance (epsilon). Returns true if |x - y| <= eps
     * or if they differ by <= 1 ULP.
     */
    public boolean areDoublesEqualEpsilon(double x, double y, double eps) {
        return equalsUlps(x, y, 1) || Math.abs(x - y) <= eps;
    }

    /**
     * Common ULP-based comparison (used internally). Returns true if both doubles differ by at most
     * maxUlps units.
     */
    private boolean equalsUlps(double x, double y, int maxUlps) {
        if (Double.isNaN(x) || Double.isNaN(y)) {
            return false;
        }
        long xBits = Double.doubleToRawLongBits(x);
        long yBits = Double.doubleToRawLongBits(y);
        if ((xBits ^ yBits) < 0) {
            long xMag = xBits & Long.MAX_VALUE;
            long yMag = yBits & Long.MAX_VALUE;
            return xMag <= maxUlps && yMag <= (maxUlps - xMag);
        }
        return Math.abs(xBits - yBits) <= maxUlps;
    }
}
