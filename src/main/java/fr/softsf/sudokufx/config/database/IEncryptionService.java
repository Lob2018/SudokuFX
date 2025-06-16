/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

/**
 * Interface defining methods to encrypt and decrypt using AES-GCM (Galois/Counter Mode) encryption.
 */
sealed interface IEncryptionService permits SecretKeyEncryptionServiceAESGCM {
    /**
     * Encrypts the given string using AES-GCM encryption.
     *
     * @param original The string to be encrypted
     * @return A Base64 encoded string containing the encrypted data and Initialization Vector,
     *     separated by '#'
     */
    String encrypt(String original);

    /**
     * Decrypts the given cipher text using AES-GCM decryption.
     *
     * @param cypher The Base64 encoded string containing the encrypted data and Initialization
     *     Vector, separated by '#'
     * @return The decrypted string, or an empty string if decryption fails
     */
    String decrypt(String cypher);
}
