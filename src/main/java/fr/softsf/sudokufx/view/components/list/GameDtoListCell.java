/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.components.list;

import java.text.MessageFormat;
import java.util.Objects;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.utils.MyDateTime;

/**
 * Custom cell for a ListView<GameDto> that displays an item with a button allowing its removal
 * after user confirmation.
 */
public final class GameDtoListCell extends ListCell<GameDto> {

    private static final Logger log = LoggerFactory.getLogger(GameDtoListCell.class);

    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final Button button = new Button();
    private final String buttonAccessibleText;
    private final ListView<GameDto> listView;
    private final String confirmationTitle;
    private final String confirmationMessage;
    private final Alert confirmationAlert;

    public GameDtoListCell(
            ListView<GameDto> listView,
            String buttonText,
            String buttonAccessibleText,
            String confirmationTitle,
            String confirmationMessage,
            Alert confirmationAlert) {

        this.listView = Objects.requireNonNull(listView, "listView must not be null");
        this.confirmationTitle =
                Objects.requireNonNull(confirmationTitle, "confirmationTitle must not be null");
        this.confirmationMessage =
                Objects.requireNonNull(confirmationMessage, "confirmationMessage must not be null");
        this.buttonAccessibleText =
                Objects.requireNonNull(
                        buttonAccessibleText, "buttonAccessibleText must not be null");
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
            String name = MyDateTime.INSTANCE.getFormatted(item.updatedat());
            label.setText(name);
            setGraphic(hBox);
            setAccessibleText(name);
            button.setAccessibleText(MessageFormat.format(buttonAccessibleText, name));
            button.setTooltip(new Tooltip(MessageFormat.format(buttonAccessibleText, name)));
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

    private void confirmAndRemoveItem(GameDto item) {
        String name = MyDateTime.INSTANCE.getFormatted(item.updatedat());
        confirmationAlert.setTitle(confirmationTitle);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(MessageFormat.format(confirmationMessage, name));
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
