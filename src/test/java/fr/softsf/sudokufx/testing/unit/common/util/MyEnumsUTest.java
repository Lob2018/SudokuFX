/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.util;

import org.junit.jupiter.api.Test;

import static fr.softsf.sudokufx.common.enums.Paths.*;
import static fr.softsf.sudokufx.common.enums.ToastLevels.*;
import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_API_REPOSITORY_TAGS_URL;
import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MyEnumsUTest {

    @Test
    void givenFxmlResources_whenGetPath_thenPathExists() {
        assertNotNull(RESOURCES_FXML_PATH.getPath());
        assertNotNull(getClass().getResource(RESOURCES_FXML_PATH.getPath()));
    }

    @Test
    void givenCssResources_whenGetPath_thenPathExists() {
        assertNotNull(RESOURCES_CSS_PATH.getPath());
        assertNotNull(getClass().getResource(RESOURCES_CSS_PATH.getPath()));
    }

    @Test
    void givenI18nResources_whenGetPath_thenPathExists() {
        assertNotNull(I18N_PATH.getPath());
        assertNotNull(getClass().getResource("/" + I18N_PATH.getPath() + ".properties"));
    }

    @Test
    void givenLogoSudoPngResource_whenGetPath_thenPathExists() {
        assertNotNull(LOGO_SUDO_PNG_PATH.getPath());
        assertNotNull(getClass().getResource(LOGO_SUDO_PNG_PATH.getPath()));
    }

    @Test
    void givenUserHome_whenGetPath_thenPathExists() {
        assertNotNull(USER_HOME.getPath());
    }

    @Test
    void givenDataFolder_whenGetPath_thenPathExists() {
        assertNotNull(DATA_FOLDER.getPath());
    }

    @Test
    void givenLogsFolder_whenGetPath_thenPathExists() {
        assertNotNull(LOGS_FOLDER.getPath());
    }

    @Test
    void givenBasePaths_whenGetPaths_thenPathsExist() {
        assertNotNull(WINDOWS_BASE_PATH.getPath());
        assertNotNull(LINUX_BASE_PATH.getPath());
        assertNotNull(MACOS_BASE_PATH.getPath());
    }

    @Test
    void givenToastLevels_whenGetLevels_thenLevelsExist() {
        assertNotNull(INFO.getLevel());
        assertNotNull(WARN.getLevel());
        assertNotNull(ERROR.getLevel());
    }

    @Test
    void givenUrls_whenGetUrls_thenUrlsExist() {
        assertNotNull(GITHUB_REPOSITORY_RELEASES_URL.getUrl());
        assertNotNull(GITHUB_API_REPOSITORY_TAGS_URL.getUrl());
    }
}
