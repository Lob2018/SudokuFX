/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.UUID;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.config.os.IOsFolder;

/**
 * Manages the application's keystore for secure storage of a symmetric key and database
 * credentials. Ensures dependencies are validated at construction to guarantee correct setup.
 */
@Component
final class ApplicationKeystore implements IKeystore {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationKeystore.class);

    private static final String KEYSTORE_PASSWORD_FROM_UUID =
            String.valueOf(UUID.nameUUIDFromBytes(System.getProperty("user.name").getBytes()));
    private static final String KEYSTORE_TYPE = "pkcs12";
    private static final char[] PWD_ARRAY = KEYSTORE_PASSWORD_FROM_UUID.toCharArray();
    private static final String SYMMETRIC_KEY_ALIAS = "db-encryption-secret";
    private static final String USERNAME_ALIAS = "db-user-alias";
    private static final String PASS_ALIAS = "db-pass-alias";
    private static final String KEYSTORE_FILE_PATH = "/SudokuFXKeyStore.p12";
    private static final String AES_ALGORITHM = "AES";
    private static final int AES_KEY_SIZE_BITS = 256;
    private final GenerateSecret generateSecret;
    private final IOsFolder iOsFolder;
    private String keystoreFilePath;
    private KeyStore ks;
    private IEncryptionService iEncryptionService;

    private String username;
    private String password;

    public ApplicationKeystore(IOsFolder iOsFolder, GenerateSecret generateSecret) {
        if (Objects.isNull(iOsFolder)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The iOsFolderFactory must not be null");
        }
        if (Objects.isNull(generateSecret)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The generateSecret must not be null");
        }
        this.iOsFolder = iOsFolder;
        this.generateSecret = generateSecret;
    }

    /**
     * Writes the keystore content to a file.
     *
     * @param ks the keystore to save (must not be null)
     * @param keystoreFileName the output file path (must not be null or blank)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    private static void writeTheKeystore(final KeyStore ks, final String keystoreFileName) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                keystoreFileName,
                "keystoreFileName must not be null or blank, but was " + keystoreFileName);
        if (Objects.isNull(ks)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The keystore must not be null");
        }
        if (ObjectUtils.isEmpty(ApplicationKeystore.PWD_ARRAY)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The pwdArray must not be null or empty");
        }
        try (FileOutputStream fos = new FileOutputStream(keystoreFileName)) {
            ks.store(fos, ApplicationKeystore.PWD_ARRAY);
        } catch (Exception e) {
            LOG.error("██ Exception catch inside writeTheKeystore/fos : {}", e.getMessage(), e);
        }
    }

    /**
     * Configures the keystore by managing the entire process of creating, loading, and encrypting
     * the necessary keys and credentials.
     */
    public void setupApplicationKeystore() {
        LOG.info("\n▓▓ ApplicationKeystore starts");
        try {
            ks = KeyStore.getInstance(KEYSTORE_TYPE);
            keystoreFilePath = iOsFolder.getOsDataFolderPath() + KEYSTORE_FILE_PATH;
            createOrUpdateKeystore();
            loadKeyStore();
            symmetricKey();
            credentials(USERNAME_ALIAS);
            credentials(PASS_ALIAS);
        } catch (Exception e) {
            LOG.error(
                    "██ Exception catch inside ApplicationKeystore setupApplicationKeystore() : {}",
                    e.getMessage(),
                    e);
        }
        LOG.info("\n▓▓ ApplicationKeystore is ready");
    }

    /** Create or update the Keystore */
    private void createOrUpdateKeystore() {
        try (FileOutputStream fos = new FileOutputStream(keystoreFilePath, true)) {
            ks.load(null, PWD_ARRAY);
            ks.store(fos, PWD_ARRAY);
        } catch (IOException
                | NoSuchAlgorithmException
                | CertificateException
                | KeyStoreException e) {
            LOG.error("██ Exception catch inside createOrUpdateKeystore() : {}", e.getMessage(), e);
        }
    }

    /** Load the Keystore */
    private void loadKeyStore() {
        try (FileInputStream fileInputStream = new FileInputStream(keystoreFilePath)) {
            ks.load(fileInputStream, PWD_ARRAY);
        } catch (Exception e) {
            LOG.error(
                    "██ Exception catch inside loadKeyStore() - JVM doesn't support type OR"
                            + " password is wrong : {}",
                    e.getMessage(),
                    e);
        }
    }

    /** Check the symmetric key presence */
    private void symmetricKey() {
        try {
            if (ks.containsAlias(SYMMETRIC_KEY_ALIAS)) {
                symmetricKeyIsInKeystore();
            } else {
                symmetricKeyNotInKeystore();
            }
        } catch (KeyStoreException e) {
            LOG.error(
                    "██ Exception catch inside symmetricKey/ks.containsAlias(SYMMETRIC_KEY_ALIAS) :"
                            + " {}",
                    e.getMessage(),
                    e);
        }
    }

    /** Get the symmetric key and set encryption service */
    private void symmetricKeyIsInKeystore() {
        try {
            KeyStore.SecretKeyEntry entry =
                    (KeyStore.SecretKeyEntry)
                            ks.getEntry(
                                    SYMMETRIC_KEY_ALIAS,
                                    new KeyStore.PasswordProtection(PWD_ARRAY));
            if (entry != null) {
                iEncryptionService = new SecretKeyEncryptionServiceAESGCM(entry.getSecretKey());
            }
        } catch (NoSuchAlgorithmException | UnrecoverableEntryException | KeyStoreException e) {
            LOG.error(
                    "██ Exception catch inside"
                            + " symmetricKeyIsNotInKeystore/ks.getEntry(SYMMETRIC_KEY_ALIAS :{}",
                    e.getMessage(),
                    e);
        }
    }

    /** Set the symmetric key and set encryption service */
    private void symmetricKeyNotInKeystore() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGen.init(AES_KEY_SIZE_BITS, new SecureRandom());
            SecretKey symmetricKey = keyGen.generateKey();
            iEncryptionService = new SecretKeyEncryptionServiceAESGCM(symmetricKey);
            addToKeystore(SYMMETRIC_KEY_ALIAS, symmetricKey);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(
                    "██ Exception catch inside symmetricKeyIsInKeystore/keyGen ="
                            + " KeyGenerator.getInstance : {}",
                    e.getMessage(),
                    e);
        }
    }

    /**
     * Retrieves credentials if the alias exists in the keystore; otherwise, generates and stores
     * them.
     *
     * @param alias the keystore alias for the credential
     * @throws IllegalArgumentException if alias is null or blank
     */
    private void credentials(final String alias) {
        validateAliasNotBlank(alias);
        try {
            if (ks.containsAlias(alias)) {
                getCredentials(alias);
            } else {
                setCredentials(alias);
            }
        } catch (KeyStoreException e) {
            LOG.error(
                    "██ Exception catch inside credentials/ks.containsAlias(alias) : {}",
                    e.getMessage(),
                    e);
        }
    }

    /**
     * Generates and encrypts a credential for the given alias, then stores it securely in the
     * keystore.
     *
     * @param alias the keystore alias for the credential
     */
    private void setCredentials(final String alias) {
        try {
            String secret =
                    switch (alias) {
                        case USERNAME_ALIAS -> {
                            username = generateSecret.generatePassaySecret();
                            yield iEncryptionService.encrypt(username);
                        }
                        case PASS_ALIAS -> {
                            password = generateSecret.generatePassaySecret();
                            yield iEncryptionService.encrypt(password);
                        }
                        default -> {
                            ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                                    alias,
                                    "The keystore alias for the credential must not be null or"
                                            + " blank, but was "
                                            + alias);
                            yield "";
                        }
                    };
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "AES");
            addToKeystore(alias, secretKey);
        } catch (Exception e) {
            LOG.error("██ Exception catch inside setCredentials(alias) : {}", e.getMessage(), e);
        }
    }

    /**
     * Retrieves and decrypts the credential associated with the given alias from the keystore,
     * updating the corresponding field.
     *
     * @param alias the keystore alias for the credential
     */
    private void getCredentials(final String alias) {
        try {
            KeyStore.Entry entry = ks.getEntry(alias, new KeyStore.PasswordProtection(PWD_ARRAY));
            if (entry instanceof KeyStore.SecretKeyEntry secretEntry) {
                byte[] keyBytes = secretEntry.getSecretKey().getEncoded();
                String value =
                        iEncryptionService.decrypt(new String(keyBytes, StandardCharsets.UTF_8));
                if (alias.equals(USERNAME_ALIAS)) {
                    username = value;
                } else if (alias.equals(PASS_ALIAS)) {
                    password = value;
                }
                // TODO: remove in production
                LOG.info(
                        "▓▓▓▓ GET alias - username - password - secret : {} - {} - {}",
                        alias,
                        username,
                        password);
            } else {
                LOG.warn("▓▓ Entry is not an instance of the Keystore");
            }
        } catch (Exception e) {
            LOG.error("██ Exception catch inside getCredentials(alias) : {}", e.getMessage(), e);
        }
    }

    /**
     * Validates that the given alias is not null, empty, or blank.
     *
     * @param alias the string to validate
     * @throws IllegalArgumentException if alias is null, empty, or blank
     */
    private static void validateAliasNotBlank(String alias) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                alias, "Alias must not be null or blank, but was " + alias);
    }

    /**
     * Stores the given secret key in the keystore under the specified alias.
     *
     * @param alias the alias for the secret key
     * @param secretKey the secret key to store; must not be null
     * @throws IllegalArgumentException if alias is null/blank or secretKey is null
     */
    private void addToKeystore(final String alias, final SecretKey secretKey) {
        validateAliasNotBlank(alias);
        if (Objects.isNull(secretKey)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "SecretKey must not be null");
        }
        KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(PWD_ARRAY);
        try {
            ks.setEntry(alias, secret, entryPassword);
        } catch (KeyStoreException e) {
            LOG.error(
                    "██ Exception catch inside addToKeystore/ks.setEntry(alias, secret,"
                            + " entryPassword) : {}",
                    e.getMessage(),
                    e);
        }
        writeTheKeystore(ks, keystoreFilePath);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
