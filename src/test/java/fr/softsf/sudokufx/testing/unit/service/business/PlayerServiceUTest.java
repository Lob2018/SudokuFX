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
import fr.softsf.sudokufx.model.*;
import fr.softsf.sudokufx.repository.*;
import fr.softsf.sudokufx.service.business.PlayerService;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceUTest {

    private PlayerRepository playerRepository;
    private PlayerLanguageRepository playerLanguageRepository;
    private OptionsRepository optionsRepository;
    private MenuRepository menuRepository;

    private IPlayerMapper playerMapper;
    private JakartaValidator jakartaValidator;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerRepository = mock(PlayerRepository.class);
        playerLanguageRepository = mock(PlayerLanguageRepository.class);
        optionsRepository = mock(OptionsRepository.class);
        menuRepository = mock(MenuRepository.class);
        GameRepository gameRepository = mock(GameRepository.class);
        playerMapper = mock(IPlayerMapper.class);
        jakartaValidator = mock(JakartaValidator.class);
        playerService =
                new PlayerService(
                        playerRepository,
                        playerLanguageRepository,
                        optionsRepository,
                        menuRepository,
                        gameRepository,
                        playerMapper,
                        jakartaValidator);
    }

    @Test
    void givenValidPlayer_whenGetPlayer_thenReturnsDto() {
        Player mockEntity = mock(Player.class);
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
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
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);
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
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());

        when(playerRepository.findSelectedPlayerWithSelectedGame()).thenReturn(List.of(player));
        when(playerMapper.mapPlayerToDto(player)).thenReturn(dtoWithNullGame);
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.getPlayer());
        assertEquals("No selected player with selected game found.", ex.getMessage());
    }

    @Test
    void givenInvalidDto_whenUpdatePlayer_thenThrowsConstraintViolationException() {
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        when(jakartaValidator.validateOrThrow(dto))
                .thenThrow(new ConstraintViolationException("DTO invalid", Set.of()));
        ConstraintViolationException ex =
                assertThrows(
                        ConstraintViolationException.class, () -> playerService.updatePlayer(dto));
        assertTrue(ex.getMessage().contains("DTO invalid"));
    }

    @Test
    void givenValidPlayerDto_whenUpdatePlayer_thenReturnsUpdatedDto() {
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        Player existingPlayer = mock(Player.class);
        PlayerLanguage pl = mock(PlayerLanguage.class);
        Options op = mock(Options.class);
        Menu menu = mock(Menu.class);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto); // ← important
        when(playerRepository.findById(dto.playerid()))
                .thenReturn(java.util.Optional.of(existingPlayer));
        when(playerLanguageRepository.findById(dto.playerlanguageidDto().playerlanguageid()))
                .thenReturn(java.util.Optional.of(pl));
        when(optionsRepository.findById(dto.optionsidDto().optionsid()))
                .thenReturn(java.util.Optional.of(op));
        when(menuRepository.findById(dto.menuidDto().menuid()))
                .thenReturn(java.util.Optional.of(menu));
        when(playerRepository.save(existingPlayer)).thenReturn(existingPlayer);
        when(playerMapper.mapPlayerToDto(existingPlayer)).thenReturn(dto);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto); // validation DTO final
        PlayerDto result = playerService.updatePlayer(dto);
        assertNotNull(result);
        assertEquals("Jean", result.name());
        verify(existingPlayer).setName(dto.name());
        verify(existingPlayer).setSelected(dto.selected());
        verify(existingPlayer).setUpdatedat(dto.updatedat());
        verify(existingPlayer).setPlayerlanguageid(pl);
        verify(existingPlayer).setOptionsid(op);
        verify(existingPlayer).setMenuid(menu);
    }

    @Test
    @SuppressWarnings("DataFlowIssue")
    void givenNullPlayerDto_whenUpdatePlayer_thenThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> playerService.updatePlayer(null));
    }

    @Test
    void givenNonExistentPlayer_whenUpdatePlayer_thenThrowsIllegalArgumentException() {
        PlayerDto dto =
                new PlayerDto(
                        999L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto); // ← important
        when(playerRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.updatePlayer(dto));
        assertTrue(ex.getMessage().contains("Player not found"));
    }

    @Test
    void givenNonExistentPlayerLanguage_whenUpdatePlayer_thenThrowsIllegalArgumentException() {
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(999L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        Player existingPlayer = mock(Player.class);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);
        when(playerRepository.findById(dto.playerid()))
                .thenReturn(java.util.Optional.of(existingPlayer));
        when(playerLanguageRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.updatePlayer(dto));
        assertTrue(ex.getMessage().contains("PlayerLanguage not found"));
    }

    @Test
    void givenNonExistentOptions_whenUpdatePlayer_thenThrowsIllegalArgumentException() {
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(999L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        Player existingPlayer = mock(Player.class);
        PlayerLanguage pl = mock(PlayerLanguage.class);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);
        when(playerRepository.findById(dto.playerid()))
                .thenReturn(java.util.Optional.of(existingPlayer));
        when(playerLanguageRepository.findById(dto.playerlanguageidDto().playerlanguageid()))
                .thenReturn(java.util.Optional.of(pl));
        when(optionsRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.updatePlayer(dto));
        assertTrue(ex.getMessage().contains("Options not found"));
    }

    @Test
    void givenNonExistentMenu_whenUpdatePlayer_thenThrowsIllegalArgumentException() {
        PlayerDto dto =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#FFFFFF", "", "", true, true),
                        new MenuDto((byte) 99, (byte) 1),
                        null,
                        "Jean",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        Player existingPlayer = mock(Player.class);
        PlayerLanguage pl = mock(PlayerLanguage.class);
        Options op = mock(Options.class);
        when(jakartaValidator.validateOrThrow(dto)).thenReturn(dto);
        when(playerRepository.findById(dto.playerid()))
                .thenReturn(java.util.Optional.of(existingPlayer));
        when(playerLanguageRepository.findById(dto.playerlanguageidDto().playerlanguageid()))
                .thenReturn(java.util.Optional.of(pl));
        when(optionsRepository.findById(dto.optionsidDto().optionsid()))
                .thenReturn(java.util.Optional.of(op));
        when(menuRepository.findById(dto.menuidDto().menuid()))
                .thenReturn(java.util.Optional.empty());
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> playerService.updatePlayer(dto));
        assertTrue(ex.getMessage().contains("Menu not found"));
    }
}
