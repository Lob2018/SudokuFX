/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.component;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A custom JavaFX GridPane that displays a two-phase animated spinner using Unicode icons. The
 * spinner consists of two overlapping Text nodes with pulsing animations, creating a visual loading
 * indicator. By default, the spinner is hidden and not managed by the layout. It can be shown or
 * hidden using the {@link #showSpinner(boolean)} method.
 */
public final class SpinnerGridPane extends GridPane {

    private static final Duration DEFAULT_SECOND_PHASE_DELAY = Duration.seconds(.5);
    private final Animation spinnerAnimation1, spinnerAnimation2;

    /**
     * Constructs a SpinnerGridPane with two animated text icons centered in the pane. Initializes
     * pulsing animations with staggered delays for a dynamic effect.
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
        spinnerAnimation2 = createPulse(spinnerText2, DEFAULT_SECOND_PHASE_DELAY);
        setVisible(false);
        setManaged(false);
    }

    /**
     * Shows or hides the spinner. When visible, animations are started; otherwise, they are
     * stopped.
     *
     * @param b true to show and play the spinner, false to hide and stop it.
     */
    public void showSpinner(boolean b) {
        setVisible(b);
        setManaged(b);
        if (b) {
            spinnerAnimation1.play();
            spinnerAnimation2.play();
        } else {
            spinnerAnimation1.stop();
            spinnerAnimation2.stop();
        }
    }

    /**
     * Creates a pulsing animation for a given text element with scaling and fading effects.
     *
     * @param text the Text node to apply the animation to.
     * @param delay the delay before the animation starts.
     * @return a ParallelTransition combining scale and fade animations.
     */
    private Animation createPulse(Text text, Duration delay) {
        ScaleTransition scale = new ScaleTransition(Duration.seconds(2), text);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setFromX(0);
        scale.setFromY(0);
        scale.setToX(1);
        scale.setToY(1);
        scale.setCycleCount(Animation.INDEFINITE);
        scale.setDelay(delay);
        FadeTransition fade = new FadeTransition(Duration.seconds(2), text);
        scale.setInterpolator(Interpolator.EASE_OUT);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setDelay(delay);
        ParallelTransition pulse = new ParallelTransition(scale, fade);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setDelay(delay);
        return pulse;
    }
}
