/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import javafx.beans.binding.StringBinding;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.viewmodel.MenuMiniViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MenuMiniViewModelUTest {

    private Locale originalLocale;
    private MenuMiniViewModel viewModel;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        viewModel = new MenuMiniViewModel();
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void allBindingsShouldReturnExpectedI18nValues() {
        Map<StringBinding, Supplier<String>> bindingToExpectedSupplier =
                getStringBindingStringMap(viewModel);
        for (var entry : bindingToExpectedSupplier.entrySet()) {
            StringBinding binding = entry.getKey();
            String expected = entry.getValue().get();
            String actual = binding.get();
            assertEquals(
                    expected, actual, "Binding should return correct value before locale change");
        }
        I18n.INSTANCE.setLocaleBundle("EN");
        for (var entry : bindingToExpectedSupplier.entrySet()) {
            StringBinding binding = entry.getKey();
            String expected = entry.getValue().get();
            String actual = binding.get();
            assertEquals(
                    expected, actual, "Binding should return correct value after locale change");
        }
    }

    private static Map<StringBinding, Supplier<String>> getStringBindingStringMap(
            MenuMiniViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        Supplier<String> closedRoleDesc =
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed");
        map.put(
                vm.playerTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.mini.button.player.accessibility")
                                + closedRoleDesc.get());
        map.put(
                vm.showAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        map.put(
                vm.playerAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.player.accessibility"));
        map.put(
                vm.solveAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility"));
        map.put(
                vm.backupAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility"));
        map.put(
                vm.backgroundAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.background.accessibility"));
        map.put(
                vm.languageAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        map.put(
                vm.helpAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        map.put(
                vm.menuMiniButtonLanguageIsoTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.language.iso"));
        map.put(
                vm.showTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.show.accessibility")); // no suffix
        map.put(
                vm.playerTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.mini.button.player.accessibility")
                                + closedRoleDesc.get());
        map.put(
                vm.solveTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility")
                                + closedRoleDesc.get());
        map.put(
                vm.backupTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility")
                                + closedRoleDesc.get());
        map.put(
                vm.backgroundTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.mini.button.background.accessibility")
                                + closedRoleDesc.get());
        map.put(
                vm.languageTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue(
                                "menu.mini.button.language.accessibility")); // no suffix
        map.put(
                vm.helpTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.help.accessibility")); // no suffix
        return map;
    }
}
