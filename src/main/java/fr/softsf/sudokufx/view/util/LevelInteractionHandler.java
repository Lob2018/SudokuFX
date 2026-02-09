/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.util;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.service.ui.ToasterService;
import fr.softsf.sudokufx.viewmodel.MenuLevelViewModel;
import fr.softsf.sudokufx.viewmodel.MenuOptionsViewModel;
import fr.softsf.sudokufx.viewmodel.grid.GridViewModel;

/**
 * Utility component managing interaction logic for level selection. Encapsulates Timeline lifecycle
 * management, input event filtering, and multi-ViewModel synchronization.
 */
@Component
public class LevelInteractionHandler {

    public static final String EVENT_MUST_NOT_BE_NULL = "event mustn't be null";
    private final GridViewModel gridViewModel;
    private final MenuLevelViewModel menuLevelViewModel;
    private final MenuOptionsViewModel menuOptionsViewModel;
    private final ToasterService toasterService;
    private final Timeline desiredPossibilitiesTimeline = new Timeline();

    private static final Set<KeyCode> LEVEL_VALID_KEYS = Set.of(KeyCode.ENTER, KeyCode.SPACE);

    /**
     * Initializes the handler with required reactive view models and notification services.
     *
     * @param menuOptionsViewModel the view model for grid display options
     * @param menuLevelViewModel the view model for level state updates
     * @param gridViewModel the view model for core grid operations
     * @param toasterService the service for UI notifications
     */
    public LevelInteractionHandler(
            MenuOptionsViewModel menuOptionsViewModel,
            MenuLevelViewModel menuLevelViewModel,
            GridViewModel gridViewModel,
            ToasterService toasterService) {
        this.menuOptionsViewModel = menuOptionsViewModel;
        this.menuLevelViewModel = menuLevelViewModel;
        this.gridViewModel = gridViewModel;
        this.toasterService = toasterService;
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
        switch (event) {
            case InputEvent e when isStartEvent(e) -> startCycle();
            case InputEvent e when isEndEvent(e) ->
                    applyLevel(getLevelWithId(btn.getId()), opaqueApplier);
            default -> desiredPossibilitiesTimeline.stop();
        }
    }

    /** Configures the cyclical Timeline for periodic desired possibilities increments. */
    private void setupTimeline() {
        desiredPossibilitiesTimeline
                .getKeyFrames()
                .setAll(
                        new KeyFrame(
                                Duration.seconds(1),
                                _ -> {
                                    gridViewModel.incrementDesiredPossibilities();
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
     * Finalizes level selection and updates reactive state across view models.
     *
     * @param level the DifficultyLevel to apply
     * @param opaqueApplier callback to refresh visual opacity based on the current options state
     */
    private void applyLevel(DifficultyLevel level, Consumer<Boolean> opaqueApplier) {
        Objects.requireNonNull(level, "level mustn't be null");
        Objects.requireNonNull(opaqueApplier, "opaqueApplier mustn't be null");
        desiredPossibilitiesTimeline.stop();
        menuLevelViewModel.updateSelectedLevel(level, gridViewModel.setCurrentGridWithLevel(level));
        opaqueApplier.accept(menuOptionsViewModel.gridOpacityProperty().get());
    }

    /** Formats and pushes the current possibilities state to the notification service. */
    private void notifyUser() {
        int val = gridViewModel.getDesiredPossibilities();
        String label =
                (val == -1)
                        ? "Default"
                        : val + " to " + (val + GridViewModel.DESIRED_POSSIBILITIES_STEP);
        toasterService.showInfo("Possibilities: " + label, "Possibilities: " + label);
    }

    /**
     * Evaluates if the event corresponds to a cycle initiation trigger.
     *
     * @param event the event to evaluate
     * @return true if it is a valid start trigger
     */
    private boolean isStartEvent(InputEvent event) {
        Objects.requireNonNull(event, EVENT_MUST_NOT_BE_NULL);
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
        Objects.requireNonNull(event, EVENT_MUST_NOT_BE_NULL);
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
