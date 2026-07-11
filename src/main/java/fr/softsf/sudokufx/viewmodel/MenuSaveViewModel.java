/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import java.util.List;
import java.util.Objects;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.business.GameService;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

/**
 * ViewModel for managing backup menu UI state and accessibility texts.
 *
 * <p>Holds an observable list of saved games, the currently selected backup, and provides localized
 * StringBindings for UI labels, accessibility, tooltips, and role descriptions.
 *
 * <p>Uses I18n singleton for localization with automatic updates on locale changes.
 */
@Component
public final class MenuSaveViewModel {

    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";

    private final PlayerStateHolder playerStateHolder;
    private final GameService gameService;

    private final ObservableList<GameDto> games = FXCollections.observableArrayList();
    private final ObjectProperty<GameDto> selectedBackup = new SimpleObjectProperty<>();

    private final StringBinding saveAccessibleText;
    private final StringBinding saveTooltip;
    private final StringBinding saveRoleDescription;
    private final StringBinding saveText;
    private final StringBinding reduceAccessibleText;
    private final StringBinding reduceTooltip;
    private final StringBinding reduceText;
    private final StringBinding backupAccessibleText;
    private final StringBinding backupRoleDescription;
    private final StringBinding backupText;
    private final StringBinding backupTooltip;
    private final StringBinding maxiBackupAccessibleText;
    private final StringBinding maxiBackupRoleDescription;
    private final StringBinding maxiBackupText;
    private final StringBinding maxiBackupTooltip;
    private final StringBinding cellDeleteAccessibleText;
    private final StringBinding cellConfirmationTitle;
    private final StringBinding cellConfirmationMessage;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "Infrastructure services must be stored by reference to maintain reactive state"
                            + " and binding integrity.")
    public MenuSaveViewModel(PlayerStateHolder playerStateHolder, GameService gameService) {
        this.playerStateHolder = playerStateHolder;
        this.gameService = gameService;
        saveAccessibleText = createStringBinding("menu.save.button.save.accessibility");
        saveTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.save.button.save.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        saveRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        saveText = createStringBinding("menu.save.button.save.text");
        reduceAccessibleText = createStringBinding("menu.save.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.save.button.reduce.accessibility");
        reduceText = createStringBinding("menu.save.button.reduce.text");
        backupAccessibleText = createStringBinding("menu.save.button.backup.accessibility");
        backupRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");
        backupText = createStringBinding("menu.save.button.backup.text");
        backupTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.save.button.backup.accessibility",
                        "menu.accessibility.role.description.submenu.option");
        maxiBackupAccessibleText = createStringBinding("menu.maxi.button.backup.accessibility");
        maxiBackupRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        maxiBackupText = createStringBinding("menu.maxi.button.backup.text");
        maxiBackupTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.maxi.button.backup.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        cellDeleteAccessibleText =
                createStringBinding("menu.save.button.backup.cell.delete.accessibility");
        cellConfirmationTitle =
                createStringBinding("menu.save.button.backup.dialog.confirmation.title");
        cellConfirmationMessage =
                createStringBinding("menu.save.button.backup.dialog.confirmation.message");
        refreshGames();
    }

    /**
     * Creates a simple localized StringBinding for the given key. Updates automatically when the
     * locale changes.
     *
     * @param key the i18n key
     * @return a StringBinding for the localized value
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a localized StringBinding by concatenating the value of two keys. Updates
     * automatically when the locale changes.
     *
     * @param key the base key
     * @param suffixKey the key for the suffix to append
     * @return a StringBinding for the combined localized value
     */
    private StringBinding createFormattedAndConcatenatedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key) + I18n.INSTANCE.getValue(suffixKey),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Refreshes the game list and re-synchronizes the selected backup. Used when the active player
     * changes to update the UI state.
     */
    public void refreshGames() {
        loadGames();
        setSelectedBackup();
    }

    /** Loads games into the observable list. */
    private void loadGames() {
        games.clear();
        PlayerDto player = playerStateHolder.getCurrentPlayer();
        if (player != null) {
            games.add(player.selectedGame());
            List<GameDto> otherGames = gameService.getGames(player.playerid()).stream().toList();
            if (otherGames.isEmpty()) {
                return;
            }
            games.addAll(otherGames);
        }
    }

    /**
     * Sets the selected backup to the one marked as selected, or defaults to the first in the list
     * if none are selected.
     */
    private void setSelectedBackup() {
        if (games.isEmpty()) {
            return;
        }
        games.stream()
                .filter(GameDto::selected)
                .findFirst()
                .ifPresentOrElse(selectedBackup::set, () -> selectedBackup.set(games.getFirst()));
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public ObservableList<GameDto> getGames() {
        return games;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public ObjectProperty<GameDto> selectedBackupProperty() {
        return selectedBackup;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding saveAccessibleTextProperty() {
        return saveAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding saveTooltipProperty() {
        return saveTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding saveRoleDescriptionProperty() {
        return saveRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding saveTextProperty() {
        return saveText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding reduceTextProperty() {
        return reduceText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupAccessibleTextProperty() {
        return backupAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupTooltipProperty() {
        return backupTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupRoleDescriptionProperty() {
        return backupRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding backupTextProperty() {
        return backupText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiBackupAccessibleTextProperty() {
        return maxiBackupAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiBackupTooltipProperty() {
        return maxiBackupTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiBackupRoleDescriptionProperty() {
        return maxiBackupRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiBackupTextProperty() {
        return maxiBackupText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding cellDeleteAccessibleTextProperty() {
        return cellDeleteAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding cellConfirmationTitleProperty() {
        return cellConfirmationTitle;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding cellConfirmationMessageProperty() {
        return cellConfirmationMessage;
    }

    /**
     * Backup the player's game.
     *
     * <p>Creates a copy of the current game state and refreshes the player's game list.
     *
     * @throws NullPointerException if the current player or selected game is null.
     */
    public void createABackup() {
        PlayerDto playerDto =
                Objects.requireNonNull(
                        playerStateHolder.getCurrentPlayer(), "Current player cannot be null");
        GameDto selectedGame =
                Objects.requireNonNull(playerDto.selectedGame(), "Selected game cannot be null");
        gameService.createNewGameWithCurrent(selectedGame, playerDto.playerid());
        playerStateHolder.refreshCurrentPlayer();
        refreshGames();
    }

    /**
     * Restores a specific game backup.
     *
     * <p>Switches the active selection to the provided game and refreshes the inventory.
     *
     * @param gameToRestore the target game to restore; must not be null.
     * @throws NullPointerException if the current player's game context is missing.
     */
    public void restoreABackup(GameDto gameToRestore) {
        GameDto gameDto =
                Objects.requireNonNull(
                        playerStateHolder.getCurrentPlayer().selectedGame(),
                        "Current player's game cannot be null");
        gameService.switchAndSelectNewGame(gameDto.gameid(), gameToRestore.gameid());
        playerStateHolder.refreshCurrentPlayer();
        refreshGames();
    }

    public void deleteABackup(GameDto gameDto) {
        // TODO work in progress (latest selected)
    }
}
