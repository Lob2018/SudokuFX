/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum for operating system names. */
public enum OsName {
    OS_NAME(System.getProperty("os.name").toLowerCase()),
    WRONG_OS_FOR_TESTS("wrongOs");

    private final String os;

    OsName(final String os_) {
        os = os_;
    }

    public final String getOs() {
        return os;
    }
}
