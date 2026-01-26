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
     * Encrypts the given non-null, non-blank string using AES-GCM encryption.
     *
     * @param original the plaintext string to encrypt; must not be null or blank
     * @return a Base64 encoded string containing the encrypted data and initialization vector,
     *     separated by '#'
     * @throws IllegalArgumentException if {@code original} is null or blank
     */
    String encrypt(String original);

    /**
     * Decrypts the given non-null, non-blank Base64 encoded cipher text using AES-GCM decryption.
     *
     * @param cypher a Base64 encoded string containing the encrypted data and initialization
     *     vector, separated by '#'; must not be null or blank
     * @return the decrypted plaintext string, or an empty string if decryption fails
     * @throws IllegalArgumentException if {@code cypher} is null or blank
     */
    String decrypt(String cypher);
}
