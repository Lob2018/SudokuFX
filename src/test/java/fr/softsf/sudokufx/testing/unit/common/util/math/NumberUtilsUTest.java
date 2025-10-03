/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util.math;

import java.util.OptionalDouble;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.util.math.NumberUtils;
import fr.softsf.sudokufx.common.util.math.PowerOfTen;

import static org.junit.jupiter.api.Assertions.*;

class NumberUtilsUTest {

    @Test
    void givenValidNumeratorAndDenominator_whenSafeDivide_thenReturnsCorrectResult() {
        OptionalDouble result = NumberUtils.INSTANCE.safeDivide(10.0, 2.0);
        assertTrue(result.isPresent());
        assertEquals(5.0, result.getAsDouble(), 1e-12);
    }

    @Test
    void givenZeroDenominator_whenSafeDivide_thenReturnsEmpty() {
        OptionalDouble result = NumberUtils.INSTANCE.safeDivide(10.0, 0.0);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenVerySmallDenominator_whenSafeDivide_thenReturnsEmpty() {
        double tiny = PowerOfTen.DOUBLE_EPSILON.getValue() / 2;
        OptionalDouble result = NumberUtils.INSTANCE.safeDivide(1.0, tiny);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenDivisionProducingNaN_whenSafeDivide_thenReturnsEmpty() {
        OptionalDouble result = NumberUtils.INSTANCE.safeDivide(0.0, 0.0);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenDivisionProducingInfinity_whenSafeDivide_thenReturnsEmpty() {
        OptionalDouble result = NumberUtils.INSTANCE.safeDivide(Double.MAX_VALUE, 1e-300);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenTwoDoublesWithinDefaultEpsilon_whenAreDoublesEqual_thenReturnsTrue() {
        assertTrue(NumberUtils.INSTANCE.areDoublesEqual(0.1 + 0.2, 0.3));
        assertFalse(NumberUtils.INSTANCE.areDoublesEqual(0.1, 0.2));
    }

    @Test
    void givenTwoDoublesWithinCentiEpsilon_whenAreDoublesEqualCenti_thenReturnsTrue() {
        double a = 0.1;
        double b = 0.1 + PowerOfTen.CENTI_EPSILON.getValue() / 2;
        assertTrue(NumberUtils.INSTANCE.areDoublesEqualCenti(a, b));
        double c = 0.1 + PowerOfTen.CENTI_EPSILON.getValue() * 2;
        assertFalse(NumberUtils.INSTANCE.areDoublesEqualCenti(a, c));
    }
}
