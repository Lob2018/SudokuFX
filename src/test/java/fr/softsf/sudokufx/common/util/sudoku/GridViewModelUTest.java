/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util.sudoku;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;
import javafx.beans.property.IntegerProperty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.JakartaValidator;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.GridDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.grid.CurrentGrid;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;
import fr.softsf.sudokufx.viewmodel.state.AbstractPlayerStateTest;

import static fr.softsf.sudokufx.common.enums.ToastLevels.INFO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class GridViewModelUTest extends AbstractPlayerStateTest {

    private GridViewModel viewModel;

    @BeforeEach
    void givenPlayerStateHolder_whenInitGridViewModel_thenViewModelInitialized() {
        JakartaValidator validatorMock = mock(JakartaValidator.class);
        doAnswer(invocation -> invocation.getArgument(0))
                .when(validatorMock)
                .validateOrThrow(any());
        viewModel =
                new GridViewModel(
                        new GridMaster(validatorMock),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter());
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
        viewModel.setValues(values, true);
        assertEquals(values, viewModel.getAllValues());
    }

    @Test
    void givenNullValues_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> viewModel.setValues(null, true));
    }

    @Test
    void givenWrongSizeList_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        List<String> shortList = Collections.nCopies(80, "1");
        assertThrows(IllegalArgumentException.class, () -> viewModel.setValues(shortList, true));
    }

    @Test
    void givenListWithNullElement_whenSetValues_thenIllegalArgumentExceptionIsThrown() {
        List<String> valuesWithNull = new ArrayList<>(Collections.nCopies(81, "1"));
        valuesWithNull.set(5, null);
        assertThrows(
                IllegalArgumentException.class, () -> viewModel.setValues(valuesWithNull, true));
    }

    @Test
    void givenNullLevel_whenSetCurrentGridWithLevel_thenNullPointerExceptionIsThrown() {
        assertThrows(NullPointerException.class, () -> viewModel.setCurrentGridWithLevel(null));
    }

    @Test
    void givenValidLevel_whenSetCurrentGridWithLevel_thenGridIsUpdatedAndPlayerServiceCalled() {
        var player = playerStateHolder.getCurrentPlayer();
        int percentage = viewModel.setCurrentGridWithLevel(DifficultyLevel.EASY);
        assertTrue(percentage >= 0 && percentage <= 100);
        verify(playerServiceMock).updatePlayer(any());
        assertNotNull(player.selectedGame());
        assertNotNull(player.selectedGame().grididDto().gridvalue());
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
    void
            givenPlayerWithDefaultGrid_whenGetCurrentGridFromModel_thenValuesLoadedAndCurrentGridReturned() {
        String defaultGrid = String.join("", Collections.nCopies(81, "0"));
        String gridValue = String.join(",", Collections.nCopies(81, "0"));
        GridDto newGridDto = new GridDto(1L, defaultGrid, gridValue, (byte) 100);
        GameDto newGameDto =
                Objects.requireNonNull(playerStateHolder.getCurrentPlayer().selectedGame())
                        .withGrididDto(newGridDto);
        PlayerDto newPlayer = playerStateHolder.getCurrentPlayer().withSelectedGame(newGameDto);
        when(playerServiceMock.getPlayer()).thenReturn(newPlayer);
        playerStateHolder = new TestablePlayerStateHolder(playerServiceMock);
        GridViewModel viewModelLocal =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter());
        viewModelLocal.init(new ToasterVBox());
        Optional<CurrentGrid> currentGrid = viewModelLocal.getCurrentGridFromModel();
        assertTrue(currentGrid.isPresent());
        assertNotNull(currentGrid.get());
        assertEquals(
                DifficultyLevel.fromGridByte(newGameDto.levelidDto().level()),
                currentGrid.get().level());
        assertEquals(newGameDto.grididDto().possibilities(), currentGrid.get().percentage());
        assertTrue(viewModelLocal.getAllValues().stream().allMatch(v -> v.equals("0")));
    }

    @Test
    void givenPlayerWithCompletedGrid_whenGridIsComplete_thenToastTriggered() {
        ToasterVBox toasterMock = mock(ToasterVBox.class);
        GridViewModel gridVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter());
        gridVM.init(toasterMock);
        List<String> completedGrid =
                Arrays.asList(
                        "3", "1", "6", "9", "7", "2", "4", "5", "8", "7", "8", "9", "4", "5", "3",
                        "2", "6", "1", "4", "2", "5", "8", "1", "6", "9", "3", "7", "6", "4", "1",
                        "7", "2", "8", "3", "9", "5", "8", "9", "7", "5", "3", "4", "1", "2", "6",
                        "5", "3", "2", "1", "6", "9", "7", "8", "4", "2", "5", "4", "6", "9", "1",
                        "8", "7", "3", "1", "7", "3", "2", "8", "5", "6", "4", "9", "9", "6", "8",
                        "3", "4", "7", "5", "1", "2");
        gridVM.setValues(completedGrid, true);
        assertTrue(gridVM.getCellViewModelById(1).isPresent());
        gridVM.getCellViewModelById(1).get().getTextArea().setText("");
        gridVM.getCellViewModelById(1).get().getTextArea().setText("3");
        verify(toasterMock, atLeastOnce())
                .addToast(
                        eq(
                                MessageFormat.format(
                                        I18n.INSTANCE.getValue("toast.msg.gridviewmodel.completed"),
                                        playerStateHolder.getCurrentPlayer().name())),
                        anyString(),
                        eq(INFO),
                        eq(false));
    }

    @Test
    void givenPlayerWithoutDefaultGrid_whenGetCurrentSolvedGridFromModel_thenReturnEmptyOptional() {
        GridDto gridDto = new GridDto(1L, "", "", (byte) 50);
        GameDto gameDto =
                Objects.requireNonNull(playerStateHolder.getCurrentPlayer().selectedGame())
                        .withGrididDto(gridDto);
        PlayerDto player = playerStateHolder.getCurrentPlayer().withSelectedGame(gameDto);
        when(playerServiceMock.getPlayer()).thenReturn(player);
        playerStateHolder = new TestablePlayerStateHolder(playerServiceMock);
        GridViewModel localVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter());
        localVM.init(new ToasterVBox());
        Optional<CurrentGrid> result = localVM.getCurrentSolvedGridFromModel();
        assertTrue(result.isEmpty());
    }

    @Test
    void
            givenPlayerWithDefaultGrid_whenGetCurrentSolvedGridFromModel_thenSolvedGridAndCurrentGridReturned() {
        String defaultGrid = String.join("", Collections.nCopies(81, "0"));
        GridDto gridDto = new GridDto(1L, defaultGrid, "", (byte) 30);
        GameDto gameDto =
                Objects.requireNonNull(playerStateHolder.getCurrentPlayer().selectedGame())
                        .withGrididDto(gridDto);
        PlayerDto player = playerStateHolder.getCurrentPlayer().withSelectedGame(gameDto);
        when(playerServiceMock.getPlayer()).thenReturn(player);
        playerStateHolder = new TestablePlayerStateHolder(playerServiceMock);
        GridViewModel localVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter());
        localVM.init(new ToasterVBox());
        Optional<CurrentGrid> result = localVM.getCurrentSolvedGridFromModel();
        assertTrue(result.isPresent());
        CurrentGrid currentGrid = result.get();
        assertEquals(
                DifficultyLevel.fromGridByte(gameDto.levelidDto().level()), currentGrid.level());
        assertEquals(0, currentGrid.percentage());
        assertEquals(81, localVM.getAllValues().size());
    }
}
