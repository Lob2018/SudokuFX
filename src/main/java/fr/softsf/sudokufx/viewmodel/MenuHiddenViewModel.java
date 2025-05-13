/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;

/** MenuHiddenViewModel with business logic (not final) */
@Component
public class MenuHiddenViewModel {
    private final StringProperty menuHiddenButtonShowAccessibilityText = new SimpleStringProperty();

    public MenuHiddenViewModel() {
        updateTexts();
    }

    public void updateTexts() {
        menuHiddenButtonShowAccessibilityText.set(
                I18n.INSTANCE.getValue("menu.hidden.button.show.accessibility"));
    }

    public StringProperty menuHiddenButtonShowAccessibilityTextProperty() {
        return menuHiddenButtonShowAccessibilityText;
    }
}
