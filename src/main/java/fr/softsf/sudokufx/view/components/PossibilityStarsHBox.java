/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.view.components;

import java.text.MessageFormat;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import fr.softsf.sudokufx.enums.I18n;

/**
 * A custom HBox displaying star ratings based on a percentage value (default: 100%), with formatted
 * accessibility and tooltip text. Stars are represented by Unicode characters: - Full star: \ue838
 * - Half star: \ue839 - Empty star: \ue83a
 */
public class PossibilityStarsHBox extends HBox {

    private final IntegerProperty percentage = new SimpleIntegerProperty(100);

    public IntegerProperty getPercentage() {
        return percentage;
    }

    /**
     * Constructor that sets the alignment, style, and visibility of the HBox. It initializes 5
     * stars as Text nodes with appropriate style classes.
     */
    public PossibilityStarsHBox() {
        super();
        setAlignment(Pos.CENTER_RIGHT);
        getStyleClass().add("menuHBoxRightContainer");
        setVisible(false);
        String[] starClasses = {
            "menuButtonLevelStar1",
            "menuButtonLevelStar2",
            "menuButtonLevelStar3",
            "menuButtonLevelStar4",
            "menuButtonLevelStar5"
        };
        for (String starClass : starClasses) {
            Text star = new Text("\uE838");
            star.getStyleClass().addAll("material", "menuButtonLevelStar", starClass);
            getChildren().add(star);
        }
    }

    /**
     * Updates the star ratings based on the given percentage. The percentage determines the number
     * of full, half, and empty stars displayed.
     *
     * @param percentage The percentage value (from 0 to 100) to set the star ratings.
     */
    public void setHBoxPossibilityStarsFromPercentage(int percentage) {
        this.percentage.set(percentage);
        double stars = Math.round(percentage * 0.1) / 2.0;
        List<Text> listTextsStars =
                getChildren().stream()
                        .filter(Text.class::isInstance)
                        .map(Text.class::cast)
                        .toList();
        for (int i = 0; i < listTextsStars.size(); i++) {
            if (stars >= i + 1) {
                listTextsStars.get(i).setText("\ue838"); // Full star
            } else if (stars >= i + 0.5) {
                listTextsStars.get(i).setText("\ue839"); // Half star
            } else {
                listTextsStars.get(i).setText("\ue83a"); // Empty star
            }
        }
    }

    /**
     * Creates a string binding based on the current percentage and visibility. If the element is
     * visible, a ".selected" suffix is added to the key. Optionally appends a role description at
     * the end of the text.
     *
     * @param accessibilityKey Base i18n key for the text.
     * @param appendRoleDescription Whether to append the role description.
     * @return A string binding with the formatted text.
     */
    public StringBinding formattedTextBinding(
            String accessibilityKey, boolean appendRoleDescription) {
        return Bindings.createStringBinding(
                () -> {
                    String key = isVisible() ? accessibilityKey + ".selected" : accessibilityKey;
                    String formattedText =
                            MessageFormat.format(I18n.INSTANCE.getValue(key), percentage.get());
                    return appendRoleDescription
                            ? formattedText
                                    + I18n.INSTANCE.getValue(
                                            "menu.accessibility.role.description.opened")
                            : formattedText;
                },
                percentage);
    }
}
