package fr.softsf.sudokufx.view;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.Paths;
import fr.softsf.sudokufx.interfaces.IMainView;
import fr.softsf.sudokufx.interfaces.ISceneProvider;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.view.components.list.ItemListCell;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import javafx.animation.FadeTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Default view class of the Sudoku application. This class is
 * responsible for displaying and managing the UI.
 */
@Slf4j
public final class DefaultView implements IMainView, ISceneProvider {

    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private final Stage primaryStage = new Stage();
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED = "menu.accessibility.role.description.closed";
    private static final Alert CONFIRMATION_ALERT = new Alert(Alert.AlertType.CONFIRMATION);

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
    private Button menuPlayerButtonReduce;
    @FXML
    private Label menuPlayerButtonReduceText;
    @FXML
    private Button menuPlayerButtonPlayer;
    @FXML
    private Label menuPlayerButtonPlayerText;
    @FXML
    private Button menuPlayerButtonPlayerEdit;
    @FXML
    private Button menuPlayerButtonNew;
    @FXML
    private Label menuPlayerButtonNewText;
    @FXML
    private ListView<String> menuPlayerListView;
    @FXML
    private Rectangle menuPlayerClipListView;


    /**
     * Initializes the default view. This method is automatically called by
     * JavaFX after loading the FXML.
     */
    @FXML
    private void initialize() {

        confirmationAlertStyle();

        menuHiddenButtonShow.setAccessibleText(I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));

