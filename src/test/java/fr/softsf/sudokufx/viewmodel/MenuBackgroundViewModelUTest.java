/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.*;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Task;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

import static fr.softsf.sudokufx.common.enums.Paths.LOGO_SUDO_PNG_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MenuBackgroundViewModelUTest {

    private Locale originalLocale;
    private MenuBackgroundViewModel viewModel;

    private GridPane sudokuFX;
    private ColorPicker colorPicker;
    private ToasterVBox toaster;
    private SpinnerGridPane spinner;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        viewModel = new MenuBackgroundViewModel();
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
            MenuBackgroundViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        map.put(
                vm.menuMaxiAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility"));
        map.put(
                vm.menuMaxiTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.closed"));
        map.put(
                vm.menuMaxiRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        map.put(
                vm.menuMaxiTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.background.text"));

        map.put(
                vm.reduceAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.reduce.accessibility"));
        map.put(
                vm.reduceTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.reduce.accessibility"));
        map.put(
                vm.reduceTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.reduce.text"));

        map.put(
                vm.backgroundAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.background.accessibility"));
        map.put(
                vm.backgroundTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.background.button.background.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.opened"));
        map.put(
                vm.backgroundRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        map.put(
                vm.backgroundTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.background.text"));

        map.put(
                vm.imageAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.image.accessibility"));
        map.put(
                vm.imageTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.background.button.image.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.imageRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.imageTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.image.text"));

        map.put(
                vm.colorAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.background.button.color.accessibility"));
        map.put(
                vm.colorTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.background.button.color.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.colorRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        return map;
    }

    @Test
    void givenViewModelAndColorPicker_whenInitCalled_thenColorIsSetAndColorPickerUpdated() {
        MenuBackgroundViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).handleFileImageChooser(any(), any(), any(), any());
        spyViewModel.init(sudokuFX, colorPicker, toaster, spinner);
        Color expectedColor = Color.rgb(153, 179, 255, 0.803921568627451);
        assertEquals(expectedColor, colorPicker.getValue());
        BackgroundFill fill = sudokuFX.getBackground().getFills().getFirst();
        assertEquals(expectedColor, fill.getFill());
    }

    @Test
    void
            givenValidPngImage_whenHandleFileImageChooserCalled_thenGridBackgroundIsSetAndInfoToastShown()
                    throws Exception {
        File validImage = getValidTestImage();
        assertTrue(validImage.exists());
        GridPane grid = new GridPane();
        ToasterVBox mockToaster = mock(ToasterVBox.class);
        SpinnerGridPane mockSpinner = mock(SpinnerGridPane.class);
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(
                () -> {
                    grid.backgroundProperty()
                            .addListener(
                                    (obs, oldVal, newVal) -> {
                                        if (newVal != null) {
                                            latch.countDown();
                                        }
                                    });
                    viewModel.handleFileImageChooser(validImage, mockToaster, mockSpinner, grid);
                });
        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timeout waiting for background to be set");
        Background background = grid.getBackground();
        assertNotNull(background, "Background should not be null");
        assertFalse(background.getImages().isEmpty(), "Background image should be set");
        verify(mockToaster)
                .addToast(anyString(), contains("file:"), eq(ToastLevels.INFO), eq(false));
        verify(mockToaster).removeToast();
        verify(mockSpinner).showSpinner(false);
    }

    @Test
    void givenImageTaskFails_whenHandleFileImageChooser_thenOnImageTaskErrorCalled()
            throws InterruptedException {
        File validImage = getValidTestImage();
        assertTrue(validImage.exists());
        GridPane grid = new GridPane();
        ToasterVBox mockToaster = mock(ToasterVBox.class);
        SpinnerGridPane mockSpinner = mock(SpinnerGridPane.class);
        viewModel = spy(new MenuBackgroundViewModel());
        CountDownLatch taskFailedLatch = new CountDownLatch(1);
        CountDownLatch fxRunLaterLatch = new CountDownLatch(1);
        doAnswer(
                        invocation -> {
                            Task<Image> failingTask =
                                    new Task<>() {
                                        @Override
                                        protected Image call() throws Exception {
                                            throw new RuntimeException("Forced failure");
                                        }
                                    };
                            failingTask.setOnFailed(
                                    e -> {
                                        viewModel.onImageTaskError(
                                                e,
                                                invocation.getArgument(1),
                                                invocation.getArgument(2));
                                        taskFailedLatch.countDown();
                                    });
                            Thread thread = new Thread(failingTask);
                            thread.setDaemon(true);
                            thread.start();
                            return null;
                        })
                .when(viewModel)
                .handleFileImageChooser(any(), any(), any(), any());
        doAnswer(
                        invocation -> {
                            fxRunLaterLatch.countDown();
                            return null;
                        })
                .when(mockToaster)
                .addToast(anyString(), eq("Forced failure"), eq(ToastLevels.ERROR), eq(true));
        Platform.runLater(
                () -> viewModel.handleFileImageChooser(validImage, mockToaster, mockSpinner, grid));
        assertTrue(taskFailedLatch.await(5, TimeUnit.SECONDS), "Timeout waiting for task failure");
        assertTrue(
                fxRunLaterLatch.await(5, TimeUnit.SECONDS),
                "Timeout waiting for addToast in FX thread");
        verify(mockToaster)
                .addToast(anyString(), eq("Forced failure"), eq(ToastLevels.ERROR), eq(true));
        verify(mockSpinner).showSpinner(false);
    }

    @Test
    void givenGridPaneAndColor_whenUpdateBackgroundColorAndApply_thenBackgroundColorIsSet() {
        GridPane grid = new GridPane();
        Color color = Color.web("#12345678");
        viewModel.updateBackgroundColorAndApply(grid, color);
        Background background = grid.getBackground();
        assertNotNull(background, "Background should not be null");
        List<BackgroundFill> fills = background.getFills();
        assertFalse(fills.isEmpty(), "Background fills should not be empty");
        assertEquals(color, fills.getFirst().getFill(), "Background color should match expected");
    }

    private File getValidTestImage() {
        try {
            URL resourceUrl =
                    Objects.requireNonNull(
                            SudoMain.class.getResource(LOGO_SUDO_PNG_PATH.getPath()));
            return new File(resourceUrl.toURI());
        } catch (Exception e) {
            throw new RuntimeException("Could not load test image", e);
        }
    }

    @Test
    void givenNullFile_whenHandleFileImageChooser_thenShowErrorToast() {
        MenuBackgroundViewModel myViewModel = spy(new MenuBackgroundViewModel());
        File nullFile = null;
        ToasterVBox toaster = mock(ToasterVBox.class);
        SpinnerGridPane spinner = mock(SpinnerGridPane.class);
        GridPane sudokuFX = new GridPane();
        String expectedErrorKey = "toast.error.backgroundviewmodel.handlefileimagechooser";
        String expectedMessage = I18n.INSTANCE.getValue(expectedErrorKey);
        myViewModel.handleFileImageChooser(nullFile, toaster, spinner, sudokuFX);
        verify(toaster).addToast(expectedMessage, "", ToastLevels.ERROR, true);
    }
}
