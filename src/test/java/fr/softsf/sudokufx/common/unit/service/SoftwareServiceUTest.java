/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.unit.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.interfaces.mapper.ISoftwareMapper;
import fr.softsf.sudokufx.model.Software;
import fr.softsf.sudokufx.repository.SoftwareRepository;
import fr.softsf.sudokufx.service.SoftwareService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SoftwareServiceUTest {

    private Software software;
    private final String currentVersion = "1.0.0";
    private final String lastVersion = "1.0.1";

    private final ISoftwareMapper softwareMapper = Mappers.getMapper(ISoftwareMapper.class);
    private SoftwareService softwareService;
    @Mock private SoftwareRepository softwareRepository;

    @BeforeEach
    void setUp() {
        softwareService = new SoftwareService(softwareRepository, softwareMapper);
        software =
                Software.builder()
                        .softwareid(1L)
                        .currentversion(currentVersion)
                        .lastversion(lastVersion)
                        .createdat(LocalDateTime.now())
                        .updatedat(LocalDateTime.now())
                        .build();
        assert software != null;
    }

    @Test
    void givenSoftware_whenGetSoftware_thenSoftwareFound() {
        Mockito.when(softwareRepository.findFirstSoftware()).thenReturn(Optional.of(software));
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertTrue(softwareDto.isPresent());
        assertThat(softwareDto.get().currentversion()).isEqualTo(currentVersion);
    }

    @Test
    void givenSoftware_whenGetSoftware_thenNoSoftwareFound() {
        Mockito.when(softwareRepository.findFirstSoftware()).thenReturn(Optional.empty());
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertFalse(softwareDto.isPresent());
    }

    @Test
    void givenSoftware_whenGetSoftware_thenDatabaseError() {
        Mockito.when(softwareRepository.findFirstSoftware())
                .thenThrow(new RuntimeException("Database error"));
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertFalse(softwareDto.isPresent());
    }

    @Test
    void givenSoftwareToUpdate_whenUpdateSoftware_thenSoftwareUpdated() {
        Mockito.when(softwareRepository.save(Mockito.any(Software.class))).thenReturn(software);
        software.setCurrentversion(lastVersion);
        software.setLastversion("1.0.2");
        software.setUpdatedat(LocalDateTime.now());
        SoftwareDto softwareDto = softwareMapper.mapSoftwareToDto(software);
        Optional<SoftwareDto> softwareDtoUpdated = softwareService.updateSoftware(softwareDto);
        assertTrue(softwareDtoUpdated.isPresent());
        assertThat(softwareDtoUpdated.get().currentversion()).isEqualTo(lastVersion);
    }

    @Test
    void givenSoftwareToUpdate_whenUpdateSoftware_thenDatabaseError() {
        Mockito.when(softwareRepository.save(Mockito.any(Software.class)))
                .thenThrow(new RuntimeException("Database error"));
        software.setCurrentversion(lastVersion);
        software.setLastversion("1.0.2");
        software.setUpdatedat(LocalDateTime.now());
        SoftwareDto softwareDto = softwareMapper.mapSoftwareToDto(software);
        Optional<SoftwareDto> softwareDtoUpdated = softwareService.updateSoftware(softwareDto);
        assertFalse(softwareDtoUpdated.isPresent());
    }
}
