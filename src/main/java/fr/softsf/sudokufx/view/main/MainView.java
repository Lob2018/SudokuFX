/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.main;

import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.AppPaths;
import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.interfaces.IMainView;
import fr.softsf.sudokufx.common.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.service.ui.FileChooserService;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.view.component.PossibilityStarsHBox;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.view.util.BindingConfigurator;
import fr.softsf.sudokufx.view.util.GenericListViewFactory;
import fr.softsf.sudokufx.viewmodel.ActiveMenuOrSubmenuViewModel;
import fr.softsf.sudokufx.viewmodel.HelpViewModel;
import fr.softsf.sudokufx.viewmodel.MenuHiddenViewModel;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuMaxiViewModel;
import fr.softsf.sudokufx.viewmodel.MenuMiniViewModel;
import fr.softsf.sudokufx.viewmodel.MenuNewViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.MenuPlayerViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSaveViewModel;
import fr.softsf.sudokufx.viewmodel.MenuSolveViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridCellViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

/**
 * Main view class of the Sudoku application. This class is responsible for displaying and managing
 * the UI.
 */
@Component
public final class MainView implements IMainView {

    private static final Logger LOG = LoggerFactory.getLogger(MainView.class);
    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.3;
    private static final PseudoClass DIFFICULTY_LEVEL_PSEUDO_SELECTED =
            PseudoClass.getPseudoClass("selected");
    private static final PseudoClass REDUCED_SONG_PSEUDO_SELECTED =
            PseudoClass.getPseudoClass("reduced");
    private static final PseudoClass OPAQUE_MODE_PSEUDO_SELECTED =
            PseudoClass.getPseudoClass("opaque-mode");
    private static final int AUTO_HIDE_MINI_MENU_DELAY_MS = 5_000;

    private Stage primaryStage;

    private final ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel;
    private final Coordinator coordinator;
    private final HelpViewModel helpViewModel;
    private final MenuHiddenViewModel menuHiddenViewModel;
    private final MenuMiniViewModel menuMiniViewModel;
    private final MenuLevelViewModel menuLevelViewModel;
    private final MenuMaxiViewModel menuMaxiViewModel;
    private final MenuPlayerViewModel menuPlayerViewModel;
    private final MenuSaveViewModel menuSaveViewModel;
    private final MenuSolveViewModel menuSolveViewModel;
    private final MenuOptionsViewModel menuOptionsViewModel;
    private final MenuNewViewModel menuNewViewModel;
    private final GridViewModel gridViewModel;
    private final AudioService audioService;
    private final FileChooserService fileChooserService;
    private final BindingConfigurator bindingConfigurator;
    private final GenericListViewFactory genericListViewFactory;
    private final ToasterService toasterService;

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
    @FXML private VBox menuOptions;

    @FXML private Button menuHiddenButtonShow;
    @FXML private Button menuMiniButtonShow;
    @FXML private Button menuMiniButtonPlayer;
    @FXML private Button menuMiniButtonEasy;
    @FXML private Button menuMiniButtonMedium;
    @FXML private Button menuMiniButtonDifficult;
    @FXML private Button menuMiniButtonSolve;
    @FXML private Button menuMiniButtonBackup;
    @FXML private Button menuMiniButtonOptions;
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
    @FXML private Button menuMaxiButtonOptions;
    @FXML private Label menuMaxiButtonOptionsText;
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

    @FXML private Button menuOptionsButtonReduce;
    @FXML private Label menuOptionsButtonReduceText;
    @FXML private Button menuOptionsButtonOptions;
    @FXML private Label menuOptionsButtonOptionsText;
    @FXML private Button menuOptionsButtonImage;
    @FXML private Label menuOptionsButtonImageText;
    @FXML private ColorPicker menuOptionsButtonColor;
    @FXML private Button menuOptionsButtonOpacity;
    @FXML private Label menuOptionsButtonOpacityText;
    @FXML private Text menuOptionsButtonOpacityIcon;
    @FXML private Button menuOptionsButtonMute;
    @FXML private Label menuOptionsButtonMuteText;
    @FXML private Text menuOptionsButtonMuteIcon;
    @FXML private Button menuOptionsButtonSong;
    @FXML private Label menuOptionsButtonSongText;
    @FXML private Button menuOptionsButtonSongClear;

