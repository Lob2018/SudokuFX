/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.model.Player;

/**
 * This interface defines methods for mapping objects of type {@link Player} to objects of type
 * {@link PlayerDto} and vice versa. It uses MapStruct to automatically generate the implementations
 * of these mapping methods.
 */
@Mapper(
        componentModel = "spring",
        uses = {
            MapperUtils.class,
            IPlayerLanguageMapper.class,
            IBackgroundMapper.class,
            IMenuMapper.class
        })
public interface IPlayerMapper {

    /**
     * Maps a Player object to a PlayerDto object.
     *
     * @param player the Player object to be mapped.
     * @return a PlayerDto object representing the data of the provided Player object.
     */
    @Mapping(target = "playerlanguageidDto", source = "player.playerlanguageid")
    @Mapping(target = "backgroundidDto", source = "player.backgroundid")
    @Mapping(target = "menuidDto", source = "player.menuid")
    @Mapping(target = "gamesid", source = "player.games", qualifiedByName = "extractGameIds")
    PlayerDto mapPlayerToDto(Player player);

    /**
     * Maps a PlayerDto object to a Player object.
     *
     * @param dto the PlayerDto object to be mapped.
     * @return a Player object representing the data of the provided PlayerDto object.
     */
    @Mapping(target = "playerlanguageid", source = "dto.playerlanguageidDto")
    @Mapping(target = "backgroundid", source = "dto.backgroundidDto")
    @Mapping(target = "menuid", source = "dto.menuidDto")
    @Mapping(target = "games", source = "dto.gamesid", qualifiedByName = "mapGameIdsToGames")
    Player mapPlayerDtoToPlayer(PlayerDto dto);
}
