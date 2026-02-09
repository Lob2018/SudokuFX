/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.component;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A custom JavaFX GridPane that displays a two-phase animated spinner using Unicode icons. The
 * spinner visibility is managed by an internal counter to handle multiple concurrent asynchronous
 * calls and features a smooth fade-out transition.
 */
public final class SpinnerGridPane extends GridPane {

    private static final Duration ANIMATION_DURATION = Duration.seconds(2);
    private static final Duration ANIMATION_SECOND_PHASE_DELAY = Duration.seconds(1);
    public static final int ANIMATION_FADE_OUT_DURATION = 150;
    private final Animation spinnerAnimation1;
    private final Animation spinnerAnimation2;
    private int activeRequests = 0;

    /**
     * Constructs a SpinnerGridPane with centered animated icons and initialized pulse animations.
     */
    public SpinnerGridPane() {
        super();
        setAlignment(Pos.CENTER);
        Text spinnerText1 = new Text("\uef4a");
        spinnerText1.getStyleClass().add("spinner-text");
        Text spinnerText2 = new Text("\ue836");
        spinnerText2.getStyleClass().add("spinner-text");
        add(spinnerText1, 0, 0);
        add(spinnerText2, 0, 0);
        setMouseTransparent(true);
        spinnerAnimation1 = createPulse(spinnerText1, Duration.seconds(0));
        spinnerAnimation2 = createPulse(spinnerText2, ANIMATION_SECOND_PHASE_DELAY);
        setVisible(false);
        setManaged(false);
    }

    /**
     * Updates the request counter and toggles the spinner visibility. This method is thread-safe
     * and ensures UI updates occur on the JavaFX Application Thread.
     *
     * @param show true to increment the active request count, false to decrement it.
     */
    public void showSpinner(boolean show) {
        if (!Platform.isFxApplicationThread()) {
            Platform.runLater(() -> updateState(show));
        } else {
            updateState(show);
        }
    }

    /**
     * Manages the internal counter state and triggers start or stop transitions. * @param increment
     * true to add a request, false to remove one.
     */
    private void updateState(boolean increment) {
        if (increment) {
            activeRequests++;
        } else {
            activeRequests = Math.max(0, activeRequests - 1);
        }
        boolean shouldBeVisible = activeRequests > 0;
        if (shouldBeVisible && !isVisible()) {
            startAnimation();
        } else if (!shouldBeVisible && isVisible()) {
            stopAnimation();
        }
    }

    /** Resets opacity and starts the infinite pulse animations. */
    private void startAnimation() {
        setOpacity(1.0);
        setVisible(true);
        setManaged(true);
        spinnerAnimation1.play();
        spinnerAnimation2.play();
    }

    /**
     * Executes a fade-out transition before stopping animations and hiding the component. The final
     * cleanup only occurs if no new requests were initiated during the fade duration.
     */
    private void stopAnimation() {
        FadeTransition fadeOut =
                new FadeTransition(Duration.millis(ANIMATION_FADE_OUT_DURATION), this);
        fadeOut.setFromValue(getOpacity());
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(
                e -> {
                    if (activeRequests == 0) {
                        setVisible(false);
                        setManaged(false);
                        spinnerAnimation1.stop();
                        spinnerAnimation2.stop();
                    }
                });
        fadeOut.play();
    }

    /**
     * Creates a synchronized pulsing animation (scale and fade) for a specific text node. * @param
     * text The node to animate.
     *
     * @param delay The initial delay before starting the pulse cycle.
     * @return A ParallelTransition configured for infinite looping.
     */
    private Animation createPulse(Text text, Duration delay) {
        ScaleTransition scale = new ScaleTransition(ANIMATION_DURATION, text);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);
        FadeTransition fade = new FadeTransition(ANIMATION_DURATION, text);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setInterpolator(Interpolator.EASE_OUT);
        ParallelTransition pulse = new ParallelTransition(scale, fade);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setDelay(delay);
        return pulse;
    }
}
