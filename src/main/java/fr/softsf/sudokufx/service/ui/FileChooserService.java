/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.enums.AppPaths;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.util.PathValidator;

/**
 * A Spring service that provides a reusable and type-safe file chooser utility for selecting
 * different kinds of files (e.g., images, audio).
 *
 * <p>The service handles:
 *
 * <ul>
 *   <li>Configuring extension filters based on file type
 *   <li>Setting an initial directory (user home by default)
 *   <li>Gracefully handling errors and returning Optional.empty() if cancelled or failed
 * </ul>
 */
@Service
public class FileChooserService {

    private static final Logger LOG = LoggerFactory.getLogger(FileChooserService.class);

    /** Supported file types for the file chooser. */
    public enum FileType {
        IMAGE,
        AUDIO
    }

    /**
     * Opens a FileChooser dialog for the specified file type.
     *
     * <p>If the user selects a valid file, it is returned wrapped in an {@link Optional}. If the
     * user cancels the dialog or if an error occurs, {@link Optional#empty()} is returned.
     *
     * @param ownerStage the parent {@link Stage} for the dialog; must not be {@code null}
     * @param type the type of file to choose (IMAGE or AUDIO); must not be {@code null}
     * @return an {@link Optional} containing the selected file, or {@link Optional#empty()} if the
     *     user cancels or if an error occurs
     * @throws NullPointerException if {@code ownerStage} or {@code type} is {@code null}
     */
    public Optional<File> chooseFile(Stage ownerStage, FileType type) {
        Objects.requireNonNull(ownerStage, "Stage must not be null");
        Objects.requireNonNull(type, "FileType must not be null");
        try {
            FileChooser fileChooser = new FileChooser();
            File initialDir =
                    PathValidator.INSTANCE.validateDirectory(Path.of(AppPaths.USER_HOME.getPath()));
            fileChooser.setInitialDirectory(initialDir);
            fileChooser.getExtensionFilters().add(getFilter(type));
            return Optional.ofNullable(fileChooser.showOpenDialog(ownerStage));
        } catch (Exception e) {
            LOG.error(
                    "██ Exception error opening file chooser for {}: {}", type, e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Returns the appropriate {@link FileChooser.ExtensionFilter} for the given file type.
     *
     * @param type the file type (IMAGE or AUDIO); must not be null
     * @return a FileChooser.ExtensionFilter restricting files to the allowed extensions
     * @throws NullPointerException if {@code type} is null
     */
    private FileChooser.ExtensionFilter getFilter(FileType type) {
        Objects.requireNonNull(type, "FileType must not be null");
        return switch (type) {
            case IMAGE ->
                    new FileChooser.ExtensionFilter(
                            I18n.INSTANCE.getValue("filechooser.extension.image"),
                            "*.jpg",
                            "*.jpeg",
                            "*.png",
                            "*.bmp");
            case AUDIO ->
                    new FileChooser.ExtensionFilter(
                            I18n.INSTANCE.getValue("filechooser.extension.audio"),
                            "*.mp3",
                            "*.wav",
                            "*.aac",
                            "*.m4a",
                            "*.aif",
                            "*.aiff");
        };
    }
}
