/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;

/**
 * ViewModel for managing "solve" menu UI state and accessibility texts.
 *
 * <p>Holds a percentage value used for solving, and provides localized StringBindings for UI
 * labels, accessibility, tooltips, and role descriptions.
 *
 * <p>Uses I18n singleton for localization with automatic updates on locale changes.
 */
@Component
public class MenuSolveViewModel {
    private final IntegerProperty solvePercentage = new SimpleIntegerProperty(-1);

    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";

    private final StringBinding solveMenuMaxiAccessibleText;
    private final StringBinding solveMenuMaxiTooltip;
    private final StringBinding solveMenuMaxiRoleDescription;
    private final StringBinding solveMenuMaxiText;

    private final StringBinding solveReduceAccessibleText;
    private final StringBinding solveReduceTooltip;
    private final StringBinding solveReduceText;

    private final StringBinding solveRoleDescription;
    private final StringBinding solveText;

    private final StringBinding solveClearAccessibleText;
    private final StringBinding solveClearRoleDescription;
    private final StringBinding solveClearTooltip;

    private final StringBinding solveUseThisGridAccessibleText;
    private final StringBinding solveUseThisGridText;
    private final StringBinding solveUseThisGridRoleDescription;
    private final StringBinding solveUseThisGridTooltip;

    /**
     * Initializes the MenuSolveViewModel with necessary reactive bindings.
     *
     * <p>Configures i18n-aware StringBindings for UI labels, tooltips, and accessibility roles.
     */
    public MenuSolveViewModel() {
        solveMenuMaxiAccessibleText = createStringBinding("menu.maxi.button.solve.accessibility");
        solveMenuMaxiTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.maxi.button.solve.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        solveMenuMaxiRoleDescription =
                createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        solveMenuMaxiText = createStringBinding("menu.maxi.button.solve.text");
        solveReduceAccessibleText = createStringBinding("menu.solve.button.reduce.accessibility");
        solveReduceTooltip = createStringBinding("menu.solve.button.reduce.accessibility");
        solveReduceText = createStringBinding("menu.solve.button.reduce.text");
        solveRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        solveText = createStringBinding("menu.solve.button.solve.text");
        solveClearAccessibleText =
                createStringBinding("menu.solve.button.solve.clear.accessibility");
        solveClearRoleDescription =
                createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);
        solveClearTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.solve.button.solve.clear.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);
        solveUseThisGridAccessibleText =
                createStringBinding("menu.solve.button.use.this.grid.accessibility");
        solveUseThisGridTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.solve.button.use.this.grid.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);
        solveUseThisGridRoleDescription =
                createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);
        solveUseThisGridText = createStringBinding("menu.solve.button.use.this.grid.text");
    }

    /**
     * Creates a simple localized StringBinding for the given key. Updates automatically when the
     * locale changes.
     *
     * @param key the i18n key
     * @return a StringBinding for the localized value
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a localized StringBinding by concatenating the value of two keys. Updates
     * automatically when the locale changes.
     *
     * @param key the base key
     * @param suffixKey the key for the suffix to append
     * @return a StringBinding for the combined localized value
     */
    private StringBinding createFormattedAndConcatenatedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key) + I18n.INSTANCE.getValue(suffixKey),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns the reactive binding for the solve menu maxi accessible text property.
     *
     * @return the StringBinding for the solve menu maxi accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveMenuMaxiAccessibleTextProperty() {
        return solveMenuMaxiAccessibleText;
    }

    /**
     * Returns the reactive binding for the solve menu maxi tooltip property.
     *
     * @return the StringBinding for the solve menu maxi tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveMenuMaxiTooltipProperty() {
        return solveMenuMaxiTooltip;
    }

    /**
     * Returns the reactive binding for the solve menu maxi role description property.
     *
     * @return the StringBinding for the solve menu maxi role description
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveMenuMaxiRoleDescriptionProperty() {
        return solveMenuMaxiRoleDescription;
    }

    /**
     * Returns the reactive binding for the solve menu maxi text property.
     *
     * @return the StringBinding for the solve menu maxi text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveMenuMaxiTextProperty() {
        return solveMenuMaxiText;
    }

    /**
     * Returns the reactive binding for the solve reduce accessible text property.
     *
     * @return the StringBinding for the solve reduce accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveReduceAccessibleTextProperty() {
        return solveReduceAccessibleText;
    }

    /**
     * Returns the reactive binding for the solve reduce tooltip property.
     *
     * @return the StringBinding for the solve reduce tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveReduceTooltipProperty() {
        return solveReduceTooltip;
    }

    /**
     * Returns the reactive binding for the solve reduce text property.
     *
     * @return the StringBinding for the solve reduce text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveReduceTextProperty() {
        return solveReduceText;
    }

    /**
     * Returns the reactive binding for the solve role description property.
     *
     * @return the StringBinding for the solve role description
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveRoleDescriptionProperty() {
        return solveRoleDescription;
    }

    /**
     * Returns the reactive binding for the solve text property.
     *
     * @return the StringBinding for the solve text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveTextProperty() {
        return solveText;
    }

    /**
     * Returns the reactive binding for the solve clear accessible text property.
     *
     * @return the StringBinding for the solve clear accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveClearAccessibleTextProperty() {
        return solveClearAccessibleText;
    }

    /**
     * Returns the reactive binding for the solve clear role description property.
     *
     * @return the StringBinding for the solve clear role description
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveClearRoleDescriptionProperty() {
        return solveClearRoleDescription;
    }

    /**
     * Returns the reactive binding for the solve clear tooltip property.
     *
     * @return the StringBinding for the solve clear tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveClearTooltipProperty() {
        return solveClearTooltip;
    }

    /**
     * Returns the integer property for the solve percentage.
     *
     * @return the IntegerProperty for solve percentage
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public IntegerProperty solvePercentageProperty() {
        return solvePercentage;
    }

    /**
     * Sets the value of the percentage property.
     *
     * @param value the new percentage value
     */
    public void setSolvePercentage(int value) {
        this.solvePercentage.set(value);
    }

    /**
     * Returns the reactive binding for the solve use this grid accessible text property.
     *
     * @return the StringBinding for the solve use this grid accessible text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveUseThisGridAccessibleTextProperty() {
        return solveUseThisGridAccessibleText;
    }

    /**
     * Returns the reactive binding for the solve use this grid tooltip property.
     *
     * @return the StringBinding for the solve use this grid tooltip
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveUseThisGridTooltipProperty() {
        return solveUseThisGridTooltip;
    }

    /**
     * Returns the reactive binding for the solve use this grid role description property.
     *
     * @return the StringBinding for the solve use this grid role description
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveUseThisGridRoleDescriptionProperty() {
        return solveUseThisGridRoleDescription;
    }

    /**
     * Returns the reactive binding for the solve use this grid text property.
     *
     * @return the StringBinding for the solve use this grid text
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding solveUseThisGridTextProperty() {
        return solveUseThisGridText;
    }
}
