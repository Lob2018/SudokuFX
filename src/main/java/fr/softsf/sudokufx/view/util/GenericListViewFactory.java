/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.util;

import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.Property;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.view.component.list.GenericDtoListCell;
import fr.softsf.sudokufx.viewmodel.MenuPlayerViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSaveViewModel;

/**
 * Factory responsible for configuring {@link ListView} components with custom cells and bindings.
 *
 * <p>Centralizes repetitive setup logic for UI components in the main view, including:
 *
 * <ul>
 *   <li>Rounded clipping for visual consistency
 *   <li>Custom cell factories with accessibility and confirmation support
 *   <li>Bidirectional selection binding between the view and its ViewModel
 * </ul>
 *
 * <p>This class is designed to be injected via Spring and used to streamline menu initialization.
 */
@Component
public class GenericListViewFactory {

    public static final String LIST_VIEW_MUST_NOT_BE_NULL = "listView must not be null";
    public static final String CLIP_VIEW_MUST_NOT_BE_NULL = "clipView must not be null";

    /**
     * Configures the {@link ListView} used in the player menu.
     *
     * <p>Sets up clipping, populates the list with players, applies a custom cell factory, and
     * synchronizes selection with the {@link MenuPlayerViewModel}.
     *
     * @param listView the ListView to configure (must not be {@code null})
     * @param clipView the Rectangle used for rounded clipping (must not be {@code null})
     * @param viewModel the ViewModel providing data and bindings (must not be {@code null})
     * @throws NullPointerException if any argument is {@code null}
     */
    public void configurePlayerListView(
            ListView<PlayerDto> listView, Rectangle clipView, MenuPlayerViewModel viewModel) {
        Objects.requireNonNull(listView, LIST_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(clipView, CLIP_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(viewModel, "viewModel must not be null");
        setupListViewClip(listView, clipView);
        listView.setItems(
                Objects.requireNonNull(
                        viewModel.getPlayers(), "getPlayers() must not return null"));
        listView.setCellFactory(
                param ->
                        new GenericDtoListCell<>(
                                listView,
                                "\uef67",
                                viewModel.cellButtonAccessibleTextProperty(),
                                viewModel.cellConfirmationTitleProperty(),
                                viewModel.cellConfirmationMessageProperty(),
                                PlayerDto::name));
        setupBidirectionalSelection(listView, viewModel.selectedPlayerProperty());
        Platform.runLater(
                () -> {
                    listView.refresh();
                    listView.scrollTo(viewModel.selectedPlayerProperty().get());
                });
    }

    /**
     * Configures the {@link ListView} used in the save menu.
     *
     * <p>Sets up clipping, populates the list with saved games, applies a custom cell factory, and
     * synchronizes selection with the {@link MenuSaveViewModel}.
     *
     * @param listView the ListView to configure (must not be {@code null})
     * @param clipView the Rectangle used for rounded clipping (must not be {@code null})
     * @param viewModel the ViewModel providing data and bindings (must not be {@code null})
     * @throws NullPointerException if any argument is {@code null}
     */
    public void configureGameListView(
            ListView<GameDto> listView, Rectangle clipView, MenuSaveViewModel viewModel) {
        Objects.requireNonNull(listView, LIST_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(clipView, CLIP_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(viewModel, "viewModel must not be null");
        setupListViewClip(listView, clipView);
        listView.setItems(
                Objects.requireNonNull(
                        viewModel.getBackups(), "getBackups() must not return null"));
        listView.setCellFactory(
                param ->
                        new GenericDtoListCell<>(
                                listView,
                                "\ue92b",
                                viewModel.cellDeleteAccessibleTextProperty(),
                                viewModel.cellConfirmationTitleProperty(),
                                viewModel.cellConfirmationMessageProperty(),
                                gameDto -> {
                                    if (gameDto == null) {
                                        return "";
                                    }
                                    return MyDateTime.INSTANCE.getFormatted(gameDto.updatedat());
                                }));
        setupBidirectionalSelection(listView, viewModel.selectedBackupProperty());
        Platform.runLater(
                () -> {
                    listView.refresh();
                    listView.scrollTo(viewModel.selectedBackupProperty().get());
                });
    }

    /**
     * Applies rounded clipping to the given {@link ListView} using the provided {@link Rectangle}.
     *
     * <p>The clip adapts dynamically to the ListView's size and applies a consistent arc ratio.
     *
     * @param listView the ListView to clip (must not be {@code null})
     * @param clipView the Rectangle used as the clipping mask (must not be {@code null})
     * @throws NullPointerException if any argument is {@code null}
     */
    private void setupListViewClip(ListView<?> listView, Rectangle clipView) {
        Objects.requireNonNull(listView, LIST_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(clipView, CLIP_VIEW_MUST_NOT_BE_NULL);
        clipView.widthProperty().bind(listView.widthProperty());
        clipView.heightProperty().bind(listView.heightProperty());
        DoubleBinding radiusBinding = listView.widthProperty().divide(7);
        clipView.arcWidthProperty().bind(radiusBinding);
        clipView.arcHeightProperty().bind(radiusBinding);
    }

    /**
     * Configures a {@link ListView} to update the provided {@link Property} only upon explicit user
     * confirmation.
     *
     * <p>This method sets up event handlers for {@code ENTER}/{@code SPACE} key presses and
     * double-click mouse events to commit the current list selection to the model. This prevents
     * premature updates during list navigation.
     *
     * @param listView the {@link ListView} to configure; must not be {@code null}
     * @param selectedProperty the model property to be updated upon confirmation; must not be
     *     {@code null}
     * @param <T> the type of items in the ListView
     * @throws NullPointerException if any argument is {@code null}
     */
    private <T> void setupBidirectionalSelection(
            ListView<T> listView, Property<T> selectedProperty) {
        Objects.requireNonNull(listView, LIST_VIEW_MUST_NOT_BE_NULL);
        Objects.requireNonNull(selectedProperty, "selectedProperty must not be null");
        listView.addEventFilter(
                KeyEvent.KEY_PRESSED,
                event -> {
                    if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
                        T selectedItem = listView.getSelectionModel().getSelectedItem();
                        if (selectedItem != null) {
                            selectedProperty.setValue(selectedItem);
                            event.consume();
                        }
                    }
                });
        listView.setOnMouseClicked(
                event -> {
                    if (event.getClickCount() == 2) {
                        T selectedItem = listView.getSelectionModel().getSelectedItem();
                        if (selectedItem != null) {
                            selectedProperty.setValue(selectedItem);
                        }
                    }
                });
    }
}
