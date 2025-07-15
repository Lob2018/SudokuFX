/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

/**
 * Factory class for creating and managing LINUX OS-specific folders for the SudoFX application.
 * Implements the OsDynamicFolders.IOsFoldersFactory interface.
 */
final class LinuxFolderFactory implements IOsFolderFactory {

    private final String logsFolderForSudoFx;
    private final String dataFolderForSudoFx;

    /**
     * Constructor that initializes the data and logs folder paths.
     *
     * <p>The given paths are validated and the folders created by {@link
     * OsFolderInitializer#initializeFolders(String, String)}, which returns an {@link
     * OsInitializedFolders} record containing the created folder paths. This method may throw
     * exceptions if paths are invalid or folder creation fails.
     *
     * @param dataFolderPath The intended path for the data folder
     * @param logsFolderPath The intended path for the logs folder
     */
    public LinuxFolderFactory(String dataFolderPath, String logsFolderPath) {
        final OsInitializedFolders osInitializedFolders =
                OsFolderInitializer.INSTANCE.initializeFolders(dataFolderPath, logsFolderPath);
        dataFolderForSudoFx = osInitializedFolders.dataFolderPath();
        logsFolderForSudoFx = osInitializedFolders.logsFolderPath();
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
