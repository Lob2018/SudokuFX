/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.view.components.list;

import java.text.MessageFormat;
import java.util.Objects;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom cell for a ListView<String> that displays an item with a button allowing its removal after
 * user confirmation.
 */
public final class ItemListCell extends ListCell<String> {

    private static final Logger log = LoggerFactory.getLogger(ItemListCell.class);

    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();
    private final String buttonAccessibleText;
    private final ListView<String> listView;
    private final String confirmationTitle;
    private final String confirmationMessage;
    private final Alert confirmationAlert;

    /**
     * Constructs a ItemListCell.
     *
     * @param listView The ListView associated with this cell.
     * @param buttonText The text displayed on the button.
     * @param buttonAccessibleText The accessible text for screen readers.
     * @param confirmationTitle The title displayed in the confirmation dialog.
     * @param confirmationMessage The message displayed in the confirmation dialog.
     * @param confirmationAlert A shared instance of Alert displaying confirmation dialogs.
     * @throws NullPointerException if any of the parameters is null.
     */
    public ItemListCell(
            ListView<String> listView,
            String buttonText,
            String buttonAccessibleText,
            String confirmationTitle,
            String confirmationMessage,
            Alert confirmationAlert) {
        this.listView =
                Objects.requireNonNull(
                        listView, "The listView inside ItemListCell must not be null");
        this.confirmationTitle =
                Objects.requireNonNull(
                        confirmationTitle,
                        "The confirmationTitle inside ItemListCell must not be null");
        this.confirmationMessage =
                Objects.requireNonNull(
                        confirmationMessage,
                        "The confirmationMessage inside ItemListCell must not be null");
        this.buttonAccessibleText =
                Objects.requireNonNull(
                        buttonAccessibleText,
                        "The buttonAccessibleText inside ItemListCell must not be null");
        this.confirmationAlert =
                Objects.requireNonNull(
                        confirmationAlert,
                        "The confirmationAlert inside ItemListCell must not be null");
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
     * Updates the cell's content and accessibility settings based on the item.
     *
     * @param item The item in the list.
     * @param empty Indicates whether the cell is empty.
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setAccessibleText(null);
            button.setAccessibleText(null);
            button.setTooltip(null);
            button.setOnAction(null);
        } else {
            label.setText(item);
            setGraphic(hBox);
            setAccessibleText(item);
            button.setAccessibleText(MessageFormat.format(buttonAccessibleText, item));
            button.setTooltip(new Tooltip(MessageFormat.format(buttonAccessibleText, item)));
            button.setOnAction(
                    event -> {
                        if (getItem() != null) {
                            confirmAndRemoveItem(getItem());
                        } else {
                            log.warn("▓▓ getItem() returned null in ItemListCell button action.");
                        }
                    });
        }
    }

    /**
     * Displays the confirmation dialog before removing the item from the list.
     *
     * @param item The item to be removed.
     */
    private void confirmAndRemoveItem(String item) {
        confirmationAlert.setTitle(confirmationTitle);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(MessageFormat.format(confirmationMessage, item));
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
