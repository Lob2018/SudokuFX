/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

/**
 * Factory class for creating and managing MACOS OS-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
final class MacosFolderFactory implements IOsFolderFactory {

    private final String logsFolderForSudoFx;
    private final String dataFolderForSudoFx;

    /**
     * Constructor that initializes the data and logs folder paths.
     * <p>
     * The given paths are validated and the folders created by {@link OsFolderInitializer#initializeFolders(String, String)}.
     * This method may throw exceptions if paths are invalid or folder creation fails.
     * </p>
     *
     * @param dataFolderPath The intended path for the data folder
     * @param logsFolderPath The intended path for the logs folder
     */
    public MacosFolderFactory(String dataFolderPath, String logsFolderPath) {
        final String[] folders =
                OsFolderInitializer.INSTANCE.initializeFolders(dataFolderPath, logsFolderPath);
        dataFolderForSudoFx = folders[0];
        logsFolderForSudoFx = folders[1];
    }

    @Override
    public String getOsDataFolderPath() {
        return dataFolderForSudoFx;
    }

    @Override
    public String getOsLogsFolderPath() {
        return logsFolderForSudoFx;
    }
}
