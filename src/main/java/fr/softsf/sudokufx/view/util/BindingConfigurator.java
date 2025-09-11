/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.view.util;

import java.util.Objects;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import org.springframework.stereotype.Component;

import jakarta.annotation.Nullable;

/**
 * Utility class for configuring common UI binding patterns in JavaFX views. Designed to reduce
 * boilerplate and enforce consistency across UI components. Typically used in MVVM-C architecture
 * to bind ViewModel properties to controls.
 */
@Component
public class BindingConfigurator {

    /**
     * Configures a {@link Button} with optional text, accessibility, tooltip, and role description
     * bindings. Ensures consistent UI behavior and accessibility support.
     *
     * @param button the {@link Button} to configure
     * @param textProperty optional property bound to the button's text
     * @param accessibleTextProperty property bound to the button's accessible text
     * @param tooltipProperty property bound to the button's tooltip text
     * @param roleDescriptionProperty optional property bound to the button's accessible role
     *     description
     * @throws NullPointerException if {@code button}, {@code accessibleTextProperty}, or {@code
     *     tooltipProperty} is {@code null}
     */
    public void configureButton(
            Button button,
            @Nullable ObservableValue<String> textProperty,
            ObservableValue<String> accessibleTextProperty,
            ObservableValue<String> tooltipProperty,
            @Nullable ObservableValue<String> roleDescriptionProperty) {
        Objects.requireNonNull(button, "Button must not be null");
        Objects.requireNonNull(accessibleTextProperty, "Accessible text property must not be null");
        Objects.requireNonNull(tooltipProperty, "Tooltip property must not be null");
        if (textProperty != null) {
            button.textProperty().bind(textProperty);
        }
        button.accessibleTextProperty().bind(accessibleTextProperty);
        button.getTooltip().textProperty().bind(tooltipProperty);
        if (roleDescriptionProperty != null) {
            button.accessibleRoleDescriptionProperty().bind(roleDescriptionProperty);
        }
    }

    /**
     * Configures a {@link ColorPicker} with accessibility, tooltip, role description bindings, and
     * keyboard interaction support.
     *
     * @param colorPicker the {@link ColorPicker} to configure
     * @param accessibleTextProperty property bound to the accessible text
     * @param tooltipProperty property bound to the tooltip text
     * @param roleDescriptionProperty property bound to the accessible role description
     * @throws NullPointerException if any parameter is {@code null}
     */
    public void configureColorPicker(
            ColorPicker colorPicker,
            ObservableValue<String> accessibleTextProperty,
            ObservableValue<String> tooltipProperty,
            ObservableValue<String> roleDescriptionProperty) {
        Objects.requireNonNull(colorPicker, "ColorPicker must not be null");
        Objects.requireNonNull(accessibleTextProperty, "Accessible text property must not be null");
        Objects.requireNonNull(tooltipProperty, "Tooltip property must not be null");
        Objects.requireNonNull(
                roleDescriptionProperty, "Role description property must not be null");
        colorPicker.accessibleTextProperty().bind(accessibleTextProperty);
        colorPicker.getTooltip().textProperty().bind(tooltipProperty);
        colorPicker.accessibleRoleDescriptionProperty().bind(roleDescriptionProperty);
        configureColorPickerKeyboardSupport(colorPicker);
    }

    /**
     * Configures keyboard support for a {@link ColorPicker} with SPACE/ENTER to open and ESCAPE to
     * close the color selection popup.
     *
     * @param colorPicker the {@link ColorPicker} to configure
     * @throws NullPointerException if colorPicker is {@code null}
     */
    private void configureColorPickerKeyboardSupport(ColorPicker colorPicker) {
        Objects.requireNonNull(colorPicker, "ColorPicker must not be null");
        colorPicker.setOnKeyPressed(
                event -> {
                    switch (event.getCode()) {
                        case SPACE:
                        case ENTER:
                            colorPicker.show();
                            break;
                        case ESCAPE:
                            colorPicker.hide();
                            break;
                        default:
                            return;
                    }
                    event.consume();
                });
    }

    /**
     * Configures a {@link Label} with a text binding.
     *
     * @param labelNode the {@link Label} to configure
     * @param textProperty property bound to the label's text content
     * @throws NullPointerException if any parameter is {@code null}
     */
    public void configureLabel(Label labelNode, ObservableValue<String> textProperty) {
        Objects.requireNonNull(labelNode, "Label must not be null");
        Objects.requireNonNull(textProperty, "Text property must not be null");
        labelNode.textProperty().bind(textProperty);
    }

    /**
     * Configures a {@link Text} node with a text binding.
     *
     * @param textNode the {@link Text} node to configure
     * @param textProperty property bound to the text content
     * @throws NullPointerException if any parameter is {@code null}
     */
    public void configureText(Text textNode, ObservableValue<String> textProperty) {
        Objects.requireNonNull(textNode, "Text node must not be null");
        Objects.requireNonNull(textProperty, "Text property must not be null");
        textNode.textProperty().bind(textProperty);
    }

    /**
     * Configures both visibility and managed state of a {@link Node} using a shared boolean
     * binding. Useful for toggling UI elements while preserving layout behavior.
     *
     * @param node the {@link Node} to configure
     * @param visibilityBinding boolean observable controlling visibility and layout management
     * @throws NullPointerException if any parameter is {@code null}
     */
    public void configureVisibilityAndManaged(
            Node node, ObservableValue<Boolean> visibilityBinding) {
        Objects.requireNonNull(node, "Node must not be null");
        Objects.requireNonNull(visibilityBinding, "Visibility binding must not be null");
        node.visibleProperty().bind(visibilityBinding);
        node.managedProperty().bind(visibilityBinding);
    }

    /**
     * Configures a pseudo-class state on a {@link Node}, applying it immediately and updating it
     * reactively. Supports both {@link BooleanProperty} and {@link BooleanBinding}.
     *
     * @param node the {@link Node} to apply the pseudo-class to
     * @param property boolean observable controlling the pseudo-class state
     * @param pseudoClass the {@link PseudoClass} to toggle
     * @throws NullPointerException if any parameter is {@code null}
     */
    public void configurePseudoClassBinding(
            Node node, ObservableValue<Boolean> property, PseudoClass pseudoClass) {
        Objects.requireNonNull(node, "Node must not be null");
        Objects.requireNonNull(property, "Boolean property must not be null");
        Objects.requireNonNull(pseudoClass, "PseudoClass must not be null");
        property.addListener(
                (obs, oldVal, newVal) -> node.pseudoClassStateChanged(pseudoClass, newVal));
        node.pseudoClassStateChanged(pseudoClass, property.getValue());
    }
}
