/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.nio.file.Path;
import java.util.Objects;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.config.os.OsFoldersConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {SudoMain.class})
class ApplicationKeystoreITest {

    @TempDir static Path tempDir;

    private ApplicationKeystore keystore;

    @BeforeEach
    void setupMocks() {
        IOsFolder iCurrentIOsFolder = spy(new OsFoldersConfig().iOsFolderFactory());
        GenerateSecret generateSecret = spy(new GenerateSecret());
        doReturn(tempDir.toString()).when(iCurrentIOsFolder).getOsDataFolderPath();
        doReturn("fixedUsernameSecret".toCharArray())
                .doReturn("fixedPasswordSecret".toCharArray())
                .when(generateSecret)
                .generatePassaySecret();
        keystore = new ApplicationKeystore(iCurrentIOsFolder, generateSecret);
    }

    @Test
    void givenNullIOsFolderFactory_whenConstruct_thenThrowIllegalArgumentException() {
        GenerateSecret generateSecret = new GenerateSecret();
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            new ApplicationKeystore(null, generateSecret);
                        });
        assertEquals("The iOsFolderFactory must not be null", ex.getMessage());
    }

    @Test
    void givenNullGenerateSecret_whenConstruct_thenThrowIllegalArgumentException() {
        IOsFolder iOsFolder = new OsFoldersConfig().iOsFolderFactory();
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            new ApplicationKeystore(iOsFolder, null);
                        });
        assertEquals("The generateSecret must not be null", ex.getMessage());
    }

    @Test
    void givenNewKeystore_whenSetupKeystore_thenCredentialsInitialized() {
        keystore.setupApplicationKeystore();
        char[] user = keystore.getUsername();
        char[] pass = keystore.getPassword();
        assertNotNull(user, "Username should not be null");
        assertNotNull(pass, "Password should not be null");
        assertArrayEquals(
                "fixedUsernameSecret".toCharArray(),
                user,
                "Username should match generated secret");
        assertArrayEquals(
                "fixedPasswordSecret".toCharArray(),
                pass,
                "Password should match generated secret");
        assertEquals(19, user.length, "Username length should match expected");
        assertEquals(19, pass.length, "Password length should match expected");
    }

    @Test
    void givenExistingKeystore_whenSetupKeystore_thenCredentialsMatch() {
        keystore.setupApplicationKeystore();
        char[] initialUser = Objects.requireNonNull(keystore.getUsername()).clone();
        char[] initialPass = Objects.requireNonNull(keystore.getPassword()).clone();
        keystore.setupApplicationKeystore();
        char[] user = keystore.getUsername();
        char[] pass = keystore.getPassword();
        assertArrayEquals(initialUser, user, "Username should be the same as initial");
        assertArrayEquals(initialPass, pass, "Password should be the same as initial");
    }
}
