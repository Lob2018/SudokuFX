/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.concurrent.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;

@Service
public class AsyncFileProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncFileProcessorService.class);

    /**
     * Executes a long-running file processing task asynchronously with UI notifications.
     *
     * <p>The method runs the provided {@code taskFunction} on a background thread. It shows a
     * spinner and a toast message while processing, and updates the UI upon success or failure.
     *
     * @param <T> the result type of the task
     * @param selectedFile the file to process; must not be null
     * @param spinner UI spinner to show progress; must not be null
     * @param toaster UI toaster to display messages; must not be null
     * @param taskFunction function to process the file and return a result; must not be null
     * @param onSuccess callback to execute on success with the result; must not be null
     * @param onError callback to execute on error with the exception; must not be null
     * @throws NullPointerException if any required parameter is null
     */
    public <T> void processFileAsync(
            File selectedFile,
            SpinnerGridPane spinner,
            ToasterVBox toaster,
            Function<File, T> taskFunction,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError) {

        Objects.requireNonNull(selectedFile, "selectedFile must not be null");
        Objects.requireNonNull(spinner, "spinner must not be null");
        Objects.requireNonNull(toaster, "toaster must not be null");
        Objects.requireNonNull(taskFunction, "taskFunction must not be null");
        Objects.requireNonNull(onSuccess, "onSuccess must not be null");
        Objects.requireNonNull(onError, "onError must not be null");

        spinner.showSpinner(true);
        toaster.addToast(
                I18n.INSTANCE.getValue("toast.msg.optionsviewmodel.load"),
                selectedFile.toURI().toString(),
                ToastLevels.INFO,
                false);

        Task<Optional<T>> task =
                new Task<>() {
                    @Override
                    protected Optional<T> call() {
                        try {
                            return Optional.ofNullable(taskFunction.apply(selectedFile));
                        } catch (Exception e) {
                            LOG.error(
                                    "Error processing file {}: {}",
                                    selectedFile,
                                    e.getMessage(),
                                    e);
                            return Optional.empty();
                        }
                    }
                };

        task.setOnSucceeded(
                e ->
                        Platform.runLater(
                                () -> {
                                    toaster.removeToast();
                                    Optional<T> result = task.getValue();
                                    if (result.isPresent()) {
                                        onSuccess.accept(result.get());
                                    } else {
                                        toaster.addToast(
                                                I18n.INSTANCE.getValue(
                                                        "toast.error.optionsviewmodel.ontaskcomplete"),
                                                "",
                                                ToastLevels.ERROR,
                                                true);
                                    }
                                    spinner.showSpinner(false);
                                }));

        task.setOnFailed(
                e ->
                        Platform.runLater(
                                () -> {
                                    toaster.removeToast();
                                    Throwable ex = e.getSource().getException();
                                    LOG.error("Async task failed: {}", ex.getMessage(), ex);
                                    onError.accept(ex);
                                    toaster.addToast(
                                            I18n.INSTANCE.getValue(
                                                    "toast.error.optionsviewmodel.ontaskerror"),
                                            Objects.toString(ex.getMessage(), ""),
                                            ToastLevels.ERROR,
                                            true);
                                    spinner.showSpinner(false);
                                }));

        Thread thread = new Thread(task, "AsyncFileProcessorThread");
        thread.setDaemon(true);
        thread.start();
    }
}
