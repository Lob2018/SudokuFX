/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages file system operations, particularly folder deletion. This class implements the
 * IFileSystem interface.
 */
public final class FileSystemManager implements IFileSystem {

    private static final Logger LOG = LoggerFactory.getLogger(FileSystemManager.class);

    @Override
    public boolean deleteFolderRecursively(final Path folderPath, final String mustEndWithThat) {
        if (folderPath.endsWith(mustEndWithThat)) {
            LOG.info("▓▓▓▓ The directory path is correct :{}", folderPath);
            try (Stream<Path> stream = Files.walk(folderPath)) {
                stream.sorted(Comparator.reverseOrder()).forEach(this::deleteFile);
                return true;
            } catch (Exception e) {
                LOG.error("██ Exception catch from deleteFolder : {}", e.getMessage(), e);
            }
        } else {
            LOG.info("▓▓▓▓ The directory path is not correct :{}", folderPath);
        }
        return false;
    }

    /**
     * Attempts to delete a single file or directory.
     *
     * @param path The path of the file or directory to be deleted.
     * @return null if the file was successfully deleted, otherwise returns the Exception that
     *     occurred.
     */
    Throwable deleteFile(final Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            LOG.error("██ Failed to delete file: {}", path, e);
            return e;
        }
        return null;
    }
}
