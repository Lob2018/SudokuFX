/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;

import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.model.Menu;

/**
 * This interface defines methods for mapping objects of type {@link Menu} to objects of type {@link
 * MenuDto} and vice versa. It uses MapStruct to automatically generate the implementations of these
 * mapping methods.
 */
@Mapper(componentModel = "spring")
public interface IMenuMapper {
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
