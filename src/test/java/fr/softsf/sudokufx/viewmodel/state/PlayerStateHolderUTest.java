/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.state;

import javafx.beans.property.ObjectProperty;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationExtension;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.dto.PlayerDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class PlayerStateHolderUTest extends AbstractPlayerStateTest {

    private ListAppender<ILoggingEvent> logWatcher;
    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(PlayerStateHolder.class)).addAppender(logWatcher);
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
        assertNotNull(playerStateHolderSpy.getCurrentPlayer());
        assertEquals("SafePlayer", playerStateHolderSpy.getCurrentPlayer().name());
    }

    @Test
    void givenException_whenRefreshingPlayer_thenLogErrorIsProduced() {
        doThrow(new IllegalStateException("DB down")).when(playerServiceMock).getPlayer();
        playerStateHolderSpy.refreshCurrentPlayer();
        verify(playerStateHolderSpy).exitPlatform();

        String lastLog = logWatcher.list.getLast().getFormattedMessage();
        assertTrue(lastLog.contains("Error refreshing player: DB down"));
    }

    @Test
    void givenPlayerService_returnsPlayer_whenRefreshCurrentPlayer_thenPropertyUpdated() {
        var newPlayer =
                new PlayerDto(
                        2L,
                        defaultPlayer.playerlanguageidDto(),
                        defaultPlayer.optionsidDto(),
                        defaultPlayer.menuidDto(),
                        null,
                        "NewPlayer",
                        false,
                        defaultPlayer.createdat(),
                        defaultPlayer.updatedat());
        doReturn(newPlayer).when(playerServiceMock).getPlayer();
        playerStateHolderSpy.refreshCurrentPlayer();
        ObjectProperty<PlayerDto> prop = playerStateHolderSpy.currentPlayerProperty();
        assertEquals("NewPlayer", prop.get().name());
        assertEquals("NewPlayer", playerStateHolderSpy.getCurrentPlayer().name());
        String lastLog = logWatcher.list.getLast().getFormattedMessage();
        assertTrue(lastLog.contains("Player refreshed from database:"));
        assertTrue(lastLog.contains("NewPlayer"));
    }

    @Test
    void currentPlayerProperty_returnsObjectProperty() {
        ObjectProperty<PlayerDto> prop = playerStateHolderSpy.currentPlayerProperty();
        assertNotNull(prop);
        assertEquals(playerStateHolderSpy.getCurrentPlayer(), prop.get());
    }

    @Test
    void getCurrentPlayer_returnsCorrectPlayer() {
        assertEquals("SafePlayer", playerStateHolderSpy.getCurrentPlayer().name());
    }

    @Test
    void exitPlatform_doesNotThrow_whenCalled() {
        assertDoesNotThrow(() -> playerStateHolderSpy.exitPlatform());
    }
}
