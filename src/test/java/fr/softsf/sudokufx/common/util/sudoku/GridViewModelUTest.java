/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javafx.beans.property.IntegerProperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
class GridViewModelUTest {

    private GridViewModel viewModel;

    @BeforeEach
    void setUp() {
        viewModel =
                new GridViewModel(
                        new GridMaster(new JakartaValidator(null)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        new PlayerStateHolder(mock(PlayerService.class)));
        viewModel.init(new ToasterVBox());
    }

    @Test
    void givenViewModelInitialized_whenInit_then81CellsAreCreated() {
        assertEquals(81, viewModel.getCellViewModels().size());
    }

    @Test
    void givenFilledGrid_whenClearGrid_thenAllCellsAreEmpty() {
        viewModel.getCellViewModels().forEach(vm -> vm.rawTextProperty().set("5"));
        viewModel.clearGrid();
        boolean allEmpty =
                viewModel.getCellViewModels().stream()
                        .allMatch(vm -> vm.rawTextProperty().get().isEmpty());
        assertTrue(allEmpty);
    }

    @Test
    void givenValidValues_whenSetValues_thenAllCellsContainCorrectValues() {
        List<String> values = IntStream.rangeClosed(1, 81).mapToObj(String::valueOf).toList();
        viewModel.setValues(values);
        assertEquals(values, viewModel.getAllValues());
    }

    @Test
    void givenNullValues_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> viewModel.setValues(null));
    }

    @Test
    void givenWrongSizeList_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        List<String> shortList = Collections.nCopies(80, "1");
        assertThrows(IllegalArgumentException.class, () -> viewModel.setValues(shortList));
    }

    @Test
    void givenListWithNullElement_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        List<String> valuesWithNull = new ArrayList<>(Collections.nCopies(81, "1"));
        valuesWithNull.set(5, null);
        assertThrows(IllegalArgumentException.class, () -> viewModel.setValues(valuesWithNull));
    }

    @Test
    void givenNullLevel_whenApplyLevel_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> viewModel.applyLevel(null));
    }

    @Test
    void givenExistingId_whenGetCellViewModelById_thenCellIsReturned() {
        Optional<GridCellViewModel> result = viewModel.getCellViewModelById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void givenNonExistingId_whenGetCellViewModelById_thenEmptyOptionalIsReturned() {
        Optional<GridCellViewModel> result = viewModel.getCellViewModelById(100);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenGridCellViewModel_whenAccessorsCalled_thenReturnExpectedValues() {
        GridCellViewModel cellViewModel = viewModel.getCellViewModels().getFirst();
        IntegerProperty idProperty = cellViewModel.idProperty();
        assertNotNull(idProperty);
        assertEquals(cellViewModel.getId(), idProperty.get());
        assertEquals(0, cellViewModel.getRow());
        assertEquals(0, cellViewModel.getCol());
        assertNotNull(cellViewModel.getLabel());
        assertNotNull(cellViewModel.getTextArea());
    }
}
