/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.state;

import java.time.LocalDateTime;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.service.business.PlayerService;

import static org.mockito.Mockito.*;

/** Abstract test class providing a mocked PlayerService and a PlayerStateHolder spy. */
public abstract class AbstractPlayerStateTest {

    /** Mocked PlayerService shared across tests */
    protected PlayerService playerServiceMock;

    /** Spy of PlayerStateHolder built with mocked PlayerService */
    protected PlayerStateHolder playerStateHolderSpy;

    /** Default PlayerDto for tests */
    protected PlayerDto defaultPlayer;

    /** AutoCloseable for managing Mockito resources */
    private AutoCloseable mocks;

    @BeforeEach
    void setupPlayerStateHolder() {
        mocks = MockitoAnnotations.openMocks(this);
        playerServiceMock = mock(PlayerService.class);
        defaultPlayer =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "FFFFFFFF", "", "", false, true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "SafePlayer",
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        when(playerServiceMock.getPlayer()).thenReturn(defaultPlayer);
        playerStateHolderSpy = spy(new PlayerStateHolder(playerServiceMock));
        doNothing().when(playerStateHolderSpy).exitPlatform();
    }

    @AfterEach
    void closeMocks() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }
}