    private Timeline hideMiniMenuTimeline;

    public MainView(
            ActiveMenuOrSubmenuViewModel activeMenuOrSubmenuViewModel,
            Coordinator coordinator,
            HelpViewModel helpViewModel,
            MenuHiddenViewModel menuHiddenViewModel,
            MenuMiniViewModel menuMiniViewModel,
            MenuLevelViewModel menuLevelViewModel,
            MenuMaxiViewModel menuMaxiViewModel,
            MenuPlayerViewModel menuPlayerViewModel,
            MenuSaveViewModel menuSaveViewModel,
            MenuSolveViewModel menuSolveViewModel,
            MenuOptionsViewModel menuOptionsViewModel,
            MenuNewViewModel menuNewViewModel,
            GridViewModel gridViewModel,
            AudioService audioService,
            FileChooserService fileChooserService,
            BindingConfigurator bindingConfigurator,
            GenericListViewFactory genericListViewFactory,
            ToasterService toasterService) {
        this.activeMenuOrSubmenuViewModel = activeMenuOrSubmenuViewModel;
        this.coordinator = coordinator;
        this.helpViewModel = helpViewModel;
        this.menuHiddenViewModel = menuHiddenViewModel;
        this.menuMiniViewModel = menuMiniViewModel;
        this.menuLevelViewModel = menuLevelViewModel;
        this.menuMaxiViewModel = menuMaxiViewModel;
        this.menuPlayerViewModel = menuPlayerViewModel;
        this.menuSaveViewModel = menuSaveViewModel;
        this.menuSolveViewModel = menuSolveViewModel;
        this.menuOptionsViewModel = menuOptionsViewModel;
        this.menuNewViewModel = menuNewViewModel;
        this.gridViewModel = gridViewModel;
        this.audioService = audioService;
        this.fileChooserService = fileChooserService;
        this.bindingConfigurator = bindingConfigurator;
        this.genericListViewFactory = genericListViewFactory;
        this.toasterService = toasterService;
    }

    /**
     * Initializes the main view.
     *
     * <p>This method is automatically called by JavaFX after loading the FXML. It performs the
     * following actions:
     *
     * <ul>
     *   <li>Sets the application's locale based on the current player's language.
     *   <li>Initializes audio management, menu timelines, and menu components.
     *   <li>Initializes the grid and active menu manager.
     * </ul>
     */
    @FXML
    private void initialize() {
        primaryStage = new Stage();
        I18n.INSTANCE.setLocaleBundle(coordinator.getCurrentPlayerLanguageIso());
        stopAudioOnExitInitialization();
        toasterInitialization();
        hideMiniMenuTimeline = hideMiniMenuTimelineInitialization();
        hiddenMenuInitialization();
        miniMenuInitialization();
        levelsMenuInitialization();
        maxiMenuInitialization();
        playerMenuInitialization();
        saveMenuInitialization();
        solveMenuInitialization();
        optionsMenuInitialization();
        newMenuInitialization();
        activeMenuManagerInitialization();
        gridInitialization();
    }

