/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.softsf.sudokufx.common.util.MyRegex;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX =
            "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s.]+$";

    @Test
    void givenValidNameVersionOrganizationLicense_whenValidateByRegex_thenValidationSucceeds() {
        String validName = "MyApp";
        String validVersion = "0.0.0.1";
        String validOrganization = "Soft64.fr";
        String validLicense = "GPLv3.0";
        assertTrue(MyRegex.INSTANCE.isValidAlphanumeric(validName));
        assertTrue(MyRegex.INSTANCE.isValidAlphanumeric(validOrganization));
        assertTrue(MyRegex.INSTANCE.isValidAlphanumeric(validLicense));
        assertTrue(MyRegex.INSTANCE.isValidVersion(validVersion));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "1.0.0", // Ancien format (X.Y.Z)
                "1.0.0.0", // Nouveau format (X.Y.Z.W)
                "999999999.0.0.1", // Limite haute (9 chiffres)
                "0.0.0", // Version minimale
                "10.20.30.40" // Version standard
            })
    void givenValidVersions_whenValidateByRegex_thenSucceeds(String version) {
        assertTrue(MyRegex.INSTANCE.isValidVersion(version), "Version should be valid: " + version);
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "1.0", // Trop court
                "1.0.0.0.0", // Trop long (5 segments)
                "01.0.0", // Zéro non significatif
                "1.0.0.1234567890", // Segment trop long (> 9 chiffres)
                "a.b.c", // Non numérique
                "1.0 .0" // Contient des espaces
            })
    void givenInvalidVersions_whenValidateByRegex_thenFails(String version) {
        assertFalse(
                MyRegex.INSTANCE.isValidVersion(version), "Version should be invalid: " + version);
    }

    @Test
    void givenInvalidNameVersionOrganizationLicense_whenValidateByRegex_thenValidationFails() {
        String invalidName = "MyApp123!";
        String invalidVersion = "0.0.";
        String invalidOrganization = "Soft64.fr!";
        String invalidLicense = "MIT License!";
        assertFalse(MyRegex.INSTANCE.isValidAlphanumeric(invalidName));
        assertFalse(MyRegex.INSTANCE.isValidAlphanumeric(invalidOrganization));
        assertFalse(MyRegex.INSTANCE.isValidAlphanumeric(invalidLicense));
        assertFalse(MyRegex.INSTANCE.isValidVersion(invalidVersion));
    }

    @Test
    void givenNullSpringContext_whenGetAppNameAndVersion_thenDefaultValuesReturned() {
        JVMApplicationProperties.INSTANCE.setInitSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
    }

    @Test
    void
            givenEmptyNameVersionOrganizationLicenseWithOnRefreshSpringContext_whenGetProperties_thenDefaultValuesAndExitOnRefresh() {
        JVMApplicationProperties.INSTANCE.setEmptyAppVersionPropertyForTests();
        JVMApplicationProperties.INSTANCE.setEmptyAppNamePropertyForTests();
        JVMApplicationProperties.INSTANCE.setEmptyAppOrganizationPropertyForTests();
        JVMApplicationProperties.INSTANCE.setEmptyAppLicensePropertyForTests();
        JVMApplicationProperties.INSTANCE.setOnRefreshSpringContextExitForTests();
        assertEquals("SudokuFX", JVMApplicationProperties.INSTANCE.getAppName());
        assertFalse(JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty());
        System.out.println(
                "Organization :" + JVMApplicationProperties.INSTANCE.getAppOrganization());
        System.out.println("License :" + JVMApplicationProperties.INSTANCE.getAppLicense());
        assertFalse(JVMApplicationProperties.INSTANCE.getAppOrganization().isEmpty());
        assertFalse(JVMApplicationProperties.INSTANCE.getAppLicense().isEmpty());
        assertTrue(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }

    @Test
    void givenNeverSpringContextExit_whenCheckExitOnRefresh_thenExitNever() {
        JVMApplicationProperties.INSTANCE.setNeverSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }

    @Test
    void givenUnknownSpringContext_whenCheckExitOnRefresh_thenExitNotTriggered() {
        JVMApplicationProperties.INSTANCE.setSpringContextExitInRefresh();
        JVMApplicationProperties.INSTANCE.setInitSpringContextExitForTests();
        assertFalse(JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh());
    }

    @Test
    void givenValidProperties_whenGetWindowTitle_thenCorrectTitleReturned() {
        JVMApplicationProperties.INSTANCE.setAppNameForTests();
        JVMApplicationProperties.INSTANCE.setAppVersionForTests();
        JVMApplicationProperties.INSTANCE.setAppOrganizationForTests();
        assertEquals(
                "SudokuFX1 v0.0.0 • MySoft64.fr",
                JVMApplicationProperties.INSTANCE.getWindowTitle());
    }
}
