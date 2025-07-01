/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import javafx.beans.binding.StringBinding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.viewmodel.MenuHiddenViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MenuHiddenViewModelUTest {

    private MenuHiddenViewModel viewModel;

    @BeforeEach
    void setUp() {
        // Set a default locale to ensure deterministic test result
        I18n.INSTANCE.setLocaleBundle("EN");
        viewModel = new MenuHiddenViewModel();
    }

    @Test
    void givenEnglishLocale_whenAccessingBinding_thenReturnsExpectedTranslation() {
        StringBinding binding = viewModel.menuHiddenButtonShowAccessibilityTextProperty();
        assertEquals(
                I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"),
                binding.get(),
                "The accessibility text should match the English translation");
    }

    @Test
    void givenLocaleChange_whenAccessingBinding_thenReturnsUpdatedTranslation() {
        StringBinding binding = viewModel.menuHiddenButtonShowAccessibilityTextProperty();
        I18n.INSTANCE.setLocaleBundle("FR");
        binding.invalidate();
        assertEquals(
                I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"),
                binding.get(),
                "The accessibility text should update to the French translation");
    }
}
