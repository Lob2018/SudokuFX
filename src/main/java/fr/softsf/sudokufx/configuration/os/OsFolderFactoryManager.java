/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.configuration.os;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.softsf.sudokufx.enums.OsName;
import fr.softsf.sudokufx.enums.Paths;

/** Configuration class for managing OS-specific dynamically. */
@Configuration
public class OsFolderFactoryManager {

    private String os = OsName.OS_NAME.getOs();

    private String windowsIntendedPathDataFolder =
            Paths.WINDOWS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String windowsIntendedPathLogsFolder =
            Paths.WINDOWS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathDataFolder =
            Paths.LINUX_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String linuxIntendedPathLogsFolder =
            Paths.LINUX_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathDataFolder =
            Paths.MACOS_SUPPOSED_DATA_FOLDER_FOR_SUDO_FX.getPath();
    private String macosIntendedPathLogsFolder =
            Paths.MACOS_SUPPOSED_LOGS_FOLDER_FOR_SUDO_FX.getPath();

    /**
     * Creates and returns an OS-specific folder factory.
     *
     * @return An implementation of IOsFoldersFactory interface
     * @throws IllegalArgumentException if the OS is not supported
     */
    @Bean
    public IOsFolderFactory iOsFolderFactory() throws IllegalArgumentException {
        if (os == null || os.isEmpty()) {
            throw new IllegalArgumentException("Operating system is not specified or null.");
        }
        if (os.contains("windows")) {
            return new WindowsFolderFactory(
                    windowsIntendedPathDataFolder, windowsIntendedPathLogsFolder);
        } else if (os.contains("linux")) {
            return new LinuxFolderFactory(linuxIntendedPathDataFolder, linuxIntendedPathLogsFolder);
        } else if (os.contains("mac")) {
            return new MacosFolderFactory(macosIntendedPathDataFolder, macosIntendedPathLogsFolder);
        } else {
            throw new IllegalArgumentException("Unsupported OS: " + os);
        }
    }

    /**
     * Sets the OS to an unsupported value for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setWrongOsForTests() {
        os = OsName.WRONG_OS_FOR_TESTS.getOs();
    }

    /**
     * Sets the OS to null for testing purposes. This method should only be used in test scenarios.
     */
    void setNullOsForTests() {
        os = null;
    }

    /**
     * Sets the OS empty for testing purposes. This method should only be used in test scenarios.
     */
    void setEmptyOsForTests() {
        os = "";
    }

    /**
     * Sets Windows OS and set current paths for testing purposes. This method should only be used
     * in test scenarios.
     */
    void setWindowsOsForTests(
            String windowsIntendedPathDataFolder, String windowsIntendedPathLogsFolder) {
        this.windowsIntendedPathDataFolder = windowsIntendedPathDataFolder;
        this.windowsIntendedPathLogsFolder = windowsIntendedPathLogsFolder;
        os = "windows";
    }

    /**
     * Sets Linux OS and set current paths for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setLinuxOsForTests(
            String linuxIntendedPathDataFolder, String linuxIntendedPathLogsFolder) {
        this.linuxIntendedPathDataFolder = linuxIntendedPathDataFolder;
        this.linuxIntendedPathLogsFolder = linuxIntendedPathLogsFolder;
        os = "linux";
    }

    /**
     * Sets macOS OS and set current paths for testing purposes. This method should only be used in
     * test scenarios.
     */
    void setMacOSForTests(String macosIntendedPathDataFolder, String macosIntendedPathLogsFolder) {
        this.macosIntendedPathDataFolder = macosIntendedPathDataFolder;
        this.macosIntendedPathLogsFolder = macosIntendedPathLogsFolder;
        os = "mac";
    }
}
