/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
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
import fr.softsf.sudokufx.service.ui.SpinnerService;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.grid.CurrentGrid;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;
import fr.softsf.sudokufx.viewmodel.state.AbstractPlayerStateTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class GridViewModelUTest extends AbstractPlayerStateTest {

    private GridViewModel viewModel;
    private ToasterService toasterServiceMock;
    private SpinnerService spinnerServiceMock;

    @BeforeEach
    void setUp() {
        JakartaValidator validatorMock = mock(JakartaValidator.class);
        doAnswer(invocation -> invocation.getArgument(0))
                .when(validatorMock)
                .validateOrThrow(any());
        toasterServiceMock = mock(ToasterService.class);
        spinnerServiceMock = mock(SpinnerService.class);
        viewModel =
                new GridViewModel(
                        new GridMaster(validatorMock),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter(),
                        toasterServiceMock,
                        spinnerServiceMock);
        viewModel.init();
    }

    @Test
    void givenViewModelInitialized_whenInit_then81CellsAreCreated() {
        assertEquals(81, viewModel.getCellViewModels().size());
    }

    @Test
    void givenFilledGrid_whenClearGrid_thenAllCellsAreZero() {
        viewModel.getCellViewModels().forEach(vm -> vm.rawTextProperty().set("5"));
        viewModel.clearGrid();
        assertTrue(
                viewModel.getCellViewModels().stream()
                        .allMatch(vm -> vm.rawTextProperty().get().equals("0")));
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
    void givenNullLevel_whenSetCurrentGridWithLevel_ForTests_thenNullPointerExceptionIsThrown() {
        assertThrows(
                NullPointerException.class, () -> viewModel.setCurrentGridWithLevelForTests(null));
    }

    @Test
    void
            givenValidLevel_whenSetCurrentGridWithLevel_thenGridIsUpdatedAndPlayerServiceCalledForTests() {
        int percentage = viewModel.setCurrentGridWithLevelForTests(DifficultyLevel.EASY);
        assertTrue(percentage >= 0 && percentage <= 100);
        verify(playerServiceMock).updatePlayer(any());
        PlayerDto player = playerStateHolder.getCurrentPlayer();
        assertNotNull(player.selectedGame());
        assertNotNull(player.selectedGame().grididDto().gridvalue());
    }

    @Test
    void givenGridCellViewModel_whenAccessorsCalled_thenReturnExpectedValues() {
        GridCellViewModel cell = viewModel.getCellViewModels().getFirst();
        assertEquals(0, cell.getRow());
        assertEquals(0, cell.getCol());
        assertEquals(1, cell.getId());
        assertNotNull(cell.getLabel());
        assertNotNull(cell.getTextArea());
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
        playerStateHolder.init();
        GridViewModel localVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter(),
                        toasterServiceMock,
                        spinnerServiceMock);
        localVM.init();
        Optional<CurrentGrid> currentGrid = localVM.getCurrentGridFromModel();
        assertTrue(currentGrid.isPresent());
        assertEquals(
                DifficultyLevel.fromGridByte(newGameDto.levelidDto().level()),
                currentGrid.get().level());
        assertEquals(newGameDto.grididDto().possibilities(), currentGrid.get().percentage());
        assertTrue(localVM.getAllValues().stream().allMatch(v -> v.equals("0")));
    }

    @Test
    void givenPlayerWithCompletedGrid_whenGridIsComplete_thenToastTriggered() {
        List<String> completedGrid =
                Arrays.asList(
                        "3", "1", "6", "9", "7", "2", "4", "5", "8", "7", "8", "9", "4", "5", "3",
                        "2", "6", "1", "4", "2", "5", "8", "1", "6", "9", "3", "7", "6", "4", "1",
                        "7", "2", "8", "3", "9", "5", "8", "9", "7", "5", "3", "4", "1", "2", "6",
                        "5", "3", "2", "1", "6", "9", "7", "8", "4", "2", "5", "4", "6", "9", "1",
                        "8", "7", "3", "1", "7", "3", "2", "8", "5", "6", "4", "9", "9", "6", "8",
                        "3", "4", "7", "5", "1", "2");
        viewModel.setValues(completedGrid, true);
        GridCellViewModel firstCell = viewModel.getCellViewModels().getFirst();
        firstCell.getTextArea().setText("");
        firstCell.getTextArea().setText("3");
        verify(toasterServiceMock, atLeastOnce())
                .showInfo(
                        MessageFormat.format(
                                I18n.INSTANCE.getValue("toast.msg.gridviewmodel.completed"),
                                playerStateHolder.getCurrentPlayer().name()),
                        "");
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
        playerStateHolder.init();
        GridViewModel localVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter(),
                        toasterServiceMock,
                        spinnerServiceMock);
        localVM.init();
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
        playerStateHolder.init();
        GridViewModel localVM =
                new GridViewModel(
                        new GridMaster(mock(JakartaValidator.class)),
                        new ActiveMenuOrSubmenuViewModel(),
                        new AudioService(),
                        playerStateHolder,
                        playerServiceMock,
                        new GridConverter(),
                        toasterServiceMock,
                        spinnerServiceMock);
        localVM.init();
        Optional<CurrentGrid> result = localVM.getCurrentSolvedGridFromModel();
        assertTrue(result.isPresent());
        CurrentGrid currentGrid = result.get();
        assertEquals(
                DifficultyLevel.fromGridByte(gameDto.levelidDto().level()), currentGrid.level());
        assertEquals(0, currentGrid.percentage());
        assertEquals(81, localVM.getAllValues().size());
    }

    @Test
    void givenModifiedState_whenResetDesiredPossibilities_thenReturnsDefaultValue() {
        assertEquals(-1, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        assertNotEquals(-1, viewModel.getDesiredPossibilities());
        viewModel.resetDesiredPossibilities();
        assertEquals(-1, viewModel.getDesiredPossibilities());
    }

    @Test
    void givenInitialState_whenResetDesiredPossibilities_thenValueIsDefault() {
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        viewModel.resetDesiredPossibilities();
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        assertEquals(IGridMaster.FACILE_MIN_PERCENT, viewModel.getDesiredPossibilities());
    }

    @Test
    void givenEasyLevel_whenIncrementDesiredPossibilities_thenCyclesCorrectly() {
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        assertEquals(0, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        assertEquals(10, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        assertEquals(0, viewModel.getDesiredPossibilities());
    }

    @Test
    void givenMediumLevel_whenIncrementDesiredPossibilities_thenStartsAtMediumMin() {
        viewModel.incrementDesiredPossibilities(DifficultyLevel.MEDIUM);
        assertEquals(IGridMaster.MOYEN_MIN_PERCENT, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.MEDIUM);
        assertEquals(IGridMaster.MOYEN_MIN_PERCENT, viewModel.getDesiredPossibilities());
    }

    @Test
    void givenDifficultLevel_whenIncrementDesiredPossibilities_thenStepsCorrectly() {
        viewModel.incrementDesiredPossibilities(DifficultyLevel.DIFFICULT);
        assertEquals(27, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.DIFFICULT);
        assertEquals(30, viewModel.getDesiredPossibilities());
        for (int i = 0; i < 6; i++) {
            viewModel.incrementDesiredPossibilities(DifficultyLevel.DIFFICULT);
        }
        assertEquals(90, viewModel.getDesiredPossibilities());
        viewModel.incrementDesiredPossibilities(DifficultyLevel.DIFFICULT);
        assertEquals(27, viewModel.getDesiredPossibilities());
    }

    @Test
    void givenLevel_whenNotifyLevelPossibilityBounds_thenToasterServiceIsCalled() {
        viewModel.incrementDesiredPossibilities(DifficultyLevel.EASY);
        viewModel.notifyLevelPossibilityBounds(DifficultyLevel.EASY);
        verify(toasterServiceMock).requestRemoveToast();
        verify(toasterServiceMock)
                .showInfo(argThat(msg -> msg.contains("0") && msg.contains("10")), eq(""));
    }
}
