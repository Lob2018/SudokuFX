/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.service.business;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerLanguageMapper;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.model.PlayerLanguage;
import fr.softsf.sudokufx.repository.PlayerLanguageRepository;
import fr.softsf.sudokufx.service.business.PlayerLanguageService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PlayerLanguageServiceUTest {

    private PlayerLanguageRepository repository;
    private JakartaValidator validator;
    private IPlayerLanguageMapper mapper;
    private PlayerLanguageService service;

    @BeforeEach
    void setUp() {
        repository = mock(PlayerLanguageRepository.class);
        validator = mock(JakartaValidator.class);
        mapper = mock(IPlayerLanguageMapper.class);
        service = new PlayerLanguageService(repository, validator, mapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FR", "EN"})
    void givenExistingIso_whenGetByIso_thenReturnsValidatedDto(String iso) {
        PlayerLanguage entity = PlayerLanguage.builder().playerlanguageid(1L).iso(iso).build();
        PlayerLanguageDto dto = new PlayerLanguageDto(1L, iso);
        when(repository.findByIso(iso)).thenReturn(Optional.of(entity));
        when(mapper.mapPlayerLanguageToDto(entity)).thenReturn(dto);
        when(validator.validateOrThrow(dto)).thenReturn(dto);
        PlayerLanguageDto result = service.getByIso(iso);
        assertThat(result).isEqualTo(dto);
        verify(repository).findByIso(iso);
        verify(mapper).mapPlayerLanguageToDto(entity);
        verify(validator).validateOrThrow(dto);
    }

    @Test
    void givenNonExistingIso_whenGetByIso_thenThrowsException() {
        when(repository.findByIso("XX")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByIso("XX"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("PlayerLanguage not found for ISO: XX");
        verify(repository).findByIso("XX");
        verifyNoInteractions(mapper, validator);
    }
}
