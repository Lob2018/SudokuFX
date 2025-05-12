/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.model.Player;

/**
 * This interface defines methods for mapping objects of type {@link Player} to objects of type
 * {@link PlayerDto} and vice versa. It uses MapStruct to automatically generate the implementations
 * of these mapping methods.
 */
@Mapper(
        uses = {
            IGameMapper.class,
            IPlayerLanguageMapper.class,
            IBackgroundMapper.class,
            IMenuMapper.class
        })
public interface IPlayerMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    IPlayerMapper INSTANCE = Mappers.getMapper(IPlayerMapper.class);

    /**
     * Maps a Player object to a PlayerDto object.
     *
     * @param player the Player object to be mapped.
     * @return a PlayerDto object representing the data of the provided Player object.
     */
    @Mapping(target = "playerid", source = "player.playerid")
    @Mapping(target = "playerlanguageidDto", source = "player.playerlanguageid")
    @Mapping(target = "backgroundidDto", source = "player.backgroundid")
    @Mapping(target = "menuidDto", source = "player.menuid")
    @Mapping(target = "gamesDto", source = "player.games")
    PlayerDto mapPlayerToDto(Player player);

    /**
     * Maps a PlayerDto object to a Player object.
     *
     * @param dto the PlayerDto object to be mapped.
     * @return a Player object representing the data of the provided PlayerDto object.
     */
    @Mapping(target = "playerid", source = "dto.playerid")
    @Mapping(target = "playerlanguageid", source = "dto.playerlanguageidDto")
    @Mapping(target = "backgroundid", source = "dto.backgroundidDto")
    @Mapping(target = "menuid", source = "dto.menuidDto")
    @Mapping(target = "games", source = "dto.gamesDto")
    Player mapPlayerDtoToPlayer(PlayerDto dto);
}
