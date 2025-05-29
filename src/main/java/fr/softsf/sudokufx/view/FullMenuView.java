/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view;

import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.enums.SecureRandomGenerator;
import fr.softsf.sudokufx.enums.ToastLevels;
import fr.softsf.sudokufx.interfaces.IMainView;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.FullMenuViewModel;

/**
 * View class for the full menu screen of the Sudoku application. This class is responsible for
 * displaying and managing the full menu UI.
 */
public final class FullMenuView implements IMainView {

    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private final Text text1 = new Text("Helloj! ");
    private final Stage primaryStage = new Stage();
    @Autowired private Coordinator coordinator;
    @Autowired private FullMenuViewModel fullMenuViewModel;
    @FXML private ToasterVBox toaster;
    @FXML private Label welcomeText;
    @FXML private Label version;
    @FXML private Label githubVersion;
    @FXML private Button buttonHello;
    private Text text2 = new Text("\ue86c");

    /**
     * Initializes the full menu view. This method is automatically called by JavaFX after loading
     * the FXML.
     */
    @FXML
    private void initialize() {
        text1.getStyleClass().add("root");
        text2.getStyleClass().add("material");
        HBox hbox = new HBox(text1, text2);
        hbox.setStyle("-fx-background-color: #FF0000;");
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        buttonHello.setGraphic(hbox);
        buttonHello.setStyle("-fx-background-color: #00FF00;");

        welcomeText.textProperty().bindBidirectional(fullMenuViewModel.welcomeProperty());
        version.textProperty()
                .bind(
                        Bindings.when(fullMenuViewModel.versionProperty())
                                .then("Version à jour")
                                .otherwise("Mise à jour disponible"));
        githubVersion.textProperty().bind(fullMenuViewModel.githubVersionProperty());
    }

    /**
     * Handles the click event of the Hello button. Displays various toast notifications and
     * triggers a test in the view model.
     *
     * @param event The action event triggered by clicking the button
     */
    @FXML
    private void onHelloButtonClick(ActionEvent event) {

        switch (SecureRandomGenerator.INSTANCE.nextInt(3)) {
            case 0 ->
                    toaster.addToastWithDuration(
                            "INFO 🔹 Work in progress... 🔹", "", ToastLevels.INFO, 6000, true);
            case 1 -> toaster.addToast("WARN", "", ToastLevels.WARN, true);
            default ->
                    toaster.addToast(
                            "VISIBLE ERROR MESSAGE",
                            "DETAILED ERROR MESSAGE "
                                    .repeat(20), // Répète le message pour éviter la duplication
                            // excessive
                            ToastLevels.ERROR,
                            true);
        }
        fullMenuViewModel.test();

        //        fxmlService.setRootByFXMLName("crashscreen-view");
    }

    /** Configures the primary stage for the full menu view. */
    private void openingConfigureStage() {
        primaryStage
                .getIcons()
                .add(
                        new Image(
                                (Objects.requireNonNull(
                                                SudoMain.class.getResource(
                                                        Paths.LOGO_SUDO_PNG_PATH.getPath())))
                                        .toExternalForm()));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(coordinator.getDefaultScene());
        primaryStage.centerOnScreen();
    }

    /** Maximizes the primary stage to fill the primary screen. */
    private void openingMaximizePrimaryStage() {
        Rectangle2D r = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(r.getMinX());
        primaryStage.setY(r.getMinY());
        primaryStage.setWidth(r.getWidth());
        primaryStage.setHeight(r.getHeight());
    }

    /**
     * Applies a fade-in effect to the given node.
     *
     * @param node The node to apply the fade-in effect to
     */
    private void openingFadeIn(final Node node) {
        primaryStage.setAlwaysOnTop(true);
        FadeTransition fadeIn =
                new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        primaryStage.setMaximized(true);
    }

    /** Shows the primary stage. */
    private void openingShowStage() {
        primaryStage.show();
    }

    /**
     * Opens the main stage and handles the transition from splash screen to full menu.
     *
     * @param iSplashScreenView The splash screen view interface
     */
    @Override
    public void openingMainStage(final ISplashScreenView iSplashScreenView) {
        openingConfigureStage();
        openingMaximizePrimaryStage();
        openingFadeIn(coordinator.getDefaultScene().getRoot());
        openingShowStage();
        iSplashScreenView.hideSplashScreen();
        primaryStage.setAlwaysOnTop(false);
    }
}
