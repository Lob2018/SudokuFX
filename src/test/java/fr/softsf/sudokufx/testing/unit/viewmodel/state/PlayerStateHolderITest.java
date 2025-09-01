/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel.state;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.config.database.DataSourceConfigTest;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for {@link PlayerStateHolder}. Ensures player is loaded correctly from the
 * database.
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
        assertFalse(dto.name().isBlank(), "Player name should not be blank");
    }
}
