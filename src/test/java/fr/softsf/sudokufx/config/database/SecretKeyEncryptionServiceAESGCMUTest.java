/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
        char[] secret = {'S', 'e', 'c', 'r', 'e', 't'};
        char[] encrypted = iSecretKeyEncryptionServiceAESGCM.encrypt(secret);
        char[] decrypted = iSecretKeyEncryptionServiceAESGCM.decrypt(encrypted);
        assertArrayEquals(secret, decrypted);
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
                () -> iSecretKeyEncryptionServiceAESGCM.encrypt(new char[0]));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.encrypt(new char[] {' ', ' ', ' '}));
    }

    @Test
    void givenNullOrBlankCypher_whenDecrypt_thenIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt(null));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt(new char[0]));
        assertThrows(
                IllegalArgumentException.class,
                () -> iSecretKeyEncryptionServiceAESGCM.decrypt(new char[] {' ', ' ', ' '}));
    }

    @Test
    void givenInvalidCipherText_whenDecrypt_thenEmptyArrayReturnedAndErrorLogged() {
        char[] invalidCypher = "not#validbase64".toCharArray();
        char[] decrypted = iSecretKeyEncryptionServiceAESGCM.decrypt(invalidCypher);
        assertArrayEquals(new char[0], decrypted);
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
        char[] secret = {'T', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't'};
        char[] encrypted = iSecretKeyEncryptionServiceAESGCM.encrypt(secret);
        assertNotNull(encrypted);
        String encryptedStr = new String(encrypted);
        String[] parts = encryptedStr.split("#");
        assertEquals(2, parts.length);
        Base64.getDecoder().decode(parts[0]);
        Base64.getDecoder().decode(parts[1]);
    }

    @Test
    void givenInvalidSecretKey_whenEncrypt_thenEmptyArrayReturnedAndErrorLogged() {
        byte[] invalidBytes = new byte[4];
        new SecureRandom().nextBytes(invalidBytes);
        SecretKey invalidKey = new SecretKeySpec(invalidBytes, "AES");
        IEncryptionService brokenEncryptionService =
                new SecretKeyEncryptionServiceAESGCM(invalidKey);
        char[] result =
                brokenEncryptionService.encrypt(
                        new char[] {'f', 'a', 'i', 'l', 'E', 'n', 'c', 'r', 'y', 'p', 't'});
        assertArrayEquals(new char[0], result);
        assertFalse(logWatcher.list.isEmpty());
        assertTrue(
                logWatcher
                        .list
                        .getLast()
                        .getFormattedMessage()
                        .contains("██ Exception catch inside encrypt(original)"));
    }
}
