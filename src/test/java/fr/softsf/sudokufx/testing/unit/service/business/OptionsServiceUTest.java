/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.service.business;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IOptionsMapper;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.model.Options;
import fr.softsf.sudokufx.repository.OptionsRepository;
import fr.softsf.sudokufx.service.business.OptionsService;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OptionsServiceUTest {

    private OptionsRepository optionsRepository;
    private IOptionsMapper optionsMapper;
    private JakartaValidator jakartaValidator;
    private OptionsService optionsService;

    @BeforeEach
    void setUp() {
        optionsRepository = mock(OptionsRepository.class);
        optionsMapper = mock(IOptionsMapper.class);
        jakartaValidator = mock(JakartaValidator.class);
        optionsService = new OptionsService(optionsRepository, optionsMapper, jakartaValidator);
    }

    @Test
    void givenValidOptionsDto_whenUpdateOptions_thenReturnsUpdatedDto() {
        OptionsDto dto =
                new OptionsDto(1L, "#FFFFFF", "path/image.png", "path/song.mp3", true, false);
        Options existingOptions = mock(Options.class);

        when(optionsRepository.findById(dto.optionsid())).thenReturn(Optional.of(existingOptions));
        when(optionsRepository.save(existingOptions)).thenReturn(existingOptions);
        when(optionsMapper.mapOptionsToDto(existingOptions)).thenReturn(dto);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);

        OptionsDto result = optionsService.updateOptions(dto);

        assertNotNull(result);
        assertEquals("#FFFFFF", result.hexcolor());
        verify(existingOptions).setHexcolor(dto.hexcolor());
        verify(existingOptions).setImagepath(dto.imagepath());
        verify(existingOptions).setSongpath(dto.songpath());
        verify(existingOptions).setOpaque(dto.opaque());
        verify(existingOptions).setMuted(dto.muted());
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenNullOptionsDto_whenUpdateOptions_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> optionsService.updateOptions(null));
    }

    @Test
    void givenNonExistentOptions_whenUpdateOptions_thenThrowsIllegalArgumentException() {
        OptionsDto dto = new OptionsDto(999L, "#FFFFFF", "image.png", "song.mp3", true, false);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);
        when(optionsRepository.findById(999L)).thenReturn(Optional.empty());
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> optionsService.updateOptions(dto));
        assertTrue(ex.getMessage().contains("Options not found"));
    }

    @Test
    void givenValidationFails_whenUpdateOptions_thenThrowsConstraintViolationException() {
        OptionsDto dto = new OptionsDto(1L, "#FFFFFF", "image.png", "song.mp3", true, false);
        when(jakartaValidator.validateOrThrow(dto))
                .thenThrow(new ConstraintViolationException("Validation failed", Set.of()));

        assertThrows(ConstraintViolationException.class, () -> optionsService.updateOptions(dto));
    }
}
