/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view;

import java.time.Year;
import java.util.Objects;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.config.JVMApplicationProperties;

import static fr.softsf.sudokufx.common.enums.Paths.LOGO_SUDO_PNG_PATH;
import static fr.softsf.sudokufx.common.enums.Paths.RESOURCES_CSS_PATH;
import static fr.softsf.sudokufx.common.enums.ScreenSize.DISPOSABLE_SIZE;
import static javafx.scene.layout.Priority.ALWAYS;

/**
 * View class for the splash screen of the Sudoku application. This class is responsible for
 * creating and managing the splash screen UI.
 */
public final class SplashScreenView implements ISplashScreenView {

    private static final String FX_FONT_SIZE_0_8_EM = "-fx-font-size: 0.8em;";

    // Constantes extraites pour Magic Numbers
    private static final double SPLASHSCREEN_WIDTH_FACTOR = 0.612;
    private static final double SPLASHSCREEN_HEIGHT_FACTOR = 0.3;
    private static final double FONT_SIZE_FACTOR = 0.0219;
    private static final double DROP_SHADOW_RADIUS_DIVISOR = 2.0;
    private static final double DROP_SHADOW_OFFSET_DIVISOR = 8.0;
    private static final double FLOWER_SVG_SCALE_FACTOR = 5.474;
    private static final double ROTATE_FROM_ANGLE = -4.0;
    private static final double ROTATE_TO_ANGLE = 4.0;
    private static final double INTERPOLATOR_CONTROL_X1 = 0.5;
    private static final double INTERPOLATOR_CONTROL_Y1 = 0.0;
    private static final double INTERPOLATOR_CONTROL_X2 = 0.5;
    private static final double INTERPOLATOR_CONTROL_Y2 = 1.0;
    private static final double SOFT64_SVG_WIDTH_SCALE = 14.23;
    private static final double SOFT64_SVG_HEIGHT_SCALE = 2.0;
    private static final double STROKE_DIVISOR_FOR_SUDOSVG = 17;
    private static final String SOFT64_BG_COLOR = "#E12957";
    private static final String SUDOSVG_BG_COLOR_TOP = "#FFBE99";
    private static final String SUDOSVG_BG_COLOR_BOTTOM = "#FF4340";
    private static final String SUDOSVG_BORDER_COLOR = "#1D1D30";

    private final VBox splashscreenvbox = new VBox();
    private final HBox splashScreenvboxtophbox = new HBox();
    private final Region splashscreenvboxTophboxRegionsudosvg = new Region();
    private final Pane splashScreenvboxtophboxpane = new Pane();
    private final Label splashscreenvboxTophboxNamelabel = new Label();
    private final HBox splashscreenvboxCenterhbox = new HBox();
    private final Region splashscreenvboxCenterhboxRegionflowersvg = new Region();
    private final Region splashscreenvboxCenterhboxRegiontextsvg = new Region();
    private final HBox splashscreenvboxbottomhbox = new HBox();
    private final Label splashscreenvboxBottomhboxYearlabel = new Label();
    private final HBox splashscreenvboxbottomhboxhbox = new HBox();
    private final Label splashscreenvboxBottomhboxHboxLoaderlabel = new Label();
    private final Label splashscreenvboxBottomhboxVersionlabel = new Label();
    private final Stage splashScreenStage;
    private final DropShadow dropShadow = new DropShadow();
    private Scene scene;
    private double splashScreenFontSize;

    /**
     * Constructor for SplashScreenView. Initializes the splash screen UI and displays it.
     *
     * @param splashScreenStageP The Stage to use for the splash screen
     */
    public SplashScreenView(final Stage splashScreenStageP) {
        splashScreenStage = splashScreenStageP;
        fxmlLikeStructure();
        nodesConfiguration();
        nestingOfNodes();
        showSplashScreen();
    }

