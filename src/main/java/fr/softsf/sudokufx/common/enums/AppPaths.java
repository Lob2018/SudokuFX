/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Centralized registry of application-specific paths, both internal (classpath) and system-level.
 *
 * <p>All paths are expressed as POSIX-style strings for consistency across platforms. System paths
 * are dynamically resolved at runtime, including XDG-compliant locations on Linux.
 */
public enum AppPaths {
    RESOURCES_FXML_PATH("/fr/softsf/sudokufx/fxml/"),
    RESOURCES_CSS_PATH("/fr/softsf/sudokufx/style/style.css"),
    RESOURCES_CSS_ALERT_PATH("/fr/softsf/sudokufx/style/alert.css"),
    I18N_PATH("fr/softsf/sudokufx/i18n/resource"),
    DATABASE_MIGRATION_PATH("/fr/softsf/sudokufx/flyway/scripts/hsqldb/migration"),
    LOGO_SUDO_PNG_PATH("/fr/softsf/sudokufx/images/stage-icon.png"),
    CONFIG_LOGBACK_PATH("/fr/softsf/sudokufx/config/logback.xml"),
    CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS("/invalid/path"),
    USER_HOME(resolveUserHome()),
    DATABASE_NAME("sudokufxdb"),
    DATA_FOLDER("data-sudokufx"),
    LOGS_FOLDER("logs-sudokufx"),
    LOGS_FILE_NAME_PATH("/SudokuFX.log"),
    WINDOWS_BASE_PATH("/AppData/Local/Soft64.fr/SudokuFX/"),
    MACOS_BASE_PATH("/Library/Application Support/Soft64.fr/SudokuFX/"),
    LINUX_BASE_PATH(resolveXdgDataHome()),
    WINDOWS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + WINDOWS_BASE_PATH.getPath()),
    MACOS_SUDO_FX_BASE_PATH(USER_HOME.getPath() + MACOS_BASE_PATH.getPath()),
    LINUX_SUDO_FX_BASE_PATH(LINUX_BASE_PATH.getPath() + "/Soft64.fr/SudokuFX/"),
    WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            WINDOWS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            MACOS_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX(
            LINUX_SUDO_FX_BASE_PATH.getPath() + DATA_FOLDER.getPath()),
    WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            WINDOWS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
    MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            MACOS_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath()),
    LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX(
            LINUX_SUDO_FX_BASE_PATH.getPath() + LOGS_FOLDER.getPath());

    private final String path;

    AppPaths(final String path) {
        this.path = path;
    }

    public final String getPath() {
        return path;
    }

    /**
     * Resolves the user-specific data directory according to the XDG Base Directory Specification.
     *
     * <p>If the environment variable {@code XDG_DATA_HOME} is defined and non-empty, its value is
     * used as the base directory for application data. Otherwise, the fallback {@code
     * $HOME/.local/share} is returned, ensuring compatibility with Linux systems that follow the
     * XDG standard.
     *
     * <p>Backslashes are replaced with forward slashes to ensure consistent path formatting across
     * operating systems, especially for string-based path composition.
     *
     * @return a {@link String} representing the resolved data directory path in POSIX format
     */
    private static String resolveXdgDataHome() {
        String xdg = System.getenv("XDG_DATA_HOME");
        return (StringUtils.isBlank(xdg))
                ? resolveUserHome() + "/.local/share"
                : xdg.replace("\\", "/");
    }

    /**
     * Returns the user's home directory with forward slashes, for consistent path concatenation.
     */
    private static String resolveUserHome() {
        return System.getProperty("user.home").replace("\\", "/");
    }
}
