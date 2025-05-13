/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.interfaces.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.model.Grid;

/**
 * This interface defines methods for mapping objects of type {@link Grid} to objects of type {@link
 * GridDto} and vice versa. It uses MapStruct to automatically generate the implementations of these
 * mapping methods.
 */
@Mapper
public interface IGridMapper {
    /**
     * This instance is created by MapStruct and provides access to the mapping methods defined in
     * this interface.
     */
    IGridMapper INSTANCE = Mappers.getMapper(IGridMapper.class);

    /**
     * Maps a Grid object to a GridDto object.
     *
     * @param grid the Grid object to be mapped.
     * @return a GridDto object representing the data of the provided Grid object.
     */
    GridDto mapGridToDto(Grid grid);

    /**
     * Maps a GridDto object to a Grid object.
     *
     * @param dto the GridDto object to be mapped.
     * @return a Grid object representing the data of the provided GridDto object.
     */
    Grid mapGridDtoToGrid(GridDto dto);
}
