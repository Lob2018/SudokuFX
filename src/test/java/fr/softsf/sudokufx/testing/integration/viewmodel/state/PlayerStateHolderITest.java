/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.integration.viewmodel.state;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.config.database.DataSourceConfigTest;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration test for {@link PlayerStateHolder}. Ensures player is loaded and refreshed correctly
 * from the database.
 */
@SpringBootTest(classes = {SudoMain.class})
@ActiveProfiles("test")
@Import(DataSourceConfigTest.class)
class PlayerStateHolderITest {

    @Autowired private PlayerStateHolder playerStateHolder;

    @Test
    void givenTestDatabase_whenInMemoryPlayerLoads_thenPlayerDtoIsNotNull() {
        PlayerDto dto = playerStateHolder.getCurrentPlayer();
        assertNotNull(dto, "PlayerDto should be initialized");
        assertNotNull(dto.name(), "Player name should not be null");
    }

    @Test
    void givenRefreshCurrentPlayer_whenCalled_thenCurrentPlayerIsReloadedFromService() {
        PlayerDto original = playerStateHolder.getCurrentPlayer();
        playerStateHolder.refreshCurrentPlayer();
        PlayerDto refreshed = playerStateHolder.getCurrentPlayer();
        assertNotNull(refreshed, "Refreshed player should not be null");
        assert (original.playerid().equals(refreshed.playerid()));
    }
}
