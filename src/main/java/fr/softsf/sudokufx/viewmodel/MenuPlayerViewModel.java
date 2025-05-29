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
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.viewmodel.shared.CurrentPlayerState;

/**
 * ViewModel for managing player menu UI state and accessibility texts.
 *
 * <p>Holds an observable list of players, the currently selected player, and provides localized
 * StringBindings for UI accessibility, tooltips, role descriptions, and button texts.
 *
 * <p>Uses I18n singleton for localization with automatic updates on locale or selection changes.
 */
@Component
public class MenuPlayerViewModel {

    private final CurrentPlayerState currentPlayerState;

    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";

    private final ObservableList<PlayerDto> players = FXCollections.observableArrayList();
    private final ObjectProperty<PlayerDto> selectedPlayer = new SimpleObjectProperty<>();

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

    public MenuPlayerViewModel(CurrentPlayerState currentPlayerState) {
        this.currentPlayerState = currentPlayerState;

        playerAccessibleText =
                createFormattedBinding("menu.player.button.player.accessibility", this::playerName);
        playerTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.player.button.player.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        playerRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);

        maxiPlayerTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.player.button.player.accessibility",
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
                selectedPlayer);
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
                selectedPlayer);
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

    /** Returns the name of the currently selected player or empty if none. */
    private String playerName() {
        PlayerDto p = selectedPlayer.get();
        return (p != null) ? p.name() : "";
    }

    /** Loads test players into the observable list. */
    private void loadPlayers() {
        players.clear();
        players.add(currentPlayerState.getCurrentPlayer());
        for (int i = 1; i <= 50; i++) {
            players.add(generatePlayerForTests(i + "AAAAAAAAAAAAAAAAAAAAAAAAA"));
        }
    }

    /** Sets the selected player to the one marked as selected or first in the list. */
    private void setSelectedPlayer() {
        if (!players.isEmpty()) {
            selectedPlayer.set(currentPlayerState.getCurrentPlayer());
            //            players.stream()
            //                    .filter(PlayerDto::isselected)
            //                    .findFirst()
            //                    .ifPresentOrElse(
            //                            selectedPlayer::set, () ->
            // selectedPlayer.set(players.getFirst()));
        }
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
                new BackgroundDto(1L, "#3498db", null, false),
                new MenuDto((byte) 1, (byte) 1),
                name,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public ObservableList<PlayerDto> getPlayers() {
        return players;
    }

    public ObjectProperty<PlayerDto> selectedPlayerProperty() {
        return selectedPlayer;
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
