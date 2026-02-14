/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.concurrent.Task;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;

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
    private final SpinnerService spinnerService;

    /**
     * Constructs the service with required UI notification and feedback components.
     *
     * @param toasterService the {@link ToasterService} for displaying UI messages
     * @param spinnerService the {@link SpinnerService} for managing the loading indicator
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "Spring-managed services must be stored by reference; defensive copies are"
                            + " impossible and break dependency injection.")
    public AsyncFileProcessorService(ToasterService toasterService, SpinnerService spinnerService) {
        this.toasterService = toasterService;
        this.spinnerService = spinnerService;
    }

    /**
     * Executes a file-processing task asynchronously with spinner and toast feedback.
     *
     * @param <T> the result type
     * @param selectedFile the file to process; must not be null
     * @param taskFunction function to process the file and return a result; must not be null
     * @param onSuccess callback executed on the UI thread upon success; must not be null
     */
    public <T> void processFileAsync(
            File selectedFile, Function<File, T> taskFunction, Consumer<T> onSuccess) {
        Objects.requireNonNull(selectedFile, "selectedFile must not be null");
        Objects.requireNonNull(taskFunction, "taskFunction must not be null");
        Objects.requireNonNull(onSuccess, "onSuccess must not be null");
        toasterService.showInfo(
                I18n.INSTANCE.getValue("toast.msg.optionsviewmodel.load"),
                selectedFile.toURI().toString());
        Task<T> task = fileProcessingTask(selectedFile, taskFunction, onSuccess);
        Thread thread = new Thread(task, "AsyncFileProcessorThread");
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Instantiates the file processing task with lifecycle overrides for optimal performance.
     *
     * @param <T> result type
     * @param file file to process
     * @param function processing logic
     * @param successCallback UI thread success handler
     * @return configured Task instance
     */
    private <T> @NonNull Task<T> fileProcessingTask(
            File file, Function<File, T> function, Consumer<T> successCallback) {
        return new Task<>() {
            @Override
            protected T call() {
                spinnerService.startLoading();
                return function.apply(file);
            }

            @Override
            protected void succeeded() {
                successCallback.accept(getValue());
            }

            @Override
            protected void failed() {
                handleError(getException());
            }

            @Override
            protected void cancelled() {
                LOG.info("██ fileProcessingTask's task cancelled for file: {}", file.getName());
            }

            @Override
            protected void done() {
                toasterService.requestRemoveToast();
                spinnerService.stopLoading();
            }
        };
    }

    /**
     * Handles task errors by logging and showing a toast notification.
     *
     * @param ex the exception thrown by the task
     */
    private void handleError(Throwable ex) {
        LOG.error("██ Exception Async task failed: {}", ex.getMessage(), ex);
        toasterService.showError(
                I18n.INSTANCE.getValue("toast.error.optionsviewmodel.ontaskerror"),
                Objects.toString(ex.getMessage(), ""));
    }
}
