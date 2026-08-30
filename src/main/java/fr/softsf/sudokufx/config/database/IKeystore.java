/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

/**
 * Interface defining methods to manage the application keystore and retrieve database credentials.
 */
sealed interface IKeystore permits ApplicationKeystore {

    /** Sets up and initializes the application keystore and its credentials. */
    void setupApplicationKeystore();

    /**
     * Retrieves the database username.
     *
     * @return the database username as a character array
     */
    char[] getUsername();

    /**
     * Retrieves the database password.
     *
     * @return the database password as a character array
     */
    char[] getPassword();
}
