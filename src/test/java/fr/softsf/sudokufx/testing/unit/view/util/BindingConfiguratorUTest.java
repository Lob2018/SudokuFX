/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.view.util;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.view.util.BindingConfigurator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class BindingConfiguratorUTest {

    private final BindingConfigurator configurator = new BindingConfigurator();

    @Test
    void givenButtonAndBindings_whenConfigureButton_thenPropertiesAreBound() {
        Button button = new Button();
        button.setTooltip(new Tooltip());
        SimpleStringProperty textProp = new SimpleStringProperty("Play");
        SimpleStringProperty accessibleProp = new SimpleStringProperty("Play button");
        SimpleStringProperty tooltipProp = new SimpleStringProperty("Click to play");
        SimpleStringProperty roleDescProp = new SimpleStringProperty("Main action");
        configurator.configureButton(button, textProp, accessibleProp, tooltipProp, roleDescProp);
        assertEquals("Play", button.getText());
        assertEquals("Play button", button.getAccessibleText());
        assertEquals("Click to play", button.getTooltip().getText());
        assertEquals("Main action", button.getAccessibleRoleDescription());
    }

    @Test
    void givenLabelAndTextBinding_whenConfigureLabel_thenTextIsBoundAndReactive() {
        Label label = new Label();
        SimpleStringProperty textProp = new SimpleStringProperty("Hello");
        configurator.configureLabel(label, textProp);
        assertEquals("Hello", label.getText());
        textProp.set("Bonjour");
        assertEquals("Bonjour", label.getText());
    }

    @Test
    void givenTextNodeAndTextBinding_whenConfigureText_thenTextIsBoundAndReactive() {
        Text textNode = new Text();
        SimpleStringProperty textProp = new SimpleStringProperty("42");
        configurator.configureText(textNode, textProp);
        assertEquals("42", textNode.getText());
        textProp.set("43");
        assertEquals("43", textNode.getText());
    }

    @Test
    void
            givenNodeAndVisibilityBinding_whenConfigureVisibilityAndManaged_thenNodeVisibilityFollowsBinding() {
        Node node = new Label();
        SimpleBooleanProperty visibleProp = new SimpleBooleanProperty(true);
        configurator.configureVisibilityAndManaged(node, visibleProp);
        assertTrue(node.isVisible());
        assertTrue(node.isManaged());
        visibleProp.set(false);
        assertFalse(node.isVisible());
        assertFalse(node.isManaged());
    }

    @Test
    void
            givenNodeAndBooleanBinding_whenConfigurePseudoClassBinding_thenPseudoClassStateFollowsBinding() {
        Node node = new Label();
        SimpleBooleanProperty activeProp = new SimpleBooleanProperty(true);
        PseudoClass pseudoClass = PseudoClass.getPseudoClass("active");
        configurator.configurePseudoClassBinding(node, activeProp, pseudoClass);
        assertTrue(node.getPseudoClassStates().contains(pseudoClass));
        activeProp.set(false);
        assertFalse(node.getPseudoClassStates().contains(pseudoClass));
    }

    @Test
    void givenNullButton_whenConfigureButton_thenThrowsNullPointerException() {
        SimpleStringProperty accessibleProp = new SimpleStringProperty("Accessible");
        SimpleStringProperty tooltipProp = new SimpleStringProperty("Tooltip");
        assertThrows(
                NullPointerException.class,
                () -> configurator.configureButton(null, null, accessibleProp, tooltipProp, null));
    }

    @Test
    void givenColorPickerAndBindings_whenConfigureColorPicker_thenPropertiesAreBound() {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setTooltip(new Tooltip());
        SimpleStringProperty accessibleProp = new SimpleStringProperty("Color picker");
        SimpleStringProperty tooltipProp = new SimpleStringProperty("Choose a color");
        SimpleStringProperty roleDescProp = new SimpleStringProperty("Color selection");
        configurator.configureColorPicker(colorPicker, accessibleProp, tooltipProp, roleDescProp);
        assertEquals("Color picker", colorPicker.getAccessibleText());
        assertEquals("Choose a color", colorPicker.getTooltip().getText());
        assertEquals("Color selection", colorPicker.getAccessibleRoleDescription());
    }

    @Test
    void givenColorPicker_whenConfigureColorPicker_thenKeyboardEventHandlerIsSet() {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setTooltip(new Tooltip());
        SimpleStringProperty accessibleProp = new SimpleStringProperty("Color picker");
        SimpleStringProperty tooltipProp = new SimpleStringProperty("Choose a color");
        SimpleStringProperty roleDescProp = new SimpleStringProperty("Color selection");
        configurator.configureColorPicker(colorPicker, accessibleProp, tooltipProp, roleDescProp);
        assertNotNull(colorPicker.getOnKeyPressed());
    }

    @Test
    void givenColorPicker_whenConfigureColorPicker_thenKeyboardEventsDoNotThrowExceptions(
            FxRobot robot) {
        final ColorPicker colorPicker = new ColorPicker();
        SimpleStringProperty accessibleProp = new SimpleStringProperty("Color picker");
        SimpleStringProperty tooltipProp = new SimpleStringProperty("Choose a color");
        SimpleStringProperty roleDescProp = new SimpleStringProperty("Color selection");
        robot.interact(
                () -> {
                    colorPicker.setTooltip(new Tooltip());
                    configurator.configureColorPicker(
                            colorPicker, accessibleProp, tooltipProp, roleDescProp);
                });
        KeyEvent spaceKeyEvent =
                new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "", KeyCode.SPACE, false, false, false, false);
        KeyEvent enterKeyEvent =
                new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
        KeyEvent escapeKeyEvent =
                new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
        KeyEvent tabKeyEvent =
                new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false, false, false, false);
        assertDoesNotThrow(
                () ->
                        robot.interact(
                                () -> {
                                    colorPicker.fireEvent(spaceKeyEvent);
                                    colorPicker.fireEvent(enterKeyEvent);
                                    colorPicker.fireEvent(escapeKeyEvent);
                                    colorPicker.fireEvent(tabKeyEvent);
                                }));
    }
}
