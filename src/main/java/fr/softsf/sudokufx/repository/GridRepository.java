/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.softsf.sudokufx.model.Grid;

/**
 * Repository interface for {@link Grid} entities.
 *
 * <p>Provides standard CRUD operations and query methods for Grid objects using Spring Data JPA.
 *
 * <p>The primary key of Grid is of type {@link Long}.
 */
@Repository
public interface GridRepository extends JpaRepository<Grid, Long> {}
