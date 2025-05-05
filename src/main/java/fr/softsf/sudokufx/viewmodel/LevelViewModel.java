package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.enums.DifficultyLevel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.springframework.stereotype.Component;

/**
 * ViewModel responsible for managing the selected difficulty level and stars percentage.
 * This class exposes properties for binding the difficulty level and the percentage of stars.
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
