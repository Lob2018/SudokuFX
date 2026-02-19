/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static fr.softsf.sudokufx.common.enums.AppPaths.DATA_FOLDER;

/**
 * Handles recursive deletion of the application's local data storage.
 *
 * <p>Implements {@link IUserDataPurger} using a fail-fast approach via {@link PathValidator}.
 * Validation ensures the target is a directory and belongs to the authorized data scope.
 */
public final class LocalUserDataPurger implements IUserDataPurger {

    private static final Logger LOG = LoggerFactory.getLogger(LocalUserDataPurger.class);

    /**
     * Executes a recursive purge of the specified directory.
     *
     * @param folderPath The directory path to delete; must be a valid data folder.
     * @return {@code true} if the operation completed successfully, {@code false} otherwise.
     */
    @Override
    public boolean deleteDataFolderRecursively(final Path folderPath) {
        PathValidator.INSTANCE.validateDirectory(folderPath);
        if (folderPath.endsWith(DATA_FOLDER.getPath())) {
            LOG.info("▓▓▓▓ Authorized data purge initiated: {}", folderPath);
            try (Stream<Path> stream = Files.walk(folderPath)) {
                stream.sorted(Comparator.reverseOrder()).forEach(this::deleteFile);
                return true;
            } catch (Exception e) {
                LOG.error("██ Critical failure during recursive purge: {}", e.getMessage(), e);
            }
        } else {
            LOG.warn("▓▓▓▓ Purge rejected: path {} is out of authorized scope", folderPath);
        }
        return false;
    }

    /**
     * Deletes a filesystem entry.
     *
     * @param path The path to remove; must not be null.
     * @return {@code null} if successful, the caught {@link Throwable} otherwise.
     */
    Throwable deleteFile(final Path path) {
        PathValidator.INSTANCE.validateExists(path);
        try {
            Files.delete(path);
        } catch (Exception e) {
            LOG.error("██ IO failure deleting: {}", path, e);
            return e;
        }
        return null;
    }
}
