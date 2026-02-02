/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx;

/** A launcher class to workaround classpath issues. */
public final class Launcher {

    /** Private constructor to prevent instantiation of utility class. */
    private Launcher() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * The application's entry point, delegates execution to the SudoMain class.
     *
     * @param args the command line arguments
     */
    static void main(String[] args) {
        SudoMain.main(args);
    }
}
