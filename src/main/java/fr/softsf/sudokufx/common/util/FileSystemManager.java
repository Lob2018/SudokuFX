/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

import static fr.softsf.sudokufx.enums.Paths.DATA_FOLDER;

/**
 * Manages file system operations related to the application's data folder.
 *
 * <p>Implements {@link IFileSystem} to recursively delete the application's data directory,
 * ensuring path validation and detailed logging. Throws IllegalArgumentException via
 * ExceptionTools if input paths are null.
 */
public final class FileSystemManager implements IFileSystem {

    private static final Logger LOG = LoggerFactory.getLogger(FileSystemManager.class);

    @Override
    public boolean deleteDataFolderRecursively(final Path folderPath) {
        if (Objects.isNull(folderPath)) {
            throw ExceptionTools.INSTANCE.createAndLogIllegalArgument(
                    "The folderPath mustn't be null");
        }
        if (folderPath.endsWith(DATA_FOLDER.getPath())) {
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
     * Deletes a single file or directory.
     *
     * @param path the path to delete; must not be null
     * @return {@code null} if deletion succeeded; otherwise the exception thrown
     * @throws IllegalArgumentException if {@code path} is null
     */
    Throwable deleteFile(final Path path) {
        if (Objects.isNull(path)) {
            throw ExceptionTools.INSTANCE.createAndLogIllegalArgument("The path mustn't be null");
        }
        try {
            Files.delete(path);
        } catch (Exception e) {
            LOG.error("██ Failed to delete file: {}", path, e);
            return e;
        }
        return null;
    }
}
