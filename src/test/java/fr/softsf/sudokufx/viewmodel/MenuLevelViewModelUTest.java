/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.view.component.PossibilityStarsHBox;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MenuLevelViewModelUTest {

    private MenuLevelViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel = new MenuLevelViewModel();
    }

    @Test
    void givenDifficultyLevel_whenUpdateSelectedLevel_thenSelectedLevelAndStarsAreSet() {
        viewModel.updateSelectedLevel(DifficultyLevel.EASY,15);
        assertEquals(DifficultyLevel.EASY, viewModel.selectedLevelProperty().get());
        int stars = viewModel.percentageProperty().get();
        assertTrue(stars >= 10 && stars <= 33);
        viewModel.updateSelectedLevel(DifficultyLevel.MEDIUM,40);
        assertEquals(DifficultyLevel.MEDIUM, viewModel.selectedLevelProperty().get());
        stars = viewModel.percentageProperty().get();
        assertTrue(stars >= 34 && stars <= 66);
        viewModel.updateSelectedLevel(DifficultyLevel.DIFFICULT,70);
        assertEquals(DifficultyLevel.DIFFICULT, viewModel.selectedLevelProperty().get());
        stars = viewModel.percentageProperty().get();
        assertTrue(stars >= 67 && stars <= 89);
    }

    @Test
    void givenSelectedLevel_whenIsSelectedBinding_thenBindingReflectsSelection() {
        viewModel.updateSelectedLevel(DifficultyLevel.MEDIUM,40);
        BooleanBinding bindingMedium = viewModel.isSelectedBinding(DifficultyLevel.MEDIUM);
        assertTrue(bindingMedium.get());
        BooleanBinding bindingEasy = viewModel.isSelectedBinding(DifficultyLevel.EASY);
        assertFalse(bindingEasy.get());
    }

    @Test
    void
            givenDifficultyLevelsEasyMediumAndDifficult_whenLabelTextBinding_thenReturnCorrectI18nKeys() {
        StringBinding easyLabel = viewModel.labelTextBinding(DifficultyLevel.EASY);
        StringBinding mediumLabel = viewModel.labelTextBinding(DifficultyLevel.MEDIUM);
        StringBinding difficultLabel = viewModel.labelTextBinding(DifficultyLevel.DIFFICULT);
        assertEquals(I18n.INSTANCE.getValue("menu.maxi.button.easy.text"), easyLabel.get());
        assertEquals(I18n.INSTANCE.getValue("menu.maxi.button.medium.text"), mediumLabel.get());
        assertEquals(
                I18n.INSTANCE.getValue("menu.maxi.button.difficult.text"), difficultLabel.get());
    }

    @Test
    void givenStarsBoxAndKey_whenAccessibleTextBinding_thenReturnFormattedText() {
        PossibilityStarsHBox starsBox = new PossibilityStarsHBoxStub();
        String key = "some.key";
        StringBinding accessibleBinding = viewModel.accessibleTextBinding(starsBox, key);
        assertEquals("formattedText-for-some.key", accessibleBinding.get());
        StringBinding roleDescription = viewModel.selectedRoleDescriptionBinding();
        assertEquals(
                I18n.INSTANCE.getValue("menu.accessibility.role.description.selected"),
                roleDescription.get());
    }

    @Test
    void givenDifficultyLevels_whenGetAccessibilityKeyForLevel_thenReturnCorrectI18nKeys() {
        assertEquals(
                "menu.maxi.button.easy.accessibility",
                viewModel.getAccessibilityKeyForLevel(DifficultyLevel.EASY));
        assertEquals(
                "menu.maxi.button.medium.accessibility",
                viewModel.getAccessibilityKeyForLevel(DifficultyLevel.MEDIUM));
        assertEquals(
                "menu.maxi.button.difficult.accessibility",
                viewModel.getAccessibilityKeyForLevel(DifficultyLevel.DIFFICULT));
    }

    @Test
    void givenPercentageValue_whenSetPercentage_thenPercentagePropertyIsUpdated() {
        int newPercentage = 75;
        viewModel.setPercentage(newPercentage);
        assertEquals(newPercentage, viewModel.percentageProperty().get());
    }

    private static class PossibilityStarsHBoxStub extends PossibilityStarsHBox {
        PossibilityStarsHBoxStub() {
            super();
            percentageProperty = new SimpleIntegerProperty(50);
        }

        @Override
        public SimpleIntegerProperty getPercentage() {
            return percentageProperty;
        }

        @Override
        public StringBinding formattedTextBinding(String key, boolean someFlag) {
            return Bindings.createStringBinding(() -> "formattedText-for-" + key);
        }

        private final SimpleIntegerProperty percentageProperty;
    }
}
