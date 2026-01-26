/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.beans.binding.StringBinding;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MenuMaxiViewModelUTest {

    private Locale originalLocale;

    @BeforeEach
    void setup() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void allBindingsShouldReturnExpectedI18nValues() {
        Map<StringBinding, String> bindingToKey = getStringBindingStringMap();
        for (var entry : bindingToKey.entrySet()) {
            StringBinding binding = entry.getKey();
            String key = entry.getValue();
            String expected = I18n.INSTANCE.getValue(key);
            String actual = binding.get();
            assertEquals(
                    expected,
                    actual,
                    "Binding for key '" + key + "' should return correct I18n value");
        }
        I18n.INSTANCE.setLocaleBundle("EN");
        for (var entry : bindingToKey.entrySet()) {
            StringBinding binding = entry.getKey();
            String key = entry.getValue();
            String expected = I18n.INSTANCE.getValue(key);
            String actual = binding.get();
            assertEquals(
                    expected,
                    actual,
                    "After locale change, binding for key '" + key + "' should update correctly");
        }
    }

    private static Map<StringBinding, String> getStringBindingStringMap() {
        MenuMaxiViewModel vm = new MenuMaxiViewModel();
        Map<StringBinding, String> bindingToKey = new HashMap<>();
        bindingToKey.put(
                vm.reduceAccessibleTextProperty(), "menu.maxi.button.reduce.accessibility");
        bindingToKey.put(vm.reduceTooltipProperty(), "menu.maxi.button.reduce.accessibility");
        bindingToKey.put(vm.reduceTextProperty(), "menu.maxi.button.reduce.text");
        bindingToKey.put(
                vm.languageAccessibleTextProperty(), "menu.maxi.button.language.accessibility");
        bindingToKey.put(vm.languageTooltipProperty(), "menu.maxi.button.language.accessibility");
        bindingToKey.put(vm.languageIsoProperty(), "menu.maxi.button.language.iso");
        bindingToKey.put(vm.languageTextProperty(), "menu.maxi.button.language.text");
        bindingToKey.put(vm.helpAccessibleTextProperty(), "menu.maxi.button.help.accessibility");
        bindingToKey.put(vm.helpTooltipProperty(), "menu.maxi.button.help.accessibility");
        bindingToKey.put(vm.helpTextProperty(), "menu.maxi.button.help.text");
        return bindingToKey;
    }
}
