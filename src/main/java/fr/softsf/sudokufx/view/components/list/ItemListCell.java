package fr.softsf.sudokufx.view.components.list;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Custom cell for a ListView<String> that displays an item with a button
 * allowing its removal after user confirmation.
 */
@Slf4j
public final class ItemListCell extends ListCell<String> {
    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();
    private final String buttonAccessibleText;
    private final ListView<String> listView;
    private final String confirmationTitle;
    private final String confirmationMessage;

    /**
     * Constructs a ItemListCell.
     *
     * @param listView             The ListView associated with this cell.
     * @param buttonText           The text displayed on the button.
     * @param buttonAccessibleText The accessible text for screen readers.
     * @param confirmationTitle    The title displayed in the confirmation dialog.
     * @param confirmationMessage  The message displayed in the confirmation dialog.
     */
    public ItemListCell(ListView<String> listView, String buttonText, String buttonAccessibleText, String confirmationTitle, String confirmationMessage) {
        this.listView = Objects.requireNonNull(listView, "The listView inside ItemListCell must not be null");
        this.confirmationTitle = Objects.requireNonNull(confirmationTitle, "The confirmationTitle inside ItemListCell must not be null");
        this.confirmationMessage = Objects.requireNonNull(confirmationMessage, "The confirmationMessage inside ItemListCell must not be null");
        this.buttonAccessibleText=Objects.requireNonNull(buttonAccessibleText, "The buttonAccessibleText inside ItemListCell must not be null");
        hBox.setFocusTraversable(false);
        label.setFocusTraversable(true);
        button.setFocusTraversable(true);
        button.getStyleClass().addAll("material", "menuButtonLabel");
        button.setText(buttonText);
        label.getStyleClass().addAll("root", "menuButtonLabel");
        hBox.getStyleClass().add("menuListLineContainer");
        hBox.getChildren().addAll(label, button);
        button.setOnAction(event -> {
            if (getItem() != null) {
                confirmAndRemoveItem(getItem());
            } else {
                log.warn("▒▒ getItem() returned null in ItemListCell button action.");
            }
        });
    }

    /**
     * Updates the cell's content, accessibility description, and button accessibility text
     * based on the given item.
     *
     * @param item  The item in the list.
     * @param empty Indicates whether the cell is empty.
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            label.setText(item);
            setGraphic(hBox);
            setAccessibleText(item);
            button.setAccessibleText(MessageFormat.format(buttonAccessibleText, item));
        }
    }

    /**
     * Displays a confirmation dialog before removing the item from the list.
     *
     * @param item The item to be removed.
     */
    private void confirmAndRemoveItem(String item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(confirmationTitle);
        alert.setHeaderText(null);
        alert.setContentText(MessageFormat.format(confirmationMessage, item));
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                listView.getItems().remove(item);
            }
        });
    }
}
