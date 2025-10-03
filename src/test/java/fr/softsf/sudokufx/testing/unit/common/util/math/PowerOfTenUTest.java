/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util.math;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.util.math.PowerOfTen;

import static org.junit.jupiter.api.Assertions.*;

class PowerOfTenUTest {

    @Test
    void givenDoubleEpsilon_whenGetValue_thenReturnsCorrectDouble() {
        assertEquals(1e-10, PowerOfTen.DOUBLE_EPSILON.getValue(), 1e-20);
    }

    @Test
    void givenCentiEpsilon_whenGetValue_thenReturnsCorrectDouble() {
        assertEquals(1e-2, PowerOfTen.CENTI_EPSILON.getValue(), 1e-12);
    }

    @Test
    void givenPowerOfTenEnum_whenValuesCalled_thenContainsOnlyExpectedConstants() {
        PowerOfTen[] values = PowerOfTen.values();
        assertEquals(2, values.length);
        assertArrayEquals(
                new PowerOfTen[] {PowerOfTen.DOUBLE_EPSILON, PowerOfTen.CENTI_EPSILON}, values);
    }
}
