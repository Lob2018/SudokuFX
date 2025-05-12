/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.configuration.database;

/** Interface defining methods to get the database username and his password. */
sealed interface IKeystore permits ApplicationKeystore {

    /** Set up the application keystore */
    void setupApplicationKeystore();

    /**
     * Get database username
     *
     * @return The database username
     */
    String getUsername();

    /**
     * Get database password
     *
     * @return The database password
     */
    String getPassword();
}
