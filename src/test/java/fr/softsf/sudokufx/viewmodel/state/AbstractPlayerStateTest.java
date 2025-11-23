/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel.state;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;

import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.service.business.PlayerService;

import static org.mockito.Mockito.*;

/**
 * Abstract base test class that provides:
 *
 * <ul>
 *   <li>a mocked {@link PlayerService}
 *   <li>a real {@link PlayerStateHolder} wired with the mock
 *   <li>a default {@link PlayerDto} instance for convenience
 * </ul>
 */
public abstract class AbstractPlayerStateTest {

    /** Mocked PlayerService shared across test cases. */
    protected PlayerService playerServiceMock;

    /** Real PlayerStateHolder using the mocked PlayerService — JavaFX properties remain intact. */
    protected PlayerStateHolder playerStateHolder;

    /** Default PlayerDto available for test scenarios. */
    protected PlayerDto defaultPlayer;

    /** AutoCloseable for managing Mockito resources. */
    private AutoCloseable mocks;

    @BeforeEach
    void setupPlayerStateHolder() {
        mocks = MockitoAnnotations.openMocks(this);
        playerServiceMock = mock(PlayerService.class);
        defaultPlayer = createDefaultPlayer();
        when(playerServiceMock.getPlayer()).thenReturn(defaultPlayer);
        playerStateHolder = new TestablePlayerStateHolder(playerServiceMock);
    }

    @AfterEach
    void closeMocks() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    /**
     * Factory method to create the default {@link PlayerDto}.
     *
     * <p>Subclasses can override this to customize the default player used in tests.
     *
     * @return a default {@code PlayerDto} instance
     */
    protected PlayerDto createDefaultPlayer() {
        return new PlayerDto(
                1L,
                new PlayerLanguageDto(1L, "FR"),
                new OptionsDto(1L, "FFFFFFFF", "", "", true, true),
                new MenuDto((byte) 1, (byte) 1),
                new GameDto(
                        1L,
                        new GridDto(1L, "", "", (byte) 100),
                        1L,
                        new GameLevelDto((byte) 1, (byte) 1),
                        true,
                        LocalDateTime.of(2024, 1, 1, 10, 0, 0)
                                .atZone(ZoneId.systemDefault())
                                .toInstant(),
                        LocalDateTime.of(2024, 1, 1, 10, 0, 0)
                                .atZone(ZoneId.systemDefault())
                                .toInstant()),
                "SafePlayer",
                true,
                LocalDateTime.of(2024, 1, 1, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant(),
                LocalDateTime.of(2024, 1, 1, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Testable inner subclass of {@link PlayerStateHolder}.
     *
     * <p>Allows overriding of methods that should not be executed during tests while keeping all
     * JavaFX properties functional.
     */
    protected static class TestablePlayerStateHolder extends PlayerStateHolder {

        public TestablePlayerStateHolder(PlayerService playerService) {
            super(playerService);
        }

        /**
         * Overridden to avoid shutting down the JavaFX platform during tests.
         *
         * <p>Keeps JavaFX properties intact while making {@code exitPlatform()} a no-op.
         */
        @Override
        protected void exitPlatform() {
            System.out.println("TestablePlayerStateHolder: exitPlatform() called (no-op in tests)");
        }
    }
}
