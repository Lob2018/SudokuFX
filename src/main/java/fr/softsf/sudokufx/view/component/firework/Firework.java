/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.component.firework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Reactive container for firework animation logic. Lifecycle is strictly controlled by the firing
 * property.
 */
public class Firework extends Group {

    private double spawnRatio = 0;

    public static final double MINIMUM_PARTICLE_RADIUS = 0.2;
    private static final double REFERENCE_SCREEN_WIDTH = 1000;

    private static final int DEFAULT_COUNTDOWN = 4;
    public static final int MAXIMUM_HUE = 360;
    public static final int MINIMUM_PARTICLES = 8;
    public static final int PARTICLE_COUNT_VARIANCE = 10;
    public static final int PARTICLE_RADIUS_VARIANCE = 5;
    public static final int SPEED_VARIANCE = 4;

    private final List<FireworkParticle> particles = new ArrayList<>();
    private final AnimationTimer timer;
    private int countdown = DEFAULT_COUNTDOWN * 2;

    private final PauseTransition delay = new PauseTransition(Duration.seconds(1));
    private final FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), this);

    /**
     * Snapshot of the rendering environment including scaling metrics.
     *
     * @param firework the firework container instance
     * @param width current width of the rendering area
     * @param height current height of the rendering area
     * @param ratio scaling factor for resolution independence
     */
    private record FireworkContext(Firework firework, int width, int height, double ratio) {}

    /**
     * Captures the current scene metrics and the firework instance.
     *
     * @return a FireworkContext containing the current instance and scaling metrics.
     */
    private FireworkContext getFireworkContext() {
        Scene s = getScene();
        if (s == null) {
            return new FireworkContext(this, 0, 0, spawnRatio);
        }
        double w = s.getWidth();
        double h = s.getHeight();
        if (w > 0) {
            spawnRatio = Math.max(w, h) / REFERENCE_SCREEN_WIDTH;
        }
        return new FireworkContext(this, (int) w, (int) h, spawnRatio);
    }

    /**
     * Managed property handling the animation lifecycle. Invalidation triggers internal start/stop
     * methods, ensuring atomic state transitions.
     */
    private final BooleanProperty firingProperty =
            new BooleanPropertyBase(false) {
                @Override
                protected void invalidated() {
                    delay.stop();
                    fadeIn.stop();
                    if (get()) {
                        clearParticles();
                        countdown = DEFAULT_COUNTDOWN * 2;
                        setVisible(false);
                        setOpacity(0.0);
                        delay.setOnFinished(
                                e -> {
                                    if (get()) {
                                        setVisible(true);
                                        fadeIn.setFromValue(0.0);
                                        fadeIn.setToValue(1.0);
                                        fadeIn.play();
                                    }
                                });
                        delay.play();
                        timer.start();
                    } else {
                        timer.stop();
                        setVisible(false);
                        setOpacity(0.0);
                        clearParticles();
                    }
                }

                @Override
                public Object getBean() {
                    return Firework.this;
                }

                @Override
                public String getName() {
                    return "firing";
                }

                private void clearParticles() {
                    particles.clear();
                    getChildren().clear();
                }
            };

    /** Initializes the component and its reusable animation loop. */
    @SuppressWarnings("java:S2245")
    public Firework() {
        setManaged(false);
        this.timer =
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        updateParticles();
                        if (--countdown <= 0) {
                            ignite();
                            countdown =
                                    DEFAULT_COUNTDOWN
                                            + ThreadLocalRandom.current()
                                                    .nextInt(DEFAULT_COUNTDOWN * 2);
                        }
                    }
                };
    }

    /**
     * Returns the property for ViewModel binding.
     *
     * @return The firing status property.
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "JavaFX property intentionally exposed for binding.")
    public BooleanProperty firingProperty() {
        return firingProperty;
    }

    /** Generates a circular particle burst at a random screen location. */
    @SuppressWarnings("java:S2245")
    private void ignite() {
        var random = ThreadLocalRandom.current();
        FireworkContext context = getFireworkContext();
        if (context.width() <= 0) {
            return;
        }
        double ratio = context.ratio();
        double originX = random.nextDouble() * context.width();
        double originY = random.nextDouble() * context.height();
        Color particleColor = Color.hsb(random.nextDouble() * MAXIMUM_HUE, 1.0, 1.0);
        int particleCount = MINIMUM_PARTICLES + random.nextInt(PARTICLE_COUNT_VARIANCE);
        List<Node> newNodes = new ArrayList<>(particleCount);
        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount;
            double speed = (2 + random.nextDouble() * SPEED_VARIANCE) * ratio;
            FireworkParticle spark =
                    new FireworkParticle(
                            ratio,
                            originX,
                            originY,
                            particleColor,
                            (MINIMUM_PARTICLE_RADIUS
                                            + random.nextDouble() * PARTICLE_RADIUS_VARIANCE)
                                    * ratio);
            spark.velX = Math.cos(angle) * speed;
            spark.velY = Math.sin(angle) * speed;
            particles.add(spark);
            newNodes.add(spark.circle);
        }
        getChildren().addAll(newNodes);
    }

    /** Updates positions for all active particles and prunes expired ones. */
    private void updateParticles() {
        FireworkContext context = getFireworkContext();
        int currentHeight = context.height();
        List<Node> expiredNodes = new ArrayList<>();
        Iterator<FireworkParticle> iter = particles.iterator();
        while (iter.hasNext()) {
            FireworkParticle particle = iter.next();
            if (particle.update(currentHeight)) {
                iter.remove();
                expiredNodes.add(particle.circle);
            }
        }
        if (!expiredNodes.isEmpty()) {
            getChildren().removeAll(expiredNodes);
        }
    }
}
