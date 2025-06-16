/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.interfaces.mapper;

import org.mapstruct.Mapper;

import fr.softsf.sudokufx.dto.BackgroundDto;
import fr.softsf.sudokufx.model.Background;

/**
 * This interface defines methods for mapping objects of type {@link Background} to objects of type
 * {@link BackgroundDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper(componentModel = "spring")
public interface IBackgroundMapper {
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
