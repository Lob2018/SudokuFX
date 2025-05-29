/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.model.Game;

@Mapper(
        componentModel = "spring",
        uses = {MapperUtils.class, IGridMapper.class, IGameLevelMapper.class})
public interface IGameMapper {

    /**
     * Mappe un objet Game en GameDto.
     *
     * @param game l’objet Game à mapper.
     * @return un GameDto représentant les données du Game.
     */
    @Mapping(target = "grididDto", source = "game.gridid")
    @Mapping(target = "playerid", source = "game.playerid.playerid")
    @Mapping(target = "levelidDto", source = "game.levelid")
    GameDto mapGameToDto(Game game);

    /**
     * Mappe un GameDto en Game.
     *
     * @param dto le GameDto à mapper.
     * @return un Game représentant les données du GameDto.
     */
    @Mapping(target = "gridid", source = "dto.grididDto")
    @Mapping(target = "playerid", source = "dto.playerid", qualifiedByName = "mapPlayerIdToPlayer")
    @Mapping(target = "levelid", source = "dto.levelidDto")
    Game mapGameDtoToGame(GameDto dto);
}
