/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.dto.github;

/**
 * Data Transfer Object representing a GitHub tag (release).
 *
 * @param name the name of the tag (e.g., "v1.0.0")
 * @param zipball_url the URL to download the source as a zip archive
 * @param tarball_url the URL to download the source as a tarball archive
 * @param commit the commit associated with this tag
 * @param node_id the GitHub internal node ID for this tag
 */
public record TagDto(
        String name, String zipball_url, String tarball_url, CommitDto commit, String node_id) {}