    /** Nests the UI components in their parent containers. */
    private void nestingOfNodes() {
        splashScreenvboxtophbox
                .getChildren()
                .addAll(
                        splashscreenvboxTophboxRegionsudosvg,
                        splashScreenvboxtophboxpane,
                        splashscreenvboxTophboxNamelabel);
        splashscreenvboxCenterhbox
                .getChildren()
                .addAll(
                        splashscreenvboxCenterhboxRegionflowersvg,
                        splashscreenvboxCenterhboxRegiontextsvg);
        splashscreenvboxbottomhboxhbox
                .getChildren()
                .addAll(splashscreenvboxBottomhboxHboxLoaderlabel);
        splashscreenvboxbottomhbox
                .getChildren()
                .addAll(
                        splashscreenvboxBottomhboxYearlabel,
                        splashscreenvboxbottomhboxhbox,
                        splashscreenvboxBottomhboxVersionlabel);
        splashscreenvbox
                .getChildren()
                .addAll(
                        splashScreenvboxtophbox,
                        splashscreenvboxCenterhbox,
                        splashscreenvboxbottomhbox);
    }

    /** Configures the properties and styles of the UI nodes. */
    private void nodesConfiguration() {
        scene =
                new Scene(
                        splashscreenvbox,
                        DISPOSABLE_SIZE.getSize() * SPLASHSCREEN_WIDTH_FACTOR,
                        DISPOSABLE_SIZE.getSize() * SPLASHSCREEN_HEIGHT_FACTOR,
                        Color.TRANSPARENT);
        scene.getStylesheets()
                .add(
                        (Objects.requireNonNull(
                                        getClass().getResource(RESOURCES_CSS_PATH.getPath())))
                                .toExternalForm());
        final Color splashDefaultFontColor = Color.web("#ffffff");
        splashScreenStage
                .getIcons()
                .add(
                        new Image(
                                (Objects.requireNonNull(
                                                getClass()
                                                        .getResource(LOGO_SUDO_PNG_PATH.getPath())))
                                        .toExternalForm()));
        splashScreenStage.initStyle(StageStyle.UNDECORATED);
        splashScreenStage.centerOnScreen();
        splashscreenvbox.setPrefWidth(DISPOSABLE_SIZE.getSize() * SPLASHSCREEN_WIDTH_FACTOR);
        splashscreenvbox.setPrefHeight(DISPOSABLE_SIZE.getSize() * SPLASHSCREEN_HEIGHT_FACTOR);
        splashScreenFontSize = DISPOSABLE_SIZE.getSize() * FONT_SIZE_FACTOR;
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(splashScreenFontSize / DROP_SHADOW_RADIUS_DIVISOR);
        dropShadow.setOffsetX(splashScreenFontSize / DROP_SHADOW_OFFSET_DIVISOR);
        dropShadow.setOffsetY(splashScreenFontSize / DROP_SHADOW_OFFSET_DIVISOR);
        splashscreenvboxTophboxNamelabel.setText(JVMApplicationProperties.INSTANCE.getAppName());
        splashscreenvboxTophboxNamelabel.setTextFill(splashDefaultFontColor);
        setSplashscreenvboxTophboxLogosudosvg();
        splashscreenvboxCenterhbox.setSpacing(splashScreenFontSize);
        setSplashscreenvboxCenterhboxStackpaneLogoflowersvg();
        animateFlowerSvg();
        splashscreenvboxBottomhboxYearlabel.setText(Year.now() + "");
        splashscreenvboxBottomhboxYearlabel.setTextFill(splashDefaultFontColor);
        splashscreenvboxBottomhboxHboxLoaderlabel.setText(getLoadingOrOptimizingMessage());
        splashscreenvboxBottomhboxHboxLoaderlabel.setTextFill(splashDefaultFontColor);
        splashscreenvboxBottomhboxVersionlabel.setText(
                JVMApplicationProperties.INSTANCE.getAppVersion());
        splashscreenvboxBottomhboxVersionlabel.setTextFill(splashDefaultFontColor);
        setSplashscreenvboxCenterhboxLogosoft64textsvg();
    }

