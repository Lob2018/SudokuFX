/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/**
 * Utility enum for log messages and ASCII art.
 *
 * <p>Lines="0-34" are excluded from Checkstyle's LineLength check (see
 * checkstyle-suppressions.xml).
 */
public enum LogBackTxt {
    SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION(
            """
            ██ Risk of loss of application data because database authentication is not valid:
            ██ You can say no and try restarting the application and if the problem persists, reset the application database.
            ██ In both cases the application will close
            """),
    ASCII_LOGO(
            """

            ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ {} {} ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓

                     ▒▒▒▒▒                                                                                     \s
                    ▒▒▒▒▒▒▒                                                                                    \s
                    ▒▒▒▒▒▒▒▒                                                                                   \s
                    ▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒                                                                         \s
                    ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒                                                                        \s
               ▒▒▒▒▒▒▒▒▓▓▓▓▓▓▓▒▒▒▒▒▒▒▒▒    ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓  ▓▓▓▓▓▓    ▓▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓▓ \s
            ▒▒▒▒▒▒▒▒▒▒▓▓▓███▓▓▓▒▒▒▒▒▒▒     ▓▓▓     ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓      ▓▓▓▓▓     ▓▓▓    ▓▓▓ ▓▓▓ \s
            ▒▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒      ▓▓▓▓▓▓  ▓▓    ▓▓ ▓▓▓▓▓▓    ▓▓▓   ▓▓▓▓▓▓▓ ▓▓▓ ▓▓▓     ▓▓▓▓▓▓ ▓▓▓▓▓▓  \s
             ▒▒▒▒▒▒▒▒▓▓▓█████▓▓▓▒▒▒▒▒         ▓▓▓▓ ▓▓▓  ▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓ ▓▓▓ ▓▓▓▓▓▓▓▓    ▓▓▓    ▓▓▓ ▓▓▓ \s
               ▒▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▒▒▒▒▒▒▒     ▓▓▓▓▓▓▓ ▓▓▓▓▓▓▓▓ ▓▓▓       ▓▓▓   ▓▓▓▓▓▓▓     ▓▓▓  ▓▓ ▓▓▓    ▓▓▓  ▓▓▓\s
                   ▒▒▒▒▒▒▓▓▓▒▒▒▒▒▒▒▒▒▒                                                                         \s
                  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒                                                                         \s
                  ▒▒▒▒▒▒▒▒▒▒ ▒▒▒▒▒▒▒▒▒                                                                         \s
                  ▒▒▒▒▒▒▒▒▒    ▒▒▒▒▒▒                                                                          \s
                   ▒▒▒▒▒
            """),
    OPTIMIZING("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓ Optimizing startup ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
    private final String logBackMessage;

    LogBackTxt(final String logBackMessage) {
        this.logBackMessage = logBackMessage;
    }

    public final String getLogBackMessage() {
        return logBackMessage;
    }
}
