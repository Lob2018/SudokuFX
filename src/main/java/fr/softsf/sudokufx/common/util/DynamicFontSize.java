package fr.softsf.sudokufx.common.util;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import javafx.beans.InvalidationListener;
import javafx.scene.Scene;

import java.util.Objects;

/**
 * Dynamically adjusts the root font size of a JavaFX Scene based on its dimensions.
 * <p>
 * Uses a debounced dynamic stylesheet to minimize memory allocations and reduce GC pressure.
 * Updates occur only when the font size changes significantly, leveraging JavaFX's stylesheet caching.
 */
public final class DynamicFontSize {

    private static final double FONT_SIZE_RATIO = 0.0219;
    private static final String ROOT_FONT_CSS_PREFIX = "data:text/css,.root { -fx-font-size: ";
    private static final double EPSILON = 0.01;

    private final Scene scene;
    private double currentFontSize = -1;
    private int dynamicStylesheetIndex = -1;

    /**
     * Returns the current calculated font size in pixels.
     *
     * @return current font size
     */
    public double getCurrentFontSize() {
        return currentFontSize;
    }

    /**
     * Constructs a DynamicFontSize instance for the specified Scene.
     * <p>
     * Attaches listeners to the scene's width and height properties to automatically update
     * the font size when the scene is resized.
     *
     * @param scene the JavaFX Scene to observe; must not be null
     * @throws IllegalArgumentException if the scene is null, with logging via ExceptionTools
     */
    public DynamicFontSize(Scene scene) {
        this.scene =
                Objects.requireNonNull(
                        scene,
                        () -> {
                            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                                    "The scene mustn't be null");
                        });
        initialize();
    }

    /**
     * Sets up dynamic font resizing by adding listeners to the scene's width and height.
     * <p>
     * An initial font size update is performed immediately after attaching the listeners.
     */
    private void initialize() {
        InvalidationListener listener = obs -> updateFontSize();
        scene.widthProperty().addListener(listener);
        scene.heightProperty().addListener(listener);
        updateFontSize();
    }

    /**
     * Updates the font size of the scene's root node using a dynamic stylesheet.
     * <p>
     * The new font size is applied only if it differs by at least {@link #EPSILON} from the
     * current size. This reduces unnecessary stylesheet updates and memory allocations.
     * <p>
     * A dynamic stylesheet is created or updated, leveraging JavaFX's caching mechanism for
     * efficiency. The font size is calculated proportionally to the smaller dimension of the
     * scene using {@link #FONT_SIZE_RATIO}.
     */
    public void updateFontSize() {
        double fontSize = Math.min(scene.getWidth(), scene.getHeight()) * FONT_SIZE_RATIO;
        if (Math.abs(fontSize - currentFontSize) < EPSILON) {
            return;
        }
        currentFontSize = fontSize;
        var stylesheets = scene.getStylesheets();
        String css = ROOT_FONT_CSS_PREFIX + fontSize + "px; }";
        if (dynamicStylesheetIndex >= 0 && dynamicStylesheetIndex < stylesheets.size()) {
            stylesheets.set(dynamicStylesheetIndex, css);
        } else {
            stylesheets.add(css);
            dynamicStylesheetIndex = stylesheets.size() - 1;
        }
    }
}
