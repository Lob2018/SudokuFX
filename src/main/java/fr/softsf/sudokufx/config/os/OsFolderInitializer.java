/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.os;

import java.io.File;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.exception.FolderCreationException;

/**
 * Utility enum to initialize required application folders (data and logs). Provides static-like
 * methods to ensure these directories exist before usage. Designed to be non-instantiable outside
 * the enum instance.
 */
public enum OsFolderInitializer {
    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(OsFolderInitializer.class);

    /**
     * Creates data and logs folders at the specified paths. Validates that folder paths are neither
     * null nor blank.
     *
     * @param dataFolderPath path to the data folder to create
     * @param logsFolderPath path to the logs folder to create
     * @return an OsInitializedFolders object containing the created folder paths
     * @throws IllegalArgumentException if either folder path is null or blank
     * @throws RuntimeException if folder creation fails
     */
    OsInitializedFolders initializeFolders(String dataFolderPath, String logsFolderPath) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                dataFolderPath,
                "dataFolderPath must not be null or blank, but was: " + dataFolderPath);
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                logsFolderPath,
                "logsFolderPath must not be null or blank, but was: " + logsFolderPath);
        createFolder(new File(dataFolderPath));
        createFolder(new File(logsFolderPath));
        return new OsInitializedFolders(dataFolderPath, logsFolderPath);
    }

    /**
     * Creates the specified folder if it does not already exist.
     *
     * @param folder the folder to create; must not be null
     * @throws IllegalArgumentException if folder is null
     * @throws FolderCreationException if folder creation fails or a security error occurs
     */
    void createFolder(final File folder) {
        if (Objects.isNull(folder)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The folder mustn't be null");
        }
        try {
            if (folder.exists()) {
                LOG.info("▓▓ Folder already exists: {}", folder.getAbsolutePath());
            } else {
                if (folder.mkdirs()) {
                    LOG.info("▓▓ Folder created successfully: {}", folder.getAbsolutePath());
                } else {
                    throw ExceptionTools.INSTANCE.logAndInstantiateFolderCreation(
                            "Failed to create folder with mkdirs(): " + folder.getAbsolutePath());
                }
            }
        } catch (SecurityException e) {
            throw ExceptionTools.INSTANCE.logAndInstantiateFolderCreation(
                    "Security error when creating folder: " + folder.getAbsolutePath(), e);
        } catch (Exception e) {
            throw ExceptionTools.INSTANCE.logAndInstantiateFolderCreation(
                    "Error when creating folder: " + folder.getAbsolutePath(), e);
        }
    }
}
