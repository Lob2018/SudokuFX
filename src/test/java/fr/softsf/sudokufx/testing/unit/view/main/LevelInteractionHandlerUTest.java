/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.view.main;

import java.util.function.Consumer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.view.main.LevelInteractionHandler;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class LevelInteractionHandlerUTest {

    private GridViewModel gridViewModel;
    private LevelInteractionHandler handler;
    private Consumer<Boolean> opaqueApplier;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        gridViewModel = mock(GridViewModel.class);
        MenuLevelViewModel menuLevelViewModel = mock(MenuLevelViewModel.class);
        MenuOptionsViewModel menuOptionsViewModel = mock(MenuOptionsViewModel.class);
        opaqueApplier = mock(Consumer.class);
        when(menuOptionsViewModel.gridOpacityProperty())
                .thenReturn(new SimpleBooleanProperty(false));
        handler =
                new LevelInteractionHandler(
                        menuOptionsViewModel, menuLevelViewModel, gridViewModel);
    }

    @Test
    void givenLevelButtonId_whenHandleStart_thenStartsCycle() {
        handler.handleStart("menuMiniButtonEasy");
        verify(gridViewModel).resetDesiredPossibilities();
    }

    @Test
    void givenLevelButtonId_whenHandleEnd_thenAppliesLevel() {
        Task<Integer> mockTask =
                new Task<>() {
                    @Override
                    protected Integer call() {
                        return 0;
                    }
                };
        when(gridViewModel.setCurrentGridTask(DifficultyLevel.EASY)).thenReturn(mockTask);

        handler.handleStart("menuMiniButtonEasy");
        handler.handleEnd("menuMiniButtonEasy", opaqueApplier);

        verify(gridViewModel).setCurrentGridTask(DifficultyLevel.EASY);
    }

    @Test
    void givenInteraction_whenStopCycleCalled_thenInterrupts() {
        handler.handleStart("menuMiniButtonEasy");
        handler.stopCycle();
        verify(gridViewModel, times(1)).resetDesiredPossibilities();
    }

    @Test
    void givenUnknownButtonId_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> handler.handleStart("unknownId"));
    }

    @Test
    void givenNullArguments_whenHandleEnd_thenThrowsIllegalArgumentException() {
        assertAll(
                () ->
                        assertThrows(
                                IllegalArgumentException.class,
                                () -> handler.handleEnd("menuMiniButtonEasy", null),
                                "Should throw IllegalArgumentException when opaqueApplier is"
                                        + " null"));
    }
}
