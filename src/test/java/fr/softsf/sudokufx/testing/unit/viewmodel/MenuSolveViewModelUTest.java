/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.viewmodel.MenuSolveViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MenuSolveViewModelUTest {

    private MenuSolveViewModel viewModel;
    private Locale originalLocale;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        viewModel = new MenuSolveViewModel();
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void percentageProperty_shouldHaveInitialValue_100_and_setterShouldWork() {
        IntegerProperty percentage = viewModel.solvePercentageProperty();
        assertEquals(-1, percentage.get());
        viewModel.setSolvePercentage(42);
        assertEquals(42, percentage.get());
    }

    @Test
    void bindingsShouldReturnCorrectValues_beforeAndAfterLocaleChange() {
        Map<StringBinding, Supplier<String>> bindingsToExpected =
                getBindingsToExpectedMap(viewModel);
        for (var entry : bindingsToExpected.entrySet()) {
            String actual = entry.getKey().get();
            String expected = entry.getValue().get();
            assertEquals(
                    expected, actual, "Binding should return correct value before locale change");
        }
        I18n.INSTANCE.setLocaleBundle("EN");
        for (var entry : bindingsToExpected.entrySet()) {
            String actual = entry.getKey().get();
            String expected = entry.getValue().get();
            assertEquals(
                    expected, actual, "Binding should return correct value after locale change");
        }
    }

    private static Map<StringBinding, Supplier<String>> getBindingsToExpectedMap(
            MenuSolveViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        map.put(
                vm.solveMenuMaxiAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility"));
        map.put(
                vm.solveMenuMaxiTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.closed"));
        map.put(
                vm.solveMenuMaxiRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        map.put(
                vm.solveMenuMaxiTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.solve.text"));
        map.put(
                vm.solveReduceAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.solve.button.reduce.accessibility"));
        map.put(
                vm.solveReduceTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.solve.button.reduce.accessibility"));
        map.put(
                vm.solveReduceTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.solve.button.reduce.text"));
        map.put(
                vm.solveRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        map.put(
                vm.solveTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.solve.button.solve.text"));
        map.put(
                vm.solveClearAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.solve.button.solve.clear.accessibility"));
        map.put(
                vm.solveClearRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.solveClearTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.solve.button.solve.clear.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        return map;
    }
}
