/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.model.Menu;

/**
 * This interface defines methods for mapping objects of type {@link Menu} to objects of type {@link
 * MenuDto} and vice versa. It uses MapStruct to automatically generate the implementations of these
 * mapping methods.
 */
@Mapper
public interface IMenuMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    IMenuMapper INSTANCE = Mappers.getMapper(IMenuMapper.class);

    /**
     * Maps a Menu object to a MenuDto object.
     *
     * @param menu the Menu object to be mapped.
     * @return a MenuDto object representing the data of the provided Menu object.
     */
    MenuDto mapMenuToDto(Menu menu);

    /**
     * Maps a MenuDto object to a Menu object.
     *
     * @param dto the MenuDto object to be mapped.
     * @return a Menu object representing the data of the provided MenuDto object.
     */
    Menu mapMenuDtoToMenu(MenuDto dto);
}
