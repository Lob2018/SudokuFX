/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.service.VersionService;

@Component
public class MenuNewViewModel {

    private final StringBinding maxiNewAccessibleText;
    private final StringBinding maxiNewText;
    private final StringBinding newAccessibleText;

    private final VersionService versionService;
    private final BooleanProperty isUpToDate = new SimpleBooleanProperty(true);
    private final StringProperty statusMessage = new SimpleStringProperty();

    public MenuNewViewModel(VersionService versionService) {
        this.versionService = versionService;

        maxiNewAccessibleText = createStringBinding("menu.maxi.button.new.accessibility");
        maxiNewText = createStringBinding("menu.maxi.button.new.text");
        newAccessibleText = createStringBinding("menu.mini.button.new.accessibility");

        I18n.INSTANCE
                .localeProperty()
                .addListener(
                        (obs, oldLocale, newLocale) -> {
                            checkVersion();
                        });
        checkVersion();
    }

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

    private void checkVersion() {
        if (statusMessage.isBound()) {
            statusMessage.unbind();
        }
        Task<Boolean> task = versionService.checkLatestVersion();
        statusMessage.bind(task.messageProperty());
        task.valueProperty()
                .addListener(
                        (obs, oldVal, newVal) -> {
                            if (newVal != null) Platform.runLater(() -> isUpToDate.set(newVal));
                        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public BooleanProperty isUpToDateProperty() {
        return isUpToDate;
    }

    public StringProperty statusMessageProperty() {
        return statusMessage;
    }
}
