/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.concurrent.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;

/**
 * Service to execute file-processing tasks asynchronously with JavaFX UI feedback.
 *
 * <p>Displays a spinner and toast messages during task execution. Handles success and error
 * reporting automatically on the JavaFX Application Thread.
 */
@Service
public class AsyncFileProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncFileProcessorService.class);
    private final ToasterService toasterService;

    public AsyncFileProcessorService(ToasterService toasterService) {
        this.toasterService = toasterService;
    }

    /**
     * Executes a file-processing task asynchronously with spinner and toast feedback.
     *
     * <p>Runs the task in a background thread. On success, the result is passed to {@code
     * onSuccess}. On failure, the error is logged and shown in a toast.
     *
     * @param <T> the result type
     * @param selectedFile the file to process; must not be null
     * @param spinner UI spinner to indicate progress; must not be null
     * @param taskFunction function to process the file and return a result; must not be null
     * @param onSuccess callback executed on the UI thread upon success; must not be null
     * @throws NullPointerException if any required parameter is null
     */
    public <T> void processFileAsync(
            File selectedFile,
            SpinnerGridPane spinner,
            Function<File, T> taskFunction,
            Consumer<T> onSuccess) {
        Objects.requireNonNull(selectedFile, "selectedFile must not be null");
        Objects.requireNonNull(spinner, "spinner must not be null");
        Objects.requireNonNull(taskFunction, "taskFunction must not be null");
        Objects.requireNonNull(onSuccess, "onSuccess must not be null");
        spinner.showSpinner(true);
        toasterService.showInfo(
                I18n.INSTANCE.getValue("toast.msg.optionsviewmodel.load"),
                selectedFile.toURI().toString());
        Task<T> task =
                new Task<>() {
                    @Override
                    protected T call() throws Exception {
                        return taskFunction.apply(selectedFile);
                    }
                };
        Consumer<Runnable> finishTask =
                action ->
                        Platform.runLater(
                                () -> {
                                    toasterService.requestRemoveToast();
                                    action.run();
                                    spinner.showSpinner(false);
                                });
        task.setOnSucceeded(e -> finishTask.accept(() -> onSuccess.accept(task.getValue())));
        task.setOnFailed(e -> handleError(task.getException(), finishTask));
        Thread thread = new Thread(task, "AsyncFileProcessorThread");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Handles task errors by logging and showing a toast notification on the JavaFX thread.
     *
     * @param ex the exception thrown by the task; must not be null
     * @param finishTask function to run code on the JavaFX Application Thread; must not be null
     */
    private void handleError(Throwable ex, Consumer<Runnable> finishTask) {
        finishTask.accept(
                () -> {
                    LOG.error("██ Exception Async task failed: {}", ex.getMessage(), ex);
                    toasterService.showError(
                            I18n.INSTANCE.getValue("toast.error.optionsviewmodel.ontaskerror"),
                            Objects.toString(ex.getMessage(), ""));
                });
    }
}
