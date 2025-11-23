/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;

/**
 * MenuHiddenViewModel (not final) handles the business logic for the "Show Menu" button's
 * accessibility text when the menu is hidden.
 *
 * <p>The accessibility text is updated automatically based on the current language using the I18n
 * utility. The property can be bound to UI elements to reflect changes reactively.
 *
 * @see I18n
 */
@Component
public class MenuHiddenViewModel {

    /** Reactive binding for the accessibility text of the "Show Menu" button when hidden. */
    private final StringBinding menuHiddenButtonShowAccessibilityText;

    public MenuHiddenViewModel() {
        menuHiddenButtonShowAccessibilityText =
                Bindings.createStringBinding(
                        () -> I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"),
                        I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns the reactive StringBinding for the accessibility text of the "Show Menu" button when
     * the menu is hidden.
     *
     * @return the StringBinding for the button's accessibility text.
     */
    public StringBinding menuHiddenButtonShowAccessibilityTextProperty() {
        return menuHiddenButtonShowAccessibilityText;
    }
}
