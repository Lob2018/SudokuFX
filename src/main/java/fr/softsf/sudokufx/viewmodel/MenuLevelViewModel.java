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

/** MenuLevelViewModel with business logic (not final) */
@Component
public class MenuLevelViewModel {
    private final ObjectProperty<DifficultyLevel> selectedLevel = new SimpleObjectProperty<>(null);
    private final IntegerProperty starsPercentage = new SimpleIntegerProperty(0);

    public ObjectProperty<DifficultyLevel> getSelectedLevelProperty() {
        return selectedLevel;
    }

    public IntegerProperty getStarsPercentageProperty() {
        return starsPercentage;
    }

    public void setLevel(DifficultyLevel level, int percentage) {
        selectedLevel.set(level);
        starsPercentage.set(percentage);
    }

    public ObjectProperty<DifficultyLevel> selectedLevelProperty() {
        return selectedLevel;
    }

    public StringBinding getLabelText(DifficultyLevel level) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(getLevelNameKey(level)),
                I18n.INSTANCE.localeProperty());
    }

    public StringBinding createAccessibleTextBinding(PossibilityStarsHBox starsBox, String key) {
        return Bindings.createStringBinding(
                () -> starsBox.formattedTextBinding(key, false).get(),
                selectedLevel,
                starsBox.getPercentage(),
                I18n.INSTANCE.localeProperty());
    }

    public BooleanBinding createVisibilityBinding(DifficultyLevel level) {
        return Bindings.createBooleanBinding(() -> selectedLevel.get() == level, selectedLevel);
    }

    public StringBinding getAccessibilityRoleDescriptionSelectedBinding() {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.selected"),
                I18n.INSTANCE.localeProperty());
    }

    public String getLevelAccessibilityKey(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.accessibility";
            case MEDIUM -> "menu.maxi.button.medium.accessibility";
            case DIFFICULT -> "menu.maxi.button.difficult.accessibility";
        };
    }

    private String getLevelNameKey(DifficultyLevel level) {
        return switch (level) {
            case EASY -> "menu.maxi.button.easy.text";
            case MEDIUM -> "menu.maxi.button.medium.text";
            case DIFFICULT -> "menu.maxi.button.difficult.text";
        };
    }
}
