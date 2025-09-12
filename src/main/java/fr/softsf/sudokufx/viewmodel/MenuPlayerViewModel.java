/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
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

    private static final int TEST_NUMBER_LOADED_PLAYERS = 50;
    private static final String MENU_PLAYER_BUTTON_PLAYER_ACCESSIBILITY =
            "menu.player.button.player.accessibility";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";

    private final PlayerStateHolder playerStateHolder;

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

    public MenuPlayerViewModel(PlayerStateHolder playerStateHolder) {
        this.playerStateHolder = playerStateHolder;

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
                        "menu.accessibility.role.description.submenu.option");
        editRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");

        newAccessibleText = createStringBinding("menu.player.button.new.player.accessibility");
        newTooltip = createAppendedStringBinding();
        newText = createStringBinding("menu.player.button.new.player.text");
        newRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");

        reduceAccessibleText = createStringBinding("menu.player.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.player.button.reduce.accessibility");
        reduceText = createStringBinding("menu.player.button.reduce.text");

        cellButtonAccessibleText =
                createStringBinding("menu.player.button.new.player.cell.delete.accessibility");
        cellConfirmationTitle =
                createStringBinding("menu.player.button.new.player.dialog.confirmation.title");
        cellConfirmationMessage =
                createStringBinding("menu.player.button.new.player.dialog.confirmation.message");

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
                                        "menu.accessibility.role.description.submenu.option"),
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
        for (int i = 1; i <= TEST_NUMBER_LOADED_PLAYERS; i++) {
            players.add(generatePlayerForTests(i + "AAAAAAAAAAAAAAAAAAAAAAAAA"));
        }
    }

    /** Sets the selected player to the one marked as selected or first in the list. */
    private void setSelectedPlayer() {
        if (players.isEmpty()) {
            return;
        }
        playerStateHolder.currentPlayerProperty().set(playerStateHolder.getCurrentPlayer());
    }

    /**
     * Generates a sample PlayerDto instance for testing purposes.
     *
     * @param name the player name
     * @return a PlayerDto populated with test data and the specified name
     */
    private PlayerDto generatePlayerForTests(String name) {
        return new PlayerDto(
                1L,
                new PlayerLanguageDto(1L, "FR"),
                new OptionsDto(1L, "#3498db", "", "", true, true),
                new MenuDto((byte) 1, (byte) 1),
                null,
                name,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public ObservableList<PlayerDto> getPlayers() {
        return players;
    }

    public ObjectProperty<PlayerDto> selectedPlayerProperty() {
        return playerStateHolder.currentPlayerProperty();
    }

    public StringBinding playerAccessibleTextProperty() {
        return playerAccessibleText;
    }

    public StringBinding playerTooltipProperty() {
        return playerTooltip;
    }

    public StringBinding playerRoleDescriptionProperty() {
        return playerRoleDescription;
    }

    public StringBinding maxiPlayerTooltipProperty() {
        return maxiPlayerTooltip;
    }

    public StringBinding maxiPlayerRoleDescriptionProperty() {
        return maxiPlayerRoleDescription;
    }

    public StringBinding editAccessibleTextProperty() {
        return editAccessibleText;
    }

    public StringBinding editTooltipProperty() {
        return editTooltip;
    }

    public StringBinding editRoleDescriptionProperty() {
        return editRoleDescription;
    }

    public StringBinding newAccessibleTextProperty() {
        return newAccessibleText;
    }

    public StringBinding newTooltipProperty() {
        return newTooltip;
    }

    public StringBinding newTextProperty() {
        return newText;
    }

    public StringBinding newRoleDescriptionProperty() {
        return newRoleDescription;
    }

    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    public StringBinding reduceTextProperty() {
        return reduceText;
    }

    public StringBinding cellButtonAccessibleTextProperty() {
        return cellButtonAccessibleText;
    }

    public StringBinding cellConfirmationTitleProperty() {
        return cellConfirmationTitle;
    }

    public StringBinding cellConfirmationMessageProperty() {
        return cellConfirmationMessage;
    }
}
