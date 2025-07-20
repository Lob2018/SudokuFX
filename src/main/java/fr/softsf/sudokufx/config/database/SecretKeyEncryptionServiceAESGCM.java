/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.common.annotation.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Implementation of the ApplicationKeystore.IEncryptionService interface using AES-GCM
 * (Galois/Counter Mode) encryption.
 */
final class SecretKeyEncryptionServiceAESGCM implements IEncryptionService {

    private static final Logger LOG =
            LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class);

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int GCM_IV_LENGTH = 16;
    private static final int GCM_TAG_LENGTH = 128;
    private final SecretKey secretKey;
    private Cipher cipher;

    /**
     * Constructs a SecretKeyEncryptionServiceAESGCM instance, initializing the cipher for AES
     * encryption/decryption using GCM mode with no padding.
     *
     * @param secretKeyP the {@link SecretKey} used for encryption and decryption; must not be null
     * @throws IllegalArgumentException if {@code secretKeyP} is {@code null}
     * @throws IllegalStateException if the cipher algorithm "AES/GCM/NoPadding" is not available,
     *     leaving the instance unusable
     */
    @ExcludedFromCoverageReportGenerated
    public SecretKeyEncryptionServiceAESGCM(final SecretKey secretKeyP) {
        if (Objects.isNull(secretKeyP)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The secretKeyP must not be null");
        }
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
    public String encrypt(final String original) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                original, "original must not be null or blank, but was " + original);
        byte[] iv = new byte[GCM_IV_LENGTH];
        try {
            RANDOM.nextBytes(iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
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
    public String decrypt(final String cypher) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                cypher, "cypher must not be null or blank, but was " + cypher);
        try {
            String[] split = cypher.split("#");
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cypherText = decoder.decode(split[0]);
            byte[] iv = decoder.decode(split[1]);
            GCMParameterSpec paraSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paraSpec);
            byte[] decryptedData = cipher.doFinal(cypherText);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOG.error("██ Exception catch inside decrypt(cypher) : {}", e.getMessage(), e);
            return "";
        }
    }
}
