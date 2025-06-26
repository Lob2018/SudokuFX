/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.navigation;

import java.util.concurrent.TimeoutException;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.util.DynamicFontSize;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class CoordinatorUTest {

    @Mock private FXMLLoader fxmlLoader;
    @Mock private DynamicFontSize dynamicFontSize;
    @Mock private HostServices hostServices;

    private Coordinator coordinator;
    private Scene scene;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(Coordinator.class)).addAppender(logWatcher);
        MockitoAnnotations.openMocks(this);
        scene = new Scene(new VBox(), 200, 200);
        coordinator = spy(new Coordinator(fxmlLoader));
        coordinator.setDefaultScene(scene);
        doNothing().when(coordinator).exitPlatform();
    }

    @AfterEach
    void cleanup() throws TimeoutException {
        ((Logger) LoggerFactory.getLogger(Coordinator.class)).detachAndStopAllAppenders();
        FxToolkit.cleanupStages();
    }

    @Test
    void givenNullFXMLLoader_whenConstructCoordinator_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Coordinator(null));
        assertTrue(ex.getMessage().contains("FxmlLoader must not be null"));
    }

    @Test
    void givenNullScene_whenSetDefaultScene_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> coordinator.setDefaultScene(null));
        assertTrue(ex.getMessage().contains("Scene must not be null"));
    }

    @Test
    void givenNullDynamicFontSize_whenSetDynamicFontSize_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> coordinator.setDynamicFontSize(null));
        assertTrue(ex.getMessage().contains("DynamicFontSize must not be null"));
    }

    @Test
    void givenNullHostServices_whenSetHostServices_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> coordinator.setHostServices(null));
        assertTrue(ex.getMessage().contains("HostServices must not be null"));
    }

    @Test
    void givenValidFXMLName_whenSetRootByFXMLName_thenSetsRootAndReturnsController()
            throws Exception {
        Parent fakeRoot = new VBox();
        Object fakeController = new Object();
        when(fxmlLoader.load()).thenReturn(fakeRoot);
        when(fxmlLoader.getController()).thenReturn(fakeController);
        when(fxmlLoader.getLocation()).thenReturn(getClass().getResource("/fxml/dummy.fxml"));
        coordinator.setDynamicFontSize(dynamicFontSize);
        Object controller = coordinator.setRootByFXMLName("dummy");
        assertSame(fakeController, controller);
        assertSame(fakeRoot, scene.getRoot());
        verify(dynamicFontSize).updateFontSize();
        verify(fxmlLoader).setRoot(null);
        verify(fxmlLoader).setController(null);
        verify(fxmlLoader).setLocation(any());
    }

    @Test
    void givenBlankFXMLName_whenSetRootByFXMLName_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> coordinator.setRootByFXMLName(" "));
        assertTrue(ex.getMessage().contains("Fxml must not be null or blank"));
    }

    @Test
    void givenLanguageFR_whenToggleLanguage_thenLanguageIsSwitchedToEn() {
        I18n.INSTANCE.setLocaleBundle("FR");
        coordinator.toggleLanguage();
        assertEquals("en", I18n.INSTANCE.getLanguage());
    }

    @Test
    void givenLanguageEn_whenToggleLanguage_thenLanguageIsSwitchedToFr() {
        I18n.INSTANCE.setLocaleBundle("EN");
        coordinator.toggleLanguage();
        assertEquals("fr", I18n.INSTANCE.getLanguage());
    }

    @Test
    void givenHostServicesSet_whenOpenGitHubRepositoryReleaseUrl_thenShowDocumentIsCalled() {
        coordinator.setHostServices(hostServices);
        coordinator.openGitHubRepositoryReleaseUrl();
        verify(hostServices).showDocument(anyString());
    }

    @Test
    void givenNoHostServicesSet_whenOpenGitHubRepositoryReleaseUrl_thenLogsWarning() {
        coordinator.openGitHubRepositoryReleaseUrl();
        String logMessage = logWatcher.list.getFirst().getFormattedMessage();
        assertTrue(
                logMessage.contains(
                        "▓▓ openGitHubRepositoryReleaseUrl hostServices not set yet: cannot open"
                                + " GitHub releases URL"),
                "The warning log was not found");
    }

    @Test
    void givenDefaultSceneSet_whenGetDefaultScene_thenReturnsSameScene() {
        assertSame(scene, coordinator.getDefaultScene());
    }

    @Test
    void givenNullPointerException_whenLoad_thenCatchAndReturnNull() throws Exception {
        when(fxmlLoader.load()).thenThrow(new NullPointerException("Forced NPE"));
        Object result = coordinator.setRootByFXMLName("dummy");
        assertNull(result);
        verify(coordinator).exitPlatform();
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains("NullPointerException caught")));
    }

    @Test
    void givenGenericException_whenLoad_thenCatchAndReturnNull() throws Exception {
        when(fxmlLoader.load()).thenThrow(new RuntimeException("Forced Exception"));
        Object result = coordinator.setRootByFXMLName("dummy");
        assertNull(result);
        verify(coordinator).exitPlatform();
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains("Exception caught when setting root")));
    }
}
