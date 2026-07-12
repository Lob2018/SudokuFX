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

    /**
     * Returns the reactive binding for the show button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding showAccessibleTextProperty() {
        return showAccessibleText;
    }

    /**
     * Returns the reactive binding for the player button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerAccessibleTextProperty() {
        return playerAccessibleText;
    }

    /**
     * Returns the reactive binding for the solve button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveAccessibleTextProperty() {
        return solveAccessibleText;
    }

    /**
     * Returns the reactive binding for the backup button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupAccessibleTextProperty() {
        return backupAccessibleText;
    }

    /**
     * Returns the reactive binding for the options button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding optionsAccessibleTextProperty() {
        return optionsAccessibleText;
    }

    /**
     * Returns the reactive binding for the language button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageAccessibleTextProperty() {
        return languageAccessibleText;
    }

    /**
     * Returns the reactive binding for the help button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpAccessibleTextProperty() {
        return helpAccessibleText;
    }

    /**
     * Returns the reactive binding for the language button's ISO text.
     *
     * @return the StringBinding for the ISO text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding menuMiniButtonLanguageIsoTextProperty() {
        return languageIso;
    }

    /**
     * Returns the reactive binding for the show button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding showTooltipProperty() {
        return showTooltip;
    }

    /**
     * Returns the reactive binding for the player button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerTooltipProperty() {
        return playerTooltip;
    }

    /**
     * Returns the reactive binding for the solve button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveTooltipProperty() {
        return solveTooltip;
    }

    /**
     * Returns the reactive binding for the backup button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupTooltipProperty() {
        return backupTooltip;
    }

    /**
     * Returns the reactive binding for the options button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding optionsTooltipProperty() {
        return optionsTooltip;
    }

    /**
     * Returns the reactive binding for the language button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageTooltipProperty() {
        return languageTooltip;
    }

    /**
     * Returns the reactive binding for the help button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpTooltipProperty() {
        return helpTooltip;
    }
}
