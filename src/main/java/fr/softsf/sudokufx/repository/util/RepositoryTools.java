/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.repository.util;

import org.springframework.data.repository.CrudRepository;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/** Utility for standardized database entity retrieval. */
public enum RepositoryTools {
    INSTANCE;

    /**
     * Retrieves an entity by its identifier or throws an exception if missing.
     *
     * @param <T> entity type
     * @param <K> identifier type
     * @param repo repository instance
     * @param k identifier value
     * @param entityName descriptive name for error logs
     * @return the managed entity
     * @throws IllegalArgumentException if not found
     */
    public <T, K> T findOrThrow(CrudRepository<T, K> repo, K k, String entityName) {
        return repo.findById(k)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        entityName + " not found: " + k));
    }
}
