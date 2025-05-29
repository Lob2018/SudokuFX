/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;

import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.model.PlayerLanguage;

/**
 * This interface defines methods for mapping objects of type {@link PlayerLanguage} to objects of
 * type {@link PlayerLanguageDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper(componentModel = "spring")
public interface IPlayerLanguageMapper {
    /**
     * Maps a PlayerLanguage object to a PlayerLanguageDto object.
     *
     * @param playerLanguage the PlayerLanguage object to be mapped.
     * @return a PlayerLanguageDto object representing the data of the provided PlayerLanguage
     *     object.
     */
    PlayerLanguageDto mapPlayerLanguageToDto(PlayerLanguage playerLanguage);

    /**
     * Maps a PlayerLanguageDto object to a PlayerLanguage object.
     *
     * @param dto the PlayerLanguageDto object to be mapped.
     * @return a PlayerLanguage object representing the data of the provided PlayerLanguageDto
     *     object.
     */
    PlayerLanguage mapPlayerLanguageDtoToPlayerLanguage(PlayerLanguageDto dto);
}
