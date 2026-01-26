/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.util.List;
import java.util.Locale;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.concurrent.Task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.service.external.VersionService;
import fr.softsf.sudokufx.viewmodel.MenuNewViewModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith({ApplicationExtension.class, MockitoExtension.class})
class MenuNewViewModelUTest {

    @Mock private VersionService versionService;
    private MenuNewViewModel viewModel;
    private Locale originalLocale;

    private record BindingCheck(StringBinding binding, String i18nKey) {}

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        when(versionService.checkLatestVersion())
                .thenReturn(
                        new Task<>() {
                            @Override
                            protected Boolean call() {
                                Platform.runLater(() -> updateMessage("Up to date"));
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
                                "menu.maxi.button.new.accessibility"),
                        new BindingCheck(
                                viewModel.maxiNewTextProperty(), "menu.maxi.button.new.text"),
                        new BindingCheck(
                                viewModel.newAccessibleTextProperty(),
                                "menu.mini.button.new.accessibility"),
                        new BindingCheck(
                                viewModel.maxiNewTooltipProperty(),
                                "menu.maxi.button.new.accessibility"),
                        new BindingCheck(
                                viewModel.newTooltipProperty(),
                                "menu.mini.button.new.accessibility"));
        for (String locale : List.of("FR", "EN")) {
            I18n.INSTANCE.setLocaleBundle(locale);
            bindings.forEach(b -> assertEquals(I18n.INSTANCE.getValue(b.i18nKey), b.binding.get()));
        }
    }
}
