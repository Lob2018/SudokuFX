package fr.softsf.sudokufx.common.unit.utils;

import com.gluonhq.ignite.spring.SpringContext;
import fr.softsf.sudokufx.utils.SpringContextInitializer;
import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.testfx.framework.junit5.ApplicationTest;

class SpringContextInitializerTest extends ApplicationTest {

    @Test
    void givenMockedSpringContext_whenRunInitializationTask_thenInitIsCalled() {
        SpringContext context = mock(SpringContext.class);
        SpringContextInitializer initializer = new SpringContextInitializer(context);
        Task<Void> task = initializer.createInitializationTask();
        initializer.runInitializationTask(task);
        verify(context, times(1)).init(any());
    }
}
