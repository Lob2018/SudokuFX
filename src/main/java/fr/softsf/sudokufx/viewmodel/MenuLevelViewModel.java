/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.DifficultyLevel;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.view.component.PossibilityStarsHBox;

/**
 * ViewModel for managing difficulty level selection in the menu.
 *
 * <p>Manages the currently selected difficulty level and related UI bindings including labels,
 * accessibility texts, and stars completion percentage. Provides reactive properties and bindings
 * to facilitate JavaFX UI updates in response to state changes.
 */
@Component
public class MenuLevelViewModel {

    private final ObjectProperty<DifficultyLevel> selectedLevel = new SimpleObjectProperty<>(null);
    private final IntegerProperty starsPercentage = new SimpleIntegerProperty(100);

    /**
     * Returns the property representing the currently selected difficulty level.
     *
     * @return the selected difficulty level property
     */
    public ObjectProperty<DifficultyLevel> selectedLevelProperty() {
        return selectedLevel;
    }

    /**
     * Updates the selected difficulty level and its associated stars percentage.
     *
     * @param level the new difficulty level (must not be {@code null})
     * @param stars the completion percentage
     * @throws IllegalArgumentException if {@code level} is {@code null}
     */
    public void updateSelectedLevel(DifficultyLevel level, int stars) {
        if (Objects.isNull(level)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "Difficulty level cannot be null");
        }
        selectedLevel.set(level);
        starsPercentage.set(stars);
    }

    /**
     * Returns a reactive BooleanBinding indicating whether the given difficulty level is currently
     * selected.
     *
     * @param level the difficulty level to test
     * @return a BooleanBinding reflecting the selection state of the level
     */
    public BooleanBinding isSelectedBinding(DifficultyLevel level) {
        return Bindings.createBooleanBinding(() -> selectedLevel.get() == level, selectedLevel);
    }

    /**
     * Creates a StringBinding for the localized label text of the specified difficulty level. This
     * binding updates automatically when the application locale changes.
     *
     * @param level the difficulty level
     * @return a StringBinding providing the localized label text
     */
    public StringBinding labelTextBinding(DifficultyLevel level) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(getLevelNameKey(level)),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a StringBinding for the accessibility text of the stars UI component associated with
     * a difficulty level. The binding reacts to changes in selection, stars percentage, and locale.
     *
     * @param starsBox the stars UI component providing formatted text
     * @param key the localization key for the accessibility text
     * @return a StringBinding providing the accessible description for screen readers
     */
    public StringBinding accessibleTextBinding(PossibilityStarsHBox starsBox, String key) {
        return Bindings.createStringBinding(
                () -> starsBox.formattedTextBinding(key, false).get(),
                selectedLevel,
                starsBox.getPercentage(),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns a StringBinding for the localized role description text used for accessibility,
     * describing the selected difficulty level.
     *
     * @return a StringBinding providing the role description text
     */
    public StringBinding selectedRoleDescriptionBinding() {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.selected"),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns the i18n key used for accessibility descriptions of a given difficulty level.
     *
     * @param level the difficulty level
     * @return the localization key string for accessibility description
     */
    public String getAccessibilityKeyForLevel(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.accessibility";
            case MEDIUM -> "menu.maxi.button.medium.accessibility";
            case DIFFICULT -> "menu.maxi.button.difficult.accessibility";
        };
    }

    /**
     * Returns the i18n key used for the display text of a given difficulty level.
     *
     * @param level the difficulty level
     * @return the localization key string for the level display text
     */
    private String getLevelNameKey(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.text";
            case MEDIUM -> "menu.maxi.button.medium.text";
            case DIFFICULT -> "menu.maxi.button.difficult.text";
        };
    }

    /**
     * Returns the property representing the stars completion percentage.
     *
     * @return the stars completion percentage property
     */
    public IntegerProperty percentageProperty() {
        return starsPercentage;
    }

    /**
     * Sets the stars completion percentage.
     *
     * @param value the new percentage value to set
     */
    public void setPercentage(int value) {
        this.starsPercentage.set(value);
    }
}
