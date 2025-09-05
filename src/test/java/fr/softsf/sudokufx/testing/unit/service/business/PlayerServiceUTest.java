/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.service.business;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.common.interfaces.mapper.IPlayerMapper;
import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.model.Player;
import fr.softsf.sudokufx.repository.PlayerRepository;
import fr.softsf.sudokufx.service.business.PlayerService;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceUTest {

    private PlayerRepository playerRepository;
    private IPlayerMapper playerMapper;
    private JakartaValidator jakartaValidator;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerMapper = mock(IPlayerMapper.class);
        jakartaValidator = mock(JakartaValidator.class);
        playerService = new PlayerService(playerRepository, playerMapper, jakartaValidator);
    }

    @Test
    void givenValidPlayer_whenGetPlayer_thenReturnsDto() {
        Player mockEntity = mock(Player.class);
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", false, true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        new GameDto(
                                1L,
                                new GridDto(1L, "", "", (byte) 100),
                                1L,
                                new GameLevelDto((byte) 1, (byte) 1),
                                true,
                                LocalDateTime.now(),
                                LocalDateTime.now()),
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        when(playerRepository.findSelectedPlayerWithSelectedGame()).thenReturn(List.of(mockEntity));
        when(playerMapper.mapPlayerToDto(mockEntity)).thenReturn(dto);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto); // important

        PlayerDto result = playerService.getPlayer();

        assertNotNull(result);
        assertEquals("Jean", result.name());
    }

    @Test
    void givenNoPlayerFound_whenGetPlayer_thenThrowsException() {
        when(playerRepository.findSelectedPlayerWithSelectedGame()).thenReturn(List.of());

        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.getPlayer());

        assertTrue(ex.getMessage().contains("No selected player"));
    }

    @Test
    void givenValidationFails_whenGetPlayer_thenThrowsConstraintViolationException() {
        Player player = mock(Player.class);
        PlayerDto invalidDto = mock(PlayerDto.class);

        when(playerRepository.findSelectedPlayerWithSelectedGame()).thenReturn(List.of(player));
        when(playerMapper.mapPlayerToDto(player)).thenReturn(invalidDto);
        when(invalidDto.selectedGame())
                .thenReturn(
                        new GameDto(
                                1L,
                                new GridDto(1L, "", "", (byte) 100),
                                1L,
                                new GameLevelDto((byte) 1, (byte) 1),
                                true,
                                LocalDateTime.now(),
                                LocalDateTime.now()));

        when(jakartaValidator.validateOrThrow(invalidDto))
                .thenThrow(new ConstraintViolationException("Validation failed", Set.of()));

        assertThrows(ConstraintViolationException.class, () -> playerService.getPlayer());
    }

    @Test
    void givenSelectedPlayerWithNullGame_whenGetPlayer_thenThrowIllegalArgumentException() {
        Player player = mock(Player.class);
        PlayerDto dtoWithNullGame =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", false, true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null, // selectedGame is null
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        when(playerRepository.findSelectedPlayerWithSelectedGame()).thenReturn(List.of(player));
        when(playerMapper.mapPlayerToDto(player)).thenReturn(dtoWithNullGame);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> playerService.getPlayer());

        assertEquals("No selected player with selected game found.", exception.getMessage());
    }
}
