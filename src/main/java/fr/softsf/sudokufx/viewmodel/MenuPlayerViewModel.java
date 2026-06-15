/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;

import org.springframework.stereotype.Component;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.PlayerConstants;
import fr.softsf.sudokufx.common.enums.PlayerNameStatus;
import fr.softsf.sudokufx.common.util.MyRegex;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

/**
 * ViewModel managing player menu UI state and localized accessibility texts.
 *
 * <p>Provides observable players list and reactive StringBindings for UI labels, tooltips, and
 * roles, updated on locale or selection changes.
 *
 * <p>Assumes selected player has a valid non-blank name as enforced upstream.
 */
@Component
public class MenuPlayerViewModel {

    private static final String MENU_PLAYER_BUTTON_PLAYER_ACCESSIBILITY =
            "menu.player.button.player.accessibility";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION =
            "menu.accessibility.role.description.submenu.option";
    public static final String MENU_PLAYER_BUTTON_NEW_PLAYER_TEXT =
            "menu.player.button.new.player.text";

    private final PlayerStateHolder playerStateHolder;
    private final PlayerService playerService;

    private final ObservableList<PlayerDto> players = FXCollections.observableArrayList();

    private final StringBinding maxiPlayerTooltip;
    private final StringBinding maxiPlayerRoleDescription;

    private final StringBinding playerAccessibleText;
    private final StringBinding playerTooltip;
    private final StringBinding playerRoleDescription;

    private final StringBinding editAccessibleText;
    private final StringBinding editTooltip;
    private final StringBinding editRoleDescription;

    private final StringBinding newAccessibleText;
    private final StringBinding newTooltip;
    private final StringBinding newText;
    private final StringBinding newRoleDescription;

    private final StringBinding reduceAccessibleText;
    private final StringBinding reduceTooltip;
    private final StringBinding reduceText;

    private final StringBinding cellButtonAccessibleText;
    private final StringBinding cellConfirmationTitle;
    private final StringBinding cellConfirmationMessage;

