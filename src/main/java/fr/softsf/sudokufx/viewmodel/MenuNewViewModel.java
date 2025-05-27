/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;

/**
 * ViewModel managing menu "new version" UI state and accessibility strings.
 *
 * <p>Provides localized StringBindings for button labels and tooltips, updating automatically on
 * locale changes.
 */
@Component
public class MenuNewViewModel {

    private final StringBinding maxiNewAccessibleText;
    private final StringBinding maxiNewTooltip;
    private final StringBinding maxiNewText;
    private final StringBinding newAccessibleText;
    private final StringBinding newTooltip;

    public MenuNewViewModel() {
        maxiNewAccessibleText = createStringBinding("menu.maxi.button.new.accessibility");
        maxiNewTooltip = createStringBinding("menu.maxi.button.new.accessibility");
        maxiNewText = createStringBinding("menu.maxi.button.new.text");
        newAccessibleText = createStringBinding("menu.mini.button.new.accessibility");
        newTooltip = newAccessibleText;
    }

    /**
     * Creates a StringBinding for a given translation key, automatically updating when the locale
     * changes.
     *
     * @param key the i18n translation key
     * @return the bound localized string
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    public StringBinding maxiNewAccessibleTextProperty() {
        return maxiNewAccessibleText;
    }

    public StringBinding maxiNewTooltipProperty() {
        return maxiNewTooltip;
    }

    public StringBinding maxiNewTextProperty() {
        return maxiNewText;
    }

    public StringBinding newAccessibleTextProperty() {
        return newAccessibleText;
    }

    public StringBinding newTooltipProperty() {
        return newTooltip;
    }
}
