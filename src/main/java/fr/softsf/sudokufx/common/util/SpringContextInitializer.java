/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import java.util.Objects;
import javafx.concurrent.Task;

import org.springframework.boot.SpringApplication;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.common.exception.ExceptionTools;

/**
 * Manages asynchronous initialization of the Spring application context via JavaFX Tasks, providing
 * methods to create and run these tasks in background threads.
 */
public class SpringContextInitializer {
    private final SpringContext context;

    /**
     * Constructs a {@code SpringContextInitializer} with the given {@link SpringContext}.
     *
     * @param context the Spring context to initialize; must not be null
     * @throws IllegalArgumentException if {@code context} is null
     */
    public SpringContextInitializer(SpringContext context) {
        if (Objects.isNull(context)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
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
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The task must not be null");
        }
        new Thread(task).start();
    }

    /**
     * Returns a JavaFX Task that initializes the Spring application context using the specified
     * Spring Boot application class.
     *
     * @param appClass the Spring Boot application class to run
     * @return a Task that performs the Spring context initialization
     */
    public Task<Void> createInitializationTask(Class<?> appClass) {
        return new Task<>() {
            @Override
            protected Void call() {
                context.init(() -> SpringApplication.run(appClass));
                return null;
            }
        };
    }
}