    /** Animates the SVG flower */
    private void animateFlowerSvg() {
        RotateTransition rotateTransition =
                new RotateTransition(
                        Duration.seconds(3), splashscreenvboxCenterhboxRegionflowersvg);
        rotateTransition.setFromAngle(ROTATE_FROM_ANGLE);
        rotateTransition.setToAngle(ROTATE_TO_ANGLE);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setInterpolator(
                Interpolator.SPLINE(
                        INTERPOLATOR_CONTROL_X1,
                        INTERPOLATOR_CONTROL_Y1,
                        INTERPOLATOR_CONTROL_X2,
                        INTERPOLATOR_CONTROL_Y2));
        rotateTransition.play();
    }

    /**
     * Retrieves a message indicating the current loading or optimizing state. The message returned
     * depends on whether the Spring context is set to exit on refresh.
     *
     * @return A string message indicating either a loading or optimizing state.
     */
    private String getLoadingOrOptimizingMessage() {
        return JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh()
                ? I18n.INSTANCE.getValue("splashscreen.optimizing")
                : I18n.INSTANCE.getValue("splashscreen.loading");
    }

    /**
     * Sets up the basic structure and properties of the UI components, similar to what would be
     * defined in an FXML file.
     */
    private void fxmlLikeStructure() {
        splashscreenvbox.setAlignment(Pos.CENTER);
        splashscreenvbox.setId("splashscreenvbox");
        splashScreenvboxtophbox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(splashScreenvboxtophbox, ALWAYS);
        HBox.setHgrow(splashScreenvboxtophboxpane, ALWAYS);
        splashscreenvboxTophboxNamelabel.setStyle(FX_FONT_SIZE_0_8_EM);
        splashscreenvboxCenterhbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(splashscreenvboxCenterhbox, ALWAYS);
        VBox.setVgrow(splashscreenvboxCenterhbox, ALWAYS);
        splashscreenvboxbottomhbox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(splashscreenvboxbottomhbox, ALWAYS);
        splashscreenvboxBottomhboxYearlabel.setStyle(FX_FONT_SIZE_0_8_EM);
        HBox.setHgrow(splashscreenvboxBottomhboxYearlabel, ALWAYS);
        splashscreenvboxbottomhboxhbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(splashscreenvboxbottomhboxhbox, ALWAYS);
        splashscreenvboxBottomhboxHboxLoaderlabel.setStyle(FX_FONT_SIZE_0_8_EM);
        splashscreenvboxBottomhboxVersionlabel.setStyle(FX_FONT_SIZE_0_8_EM);
    }

    /** Sets up the SVG for the flower logo in the center of the splash screen. */
    private void setSplashscreenvboxCenterhboxStackpaneLogoflowersvg() {
        createSVG(
                splashscreenvboxCenterhboxRegionflowersvg,
                "M103.66,67.17c-3.1-4.5-9.91-6.87-17.22-8.08,12.33-.6,15.14-3.6,19.08-7.05,5.85-5.14,5-19.7,2.28-24.67s-14-10.16-30.83-2.46a22.84,22.84,0,0,0-8.06,6.25c.13-8.55-3.56-12.44-4.64-15.1-1.86-4.56-20.13-22.12-27.12-13.84s-7.43,25.41-3.93,34.21c1,2.41,3.43,5.14,6.51,7.82a30.76,30.76,0,0,0-7.38-1.91c-13.76-1.58-27-.44-31.46,7s8.85,19.53,13.42,22.33c3.57,2.19,11.64,4,18.89,3.21-5.19,5.1-6.12,9.17-7.16,13-2,7.3,1.54,20.39,3.89,23.24s16.64,1.14,28.63-9.42c5.63-5,6.38-14.88,5.59-24a52.72,52.72,0,0,0,9.54,19.15c3.73,5,21.69,6.85,26.55,3.85S111.94,79.17,103.66,67.17Z",
                splashScreenFontSize * FLOWER_SVG_SCALE_FACTOR,
                splashScreenFontSize * FLOWER_SVG_SCALE_FACTOR,
                0,
                0);
        splashscreenvboxCenterhboxRegionflowersvg.setStyle(
                "-fx-background-color: radial-gradient("
                        + "focus-angle 45deg, focus-distance 5%, "
                        + "center 50% 50%, radius 55%, "
                        + "#1a0a26 0, #1b0a28 0.12, #1f0c2f 0.17, #220d33 0.18, "
                        + "#44123a 0.18, #701844 0.18, #961e4c 0.19, #b72353 0.19, "
                        + "#d12658 0.19, #e6295d 0.19, #f42b60 0.2, #fc2d61 0.2, "
                        + "#ff2d62 0.21, #fb4374 0.23, #f5638e 0.25, #f07ea4 0.29, "
                        + "#ec95b7 0.32, #e9a6c5 0.36, #e7b2cf 0.41, #e5b9d4 0.48, "
                        + "#e5bbd6 0.66, #e5bbd6 1);"
                        + "-fx-background-position: center center;");
    }

