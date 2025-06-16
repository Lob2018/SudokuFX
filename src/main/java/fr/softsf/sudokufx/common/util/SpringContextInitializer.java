/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.util;

import javafx.concurrent.Task;

import org.springframework.boot.SpringApplication;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.SudoMain;

/**
 * Handles asynchronous initialization of the Spring application context using a JavaFX Task.
 * Provides methods to create and run the initialization task in a background thread.
 */
public class SpringContextInitializer {
    private final SpringContext context;

    public SpringContextInitializer(SpringContext context) {
        this.context = context;
    }

    /**
     * Starts the given JavaFX Task in a new background thread.
     *
     * @param task the initialization task to run
     */
    public void runInitializationTask(Task<Void> task) {
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
