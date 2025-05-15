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

@Component
public class MenuMaxiViewModel {

    private final StringBinding reduceAccessibleText;
    private final StringBinding reduceTooltip;
    private final StringBinding reduceText;

    private final StringBinding languageAccessibleText;
    private final StringBinding languageTooltip;
    private final StringBinding languageIso;
    private final StringBinding languageText;

    private final StringBinding helpAccessibleText;
    private final StringBinding helpTooltip;
    private final StringBinding helpText;

    private final StringBinding newAccessibleText;
    private final StringBinding newTooltip;
    private final StringBinding newText;

    public MenuMaxiViewModel() {
        reduceAccessibleText = createBinding("menu.maxi.button.reduce.accessibility");
        reduceTooltip = createBinding("menu.maxi.button.reduce.accessibility");
        reduceText = createBinding("menu.maxi.button.reduce.text");

        languageAccessibleText = createBinding("menu.maxi.button.language.accessibility");
        languageTooltip = createBinding("menu.maxi.button.language.accessibility");
        languageIso = createBinding("menu.maxi.button.language.iso");
        languageText = createBinding("menu.maxi.button.language.text");

        helpAccessibleText = createBinding("menu.maxi.button.help.accessibility");
        helpTooltip = createBinding("menu.maxi.button.help.accessibility");
        helpText = createBinding("menu.maxi.button.help.text");

        newAccessibleText = createBinding("menu.maxi.button.new.accessibility");
        newTooltip = createBinding("menu.maxi.button.new.accessibility");
        newText = createBinding("menu.maxi.button.new.text");
    }

    private StringBinding createBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    public StringBinding reduceTextProperty() {
        return reduceText;
    }

    public StringBinding languageAccessibleTextProperty() {
        return languageAccessibleText;
    }

    public StringBinding languageTooltipProperty() {
        return languageTooltip;
    }

    public StringBinding languageIsoProperty() {
        return languageIso;
    }

    public StringBinding languageTextProperty() {
        return languageText;
    }

    public StringBinding helpAccessibleTextProperty() {
        return helpAccessibleText;
    }

    public StringBinding helpTooltipProperty() {
        return helpTooltip;
    }

    public StringBinding helpTextProperty() {
        return helpText;
    }

    public StringBinding newAccessibleTextProperty() {
        return newAccessibleText;
    }

    public StringBinding newTooltipProperty() {
        return newTooltip;
    }

    public StringBinding newTextProperty() {
        return newText;
    }
}