    /** Sets up the SVG for the Soft64 text logo in the center of the splash screen. */
    private void setSplashscreenvboxCenterhboxLogosoft64textsvg() {
        createSVG(
                splashscreenvboxCenterhboxRegiontextsvg,
                "M6684.14,303.09v17h-8.57V279.72h11.77c5.5,0,9.21,1,11.84,3s3.95,4,3.95,8a10.72,10.72,0,0,1-2,6.35,12,12,0,0,1-5.2,4.35c6.08,9.09,9.69,16,11.53,18.67h-9.15l-9.93-17Zm0-6.49h2.76c2.12,0,5.16-.15,6.62-2s2.33-5.7.1-7.31a9.94,9.94,0,0,0-5.25-1.58c-.36,0-4.23-.15-4.23-.07Z"
                    + " M6423.31,313.32h-5.72c0,1,0,1.05,0,1.68v5.21h.87c1.75,0,5.28,0,10.44,0a36.85,36.85,0,0,0,5.62-.28,11.64,11.64,0,0,0,4.65-1.43,9,9,0,0,0,3.06-3,12.43,12.43,0,0,0,1.7-6.45,10.59,10.59,0,0,0-2.1-6.67q-2.1-2.73-7.77-5.43a50.58,50.58,0,0,1-5.43-2.84,5.66,5.66,0,0,1-1.67-1.63,3.65,3.65,0,0,1-.53-2,3.72,3.72,0,0,1,1.3-3,5.49,5.49,0,0,1,3.73-1.13h12.28V279.7c-.27,0-3.49,0-4.82,0-1.84,0-3.68,0-5.52.12a53.67,53.67,0,0,0-5.8.55,21.83,21.83,0,0,0-3.76.87,11.36,11.36,0,0,0-1.25.52,4.77,4.77,0,0,0-1.13.71,9.79,9.79,0,0,0-1.81,2.14,11.4,11.4,0,0,0-1.59,6.09,10.63,10.63,0,0,0,1.1,5,12.52,12.52,0,0,0,3,3.73,24.68,24.68,0,0,0,5.56,3.34,51,51,0,0,1,5.23,2.74,7.44,7.44,0,0,1,1.95,1.78,3.94,3.94,0,0,1-.33,4.81,4.39,4.39,0,0,1-3.13,1.24C6431,313.34,6427,313.33,6423.31,313.32Z"
                    + " M6654.47,320.13H6646V279.72h23.16v6h-14.73v11.48h13.71v5.93h-13.71Z "
                    + " M6624.7,308.67h-4.87v11.62h-8.35V308.67h-17.25v-5.58L6612,279.81h7.88V303.1h4.87Zm-13.22-5.57V289.61l-10.79,13.49Z"
                    + " M6631.07,315.85a4.3,4.3,0,1,0,8.6,0a4.3,4.3,0,1,0-8.6,0Z "
                    + " M6619.91,303.42c0-9.46-8-13-13-14.36v-2.42a11.7,11.7,0,0,1,4.36-1c4.28,0,9,3.48,11.1,6.91a9.37,9.37,0,0,1,2.06,6.34c0,7.55-8.86,11.83-13.88,13.61v-2.7C6615.12,308.46,6619.91,307.08,6619.91,303.42Z"
                    + " M6651.18,300.66a11.45,11.45,0,0,0-4.36,1.1c-4.29,1.74-8.9,5.3-10.82,10a7.56,7.56,0,0,0-1,4c0,7.55,8.85,11.83,13.87,13.61v2.69a34.3,34.3,0,0,1-11.18-2.85c-5.12-2.62-8.29-8.13-8.29-14.5,0-9.49,8-13,13-14.37Z",
                splashScreenFontSize * SOFT64_SVG_WIDTH_SCALE,
                splashScreenFontSize * SOFT64_SVG_HEIGHT_SCALE,
                0,
                0);
        splashscreenvboxCenterhboxRegiontextsvg.setStyle(
                "-fx-background-color: " + SOFT64_BG_COLOR + "; -fx-background-radius: 20;");
    }

