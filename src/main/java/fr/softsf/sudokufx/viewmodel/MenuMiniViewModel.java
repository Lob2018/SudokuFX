/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;

/**
 * ViewModel for the mini menu.
 *
 * <p>Provides internationalized accessible texts and tooltips for each button in the mini menu.
 * Texts automatically update when the application's locale changes.
 */
@Component
public class MenuMiniViewModel {

    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";

    private final StringBinding showAccessibleText;
    private final StringBinding playerAccessibleText;
    private final StringBinding solveAccessibleText;
    private final StringBinding backupAccessibleText;
    private final StringBinding optionsAccessibleText;
    private final StringBinding languageAccessibleText;
    private final StringBinding languageIso;
    private final StringBinding helpAccessibleText;

    private final StringBinding showTooltip;
    private final StringBinding playerTooltip;
    private final StringBinding solveTooltip;
    private final StringBinding backupTooltip;
    private final StringBinding optionsTooltip;
    private final StringBinding languageTooltip;
    private final StringBinding helpTooltip;

    /** Initializes all bindings for accessible texts and tooltips, bound to the current locale. */
    public MenuMiniViewModel() {
        showAccessibleText = createStringBinding("menu.mini.button.show.accessibility");
        playerAccessibleText = createStringBinding("menu.mini.button.player.accessibility");
        solveAccessibleText = createStringBinding("menu.mini.button.solve.accessibility");
        backupAccessibleText = createStringBinding("menu.mini.button.backup.accessibility");
        optionsAccessibleText = createStringBinding("menu.mini.button.options.accessibility");
        languageAccessibleText = createStringBinding("menu.mini.button.language.accessibility");
        languageIso = createStringBinding("menu.mini.button.language.iso");
        helpAccessibleText = createStringBinding("menu.mini.button.help.accessibility");

        showTooltip = showAccessibleText;
        playerTooltip = createTooltipBinding("menu.mini.button.player.accessibility");
        solveTooltip = createTooltipBinding("menu.mini.button.solve.accessibility");
        backupTooltip = createTooltipBinding("menu.mini.button.backup.accessibility");
        optionsTooltip = createTooltipBinding("menu.mini.button.options.accessibility");
        languageTooltip = languageAccessibleText;
        helpTooltip = helpAccessibleText;
    }

    /**
     * Creates a StringBinding for a given key, bound to the current locale.
     *
     * @param key the translation key
     * @return the bound string
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a tooltip StringBinding by appending the default accessibility role description.
     *
     * @param key the base translation key
     * @return the bound tooltip string
     */
    private StringBinding createTooltipBinding(String key) {
        return Bindings.createStringBinding(
                () ->
                        I18n.INSTANCE.getValue(key)
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED),
                I18n.INSTANCE.localeProperty());
    }

    public StringBinding showAccessibleTextProperty() {
        return showAccessibleText;
    }

    public StringBinding playerAccessibleTextProperty() {
        return playerAccessibleText;
    }

    public StringBinding solveAccessibleTextProperty() {
        return solveAccessibleText;
    }

    public StringBinding backupAccessibleTextProperty() {
        return backupAccessibleText;
    }

    public StringBinding optionsAccessibleTextProperty() {
        return optionsAccessibleText;
    }

    public StringBinding languageAccessibleTextProperty() {
        return languageAccessibleText;
    }

    public StringBinding helpAccessibleTextProperty() {
        return helpAccessibleText;
    }

    public StringBinding menuMiniButtonLanguageIsoTextProperty() {
        return languageIso;
    }

    public StringBinding showTooltipProperty() {
        return showTooltip;
    }

    public StringBinding playerTooltipProperty() {
        return playerTooltip;
    }

    public StringBinding solveTooltipProperty() {
        return solveTooltip;
    }

    public StringBinding backupTooltipProperty() {
        return backupTooltip;
    }

    public StringBinding optionsTooltipProperty() {
        return optionsTooltip;
    }

    public StringBinding languageTooltipProperty() {
        return languageTooltip;
    }

    public StringBinding helpTooltipProperty() {
        return helpTooltip;
    }
}