    private static final int MAX_NAME_LENGTH = 50;
    private final ReadOnlyObjectWrapper<PlayerNameStatus> playerNameStatus =
            new ReadOnlyObjectWrapper<>(PlayerNameStatus.EMPTY);
    private final StringProperty playerNameInput =
            new SimpleStringProperty(I18n.INSTANCE.getValue(MENU_PLAYER_BUTTON_NEW_PLAYER_TEXT));
    private final ReadOnlyBooleanWrapper editing = new ReadOnlyBooleanWrapper(false);
    private final ReadOnlyBooleanWrapper playerSwitchedSignal = new ReadOnlyBooleanWrapper(false);

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "Infrastructure services must be stored by reference to maintain reactive state"
                            + " and binding integrity.")
    public MenuPlayerViewModel(PlayerStateHolder playerStateHolder, PlayerService playerService) {
        this.playerStateHolder = playerStateHolder;
        this.playerService = playerService;

        playerAccessibleText =
                createFormattedBinding(MENU_PLAYER_BUTTON_PLAYER_ACCESSIBILITY, this::playerName);
        playerTooltip =
                createFormattedAndConcatenatedBinding(
                        MENU_PLAYER_BUTTON_PLAYER_ACCESSIBILITY,
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        playerRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);

        maxiPlayerTooltip =
                createFormattedAndConcatenatedBinding(
                        MENU_PLAYER_BUTTON_PLAYER_ACCESSIBILITY,
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        maxiPlayerRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);

        editAccessibleText =
                createFormattedBinding("menu.player.button.edit.accessibility", this::playerName);
        editTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.player.button.edit.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);
        editRoleDescription =
                createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);

        newAccessibleText = createStringBinding("menu.player.button.new.player.accessibility");
        newTooltip = createAppendedStringBinding();
        newText = createStringBinding(MENU_PLAYER_BUTTON_NEW_PLAYER_TEXT);
        newRoleDescription =
                createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION);

        reduceAccessibleText = createStringBinding("menu.player.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.player.button.reduce.accessibility");
        reduceText = createStringBinding("menu.player.button.reduce.text");

        cellButtonAccessibleText =
                createStringBinding("menu.player.button.new.player.cell.delete.accessibility");
        cellConfirmationTitle =
                createStringBinding("menu.player.button.new.player.dialog.confirmation.title");
        cellConfirmationMessage =
                createStringBinding("menu.player.button.new.player.dialog.confirmation.message");
        playerStateHolder
                .currentPlayerProperty()
                .addListener(
                        (obs, old, newValue) -> {
                            if (newValue == null
                                    || Objects.equals(newValue.playerid(), old.playerid())) {
                                return;
                            }
                            playerService.switchAndSelectNewPlayer(
                                    old.playerid(), newValue.playerid());
                            loadPlayers();
                            playerStateHolder.refreshCurrentPlayer();
                            playerSwitchedSignal.set(!playerSwitchedSignal.get());
                        });
        loadPlayers();
        setSelectedPlayer();
    }

    /**
     * Creates a formatted localized binding with player name argument. Updates when locale or
     * selected player changes.
     */
    private StringBinding createFormattedBinding(String key, Supplier<String> argSupplier) {
        return Bindings.createStringBinding(
                () -> MessageFormat.format(I18n.INSTANCE.getValue(key), argSupplier.get()),
                I18n.INSTANCE.localeProperty(),
                playerStateHolder.currentPlayerProperty());
    }

    /**
     * Creates a localized binding combining a formatted message and suffix. Updates when locale or
     * selected player changes.
     */
    private StringBinding createFormattedAndConcatenatedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> {
                    String name = playerName();
                    return MessageFormat.format(I18n.INSTANCE.getValue(key), name)
                            + I18n.INSTANCE.getValue(suffixKey);
                },
                I18n.INSTANCE.localeProperty(),
                playerStateHolder.currentPlayerProperty());
    }

    /** Creates a simple localized binding for a given key. Updates when locale changes. */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /** Creates a localized binding by concatenating two keys. Updates when locale changes. */
    private StringBinding createAppendedStringBinding() {
        return Bindings.createStringBinding(
                () ->
                        I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility")
                                + I18n.INSTANCE.getValue(
                                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_SUBMENU_OPTION),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Returns the name of the currently selected player.
     *
     * @return the player name, guaranteed to be non-null and not blank
     */
    private String playerName() {
        return playerStateHolder.getCurrentPlayer().name();
    }

    /** Loads players into the observable list. */
    private void loadPlayers() {
        players.clear();
        players.add(playerStateHolder.getCurrentPlayer());
        List<PlayerDto> otherPlayers = playerService.getPlayers().stream().toList();
        if (otherPlayers.isEmpty()) {
            return;
        }
        players.addAll(otherPlayers);
    }

    /** Sets the selected player to the one marked as selected or first in the list. */
    private void setSelectedPlayer() {
        if (players.isEmpty()) {
            return;
        }
        playerStateHolder.currentPlayerProperty().set(playerStateHolder.getCurrentPlayer());
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public ObservableList<PlayerDto> getPlayers() {
        return players;
    }

    public ObjectProperty<PlayerDto> selectedPlayerProperty() {
        return playerStateHolder.currentPlayerProperty();
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerAccessibleTextProperty() {
        return playerAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerTooltipProperty() {
        return playerTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding playerRoleDescriptionProperty() {
        return playerRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiPlayerTooltipProperty() {
        return maxiPlayerTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding maxiPlayerRoleDescriptionProperty() {
        return maxiPlayerRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding editAccessibleTextProperty() {
        return editAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding editTooltipProperty() {
        return editTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding editRoleDescriptionProperty() {
        return editRoleDescription;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding newAccessibleTextProperty() {
        return newAccessibleText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding newTooltipProperty() {
        return newTooltip;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding newTextProperty() {
        return newText;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX properties are intentionally exposed for bindings and listeners;"
                            + " defensive copies break UI reactivity.")
    public StringBinding newRoleDescriptionProperty() {
        return newRoleDescription;
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
    public StringBinding cellButtonAccessibleTextProperty() {
        return cellButtonAccessibleText;
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

    public ReadOnlyObjectProperty<PlayerNameStatus> playerNameStatusProperty() {
        return playerNameStatus.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty playerSwitchedSignalProperty() {
        return playerSwitchedSignal.getReadOnlyProperty();
    }

    /**
     * Returns the property for the new player name input.
     *
     * @return the Property managing the new player name
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification =
                    "JavaFX Property exposure is required for bidirectional binding with the UI;"
                            + " preventing this exposure would break input synchronization.")
    public Property<String> playerNameInputProperty() {
        return playerNameInput;
    }

    public ReadOnlyBooleanProperty editingProperty() {
        return editing.getReadOnlyProperty();
    }

    /** Prepares the text field for a new player. */
    public void prepareNewPlayer() {
        playerNameInput.set("");
        editing.set(true);
    }

    /** Validates and sets the player name into the state holder ONLY on Enter key. */
    public void createNewPlayerByName() {
        if (!editing.get() || playerNameStatus.get() != PlayerNameStatus.VALID) {
            return;
        }
        String finalName = playerNameInput.get().trim();
        playerService.createNewPlayerWithCurrent(playerStateHolder.getCurrentPlayer(), finalName);
        playerStateHolder.refreshCurrentPlayer();
        editing.set(false);
        loadPlayers();
    }

    /** Clears the text field and stops editing when focus is lost. */
    public void cancelNewPlayer() {
        playerNameInput.set(I18n.INSTANCE.getValue(MENU_PLAYER_BUTTON_NEW_PLAYER_TEXT));
        if (!editing.get()) {
            return;
        }
        editing.set(false);
    }

    /**
     * Validates the input player name against formatting and availability constraints. *
     *
     * <p>Updates the status property to:
     *
     * <ul>
     *   <li>{@link PlayerNameStatus#EMPTY}: if text is null or blank.
     *   <li>{@link PlayerNameStatus#INVALID}: if length exceeds 50 or pattern mismatches.
     *   <li>{@link PlayerNameStatus#UNAVAILABLE}: if name matches current player, the anonymous
     *       token {@code "—"}, or an existing player.
     *   <li>{@link PlayerNameStatus#VALID}: if all checks pass.
     * </ul>
     *
     * @param currentText the raw text input from the view
     */
    public void validatePlayerName(String currentText) {
        if (currentText == null || currentText.isBlank()) {
            playerNameStatus.set(PlayerNameStatus.EMPTY);
            return;
        }
        String cleanedText = currentText.trim();
        boolean isValid =
                cleanedText.length() <= MAX_NAME_LENGTH
                        && MyRegex.INSTANCE.getPlayerNamePattern().matcher(cleanedText).matches();
        boolean isAvailable =
                !cleanedText.equalsIgnoreCase(playerStateHolder.getCurrentPlayer().name())
                        && !cleanedText.equals(PlayerConstants.ANONYMOUS_NAME.getValue())
                        && playerService.getPlayers().stream()
                                .noneMatch(p -> p.name().equalsIgnoreCase(cleanedText));
        if (!isValid) {
            playerNameStatus.set(PlayerNameStatus.INVALID);
        } else if (!isAvailable) {
            playerNameStatus.set(PlayerNameStatus.UNAVAILABLE);
        } else {
            playerNameStatus.set(PlayerNameStatus.VALID);
        }
    }

    /**
     * Filters input changes to enforce character and length constraints.
     *
     * @param change the JavaFX text formatter change context
     * @return the permitted change, or null to reject the input alteration
     */
    public TextFormatter.Change filterPlayerNameInput(TextFormatter.Change change) {
        if (!change.isContentChange()) {
            return change;
        }
        String newName = change.getControlNewText();
        if (newName.length() <= MAX_NAME_LENGTH
                && MyRegex.INSTANCE.getPlayerNamePattern().matcher(newName).matches()) {
            return change;
        }
        return null;
    }

    public void deletePlayer(PlayerDto playerDto) {
        playerService.deletePlayer(playerDto.playerid());
        playerStateHolder.refreshCurrentPlayer();
        playerSwitchedSignal.set(!playerSwitchedSignal.get());
        loadPlayers();
    }
}
