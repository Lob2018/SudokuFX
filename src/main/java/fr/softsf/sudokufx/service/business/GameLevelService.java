/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.business;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.model.GameLevel;
import fr.softsf.sudokufx.repository.GameLevelRepository;

/** Service managing {@link fr.softsf.sudokufx.model.GameLevel} entities. */
@Service
public class GameLevelService {

    private final GameLevelRepository gameLevelRepository;

    /**
     * Constructs a new {@code GameLevelService} with the required repository dependency.
     *
     * @param gameLevelRepository the repository for {@link GameLevel} entities
     */
    public GameLevelService(GameLevelRepository gameLevelRepository) {
        this.gameLevelRepository = gameLevelRepository;
    }

    /**
     * Retrieves the GameLevel entity by its business level value. Used internally by other services
     * to manage relationships.
     *
     * @param levelValue the numeric level identifier
     * @return the managed GameLevel entity
     * @throws IllegalArgumentException if the level is not found
     */
    @Transactional(readOnly = true)
    public GameLevel findByLevelOrThrow(byte levelValue) {
        return gameLevelRepository
                .findByLevel(levelValue)
                .orElseThrow(
                        () ->
                                ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                        "GameLevel not found for level value: " + levelValue));
    }
}
