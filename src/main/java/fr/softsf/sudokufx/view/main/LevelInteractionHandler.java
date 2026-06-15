/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.main;

import java.util.Objects;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

/**
 * Utility component managing interaction logic for level selection. Encapsulates Timeline lifecycle
 * management, interaction duration measurement, and multi-ViewModel synchronization.
 */
@Component
public class LevelInteractionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LevelInteractionHandler.class);
    public static final int DURATION_BETWEEN_INCREMENTS_IN_MS = 500;

    private final GridViewModel gridViewModel;
    private final MenuLevelViewModel menuLevelViewModel;
    private final MenuOptionsViewModel menuOptionsViewModel;
    private final Timeline desiredPossibilitiesTimeline = new Timeline();

    private DifficultyLevel level;
    private long startTime;
    private Task<Integer> currentGridTask;

    /**
     * Initializes the handler with required reactive view models and notification services.
     *
     * @param menuOptionsViewModel the view model for grid display options
     * @param menuLevelViewModel the view model for level state updates
     * @param gridViewModel the view model for core grid operations
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Spring ViewModels are singletons and must be stored by reference.")
    public LevelInteractionHandler(
            MenuOptionsViewModel menuOptionsViewModel,
            MenuLevelViewModel menuLevelViewModel,
            GridViewModel gridViewModel) {
        this.menuOptionsViewModel = menuOptionsViewModel;
        this.menuLevelViewModel = menuLevelViewModel;
        this.gridViewModel = gridViewModel;
        setupTimeline();
    }

    /**
     * Records the start of an interaction and initiates the selection cycle.
     *
     * @param buttonId the identifier of the button triggered
     */
    public void handleStart(String buttonId) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                buttonId, "ButtonId must not be null or blank, but was " + buttonId);
        this.level = getLevelWithId(buttonId);
        this.startTime = System.currentTimeMillis();
        startCycle();
    }

    /**
     * Finalizes the interaction, calculates the duration of the press, and applies the level
     * selection.
     *
     * @param buttonId the identifier of the button triggered
     * @param opaqueApplier callback to synchronize grid visual opacity
     */
    public void handleEnd(String buttonId, Consumer<Boolean> opaqueApplier) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                buttonId, "ButtonId must not be null or blank, but was " + buttonId);
        if (Objects.isNull(opaqueApplier)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The opaqueApplier must not be null");
        }
        long duration = System.currentTimeMillis() - startTime;
        LOG.debug("Interaction duration for {}: {}ms", buttonId, duration);
        applyLevel(opaqueApplier);
    }

    /** Explicitly interrupts the interaction cycle. */
    public void stopCycle() {
        desiredPossibilitiesTimeline.stop();
        LOG.debug("Interaction interrupted, cycle stopped.");
    }

    /** Configures the cyclical Timeline for periodic desired possibilities increments. */
    private void setupTimeline() {
        desiredPossibilitiesTimeline
                .getKeyFrames()
                .setAll(
                        new KeyFrame(
                                Duration.millis(DURATION_BETWEEN_INCREMENTS_IN_MS),
                                _ -> {
                                    gridViewModel.incrementDesiredPossibilities(level);
                                    notifyUser();
                                }));
        desiredPossibilitiesTimeline.setCycleCount(Animation.INDEFINITE);
    }

    /** Initiates the possibilities increment cycle after resetting the current state. */
    private void startCycle() {
        if (desiredPossibilitiesTimeline.getStatus() != Animation.Status.RUNNING) {
            gridViewModel.resetDesiredPossibilities();
            desiredPossibilitiesTimeline.playFromStart();
        }
    }

    /**
     * Finalizes level selection and synchronizes reactive state across ViewModels asynchronously.
     *
     * @param opaqueApplier callback to synchronize grid visual opacity with current option states
     */
    private void applyLevel(Consumer<Boolean> opaqueApplier) {
        if (Objects.isNull(opaqueApplier)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The opaqueApplier must not be null");
        }
        desiredPossibilitiesTimeline.stop();
        if (currentGridTask != null && currentGridTask.isRunning()) {
            currentGridTask.cancel();
        }
        currentGridTask = gridViewModel.setCurrentGridTask(level);
        final Task<Integer> task = currentGridTask;
        task.setOnSucceeded(
                _ -> {
                    int possibilitiesPercentage = task.getValue();
                    if (possibilitiesPercentage != -1) {
                        menuLevelViewModel.updateSelectedLevel(level, possibilitiesPercentage);
                        opaqueApplier.accept(menuOptionsViewModel.gridOpacityProperty().get());
                    }
                });
        task.setOnFailed(
                _ -> LOG.error("Level application failed: {}", task.getException().getMessage()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /** Triggers a notification displaying the current difficulty percentage boundaries. */
    private void notifyUser() {
        gridViewModel.notifyLevelPossibilityBounds(level);
    }

    /**
     * Resolves a button identifier to its corresponding difficulty level.
     *
     * @param id the source button identifier
     * @return the resolved DifficultyLevel
     * @throws IllegalArgumentException if the identifier is unknown
     */
    private DifficultyLevel getLevelWithId(String id) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                id, "Id must not be null or blank, but was " + id);
        return switch (id) {
            case "menuMiniButtonEasy", "menuMaxiButtonEasy" -> DifficultyLevel.EASY;
            case "menuMiniButtonMedium", "menuMaxiButtonMedium" -> DifficultyLevel.MEDIUM;
            case "menuMiniButtonDifficult", "menuMaxiButtonDifficult" -> DifficultyLevel.DIFFICULT;
            default ->
                    throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                            "Unknown level button ID: " + id);
        };
    }
}
