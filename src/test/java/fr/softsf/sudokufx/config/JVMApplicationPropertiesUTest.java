/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.MyRegex;

import static org.junit.jupiter.api.Assertions.*;

class JVMApplicationPropertiesUTest {

    private static final String VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    private static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9\\s.]+$";

    @Test
    void givenValidNameVersionOrganizationLicense_whenValidateByRegex_thenValidationSucceeds() {
        String validName = "MyApp";
        String validVersion = "0.0.1";
        String validOrganization = "Soft64.fr";
        String validLicense = "MIT License";
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(
                        validName, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(
                        validOrganization, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(
                        validLicense, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertTrue(
                MyRegex.INSTANCE.isValidatedByRegex(validVersion, Pattern.compile(VERSION_REGEX)));
    }

    @Test
    void givenInvalidNameVersionOrganizationLicense_whenValidateByRegex_thenValidationFails() {
        String invalidName = "MyApp123!";
        String invalidVersion = "0.0.";
        String invalidOrganization = "Soft64.fr!";
        String invalidLicense = "MIT License!";
        assertFalse(
                MyRegex.INSTANCE.isValidatedByRegex(
                        invalidName, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertFalse(
                MyRegex.INSTANCE.isValidatedByRegex(
                        invalidOrganization, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertFalse(
                MyRegex.INSTANCE.isValidatedByRegex(
                        invalidLicense, Pattern.compile(ALPHANUMERIC_REGEX)));
        assertFalse(
                MyRegex.INSTANCE.isValidatedByRegex(
                        invalidVersion, Pattern.compile(VERSION_REGEX)));
        assertFalse(MyRegex.INSTANCE.isValidatedByRegex("", Pattern.compile(VERSION_REGEX)));
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
}
