/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import fr.softsf.sudokufx.exceptions.ExceptionTools;

/**
 * ViewModel responsible for managing the active menu in the application. It provides functionality
 * to set and retrieve the active menu, which can be bound to the UI to control which menu is
 * visible.
 */
public class ActiveMenuOrSubmenuViewModel {

    /** Enum representing the different possible menus */
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

    public ObjectProperty<ActiveMenu> getActiveMenu() {
        return activeMenu;
    }

    /**
     * Sets the active menu.
     *
     * @param menu The menu to set as active.
     * @throws IllegalArgumentException if the menu is null.
     */
    public void setActiveMenu(ActiveMenu menu) {
        if (menu == null) {
            ExceptionTools.INSTANCE.logAndThrowIllegalArgument(
                    "ActiveMenu cannot be null. Please provide a valid menu value.");
        }
        activeMenu.set(menu);
    }
}
