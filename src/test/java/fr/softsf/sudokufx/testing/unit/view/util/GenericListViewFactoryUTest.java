/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.view.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.view.util.GenericListViewFactory;
import fr.softsf.sudokufx.viewmodel.MenuPlayerViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSaveViewModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class GenericListViewFactoryUTest {

    private final GenericListViewFactory factory = new GenericListViewFactory();

    private PlayerDto createPlayer(String name, long id) {
        return new PlayerDto(
                id,
                new PlayerLanguageDto(id, "FR"),
                new OptionsDto(id, "#FFFFFF", "", "", true, true),
                new MenuDto((byte) 1, (byte) 1),
                null,
                name,
                false,
                LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant(),
                LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant());
    }

    private GameDto createGame(long id) {
        GridDto grid = new GridDto(id, "0".repeat(81), "0".repeat(810), (byte) 50);
        GameLevelDto level = new GameLevelDto((byte) 1, (byte) 2);
        return new GameDto(
                id,
                grid,
                100L,
                level,
                false,
                LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant(),
                LocalDateTime.of(2024, 1, 1, 10, 0).atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void givenValidViewModel_whenConfigurePlayerListView_thenSelectionIsBound() {
        PlayerDto player1 = createPlayer("Jean", 1L);
        PlayerDto player2 = createPlayer("Alice", 2L);
        ObservableList<PlayerDto> players = FXCollections.observableArrayList(player1, player2);
        SimpleObjectProperty<PlayerDto> selected = new SimpleObjectProperty<>(player1);
        MenuPlayerViewModel viewModel = mock(MenuPlayerViewModel.class);
        when(viewModel.getPlayers()).thenReturn(players);
        when(viewModel.selectedPlayerProperty()).thenReturn(selected);
        when(viewModel.cellButtonAccessibleTextProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Select";
                            }
                        });
        when(viewModel.cellConfirmationTitleProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Confirm";
                            }
                        });
        when(viewModel.cellConfirmationMessageProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Are you sure?";
                            }
                        });
        ListView<PlayerDto> listView = new ListView<>();
        Rectangle clip = new Rectangle();
        factory.configurePlayerListView(listView, clip, viewModel);
        assertEquals(player1, listView.getSelectionModel().getSelectedItem());
        listView.getSelectionModel().select(player2);
        assertEquals(player2, selected.get());
        selected.set(player1);
        assertEquals(player1, listView.getSelectionModel().getSelectedItem());
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenNullArguments_whenConfigurePlayerListView_thenThrowsNullPointerException() {
        MenuPlayerViewModel dummy = mock(MenuPlayerViewModel.class);
        when(dummy.getPlayers()).thenReturn(FXCollections.emptyObservableList());
        when(dummy.selectedPlayerProperty()).thenReturn(new SimpleObjectProperty<>());
        when(dummy.cellButtonAccessibleTextProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        when(dummy.cellConfirmationTitleProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        when(dummy.cellConfirmationMessageProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        assertAll(
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () ->
                                        factory.configurePlayerListView(
                                                null, new Rectangle(), dummy)),
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () ->
                                        factory.configurePlayerListView(
                                                new ListView<>(), null, dummy)),
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () ->
                                        factory.configurePlayerListView(
                                                new ListView<>(), new Rectangle(), null)));
    }

    @Test
    void givenValidViewModel_whenConfigureGameListView_thenSelectionIsBound() {
        GameDto game1 = createGame(1L);
        GameDto game2 = createGame(2L);
        ObservableList<GameDto> backups = FXCollections.observableArrayList(game1, game2);
        SimpleObjectProperty<GameDto> selected = new SimpleObjectProperty<>(game1);
        MenuSaveViewModel viewModel = mock(MenuSaveViewModel.class);
        when(viewModel.getBackups()).thenReturn(backups);
        when(viewModel.selectedBackupProperty()).thenReturn(selected);
        when(viewModel.cellDeleteAccessibleTextProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Delete";
                            }
                        });
        when(viewModel.cellConfirmationTitleProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Confirm";
                            }
                        });
        when(viewModel.cellConfirmationMessageProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "Are you sure?";
                            }
                        });
        ListView<GameDto> listView = new ListView<>();
        Rectangle clip = new Rectangle();
        factory.configureGameListView(listView, clip, viewModel);
        assertEquals(backups, listView.getItems());
        assertEquals(game1, listView.getSelectionModel().getSelectedItem());
        listView.getSelectionModel().select(game2);
        assertEquals(game2, selected.get());
        selected.set(game1);
        assertEquals(game1, listView.getSelectionModel().getSelectedItem());
    }

    @Test
    @SuppressWarnings("java:S5778")
    void givenNullArguments_whenConfigureGameListView_thenThrowsNullPointerException() {
        MenuSaveViewModel dummy = mock(MenuSaveViewModel.class);
        when(dummy.getBackups()).thenReturn(FXCollections.emptyObservableList());
        when(dummy.selectedBackupProperty()).thenReturn(new SimpleObjectProperty<>());
        when(dummy.cellDeleteAccessibleTextProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        when(dummy.cellConfirmationTitleProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        when(dummy.cellConfirmationMessageProperty())
                .thenReturn(
                        new StringBinding() {
                            @Override
                            protected String computeValue() {
                                return "";
                            }
                        });
        assertAll(
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () -> factory.configureGameListView(null, new Rectangle(), dummy)),
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () -> factory.configureGameListView(new ListView<>(), null, dummy)),
                () ->
                        assertThrows(
                                NullPointerException.class,
                                () ->
                                        factory.configureGameListView(
                                                new ListView<>(), new Rectangle(), null)));
    }
}
