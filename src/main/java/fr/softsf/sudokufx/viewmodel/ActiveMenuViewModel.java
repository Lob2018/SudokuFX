package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * ViewModel responsible for managing the active menu in the application.
 * It provides functionality to set and retrieve the active menu, which can be bound to the UI
 * to control which menu is visible.
 */
@Getter
@Component
public class ActiveMenuViewModel {

    /**
     * Enum representing the different possible menus
     */
    public enum ActiveMenu {
        NONE, HIDDEN, MINI, MAXI, PLAYER, SOLVE, BACKUP, BACKGROUND
    }

    private final ObjectProperty<ActiveMenu> activeMenu = new SimpleObjectProperty<>(ActiveMenu.MINI);

    /**
     * Sets the active menu.
     *
     * @param menu The menu to set as active.
     */
    public void setActiveMenu(ActiveMenu menu) {
        activeMenu.set(menu);
    }
}


