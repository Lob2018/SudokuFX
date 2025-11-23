/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.PlayerLanguage;

/**
 * Repository interface for {@link PlayerLanguage} entities.
 *
 * <p>Provides standard CRUD operations and query methods for PlayerLanguage objects using Spring
 * Data JPA.
 *
 * <p>The primary key of PlayerLanguage is of type {@link Long}.
 */
@Repository
public interface PlayerLanguageRepository extends JpaRepository<PlayerLanguage, Long> {
    Optional<PlayerLanguage> findByIso(String iso);
}
