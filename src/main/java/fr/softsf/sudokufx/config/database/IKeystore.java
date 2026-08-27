/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

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

    /**
     * Migrates the keystore by generating a new file encrypted with the specified new password.
     *
     * @param newPassword the new password used to encrypt the keystore; must not be null or empty
     * @param isMigrationFile true to prompt for a custom location to save the `.migration` file,
     *     false to overwrite the keystore
     */
    void migrateKeystore(final String newPassword, final boolean isMigrationFile);

    /**
     * Replaces the system username fallback in the OS secure store with a strong generated password
     * and overwrites the underlying keystore file, if applicable.
     */
    void enforceKeystorePasswordIfNeeded();
}
