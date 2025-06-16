/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.util;

import java.util.concurrent.CountDownLatch;
import javafx.concurrent.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.common.util.SpringContextInitializer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class SpringContextInitializerUTest {

    @Test
    void givenMockedSpringContext_whenRunInitializationTask_thenInitIsCalled()
            throws InterruptedException {
        SpringContext context = mock(SpringContext.class);
        SpringContextInitializer initializer = new SpringContextInitializer(context);
        Task<Void> task = initializer.createInitializationTask();
        CountDownLatch latch = new CountDownLatch(1);
        task.setOnSucceeded(e -> latch.countDown());
        task.setOnFailed(e -> latch.countDown());
        initializer.runInitializationTask(task);
        latch.await();
        verify(context, times(1)).init(any());
    }
}
