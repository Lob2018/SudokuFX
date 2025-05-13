/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.configuration.database;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.softsf.sudokufx.SudoMain;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {SudoMain.class})
class ApplicationKeystoreITest {

    private static String passInit;
    private static String userInit;
    private final ApplicationKeystore keystore;

    @Autowired
    public ApplicationKeystoreITest(ApplicationKeystore keystore) {
        this.keystore = keystore;
    }

    @Test
    @Order(0)
    void givenNewKeystore_whenSetupKeystore_thenCredentialsInitialized() {
        keystore.setupApplicationKeystore();
        passInit = keystore.getPassword();
        userInit = keystore.getUsername();
        assertEquals(passInit.length(), userInit.length(), 24);
    }

    @Test
    @Order(1)
    void givenExistingKeystore_whenSetupKeystore_thenCredentialsMatch() {
        keystore.setupApplicationKeystore();
        String pass = keystore.getPassword();
        String user = keystore.getUsername();
        assertEquals(pass.length(), user.length(), 24);
        assertEquals(user, userInit);
        assertEquals(pass, passInit);
    }
}
