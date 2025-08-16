/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
class MenuNewViewModelUTest {

    @Mock private VersionService versionService;

    private MenuNewViewModel viewModel;
    private Locale originalLocale;

    record BindingCheck(StringBinding binding, Supplier<String> expected) {}

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        when(versionService.checkLatestVersion())
                .thenReturn(
                        new Task<>() {
                            @Override
                            protected Boolean call() {
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
    void givenLocale_whenStringBindings_thenValuesMatchBeforeAndAfterChange() {
        List<BindingCheck> bindings =
                List.of(
                        new BindingCheck(
                                viewModel.maxiNewAccessibleTextProperty(),
                                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility")),
                        new BindingCheck(
                                viewModel.maxiNewTextProperty(),
                                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.text")),
                        new BindingCheck(
                                viewModel.newAccessibleTextProperty(),
                                () -> I18n.INSTANCE.getValue("menu.mini.button.new.accessibility")),
                        new BindingCheck(
                                viewModel.maxiNewTooltipProperty(),
                                () -> I18n.INSTANCE.getValue("menu.maxi.button.new.accessibility")),
                        new BindingCheck(
                                viewModel.newTooltipProperty(),
                                () ->
                                        I18n.INSTANCE.getValue(
                                                "menu.mini.button.new.accessibility")));
        for (var locale : List.of("FR", "EN")) {
            I18n.INSTANCE.setLocaleBundle(locale);
            bindings.forEach(b -> assertEquals(b.expected.get(), b.binding.get()));
        }
    }

    @Test
    void givenVersionService_whenCheckLatestVersion_thenIsUpToDateAndStatusMessageUpdated()
            throws Exception {
        BooleanProperty isUpToDate = viewModel.isUpToDateProperty();
        StringProperty statusMessage = viewModel.statusMessageProperty();
        CountDownLatch latch = new CountDownLatch(1);
        statusMessage.addListener(
                (obs, oldVal, newVal) -> {
                    if ("Up to date".equals(newVal)) latch.countDown();
                });
        Task<Boolean> task = versionService.checkLatestVersion();
        new Thread(task).start();
        assertTrue(latch.await(2, TimeUnit.SECONDS), "Timeout waiting for 'Up to date' message");
        CountDownLatch platformLatch = new CountDownLatch(1);
        Platform.runLater(platformLatch::countDown);
        platformLatch.await();
        assertTrue(isUpToDate.get());
        assertEquals("Up to date", statusMessage.get());
    }
}
