/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.view;

import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.*;
import fr.softsf.sudokufx.interfaces.IMainView;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.components.MyAlert;
import fr.softsf.sudokufx.view.components.PossibilityStarsHBox;
import fr.softsf.sudokufx.view.components.SpinnerGridPane;
import fr.softsf.sudokufx.view.components.list.ItemListCell;
import fr.softsf.sudokufx.view.components.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.*;

/**
 * Default view class of the Sudoku application. This class is responsible for displaying and
 * managing the UI.
 */
public final class DefaultView implements IMainView {

    private static final Logger log = LoggerFactory.getLogger(DefaultView.class);

    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private final Stage primaryStage = new Stage();
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SELECTED =
            "menu.accessibility.role.description.selected";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    public static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";

    private static final MyAlert CONFIRMATION_ALERT = new MyAlert(Alert.AlertType.CONFIRMATION);

    @Autowired private Coordinator coordinator;
    @Autowired private HelpViewModel helpViewModel;
    @Autowired private LevelViewModel levelViewModel;
    @Autowired private BackgroundViewModel backgroundViewModel;
    @Autowired private MenuHiddenViewModel menuHiddenViewModel;

    private static final PseudoClass DIFFICULTY_LEVEL_PSEUDO_SELECTED =
            PseudoClass.getPseudoClass("selected");
    private final ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel =
            new ActiveMenuOrSubmenuViewModel();

    @FXML private ToasterVBox toaster;
    @FXML private SpinnerGridPane spinner;
    @FXML private GridPane sudokuFX;

    @FXML VBox menuHidden;
    @FXML VBox menuMini;
    @FXML VBox menuMaxi;
    @FXML VBox menuPlayer;
    @FXML VBox menuSolve;
    @FXML VBox menuSave;
    @FXML VBox menuBackground;

    @FXML private Button menuHiddenButtonShow;
    @FXML private Button menuMiniButtonShow;
    @FXML private Button menuMiniButtonPlayer;
    @FXML private Button menuMiniButtonEasy;
    @FXML private Button menuMiniButtonMedium;
    @FXML private Button menuMiniButtonDifficult;
    @FXML private Button menuMiniButtonSolve;
    @FXML private Button menuMiniButtonBackup;
    @FXML private Button menuMiniButtonBackground;
    @FXML private Button menuMiniButtonLanguage;
    @FXML private Label menuMiniButtonLanguageIso;
    @FXML private Button menuMiniButtonHelp;
    @FXML private Button menuMiniButtonNew;

    @FXML private Button menuMaxiButtonReduce;
    @FXML private Label menuMaxiButtonReduceText;
    @FXML private Button menuMaxiButtonPlayer;
    @FXML private Label menuMaxiButtonPlayerText;
    @FXML private Button menuMaxiButtonEasy;
    @FXML private Label menuMaxiButtonEasyText;
    @FXML private PossibilityStarsHBox menuMaxiHBoxEasyPossibilities;
    @FXML private Button menuMaxiButtonMedium;
    @FXML private Label menuMaxiButtonMediumText;
    @FXML private PossibilityStarsHBox menuMaxiHBoxMediumPossibilities;
    @FXML private Button menuMaxiButtonDifficult;
    @FXML private Label menuMaxiButtonDifficultText;
    @FXML private PossibilityStarsHBox menuMaxiHBoxDifficultPossibilities;
    @FXML private Button menuMaxiButtonSolve;
    @FXML private Label menuMaxiButtonSolveText;
    @FXML private Button menuMaxiButtonBackup;
    @FXML private Label menuMaxiButtonBackuptext;
    @FXML private Button menuMaxiButtonBackground;
    @FXML private Label menuMaxiButtonBackgroundText;
    @FXML private Button menuMaxiButtonLanguage;
    @FXML private Label menuMaxiButtonLanguageIso;
    @FXML private Label menuMaxiButtonLanguageText;
    @FXML private Button menuMaxiButtonHelp;
    @FXML private Label menuMaxiButtonHelpText;
    @FXML private Button menuMaxiButtonNew;
    @FXML private Label menuMaxiButtonNewText;

