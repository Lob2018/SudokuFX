/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

/**
 * Interface defining methods to encrypt and decrypt strings using AES-GCM (Galois/Counter Mode).
 */
sealed interface IEncryptionService permits SecretKeyEncryptionServiceAESGCM {

    /**
     * Encrypts the given non-null, non-empty character array using AES-GCM encryption.
     *
     * @param original the plaintext character array to encrypt; must not be null or empty
     * @return a character array containing the Base64 encoded encrypted data and initialization
     *     vector, separated by '#'
     * @throws IllegalArgumentException if {@code original} is null or empty
     */
    char[] encrypt(char[] original);

    /**
     * Decrypts the given non-null, non-empty Base64 encoded cipher character array using AES-GCM
     * decryption.
     *
     * @param cypher a character array containing the Base64 encoded encrypted data and
     *     initialization vector, separated by '#'; must not be null or empty
     * @return the decrypted plaintext character array, or an empty array if decryption fails
     * @throws IllegalArgumentException if {@code cypher} is null or empty
     */
    char[] decrypt(char[] cypher);
}
