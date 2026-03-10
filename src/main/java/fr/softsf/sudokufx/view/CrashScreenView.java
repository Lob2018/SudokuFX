/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view;

import java.nio.file.Path;
import java.time.Year;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.enums.AppIcons;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.interfaces.IMainView;
import fr.softsf.sudokufx.common.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.common.util.IUserDataPurger;
import fr.softsf.sudokufx.common.util.LocalUserDataPurger;
import fr.softsf.sudokufx.common.util.PathValidator;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.config.os.OsFoldersConfig;

import static fr.softsf.sudokufx.common.enums.AppPaths.LOGO_SUDO_PNG_PATH;
import static fr.softsf.sudokufx.common.enums.ScreenSize.DISPOSABLE_SIZE;

/**
 * View class for the crash screen without business logic. This class is responsible for displaying
 * and managing the crash screen UI.
 *
 * <p>Line="217" is excluded from Checkstyle's LineLength check (see checkstyle-suppressions.xml).
 */
public final class CrashScreenView implements IMainView {

    private static final Logger LOG = LoggerFactory.getLogger(CrashScreenView.class);

    private static final IOsFolder OS_FOLDER_PROVIDER = new OsFoldersConfig().iOsFolderFactory();
    private static final IUserDataPurger DATA_PURGER = new LocalUserDataPurger();
    private static final double FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN = 0.5;
    private static final double CRASHSCREEN_SIZE_RATIO = 0.7;
    private static final double CRASHSCREEN_FONT_RATIO = 0.02;
    private static final double SVG_STROKE_DIVISOR = 17.0;
    public static final double CRASHSCREEN_STAGE_RATIO = .8;

    private final Stage crashscreenStage = new Stage();
    private final DropShadow dropShadow = new DropShadow();

    @SuppressFBWarnings(
            value = "UWF_FIELD_NOT_INITIALIZED_IN_CONSTRUCTOR",
            justification =
                    "The ISplashScreenView is injected via openingMainStage() before any usage."
                        + " CrashScreenView is never used before this callback, making the field"
                        + " safe by design.")
    private ISplashScreenView iSplashScreenView;

    @FXML private VBox crashscreenvbox;
    @FXML private Region crashscreenvboxTophboxRegionsudosvg;
    @FXML private Label crashscreenvboxTophboxNamelabel;
    @FXML private HBox crashscreenvboxCenterhboxHbox;
    @FXML private Label crashscreenvboxCenterhboxLabel;
    @FXML private Label crashscreenvboxCenterhboxLabel2;
    @FXML private Button buttonClose;
    @FXML private Button buttonReset;
    @FXML private Label crashscreenvboxBottomhboxYearlabel;
    @FXML private Label crashscreenvboxBottomhboxVersionlabel;
    private double crashScreenFontSize;

    /** Handles the close button click event. Logs the action and exits the application. */
    @FXML
    @SuppressFBWarnings(
            value = "UPM_UNCALLED_PRIVATE_METHOD",
            justification = "Invoked by FXML loader via reflection for UI event handling.")
    private void closeButtonClick() {
        LOG.info("▓▓▓▓ The user choose to close the application, triggering Platform.exit()");
        hidecrashscreen();
        Platform.exit();
    }

    /**
     * Handles the reset button click event. Validates the path before attempting recursive deletion
     * of the application data directory.
     */
    @FXML
    @SuppressFBWarnings(
            value = "UPM_UNCALLED_PRIVATE_METHOD",
            justification = "Invoked by FXML loader via reflection for UI event handling.")
    private void resetButtonClick() {
        LOG.info("▓▓▓▓ The user choose to reset the application data");
        final Path pathToDelete = Path.of(OS_FOLDER_PROVIDER.getOsDataFolderPath());
        PathValidator.INSTANCE.validateDirectory(pathToDelete);
        if (DATA_PURGER.deleteDataFolderRecursively(pathToDelete)) {
            LOG.info("▓▓▓▓ The directory is deleted, triggering Platform.exit()");
        } else {
            LOG.info("▓▓▓▓ The directory isn't deleted, triggering Platform.exit()");
        }
        hidecrashscreen();
        Platform.exit();
    }

