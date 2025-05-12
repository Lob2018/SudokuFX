/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.BackgroundDto;
import fr.softsf.sudokufx.model.Background;

/**
 * This interface defines methods for mapping objects of type {@link Background} to objects of type
 * {@link BackgroundDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper
public interface IBackgroundMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    IBackgroundMapper INSTANCE = Mappers.getMapper(IBackgroundMapper.class);

    /**
     * Maps a Background object to a BackgroundDto object.
     *
     * @param background the Background object to be mapped.
     * @return a BackgroundDto object representing the data of the provided Background object.
     */
    BackgroundDto mapBackgroundToDto(Background background);

    /**
     * Maps a BackgroundDto object to a Background object.
     *
     * @param dto the BackgroundDto object to be mapped.
     * @return a Background object representing the data of the provided BackgroundDto object.
     */
    Background mapBackgroundDtoToBackground(BackgroundDto dto);
}
