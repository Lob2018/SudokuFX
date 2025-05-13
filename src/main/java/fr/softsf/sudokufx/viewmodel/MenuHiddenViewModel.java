/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;

/**
 * MenuHiddenViewModel (not final) handles the business logic for the "Show Menu" button's
 * accessibility text when the menu is hidden.
 *
 * <p>The accessibility text is updated based on the current language using the I18n utility and can
 * be bound to UI elements to reflect changes automatically.
 *
 * @see I18n
 */
@Component
public class MenuHiddenViewModel {

    /** The accessibility text for the "Show Menu" button when the menu is hidden. */
    private final StringProperty menuHiddenButtonShowAccessibilityText = new SimpleStringProperty();

    /** Constructs the `MenuHiddenViewModel` and initializes the accessibility text. */
    public MenuHiddenViewModel() {
        updateTexts();
    }

    /** Updates the accessibility text using the current language from the I18n instance. */
    public void updateTexts() {
        menuHiddenButtonShowAccessibilityText.set(
                I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));
    }

    /**
     * Returns the property for the accessibility text of the "Show Menu" button when the menu is
     * hidden.
     *
     * @return the `StringProperty` for the button's accessibility text.
     */
    public StringProperty menuHiddenButtonShowAccessibilityTextProperty() {
        return menuHiddenButtonShowAccessibilityText;
    }
}
