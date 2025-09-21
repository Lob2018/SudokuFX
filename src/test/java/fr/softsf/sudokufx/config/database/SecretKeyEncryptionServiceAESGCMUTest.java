/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class SecretKeyEncryptionServiceAESGCMUTest {

    private static IEncryptionService iSecretKeyEncryptionServiceAESGCM;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeAll
    static void setupAll() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom());
        SecretKey symmetricKey = keyGen.generateKey();
        iSecretKeyEncryptionServiceAESGCM = spy(new SecretKeyEncryptionServiceAESGCM(symmetricKey));
    }

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class))
                .addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(SecretKeyEncryptionServiceAESGCM.class))
                .detachAndStopAllAppenders();
    }

    @Test
    void givenSecret_whenEncryptAndDecrypt_thenOriginalSecretReturned() {
        String secret = "Secret";
        String encrypted = iSecretKeyEncryptionServiceAESGCM.encrypt(secret);
        String decrypted = iSecretKeyEncryptionServiceAESGCM.decrypt(encrypted);
        assertEquals(secret, decrypted);
    }

    @Test
    void givenNullSecretKey_whenConstruct_thenIllegalArgumentException() {
        IllegalArgumentException thrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new SecretKeyEncryptionServiceAESGCM(null));
        assertTrue(thrown.getMessage().contains("must not be null"));
    }

    @Test
    void givenNullOrBlankOriginal_whenEncrypt_thenIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.encrypt(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.encrypt(""));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.encrypt("   "));
    }

    @Test
    void givenNullOrBlankCypher_whenDecrypt_thenIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt(""));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt("   "));
    }

    @Test
    void givenInvalidCipherText_whenDecrypt_thenEmptyStringReturnedAndErrorLogged() {
        String invalidCypher = "not#validbase64";
        String decrypted = iSecretKeyEncryptionServiceAESGCM.decrypt(invalidCypher);
        assertEquals("", decrypted);
        assertFalse(logWatcher.list.isEmpty());
        assertTrue(
                logWatcher
                        .list
                        .getLast()
                        .getFormattedMessage()
                        .contains("██ Exception catch inside decrypt(cypher)"));
    }

    @Test
    void givenValidEncryptionOutput_whenSplit_thenContainsEncryptedDataAndIv() {
        String secret = "TestSecret";
        String encrypted = iSecretKeyEncryptionServiceAESGCM.encrypt(secret);
        assertNotNull(encrypted);
        String[] parts = encrypted.split("#");
        assertEquals(2, parts.length);
        Base64.getDecoder().decode(parts[0]);
        Base64.getDecoder().decode(parts[1]);
    }

    @Test
    void givenInvalidSecretKey_whenEncrypt_thenEmptyStringReturnedAndErrorLogged() {
        byte[] invalidBytes = new byte[4];
        new SecureRandom().nextBytes(invalidBytes);
        SecretKey invalidKey = new SecretKeySpec(invalidBytes, "AES");
        IEncryptionService brokenEncryptionService =
                new SecretKeyEncryptionServiceAESGCM(invalidKey);
        String result = brokenEncryptionService.encrypt("failEncrypt");
        assertEquals("", result);
        assertFalse(logWatcher.list.isEmpty());
        assertTrue(
                logWatcher
                        .list
                        .getLast()
                        .getFormattedMessage()
                        .contains("██ Exception catch inside encrypt(original)"));
    }
}