    /**
     * Initializes the toaster by subscribing to the ToasterService. When a new ToastData is
     * published, it adds the toast to the ToasterVBox.
     */
    private void toasterInitialization() {
        toasterService
                .toastRequestProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            if (newVal != null) {
                                toaster.addToast(
                                        newVal.visibleText(),
                                        newVal.detailedText(),
                                        newVal.level(),
                                        newVal.requestFocus());
                            }
                        });
    }

    /**
     * Registers a listener on the primary stage to stop all audio when the application window is
     * closed.
     */
    private void stopAudioOnExitInitialization() {
        primaryStage.setOnCloseRequest(event -> audioService.stopAll());
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
                                        "██ Exception MainView > handleMenuMiniShow exception"
                                                + " occurred: {}",
                                        e.getMessage(),
                                        e);
                            }
                        }));
    }

    /**
     * Initializes the 9x9 Sudoku grid UI and loads the current grid values from the model if they
     * exist.
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
        restoreCurrentGridLevelFromModel();
    }

    /**
     * Binds "New" menu UI elements to the ViewModel for text, accessibility, visibility, and
     * tooltips, and shows toast notifications on status message updates.
     */
    private void newMenuInitialization() {
        bindingConfigurator.configureLabel(
                menuMaxiButtonNewText, menuNewViewModel.maxiNewTextProperty());
        bindingConfigurator.configureButton(
                menuMaxiButtonNew,
                menuNewViewModel.maxiNewTextProperty(),
                menuNewViewModel.maxiNewAccessibleTextProperty(),
                menuNewViewModel.maxiNewTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonNew,
                menuNewViewModel.newAccessibleTextProperty(),
                menuNewViewModel.newAccessibleTextProperty(),
                menuNewViewModel.newTooltipProperty(),
                null);
        bindingConfigurator.configureVisibilityAndManaged(
                menuMaxiButtonNew, menuNewViewModel.isOutOfDateProperty());
        bindingConfigurator.configureVisibilityAndManaged(
                menuMiniButtonNew, menuNewViewModel.isOutOfDateProperty());
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
     * Initializes and binds all UI components of the options menu to the {@link
     * MenuOptionsViewModel}. Loads saved options from the database and applies them to the
     * corresponding UI controls.
     *
     * <p>This method performs the following tasks:
     *
     * <ul>
     *   <li>Binds accessibility texts, tooltips, role descriptions, and labels for all options
     *       buttons.
     *   <li>Synchronizes user interactions (color selection, opacity adjustment, mute toggle, etc.)
     *       with the ViewModel.
     *   <li>Loads and applies saved configuration settings to the UI controls, including background
     *       color, background image, grid transparency, etc.
     *   <li><strong>Configures reactive UI states: song clear button visibility and song selection
     *       pseudo-class are bound to song availability state.</strong>
     * </ul>
     */
    private void optionsMenuInitialization() {
        bindingConfigurator.configureButton(
                menuMaxiButtonOptions,
                null,
                menuOptionsViewModel.optionsMenuMaxiAccessibleTextProperty(),
                menuOptionsViewModel.optionsMenuMaxiTooltipProperty(),
                menuOptionsViewModel.optionsMenuMaxiRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonOptionsText, menuOptionsViewModel.optionsMenuMaxiTextProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonReduce,
                null,
                menuOptionsViewModel.optionsReduceAccessibleTextProperty(),
                menuOptionsViewModel.optionsReduceTooltipProperty(),
                null);
        bindingConfigurator.configureLabel(
                menuOptionsButtonReduceText, menuOptionsViewModel.optionsReduceTextProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonOptions,
                null,
                menuOptionsViewModel.optionsAccessibleTextProperty(),
                menuOptionsViewModel.optionsTooltipProperty(),
                menuOptionsViewModel.optionsRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuOptionsButtonOptionsText, menuOptionsViewModel.optionsTextProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonImage,
                null,
                menuOptionsViewModel.optionsImageAccessibleTextProperty(),
                menuOptionsViewModel.optionsImageTooltipProperty(),
                menuOptionsViewModel.optionsImageRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuOptionsButtonImageText, menuOptionsViewModel.optionsImageTextProperty());
        bindingConfigurator.configureColorPicker(
                menuOptionsButtonColor,
                menuOptionsViewModel.optionsColorAccessibleTextProperty(),
                menuOptionsViewModel.optionsColorTooltipProperty(),
                menuOptionsViewModel.optionsColorRoleDescriptionProperty());
        menuOptionsButtonColor
                .valueProperty()
                .addListener(
                        (obs, oldColor, newColor) ->
                                menuOptionsViewModel.applyAndPersistOptionsColor(
                                        sudokuFX, newColor));
        bindingConfigurator.configureButton(
                menuOptionsButtonOpacity,
                null,
                menuOptionsViewModel.optionsOpacityAccessibleTextProperty(),
                menuOptionsViewModel.optionsOpacityTooltipProperty(),
                menuOptionsViewModel.optionsOpacityRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuOptionsButtonOpacityText, menuOptionsViewModel.optionsOpacityTextProperty());
        bindingConfigurator.configureText(
                menuOptionsButtonOpacityIcon, menuOptionsViewModel.optionsOpacityIconProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonMute,
                null,
                menuOptionsViewModel.optionsMuteAccessibleTextProperty(),
                menuOptionsViewModel.optionsMuteTooltipProperty(),
                menuOptionsViewModel.optionsMuteRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuOptionsButtonMuteText, menuOptionsViewModel.optionsMuteTextProperty());
        bindingConfigurator.configureText(
                menuOptionsButtonMuteIcon, menuOptionsViewModel.optionsMuteIconProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonSong,
                null,
                menuOptionsViewModel.optionsSongAccessibleTextProperty(),
                menuOptionsViewModel.optionsSongTooltipProperty(),
                menuOptionsViewModel.optionsSongRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuOptionsButtonSongText, menuOptionsViewModel.optionsSongTextProperty());
        bindingConfigurator.configureButton(
                menuOptionsButtonSongClear,
                null,
                menuOptionsViewModel.optionsClearSongAccessibleTextProperty(),
                menuOptionsViewModel.optionsClearSongTooltipProperty(),
                menuOptionsViewModel.optionsClearSongRoleDescriptionProperty());
        BooleanBinding isSongNotBlank = menuOptionsViewModel.songIsBlankProperty().not();
        bindingConfigurator.configureVisibilityAndManaged(
                menuOptionsButtonSongClear, isSongNotBlank);
        bindingConfigurator.configurePseudoClassBinding(
                menuOptionsButtonSong, isSongNotBlank, REDUCED_SONG_PSEUDO_SELECTED);
        menuOptionsViewModel.init(sudokuFX, menuOptionsButtonColor, toaster, spinner);
        applyOpaqueMode(menuOptionsViewModel.gridOpacityProperty().get());
    }

    /**
     * Sets up bindings between the solve menu UI components and menuSolveViewModel.
     *
     * <p>Binds accessibility texts, tooltips, role descriptions, and labels, and synchronizes stars
     * percentage from menuLevelViewModel to menuSolveViewModel.
     */
    private void solveMenuInitialization() {
        menuSolveViewModel.solvePercentageProperty().bind(menuLevelViewModel.percentageProperty());
        menuSolveHBoxPossibilities
                .getPercentage()
                .bind(menuSolveViewModel.solvePercentageProperty());
        menuSolveHBoxPossibilities.setVisible(true);
        bindingConfigurator.configureButton(
                menuMaxiButtonSolve,
                null,
                menuSolveViewModel.solveMenuMaxiAccessibleTextProperty(),
                menuSolveViewModel.solveMenuMaxiTooltipProperty(),
                menuSolveViewModel.solveMenuMaxiRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonSolveText, menuSolveViewModel.solveMenuMaxiTextProperty());
        bindingConfigurator.configureButton(
                menuSolveButtonReduce,
                null,
                menuSolveViewModel.solveReduceAccessibleTextProperty(),
                menuSolveViewModel.solveReduceTooltipProperty(),
                null);
        bindingConfigurator.configureLabel(
                menuSolveButtonReduceText, menuSolveViewModel.solveReduceTextProperty());
        bindingConfigurator.configureButton(
                menuSolveButtonSolve,
                null,
                menuSolveHBoxPossibilities.formattedTextBinding(
                        "menu.solve.button.solve.accessibility", false),
                menuSolveHBoxPossibilities.formattedTextBinding(
                        "menu.solve.button.solve.accessibility", true),
                menuSolveViewModel.solveRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuSolveButtonSolveText, menuSolveViewModel.solveTextProperty());
        bindingConfigurator.configureButton(
                menuSolveButtonSolveClear,
                null,
                menuSolveViewModel.solveClearAccessibleTextProperty(),
                menuSolveViewModel.solveClearTooltipProperty(),
                menuSolveViewModel.solveClearRoleDescriptionProperty());
    }

    /**
     * Initializes bindings and event listeners for the save menu components. Binds accessibility
     * texts, tooltips, and labels to the ViewModel. Synchronizes the selected backup between the
     * ListView and the ViewModel. Sets up the ListView with custom backup cells and refreshes UI
     * state.
     */
    private void saveMenuInitialization() {
        bindingConfigurator.configureButton(
                menuMaxiButtonBackup,
                null,
                menuSaveViewModel.maxiBackupAccessibleTextProperty(),
                menuSaveViewModel.maxiBackupTooltipProperty(),
                menuSaveViewModel.maxiBackupRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonBackupText, menuSaveViewModel.maxiBackupTextProperty());
        bindingConfigurator.configureButton(
                menuSaveButtonReduce,
                null,
                menuSaveViewModel.reduceAccessibleTextProperty(),
                menuSaveViewModel.reduceTooltipProperty(),
                null);
        bindingConfigurator.configureLabel(
                menuSaveButtonReduceText, menuSaveViewModel.reduceTextProperty());
        bindingConfigurator.configureButton(
                menuSaveButtonSave,
                null,
                menuSaveViewModel.saveAccessibleTextProperty(),
                menuSaveViewModel.saveTooltipProperty(),
                menuSaveViewModel.saveRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuSaveButtonSaveText, menuSaveViewModel.saveTextProperty());
        bindingConfigurator.configureButton(
                menuSaveButtonBackup,
                null,
                menuSaveViewModel.backupAccessibleTextProperty(),
                menuSaveViewModel.backupTooltipProperty(),
                menuSaveViewModel.backupRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuSaveButtonBackupText, menuSaveViewModel.backupTextProperty());
        genericListViewFactory.configureGameListView(
                menuSaveListView, menuSaveClipListView, menuSaveViewModel);
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
        bindVisibleAndManaged(menuOptions, ActiveMenuOrSubmenuViewModel.ActiveMenu.OPTIONS);
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
        bindingConfigurator.configureButton(
                menuMaxiButtonPlayer,
                null,
                menuPlayerViewModel.playerAccessibleTextProperty(),
                menuPlayerViewModel.maxiPlayerTooltipProperty(),
                menuPlayerViewModel.maxiPlayerRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonPlayerText,
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto selected = menuPlayerViewModel.selectedPlayerProperty().get();
                            return selected != null ? selected.name() : "";
                        },
                        menuPlayerViewModel.selectedPlayerProperty()));
        bindingConfigurator.configureButton(
                menuPlayerButtonReduce,
                null,
                menuPlayerViewModel.reduceAccessibleTextProperty(),
                menuPlayerViewModel.reduceTooltipProperty(),
                null);
        bindingConfigurator.configureLabel(
                menuPlayerButtonReduceText, menuPlayerViewModel.reduceTextProperty());
        bindingConfigurator.configureButton(
                menuPlayerButtonPlayer,
                null,
                menuPlayerViewModel.playerAccessibleTextProperty(),
                menuPlayerViewModel.playerTooltipProperty(),
                menuPlayerViewModel.playerRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuPlayerButtonPlayerText,
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto selected = menuPlayerViewModel.selectedPlayerProperty().get();
                            return selected != null ? selected.name() : "";
                        },
                        menuPlayerViewModel.selectedPlayerProperty()));
        bindingConfigurator.configureButton(
                menuPlayerButtonPlayerEdit,
                null,
                menuPlayerViewModel.editAccessibleTextProperty(),
                menuPlayerViewModel.editTooltipProperty(),
                menuPlayerViewModel.editRoleDescriptionProperty());
        bindingConfigurator.configureButton(
                menuPlayerButtonNew,
                null,
                menuPlayerViewModel.newAccessibleTextProperty(),
                menuPlayerViewModel.newTooltipProperty(),
                menuPlayerViewModel.newRoleDescriptionProperty());
        bindingConfigurator.configureLabel(
                menuPlayerButtonNewText, menuPlayerViewModel.newTextProperty());
        genericListViewFactory.configurePlayerListView(
                menuPlayerListView, menuPlayerClipListView, menuPlayerViewModel);
    }

    /**
     * Initializes the maxi menu components by binding their labels, accessible texts, and tooltips
     * to the corresponding properties in the ViewModel.
     */
    private void maxiMenuInitialization() {
        bindingConfigurator.configureLabel(
                menuMaxiButtonReduceText, menuMaxiViewModel.reduceTextProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonLanguageIso, menuMaxiViewModel.languageIsoProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonLanguageText, menuMaxiViewModel.languageTextProperty());
        bindingConfigurator.configureLabel(
                menuMaxiButtonHelpText, menuMaxiViewModel.helpTextProperty());
        bindingConfigurator.configureButton(
                menuMaxiButtonReduce,
                null,
                menuMaxiViewModel.reduceAccessibleTextProperty(),
                menuMaxiViewModel.reduceTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMaxiButtonLanguage,
                null,
                menuMaxiViewModel.languageAccessibleTextProperty(),
                menuMaxiViewModel.languageTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMaxiButtonHelp,
                null,
                menuMaxiViewModel.helpAccessibleTextProperty(),
                menuMaxiViewModel.helpTooltipProperty(),
                null);
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
        bindingConfigurator.configureButton(
                menuMiniButtonShow,
                menuMiniViewModel.showAccessibleTextProperty(),
                menuMiniViewModel.showAccessibleTextProperty(),
                menuMiniViewModel.showTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonPlayer,
                menuMiniViewModel.playerAccessibleTextProperty(),
                menuMiniViewModel.playerAccessibleTextProperty(),
                menuMiniViewModel.playerTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonSolve,
                menuMiniViewModel.solveAccessibleTextProperty(),
                menuMiniViewModel.solveAccessibleTextProperty(),
                menuMiniViewModel.solveTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonBackup,
                menuMiniViewModel.backupAccessibleTextProperty(),
                menuMiniViewModel.backupAccessibleTextProperty(),
                menuMiniViewModel.backupTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonOptions,
                menuMiniViewModel.optionsAccessibleTextProperty(),
                menuMiniViewModel.optionsAccessibleTextProperty(),
                menuMiniViewModel.optionsTooltipProperty(),
                null);
        bindingConfigurator.configureButton(
                menuMiniButtonLanguage,
                menuMiniViewModel.languageAccessibleTextProperty(),
                menuMiniViewModel.languageAccessibleTextProperty(),
                menuMiniViewModel.languageTooltipProperty(),
                null);
        bindingConfigurator.configureLabel(
                menuMiniButtonLanguageIso,
                menuMiniViewModel.menuMiniButtonLanguageIsoTextProperty());
        bindingConfigurator.configureButton(
                menuMiniButtonHelp,
                menuMiniViewModel.helpAccessibleTextProperty(),
                menuMiniViewModel.helpAccessibleTextProperty(),
                menuMiniViewModel.helpTooltipProperty(),
                null);
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
     * Opens a file chooser for a background image and passes the selected file to the ViewModel for
     * asynchronous loading.
     */
    @FXML
    private void handleFileImageChooser() {
        fileChooserService
                .chooseFile(primaryStage, FileChooserService.FileType.IMAGE)
                .ifPresent(
                        file ->
                                menuOptionsViewModel.applyAndPersistBackgroundImage(
                                        file, spinner, sudokuFX));
    }

    /**
     * Opens a file chooser for a song and passes the selected file to the ViewModel for saving the
     * path.
     */
    @FXML
    private void handleFileSongChooser() {
        fileChooserService
                .chooseFile(primaryStage, FileChooserService.FileType.AUDIO)
                .ifPresent(menuOptionsViewModel::saveSong);
    }

    /** Clears the song, shows a toast, and refocuses the song button. */
    @FXML
    public void handleSongClear() {
        menuOptionsViewModel.resetSongPath();
        menuOptionsButtonSong.requestFocus();
    }

    /** Handles grid transparency toggle button action. */
    @FXML
    private void handleGridOpacity() {
        boolean isOpaque = menuOptionsViewModel.toggleGridOpacityAndPersist();
        applyOpaqueMode(isOpaque);
    }

    /**
     * Applies opaque or transparent styling to the sudoku grid and its cells by setting the {@code
     * OPAQUE_MODE_PSEUDO_SELECTED} pseudo-class.
     *
     * @param opaque true for opaque styling, false for transparent
     */
    private void applyOpaqueMode(boolean opaque) {
        sudokuFXGridPane.pseudoClassStateChanged(OPAQUE_MODE_PSEUDO_SELECTED, opaque);
        Platform.runLater(
                () ->
                        sudokuFXGridPane
                                .lookupAll(".sudokuFXGridCell")
                                .forEach(
                                        cell ->
                                                cell.pseudoClassStateChanged(
                                                        OPAQUE_MODE_PSEUDO_SELECTED, opaque)));
    }

    /** Called when the mute button is pressed. Toggles the audio mute state via the ViewModel. */
    @FXML
    public void handleMute() {
        menuOptionsViewModel.toggleMuteAndPersist();
    }

    /** Applies the EASY difficulty level, updates the grid and level view models accordingly. */
    public void handleEasyLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.EASY, gridViewModel.setCurrentGridWithLevel(DifficultyLevel.EASY));
        applyOpaqueMode(menuOptionsViewModel.gridOpacityProperty().get());
    }

    /** Applies the MEDIUM difficulty level, updates the grid and level view models accordingly. */
    public void handleMediumLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.MEDIUM,
                gridViewModel.setCurrentGridWithLevel(DifficultyLevel.MEDIUM));
        applyOpaqueMode(menuOptionsViewModel.gridOpacityProperty().get());
    }

    /**
     * Applies the DIFFICULT difficulty level, updates the grid and level view models accordingly.
     */
    public void handleDifficultLevelShow() {
        menuLevelViewModel.updateSelectedLevel(
                DifficultyLevel.DIFFICULT,
                gridViewModel.setCurrentGridWithLevel(DifficultyLevel.DIFFICULT));
        applyOpaqueMode(menuOptionsViewModel.gridOpacityProperty().get());
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

    /** Restores the grid and shows the MINI menu. */
    public void handleRestoreGridAndMenuMiniShow() {
        restoreCurrentGridLevelFromModel();
        handleMenuMiniShow();
    }

    /** Restores the grid and shows the MAXI menu. */
    public void handleRestoreGridAndMenuMaxiShow(ActionEvent event) {
        restoreCurrentGridLevelFromModel();
        handleMenuMaxiShow(event);
    }

    /**
     * Loads the current user-entered grid from the model and updates the selected level and
     * completion percentage in the menu.
     *
     * <p>Used to synchronize the ViewModel with the latest grid state stored in the model.
     */
    private void restoreCurrentGridLevelFromModel() {
        gridViewModel
                .getCurrentGridFromModel()
                .ifPresent(
                        currentGrid ->
                                menuLevelViewModel.updateSelectedLevel(
                                        currentGrid.level(), currentGrid.percentage()));
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
        if (source instanceof Button button) {
            switch (button.getId()) {
                case "menuPlayerButtonPlayer" -> menuMaxiButtonPlayer.requestFocus();
                case "menuSolveButtonSolve" -> menuMaxiButtonSolve.requestFocus();
                case "menuSaveButtonSave" -> menuMaxiButtonBackup.requestFocus();
                case "menuOptionsButtonBackground" -> menuMaxiButtonOptions.requestFocus();
                default -> menuMaxiButtonReduce.requestFocus();
            }
            hideMiniMenuTimeline.stop();
        }
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

    /**
     * Activates the SOLVE menu, focuses the solve button, and updates the selected level if a
     * solved grid is available.
     */
    public void handleMenuSolveShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.SOLVE);
        menuSolveButtonSolve.requestFocus();
        gridViewModel
                .getCurrentSolvedGridFromModel()
                .ifPresent(
                        currentGrid ->
                                menuLevelViewModel.updateSelectedLevel(
                                        currentGrid.level(), currentGrid.percentage()));
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

    /** Activates the OPTIONS menu and sets focus on the options button. */
    public void handleMenuOptionsShow() {
        activeMenuOrSubmenuViewModel.setActiveMenu(ActiveMenuOrSubmenuViewModel.ActiveMenu.OPTIONS);
        menuOptionsButtonOptions.requestFocus();
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
                                                        AppPaths.LOGO_SUDO_PNG_PATH.getPath())))
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
