package fr.softsf.sudokufx.configuration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.configuration.os.OsFolderFactoryManager;
import fr.softsf.sudokufx.interfaces.IOsFolderFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

import static fr.softsf.sudokufx.utils.MyEnums.LogBackTxt.ASCII_LOGO;
import static fr.softsf.sudokufx.utils.MyEnums.LogBackTxt.OPTIMIZING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MyLogbackConfigUTest {

    private final IOsFolderFactory iCurrentIOsFolderFactory;
    private final MyLogbackConfig myLogbackConfig;
    private ListAppender<ILoggingEvent> logWatcher;

    public MyLogbackConfigUTest() {
        iCurrentIOsFolderFactory = new OsFolderFactoryManager().iOsFolderFactory();
        this.myLogbackConfig = new MyLogbackConfig(iCurrentIOsFolderFactory);
    }

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MyLogbackConfig.class)).addAppender(logWatcher);
    }

    @AfterEach
    void tearDown() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(MyLogbackConfig.class)).detachAndStopAllAppenders();
    }

    @Test
    void testLogs_real_folder_path_is_correct() {
        assertEquals(myLogbackConfig.getLogsFolderPath(), iCurrentIOsFolderFactory.getOsLogsFolderPath());
    }

    @Test
    void testLogs_real_folder_exists() {
        Path dossier = Path.of(myLogbackConfig.getLogsFolderPath());
        assertTrue(Files.exists(dossier));
    }

    @Test
    void testLog_as_fatal(@Mock Logger logger) {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        logger.error("This is a critical message");
        verify(logger, times(1)).error(stringCaptor.capture());
        assertEquals("This is a critical message", stringCaptor.getValue());
    }

    @Test
    void testConfigureLogbackWithInvalidPath() {
        myLogbackConfig.setLogBackPathForTests();
        assertThrows(RuntimeException.class, myLogbackConfig::configureLogback);
    }

    @Test
    void testLogEntryMessageWithSpringContextIsOnRefresh() {
        JVMApplicationProperties.setOnRefreshSpringContextExitForTests();
        myLogbackConfig.printLogEntryMessage();
        assertTrue(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assert (logWatcher.list.getLast().getFormattedMessage()).contains(OPTIMIZING.getLogBackMessage());
    }

    @Test
    void testLogEntryMessageWithInitSpringContextExitForTests() {
        JVMApplicationProperties.setInitSpringContextExitForTests();
        myLogbackConfig.printLogEntryMessage();
        assertFalse(JVMApplicationProperties.isSpringContextExitOnRefresh());
        assert (logWatcher.list.getLast().getFormattedMessage()).contains(ASCII_LOGO.getLogBackMessage());
    }


}


