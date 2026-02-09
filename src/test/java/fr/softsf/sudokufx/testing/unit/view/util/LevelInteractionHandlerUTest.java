/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.view.util;

import java.util.function.Consumer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.view.util.LevelInteractionHandler;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class LevelInteractionHandlerUTest {

    private GridViewModel gridViewModel;
    private MenuLevelViewModel menuLevelViewModel;
    private MenuOptionsViewModel menuOptionsViewModel;
    private ToasterService toasterService;
    private LevelInteractionHandler handler;
    private Consumer<Boolean> opaqueApplier;

    @BeforeEach
    void setUp() {
        gridViewModel = mock(GridViewModel.class);
        menuLevelViewModel = mock(MenuLevelViewModel.class);
        menuOptionsViewModel = mock(MenuOptionsViewModel.class);
        toasterService = mock(ToasterService.class);
        opaqueApplier = mock(Consumer.class);
        when(menuOptionsViewModel.gridOpacityProperty())
                .thenReturn(new SimpleBooleanProperty(false));
        handler =
                new LevelInteractionHandler(
                        menuOptionsViewModel, menuLevelViewModel, gridViewModel, toasterService);
    }

    @Test
    void givenLevelButton_whenMousePressed_thenStartsCycle() {
        Button btn = new Button();
        btn.setId("menuMiniButtonEasy");
        MouseEvent event =
                new MouseEvent(
                        btn,
                        btn,
                        MouseEvent.MOUSE_PRESSED,
                        0,
                        0,
                        0,
                        0,
                        MouseButton.PRIMARY,
                        1,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        false,
                        false,
                        false,
                        false,
                        null);
        handler.handleAction(event, opaqueApplier);
        verify(gridViewModel).resetDesiredPossibilities();
    }

    @Test
    void givenLevelButton_whenMouseReleased_thenAppliesLevel() {
        Button btn = new Button();
        btn.setId("menuMiniButtonEasy");
        MouseEvent event =
                new MouseEvent(
                        btn,
                        btn,
                        MouseEvent.MOUSE_RELEASED,
                        0,
                        0,
                        0,
                        0,
                        MouseButton.PRIMARY,
                        1,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        false,
                        false,
                        false,
                        false,
                        null);
        when(gridViewModel.setCurrentGridWithLevel(DifficultyLevel.EASY)).thenReturn(0);
        handler.handleAction(event, opaqueApplier);
        verify(gridViewModel).setCurrentGridWithLevel(DifficultyLevel.EASY);
        verify(menuLevelViewModel).updateSelectedLevel(eq(DifficultyLevel.EASY), eq(0));
        verify(opaqueApplier).accept(anyBoolean());
    }

    @Test
    void givenLevelButton_whenKeyPressedEnter_thenStartsCycle() {
        Button btn = new Button();
        btn.setId("menuMaxiButtonDifficult");
        KeyEvent event =
                new KeyEvent(
                        btn,
                        btn,
                        KeyEvent.KEY_PRESSED,
                        "",
                        "",
                        KeyCode.ENTER,
                        false,
                        false,
                        false,
                        false);
        handler.handleAction(event, opaqueApplier);
        verify(gridViewModel).resetDesiredPossibilities();
    }

    @Test
    void givenUnknownButtonId_whenEndEvent_thenThrowsException() {
        Button btn = new Button();
        btn.setId("unknownId");
        MouseEvent event =
                new MouseEvent(
                        btn,
                        btn,
                        MouseEvent.MOUSE_RELEASED,
                        0,
                        0,
                        0,
                        0,
                        MouseButton.PRIMARY,
                        1,
                        false,
                        false,
                        false,
                        false,
                        true,
                        false,
                        false,
                        false,
                        false,
                        false,
                        null);
        assertThrows(
                IllegalArgumentException.class, () -> handler.handleAction(event, opaqueApplier));
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenNullArguments_whenHandleAction_thenThrowsNullPointerException() {
        MouseEvent event = mock(MouseEvent.class);
        assertAll(
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () -> handler.handleAction(null, opaqueApplier)),
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () -> handler.handleAction(event, null)));
    }
}
