/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.DifficultyLevel;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.view.components.PossibilityStarsHBox;

/**
 * ViewModel for menu difficulty levels, managing the selected difficulty level state and related UI
 * bindings such as labels, accessibility texts, and visibility. Provides reactive properties and
 * bindings for JavaFX views, encapsulating business logic for difficulty selection.
 *
 * <p>This class is a Spring component and intended for dependency injection.
 *
 * <p>Note: implementation is not final and may evolve.
 */
@Component
public class MenuLevelViewModel {

    private final ObjectProperty<DifficultyLevel> selectedLevel = new SimpleObjectProperty<>(null);
    private final IntegerProperty starsPercentage = new SimpleIntegerProperty(0);

    /**
     * Returns the property representing the currently selected difficulty level.
     *
     * @return the selected difficulty level property
     */
    public ObjectProperty<DifficultyLevel> selectedLevelProperty() {
        return selectedLevel;
    }

    /**
     * Updates the selected difficulty level and corresponding stars percentage.
     *
     * @param level the new difficulty level to select
     * @param percentage the stars completion percentage for this level
     */
    public void updateSelectedLevel(DifficultyLevel level, int percentage) {
        selectedLevel.set(level);
        starsPercentage.set(percentage);
    }

    /**
     * Returns a reactive BooleanBinding that is true when the specified difficulty level is
     * selected.
     *
     * @param level the difficulty level to check
     * @return a BooleanBinding reflecting selection state
     */
    public BooleanBinding isSelectedBinding(DifficultyLevel level) {
        return Bindings.createBooleanBinding(() -> selectedLevel.get() == level, selectedLevel);
    }

    /**
     * Creates a StringBinding for the localized label text of the specified difficulty level,
     * automatically updated when the locale changes.
     *
     * @param level the difficulty level
     * @return a reactive binding to the localized label text
     */
    public StringBinding labelTextBinding(DifficultyLevel level) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(getLevelNameKey(level)),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a StringBinding for accessible text describing the stars component, useful for screen
     * readers. The binding updates on changes to selected level, star percentage, or locale.
     *
     * @param starsBox the stars UI component
     * @param key the localization key for accessibility text
     * @return a reactive binding to the accessibility text
     */
    public StringBinding accessibleTextBinding(PossibilityStarsHBox starsBox, String key) {
        return Bindings.createStringBinding(
                () -> starsBox.formattedTextBinding(key, false).get(),
                selectedLevel,
                starsBox.getPercentage(),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns a StringBinding with the localized role description for the selected difficulty
     * level, useful for accessibility tools.
     *
     * @return a reactive binding to the role description text
     */
    public StringBinding selectedRoleDescriptionBinding() {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.selected"),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns the i18n localization key used for accessibility descriptions of the specified
     * difficulty level.
     *
     * @param level the difficulty level
     * @return the localization key string
     */
    public String getAccessibilityKeyForLevel(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.accessibility";
            case MEDIUM -> "menu.maxi.button.medium.accessibility";
            case DIFFICULT -> "menu.maxi.button.difficult.accessibility";
        };
    }

    /**
     * Returns the i18n localization key used for the display text of the specified difficulty
     * level.
     *
     * @param level the difficulty level
     * @return the localization key string
     */
    private String getLevelNameKey(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.text";
            case MEDIUM -> "menu.maxi.button.medium.text";
            case DIFFICULT -> "menu.maxi.button.difficult.text";
        };
    }
}
