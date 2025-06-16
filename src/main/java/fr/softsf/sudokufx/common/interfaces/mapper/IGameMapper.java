/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.model.Game;

/**
 * Mapper interface for converting between {@link Game} entity and {@link GameDto} data transfer
 * object.
 *
 * <p>Uses additional mappers {@link MapperUtils}, {@link IGridMapper}, {@link IGameLevelMapper} for
 * nested or complex mappings.
 */
@Mapper(
        componentModel = "spring",
        uses = {MapperUtils.class, IGridMapper.class, IGameLevelMapper.class})
public interface IGameMapper {

    /**
     * Converts a {@link Game} entity to a {@link GameDto}.
     *
     * @param game the Game entity to convert; must not be null.
     * @return a GameDto representing the given Game entity.
     */
    @Mapping(target = "grididDto", source = "game.gridid")
    @Mapping(target = "playerid", source = "game.playerid.playerid")
    @Mapping(target = "levelidDto", source = "game.levelid")
    GameDto mapGameToDto(Game game);

    /**
     * Converts a {@link GameDto} to a {@link Game} entity.
     *
     * @param dto the GameDto to convert; must not be null.
     * @return a Game entity representing the given GameDto.
     */
    @Mapping(target = "gridid", source = "dto.grididDto")
    @Mapping(target = "playerid", source = "dto.playerid", qualifiedByName = "mapPlayerIdToPlayer")
    @Mapping(target = "levelid", source = "dto.levelidDto")
    Game mapGameDtoToGame(GameDto dto);
}