    /** Sets up the SVG for the "sudo.svg" text logo in the center of the splash screen. */
    private void setSplashscreenvboxTophboxLogosudosvg() {
        createSVG(
                splashscreenvboxTophboxRegionsudosvg,
                "M15.91 17.19v-5.19a8.5 8.5 0 0 0-2.54-.46 4.18 4.18 0 0 0-3 1.12 3.48 3.48 0 0 0-1"
                    + " 2.58 3.34 3.34 0 0 0 1 2.53 4.13 4.13 0 0 0 3 1.1 10.75 10.75 0 0 0"
                    + " 3-.47Zm2.17 2.92c-2.29.79-7.72.79-10 0a5.12 5.12 0 0 1-3.3-4.66 5.11 5.11 0"
                    + " 0 1 1.71-4.17 7.59 7.59 0 0 1 2.51-1.23c-.12-.38-.21-.75-.33-1.13A13.41"
                    + " 13.41 0 0 0 3.75 9 3.41 3.41 0 0 0 2 13a7.84 7.84 0 0 0 1.66 5.19 10.08"
                    + " 10.08 0 0 0 7.45 4 10.35 10.35 0 0 0 3.23-.52l-.26-1.47Z",
                splashScreenFontSize,
                splashScreenFontSize,
                1.0 / STROKE_DIVISOR_FOR_SUDOSVG,
                1.0 / STROKE_DIVISOR_FOR_SUDOSVG);
        splashscreenvboxTophboxRegionsudosvg.setStyle(
                "-fx-background-color: linear-gradient("
                        + "to bottom, "
                        + SUDOSVG_BG_COLOR_TOP
                        + " 0%, "
                        + SUDOSVG_BG_COLOR_BOTTOM
                        + " 70%); "
                        + "-fx-border-radius: 18; "
                        + "-fx-border-width: 1px; "
                        + "-fx-border-color: "
                        + SUDOSVG_BORDER_COLOR
                        + "; "
                        + "-fx-background-radius: 18;");
    }

    /**
     * Creates an SVG shape and applies it to a Region.
     *
     * @param region The Region to which the SVG shape will be applied
     * @param path The SVG path data
     * @param width The width of the SVG
     * @param height The height of the SVG
     * @param offsetX The X offset of the SVG
     * @param offsetY The Y offset of the SVG
     */
    private void createSVG(
            final Region region,
            final String path,
            final double width,
            final double height,
            final double offsetX,
            final double offsetY) {
        final SVGPath svgPath = new SVGPath();
        svgPath.setContent(path);
        region.setShape(svgPath);
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        region.setTranslateX(offsetX);
        region.setTranslateY(offsetY);
    }

    @Override
    public void hideSplashScreen() {
        splashScreenStage.hide();
        splashScreenStage.close();
        scene.setCursor(Cursor.DEFAULT);
    }

    @Override
    public void showSplashScreen() {
        scene.setCursor(Cursor.WAIT);
        splashScreenStage.setScene(scene);
        splashScreenStage.show();
        scene.getRoot().setStyle("-fx-font-size: " + splashScreenFontSize + "px;");
    }

    @Override
    public Scene getSplashScreenScene() {
        return scene;
    }
}
