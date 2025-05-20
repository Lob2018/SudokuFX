/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.components.list;

import java.text.MessageFormat;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.utils.MyDateTime;

/**
 * A custom {@code ListCell} for displaying a {@code GameDto} in a {@code ListView}, with a label
 * showing the formatted update date and a button to remove the item after user confirmation.
 *
 * <p>The removal button and confirmation dialog texts are fully internationalized using {@code
 * StringBinding}. Accessibility and tooltip texts are updated dynamically based on the current
 * item.
 */
public final class GameDtoListCell extends ListCell<GameDto> {

    private static final Logger log = LoggerFactory.getLogger(GameDtoListCell.class);

    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();
    private final ListView<GameDto> listView;
    private final Alert confirmationAlert;
    private final StringBinding buttonAccessibleTextBinding;
    private final StringBinding confirmationTitleBinding;
    private final StringBinding confirmationMessageBinding;

    public GameDtoListCell(
            ListView<GameDto> listView,
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
     * Updates the cell's content with the formatted update date of the {@code GameDto} item.
     * Configures the removal button's accessibility and tooltip text, and binds its action to show
     * a localized confirmation dialog before removing the item.
     *
     * @param item the {@code GameDto} to display
     * @param empty whether this cell represents data from the list or is empty
     */
    @Override
    protected void updateItem(GameDto item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setAccessibleText(null);
            button.setAccessibleText(null);
            button.setTooltip(null);
            button.setOnAction(null);
        } else {
            String formattedDate = MyDateTime.INSTANCE.getFormatted(item.updatedat());
            label.setText(formattedDate);
            setGraphic(hBox);
            setAccessibleText(formattedDate);
            button.setAccessibleText(
                    MessageFormat.format(buttonAccessibleTextBinding.get(), formattedDate));
            button.setTooltip(
                    new Tooltip(
                            MessageFormat.format(
                                    buttonAccessibleTextBinding.get(), formattedDate)));
            button.setOnAction(
                    event -> {
                        if (getItem() != null) {
                            confirmAndRemoveItem(getItem());
                        } else {
                            log.warn(
                                    "▓▓ getItem() returned null in GameDtoListCell button action.");
                        }
                    });
        }
    }

    /**
     * Shows a confirmation dialog with localized title and message including the item's formatted
     * date. Removes the {@code GameDto} from the {@code ListView} if the user confirms.
     *
     * @param item the {@code GameDto} to remove upon confirmation
     */
    private void confirmAndRemoveItem(GameDto item) {
        String formattedDate = MyDateTime.INSTANCE.getFormatted(item.updatedat());
        confirmationAlert.setTitle(confirmationTitleBinding.get());
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(
                MessageFormat.format(confirmationMessageBinding.get(), formattedDate));
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