    /**
     * Applies a fade-in effect to the given node.
     *
     * @param node The node to apply the fade-in effect to
     */
    private void fadeIn(final Node node) {
        FadeTransition fadeIn =
                new FadeTransition(Duration.seconds(FADE_IN_IN_SECONDS_AFTER_SPLASHSCREEN), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    /**
     * Opens the main stage and handles the transition from splash screen to crash screen.
     *
     * @param iSplashScreenView The splash screen view interface
     */
    @Override
    public void openingMainStage(ISplashScreenView iSplashScreenView) {
        this.iSplashScreenView = iSplashScreenView;
        fadeIn(iSplashScreenView.getSplashScreenScene().getRoot());
        showcrashscreen();
        iSplashScreenView.hideSplashScreen();
    }

    /**
     * Initializes the crash screen components. This method is automatically called by JavaFX after
     * loading the FXML.
     */
    @FXML
    @SuppressFBWarnings(
            value = "UPM_UNCALLED_PRIVATE_METHOD",
            justification = "Lifecycle method invoked by FXML loader via reflection.")
    private void initialize() {
        final Color crashDefaultFontColor = Color.web("#ffffff");
        crashscreenStage
                .getIcons()
                .add(
                        new Image(
                                (Objects.requireNonNull(
                                                SudoMain.class.getResource(
                                                        LOGO_SUDO_PNG_PATH.getPath())))
                                        .toExternalForm()));
        crashscreenStage.initStyle(StageStyle.TRANSPARENT);
        crashscreenStage.centerOnScreen();
        crashscreenStage.setTitle(JVMApplicationProperties.INSTANCE.getWindowTitle());
        crashscreenvbox.setPrefWidth(DISPOSABLE_SIZE.getSize() * CRASHSCREEN_SIZE_RATIO);
        crashscreenvbox.setPrefHeight(DISPOSABLE_SIZE.getSize() * CRASHSCREEN_SIZE_RATIO);
        crashScreenFontSize = DISPOSABLE_SIZE.getSize() * CRASHSCREEN_FONT_RATIO;
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(crashScreenFontSize / 2);
        dropShadow.setOffsetX(crashScreenFontSize / 8);
        dropShadow.setOffsetY(crashScreenFontSize / 8);
        crashscreenvboxTophboxNamelabel.setText(JVMApplicationProperties.INSTANCE.getAppName());
        crashscreenvboxTophboxNamelabel.setTextFill(crashDefaultFontColor);
        setCrashscreenvboxTophboxLogosudosvg();
        crashscreenvboxCenterhboxLabel.setText(I18n.INSTANCE.getValue("crashscreen.message"));
        crashscreenvboxCenterhboxLabel.setWrapText(true);
        crashscreenvboxCenterhboxLabel.setTextFill(crashDefaultFontColor);
        crashscreenvboxCenterhboxLabel2.setText(
                I18n.INSTANCE.getValue("crashscreen.extramessage")
                        + "\n"
                        + OS_FOLDER_PROVIDER.getOsDataFolderPath());
        crashscreenvboxCenterhboxLabel2.setWrapText(true);
        crashscreenvboxCenterhboxLabel2.setTextFill(crashDefaultFontColor);
        buttonReset.setText(I18n.INSTANCE.getValue("crashscreen.reset"));
        buttonClose.setText(I18n.INSTANCE.getValue("crashscreen.close"));
        crashscreenvboxCenterhboxHbox.setSpacing(crashScreenFontSize);
        crashscreenvboxBottomhboxYearlabel.setText(Year.now() + "");
        crashscreenvboxBottomhboxVersionlabel.setText(
                JVMApplicationProperties.INSTANCE.getAppVersion());
        crashscreenvboxBottomhboxYearlabel.setTextFill(crashDefaultFontColor);
        crashscreenvboxBottomhboxVersionlabel.setTextFill(crashDefaultFontColor);
    }

    /** Hides and closes the crash screen stage. */
    private void hidecrashscreen() {
        crashscreenStage.hide();
        crashscreenStage.close();
    }

    /** Shows the crash screen stage. */
    private void showcrashscreen() {
        Scene s = iSplashScreenView.getSplashScreenScene();
        crashscreenStage.setScene(s);
        crashscreenStage.setWidth(DISPOSABLE_SIZE.getSize() * CRASHSCREEN_STAGE_RATIO);
        crashscreenStage.setHeight(DISPOSABLE_SIZE.getSize() * CRASHSCREEN_STAGE_RATIO);
        crashscreenStage.show();
        buttonClose.requestFocus();
    }

    /** Sets up the SVG logo for the crash screen. */
    private void setCrashscreenvboxTophboxLogosudosvg() {
        createSVG(
                crashscreenvboxTophboxRegionsudosvg,
                AppIcons.LOGO.getPath(),
                crashScreenFontSize,
                crashScreenFontSize);
        double strokeWidth = crashScreenFontSize / SVG_STROKE_DIVISOR;
        strokeWidth = Math.max(strokeWidth, 1.0);
        if (Double.isNaN(strokeWidth)) {
            return;
        }
        crashscreenvboxTophboxRegionsudosvg.setStyle(
                String.format(
                        "-fx-background-color: linear-gradient(to bottom, #FFBE99, #FF4340);"
                                + "-fx-border-color: #1D1D30;"
                                + "-fx-border-width: %.1fpx;"
                                + "-fx-border-insets: -%.1f;",
                        strokeWidth, strokeWidth));
    }

    /**
     * Creates an SVG shape and applies it to a Region.
     *
     * @param region The Region to apply the SVG shape to
     * @param path The SVG path data
     * @param width The width of the SVG
     * @param height The height of the SVG
     */
    private void createSVG(
            final Region region, final String path, final double width, final double height) {
        final SVGPath svgPath = new SVGPath();
        svgPath.setContent(path);
        region.setShape(svgPath);
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
    }
}
