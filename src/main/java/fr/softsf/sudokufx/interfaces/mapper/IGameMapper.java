/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.model.Game;

/**
 * This interface defines methods for mapping objects of type {@link Game} to objects of type {@link
 * GameDto} and vice versa. It uses MapStruct to automatically generate the implementations of these
 * mapping methods.
 */
@Mapper(uses = {IPlayerMapper.class, IGridMapper.class, IGameLevelMapper.class})
public interface IGameMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    IGameMapper INSTANCE = Mappers.getMapper(IGameMapper.class);

    /**
     * Maps a Game object to a GameDto object.
     *
     * @param game the Game object to be mapped.
     * @return a GameDto object representing the data of the provided Game object.
     */
    @Mapping(target = "gameid", source = "game.gameid")
    @Mapping(target = "grididDto", source = "game.gridid")
    @Mapping(target = "playeridDto", source = "game.playerid")
    @Mapping(target = "levelidDto", source = "game.levelid")
    GameDto mapGameToDto(Game game);

    /**
     * Maps a GameDto object to a Game object.
     *
     * @param dto the GameDto object to be mapped.
     * @return a Game object representing the data of the provided GameDto object.
     */
    @Mapping(target = "gameid", source = "dto.gameid")
    @Mapping(target = "gridid", source = "dto.grididDto")
    @Mapping(target = "playerid", source = "dto.playeridDto")
    @Mapping(target = "levelid", source = "dto.levelidDto")
    Game mapGameDtoToGame(GameDto dto);
}
