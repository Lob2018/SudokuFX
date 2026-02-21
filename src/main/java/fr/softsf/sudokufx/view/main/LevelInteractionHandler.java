/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.main;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

/**
 * Utility component managing interaction logic for level selection. Encapsulates Timeline lifecycle
 * management, input event filtering, and multi-ViewModel synchronization.
 */
@Component
public class LevelInteractionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LevelInteractionHandler.class);

    public static final String EVENT_MUST_NOT_BE_NULL = "event mustn't be null";
    public static final int DURATION_BETWEEN_INCREMENTS_IN_MS = 500;
    private final GridViewModel gridViewModel;
    private final MenuLevelViewModel menuLevelViewModel;
    private final MenuOptionsViewModel menuOptionsViewModel;
    private final Timeline desiredPossibilitiesTimeline = new Timeline();
    private static final Set<KeyCode> LEVEL_VALID_KEYS = Set.of(KeyCode.ENTER, KeyCode.SPACE);
    private DifficultyLevel level;
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
     * Processes input events to drive the iterative selection cycle or finalize level application.
     * Uses pattern matching to dispatch actions based on the event state.
     *
     * @param event the interaction event to be dispatched
     * @param opaqueApplier a functional callback to apply visual styling to the grid
     */
    public void handleAction(InputEvent event, Consumer<Boolean> opaqueApplier) {
        Objects.requireNonNull(event, EVENT_MUST_NOT_BE_NULL);
        Objects.requireNonNull(opaqueApplier, "opaqueApplier mustn't be null");
        if (!(event.getSource() instanceof Button btn)) {
            return;
        }
        level = getLevelWithId(btn.getId());
        switch (event) {
            case InputEvent e when isStartEvent(e) -> startCycle();
            case InputEvent e when isEndEvent(e) -> applyLevel(opaqueApplier);
            default -> desiredPossibilitiesTimeline.stop();
        }
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
     * <p>Uses a {@link Task} to offload grid generation from the JavaFX Application Thread. Any
     * pending generation task is canceled before starting a new one to prevent race conditions.
     * State updates are skipped if the engine returns -1 or if the task is interrupted.
     *
     * @param opaqueApplier callback to synchronize grid visual opacity with current option states
     */
    private void applyLevel(Consumer<Boolean> opaqueApplier) {
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
                _ ->
                        LOG.error(
                                "██ Level application failed: {}",
                                task.getException().getMessage(),
                                task.getException()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Triggers a notification displaying the current difficulty percentage boundaries. *
     *
     * <p>Delegates to the {@link GridViewModel} to format and push the active percentage range to
     * the toaster service.
     */
    private void notifyUser() {
        gridViewModel.notifyLevelPossibilityBounds(level);
    }

    /**
     * Evaluates if the event corresponds to a cycle initiation trigger.
     *
     * @param event the event to evaluate
     * @return true if it is a valid start trigger
     */
    private boolean isStartEvent(InputEvent event) {
        return (event instanceof MouseEvent me && me.getEventType() == MouseEvent.MOUSE_PRESSED)
                || (event instanceof KeyEvent ke
                        && ke.getEventType() == KeyEvent.KEY_PRESSED
                        && LEVEL_VALID_KEYS.contains(ke.getCode()));
    }

    /**
     * Evaluates if the event corresponds to a selection finalization trigger.
     *
     * @param event the event to evaluate
     * @return true if it is a valid end trigger
     */
    private boolean isEndEvent(InputEvent event) {
        return (event instanceof MouseEvent me && me.getEventType() == MouseEvent.MOUSE_RELEASED)
                || (event instanceof KeyEvent ke
                        && ke.getEventType() == KeyEvent.KEY_RELEASED
                        && LEVEL_VALID_KEYS.contains(ke.getCode()));
    }

    /**
     * Resolves a button identifier to its corresponding difficulty level.
     *
     * @param id the source button identifier
     * @return the resolved DifficultyLevel
     * @throws IllegalArgumentException if the identifier is unknown
     */
    private DifficultyLevel getLevelWithId(String id) {
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