    @FXML private Button menuPlayerButtonReduce;
    @FXML private Label menuPlayerButtonReduceText;
    @FXML private Button menuPlayerButtonPlayer;
    @FXML private Label menuPlayerButtonPlayerText;
    @FXML private Button menuPlayerButtonPlayerEdit;
    @FXML private Button menuPlayerButtonNew;
    @FXML private Label menuPlayerButtonNewText;
    @FXML private ListView<String> menuPlayerListView;
    @FXML private Rectangle menuPlayerClipListView;

    @FXML private Button menuSolveButtonReduce;
    @FXML private Button menuSolveButtonSolve;
    @FXML private Label menuSolveButtonReduceText;
    @FXML private Label menuSolveButtonSolveText;
    @FXML private Button menuSolveButtonSolveClear;
    @FXML private PossibilityStarsHBox menuSolveHBoxPossibilities;

    @FXML private Button menuSaveButtonReduce;
    @FXML private Label menuSaveButtonReduceText;
    @FXML private Button menuSaveButtonSave;
    @FXML private Label menuSaveButtonSaveText;
    @FXML private Button menuSaveButtonBackup;
    @FXML private Label menuSaveButtonBackupText;
    @FXML private ListView<String> menuSaveListView;
    @FXML private Rectangle menuSaveClipListView;

    @FXML private Button menuBackgroundButtonReduce;
    @FXML private Label menuBackgroundButtonReduceText;
    @FXML private Button menuBackgroundButtonBackground;
    @FXML private Label menuBackgroundButtonBackgroundText;
    @FXML private Button menuBackgroundButtonImage;
    @FXML private Label menuBackgroundButtonImageText;
    @FXML private ColorPicker menuBackgroundButtonColor;