        menuHiddenButtonShow.getTooltip().setText(I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));
        menuMiniButtonShow.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonShow.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonPlayer.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.player.accessibility"));
        menuMiniButtonPlayer.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.player.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonPlayer.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonEasy.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.easy.accessibility"));
        menuMiniButtonEasy.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.easy.accessibility"));
        menuMiniButtonMedium.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.medium.accessibility"));
        menuMiniButtonMedium.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.medium.accessibility"));
        menuMiniButtonDifficult.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.difficult.accessibility"));
        menuMiniButtonDifficult.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.difficult.accessibility"));
        menuMiniButtonSolve.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility"));
        menuMiniButtonSolve.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonSolve.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackup.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility"));
        menuMiniButtonBackup.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackup.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackground.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.background.accessibility"));
        menuMiniButtonBackground.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.background.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackground.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonLanguage.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguage.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.mini.button.language.iso"));
        menuMiniButtonHelp.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonHelp.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonNew.setAccessibleText(I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        menuMiniButtonNew.getTooltip().setText(I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        String playerName = "Toto";
        menuMaxiButtonReduce.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.accessibility"));
        menuMaxiButtonReduce.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.accessibility"));
        menuMaxiButtonReduceText.setText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.text"));
        menuMaxiButtonPlayer.setAccessibleText(MessageFormat.format(I18n.INSTANCE.getValue("menu.maxi.button.player.accessibility"), playerName));
        menuMaxiButtonPlayer.getTooltip().setText(MessageFormat.format(I18n.INSTANCE.getValue("menu.maxi.button.player.accessibility"), playerName)+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonPlayer.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonPlayerText.setText(playerName);
        menuMaxiButtonEasy.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.easy.accessibility"));
        menuMaxiButtonEasy.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.easy.accessibility"));
        menuMaxiButtonEasyText.setText(I18n.INSTANCE.getValue("menu.maxi.button.easy.text"));
        menuMaxiButtonMedium.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.medium.accessibility"));
        menuMaxiButtonMedium.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.medium.accessibility"));
        menuMaxiButtonMediumText.setText(I18n.INSTANCE.getValue("menu.maxi.button.medium.text"));
        menuMaxiButtonDifficult.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.difficult.accessibility"));
        menuMaxiButtonDifficult.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.difficult.accessibility"));
        menuMaxiButtonDifficultText.setText(I18n.INSTANCE.getValue("menu.maxi.button.difficult.text"));
        menuMaxiButtonSolve.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility"));
        menuMaxiButtonSolve.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonSolve.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonSolveText.setText(I18n.INSTANCE.getValue("menu.maxi.button.solve.text"));
        menuMaxiButtonSolveClear.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.solve.clear.accessibility"));
        menuMaxiButtonSolveClear.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        menuMaxiButtonBackup.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility"));
        menuMaxiButtonBackup.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackup.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackuptext.setText(I18n.INSTANCE.getValue("menu.maxi.button.backup.text"));
        menuMaxiButtonBackground.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility"));
        menuMaxiButtonBackground.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility")+I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackground.setAccessibleRoleDescription(I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackgroundText.setText(I18n.INSTANCE.getValue("menu.maxi.button.background.text"));
        menuMaxiButtonLanguage.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.language.accessibility"));
        menuMaxiButtonLanguage.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.language.accessibility"));
        menuMaxiButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.maxi.button.language.iso"));
        menuMaxiButtonLanguageText.setText(I18n.INSTANCE.getValue("menu.maxi.button.language.text"));
        menuMaxiButtonHelp.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.help.accessibility"));
        menuMaxiButtonHelp.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.help.accessibility"));
        menuMaxiButtonHelpText.setText(I18n.INSTANCE.getValue("menu.maxi.button.help.text"));
        menuMaxiButtonNew.setAccessibleText(I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        menuMaxiButtonNew.getTooltip().setText(I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        menuMaxiButtonNewText.setText(I18n.INSTANCE.getValue("menu.maxi.button.new.text"));

        menuPlayerButtonReduce.setAccessibleText(I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        menuPlayerButtonReduce.getTooltip().setText(I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        menuPlayerButtonReduceText.setText(I18n.INSTANCE.getValue("menu.player.button.reduce.text"));
        menuPlayerButtonPlayer.setAccessibleText(MessageFormat.format(I18n.INSTANCE.getValue("menu.player.button.player.accessibility"), playerName));
        menuPlayerButtonPlayer.getTooltip().setText(MessageFormat.format(I18n.INSTANCE.getValue("menu.player.button.player.accessibility"), playerName)+I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        menuPlayerButtonPlayer.setAccessibleRoleDescription(I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));
        menuPlayerButtonPlayerText.setText(playerName);
        menuPlayerButtonPlayerEdit.setAccessibleText(MessageFormat.format(I18n.INSTANCE.getValue("menu.player.button.edit.accessibility"), playerName));
        menuPlayerButtonPlayerEdit.getTooltip().setText(MessageFormat.format(I18n.INSTANCE.getValue("menu.player.button.edit.accessibility"), playerName));
        menuPlayerButtonNew.setAccessibleText(I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility"));
        menuPlayerButtonNew.getTooltip().setText(I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility"));
        menuPlayerButtonNewText.setText(I18n.INSTANCE.getValue("menu.player.button.new.player.text"));
        setupListViewClip(menuPlayerListView, menuPlayerClipListView);
        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        for (int i = 1; i <= 20; i++) {
            menuPlayerListView.getItems().add(playerName + i + " AAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        menuPlayerListView.setCellFactory(param ->
                new ItemListCell(
                        menuPlayerListView,
                        "\uef67",
                        I18n.INSTANCE.getValue("menu.player.button.new.player.cell.delete.accessibility"),
                        I18n.INSTANCE.getValue("menu.player.button.new.player.dialog.confirmation.title"),
                        I18n.INSTANCE.getValue("menu.player.button.new.player.dialog.confirmation.message"),
                        CONFIRMATION_ALERT
                )
        );

    }

    /**
     * Styles the confirmation alert dialog.
     * - Sets a radial gradient background.
     * - Changes content text color to white.
     */
    private void confirmationAlertStyle() {
        CONFIRMATION_ALERT.getDialogPane().setStyle(
                "-fx-background-color: radial-gradient(center 50% 150%, radius 100%, #A83449, #12020B);"
        );
        Label contentLabel = (Label) CONFIRMATION_ALERT.getDialogPane().lookup(".content");
        if (contentLabel != null) {
            contentLabel.setTextFill(Color.WHITE);
        }
    }

    /**
     * Sets up a rounded clip for a ListView.
     *
     * @param listView The ListView to be clipped.
     * @param clipView The Rectangle used as the clip.
     */
    private void setupListViewClip(ListView<?> listView, Rectangle clipView) {
        clipView.widthProperty().bind(listView.widthProperty());
        clipView.heightProperty().bind(listView.heightProperty());
        DoubleBinding radiusBinding = listView.widthProperty().divide(7);
        clipView.arcWidthProperty().bind(radiusBinding);
        clipView.arcHeightProperty().bind(radiusBinding);
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
