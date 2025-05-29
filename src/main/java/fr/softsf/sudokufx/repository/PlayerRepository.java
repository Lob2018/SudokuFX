/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    //    @Query(value = "SELECT * FROM player WHERE isselected = true LIMIT 1", nativeQuery = true)
    @Query(value = "SELECT * FROM player WHERE isselected = true LIMIT 1", nativeQuery = true)
    Optional<Player> findFirstSelectedPlayer();

    @Query(
"""
    select distinct p from Player p
    left join fetch p.playerlanguageid
    left join fetch p.backgroundid
    left join fetch p.menuid
    left join fetch p.games
    where p.isselected = true
""")
    Optional<Player> findFirstSelectedPlayerWithAllRelations();
}