    /**
     * Initializes the default view. This method is automatically called by JavaFX after loading the
     * FXML.
     */
    @FXML
    private void initialize() {
        // Menu hidden
        menuHiddenButtonShow
                .accessibleTextProperty()
                .bind(menuHiddenViewModel.menuHiddenButtonShowAccessibilityTextProperty());
        menuHiddenButtonShow
                .getTooltip()
                .textProperty()
                .bind(menuHiddenViewModel.menuHiddenButtonShowAccessibilityTextProperty());
        //        menuHiddenButtonShow.setAccessibleText(
        //                I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));
        //        menuHiddenButtonShow
        //                .getTooltip()
        //                .setText(I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));

        // Menu mini
        menuMiniButtonShow.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonShow
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonPlayer.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.player.accessibility"));
        menuMiniButtonPlayer
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.mini.button.player.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonPlayer.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonEasy.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.easy.accessibility"));
        menuMiniButtonEasy
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.easy.accessibility"));
        menuMiniButtonMedium.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.medium.accessibility"));
        menuMiniButtonMedium
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.medium.accessibility"));
        menuMiniButtonDifficult.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.difficult.accessibility"));
        menuMiniButtonDifficult
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.difficult.accessibility"));
        menuMiniButtonSolve.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility"));
        menuMiniButtonSolve
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonSolve.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackup.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility"));
        menuMiniButtonBackup
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackup.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackground.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.background.accessibility"));
        menuMiniButtonBackground
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.mini.button.background.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackground.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonLanguage.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguage
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.mini.button.language.iso"));
        menuMiniButtonHelp.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonHelp
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonNew.setAccessibleText(
                I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        menuMiniButtonNew
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        String playerName = "Tototototototototototototototo";
        menuMaxiButtonReduce.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.reduce.accessibility"));
        menuMaxiButtonReduce
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.accessibility"));
        menuMaxiButtonReduceText.setText(I18n.INSTANCE.getValue("menu.maxi.button.reduce.text"));
        menuMaxiButtonPlayer.setAccessibleText(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("menu.maxi.button.player.accessibility"),
                        playerName));
        menuMaxiButtonPlayer
                .getTooltip()
                .setText(
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.maxi.button.player.accessibility"),
                                        playerName)
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonPlayer.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonPlayerText.setText(playerName);
        bindLevel(
                DifficultyLevel.EASY,
                menuMaxiHBoxEasyPossibilities,
                menuMaxiButtonEasy,
                menuMaxiButtonEasyText,
                "menu.maxi.button.easy.accessibility",
                "menu.maxi.button.easy.text",
                menuMiniButtonEasy);
        bindLevel(
                DifficultyLevel.MEDIUM,
                menuMaxiHBoxMediumPossibilities,
                menuMaxiButtonMedium,
                menuMaxiButtonMediumText,
                "menu.maxi.button.medium.accessibility",
                "menu.maxi.button.medium.text",
                menuMiniButtonMedium);
        bindLevel(
                DifficultyLevel.DIFFICULT,
                menuMaxiHBoxDifficultPossibilities,
                menuMaxiButtonDifficult,
                menuMaxiButtonDifficultText,
                "menu.maxi.button.difficult.accessibility",
                "menu.maxi.button.difficult.text",
                menuMiniButtonDifficult);
        menuMaxiButtonSolve.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility"));
        menuMaxiButtonSolve
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.maxi.button.solve.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonSolve.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonSolveText.setText(I18n.INSTANCE.getValue("menu.maxi.button.solve.text"));
        menuMaxiButtonBackup.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility"));
        menuMaxiButtonBackup
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.maxi.button.backup.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackup.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackuptext.setText(I18n.INSTANCE.getValue("menu.maxi.button.backup.text"));
        menuMaxiButtonBackground.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility"));
        menuMaxiButtonBackground
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.maxi.button.background.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackground.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMaxiButtonBackgroundText.setText(
                I18n.INSTANCE.getValue("menu.maxi.button.background.text"));
        menuMaxiButtonLanguage.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.language.accessibility"));
        menuMaxiButtonLanguage
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.maxi.button.language.accessibility"));
        menuMaxiButtonLanguageIso.setText(I18n.INSTANCE.getValue("menu.maxi.button.language.iso"));
        menuMaxiButtonLanguageText.setText(
                I18n.INSTANCE.getValue("menu.maxi.button.language.text"));
        menuMaxiButtonHelp.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.help.accessibility"));
        menuMaxiButtonHelp
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.maxi.button.help.accessibility"));
        menuMaxiButtonHelpText.setText(I18n.INSTANCE.getValue("menu.maxi.button.help.text"));
        menuMaxiButtonNew.setAccessibleText(
                I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        menuMaxiButtonNew
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        menuMaxiButtonNewText.setText(I18n.INSTANCE.getValue("menu.maxi.button.new.text"));

        menuPlayerButtonReduce.setAccessibleText(
                I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        menuPlayerButtonReduce
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        menuPlayerButtonReduceText.setText(
                I18n.INSTANCE.getValue("menu.player.button.reduce.text"));
        menuPlayerButtonPlayer.setAccessibleText(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("menu.player.button.player.accessibility"),
                        playerName));
        menuPlayerButtonPlayer
                .getTooltip()
                .setText(
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.player.button.player.accessibility"),
                                        playerName)
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuPlayerButtonPlayer.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuPlayerButtonPlayerText.setText(playerName);
        menuPlayerButtonPlayerEdit.setAccessibleText(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("menu.player.button.edit.accessibility"),
                        playerName));
        menuPlayerButtonPlayerEdit.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuPlayerButtonPlayerEdit
                .getTooltip()
                .setText(
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.player.button.edit.accessibility"),
                                        playerName)
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuPlayerButtonNew.setAccessibleText(
                I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility"));
        menuPlayerButtonNew.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuPlayerButtonNew
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuPlayerButtonNewText.setText(
                I18n.INSTANCE.getValue("menu.player.button.new.player.text"));
        setupListViewClip(menuPlayerListView, menuPlayerClipListView);
        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        menuPlayerListView.getItems().add("ANONYMOUS");
        for (int i = 1; i <= 20; i++) {
            menuPlayerListView.getItems().add(playerName + i + " AAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        menuPlayerListView.getItems().add(playerName);
        menuPlayerListView.getSelectionModel().select(playerName);
        Platform.runLater(
                () -> {
                    menuPlayerListView.refresh();
                    menuPlayerListView.scrollTo(playerName);
                });
        menuPlayerListView.setCellFactory(
                param ->
                        new ItemListCell(
                                menuPlayerListView,
                                "\uef67",
                                I18n.INSTANCE.getValue(
                                        "menu.player.button.new.player.cell.delete.accessibility"),
                                I18n.INSTANCE.getValue(
                                        "menu.player.button.new.player.dialog.confirmation.title"),
                                I18n.INSTANCE.getValue(
                                        "menu.player.button.new.player.dialog.confirmation.message"),
                                CONFIRMATION_ALERT));

        menuSolveButtonReduce.setAccessibleText(
                I18n.INSTANCE.getValue("menu.solve.button.reduce.accessibility"));
        menuSolveButtonReduce
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.solve.button.reduce.accessibility"));
        menuSolveButtonReduceText.setText(I18n.INSTANCE.getValue("menu.solve.button.reduce.text"));

        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        menuSolveHBoxPossibilities.setVisible(true);
        menuSolveButtonSolve
                .accessibleTextProperty()
                .bind(
                        menuSolveHBoxPossibilities.formattedTextBinding(
                                "menu.solve.button.solve.accessibility", false));
        menuSolveButtonSolve
                .getTooltip()
                .textProperty()
                .bind(
                        menuSolveHBoxPossibilities.formattedTextBinding(
                                "menu.solve.button.solve.accessibility", true));
        menuSolveButtonSolve.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuSolveButtonSolveText.setText(I18n.INSTANCE.getValue("menu.solve.button.solve.text"));

        menuSolveButtonSolveClear.setAccessibleText(
                I18n.INSTANCE.getValue("menu.solve.button.solve.clear.accessibility"));
        menuSolveButtonSolveClear.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuSolveButtonSolveClear
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.solve.button.solve.clear.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));

        menuSaveButtonReduce.setAccessibleText(
                I18n.INSTANCE.getValue("menu.save.button.reduce.accessibility"));
        menuSaveButtonReduce
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.save.button.reduce.accessibility"));
        menuSaveButtonReduceText.setText(I18n.INSTANCE.getValue("menu.save.button.reduce.text"));
        menuSaveButtonSave.setAccessibleText(
                I18n.INSTANCE.getValue("menu.save.button.save.accessibility"));
        menuSaveButtonSave
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.save.button.save.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuSaveButtonSave.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuSaveButtonSaveText.setText(I18n.INSTANCE.getValue("menu.save.button.save.text"));
        menuSaveButtonBackup.setAccessibleText(
                I18n.INSTANCE.getValue("menu.save.button.backup.accessibility"));
        menuSaveButtonBackup.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuSaveButtonBackup
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.save.button.backup.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuSaveButtonBackupText.setText(I18n.INSTANCE.getValue("menu.save.button.backup.text"));
        setupListViewClip(menuSaveListView, menuSaveClipListView);
        // TODO: À SUPPRIMER OU ADAPTER (ex. SERVICE)
        String backupName = "01/03/25 14:";
        for (int i = 31; i >= 11; i--) {
            menuSaveListView.getItems().add(backupName + i);
        }
        menuSaveListView.getItems().add(backupName + 10);
        menuSaveListView.getSelectionModel().select(backupName + 10);
        Platform.runLater(
                () -> {
                    menuSaveListView.refresh();
                    menuSaveListView.scrollTo(backupName + 10);
                });
        menuSaveListView.setCellFactory(
                param ->
                        new ItemListCell(
                                menuSaveListView,
                                "\ue92b",
                                I18n.INSTANCE.getValue(
                                        "menu.save.button.backup.cell.delete.accessibility"),
                                I18n.INSTANCE.getValue(
                                        "menu.save.button.backup.dialog.confirmation.title"),
                                I18n.INSTANCE.getValue(
                                        "menu.save.button.backup.dialog.confirmation.message"),
                                CONFIRMATION_ALERT));
        menuBackgroundButtonReduce.setAccessibleText(
                I18n.INSTANCE.getValue("menu.background.button.reduce.accessibility"));
        menuBackgroundButtonReduce
                .getTooltip()
                .setText(I18n.INSTANCE.getValue("menu.background.button.reduce.accessibility"));
        menuBackgroundButtonReduceText.setText(
                I18n.INSTANCE.getValue("menu.background.button.reduce.text"));
        menuBackgroundButtonBackground.setAccessibleText(
                I18n.INSTANCE.getValue("menu.background.button.background.accessibility"));
        menuBackgroundButtonBackground
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.background.button.background.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuBackgroundButtonBackground.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED));
        menuBackgroundButtonBackgroundText.setText(
                I18n.INSTANCE.getValue("menu.background.button.background.text"));
        menuBackgroundButtonImage.setAccessibleText(
                I18n.INSTANCE.getValue("menu.background.button.image.accessibility"));
        menuBackgroundButtonImage
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.background.button.image.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuBackgroundButtonImage.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuBackgroundButtonImageText.setText(
                I18n.INSTANCE.getValue("menu.background.button.image.text"));
        menuBackgroundButtonColor.setAccessibleText(
                I18n.INSTANCE.getValue("menu.background.button.color.accessibility"));
        menuBackgroundButtonColor
                .getTooltip()
                .setText(
                        I18n.INSTANCE.getValue("menu.background.button.color.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        menuBackgroundButtonColor.setAccessibleRoleDescription(
                I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION));
        backgroundViewModel.init(sudokuFX, menuBackgroundButtonColor, toaster, spinner);
        menuBackgroundButtonColor
                .valueProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            backgroundViewModel.updateBackgroundColorAndApply(sudokuFX, newValue);
                        });
        // Managing the active menu
        menuHidden
                .visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.HIDDEN));
        menuHidden
                .managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.HIDDEN));
        menuMini.visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI));
        menuMini.managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI));
        menuMaxi.visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI));
        menuMaxi.managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI));
        menuPlayer
                .visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER));
        menuPlayer
                .managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER));
        menuSolve
                .visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE));
        menuSolve
                .managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE));
        menuSave.visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKUP));
        menuSave.managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKUP));
        menuBackground
                .visibleProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKGROUND));
        menuBackground
                .managedProperty()
                .bind(
                        activeMenuOrSubmenuViewModel
                                .getActiveMenu()
                                .isEqualTo(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKGROUND));
    }

    /**
     * Binds UI components for a difficulty-level toggle in both the maxi and mini menus. This
     * method configures visibility, accessibility, tooltips, and styling based on the selected
     * difficulty level.
     *
     * <ul>
     *   <li><strong>Visibility:</strong> Displays stars when the corresponding difficulty level is
     *       selected.
     *   <li><strong>Accessibility:</strong> Updates role descriptions and accessible text for
     *       screen readers based on the level.
     *   <li><strong>Tooltips:</strong> Binds the tooltip text to the accessible text for
     *       consistency.
     *   <li><strong>Styling:</strong> Applies a 'selected' pseudo-class to both buttons when the
     *       level is active.
     * </ul>
     *
     * @param difficultyLevel The difficulty level to configure.
     * @param possibilityStarsBox The container that displays the stars for this difficulty level.
     * @param maxiLevel The button in the maxi menu corresponding to this level.
     * @param levelName The label showing the level's name in the maxi menu.
     * @param levelAccessibilityKey The I18n key used to retrieve the accessibility description for
     *     this level.
     * @param levelNameKey The I18n key used to retrieve the display name for this level.
     * @param miniLevel The button in the mini menu corresponding to this level.
     */
    private void bindLevel(
            DifficultyLevel difficultyLevel,
            PossibilityStarsHBox possibilityStarsBox,
            Button maxiLevel,
            Label levelName,
            String levelAccessibilityKey,
            String levelNameKey,
            Button miniLevel) {
        levelName.setText(I18n.INSTANCE.getValue(levelNameKey));
        possibilityStarsBox
                .visibleProperty()
                .bind(
                        Bindings.createBooleanBinding(
                                () ->
                                        levelViewModel.getSelectedLevelProperty().get()
                                                == difficultyLevel,
                                levelViewModel.getSelectedLevelProperty()));
        maxiLevel
                .accessibleRoleDescriptionProperty()
                .bind(
                        Bindings.when(possibilityStarsBox.visibleProperty())
                                .then(
                                        I18n.INSTANCE.getValue(
                                                MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SELECTED))
                                .otherwise((String) null));
        miniLevel
                .accessibleRoleDescriptionProperty()
                .bind(
                        Bindings.when(possibilityStarsBox.visibleProperty())
                                .then(
                                        I18n.INSTANCE.getValue(
                                                MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SELECTED))
                                .otherwise((String) null));
        StringBinding accessibleTextBinding =
                Bindings.createStringBinding(
                        () ->
                                possibilityStarsBox
                                        .formattedTextBinding(levelAccessibilityKey, false)
                                        .get(),
                        levelViewModel.getSelectedLevelProperty(),
                        possibilityStarsBox.getPercentage());
        maxiLevel.accessibleTextProperty().bind(accessibleTextBinding);
        miniLevel.accessibleTextProperty().bind(accessibleTextBinding);
        maxiLevel.getTooltip().textProperty().bind(accessibleTextBinding);
        miniLevel.getTooltip().textProperty().bind(accessibleTextBinding);
        levelViewModel
                .getSelectedLevelProperty()
                .addListener(
                        (obs, oldLvl, newLvl) -> {
                            boolean isThisLevel = newLvl == difficultyLevel;
                            maxiLevel.pseudoClassStateChanged(
                                    DIFFICULTY_LEVEL_PSEUDO_SELECTED, isThisLevel);
                            miniLevel.pseudoClassStateChanged(
                                    DIFFICULTY_LEVEL_PSEUDO_SELECTED, isThisLevel);
                        });
    }

    @FXML
    private void handleFileImageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser
                .getExtensionFilters()
                .add(
                        new FileChooser.ExtensionFilter(
                                "Fichiers d'images", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        applySelectedBackgroundImage(selectedFile);
    }

    /**
     * Applies the selected background image to the GridPane.
     *
     * @param selectedFile The image file selected by the user. If null, no action is taken.
     */
    private void applySelectedBackgroundImage(File selectedFile) {
        if (selectedFile != null) {
            backgroundViewModel.handleFileImageChooser(selectedFile, toaster, spinner, sudokuFX);
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

    /** Sets the difficulty level to EASY and updates the UI with a random percentage. */
    public void handleEasyLevelShow() {
        // TODO WITH TRUE GRID
        int stars = SecureRandomGenerator.INSTANCE.nextInt(10, 33);
        menuMaxiHBoxEasyPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        menuSolveHBoxPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        levelViewModel.setLevel(DifficultyLevel.EASY, stars);
    }

    /** Sets the difficulty level to MEDIUM and updates the UI with a random percentage. */
    public void handleMediumLevelShow() {
        // TODO WITH TRUE GRID
        int stars = SecureRandomGenerator.INSTANCE.nextInt(34, 66);
        menuMaxiHBoxMediumPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        menuSolveHBoxPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        levelViewModel.setLevel(DifficultyLevel.MEDIUM, stars);
    }

    /** Sets the difficulty level to DIFFICULT and updates the UI with a random percentage. */
    public void handleDifficultLevelShow() {
        // TODO WITH TRUE GRID
        int stars = SecureRandomGenerator.INSTANCE.nextInt(67, 89);
        menuMaxiHBoxDifficultPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        menuSolveHBoxPossibilities.setHBoxPossibilityStarsFromPercentage(stars);
        levelViewModel.setLevel(DifficultyLevel.DIFFICULT, stars);
    }

    /**
     * Activates the MINI menu and hides it after 10 seconds if still active and the button
     * "menuMiniButtonShow" has the focus.
     */
    public void handleMenuMiniShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI);
        Timeline hideMenuTimeline =
                new Timeline(
                        new KeyFrame(
                                Duration.millis(10000),
                                event -> {
                                    try {
                                        if (activeMenuOrSubmenuViewModel.getActiveMenu().get()
                                                        == ActiveMenuOrSubmenuViewModel.ActiveMenu
                                                                .MINI
                                                && coordinator
                                                        .getDefaultScene()
                                                        .getFocusOwner()
                                                        .getId()
                                                        .equals("menuMiniButtonShow")) {
                                            activeMenuOrSubmenuViewModel.setActiveMenu(
                                                    ActiveMenuOrSubmenuViewModel.ActiveMenu.HIDDEN);
                                        }
                                    } catch (Exception e) {
                                        log.error(
                                                "██ DefaultView > handleMenuMiniShow exception"
                                                        + " occurred: {}",
                                                e.getMessage(),
                                                e);
                                    }
                                }));
        hideMenuTimeline.play();
    }

    /**
     * Activates the MAXI menu and sets focus on the corresponding button based on the submenu
     * source button of the event or on the reduce button.
     *
     * @param event the event triggered by clicking a menu button.
     */
    public void handleMenuMaxiShow(ActionEvent event) {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI);
        Object source = event.getSource();
        if (!(source instanceof Button button)) return;
        switch (button.getId()) {
            case "menuPlayerButtonPlayer" -> menuMaxiButtonPlayer.requestFocus();
            case "menuSolveButtonSolve" -> menuMaxiButtonSolve.requestFocus();
            case "menuSaveButtonSave" -> menuMaxiButtonBackup.requestFocus();
            case "menuBackgroundButtonBackground" -> menuMaxiButtonBackground.requestFocus();
            default -> menuMaxiButtonReduce.requestFocus();
        }
    }

    /** Activates the PLAYER menu and sets focus on the player button. */
    public void handleMenuPlayerShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER);
        menuPlayerButtonPlayer.requestFocus();
    }

    /** Activates the SOLVE menu and sets focus on the solve button. */
    public void handleMenuSolveShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE);
        menuSolveButtonSolve.requestFocus();
    }

    /** Activates the BACKUP menu and sets focus on the save button. */
    public void handleMenuBackupShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKUP);
        menuSaveButtonSave.requestFocus();
    }

    /** Activates the BACKGROUND menu and sets focus on the background button. */
    public void handleMenuBackgroundShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(
                ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKGROUND);
        menuBackgroundButtonBackground.requestFocus();
    }

    /** Displays the Help menu dialog with game rules and the application log path. */
    public void handleMenuHelpShow() {
        helpViewModel.showHelp();
    }

    /** Switches language and refreshes all ViewModel texts. */
    public void handleToggleLanguage() {
        coordinator.toggleLanguage();
        menuHiddenViewModel.updateTexts();
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
