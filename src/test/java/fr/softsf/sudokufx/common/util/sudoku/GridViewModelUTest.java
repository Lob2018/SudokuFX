/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.util.stream.IntStream;
import java.util.*;
import javafx.beans.property.IntegerProperty;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;
import fr.softsf.sudokufx.viewmodel.state.AbstractPlayerStateTest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GridViewModelUTest extends AbstractPlayerStateTest {

    private GridViewModel viewModel;

    @BeforeEach
    void givenPlayerStateHolder_whenInitGridViewModel_thenViewModelInitialized() {
        viewModel =
                new GridViewModel(
                        new GridMaster(new JakartaValidator(null)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolderSpy);
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
        assertTrue(
                viewModel.getCellViewModels().stream()
                        .allMatch(vm -> vm.rawTextProperty().get().isEmpty()));
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
        var result = viewModel.getCellViewModelById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void givenNonExistingId_whenGetCellViewModelById_thenEmptyOptionalIsReturned() {
        var result = viewModel.getCellViewModelById(100);
        assertTrue(result.isEmpty());
    }

    @Test
    void givenGridCellViewModel_whenAccessorsCalled_thenReturnExpectedValues() {
        GridCellViewModel cell = viewModel.getCellViewModels().getFirst();
        IntegerProperty idProp = cell.idProperty();
        assertNotNull(idProp);
        assertEquals(cell.getId(), idProp.get());
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertNotNull(cell.getLabel());
        assertNotNull(cell.getTextArea());
    }
}
