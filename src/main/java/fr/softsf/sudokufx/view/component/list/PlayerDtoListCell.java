/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.component.list;

import java.text.MessageFormat;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.dto.PlayerDto;

/**
 * A custom {@code ListCell} for displaying a {@code PlayerDto} in a {@code ListView}, with a label
 * and a button that removes the item from the list after user confirmation.
 *
 * <p>The button and confirmation dialog are fully internationalized using {@code StringBinding}.
 * The cell updates its accessibility and tooltip text accordingly.
 */
public final class PlayerDtoListCell extends ListCell<PlayerDto> {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerDtoListCell.class);

    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();
    private final ListView<PlayerDto> listView;
    private final Alert confirmationAlert;
    private final StringBinding buttonAccessibleTextBinding;
    private final StringBinding confirmationTitleBinding;
    private final StringBinding confirmationMessageBinding;

    public PlayerDtoListCell(
            ListView<PlayerDto> listView,
            String buttonText,
            StringBinding buttonAccessibleTextBinding,
            StringBinding confirmationTitleBinding,
            StringBinding confirmationMessageBinding,
            Alert confirmationAlert) {

        this.listView = Objects.requireNonNull(listView, "listView must not be null");
        this.confirmationTitleBinding =
                Objects.requireNonNull(
                        confirmationTitleBinding, "confirmationTitle must not be null");
        this.confirmationMessageBinding =
                Objects.requireNonNull(
                        confirmationMessageBinding, "confirmationMessage must not be null");
        this.buttonAccessibleTextBinding =
                Objects.requireNonNull(
                        buttonAccessibleTextBinding, "buttonAccessibleText must not be null");
        this.confirmationAlert =
                Objects.requireNonNull(confirmationAlert, "confirmationAlert must not be null");

        HBox.setHgrow(label, Priority.ALWAYS);
        hBox.setFocusTraversable(false);
        label.setFocusTraversable(true);
        button.setFocusTraversable(true);
        button.getStyleClass().addAll("material", "menuListLineButton");
        button.setText(buttonText);
        label.getStyleClass().addAll("root", "menuButtonLabel");
        hBox.getStyleClass().add("menuListLineContainer");
        hBox.getChildren().addAll(label, button);
    }

    /**
     * Updates the visual representation of the cell based on the given {@code PlayerDto} item. If
     * the item is not empty, sets up the label and button with localized text and accessibility
     * metadata.
     *
     * @param item the {@code PlayerDto} to display
     * @param empty whether this cell represents data from the list
     */
    @Override
    protected void updateItem(PlayerDto item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setAccessibleText(null);
            button.setAccessibleText(null);
            button.setTooltip(null);
            button.setOnAction(null);
        } else {
            String name = item.name();
            label.setText(name);
            setGraphic(hBox);
            setAccessibleText(name);
            button.setAccessibleText(MessageFormat.format(buttonAccessibleTextBinding.get(), name));
            button.setTooltip(
                    new Tooltip(MessageFormat.format(buttonAccessibleTextBinding.get(), name)));
            button.setOnAction(
                    event -> {
                        if (getItem() != null) {
                            confirmAndRemoveItem(getItem());
                        } else {
                            LOG.warn("▓▓ getItem() returned null in ItemListCell button action.");
                        }
                    });
        }
    }

    /**
     * Displays a confirmation dialog using localized messages. If the user confirms, removes the
     * specified {@code PlayerDto} from the {@code ListView}.
     *
     * @param item the {@code PlayerDto} to remove upon confirmation
     */
    private void confirmAndRemoveItem(PlayerDto item) {
        String name = item.name();
        confirmationAlert.setTitle(confirmationTitleBinding.get());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(
                MessageFormat.format(confirmationMessageBinding.get(), name));
        confirmationAlert
                .showAndWait()
                .ifPresent(
                        response -> {
                            if (response.getText().equals("OK")) {
                                listView.getItems().remove(item);
                            }
                        });
    }
}
