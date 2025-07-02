/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.component.list;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic {@code ListCell} for displaying and removing items of type {@code T} in a {@code
 * ListView}.
 *
 * <p>Displays a label with text obtained from a provided function, and a button that removes the
 * item after user confirmation via a localized dialog. Accessibility and tooltips are dynamically
 * updated using {@link StringBinding}s.
 *
 * @param <T> the type of items displayed in the ListView
 */
public final class GenericDtoListCell<T> extends ListCell<T> {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDtoListCell.class);

    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();

    private final ListView<T> listView;
    private final Alert confirmationAlert;

    private final StringBinding buttonAccessibleTextBinding;
    private final StringBinding confirmationTitleBinding;
    private final StringBinding confirmationMessageBinding;

    private final Function<T, String> displayTextFunction;

    public GenericDtoListCell(
            ListView<T> listView,
            String buttonText,
            StringBinding buttonAccessibleTextBinding,
            StringBinding confirmationTitleBinding,
            StringBinding confirmationMessageBinding,
            Alert confirmationAlert,
            Function<T, String> displayTextFunction) {
        this.listView = Objects.requireNonNull(listView);
        this.confirmationTitleBinding = Objects.requireNonNull(confirmationTitleBinding);
        this.confirmationMessageBinding = Objects.requireNonNull(confirmationMessageBinding);
        this.buttonAccessibleTextBinding = Objects.requireNonNull(buttonAccessibleTextBinding);
        this.confirmationAlert = Objects.requireNonNull(confirmationAlert);
        this.displayTextFunction = Objects.requireNonNull(displayTextFunction);
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
     * Updates the cell's content or clears it if empty.
     *
     * <p>If the item is non-null, displays the label text and configures the remove button with
     * accessibility and tooltip texts. The button action shows a confirmation dialog before
     * removing the item.
     *
     * @param item the item to display
     * @param empty true if this cell is empty
     */
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setAccessibleText(null);
            button.setAccessibleText(null);
            button.setTooltip(null);
            button.setOnAction(null);
        } else {
            String displayText = displayTextFunction.apply(item);
            label.setText(displayText);
            setGraphic(hBox);
            setAccessibleText(displayText);
            String buttonTextFormatted =
                    MessageFormat.format(buttonAccessibleTextBinding.get(), displayText);
            button.setAccessibleText(buttonTextFormatted);
            button.setTooltip(new Tooltip(buttonTextFormatted));
            button.setOnAction(
                    event -> {
                        if (getItem() != null) {
                            confirmAndRemoveItem(getItem());
                        } else {
                            LOG.warn(
                                    "getItem() returned null in GenericDtoListCell button action.");
                        }
                    });
        }
    }

    /**
     * Shows a confirmation dialog with localized title and message for the given item. If the user
     * confirms, removes the item from the ListView.
     *
     * @param item the item to remove upon confirmation
     */
    private void confirmAndRemoveItem(T item) {
        String displayText = displayTextFunction.apply(item);
        confirmationAlert.setTitle(confirmationTitleBinding.get());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(
                MessageFormat.format(confirmationMessageBinding.get(), displayText));
        confirmationAlert
                .showAndWait()
                .ifPresent(
                        response -> {
                            if ("OK".equals(response.getText())) {
                                listView.getItems().remove(item);
                            }
                        });
    }
}
