/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import org.mapstruct.Mapper;

import fr.softsf.sudokufx.dto.GameLevelDto;
import fr.softsf.sudokufx.model.GameLevel;

/**
 * This interface defines methods for mapping objects of type {@link GameLevel} to objects of type
 * {@link GameLevelDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper(componentModel = "spring")
public interface IGameLevelMapper {
    /**
     * Maps a GameLevel object to a GameLevelDto object.
     *
     * @param gameLevel the Options object to be mapped.
     * @return a GameLevelDto object representing the data of the provided GameLevel object.
     */
    GameLevelDto mapGameLevelToDto(GameLevel gameLevel);

    /**
     * Maps a GameLevelDto object to a GameLevel object.
     *
     * @param dto the GameLevelDto object to be mapped.
     * @return a GameLevel object representing the data of the provided GameLevelDto object.
     */
    GameLevel mapGameLevelDtoToGameLevel(GameLevelDto dto);
}
