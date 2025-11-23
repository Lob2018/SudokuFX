/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ActiveMenuOrSubmenuViewModelUTest {

    private ActiveMenuOrSubmenuViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new ActiveMenuOrSubmenuViewModel();
    }

    @Test
    void defaultActiveMenuIsMAXI() {
        assertEquals(
                ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI,
                viewModel.getActiveMenu().get(),
                "Default active menu should be MAXI");
    }

    @Test
    void setActiveMenuToNonNullValueUpdatesProperty() {
        viewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER);
        assertEquals(
                ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER,
                viewModel.getActiveMenu().get(),
                "Active menu should update to PLAYER");
    }

    @Test
    void setActiveMenuToNullResetsToDefaultMAXI() {
        viewModel.setActiveMenu(null);
        assertEquals(
                ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI,
                viewModel.getActiveMenu().get(),
                "Active menu should reset to default MAXI when set to null");
    }
}
