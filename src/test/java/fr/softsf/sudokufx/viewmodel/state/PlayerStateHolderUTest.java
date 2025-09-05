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
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationExtension;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.service.business.PlayerService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class PlayerStateHolderUTest {

    @Mock private PlayerService playerServiceMock;

    private PlayerStateHolder playerStateHolder;
    private ListAppender<ILoggingEvent> logWatcher;
    private AutoCloseable mocks;

    private PlayerLanguageDto safeLang() {
        return new PlayerLanguageDto(1L, "FR");
    }

    private OptionsDto safeOptions() {
        return new OptionsDto(
                1L, "FFFFFFFF", "/img/default.png", "/song/default.mp3", true, true, false);
    }

    private MenuDto safeMenu() {
        return new MenuDto((byte) 1, (byte) 1);
    }

    private PlayerDto safePlayer() {
        return new PlayerDto(
                1L,
                safeLang(),
                safeOptions(),
                safeMenu(),
                null,
                "SafePlayer",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    private PlayerDto newPlayer() {
        return new PlayerDto(
                2L,
                safeLang(),
                safeOptions(),
                safeMenu(),
                null,
                "NewPlayer",
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(PlayerStateHolder.class)).addAppender(logWatcher);
        when(playerServiceMock.getPlayer()).thenReturn(safePlayer());
        playerStateHolder = spy(new PlayerStateHolder(playerServiceMock));
        doNothing().when(playerStateHolder).exitPlatform();
    }

    @AfterEach
    void cleanup() throws Exception {
        ((Logger) LoggerFactory.getLogger(PlayerStateHolder.class)).detachAndStopAllAppenders();
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void givenPlayerService_returnsPlayer_thenCurrentPlayerIsSet() {
        PlayerDto player = playerStateHolder.getCurrentPlayer();
        assertNotNull(player);
        assertEquals("SafePlayer", player.name());
    }

    @Test
    void givenException_whenRefreshingPlayer_thenLogErrorIsProduced_withoutCallingPlatformExit() {
        doThrow(new IllegalStateException("DB down")).when(playerServiceMock).getPlayer();
        playerStateHolder.refreshCurrentPlayer();
        verify(playerStateHolder).exitPlatform();
        String lastLog = logWatcher.list.getLast().getFormattedMessage();
        assertTrue(lastLog.contains("Error refreshing player: DB down"));
    }

    @Test
    void givenPlayerService_returnsPlayer_whenRefreshCurrentPlayer_thenPropertyUpdated() {
        doReturn(newPlayer()).when(playerServiceMock).getPlayer();
        playerStateHolder.refreshCurrentPlayer();
        ObjectProperty<PlayerDto> property = playerStateHolder.currentPlayerProperty();
        assertEquals("NewPlayer", property.get().name());
        assertEquals("NewPlayer", playerStateHolder.getCurrentPlayer().name());
        String lastLog = logWatcher.list.getLast().getFormattedMessage();
        assertTrue(lastLog.contains("Player refreshed from database:"));
        assertTrue(lastLog.contains("NewPlayer"));
    }

    @Test
    void currentPlayerProperty_returnsObjectProperty() {
        ObjectProperty<PlayerDto> prop = playerStateHolder.currentPlayerProperty();
        assertNotNull(prop);
        assertEquals(playerStateHolder.getCurrentPlayer(), prop.get());
    }

    @Test
    void getCurrentPlayer_returnsCorrectPlayer() {
        PlayerDto current = playerStateHolder.getCurrentPlayer();
        assertNotNull(current);
        assertEquals("SafePlayer", current.name());
    }

    @Test
    void exitPlatform_doesNotThrow_whenCalled() {
        assertDoesNotThrow(() -> playerStateHolder.exitPlatform());
    }
}
