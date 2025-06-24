/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * ViewModel component managing the currently active menu or submenu. Provides observable state for
 * UI bindings and enforces non-null values.
 */
@Component
public class ActiveMenuOrSubmenuViewModel {

    /** Enumeration of all possible menus or submenus. */
    public enum ActiveMenu {
        NONE,
        HIDDEN,
        MINI,
        MAXI,
        PLAYER,
        SOLVE,
        BACKUP,
        BACKGROUND
    }

    private final ObjectProperty<ActiveMenu> activeMenu =
            new SimpleObjectProperty<>(ActiveMenu.MAXI);

    /** Returns the observable property representing the active menu. */
    public ObjectProperty<ActiveMenu> getActiveMenu() {
        return activeMenu;
    }

    /**
     * Sets the active menu. If the provided value is {@code null},
     * the default value {@link ActiveMenu#MAXI} is used instead.
     *
     * @param menu the menu to activate, or {@code null} to use the default
     */
    public void setActiveMenu(ActiveMenu menu) {
        activeMenu.set(Objects.requireNonNullElse(menu, ActiveMenu.MAXI));
    }
}
