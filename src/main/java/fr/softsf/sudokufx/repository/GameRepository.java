/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.Game;
import fr.softsf.sudokufx.model.Player;

/**
 * Repository interface for {@link Game} entities.
 *
 * <p>Provides standard CRUD operations and query methods for Game objects using Spring Data JPA.
 *
 * <p>The primary key of Game is of type {@link Long}.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    /**
     * Retrieves all non-selected games for a specific player.
     *
     * <p>Uses {@code JOIN FETCH} to eagerly load the associated {@link Player} entity, minimizing
     * N+1 performance overhead.
     *
     * @param playerId the unique identifier of the target player
     * @param sort sorting criteria for the resulting game list
     * @return a list of filtered games matching the selection criteria
     */
    @Query(
            "select distinct g from Game g "
                    + "join fetch g.playerid p "
                    + "where p.playerid = :playerId and g.selected = false")
    List<Game> findAllUnselected(@Param("playerId") long playerId, Sort sort);
}
