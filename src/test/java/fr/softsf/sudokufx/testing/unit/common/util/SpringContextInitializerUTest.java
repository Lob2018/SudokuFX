/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.common.util;

import java.util.concurrent.CountDownLatch;
import javafx.concurrent.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.common.util.SpringContextInitializer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class SpringContextInitializerUTest {

    @Test
    void givenNullContext_whenConstructing_thenThrowsIllegalArgumentException() {
        IllegalArgumentException thrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            new SpringContextInitializer(null);
                        });
        assertEquals("The context must not be null", thrown.getMessage());
    }

    @Test
    void givenNullTask_whenRunInitializationTask_thenThrowsIllegalArgumentException() {
        SpringContext context = mock(SpringContext.class);
        SpringContextInitializer initializer = new SpringContextInitializer(context);
        IllegalArgumentException thrown =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> {
                            initializer.runInitializationTask(null);
                        });
        assertEquals("The task must not be null", thrown.getMessage());
    }

    @Test
    void givenMockedSpringContext_whenRunInitializationTask_thenInitIsCalled()
            throws InterruptedException {
        SpringContext context = mock(SpringContext.class);
        SpringContextInitializer initializer = new SpringContextInitializer(context);
        Task<Void> task = initializer.createInitializationTask(SudoMain.class);
        CountDownLatch latch = new CountDownLatch(1);
        task.setOnSucceeded(e -> latch.countDown());
        task.setOnFailed(e -> latch.countDown());
        initializer.runInitializationTask(task);
        latch.await();
        verify(context, times(1)).init(any());
    }
}
