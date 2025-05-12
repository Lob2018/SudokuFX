/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
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

import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.interfaces.ISplashScreenView;

import static fr.softsf.sudokufx.enums.Paths.LOGO_SUDO_PNG_PATH;
import static fr.softsf.sudokufx.enums.Paths.RESOURCES_CSS_PATH;
import static fr.softsf.sudokufx.enums.ScreenSize.DISPOSABLE_SIZE;
import static javafx.scene.layout.Priority.ALWAYS;

/**
 * View class for the splash screen of the Sudoku application. This class is responsible for
 * creating and managing the splash screen UI.
 */
public final class SplashScreenView implements ISplashScreenView {

    private static final String FX_FONT_SIZE_0_8_EM = "-fx-font-size: 0.8em;";
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
                        DISPOSABLE_SIZE.getSize() * .612,
                        DISPOSABLE_SIZE.getSize() * .3,
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
        splashscreenvbox.setPrefWidth(DISPOSABLE_SIZE.getSize() * .612);
        splashscreenvbox.setPrefHeight(DISPOSABLE_SIZE.getSize() * .3);
        splashScreenFontSize = DISPOSABLE_SIZE.getSize() * 0.0219;
        dropShadow.setColor(Color.BLACK);
        dropShadow.setRadius(splashScreenFontSize / 2);
        dropShadow.setOffsetX(splashScreenFontSize / 8);
        dropShadow.setOffsetY(splashScreenFontSize / 8);
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
        rotateTransition.setFromAngle(-4);
        rotateTransition.setToAngle(4);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setInterpolator(Interpolator.SPLINE(0.5, 0.0, 0.5, 1.0));
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
                splashScreenFontSize * 5.474,
                splashScreenFontSize * 5.474,
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
                    + " M6631.07,315.85a4.3,4.3,0,1,0,8.6,0a4.3,4.3,0,1,0,-8.6,0Z"
                    + " M6585.89,297.56c-1.88-3.09-9.64-8.25-18.5-.17A11.43,11.43,0,0,1,6570,289s.16-.18.33-.35c2.95-2.95,10.82-3,14.81-2.83V279.7l-3.75,0c-12,0-20.31,5.66-21.6,17.65-1.74,15,4.32,22.82,15.05,22.78s13.06-9.83,13.06-13.24S6587.77,300.66,6585.89,297.56Zm-8.07,13.56a5.23,5.23,0,0,1-4.22,1.76c-3-.29-4.68-3.26-4.82-3.51a7.6,7.6,0,0,1-.78-5.21,5.77,5.77,0,0,1,2-3.09c.32-.26,3.39-2.7,6.58-.75a7.28,7.28,0,0,1,3.21,6A6.57,6.57,0,0,1,6577.82,311.12Z"
                    + " M6542.11,320.13h-8.57V285.79h-11v-6.07h30.51v6.07h-11Z"
                    + " M6501.46,320.13H6493V279.72h23.17v6h-14.74v11.48h13.71v5.93h-13.71Z"
                    + " M6486.66,299.94c0,6.69-.59,11.83-3.91,15.42s-8.08,4.84-14.27,4.84-10.94-1.24-14.26-4.84-3.91-8.75-3.91-15.48.6-11.86,3.93-15.41,8-4.76,14.24-4.76,11,1.23,14.31,4.8S6486.66,293.23,6486.66,299.94Zm-28.43,0q0,6.76,2.57,10.2c1.72,2.28,4.28,3.43,7.69,3.43q10.24,0,10.25-13.63t-10.2-13.66q-5.11,0-7.71,3.44T6458.23,299.94Z",
                splashScreenFontSize * 14.23,
                splashScreenFontSize * 2,
                0,
                0);
        splashscreenvboxCenterhboxRegiontextsvg.setStyle("-fx-background-color: #E12957;");
        splashscreenvboxCenterhboxRegiontextsvg.setEffect(dropShadow);
    }

    /** Sets up the SVG for the Sudoku logo in the top section of the splash screen. */
    private void setSplashscreenvboxTophboxLogosudosvg() {
        createSVG(
                splashscreenvboxTophboxRegionsudosvg,
                "M26.9,53.4c-.86,0-1.73,0-2.6,0l-1.43-.09c-.38,0-.74-.07-1.11-.12l-.35,0c-.82-.09-1.78-.21-2.73-.39a28.17,28.17,0,0,1-3.67-1,19.33,19.33,0,0,1-6.43-3.54l-.39-.35A10.24,10.24,0,0,1,5.5,44.14a10.53,10.53,0,0,1-.77-3.89V37.14c0-3.09,2-5.43,4.59-5.43l6.4.25q-1.08-.45-2-.9c-.46-.21-.9-.43-1.29-.64a19.36,19.36,0,0,1-5.76-4.59,10.55,10.55,0,0,1-2.45-6.89,11.48,11.48,0,0,1,3.43-8.26A15.79,15.79,0,0,1,9.17,9.33a18.86,18.86,0,0,1,5-2.56A44.35,44.35,0,0,1,23,4.7c1.16-.15,2.35-.28,3.53-.36,1.4-.1,2.8-.15,4.17-.15.87,0,1.74,0,2.6.06q.72,0,1.44.09c.37,0,.74.07,1.1.11l.35,0c.82.1,1.78.22,2.74.4a26.52,26.52,0,0,1,3.66,1A19.38,19.38,0,0,1,49,9.39l.38.34a10.12,10.12,0,0,1,2.69,3.73,10.44,10.44,0,0,1,.77,3.88v3.11c0,3.1-2,5.43-4.6,5.43l-6.4-.24c.72.3,1.41.59,2,.89.46.22.9.44,1.29.65A19.49,19.49,0,0,1,51,31.76a10.54,10.54,0,0,1,2.45,6.9A11.46,11.46,0,0,1,50,46.92a14.66,14.66,0,0,1-1.54,1.34,18.64,18.64,0,0,1-5,2.56,43.5,43.5,0,0,1-8.78,2.07c-1.16.16-2.34.28-3.53.37-1.39.09-2.79.14-4.17.14Z",
                splashScreenFontSize,
                splashScreenFontSize,
                0,
                0);
        String strokeWidth = "" + splashScreenFontSize / 17;
        splashscreenvboxTophboxRegionsudosvg.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #FFBE99,"
                        + " #FF4340);-fx-border-color: #1D1D30;-fx-border-width: "
                        + strokeWidth
                        + "px;-fx-border-insets: -"
                        + strokeWidth
                        + " -"
                        + strokeWidth
                        + " -"
                        + strokeWidth
                        + " -"
                        + strokeWidth
                        + ";");
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
