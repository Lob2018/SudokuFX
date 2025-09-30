/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.springframework.stereotype.Component;

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
                createStringBinding("menu.accessibility.role.description.submenu.option");
        solveClearTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.solve.button.solve.clear.accessibility",
                        "menu.accessibility.role.description.submenu.option");
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

    public StringBinding solveMenuMaxiAccessibleTextProperty() {
        return solveMenuMaxiAccessibleText;
    }

    public StringBinding solveMenuMaxiTooltipProperty() {
        return solveMenuMaxiTooltip;
    }

    public StringBinding solveMenuMaxiRoleDescriptionProperty() {
        return solveMenuMaxiRoleDescription;
    }

    public StringBinding solveMenuMaxiTextProperty() {
        return solveMenuMaxiText;
    }

    public StringBinding solveReduceAccessibleTextProperty() {
        return solveReduceAccessibleText;
    }

    public StringBinding solveReduceTooltipProperty() {
        return solveReduceTooltip;
    }

    public StringBinding solveReduceTextProperty() {
        return solveReduceText;
    }

    public StringBinding solveRoleDescriptionProperty() {
        return solveRoleDescription;
    }

    public StringBinding solveTextProperty() {
        return solveText;
    }

    public StringBinding solveClearAccessibleTextProperty() {
        return solveClearAccessibleText;
    }

    public StringBinding solveClearRoleDescriptionProperty() {
        return solveClearRoleDescription;
    }

    public StringBinding solveClearTooltipProperty() {
        return solveClearTooltip;
    }

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
}
