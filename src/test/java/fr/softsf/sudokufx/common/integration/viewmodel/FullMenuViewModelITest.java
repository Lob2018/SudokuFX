/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.integration.viewmodel;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.configuration.database.DataSourceConfigTest;
import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.service.SoftwareService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {SudoMain.class})
@ActiveProfiles("test")
@Import(DataSourceConfigTest.class)
class FullMenuViewModelITest {

    @Autowired private SoftwareService softwareService;

    @Autowired private FullMenuViewModel fullMenuViewModel;

    @Test
    void givenSoftware_whenGetSoftware_thenVersionMatch() {
        fullMenuViewModel.test();
        Optional<SoftwareDto> software = softwareService.getSoftware();
        assertTrue(software.isPresent(), "The software should be found.");
        assertEquals(
                "1.0.0", software.get().currentversion(), "The current version should be '1.0.0'");
        assertEquals("1.0.0", software.get().lastversion(), "The latest version should be '1.0.0'");
        assertTrue(
                fullMenuViewModel.welcomeProperty().get().contains("Version : 1.0.0"),
                "The message should contain the version.");
    }
}
