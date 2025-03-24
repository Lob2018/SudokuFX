package fr.softsf.sudokufx.view.components.list;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public final class SelectListCell extends ListCell<String> {
    private final HBox hBox = new HBox();
    private final Label label = new Label();
    private final ListView<String> listView;
    private final String messageConfirmation;
    private final Button button = new Button();

    public SelectListCell(ListView<String> listView, String buttonText, String buttonAccessibleText, String messageConfirmation) {
        this.listView = listView;
        this.messageConfirmation = messageConfirmation;
        hBox.setFocusTraversable(false);
        label.setFocusTraversable(true);
        button.setFocusTraversable(true);
        button.getStyleClass().addAll("material", "menuButtonLabel");
        button.setText(buttonText);
        button.setAccessibleText(buttonAccessibleText);
        label.getStyleClass().addAll("root", "menuButtonLabel");
        hBox.getStyleClass().add("menuListLineContainer");
        hBox.getChildren().addAll(label, button);
        button.setOnAction(event -> {
            if (getItem() != null) {
                afficherConfirmation(getItem());
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            label.setText(item);
            setGraphic(hBox);
        }
    }

    private void afficherConfirmation(String item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(messageConfirmation + " : " + item + " ?");
        alert.showAndWait().ifPresent(response -> {
            if (response.getText().equals("OK")) {
                listView.getItems().remove(item);
            }
        });
    }
}
