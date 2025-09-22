/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum for various application-specific paths. */
public enum Paths {
    RESOURCES_FXML_PATH("/fr/softsf/sudokufx/fxml/"),
    RESOURCES_CSS_PATH("/fr/softsf/sudokufx/style/style.css"),
    I18N_PATH("fr/softsf/sudokufx/i18n/resource"),
    DATABASE_MIGRATION_PATH("/fr/softsf/sudokufx/flyway/scripts/hsqldb/migration"),
    LOGO_SUDO_PNG_PATH("/fr/softsf/sudokufx/images/stage-icon.png"),
    CONFIG_LOGBACK_PATH("/fr/softsf/sudokufx/config/logback.xml"),
    CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS("/invalid/path"),
    USER_HOME(System.getProperty("user.home").replace("\\", "/")),
    DATABASE_NAME("sudokufxdb"),
    DATA_FOLDER("data-sudokufx"),
    LOGS_FOLDER("logs-sudokufx"),
    LOGS_FILE_NAME_PATH("/SudokuFX.log"),
    WINDOWS_BASE_PATH("/AppData/Local/Soft64.fr/SudokuFX/"),
    LINUX_BASE_PATH("/.local/share/Soft64.fr/SudokuFX/"),
    MACOS_BASE_PATH("/Library/Application Support/Soft64.fr/SudokuFX/"),
    WINDOWS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + WINDOWS_BASE_PATH.getPath()),
    LINUX_SUDO_FX_BASE_PATH(USER_HOME.getPath() + LINUX_BASE_PATH.getPath()),
    MACOS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + MACOS_BASE_PATH.getPath()),
    WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            WINDOWS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            LINUX_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            MACOS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            WINDOWS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
    LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            LINUX_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
    MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            MACOS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath());

    private final String path;

    Paths(final String path) {
        this.path = path;
    }

    public final String getPath() {
        return path;
    }
}
