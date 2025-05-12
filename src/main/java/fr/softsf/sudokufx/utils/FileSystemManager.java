/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.utils;

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

    private static final Logger log = LoggerFactory.getLogger(FileSystemManager.class);

    @Override
    public boolean deleteFolderRecursively(final Path folderPath, final String mustEndWithThat) {
        if (folderPath.endsWith(mustEndWithThat)) {
            log.info("▓▓▓▓ The directory path is correct :{}", folderPath);
            try (Stream<Path> stream = Files.walk(folderPath)) {
                stream.sorted(Comparator.reverseOrder()).forEach(this::deleteFile);
                return true;
            } catch (Exception e) {
                log.error("██ Exception catch from deleteFolder : {}", e.getMessage(), e);
            }
        } else {
            log.info("▓▓▓▓ The directory path is not correct :{}", folderPath);
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
            log.error("██ Failed to delete file: {}", path, e);
            return e;
        }
        return null;
    }
}
