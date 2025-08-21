/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view;

import java.io.File;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.Paths;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.interfaces.IMainView;
import fr.softsf.sudokufx.common.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.component.MyAlert;
import fr.softsf.sudokufx.view.component.PossibilityStarsHBox;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.list.GenericDtoListCell;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.HelpViewModel;
import fr.softsf.sudokufx.viewmodel.MenuBackgroundViewModel;
import fr.softsf.sudokufx.viewmodel.MenuHiddenViewModel;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuMaxiViewModel;
import fr.softsf.sudokufx.viewmodel.MenuMiniViewModel;
import fr.softsf.sudokufx.viewmodel.MenuNewViewModel;
import fr.softsf.sudokufx.viewmodel.MenuPlayerViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSaveViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSolveViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

/**
 * Default view class of the Sudoku application. This class is responsible for displaying and
 * managing the UI.
 */
public final class DefaultView implements IMainView {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultView.class);
    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private static final MyAlert CONFIRMATION_ALERT = new MyAlert(Alert.AlertType.CONFIRMATION);
    private static final PseudoClass DIFFICULTY_LEVEL_PSEUDO_SELECTED =
            PseudoClass.getPseudoClass("selected");
    private static final int AUTO_HIDE_MINI_MENU_DELAY_MS = 5_000;

    private final Stage primaryStage = new Stage();

    @Autowired private ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel;
    @Autowired private Coordinator coordinator;
    @Autowired private HelpViewModel helpViewModel;
    @Autowired private MenuHiddenViewModel menuHiddenViewModel;
    @Autowired private MenuMiniViewModel menuMiniViewModel;
    @Autowired private MenuLevelViewModel menuLevelViewModel;
    @Autowired private MenuMaxiViewModel menuMaxiViewModel;
    @Autowired private MenuPlayerViewModel menuPlayerViewModel;
    @Autowired private MenuSaveViewModel menuSaveViewModel;
    @Autowired private MenuSolveViewModel menuSolveViewModel;
    @Autowired private MenuBackgroundViewModel menuBackgroundViewModel;
    @Autowired private MenuNewViewModel menuNewViewModel;
    @Autowired private GridViewModel gridViewModel;

    @FXML private ToasterVBox toaster;
    @FXML private SpinnerGridPane spinner;
    @FXML private GridPane sudokuFX;
    @FXML private GridPane sudokuFXGridPane;

    @FXML private VBox menuHidden;
    @FXML private VBox menuMini;
    @FXML private VBox menuMaxi;
    @FXML private VBox menuPlayer;
    @FXML private VBox menuSolve;
    @FXML private VBox menuSave;
    @FXML private VBox menuBackground;

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
    @FXML private Label menuMaxiButtonBackupText;
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
    @FXML private ListView<PlayerDto> menuPlayerListView;
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
    @FXML private ListView<GameDto> menuSaveListView;
    @FXML private Rectangle menuSaveClipListView;

    @FXML private Button menuBackgroundButtonReduce;
    @FXML private Label menuBackgroundButtonReduceText;
    @FXML private Button menuBackgroundButtonBackground;
    @FXML private Label menuBackgroundButtonBackgroundText;
    @FXML private Button menuBackgroundButtonImage;
    @FXML private Label menuBackgroundButtonImageText;
    @FXML private ColorPicker menuBackgroundButtonColor;

    private Timeline hideMiniMenuTimeline;

    /**
     * Initializes the default view. This method is automatically called by JavaFX after loading the
     * FXML.
     */
    @FXML
    private void initialize() {
        hideMiniMenuTimeline = hideMiniMenuTimelineInitialization();
        hiddenMenuInitialization();
        miniMenuInitialization();
        levelsMenuInitialization();
        maxiMenuInitialization();
        playerMenuInitialization();
        saveMenuInitialization();
        solveMenuInitialization();
        backgroundMenuInitialization();
        newMenuInitialization();
        activeMenuManagerInitialization();
        gridInitialization();
    }

    /**
     * Initializes a {@link Timeline} that hides the MINI menu after {@code
     * AUTO_HIDE_MINI_MENU_DELAY_MS} milliseconds if it is still active and the "menuMiniButtonShow"
     * button retains focus.
     *
     * @return the {@code Timeline} responsible for automatically hiding the MINI menu
     */
    private Timeline hideMiniMenuTimelineInitialization() {
        return new Timeline(
                new KeyFrame(
                        Duration.millis(AUTO_HIDE_MINI_MENU_DELAY_MS),
                        event -> {
                            try {
                                if (activeMenuOrSubmenuViewModel.getActiveMenu().get()
                                                == ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI
                                        && "menuMiniButtonShow"
                                                .equals(
                                                        coordinator
                                                                .getDefaultScene()
                                                                .getFocusOwner()
                                                                .getId())) {
                                    activeMenuOrSubmenuViewModel.setActiveMenu(
                                            ActiveMenuOrSubmenuViewModel.ActiveMenu.HIDDEN);
                                    menuHiddenButtonShow.requestFocus();
                                }
                            } catch (Exception e) {
                                LOG.error(
                                        "██ DefaultView > handleMenuMiniShow exception occurred:"
                                                + " {}",
                                        e.getMessage(),
                                        e);
                            }
                        }));
    }

    /**
     * Adds each cell's Label and TextArea to the GridPane at positions calculated from their index
     * in a 9x9 grid, after initializing the cell view models.
     */
    private void gridInitialization() {
        gridViewModel.init();
        int index = 0;
        for (GridCellViewModel cellVM : gridViewModel.getCellViewModels()) {
            Label label = cellVM.getLabel();
            TextArea textArea = cellVM.getTextArea();
            int row = index / 9;
            int col = index % 9;
            sudokuFXGridPane.add(label, col, row);
            sudokuFXGridPane.add(textArea, col, row);
            index++;
        }
    }

    /**
     * Binds "New" menu UI elements to the ViewModel for text, accessibility, visibility, and
     * tooltips, and shows toast notifications on status message updates.
     */
    private void newMenuInitialization() {
        menuMaxiButtonNewText.textProperty().bind(menuNewViewModel.maxiNewTextProperty());
        menuMaxiButtonNew
                .accessibleTextProperty()
                .bind(menuNewViewModel.maxiNewAccessibleTextProperty());
        menuMiniButtonNew.textProperty().bind(menuNewViewModel.newAccessibleTextProperty());
        menuMaxiButtonNew.visibleProperty().bind(menuNewViewModel.isUpToDateProperty().not());
        menuMiniButtonNew.visibleProperty().bind(menuNewViewModel.isUpToDateProperty().not());
        menuMaxiButtonNew
                .getTooltip()
                .textProperty()
                .bind(menuNewViewModel.maxiNewTooltipProperty());
        menuMiniButtonNew.getTooltip().textProperty().bind(menuNewViewModel.newTooltipProperty());
        menuNewViewModel
                .statusMessageProperty()
                .addListener(
                        (obs, oldMsg, newMsg) -> {
                            if (newMsg != null && !newMsg.isEmpty()) {
                                toaster.addToast(newMsg, newMsg, ToastLevels.INFO, false);
                            }
                        });
    }

    /**
     * Sets up bindings between the background menu UI components and menuBackgroundViewModel.
     *
     * <p>Binds accessibility texts, tooltips, role descriptions, labels, and synchronizes the
     * background color picker changes with the ViewModel to update and apply the background color
     * dynamically.
     */
    private void backgroundMenuInitialization() {
        menuMaxiButtonBackground
                .accessibleTextProperty()
                .bind(menuBackgroundViewModel.backgroundMenuMaxiAccessibleTextProperty());
        menuMaxiButtonBackground
                .getTooltip()
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundMenuMaxiTooltipProperty());
        menuMaxiButtonBackground
                .accessibleRoleDescriptionProperty()
                .bind(menuBackgroundViewModel.backgroundMenuMaxiRoleDescriptionProperty());
        menuMaxiButtonBackgroundText
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundMenuMaxiTextProperty());
        menuBackgroundButtonReduce
                .getTooltip()
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundReduceTooltipProperty());
        menuBackgroundButtonReduce
                .accessibleTextProperty()
                .bind(menuBackgroundViewModel.backgroundReduceAccessibleTextProperty());
        menuBackgroundButtonReduceText
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundReduceTextProperty());
        menuBackgroundButtonBackgroundText
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundTextProperty());
        menuBackgroundButtonBackground
                .accessibleTextProperty()
                .bind(menuBackgroundViewModel.backgroundAccessibleTextProperty());
        menuBackgroundButtonBackground
                .getTooltip()
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundTooltipProperty());
        menuBackgroundButtonBackground
                .accessibleRoleDescriptionProperty()
                .bind(menuBackgroundViewModel.backgroundRoleDescriptionProperty());
        menuBackgroundButtonImageText
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundImageTextProperty());
        menuBackgroundButtonImage
                .accessibleTextProperty()
                .bind(menuBackgroundViewModel.backgroundImageAccessibleTextProperty());
        menuBackgroundButtonImage
                .getTooltip()
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundImageTooltipProperty());
        menuBackgroundButtonImage
                .accessibleRoleDescriptionProperty()
                .bind(menuBackgroundViewModel.backgroundImageRoleDescriptionProperty());
        menuBackgroundButtonColor
                .accessibleTextProperty()
                .bind(menuBackgroundViewModel.backgroundColorAccessibleTextProperty());
        menuBackgroundButtonColor
                .getTooltip()
                .textProperty()
                .bind(menuBackgroundViewModel.backgroundColorTooltipProperty());
        menuBackgroundButtonColor
                .accessibleRoleDescriptionProperty()
                .bind(menuBackgroundViewModel.backgroundColorRoleDescriptionProperty());
        menuBackgroundButtonColor
                .valueProperty()
                .addListener(
                        (obs, oldColor, newColor) ->
                                menuBackgroundViewModel.updateBackgroundColorAndApply(
                                        sudokuFX, newColor));
        menuBackgroundViewModel.init(sudokuFX, menuBackgroundButtonColor, toaster, spinner);
    }

    /**
     * Sets up bindings between the solve menu UI components and menuSolveViewModel.
     *
     * <p>Binds accessibility texts, tooltips, role descriptions, and labels, and synchronizes stars
     * percentage from menuLevelViewModel to menuSolveViewModel.
     */
    private void solveMenuInitialization() {
        menuSolveViewModel.solvePercentageProperty().bind(menuLevelViewModel.percentageProperty());
        menuMaxiButtonSolve
                .accessibleTextProperty()
                .bind(menuSolveViewModel.solveMenuMaxiAccessibleTextProperty());
        menuMaxiButtonSolve
                .getTooltip()
                .textProperty()
                .bind(menuSolveViewModel.solveMenuMaxiTooltipProperty());
        menuMaxiButtonSolve
                .accessibleRoleDescriptionProperty()
                .bind(menuSolveViewModel.solveMenuMaxiRoleDescriptionProperty());
        menuMaxiButtonSolveText.textProperty().bind(menuSolveViewModel.solveMenuMaxiTextProperty());
        menuSolveButtonReduce
                .accessibleTextProperty()
                .bind(menuSolveViewModel.solveReduceAccessibleTextProperty());
        menuSolveButtonReduce
                .getTooltip()
                .textProperty()
                .bind(menuSolveViewModel.solveReduceTooltipProperty());
        menuSolveButtonReduceText.textProperty().bind(menuSolveViewModel.solveReduceTextProperty());
        menuSolveHBoxPossibilities.setVisible(true);
        menuSolveHBoxPossibilities
                .getPercentage()
                .bind(menuSolveViewModel.solvePercentageProperty());
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
        menuSolveButtonSolve
                .accessibleRoleDescriptionProperty()
                .bind(menuSolveViewModel.solveRoleDescriptionProperty());
        menuSolveButtonSolveText.textProperty().bind(menuSolveViewModel.solveTextProperty());
        menuSolveButtonSolveClear
                .accessibleTextProperty()
                .bind(menuSolveViewModel.solveClearAccessibleTextProperty());
        menuSolveButtonSolveClear
                .accessibleRoleDescriptionProperty()
                .bind(menuSolveViewModel.solveClearRoleDescriptionProperty());
        menuSolveButtonSolveClear
                .getTooltip()
                .textProperty()
                .bind(menuSolveViewModel.solveClearTooltipProperty());
    }

    /**
     * Initializes bindings and event listeners for the save menu components. Binds accessibility
     * texts, tooltips, and labels to the ViewModel. Synchronizes the selected backup between the
     * ListView and the ViewModel. Sets up the ListView with custom backup cells and refreshes UI
     * state.
     */
    private void saveMenuInitialization() {
        menuMaxiButtonBackup
                .accessibleTextProperty()
                .bind(menuSaveViewModel.maxiBackupAccessibleTextProperty());
        menuMaxiButtonBackup
                .getTooltip()
                .textProperty()
                .bind(menuSaveViewModel.maxiBackupTooltipProperty());
        menuMaxiButtonBackup
                .accessibleRoleDescriptionProperty()
                .bind(menuSaveViewModel.maxiBackupRoleDescriptionProperty());
        menuMaxiButtonBackupText.textProperty().bind(menuSaveViewModel.maxiBackupTextProperty());
        menuSaveButtonReduce
                .accessibleTextProperty()
                .bind(menuSaveViewModel.reduceAccessibleTextProperty());
        menuSaveButtonReduce
                .getTooltip()
                .textProperty()
                .bind(menuSaveViewModel.reduceTooltipProperty());
        menuSaveButtonReduceText.textProperty().bind(menuSaveViewModel.reduceTextProperty());
        menuSaveButtonSave
                .accessibleTextProperty()
                .bind(menuSaveViewModel.saveAccessibleTextProperty());
        menuSaveButtonSave
                .getTooltip()
                .textProperty()
                .bind(menuSaveViewModel.saveTooltipProperty());
        menuSaveButtonSave
                .accessibleRoleDescriptionProperty()
                .bind(menuSaveViewModel.saveRoleDescriptionProperty());
        menuSaveButtonSaveText.textProperty().bind(menuSaveViewModel.saveTextProperty());
        menuSaveButtonBackup
                .accessibleTextProperty()
                .bind(menuSaveViewModel.backupAccessibleTextProperty());
        menuSaveButtonBackup
                .accessibleRoleDescriptionProperty()
                .bind(menuSaveViewModel.backupRoleDescriptionProperty());
        menuSaveButtonBackup
                .getTooltip()
                .textProperty()
                .bind(menuSaveViewModel.backupTooltipProperty());
        menuSaveButtonBackupText.textProperty().bind(menuSaveViewModel.backupTextProperty());
        setupListViewClip(menuSaveListView, menuSaveClipListView);
        menuSaveListView.setItems(menuSaveViewModel.getBackups());
        menuSaveListView.setCellFactory(
                param ->
                        new GenericDtoListCell<>(
                                menuSaveListView,
                                "\ue92b",
                                menuSaveViewModel.cellDeleteAccessibleTextProperty(),
                                menuSaveViewModel.cellConfirmationTitleProperty(),
                                menuSaveViewModel.cellConfirmationMessageProperty(),
                                CONFIRMATION_ALERT,
                                gameDto -> MyDateTime.INSTANCE.getFormatted(gameDto.updatedat())));
        menuSaveListView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            if (newVal != null
                                    && !newVal.equals(
                                            menuSaveViewModel.selectedBackupProperty().get())) {
                                menuSaveViewModel.selectedBackupProperty().set(newVal);
                            }
                        });
        menuSaveViewModel
                .selectedBackupProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            if (newVal != null
                                    && !newVal.equals(
                                            menuSaveListView
                                                    .getSelectionModel()
                                                    .getSelectedItem())) {
                                menuSaveListView.getSelectionModel().select(newVal);
                            }
                        });
        menuSaveListView
                .getSelectionModel()
                .select(menuSaveViewModel.selectedBackupProperty().get());
        Platform.runLater(
                () -> {
                    menuSaveListView.refresh();
                    menuSaveListView.scrollTo(menuSaveViewModel.selectedBackupProperty().get());
                });
    }

    /**
     * Initializes bindings between each menu pane's visibility and managed state and the
     * corresponding value of the active menu from ActiveMenuOrSubmenuViewModel. Ensures that only
     * the currently active menu is visible and participates in layout calculations.
     */
    private void activeMenuManagerInitialization() {
        bindVisibleAndManaged(menuHidden, ActiveMenuOrSubmenuViewModel.ActiveMenu.HIDDEN);
        bindVisibleAndManaged(menuMini, ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI);
        bindVisibleAndManaged(menuMaxi, ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI);
        bindVisibleAndManaged(menuPlayer, ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER);
        bindVisibleAndManaged(menuSolve, ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE);
        bindVisibleAndManaged(menuSave, ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKUP);
        bindVisibleAndManaged(menuBackground, ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKGROUND);
    }

    private void bindVisibleAndManaged(
            Region menu, ActiveMenuOrSubmenuViewModel.ActiveMenu menuType) {
        BooleanBinding isActive = activeMenuOrSubmenuViewModel.getActiveMenu().isEqualTo(menuType);
        menu.visibleProperty().bind(isActive);
        menu.managedProperty().bind(isActive);
    }

    /**
     * Initializes bindings and event listeners for the player menu components. Binds accessible
     * texts, tooltips, and displayed texts to the ViewModel. Synchronizes selected player state
     * between the ListView and ViewModel. Sets up the player ListView with custom cells and
     * refreshes UI state.
     */
    private void playerMenuInitialization() {
        menuMaxiButtonPlayer
                .accessibleTextProperty()
                .bind(menuPlayerViewModel.playerAccessibleTextProperty());
        menuMaxiButtonPlayer
                .getTooltip()
                .textProperty()
                .bind(menuPlayerViewModel.maxiPlayerTooltipProperty());
        menuMaxiButtonPlayer
                .accessibleRoleDescriptionProperty()
                .bind(menuPlayerViewModel.maxiPlayerRoleDescriptionProperty());
        menuMaxiButtonPlayerText
                .textProperty()
                .bind(
                        Bindings.createStringBinding(
                                () -> {
                                    PlayerDto selected =
                                            menuPlayerViewModel.selectedPlayerProperty().get();
                                    return selected != null ? selected.name() : "";
                                },
                                menuPlayerViewModel.selectedPlayerProperty()));
        menuPlayerButtonReduce
                .accessibleTextProperty()
                .bind(menuPlayerViewModel.reduceAccessibleTextProperty());
        menuPlayerButtonReduce
                .getTooltip()
                .textProperty()
                .bind(menuPlayerViewModel.reduceTooltipProperty());
        menuPlayerButtonReduceText.textProperty().bind(menuPlayerViewModel.reduceTextProperty());
        menuPlayerButtonPlayer
                .accessibleTextProperty()
                .bind(menuPlayerViewModel.playerAccessibleTextProperty());
        menuPlayerButtonPlayer
                .getTooltip()
                .textProperty()
                .bind(menuPlayerViewModel.playerTooltipProperty());
        menuPlayerButtonPlayer
                .accessibleRoleDescriptionProperty()
                .bind(menuPlayerViewModel.playerRoleDescriptionProperty());
        menuPlayerButtonPlayerText
                .textProperty()
                .bind(
                        Bindings.createStringBinding(
                                () -> {
                                    PlayerDto selected =
                                            menuPlayerViewModel.selectedPlayerProperty().get();
                                    return selected != null ? selected.name() : "";
                                },
                                menuPlayerViewModel.selectedPlayerProperty()));
        menuPlayerButtonPlayerEdit
                .accessibleTextProperty()
                .bind(menuPlayerViewModel.editAccessibleTextProperty());
        menuPlayerButtonPlayerEdit
                .accessibleRoleDescriptionProperty()
                .bind(menuPlayerViewModel.editRoleDescriptionProperty());
        menuPlayerButtonPlayerEdit
                .getTooltip()
                .textProperty()
                .bind(menuPlayerViewModel.editTooltipProperty());
        menuPlayerButtonNew
                .accessibleTextProperty()
                .bind(menuPlayerViewModel.newAccessibleTextProperty());
        menuPlayerButtonNew
                .accessibleRoleDescriptionProperty()
                .bind(menuPlayerViewModel.newRoleDescriptionProperty());
        menuPlayerButtonNew
                .getTooltip()
                .textProperty()
                .bind(menuPlayerViewModel.newTooltipProperty());
        menuPlayerButtonNewText.textProperty().bind(menuPlayerViewModel.newTextProperty());
        setupListViewClip(menuPlayerListView, menuPlayerClipListView);
        menuPlayerListView.setItems(menuPlayerViewModel.getPlayers());
        menuPlayerListView.setCellFactory(
                param ->
                        new GenericDtoListCell<>(
                                menuPlayerListView,
                                "\uef67",
                                menuPlayerViewModel.cellButtonAccessibleTextProperty(),
                                menuPlayerViewModel.cellConfirmationTitleProperty(),
                                menuPlayerViewModel.cellConfirmationMessageProperty(),
                                CONFIRMATION_ALERT,
                                PlayerDto::name));
        menuPlayerListView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, old, selected) -> {
                            if (selected != null
                                    && !selected.equals(
                                            menuPlayerViewModel.selectedPlayerProperty().get())) {
                                menuPlayerViewModel.selectedPlayerProperty().set(selected);
                            }
                        });
        menuPlayerViewModel
                .selectedPlayerProperty()
                .addListener(
                        (obs, old, selected) -> {
                            if (selected != null
                                    && !selected.equals(
                                            menuPlayerListView
                                                    .getSelectionModel()
                                                    .getSelectedItem())) {
                                menuPlayerListView.getSelectionModel().select(selected);
                            }
                        });
        menuPlayerListView
                .getSelectionModel()
                .select(menuPlayerViewModel.selectedPlayerProperty().get());
        Platform.runLater(
                () -> {
                    menuPlayerListView.refresh();
                    menuPlayerListView.scrollTo(menuPlayerViewModel.selectedPlayerProperty().get());
                });
    }

    /**
     * Initializes the maxi menu components by binding their labels, accessible texts, and tooltips
     * to the corresponding properties in the ViewModel.
     */
    private void maxiMenuInitialization() {
        menuMaxiButtonReduceText.textProperty().bind(menuMaxiViewModel.reduceTextProperty());
        menuMaxiButtonLanguageIso.textProperty().bind(menuMaxiViewModel.languageIsoProperty());
        menuMaxiButtonLanguageText.textProperty().bind(menuMaxiViewModel.languageTextProperty());
        menuMaxiButtonHelpText.textProperty().bind(menuMaxiViewModel.helpTextProperty());
        menuMaxiButtonReduce
                .accessibleTextProperty()
                .bind(menuMaxiViewModel.reduceAccessibleTextProperty());
        menuMaxiButtonLanguage
                .accessibleTextProperty()
                .bind(menuMaxiViewModel.languageAccessibleTextProperty());
        menuMaxiButtonHelp
                .accessibleTextProperty()
                .bind(menuMaxiViewModel.helpAccessibleTextProperty());
        menuMaxiButtonReduce
                .getTooltip()
                .textProperty()
                .bind(menuMaxiViewModel.reduceTooltipProperty());
        menuMaxiButtonLanguage
                .getTooltip()
                .textProperty()
                .bind(menuMaxiViewModel.languageTooltipProperty());
        menuMaxiButtonHelp
                .getTooltip()
                .textProperty()
                .bind(menuMaxiViewModel.helpTooltipProperty());
    }

    /**
     * Initializes each difficulty level section in the menu by binding UI components to ViewModel
     * properties for EASY, MEDIUM, and DIFFICULT levels.
     */
    private void levelsMenuInitialization() {
        bindLevel(
                DifficultyLevel.EASY,
                menuMaxiHBoxEasyPossibilities,
                menuMaxiButtonEasy,
                menuMaxiButtonEasyText,
                menuMiniButtonEasy);
        bindLevel(
                DifficultyLevel.MEDIUM,
                menuMaxiHBoxMediumPossibilities,
                menuMaxiButtonMedium,
                menuMaxiButtonMediumText,
                menuMiniButtonMedium);
        bindLevel(
                DifficultyLevel.DIFFICULT,
                menuMaxiHBoxDifficultPossibilities,
                menuMaxiButtonDifficult,
                menuMaxiButtonDifficultText,
                menuMiniButtonDifficult);
    }

    /**
     * Initializes the mini menu by binding all buttons' texts and tooltips to their respective
     * ViewModel properties.
     */
    private void miniMenuInitialization() {
        menuMiniButtonShow.textProperty().bind(menuMiniViewModel.showAccessibleTextProperty());
        menuMiniButtonShow
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.showTooltipProperty());
        menuMiniButtonPlayer.textProperty().bind(menuMiniViewModel.playerAccessibleTextProperty());
        menuMiniButtonPlayer
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.playerTooltipProperty());
        menuMiniButtonSolve.textProperty().bind(menuMiniViewModel.solveAccessibleTextProperty());
        menuMiniButtonSolve
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.solveTooltipProperty());
        menuMiniButtonBackup.textProperty().bind(menuMiniViewModel.backupAccessibleTextProperty());
        menuMiniButtonBackup
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.backupTooltipProperty());
        menuMiniButtonBackground
                .textProperty()
                .bind(menuMiniViewModel.backgroundAccessibleTextProperty());
        menuMiniButtonBackground
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.backgroundTooltipProperty());
        menuMiniButtonLanguage
                .textProperty()
                .bind(menuMiniViewModel.languageAccessibleTextProperty());
        menuMiniButtonLanguage
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.languageTooltipProperty());
        menuMiniButtonLanguageIso
                .textProperty()
                .bind(menuMiniViewModel.menuMiniButtonLanguageIsoTextProperty());
        menuMiniButtonHelp.textProperty().bind(menuMiniViewModel.helpAccessibleTextProperty());
        menuMiniButtonHelp
                .getTooltip()
                .textProperty()
                .bind(menuMiniViewModel.helpTooltipProperty());
    }

    /**
     * Initializes the hidden menu by binding its show button's accessible text and tooltip to the
     * ViewModel.
     */
    private void hiddenMenuInitialization() {
        menuHiddenButtonShow
                .accessibleTextProperty()
                .bind(menuHiddenViewModel.menuHiddenButtonShowAccessibilityTextProperty());
        menuHiddenButtonShow
                .getTooltip()
                .textProperty()
                .bind(menuHiddenViewModel.menuHiddenButtonShowAccessibilityTextProperty());
    }

    /**
     * Binds UI components related to a specific difficulty level in both the maxi and mini menus.
     * This method sets up the following behaviors:
     *
     * <ul>
     *   <li><strong>Label:</strong> Binds the level name label text to the localized difficulty
     *       name.
     *   <li><strong>Percentage binding:</strong> Binds the percentage property from the ViewModel
     *       to the stars box, ensuring that star display updates reflect changes in the ViewModel.
     *   <li><strong>Visibility:</strong> Shows the stars box only when the level is selected.
     *   <li><strong>Accessibility:</strong> Binds accessible text and role descriptions for screen
     *       readers.
     *   <li><strong>Tooltip:</strong> Synchronizes tooltip text with accessible text for
     *       consistency.
     *   <li><strong>Styling:</strong> Applies a pseudo-class to indicate selection state on
     *       buttons.
     * </ul>
     *
     * @param level the difficulty level to bind
     * @param starsBox the UI component displaying possibility stars for this level
     * @param maxi the button in the maxi menu for this level
     * @param label the label showing the difficulty level name in the maxi menu
     * @param mini the button in the mini menu for this level
     */
    private void bindLevel(
            DifficultyLevel level,
            PossibilityStarsHBox starsBox,
            Button maxi,
            Label label,
            Button mini) {
        starsBox.getPercentage().bind(menuLevelViewModel.percentageProperty());
        bindLevelLabelText(level, label);
        bindLevelStarsVisibility(level, starsBox);
        bindLevelAccessibility(level, starsBox, maxi, mini);
        bindLevelTooltipText(level, starsBox, maxi, mini);
        bindLevelSelectedStyling(level, maxi, mini);
    }

    /**
     * Binds the label's text to the localized name of the specified difficulty level.
     *
     * @param level the difficulty level whose label text to bind
     * @param label the label to bind
     */
    private void bindLevelLabelText(DifficultyLevel level, Label label) {
        label.textProperty().bind(menuLevelViewModel.labelTextBinding(level));
    }

    /**
     * Binds the visibility of the stars box to whether the specified difficulty level is selected.
     *
     * @param level the difficulty level whose selection state controls visibility
     * @param starsBox the stars box to show or hide
     */
    private void bindLevelStarsVisibility(DifficultyLevel level, PossibilityStarsHBox starsBox) {
        starsBox.visibleProperty().bind(menuLevelViewModel.isSelectedBinding(level));
    }

    /**
     * Binds accessibility properties (accessible text and role description) for the specified
     * difficulty level's buttons.
     *
     * @param level the difficulty level whose accessibility properties are bound
     * @param starsBox the stars box used for calculating accessible text
     * @param maxi the button in the maxi menu
     * @param mini the button in the mini menu
     */
    private void bindLevelAccessibility(
            DifficultyLevel level, PossibilityStarsHBox starsBox, Button maxi, Button mini) {
        String accessibilityKey = menuLevelViewModel.getAccessibilityKeyForLevel(level);
        StringBinding accessibleText =
                menuLevelViewModel.accessibleTextBinding(starsBox, accessibilityKey);
        StringBinding roleDescription = menuLevelViewModel.selectedRoleDescriptionBinding();
        BooleanBinding isSelected = menuLevelViewModel.isSelectedBinding(level);

        maxi.accessibleTextProperty().bind(accessibleText);
        mini.accessibleTextProperty().bind(accessibleText);
        maxi.accessibleRoleDescriptionProperty()
                .bind(Bindings.when(isSelected).then(roleDescription).otherwise((String) null));
        mini.accessibleRoleDescriptionProperty()
                .bind(Bindings.when(isSelected).then(roleDescription).otherwise((String) null));
    }

    /**
     * Binds the tooltip text properties of both buttons to the accessible text corresponding to the
     * specified difficulty level to ensure consistency and improve user experience.
     *
     * @param level the difficulty level whose tooltip text is bound
     * @param starsBox the stars UI component used to compute accessible text
     * @param maxi the button in the maxi menu
     * @param mini the button in the mini menu
     */
    private void bindLevelTooltipText(
            DifficultyLevel level, PossibilityStarsHBox starsBox, Button maxi, Button mini) {
        String accessibilityKey = menuLevelViewModel.getAccessibilityKeyForLevel(level);
        StringBinding accessibleText =
                menuLevelViewModel.accessibleTextBinding(starsBox, accessibilityKey);
        maxi.getTooltip().textProperty().bind(accessibleText);
        mini.getTooltip().textProperty().bind(accessibleText);
    }

    /**
     * Binds the pseudo-class ":selected" state of the given buttons to the specified difficulty
     * level. Updates styling immediately and whenever the selected level changes.
     *
     * @param level the difficulty level to track
     * @param maxi the button in the maxi menu
     * @param mini the button in the mini menu
     */
    private void bindLevelSelectedStyling(DifficultyLevel level, Button maxi, Button mini) {
        menuLevelViewModel
                .selectedLevelProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            boolean selected = newVal == level;
                            maxi.pseudoClassStateChanged(
                                    DIFFICULTY_LEVEL_PSEUDO_SELECTED, selected);
                            mini.pseudoClassStateChanged(
                                    DIFFICULTY_LEVEL_PSEUDO_SELECTED, selected);
                        });
    }

    /**
     * Handles background image selection from file chooser. Opens file dialog and applies selected
     * image if valid.
     */
    @FXML
    private void handleFileImageChooser() {
        File selectedFile = menuBackgroundViewModel.chooseBackgroundImage(primaryStage);
        if (Objects.nonNull(selectedFile)) {
            menuBackgroundViewModel.handleFileImageChooser(
                    selectedFile, toaster, spinner, sudokuFX);
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

    /** Applies the EASY difficulty level, updates the grid and level view models accordingly. */
    public void handleEasyLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.EASY, gridViewModel.applyLevel(DifficultyLevel.EASY));
    }

    /** Applies the MEDIUM difficulty level, updates the grid and level view models accordingly. */
    public void handleMediumLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.MEDIUM, gridViewModel.applyLevel(DifficultyLevel.MEDIUM));
    }

    /**
     * Applies the DIFFICULT difficulty level, updates the grid and level view models accordingly.
     */
    public void handleDifficultLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.DIFFICULT, gridViewModel.applyLevel(DifficultyLevel.DIFFICULT));
    }

    /**
     * Activates the MINI menu and starts a timer to auto-hide it after N seconds if it remains
     * active and the "menuMiniButtonShow" button still has focus.
     */
    public void handleMenuMiniShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.MINI);
        menuMiniButtonShow.requestFocus();
        hideMiniMenuTimeline.stop();
        hideMiniMenuTimeline.play();
    }

    /**
     * Activates the MAXI menu and sets focus on the corresponding button based on the event source.
     * Stops the MINI menu auto-hide timer if running.
     *
     * @param event the event triggered by a submenu button click
     */
    public void handleMenuMaxiShow(ActionEvent event) {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.MAXI);
        Object source = event.getSource();
        if (!(source instanceof Button button)) {
            return;
        }
        switch (button.getId()) {
            case "menuPlayerButtonPlayer" -> menuMaxiButtonPlayer.requestFocus();
            case "menuSolveButtonSolve" -> menuMaxiButtonSolve.requestFocus();
            case "menuSaveButtonSave" -> menuMaxiButtonBackup.requestFocus();
            case "menuBackgroundButtonBackground" -> menuMaxiButtonBackground.requestFocus();
            default -> menuMaxiButtonReduce.requestFocus();
        }
        hideMiniMenuTimeline.stop();
    }

    /**
     * Activates the PLAYER menu, refreshes the player list to reflect potential localization
     * changes, and sets focus on the player button to assist keyboard and accessibility navigation.
     */
    public void handleMenuPlayerShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.PLAYER);
        menuPlayerListView.refresh();
        menuPlayerButtonPlayer.requestFocus();
    }

    /** Activates the SOLVE menu and sets focus on the solve button. */
    public void handleMenuSolveShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE);
        menuSolveButtonSolve.requestFocus();
    }

    /**
     * Activates the BACKUP menu, refreshes the backup list to ensure the displayed data is
     * up-to-date, and sets focus on the save button to facilitate keyboard navigation and
     * accessibility.
     */
    public void handleMenuBackupShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.BACKUP);
        menuSaveListView.refresh();
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

    /** Switches language. */
    public void handleToggleLanguage() {
        coordinator.toggleLanguage();
    }

    /** Opens GitHub releases URL via the Coordinator. */
    public void handleMenuNewShow() {
        coordinator.openGitHubRepositoryReleaseUrl();
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
