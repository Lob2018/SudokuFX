/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.component.firework;

import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** Individual particle entity with physics and fade-out logic. */
public class FireworkParticle {

    private static final double GRAVITY_FORCE = 0.1;
    private static final double AIR_FRICTION = 0.98;
    private static final double MIN_BLUR_RADIUS = 1.5;
    private static final double BLUR_VARIANCE = 3.0;

    public static final int MIN_DISPLAY_DURATION = 25;
    public static final int DISPLAY_DURATION_VARIANCE = 33;

    final Circle circle;
    private final double ratio;
    private final int maxDurationTicks;
    private double x;
    private double y;
    double velX;
    double velY;
    private int remainingDurationTicks;

    /**
     * Represents a visual particle with a limited display time.
     *
     * @param ratio Scaling factor used for resolution independence.
     * @param startX Initial X coordinate.
     * @param startY Initial Y coordinate.
     * @param color Particle color.
     * @param radius Particle radius.
     */
    @SuppressWarnings("java:S2245")
    public FireworkParticle(
            double ratio, double startX, double startY, Color color, double radius) {
        this.ratio = ratio;
        this.x = startX;
        this.y = startY;
        var random = ThreadLocalRandom.current();
        this.maxDurationTicks = MIN_DISPLAY_DURATION + random.nextInt(DISPLAY_DURATION_VARIANCE);
        this.remainingDurationTicks = maxDurationTicks;
        circle = new Circle(radius, color);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setEffect(
                new javafx.scene.effect.GaussianBlur(
                        (MIN_BLUR_RADIUS + random.nextDouble() * BLUR_VARIANCE) * ratio));
    }

    /**
     * Updates physics, position, and opacity.
     *
     * @param height Current screen height for boundary pruning.
     * @return true if the particle is expired.
     */
    public boolean update(int height) {
        velY += GRAVITY_FORCE * ratio;
        x += velX;
        y += velY;
        velX *= AIR_FRICTION;
        circle.setCenterX(x);
        circle.setCenterY(y);
        remainingDurationTicks--;
        double opacity = (double) remainingDurationTicks / maxDurationTicks;
        circle.setOpacity(Math.max(0, opacity));
        return remainingDurationTicks <= 0 || y < 0 || y > height;
    }
}
