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

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
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
