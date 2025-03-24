package fr.softsf.sudokufx.view;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.interfaces.IMainView;
import fr.softsf.sudokufx.interfaces.ISceneProvider;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.view.components.list.SelectListCell;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * Default view class of the Sudoku application. This class is
 * responsible for displaying and managing the UI.
 */
@Slf4j
public final class DefaultView implements IMainView, ISceneProvider {

    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private final Stage primaryStage = new Stage();
    @FXML
    @Autowired
    private ToasterVBox toaster;
    @FXML
    private Button menuHiddenButtonShow;
    @FXML
    private Button menuMiniButtonShow;
    @FXML
    private Button menuMiniButtonPlayer;
    @FXML
    private Button menuMiniButtonEasy;
    @FXML
    private Button menuMiniButtonMedium;
    @FXML
    private Button menuMiniButtonDifficult;
    @FXML
    private Button menuMiniButtonSolve;
    @FXML
    private Button menuMiniButtonBackup;
    @FXML
    private Button menuMiniButtonBackground;
    @FXML
    private Button menuMiniButtonLanguage;
    @FXML
    private Label menuMiniButtonLanguageIso;
    @FXML
    private Button menuMiniButtonHelp;
    @FXML
    private Button menuMiniButtonNew;

    @FXML
    private Button menuMaxiButtonReduce;
    @FXML
    private Label menuMaxiButtonReduceText;
    @FXML
    private Button menuMaxiButtonPlayer;
    @FXML
    private Label menuMaxiButtonPlayerText;
    @FXML
    private Button menuMaxiButtonEasy;
    @FXML
    private Label menuMaxiButtonEasyText;
    @FXML
    private Button menuMaxiButtonMedium;
    @FXML
    private Label menuMaxiButtonMediumText;
    @FXML
    private Button menuMaxiButtonDifficult;
    @FXML
    private Label menuMaxiButtonDifficultText;
    @FXML
    private Button menuMaxiButtonSolve;
    @FXML
    private Label menuMaxiButtonSolveText;
    @FXML
    private Button menuMaxiButtonSolveClear;
    @FXML
    private Button menuMaxiButtonBackup;
    @FXML
    private Label menuMaxiButtonBackuptext;
    @FXML
    private Button menuMaxiButtonBackground;
    @FXML
    private Label menuMaxiButtonBackgroundText;
    @FXML
    private Button menuMaxiButtonLanguage;
    @FXML
    private Label menuMaxiButtonLanguageIso;
    @FXML
    private Label menuMaxiButtonLanguageText;
    @FXML
    private Button menuMaxiButtonHelp;
    @FXML
    private Label menuMaxiButtonHelpText;
    @FXML
    private Button menuMaxiButtonNew;
    @FXML
    private Label menuMaxiButtonNewText;
    @FXML
    private Button menuPlayerButtonPlayerEdit;


    @FXML
    private ListView<String> menuPlayerListView;


    /**
     * Initializes the default view. This method is automatically called by
     * JavaFX after loading the FXML.
     */
    @FXML
    private void initialize() {
        menuHiddenButtonShow.setAccessibleText(I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));
        menuMiniButtonShow.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonPlayer.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.player.accessibility"));
        menuMiniButtonPlayer.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMiniButtonEasy.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.easy.accessibility"));
        menuMiniButtonMedium.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.medium.accessibility"));
        menuMiniButtonDifficult.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.difficult.accessibility"));
        menuMiniButtonSolve.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility"));
        menuMiniButtonSolve.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMiniButtonBackup.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility"));
        menuMiniButtonBackup.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMiniButtonBackground.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.background.accessibility"));
        menuMiniButtonBackground.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMiniButtonLanguage.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.mini.button.language.iso"));
        menuMiniButtonHelp.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonNew.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));

        menuMaxiButtonReduce.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.accessibility"));
        menuMaxiButtonReduceText.setText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.text"));
        menuMaxiButtonPlayer.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.player.accessibility"));
        menuMaxiButtonPlayer.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        // service is needed
        menuMaxiButtonPlayerText.setText("PLAYERPLAYERPLAYERPLAYERPLAYERPLAYERPLAYERPLAYER");
        menuMaxiButtonEasy.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.easy.accessibility"));
        menuMaxiButtonEasyText.setText(I18n.INSTANCE.getValue("menu.maxi.button.easy.text"));
        menuMaxiButtonMedium.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.medium.accessibility"));
        menuMaxiButtonMediumText.setText(I18n.INSTANCE.getValue("menu.maxi.button.medium.text"));
        menuMaxiButtonDifficult.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.difficult.accessibility"));
        menuMaxiButtonDifficultText.setText(I18n.INSTANCE.getValue("menu.maxi.button.difficult.text"));
        menuMaxiButtonSolve.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility"));
        menuMaxiButtonSolve.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMaxiButtonSolveText.setText(I18n.INSTANCE.getValue("menu.maxi.button.solve.text"));
        menuMaxiButtonSolveClear.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.solve.clear.accessibility"));
        menuMaxiButtonSolveClear.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        menuMaxiButtonBackup.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility"));
        menuMaxiButtonBackup.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMaxiButtonBackuptext.setText(I18n.INSTANCE.getValue("menu.maxi.button.backup.text"));
        menuMaxiButtonBackground.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility"));
        menuMaxiButtonBackground.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        menuMaxiButtonBackgroundText.setText(I18n.INSTANCE.getValue("menu.maxi.button.background.text"));
        menuMaxiButtonLanguage.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.language.accessibility"));
        menuMaxiButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.maxi.button.language.iso"));
        menuMaxiButtonLanguageText.setText(I18n.INSTANCE.getValue("menu.maxi.button.language.text"));
        menuMaxiButtonHelp.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.help.accessibility"));
        menuMaxiButtonHelpText.setText(I18n.INSTANCE.getValue("menu.maxi.button.help.text"));
        menuMaxiButtonNew.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        menuMaxiButtonNewText.setText(I18n.INSTANCE.getValue("menu.maxi.button.new.text"));
        menuPlayerButtonPlayerEdit.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.player.edit.accessibility"));

        for (int i = 1; i <= 20; i++) {
            menuPlayerListView.getItems().add("Joueur " + i+" AAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        menuPlayerListView.setCellFactory(param -> new SelectListCell(menuPlayerListView, "\uef67", "Accessibility", "Message de confirmation"));

    }

    /**
     * Configures the primary stage for the full menu view.
     */
    private void openingConfigureStage() {
        primaryStage.getIcons().add(new Image((Objects.requireNonNull(SudoMain.class.getResource(Paths.LOGO_SUDO_PNG_PATH.getPath()))).toExternalForm()));
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(getScene());
        primaryStage.centerOnScreen();
    }

    /**
     * Maximizes the primary stage to fill the primary screen.
     */
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
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        primaryStage.setMaximized(true);
    }

    /**
     * Shows the primary stage.
     */
    private void openingShowStage() {
        primaryStage.show();
    }

    /**
     * Opens the main stage and handles the transition from splash screen to
     * full menu.
     *
     * @param iSplashScreenView The splash screen view interface
     */
    @Override
    public void openingMainStage(final ISplashScreenView iSplashScreenView) {
        openingConfigureStage();
        openingMaximizePrimaryStage();
        openingFadeIn(getScene().getRoot());
        openingShowStage();
        iSplashScreenView.hideSplashScreen();
        primaryStage.setAlwaysOnTop(false);
    }

}
