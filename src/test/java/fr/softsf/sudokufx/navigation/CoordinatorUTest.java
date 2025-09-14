/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.navigation;

import java.time.LocalDateTime;
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
import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.service.business.PlayerLanguageService;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

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
    private AutoCloseable mocks;

    private PlayerService playerServiceMock;
    private PlayerLanguageService playerLanguageServiceMock;
    private PlayerStateHolder playerStateHolderMock;
    private ToasterVBox toaster;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(Coordinator.class)).addAppender(logWatcher);
        mocks = MockitoAnnotations.openMocks(this);
        scene = new Scene(new VBox(), 200, 200);
        toaster = new ToasterVBox();
        coordinator = spy(new Coordinator(fxmlLoader));
        coordinator.setDefaultScene(scene);
        doNothing().when(coordinator).exitPlatform();
        playerServiceMock = mock(PlayerService.class);
        playerLanguageServiceMock = mock(PlayerLanguageService.class);
        playerStateHolderMock = mock(PlayerStateHolder.class);
        when(playerLanguageServiceMock.getByIso("EN")).thenReturn(new PlayerLanguageDto(2L, "EN"));
        when(playerLanguageServiceMock.getByIso("FR")).thenReturn(new PlayerLanguageDto(1L, "FR"));
        PlayerDto defaultPlayer =
                new PlayerDto(
                        1L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "FFFFFFFF", "", "", true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "SafePlayer",
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        when(playerStateHolderMock.getCurrentPlayer()).thenReturn(defaultPlayer);
        when(playerServiceMock.updatePlayer(any(PlayerDto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        coordinator.setPlayerService(playerServiceMock);
        coordinator.setPlayerLanguageService(playerLanguageServiceMock);
        coordinator.setPlayerStateHolder(playerStateHolderMock);
    }

    @AfterEach
    void cleanup() throws Exception {
        ((Logger) LoggerFactory.getLogger(Coordinator.class)).detachAndStopAllAppenders();
        FxToolkit.cleanupStages();
        if (mocks != null) {
            mocks.close();
        }
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
    void givenNonNullDynamicFontSize_whenSetDynamicFontSize_thenDoesNotThrow() {
        assertDoesNotThrow(() -> coordinator.setDynamicFontSize(dynamicFontSize));
    }

    @Test
    void givenBlankFXMLName_whenSetRootByFXMLName_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class, () -> coordinator.setRootByFXMLName(" "));
        assertTrue(ex.getMessage().contains("Fxml must not be null or blank"));
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

    @Test
    void givenLanguageFR_whenToggleLanguage_thenLanguageIsSwitchedToEn() {
        I18n.INSTANCE.setLocaleBundle("FR");
        coordinator.toggleLanguage(toaster);
        assertEquals("en", I18n.INSTANCE.getLanguage());
    }

    @Test
    void givenLanguageEn_whenToggleLanguage_thenLanguageIsSwitchedToFr() {
        I18n.INSTANCE.setLocaleBundle("EN");
        coordinator.toggleLanguage(toaster);
        assertEquals("fr", I18n.INSTANCE.getLanguage());
    }

    @Test
    void givenPlayerStateHolder_whenGetCurrentPlayerLanguageIso_thenReturnsIsoCode() {
        String iso = coordinator.getCurrentPlayerLanguageIso();
        assertEquals("FR", iso);
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
                logMessage.contains("▓▓ openGitHubRepositoryReleaseUrl hostServices not set yet"));
    }

    @Test
    void givenDefaultSceneSet_whenGetDefaultScene_thenReturnsSameScene() {
        assertSame(scene, coordinator.getDefaultScene());
    }

    @Test
    void givenExceptionDuringToggleLanguage_whenToggleLanguage_thenCatchAndToast() {
        I18n.INSTANCE.setLocaleBundle("FR");
        when(playerLanguageServiceMock.getByIso("EN"))
                .thenThrow(new RuntimeException("Forced exception"));
        int beforeToastCount = toaster.getChildren().size();
        String isoBefore = I18n.INSTANCE.getLanguage();
        coordinator.toggleLanguage(toaster);
        assertEquals(isoBefore, I18n.INSTANCE.getLanguage());
        assertEquals(beforeToastCount + 1, toaster.getChildren().size());
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains("██ Exception ToggleLanguage failed")));
        toaster.getChildren().clear();
    }
}
