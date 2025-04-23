package fr.softsf.sudokufx.view.components;

import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public final class SpinnerGridPane extends GridPane {

    private final Animation spinnerAnimation1, spinnerAnimation2;

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
        spinnerAnimation2 = createPulse(spinnerText2, Duration.seconds(.5));
        setVisible(false);
        setManaged(false);
    }

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
     * @param text  the Text node to apply the animation to.
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
