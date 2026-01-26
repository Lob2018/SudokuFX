/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.os;

/**
 * Immutable record holding the initialized OS-specific folder paths (data and logs).
 *
 * <p>This record is created by {@link OsFolderInitializer} after validating and creating the
 * required directories. It implements {@link IOsFolder} to provide a unified access point for
 * retrieving these paths across the application.
 *
 * @param dataFolderPath the absolute path to the application data folder (never {@code null} or
 *     blank)
 * @param logsFolderPath the absolute path to the application logs folder (never {@code null} or
 *     blank)
 */
public record OsInitializedFolders(String dataFolderPath, String logsFolderPath)
        implements IOsFolder {

    /** {@inheritDoc} */
    @Override
    public String getOsDataFolderPath() {
        return dataFolderPath;
    }

    /** {@inheritDoc} */
    @Override
    public String getOsLogsFolderPath() {
        return logsFolderPath;
    }
}
