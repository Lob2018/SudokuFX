/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.model.Software;

/**
 * This interface defines methods for mapping objects of type {@link Software} to objects of type
 * {@link SoftwareDto} and vice versa. It uses MapStruct to automatically generate the
 * implementations of these mapping methods.
 */
@Mapper
public interface ISoftwareMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    ISoftwareMapper INSTANCE = Mappers.getMapper(ISoftwareMapper.class);

    /**
     * Maps a Software object to a SoftwareDto object.
     *
     * @param software the Software object to be mapped.
     * @return a SoftwareDto object representing the data of the provided Software object.
     */
    SoftwareDto mapSoftwareToDto(Software software);

    /**
     * Maps a SoftwareDto object to a Software object.
     *
     * @param dto the SoftwareDto object to be mapped.
     * @return a Software object representing the data of the provided SoftwareDto object.
     */
    Software mapSoftwareDtoToSoftware(SoftwareDto dto);
}
