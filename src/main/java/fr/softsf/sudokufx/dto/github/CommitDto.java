/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto.github;

/**
 * Data Transfer Object representing a commit in GitHub.
 *
 * @param sha the SHA hash of the commit
 * @param url the API URL to access the commit details
 */
public record CommitDto(String sha, String url) {}
