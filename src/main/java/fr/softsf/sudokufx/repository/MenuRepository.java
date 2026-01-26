/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.Menu;

/**
 * Repository interface for {@link Menu} entities.
 *
 * <p>Provides standard CRUD operations and query methods for Menu objects using Spring Data JPA.
 *
 * <p>The primary key of Menu is of type {@link Byte}.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Byte> {}
