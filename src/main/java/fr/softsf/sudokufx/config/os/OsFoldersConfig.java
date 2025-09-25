/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.softsf.sudokufx.common.enums.AppPaths;
import fr.softsf.sudokufx.common.enums.OsName;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/** Configuration class for managing OS-specific dynamically. */
@Configuration
public class OsFoldersConfig {

    private OsName os = OsName.detect();

    private String windowsIntendedPathDataFolder =
            AppPaths.WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String windowsIntendedPathLogsFolder =
            AppPaths.WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathDataFolder =
            AppPaths.LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathLogsFolder =
            AppPaths.LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathDataFolder =
            AppPaths.MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathLogsFolder =
            AppPaths.MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();

    /**
     * Creates and returns the initialized OS-specific folders.
     *
     * @return an {@link IOsFolder} (backed by {@link OsInitializedFolders})
     * @throws IllegalArgumentException if the OS is blank or not supported
     */
    @Bean
    public IOsFolder iOsFolderFactory() {
        return switch (os) {
            case WINDOWS ->
                    OsFolderInitializer.INSTANCE.initializeFolders(
                            windowsIntendedPathDataFolder, windowsIntendedPathLogsFolder);
            case LINUX ->
                    OsFolderInitializer.INSTANCE.initializeFolders(
                            linuxIntendedPathDataFolder, linuxIntendedPathLogsFolder);
            case MAC ->
                    OsFolderInitializer.INSTANCE.initializeFolders(
                            macosIntendedPathDataFolder, macosIntendedPathLogsFolder);
            default ->
                    throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                            "Unsupported OS: " + os);
        };
    }

    /**
     * Sets the OS to an unsupported value for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setWrongOsForTests() {
        os = OsName.WRONG_OS_FOR_TESTS;
    }

    /**
     * Sets the OS empty for testing purposes. This method should only be used in test scenarios.
     */
    void setEmptyOsForTests() {
        os = OsName.EMPTY_OS_FOR_TESTS;
    }

    /**
     * Sets Windows OS and set current paths for testing purposes. This method should only be used
     * in test scenarios.
     */
    void setWindowsOsForTests(
            String windowsIntendedPathDataFolder, String windowsIntendedPathLogsFolder) {
        this.windowsIntendedPathDataFolder = windowsIntendedPathDataFolder;
        this.windowsIntendedPathLogsFolder = windowsIntendedPathLogsFolder;
        os = OsName.WINDOWS;
    }

    /**
     * Sets Linux OS and set current paths for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setLinuxOsForTests(
            String linuxIntendedPathDataFolder, String linuxIntendedPathLogsFolder) {
        this.linuxIntendedPathDataFolder = linuxIntendedPathDataFolder;
        this.linuxIntendedPathLogsFolder = linuxIntendedPathLogsFolder;
        os = OsName.LINUX;
    }

    /**
     * Sets macOS OS and set current paths for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setMacOSForTests(String macosIntendedPathDataFolder, String macosIntendedPathLogsFolder) {
        this.macosIntendedPathDataFolder = macosIntendedPathDataFolder;
        this.macosIntendedPathLogsFolder = macosIntendedPathLogsFolder;
        os = OsName.MAC;
    }
}
