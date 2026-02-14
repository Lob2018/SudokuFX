/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding showAccessibleTextProperty() {
        return showAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerAccessibleTextProperty() {
        return playerAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveAccessibleTextProperty() {
        return solveAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupAccessibleTextProperty() {
        return backupAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding optionsAccessibleTextProperty() {
        return optionsAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageAccessibleTextProperty() {
        return languageAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpAccessibleTextProperty() {
        return helpAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding menuMiniButtonLanguageIsoTextProperty() {
        return languageIso;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding showTooltipProperty() {
        return showTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerTooltipProperty() {
        return playerTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveTooltipProperty() {
        return solveTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupTooltipProperty() {
        return backupTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding optionsTooltipProperty() {
        return optionsTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageTooltipProperty() {
        return languageTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpTooltipProperty() {
        return helpTooltip;
    }
}
