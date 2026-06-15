/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum for player-related constants. */
public enum PlayerConstants {
    /** The specific name used to identify the unknown player in the system. */
    ANONYMOUS_NAME("—");

    private final String value;

    /**
     * Constructs a new PlayerConstants with the specified value.
     *
     * @param value the constant string value
     */
    PlayerConstants(final String value) {
        this.value = value;
    }

    /**
     * Gets the constant value.
     *
     * @return the value
     */
    public final String getValue() {
        return value;
    }
}
