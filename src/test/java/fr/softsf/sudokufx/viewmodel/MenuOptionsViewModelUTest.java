/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.util.function.Supplier;
import java.util.*;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.service.AsyncFileProcessorService;
import fr.softsf.sudokufx.service.AudioService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MenuOptionsViewModelUTest {

    private Locale originalLocale;
    private MenuOptionsViewModel viewModel;

    private GridPane sudokuFX;
    private ColorPicker colorPicker;
    private ToasterVBox toaster;
    private SpinnerGridPane spinner;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        viewModel = new MenuOptionsViewModel(new AudioService(), new AsyncFileProcessorService());
        sudokuFX = new GridPane();
        colorPicker = new ColorPicker();
        toaster = mock(ToasterVBox.class);
        spinner = mock(SpinnerGridPane.class);
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
            MenuOptionsViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        map.put(
                vm.optionsMenuMaxiAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.options.accessibility"));
        map.put(
                vm.optionsMenuMaxiTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.maxi.button.options.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.closed"));
        map.put(
                vm.optionsMenuMaxiRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        map.put(
                vm.optionsMenuMaxiTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.options.text"));

        map.put(
                vm.optionsReduceAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.reduce.accessibility"));
        map.put(
                vm.optionsReduceTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.reduce.accessibility"));
        map.put(
                vm.optionsReduceTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.reduce.text"));

        map.put(
                vm.optionsAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.options.accessibility"));
        map.put(
                vm.optionsTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.options.button.options.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.opened"));
        map.put(
                vm.optionsRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        map.put(
                vm.optionsTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.options.text"));

        map.put(
                vm.optionsImageAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.image.accessibility"));
        map.put(
                vm.optionsImageTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.options.button.image.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.optionsImageRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.optionsImageTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.image.text"));

        map.put(
                vm.optionsColorAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.options.button.color.accessibility"));
        map.put(
                vm.optionsColorTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.options.button.color.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.optionsColorRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        return map;
    }

    @Test
    void givenViewModelAndColorPicker_whenInitCalled_thenColorIsSetAndColorPickerUpdated() {
        MenuOptionsViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).loadBackgroundImage(any(), any(), any(), any());
        spyViewModel.init(sudokuFX, colorPicker, toaster, spinner);
        Color expectedColor = Color.rgb(153, 179, 255, 0.803921568627451);
        assertEquals(expectedColor, colorPicker.getValue());
        BackgroundFill fill = sudokuFX.getBackground().getFills().getFirst();
        assertEquals(expectedColor, fill.getFill());
    }

    @Test
    void givenGridPaneAndColor_whenUpdateBackgroundColorAndApply_thenOptionsColorIsSet() {
        GridPane grid = new GridPane();
        Color color = Color.web("#12345678");
        viewModel.updateOptionsColorAndApply(grid, color);
        Background background = grid.getBackground();
        assertNotNull(background, "Options should not be null");
        List<BackgroundFill> fills = background.getFills();
        assertFalse(fills.isEmpty(), "Options fills should not be empty");
        assertEquals(color, fills.getFirst().getFill(), "Options color should match expected");
    }

    @Test
    void givenNullFile_whenLoadBackgroundImage_thenShowErrorToast() {
        ToasterVBox toasterMock = mock(ToasterVBox.class);
        SpinnerGridPane spinnerMock = mock(SpinnerGridPane.class);
        GridPane grid = new GridPane();
        AsyncFileProcessorService asyncServiceMock = mock(AsyncFileProcessorService.class);
        MenuOptionsViewModel viewModel =
                new MenuOptionsViewModel(new AudioService(), asyncServiceMock);
        viewModel.loadBackgroundImage(null, toasterMock, spinnerMock, grid);
        verify(toasterMock)
                .addToast(
                        I18n.INSTANCE.getValue(
                                "toast.error.optionsviewmodel.handlefileimagechooser"),
                        "",
                        ToastLevels.ERROR,
                        true);
        verifyNoInteractions(asyncServiceMock);
    }
}
