/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.state;

import java.time.LocalDateTime;
import javafx.beans.property.ObjectProperty;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationExtension;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.dto.BackgroundDto;
import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.service.PlayerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class InMemoryPlayerUTest {

    private PlayerService playerServiceMock;
    private InMemoryPlayer inMemoryPlayer;
    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(InMemoryPlayer.class))
                .addAppender(logWatcher);
        playerServiceMock = mock(PlayerService.class);
    }

    @AfterEach
    void tearDown() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(InMemoryPlayer.class))
                .detachAndStopAllAppenders();
    }

    private PlayerLanguageDto defaultPlayerLanguage() {
        return new PlayerLanguageDto(1L, "FR");
    }

    private BackgroundDto defaultBackground() {
        return new BackgroundDto(1L, "000000ff", "", false);
    }

    private MenuDto defaultMenu() {
        return new MenuDto((byte) 1, (byte) 2);
    }

    private PlayerDto createPlayerDto(
            Long playerId,
            String playerName,
            Boolean isSelected,
            PlayerLanguageDto playerLanguage,
            BackgroundDto background,
            MenuDto menu) {
        return new PlayerDto(
                playerId,
                playerLanguage != null ? playerLanguage : defaultPlayerLanguage(),
                background != null ? background : defaultBackground(),
                menu != null ? menu : defaultMenu(),
                null,
                playerName,
                isSelected,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Test
    void givenValidPlayer_whenConstructed_thenPlayerIsLoaded() {
        PlayerDto expectedPlayer = createPlayerDto(1L, "John Doe", true, null, null, null);
        when(playerServiceMock.getPlayer()).thenReturn(expectedPlayer);
        inMemoryPlayer = new InMemoryPlayer(playerServiceMock);
        PlayerDto actualPlayer = inMemoryPlayer.getCurrentPlayer();
        assertNotNull(actualPlayer);
        assertEquals(expectedPlayer.name(), actualPlayer.name());
        assertTrue(actualPlayer.isselected());
    }

    @Test
    void givenSetCurrentPlayer_whenCalled_thenPlayerIsUpdated() {
        PlayerDto initialPlayer = createPlayerDto(1L, "John Doe", true, null, null, null);
        PlayerDto newPlayer =
                createPlayerDto(
                        2L,
                        "Jane Smith",
                        false,
                        new PlayerLanguageDto(2L, "EN"),
                        new BackgroundDto(2L, "ffffff00", "", false),
                        new MenuDto((byte) 2, (byte) 3));
        when(playerServiceMock.getPlayer()).thenReturn(initialPlayer);
        inMemoryPlayer = new InMemoryPlayer(playerServiceMock);
        inMemoryPlayer.setCurrentPlayer(newPlayer);
        PlayerDto actualPlayer = inMemoryPlayer.getCurrentPlayer();
        assertEquals(newPlayer, actualPlayer);
    }

    @Test
    void givenNullCurrentPlayer_whenConstructed_thenInitializeCalled() {
        PlayerDto expectedPlayer = createPlayerDto(1L, "Init Player", true, null, null, null);
        when(playerServiceMock.getPlayer()).thenReturn(expectedPlayer);
        inMemoryPlayer = new InMemoryPlayer(playerServiceMock);
        assertEquals(expectedPlayer, inMemoryPlayer.getCurrentPlayer());
    }

    @Test
    void currentPlayerProperty_shouldReturnObjectProperty() {
        PlayerDto expectedPlayer = createPlayerDto(1L, "Prop Player", true, null, null, null);
        when(playerServiceMock.getPlayer()).thenReturn(expectedPlayer);
        inMemoryPlayer = new InMemoryPlayer(playerServiceMock);
        ObjectProperty<PlayerDto> prop = inMemoryPlayer.currentPlayerProperty();
        assertNotNull(prop);
        assertEquals(expectedPlayer, prop.get());
    }

    @Test
    void givenException_whenInitializingPlayer_thenLogErrorIsProduced_withoutCallingPlatformExit() {
        when(playerServiceMock.getPlayer()).thenThrow(new IllegalStateException("DB down"));

        // Sous-classe anonyme qui override exitPlatform pour éviter Platform.exit() en test
        InMemoryPlayer player =
                new InMemoryPlayer(playerServiceMock) {
                    @Override
                    void exitPlatform() {
                        // Do nothing to prevent Platform.exit() in tests
                    }
                };

        String lastLog = logWatcher.list.get(logWatcher.list.size() - 1).getFormattedMessage();
        assertTrue(
                lastLog.contains("Error initializing player: DB down"),
                "The last log message must contenir l’erreur attendue");
    }
}
