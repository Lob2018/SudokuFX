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
import javafx.collections.ObservableList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.viewmodel.MenuSaveViewModel;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MenuSaveViewModelUTest {

    private Locale originalLocale;
    private MenuSaveViewModel viewModel;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        viewModel = new MenuSaveViewModel();
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void givenFrenchLocale_whenGettingSaveTextProperty_thenReturnsLocalizedValueInFrench() {
        String actual = viewModel.saveTextProperty().get();
        assertEquals("Sauvegardes", actual);
    }

    @Test
    void givenEnglishLocale_whenLocaleChanges_thenAllBindingsAreUpdated() {
        String frenchSave = viewModel.saveTextProperty().get();
        I18n.INSTANCE.setLocaleBundle("EN");
        String englishSave = viewModel.saveTextProperty().get();
        assertNotEquals(frenchSave, englishSave);
        assertEquals("Backups", englishSave); // Remplacez par la vraie valeur attendue
    }

    @Test
    void givenSelectedBackup_whenLoaded_thenSelectedIsCorrectlySet() {
        GameDto selected = viewModel.selectedBackupProperty().get();
        assertNotNull(selected);
        assertTrue(selected.isselected());
    }

    @Test
    void backupsListShouldBeInitializedWithExpectedBackups() {
        ObservableList<GameDto> backups = viewModel.getBackups();
        assertEquals(21, backups.size(), "Backups list should contain 21 entries");
        boolean hasSelected = backups.stream().anyMatch(GameDto::isselected);
        assertTrue(hasSelected, "At least one backup should be marked as selected");
        GameDto selected = viewModel.selectedBackupProperty().get();
        assertNotNull(selected, "Selected backup should not be null");
        assertTrue(selected.isselected(), "Selected backup should be the one marked as selected");
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
            MenuSaveViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        map.put(
                vm.saveAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.save.accessibility"));
        map.put(
                vm.saveTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.save.button.save.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.opened"));
        map.put(
                vm.saveRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        map.put(vm.saveTextProperty(), () -> I18n.INSTANCE.getValue("menu.save.button.save.text"));
        map.put(
                vm.reduceAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.reduce.accessibility"));
        map.put(
                vm.reduceTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.reduce.accessibility"));
        map.put(
                vm.reduceTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.reduce.text"));
        map.put(
                vm.backupAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.backup.accessibility"));
        map.put(
                vm.backupTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.save.button.backup.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.backupRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.backupTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.backup.text"));
        map.put(
                vm.maxiBackupAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility"));
        map.put(
                vm.maxiBackupTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.closed"));
        map.put(
                vm.maxiBackupRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        map.put(
                vm.maxiBackupTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.backup.text"));
        map.put(
                vm.cellDeleteAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.backup.cell.delete.accessibility"));
        map.put(
                vm.cellConfirmationTitleProperty(),
                () -> I18n.INSTANCE.getValue("menu.save.button.backup.dialog.confirmation.title"));
        map.put(
                vm.cellConfirmationMessageProperty(),
                () ->
                        I18n.INSTANCE.getValue(
                                "menu.save.button.backup.dialog.confirmation.message"));
        return map;
    }
}
