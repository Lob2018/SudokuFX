/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.configuration.database;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import jakarta.validation.constraints.NotBlank;

/**
 * Implementation of the ApplicationKeystore.IEncryptionService interface using AES-GCM
 * (Galois/Counter Mode) encryption.
 */
final class SecretKeyEncryptionServiceAESGCM implements IEncryptionService {

    private static final Logger LOG =
            LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class);

    private static final SecureRandom RANDOM = new SecureRandom();
    private final SecretKey secretKey;
    private Cipher cipher;

    /**
     * Constructor for the SecretKeyEncryptionServiceAESGCM. Initializes the cipher with
     * AES/GCM/NoPadding algorithm.
     *
     * @param secretKeyP The SecretKey to be used for encryption and decryption
     */
    @ExcludedFromCoverageReportGenerated
    public SecretKeyEncryptionServiceAESGCM(final SecretKey secretKeyP) {
        secretKey = secretKeyP;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOG.error(
                    "██ Exception catch inside SecretKeyEncryptionServiceAESGCM(SecretKey)"
                            + " constructor : {}",
                    e.getMessage(),
                    e);
        }
    }

    @Override
    public String encrypt(@NotBlank final String original) {
        byte[] iv = new byte[16];
        try {
            RANDOM.nextBytes(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            byte[] encryptedData = cipher.doFinal(original.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            String encrypt64 = encoder.encodeToString(encryptedData);
            String iv64 = encoder.encodeToString(iv);
            return encrypt64 + "#" + iv64;
        } catch (Exception e) {
            LOG.error("██ Exception catch inside encrypt(original) : {}", e.getMessage(), e);
            return "";
        }
    }

    @Override
    public String decrypt(@NotBlank final String cypher) {
        try {
            String[] split = cypher.split("#");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cypherText = decoder.decode(split[0]);
            byte[] iv = decoder.decode(split[1]);
            GCMParameterSpec paraSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paraSpec);
            byte[] decryptedData = cipher.doFinal(cypherText);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOG.error("██ Exception catch inside decrypt(cypher) : {}", e.getMessage(), e);
            return "";
        }
    }
}
