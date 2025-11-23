/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import org.mapstruct.Mapper;

import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.model.Options;

/**
 * This interface defines methods for mapping objects of type {@link Options} to objects of type
 * {@link OptionsDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper(componentModel = "spring")
public interface IOptionsMapper {
    /**
     * Maps an Options object to a OptionsDto object.
     *
     * @param options the Options object to be mapped.
     * @return a OptionsDto object representing the data of the provided Options object.
     */
    OptionsDto mapOptionsToDto(Options options);

    /**
     * Maps a OptionsDto object to an Options object.
     *
     * @param dto the OptionsDto object to be mapped.
     * @return an Options object representing the data of the provided OptionsDto object.
     */
    Options mapOptionsDtoToOptions(OptionsDto dto);
}
