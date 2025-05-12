/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.DifficultyLevel;

/**
 * ViewModel responsible for managing the selected difficulty level and stars percentage. This class
 * exposes properties for binding the difficulty level and the percentage of stars.
 */
@Component
public class LevelViewModel {
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
}
