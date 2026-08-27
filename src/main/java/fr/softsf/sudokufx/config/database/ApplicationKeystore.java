/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.config.os.OSSecureStore;

/**
 * Manages the application's keystore for secure storage of a symmetric key and database
 * credentials. Ensures dependencies are validated at construction to guarantee correct setup.
 */
@Component
public final class ApplicationKeystore implements IKeystore {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationKeystore.class);
    private static final String KEYSTORE_TYPE = "pkcs12";
    private static final String SYMMETRIC_KEY_ALIAS = "db-encryption-secret";
    private static final String USERNAME_ALIAS = "db-user-alias";
    private static final String PASS_ALIAS = "db-pass-alias";
    private static final String KEYSTORE_FILE_PATH = "/SudokuFXKeyStore.p12";
    private static final String AES_ALGORITHM = "AES";
    private static final int AES_KEY_SIZE_BITS = 256;
    private final char[] pwdArray;
    private final GenerateSecret generateSecret;
    private final IOsFolder iOsFolder;
    private final OSSecureStore oSSecureStore;
    private String keystoreFilePath;
    private KeyStore ks;
    private IEncryptionService iEncryptionService;

    private String username;
    private String password;

    /**
     * Constructs a new {@code ApplicationKeystore} with required infrastructure dependencies.
     *
     * <p>Validates that the provided folder service and secret generator are non-null to ensure
     * proper keystore initialization and credential management.
     *
     * @param iOsFolder the OS-specific folder utility for locating the keystore file
     * @param generateSecret the service used to generate secure secrets for credentials
     * @throws IllegalArgumentException if {@code iOsFolder} or {@code generateSecret} or {@code
     *     oSSecureStore} is {@code null}
     */
    public ApplicationKeystore(
            IOsFolder iOsFolder, GenerateSecret generateSecret, OSSecureStore oSSecureStore) {
        if (Objects.isNull(iOsFolder)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The iOsFolderFactory must not be null");
        }
        if (Objects.isNull(generateSecret)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The generateSecret must not be null");
        }
        if (Objects.isNull(oSSecureStore)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The oSSecureStore must not be null");
        }
        this.iOsFolder = iOsFolder;
        this.generateSecret = generateSecret;
        this.oSSecureStore = oSSecureStore;
        this.pwdArray = resolvePwdArray();
    }

    /**
     * Resolves a password character array derived from the secure store or system username.
     *
     * <p>Retrieves the source string from the secure store, falling back to the system username if
     * blank, and generates a deterministic UUID character array from it.
     *
     * @return the generated UUID password as a character array
     * @throws IllegalArgumentException if the resolved password source is blank or null
     */
    private char[] resolvePwdArray() {
        String source = oSSecureStore.getCredentialPassword();
        if (StringUtils.isBlank(source)) {
            source = System.getProperty("user.name");
        }
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                source, "Password source must not be null or blank");
        return String.valueOf(UUID.nameUUIDFromBytes(source.getBytes(StandardCharsets.UTF_8)))
                .toCharArray();
    }

    /**
     * Writes the keystore content to a file.
     *
     * @param ks the keystore to save (must not be null)
     * @param keystoreFileName the output file path (must not be null or blank)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional for cryptographic and keystore operations; providers"
                            + " may throw unexpected RuntimeExceptions.")
    private void writeTheKeystore(final KeyStore ks, final String keystoreFileName) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                keystoreFileName,
                "keystoreFileName must not be null or blank, but was " + keystoreFileName);
        if (Objects.isNull(ks)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The keystore must not be null");
        }
        if (ObjectUtils.isEmpty(pwdArray)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The pwdArray must not be null or empty");
        }
        try (FileOutputStream fos = new FileOutputStream(keystoreFileName)) {
            ks.store(fos, pwdArray);
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
            ks.load(null, pwdArray);
            ks.store(fos, pwdArray);
        } catch (IOException
                | NoSuchAlgorithmException
                | CertificateException
                | KeyStoreException e) {
            LOG.error("██ Exception catch inside createOrUpdateKeystore() : {}", e.getMessage(), e);
        }
    }

    /** Load the Keystore */
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional for keystore loading; JCE providers and IO"
                            + " operations may throw unexpected RuntimeExceptions.")
    private void loadKeyStore() {
        try (FileInputStream fileInputStream = new FileInputStream(keystoreFilePath)) {
            ks.load(fileInputStream, pwdArray);
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
                                    SYMMETRIC_KEY_ALIAS, new KeyStore.PasswordProtection(pwdArray));
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
                            if (Objects.isNull(username)) {
                                username = generateSecret.generatePassaySecret();
                            }
                            yield iEncryptionService.encrypt(username);
                        }
                        case PASS_ALIAS -> {
                            if (Objects.isNull(password)) {
                                password = generateSecret.generatePassaySecret();
                            }
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
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");
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
    @SuppressFBWarnings(
            value = "REC_CATCH_EXCEPTION",
            justification =
                    "Wide catch is intentional for keystore credential retrieval; JCE providers and"
                            + " decryption routines may throw unexpected RuntimeExceptions.")
    private void getCredentials(final String alias) {
        try {
            KeyStore.Entry entry = ks.getEntry(alias, new KeyStore.PasswordProtection(pwdArray));
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
                // UUID.nameUUIDFromBytes(System.getProperty("user.name").getBytes(StandardCharsets.UTF_8)) alias username password
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
        KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(pwdArray);
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
    public void enforceKeystorePasswordIfNeeded() {
        if (oSSecureStore.isSystemUsernamePassword()) {
            LOG.info("\n▓▓ Enforcing keystore password replacement");
            String strongPassword = generateSecret.generatePassaySecret();
            migrateKeystore(strongPassword, false);
            oSSecureStore.saveCredential(strongPassword);
            LOG.info("\n▓▓ Keystore password successfully enforced and overwritten");
        }
    }

    @Override
    public void migrateKeystore(final String newPassword, final boolean isMigrationFile) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                newPassword,
                "The newUserNameInput must not be null or empty, but was " + newPassword);
        if (Objects.isNull(keystoreFilePath)) {
            keystoreFilePath = iOsFolder.getOsDataFolderPath() + KEYSTORE_FILE_PATH;
        }
        Path path = Paths.get(keystoreFilePath);
        if (Files.exists(path)) {
            try {
                KeyStore tempKs = KeyStore.getInstance(KEYSTORE_TYPE);
                try (FileInputStream fis = new FileInputStream(path.toFile())) {
                    tempKs.load(fis, pwdArray);
                }
                char[] migrationPwdArray =
                        String.valueOf(
                                        UUID.nameUUIDFromBytes(
                                                newPassword.getBytes(StandardCharsets.UTF_8)))
                                .toCharArray();
                KeyStore migrationKs = KeyStore.getInstance(KEYSTORE_TYPE);
                migrationKs.load(null, migrationPwdArray);
                copyKeystoreEntries(tempKs, migrationKs, migrationPwdArray);
                Path targetPath;
                if (isMigrationFile) {
                    javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
                    fileChooser.setTitle("Save Migration Keystore");
                    fileChooser.setInitialFileName("SudokuFXKeyStore.p12.migration");
                    fileChooser
                            .getExtensionFilters()
                            .add(
                                    new javafx.stage.FileChooser.ExtensionFilter(
                                            "Migration Files", "*.migration"));
                    java.io.File selectedFile = fileChooser.showSaveDialog(null);
                    if (Objects.isNull(selectedFile)) {
                        LOG.warn("▓▓ Keystore migration cancelled by user");
                        return;
                    }
                    targetPath = selectedFile.toPath();
                    if (!targetPath.getFileName().toString().endsWith(".migration")) {
                        targetPath = targetPath.resolveSibling("SudokuFXKeyStore.p12.migration");
                    }
                } else {
                    targetPath = Paths.get(keystoreFilePath);
                }
                try (FileOutputStream fos = new FileOutputStream(targetPath.toFile())) {
                    migrationKs.store(fos, migrationPwdArray);
                }
                LOG.info("▓▓ Keystore migration successfully generated at: {}", targetPath);
            } catch (KeyStoreException
                    | IOException
                    | NoSuchAlgorithmException
                    | CertificateException
                    | UnrecoverableEntryException e) {
                LOG.error("██ Exception catch inside migrateKeystore() : {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Copies required entries from the source keystore to the migration keystore.
     *
     * @param sourceKs the source keystore
     * @param targetKs the target migration keystore
     * @param targetPwd the target protection password
     */
    private void copyKeystoreEntries(
            final KeyStore sourceKs, final KeyStore targetKs, final char[] targetPwd)
            throws UnrecoverableEntryException, KeyStoreException, NoSuchAlgorithmException {
        copySingleEntry(sourceKs, targetKs, targetPwd, SYMMETRIC_KEY_ALIAS);
        copySingleEntry(sourceKs, targetKs, targetPwd, USERNAME_ALIAS);
        copySingleEntry(sourceKs, targetKs, targetPwd, PASS_ALIAS);
    }

    /**
     * Copies a single entry by alias from source to target keystore if present.
     *
     * @param sourceKs the source keystore
     * @param targetKs the target migration keystore
     * @param targetPwd the target protection password
     * @param alias the entry alias
     */
    private void copySingleEntry(
            final KeyStore sourceKs,
            final KeyStore targetKs,
            final char[] targetPwd,
            final String alias)
            throws KeyStoreException, UnrecoverableEntryException, NoSuchAlgorithmException {
        if (Objects.isNull(sourceKs)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The sourceKs must not be null");
        }
        if (Objects.isNull(targetKs)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The targetKs must not be null");
        }
        if (ObjectUtils.isEmpty(targetPwd)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The targetPwd must not be null or empty");
        }
        if (sourceKs.containsAlias(alias)) {
            KeyStore.Entry entry =
                    sourceKs.getEntry(alias, new KeyStore.PasswordProtection(pwdArray));
            if (entry != null) {
                targetKs.setEntry(alias, entry, new KeyStore.PasswordProtection(targetPwd));
            }
        }
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
