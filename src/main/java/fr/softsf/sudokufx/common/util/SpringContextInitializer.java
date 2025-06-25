/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.util.Objects;
import javafx.concurrent.Task;

import org.springframework.boot.SpringApplication;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Manages asynchronous initialization of the Spring application context via JavaFX Tasks, providing
 * methods to create and run these tasks in background threads.
 */
public class SpringContextInitializer {
    private final SpringContext context;

    public SpringContextInitializer(SpringContext context) {
        if (Objects.isNull(context)) {
            throw ExceptionTools.INSTANCE.createAndLogIllegalArgument(
                    "The context must not be null");
        }
        this.context = context;
    }

    /**
     * Starts the given JavaFX Task in a new background thread.
     *
     * @param task the initialization task to run; must not be null
     * @throws IllegalArgumentException if {@code task} is null
     */
    public void runInitializationTask(Task<Void> task) {
        if (Objects.isNull(task)) {
            throw ExceptionTools.INSTANCE.createAndLogIllegalArgument("The task must not be null");
        }
        new Thread(task).start();
    }

    /**
     * Creates a JavaFX Task that initializes the Spring context.
     *
     * @return a new Task that performs Spring context initialization
     */
    public Task<Void> createInitializationTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                context.init(() -> SpringApplication.run(SudoMain.class));
                return null;
            }
        };
    }
}
