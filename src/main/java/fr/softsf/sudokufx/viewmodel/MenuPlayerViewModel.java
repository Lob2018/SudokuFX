/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.dto.*;
import fr.softsf.sudokufx.enums.I18n;

/**
 * ViewModel for the player menu section.
 *
 * <p>Provides internationalized accessible texts, tooltips, and labels for each player-related
 * button. Texts automatically update when the application's locale or player name changes.
 */
@Component
public class MenuPlayerViewModel {

    // Liste observable des joueurs (PlayerDto)
    private final ObservableList<PlayerDto> players = FXCollections.observableArrayList();

    // Joueur sélectionné (PlayerDto)
    private final ObjectProperty<PlayerDto> selectedPlayer = new SimpleObjectProperty<>();

    // Accessibility bindings
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

    public MenuPlayerViewModel() {
        // Binding texte accessible du bouton player, utilise le nom du joueur sélectionné
        playerAccessibleText =
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto p = selectedPlayer.get();
                            String name = (p != null) ? p.name() : "";
                            return MessageFormat.format(
                                    I18n.INSTANCE.getValue(
                                            "menu.player.button.player.accessibility"),
                                    name);
                        },
                        I18n.INSTANCE.localeProperty(),
                        selectedPlayer);

        playerTooltip =
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto p = selectedPlayer.get();
                            String name = (p != null) ? p.name() : "";
                            return MessageFormat.format(
                                            I18n.INSTANCE.getValue(
                                                    "menu.player.button.player.accessibility"),
                                            name)
                                    + I18n.INSTANCE.getValue(
                                            "menu.accessibility.role.description.opened");
                        },
                        I18n.INSTANCE.localeProperty(),
                        selectedPlayer);

        playerRoleDescription = createStringBinding("menu.accessibility.role.description.opened");

        editAccessibleText =
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto p = selectedPlayer.get();
                            String name = (p != null) ? p.name() : "";
                            return MessageFormat.format(
                                    I18n.INSTANCE.getValue("menu.player.button.edit.accessibility"),
                                    name);
                        },
                        I18n.INSTANCE.localeProperty(),
                        selectedPlayer);

        editTooltip =
                Bindings.createStringBinding(
                        () -> {
                            PlayerDto p = selectedPlayer.get();
                            String name = (p != null) ? p.name() : "";
                            return MessageFormat.format(
                                            I18n.INSTANCE.getValue(
                                                    "menu.player.button.edit.accessibility"),
                                            name)
                                    + I18n.INSTANCE.getValue(
                                            "menu.accessibility.role.description.submenu.option");
                        },
                        I18n.INSTANCE.localeProperty(),
                        selectedPlayer);

        editRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");

        newAccessibleText = createStringBinding("menu.player.button.new.player.accessibility");

        newTooltip =
                Bindings.createStringBinding(
                        () ->
                                I18n.INSTANCE.getValue(
                                                "menu.player.button.new.player.accessibility")
                                        + I18n.INSTANCE.getValue(
                                                "menu.accessibility.role.description.submenu.option"),
                        I18n.INSTANCE.localeProperty());

        newText = createStringBinding("menu.player.button.new.player.text");

        newRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");

        reduceAccessibleText = createStringBinding("menu.player.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.player.button.reduce.accessibility");
        reduceText = createStringBinding("menu.player.button.reduce.text");

        // Charger la liste des joueurs
        loadPlayers();
        // sélectionne le joueur actif
        setSelectedPlayer();
    }

    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    // Charge la liste des joueurs (exemple simplifié avec PlayerDto immutables)
    private void loadPlayers() {
        players.clear();
        players.add(generatePlayerForTests("ANONYME", false));
        for (int i = 1; i <= 50; i++) {
            if (i == 30) players.add(generatePlayerForTests(i + "SELECTED !", true));
            else players.add(generatePlayerForTests(i + " AAAAAAAAAAAAAAAAAAAAAAAAA", false));
        }
    }

    private void setSelectedPlayer() {
        if (players.getFirst().isselected()) selectedPlayer.set(players.getFirst());
        else {
            for (PlayerDto p : players) {
                if (p.isselected()) {
                    selectedPlayer.set(p);
                    break;
                }
            }
        }
    }

    PlayerDto generatePlayerForTests(String name, boolean isSelected) {
        return new PlayerDto(
                1L,
                new PlayerLanguageDto(1L, "FR"),
                new BackgroundDto(1L, "#3498db", null, false),
                new MenuDto((byte) 1, (byte) 1),
                Set.of(
                        new GameDto(
                                1L,
                                new GridDto(1L, "0".repeat(81), "1".repeat(810), (byte) 75),
                                null,
                                new GameLevelDto((byte) 1, (byte) 1),
                                true,
                                LocalDateTime.now(),
                                LocalDateTime.now())),
                name,
                isSelected,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    // Propriétés observables

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
}
