/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.ui;

import java.io.File;
import java.text.MessageFormat;
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
     * Executes a long-running file task asynchronously with UI notifications.
     *
     * <p>The task runs on a background thread. A spinner and toast are shown during processing. On
     * success, the result is passed to {@code onSuccess} and a success toast is displayed. Errors
     * are handled internally (logged, toast shown, spinner hidden); no error callback is needed.
     *
     * @param <T> the result type
     * @param selectedFile the file to process; must not be null
     * @param spinner the UI spinner to indicate progress; must not be null
     * @param toaster the UI toaster to display messages; must not be null
     * @param taskFunction function to process the file and return a result; must not be null
     * @param onSuccess callback executed on the UI thread upon success; must not be null
     * @throws NullPointerException if any required parameter is null
     */
    public <T> void processFileAsync(
            File selectedFile,
            SpinnerGridPane spinner,
            ToasterVBox toaster,
            Function<File, T> taskFunction,
            Consumer<T> onSuccess) {
        Objects.requireNonNull(selectedFile, "selectedFile must not be null");
        Objects.requireNonNull(spinner, "spinner must not be null");
        Objects.requireNonNull(toaster, "toaster must not be null");
        Objects.requireNonNull(taskFunction, "taskFunction must not be null");
        Objects.requireNonNull(onSuccess, "onSuccess must not be null");
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
        Consumer<Runnable> finishTask =
                action ->
                        Platform.runLater(
                                () -> {
                                    toaster.removeToast();
                                    action.run();
                                    spinner.showSpinner(false);
                                });
        task.setOnSucceeded(
                e ->
                        finishTask.accept(
                                () -> {
                                    Optional<T> result = task.getValue();
                                    if (result.isPresent()) {
                                        onSuccess.accept(result.get());
                                        toaster.addToast(
                                                MessageFormat.format(
                                                        I18n.INSTANCE.getValue(
                                                                "toast.msg.optionsviewmodel.saved"),
                                                        selectedFile.getName()),
                                                selectedFile.toURI().toString(),
                                                ToastLevels.INFO,
                                                false);
                                    } else {
                                        toaster.addToast(
                                                I18n.INSTANCE.getValue(
                                                        "toast.error.optionsviewmodel.ontaskcomplete"),
                                                "",
                                                ToastLevels.ERROR,
                                                true);
                                    }
                                }));
        task.setOnFailed(
                e ->
                        finishTask.accept(
                                () -> {
                                    Throwable ex = task.getException();
                                    LOG.error("Async task failed: {}", ex.getMessage(), ex);
                                    toaster.addToast(
                                            I18n.INSTANCE.getValue(
                                                    "toast.error.optionsviewmodel.ontaskerror"),
                                            Objects.toString(ex.getMessage(), ""),
                                            ToastLevels.ERROR,
                                            true);
                                }));
        Thread thread = new Thread(task, "AsyncFileProcessorThread");
        thread.setDaemon(true);
        thread.start();
    }
}
