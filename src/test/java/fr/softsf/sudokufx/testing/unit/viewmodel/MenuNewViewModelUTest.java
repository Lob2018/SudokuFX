/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.util.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.service.VersionService;
import fr.softsf.sudokufx.viewmodel.MenuNewViewModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// TODO: Review this code for side effects
@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
class MenuNewViewModelUTest {

    @Mock private VersionService versionService;
    private MenuNewViewModel viewModel;
    private Locale originalLocale;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        when(versionService.checkLatestVersion())
                .thenAnswer(
                        invocation ->
                                new Task<>() {
                                    @Override
                                    protected Boolean call() throws Exception {
                                        updateMessage("Version check in progress...");
                                        CountDownLatch latch = new CountDownLatch(1);
                                        Platform.runLater(
                                                () -> {
                                                    PauseTransition pause =
                                                            new PauseTransition(
                                                                    Duration.millis(50));
                                                    pause.setOnFinished(e -> latch.countDown());
                                                    pause.play();
                                                });
                                        latch.await();
                                        updateMessage("Up to date");
                                        return true;
                                    }
                                });
        viewModel = new MenuNewViewModel(versionService);
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void stringBindings_shouldMatchI18nValues_beforeAndAfterLocaleChange() {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        map.put(
                viewModel.maxiNewAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        map.put(
                viewModel.maxiNewTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.text"));
        map.put(
                viewModel.newAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        map.put(
                viewModel.maxiNewTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility"));
        map.put(
                viewModel.newTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
        for (var entry : map.entrySet()) {
            assertEquals(entry.getValue().get(), entry.getKey().get());
        }
        I18n.INSTANCE.setLocaleBundle("EN");
        for (var entry : map.entrySet()) {
            assertEquals(entry.getValue().get(), entry.getKey().get());
        }
    }

    @Test
    void checkVersion_shouldUpdateIsUpToDateAndStatusMessage() throws Exception {
        BooleanProperty isUpToDate = viewModel.isUpToDateProperty();
        StringProperty statusMessage = viewModel.statusMessageProperty();
        assertTrue(isUpToDate.get());
        assertNotNull(statusMessage.get());
        Task<Boolean> task = versionService.checkLatestVersion();
        CountDownLatch messageLatch = new CountDownLatch(1);
        statusMessage.addListener(
                (obs, oldVal, newVal) -> {
                    if ("Up to date".equals(newVal)) {
                        messageLatch.countDown();
                    }
                });
        new Thread(task).start();
        assertTrue(
                messageLatch.await(2, TimeUnit.SECONDS),
                "Timeout waiting for 'Up to date' message");
        CountDownLatch platformLatch = new CountDownLatch(1);
        Platform.runLater(platformLatch::countDown);
        platformLatch.await();
        assertTrue(isUpToDate.get(), "isUpToDate should be true after task completion");
        assertEquals(
                "Up to date",
                statusMessage.get(),
                "statusMessage should be updated from Task message");
    }
}
