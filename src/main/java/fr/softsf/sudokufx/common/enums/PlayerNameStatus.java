/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum representing the validation status of a player name. */
public enum PlayerNameStatus {
    /** The player name is empty or consists only of whitespace. */
    EMPTY,

    /** The player name conforms to all naming conventions and is acceptable. */
    VALID,

    /** The player name violates length or character constraints. */
    INVALID,

    /** The player name is already in use. */
    UNAVAILABLE
}
