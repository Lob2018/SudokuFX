/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/** Utility enum for operating system names. */
public enum OsName {
    WINDOWS("windows"),
    LINUX("linux"),
    MAC("mac"),
    WRONG_OS_FOR_TESTS("wrongOs"),
    EMPTY_OS_FOR_TESTS("");

    private final String os;

    OsName(final String os_) {
        os = os_;
    }

    public final String getOs() {
        return os;
    }

    /**
     * Detects the current operating system by reading the system property "os.name".
     *
     * @return the corresponding OsName enum constant (WINDOWS, LINUX, or MAC)
     * @throws IllegalArgumentException if the OS name is null, blank, or unsupported
     */
    public static OsName detect() {
        String current = System.getProperty("os.name").toLowerCase();
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                current, "Operating system must not be null or blank, but was " + current);
        if (current.contains(WINDOWS.os)) return WINDOWS;
        if (current.contains(LINUX.os)) return LINUX;
        if (current.contains(MAC.os)) return MAC;
        throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                "Unsupported OS: " + current);
    }
}
