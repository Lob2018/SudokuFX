/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.view.component;

import java.text.MessageFormat;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;

/**
 * A reusable JavaFX HBox component that displays a star rating based on a percentage (0–100).
 *
 * <p>Uses Unicode Material Design icons:
 *
 * <ul>
 *   <li>Full star: {@code \uE838}
 *   <li>Half star: {@code \uE839}
 *   <li>Empty star: {@code \uE83A}
 * </ul>
 *
 * <p>This component is intended to behave like a visual-only control (e.g., a ProgressBar). It
 * encapsulates star rendering and provides dynamic, localized accessibility and tooltip bindings
 * based on the current percentage and locale. Visibility also affects the accessible key.
 *
 * <p>MVVM-compliant if the percentage property is updated externally; the component does not modify
 * its bound value.
 */
public final class PossibilityStarsHBox extends HBox {
    private static final double STAR_DIVISOR = 0.1;
    private static final double HALF_STAR_THRESHOLD = 0.5;
    private final IntegerProperty percentage = new SimpleIntegerProperty(100);

    /**
     * Returns the percentage property (0–100) used to calculate the star rating display.
     *
     * <p>This property should be updated externally to reflect star rating changes.
     *
     * @return the percentage property
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public IntegerProperty getPercentage() {
        return percentage;
    }

    /**
     * Creates the component with right-aligned layout, 5 star icons, and default styles. Initially
     * hidden and displaying full stars.
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
        percentage.addListener(
                (obs, oldVal, newVal) -> setHBoxPossibilityStarsFromPercentage(newVal.intValue()));
        setHBoxPossibilityStarsFromPercentage(percentage.get());
    }

    /**
     * Updates the star icons to reflect the given percentage (0–100), rounding to the nearest
     * half-star.
     *
     * <p>This method does not modify the {@code percentage} property itself. It assumes that
     * updates to the star display are triggered via listeners on the {@code percentage} property.
     *
     * @param percentage the rating percentage to display
     */
    private void setHBoxPossibilityStarsFromPercentage(int percentage) {
        double stars = Math.round(percentage * STAR_DIVISOR) / 2.0;
        List<Text> starsNodes =
                getChildren().stream()
                        .filter(Text.class::isInstance)
                        .map(Text.class::cast)
                        .toList();
        for (int i = 0; i < starsNodes.size(); i++) {
            if (stars >= i + 1) {
                starsNodes.get(i).setText("\uE838"); // full star
            } else if (stars >= i + HALF_STAR_THRESHOLD) {
                starsNodes.get(i).setText("\uE839"); // half star
            } else {
                starsNodes.get(i).setText("\uE83A"); // empty star
            }
        }
    }

    /**
     * Returns a StringBinding for a localized, formatted accessibility or tooltip text.
     * Automatically updates on percentage or locale change.
     *
     * @param accessibilityKey the base i18n key (e.g., {@code
     *     "menu.solve.button.solve.accessibility"})
     * @param appendRoleDescription whether to append a localized role description (e.g., "opened")
     * @return a StringBinding with the formatted and localized text
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
                percentage,
                I18n.INSTANCE.localeProperty());
    }
}
