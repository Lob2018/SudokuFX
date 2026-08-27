/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.os;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microsoft.credentialstorage.SecretStore;
import com.microsoft.credentialstorage.StorageProvider;
import com.microsoft.credentialstorage.model.StoredCredential;

/** Manages secure credential storage using the native OS vault abstraction. */
@Component
public final class OSSecureStore {

    private static final Logger LOG = LoggerFactory.getLogger(OSSecureStore.class);
    private static final String CREDENTIALS_KEY = "SudokuFXMasterKey";

    private SecretStore<StoredCredential> credentialStorage;

    /**
     * Constructs a new {@code OSSecureStore} and initializes the native credential storage
     * provider.
     */
    public OSSecureStore() {
        try {
            this.credentialStorage =
                    StorageProvider.getCredentialStorage(
                            true, StorageProvider.SecureOption.REQUIRED);
            if (this.credentialStorage != null) {
                LOG.info("\n▓▓ Native secure storage available");
            } else {
                LOG.warn("\n▓▓ Native secure storage unavailable");
            }
        } catch (Exception e) {
            LOG.warn("\n▓▓ Native secure storage initialization failed: {}", e.getMessage());
            this.credentialStorage = null;
        }
    }

    /**
     * Checks if the native OS secure storage is available.
     *
     * @return true if available, false otherwise
     */
    private boolean isNotAvailable() {
        return this.credentialStorage == null;
    }

    /**
     * Checks if the specific key exists in the secure storage, initializing it with the system
     * username if missing.
     *
     * @return true if the key exists or was successfully initialized, false otherwise
     */
    private boolean credentialExists() {
        try {
            if (isNotAvailable()) {
                return false;
            }
            if (this.credentialStorage.get(CREDENTIALS_KEY) == null) {
                return saveCredential(System.getProperty("user.name"));
            }
            return true;
        } catch (Exception e) {
            LOG.error("██ Error checking credential existence: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the stored password from the native vault as a String, or null if not found.
     *
     * @return the stored password, or null if not found
     */
    public String getCredentialPassword() {
        if (!credentialExists()) {
            return null;
        }
        try {
            StoredCredential credential = this.credentialStorage.get(CREDENTIALS_KEY);
            return credential != null && credential.getPassword() != null
                    ? new String(credential.getPassword())
                    : null;
        } catch (Exception e) {
            LOG.error("██ Error retrieving credential: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Stores a password into the native vault as a credential.
     *
     * @param password the password string to store
     * @return true if the credential was successfully stored, false otherwise
     */
    public boolean saveCredential(final String password) {
        if (isNotAvailable() || StringUtils.isBlank(password)) {
            return false;
        }
        try {
            this.credentialStorage.add(
                    CREDENTIALS_KEY, new StoredCredential(CREDENTIALS_KEY, password.toCharArray()));
            LOG.info("\n▓▓ Credential are saved");
            return true;
        } catch (Exception e) {
            LOG.error("██ Error saving credential: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Deletes the credential from the native vault.
     *
     * @return true if the credential was successfully deleted, false otherwise
     */
    public boolean deleteCredential() {
        if (isNotAvailable()) {
            return false;
        }
        try {
            this.credentialStorage.delete(CREDENTIALS_KEY);
            LOG.info("\n▓▓ Credential is deleted");
            return true;
        } catch (Exception e) {
            LOG.error("██ Error deleting credential: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the stored credential password matches the current system username.
     *
     * @return true if the stored password equals the system username, false otherwise
     */
    public boolean isSystemUsernamePassword() {
        final String password = getCredentialPassword();
        final String username = System.getProperty("user.name");
        LOG.info("██ password: {} - System.getProperty(\"user.name\"): {}", password, username);
        return password != null && password.equals(System.getProperty("user.name"));
    }
}
