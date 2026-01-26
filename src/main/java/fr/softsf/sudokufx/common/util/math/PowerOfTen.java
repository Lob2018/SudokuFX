/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util.math;

/**
 * Represents predefined powers of ten used as numeric thresholds.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * double epsilon = PowerOfTen.DOUBLE_EPSILON.getValue(); // 1e-10
 * }</pre>
 */
public enum PowerOfTen {
    DOUBLE_EPSILON(1e-10),
    CENTI_EPSILON(1e-2);

    private final double value;

    PowerOfTen(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
