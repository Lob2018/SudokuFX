/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.annotation.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Implementation of the ApplicationKeystore.IEncryptionService interface using AES-GCM
 * (Galois/Counter Mode) encryption with secure char[] handling.
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
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional for AES-GCM encryption; JCE providers may throw"
                            + " unexpected RuntimeExceptions.")
    public char[] encrypt(final char[] original) {
        if (original == null
                || original.length == 0
                || CharBuffer.wrap(original).chars().allMatch(Character::isWhitespace)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "original must not be null, empty or blank");
        }
        byte[] iv = new byte[GCM_IV_LENGTH];
        try {
            RANDOM.nextBytes(iv);
            ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(original));
            byte[] originalBytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(originalBytes);
            Arrays.fill(byteBuffer.array(), (byte) 0);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            byte[] encryptedData = cipher.doFinal(originalBytes);
            Arrays.fill(originalBytes, (byte) 0);
            byte[] encodedEncrypt = Base64.getEncoder().encode(encryptedData);
            byte[] encodedIv = Base64.getEncoder().encode(iv);
            Arrays.fill(encryptedData, (byte) 0);
            Arrays.fill(iv, (byte) 0);
            char[] result = new char[encodedEncrypt.length + 1 + encodedIv.length];
            int index = 0;
            for (byte b : encodedEncrypt) {
                result[index++] = (char) b;
            }
            result[index++] = '#';
            for (byte b : encodedIv) {
                result[index++] = (char) b;
            }
            Arrays.fill(encodedEncrypt, (byte) 0);
            Arrays.fill(encodedIv, (byte) 0);
            return result;
        } catch (Exception e) {
            LOG.error("██ Exception catch inside encrypt(original) : {}", e.getMessage(), e);
            Arrays.fill(iv, (byte) 0);
            return new char[0];
        }
    }

    @Override
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional for AES-GCM decryption; JCE providers may throw"
                            + " unexpected RuntimeExceptions.")
    public char[] decrypt(final char[] cypher) {
        if (cypher == null
                || cypher.length == 0
                || CharBuffer.wrap(cypher).chars().allMatch(Character::isWhitespace)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "cypher must not be null, empty or blank");
        }
        try {
            int separatorIndex = -1;
            for (int i = 0; i < cypher.length; i++) {
                if (cypher[i] == '#') {
                    separatorIndex = i;
                    break;
                }
            }
            if (separatorIndex == -1) {
                throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                        "Invalid cypher format: missing separator '#'");
            }
            CharBuffer cypherCb = CharBuffer.wrap(cypher, 0, separatorIndex);
            ByteBuffer cypherBb = StandardCharsets.US_ASCII.encode(cypherCb);
            CharBuffer ivCb =
                    CharBuffer.wrap(cypher, separatorIndex + 1, cypher.length - separatorIndex - 1);
            ByteBuffer ivBb = StandardCharsets.US_ASCII.encode(ivCb);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cypherText = decoder.decode(cypherBb).array();
            byte[] iv = decoder.decode(ivBb).array();
            GCMParameterSpec paraSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paraSpec);
            byte[] decryptedData = cipher.doFinal(cypherText);
            Arrays.fill(cypherText, (byte) 0);
            Arrays.fill(iv, (byte) 0);
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(decryptedData));
            Arrays.fill(decryptedData, (byte) 0);
            char[] decryptedChars = new char[charBuffer.remaining()];
            charBuffer.get(decryptedChars);
            return decryptedChars;
        } catch (Exception e) {
            LOG.error("██ Exception catch inside decrypt(cypher) : {}", e.getMessage(), e);
            return new char[0];
        }
    }
}
