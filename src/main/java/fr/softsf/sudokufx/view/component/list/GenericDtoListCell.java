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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic {@code ListCell} for displaying items of type {@code T} in a {@link ListView} with a
 * label and a remove button.
 *
 * <p>The label displays text generated via a provided {@link Function}. The remove button shows a
 * confirmation dialog before removing the item. Accessibility text and tooltips are updated
 * dynamically via {@link StringBinding}s.
 *
 * @param <T> the type of the items displayed in the ListView
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

    /**
     * Constructs a new GenericDtoListCell.
     *
     * @param listView the ListView containing this cell
     * @param buttonText the text displayed on the remove button
     * @param buttonAccessibleTextBinding binding for the button's accessible text (localized)
     * @param confirmationTitleBinding binding for the confirmation dialog title (localized)
     * @param confirmationMessageBinding binding for the confirmation dialog message (localized)
     * @param confirmationAlert the alert dialog used for removal confirmation
     * @param displayTextFunction function to generate the display text for each item
     */
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
     * Updates the cell's content.
     *
     * <p>If the item is not null, sets the label text and button behavior. If empty, clears the
     * cell's content.
     *
     * @param item the item to display
     * @param empty whether this cell is empty
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
     * Displays a confirmation dialog and removes the item if the user confirms.
     *
     * @param item the item to remove
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
