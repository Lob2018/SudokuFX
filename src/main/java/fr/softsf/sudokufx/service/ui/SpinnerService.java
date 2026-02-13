/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.ui;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

import org.springframework.stereotype.Service;

/**
 * A centralized service for managing the global loading state through a thread-safe reference
 * counter. This service ensures the UI spinner remains active as long as at least one background
 * task is being processed.
 */
@Service
public class SpinnerService {

    private final AtomicInteger taskCount = new AtomicInteger(0);
    private final ReadOnlyBooleanWrapper loading = new ReadOnlyBooleanWrapper(false);

    /**
     * Returns a read-only observable property indicating whether a loading process is active.
     *
     * @return The loading property
     */
    public ReadOnlyBooleanProperty loadingProperty() {
        return loading.getReadOnlyProperty();
    }

    /**
     * Increments the active task counter and activates the loading state upon the first
     * registration. Executed on the JavaFX Application Thread.
     */
    public void startLoading() {
        if (taskCount.getAndIncrement() == 0) {
            Platform.runLater(() -> loading.set(true));
        }
    }

    /**
     * Decrements the active task counter and deactivates the loading state only when no more tasks
     * are pending. Executed on the JavaFX Application Thread.
     */
    public void stopLoading() {
        if (taskCount.decrementAndGet() <= 0) {
            taskCount.set(0);
            Platform.runLater(() -> loading.set(false));
        }
    }
}
