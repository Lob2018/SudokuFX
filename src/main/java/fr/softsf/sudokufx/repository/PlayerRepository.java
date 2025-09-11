/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.Player;

/**
 * Repository interface for {@link Player} entities.
 *
 * <p>Provides standard CRUD operations and custom query methods for Player objects using Spring
 * Data JPA.
 *
 * <p>Includes a specialized query to fetch the selected player along with their selected game and
 * all related associations to avoid lazy loading issues.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Finds the currently selected player along with their selected game and all related
     * associations.
     *
     * <p>This method performs a fetch join to eagerly load:
     *
     * <ul>
     *   <li>Player's games
     *   <li>Options
     *   <li>Each game's grid
     *   <li>Each game's level
     *   <li>Menu
     *   <li>PlayerLanguage
     * </ul>
     *
     * <p>Only players and games marked as selected are returned.
     *
     * @return a list of selected players with their selected games and all associations
     */
    @Query(
            "select distinct p from Player p "
                    + "join fetch p.games g "
                    + "join fetch p.optionsid "
                    + "join fetch g.gridid "
                    + "join fetch g.levelid "
                    + "join fetch p.menuid "
                    + "join fetch p.playerlanguageid "
                    + "where p.isselected = true and g.isselected = true")
    List<Player> findSelectedPlayerWithSelectedGame();
}
