/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.service.external.VersionService;

/**
 * ViewModel for managing the "New" menu UI state, accessibility labels, and update status.
 *
 * <p>Provides localized StringBindings for button texts, accessible texts, and tooltips for both
 * maxi and mini "New" buttons.
 *
 * <p>Checks asynchronously via VersionService if the application is up-to-date, exposing update
 * status and related messages to the view.
 *
 * <p>Uses the I18n singleton for localization with automatic updates on locale changes.
 */
@Component
public class MenuNewViewModel {

    private final StringBinding maxiNewAccessibleText;
    private final StringBinding maxiNewText;
    private final StringBinding newAccessibleText;
    private final StringBinding maxiNewTooltip;
    private final StringBinding newTooltip;

    private final VersionService versionService;
    private final BooleanProperty isOutOfDate = new SimpleBooleanProperty(false);
    private final StringProperty statusMessage = new SimpleStringProperty();
    private Task<Boolean> checkVersionTask;
    private ChangeListener<Boolean> versionListener;

    public MenuNewViewModel(VersionService versionService) {
        this.versionService = versionService;
        maxiNewAccessibleText = createStringBinding("menu.maxi.button.new.accessibility");
        maxiNewText = createStringBinding("menu.maxi.button.new.text");
        newAccessibleText = createStringBinding("menu.mini.button.new.accessibility");
        maxiNewTooltip = createStringBinding("menu.maxi.button.new.accessibility");
        newTooltip = newAccessibleText;
        checkVersion();
    }

    /**
     * Creates a localized StringBinding for the given key. Automatically updates when the locale
     * changes.
     *
     * @param key the localization key
     * @return a StringBinding providing localized text
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    public StringBinding maxiNewAccessibleTextProperty() {
        return maxiNewAccessibleText;
    }

    public StringBinding maxiNewTextProperty() {
        return maxiNewText;
    }

    public StringBinding newAccessibleTextProperty() {
        return newAccessibleText;
    }

    public StringBinding maxiNewTooltipProperty() {
        return maxiNewTooltip;
    }

    public StringBinding newTooltipProperty() {
        return newTooltip;
    }

    /**
     * Starts an async version check, updating isOutOfDate and statusMessage. Safely removes
     * previous listeners and bindings to avoid memory leaks. Runs the check in a daemon thread.
     */
    private void checkVersion() {
        if (statusMessage.isBound()) {
            statusMessage.unbind();
        }
        if (checkVersionTask != null && versionListener != null) {
            checkVersionTask.valueProperty().removeListener(versionListener);
        }
        checkVersionTask = versionService.checkLatestVersion();
        statusMessage.bind(checkVersionTask.messageProperty());
        versionListener =
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        Platform.runLater(() -> isOutOfDate.set(!newVal));
                    }
                };
        checkVersionTask.valueProperty().addListener(versionListener);
        Thread thread = new Thread(checkVersionTask);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Indicates whether the application is out-of-date. This property is useful for binding
     * visibility of update notifications.
     *
     * @return a BooleanProperty representing out-of-date status
     */
    public BooleanProperty isOutOfDateProperty() {
        return isOutOfDate;
    }

    /**
     * Provides the current status message from the version check.
     *
     * @return a StringProperty with status messages
     */
    public StringProperty statusMessageProperty() {
        return statusMessage;
    }
}
