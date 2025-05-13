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

import fr.softsf.sudokufx.model.Software;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {
    @Query(value = "SELECT * FROM software LIMIT 1", nativeQuery = true)
    Optional<Software> findFirstSoftware();
}
