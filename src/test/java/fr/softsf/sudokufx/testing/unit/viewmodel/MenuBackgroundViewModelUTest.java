/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.view.components.SpinnerGridPane;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.MenuBackgroundViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MenuBackgroundViewModelUTest {

    private MenuBackgroundViewModel viewModel;

    private GridPane sudokuFX;
    private ColorPicker colorPicker;
    private ToasterVBox toaster;
    private SpinnerGridPane spinner;

    @BeforeEach
    void setUp() {
        viewModel = new MenuBackgroundViewModel();
        sudokuFX = new GridPane();
        colorPicker = new ColorPicker();
        toaster = mock(ToasterVBox.class);
        spinner = mock(SpinnerGridPane.class);
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
}
