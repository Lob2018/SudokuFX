/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.model.Player;

/**
 * Mapper interface for converting between {@link Player} entity and {@link PlayerDto} data transfer
 * object.
 *
 * <p>Utilizes MapStruct to generate the mapping implementations automatically and is managed as a
 * Spring component. Additional mappers are used for nested objects and collections.
 */
@Mapper(
        componentModel = "spring",
        uses = {
            MapperUtils.class,
            IPlayerLanguageMapper.class,
            IBackgroundMapper.class,
            IMenuMapper.class,
            IGameMapper.class
        })
public interface IPlayerMapper {

    /**
     * Converts a {@link Player} entity to a {@link PlayerDto}.
     *
     * <p>The 'selectedGame' in PlayerDto is mapped from the first selected Game in the Player's
     * 'games' set. Other games are ignored.
     *
     * @param player the Player entity to map; must not be null.
     * @return a PlayerDto representing the data of the given Player entity.
     */
    @Mapping(target = "playerlanguageidDto", source = "player.playerlanguageid")
    @Mapping(target = "backgroundidDto", source = "player.backgroundid")
    @Mapping(target = "menuidDto", source = "player.menuid")
    @Mapping(
            target = "selectedGame",
            source = "player.games",
            qualifiedByName = "mapSelectedGameToDto")
    PlayerDto mapPlayerToDto(Player player);

    /**
     * Converts a {@link PlayerDto} to a {@link Player} entity.
     *
     * <p>The 'games' field is ignored during this mapping.
     *
     * @param dto the PlayerDto to map; must not be null.
     * @return a Player entity representing the data of the given PlayerDto.
     */
    @Mapping(target = "playerlanguageid", source = "dto.playerlanguageidDto")
    @Mapping(target = "backgroundid", source = "dto.backgroundidDto")
    @Mapping(target = "menuid", source = "dto.menuidDto")
    @Mapping(target = "games", ignore = true)
    Player mapPlayerDtoToPlayer(PlayerDto dto);
}
