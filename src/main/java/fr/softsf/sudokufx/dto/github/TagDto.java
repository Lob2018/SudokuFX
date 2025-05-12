/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.dto.github;

public record TagDto(
        String name, String zipball_url, String tarball_url, CommitDto commit, String node_id) {}
