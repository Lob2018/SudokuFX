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
 * ViewModel for the maxi menu.
 *
 * <p>Provides internationalized accessible texts, tooltips, and labels for each button in the maxi
 * menu. Texts automatically update when the application's locale changes.
 */
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

    /**
     * Initializes all bindings for accessible texts, tooltips, and labels, bound to the current
     * locale.
     */
    public MenuMaxiViewModel() {
        reduceAccessibleText = createStringBinding("menu.maxi.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.maxi.button.reduce.accessibility");
        reduceText = createStringBinding("menu.maxi.button.reduce.text");

        languageAccessibleText = createStringBinding("menu.maxi.button.language.accessibility");
        languageTooltip = createStringBinding("menu.maxi.button.language.accessibility");
        languageIso = createStringBinding("menu.maxi.button.language.iso");
        languageText = createStringBinding("menu.maxi.button.language.text");

        helpAccessibleText = createStringBinding("menu.maxi.button.help.accessibility");
        helpTooltip = createStringBinding("menu.maxi.button.help.accessibility");
        helpText = createStringBinding("menu.maxi.button.help.text");
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
     * Returns the reactive binding for the reduce button's accessible text.
     *
     * @return the StringBinding for the accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    /**
     * Returns the reactive binding for the reduce button's tooltip.
     *
     * @return the StringBinding for the tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    /**
     * Returns the reactive binding for the reduce button's label text.
     *
     * @return the StringBinding for the label text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceTextProperty() {
        return reduceText;
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
     * Returns the reactive binding for the language button's ISO code text.
     *
     * @return the StringBinding for the ISO code
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
     * Returns the reactive binding for the language button's ISO code text.
     *
     * @return the StringBinding for the ISO code
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageIsoProperty() {
        return languageIso;
    }

    /**
     * Returns the reactive binding for the language button's label text.
     *
     * @return the StringBinding for the label text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding languageTextProperty() {
        return languageText;
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
     * Returns the reactive binding for the help button's label text.
     *
     * @return the StringBinding for the label text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpTooltipProperty() {
        return helpTooltip;
    }

    /**
     * Returns the reactive binding for the help button's label text.
     *
     * @return the StringBinding for the label text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding helpTextProperty() {
        return helpText;
    }
}
