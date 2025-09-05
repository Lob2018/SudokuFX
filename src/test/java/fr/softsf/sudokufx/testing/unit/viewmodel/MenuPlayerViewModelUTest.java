/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.viewmodel;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.dto.MenuDto;
import fr.softsf.sudokufx.dto.OptionsDto;
import fr.softsf.sudokufx.dto.PlayerDto;
import fr.softsf.sudokufx.dto.PlayerLanguageDto;
import fr.softsf.sudokufx.service.business.PlayerService;
import fr.softsf.sudokufx.viewmodel.MenuPlayerViewModel;
import fr.softsf.sudokufx.viewmodel.state.PlayerStateHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class MenuPlayerViewModelUTest {

    private Locale originalLocale;
    private PlayerStateHolder playerStateHolder;
    private MenuPlayerViewModel viewModel;

    @BeforeEach
    void setUp() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        PlayerService playerServiceMock = mock(PlayerService.class);
        PlayerDto testPlayer =
                new PlayerDto(
                        42L,
                        new PlayerLanguageDto(1L, "FR"),
                        new OptionsDto(1L, "#ffffff", "", "", false, true, true),
                        new MenuDto((byte) 1, (byte) 1),
                        null,
                        "TestPlayer",
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        when(playerServiceMock.getPlayer()).thenReturn(testPlayer);
        playerStateHolder = new PlayerStateHolder(playerServiceMock);
        viewModel = new MenuPlayerViewModel(playerStateHolder);
    }

    @AfterEach
    void tearDown() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void allBindingsShouldReturnExpectedI18nValues() {
        Map<StringBinding, Supplier<String>> bindingToExpectedSupplier =
                getStringBindingStringMap(viewModel);
        for (var entry : bindingToExpectedSupplier.entrySet()) {
            StringBinding binding = entry.getKey();
            String expected = entry.getValue().get();
            String actual = binding.get();
            assertEquals(
                    expected, actual, "Binding should return correct value before locale change");
        }
        I18n.INSTANCE.setLocaleBundle("EN");
        for (var entry : bindingToExpectedSupplier.entrySet()) {
            StringBinding binding = entry.getKey();
            String expected = entry.getValue().get();
            String actual = binding.get();
            assertEquals(
                    expected, actual, "Binding should return correct value after locale change");
        }
    }

    private static Map<StringBinding, Supplier<String>> getStringBindingStringMap(
            MenuPlayerViewModel vm) {
        Map<StringBinding, Supplier<String>> map = new HashMap<>();
        Supplier<String> playerNameSupplier = () -> vm.selectedPlayerProperty().get().name();
        map.put(
                vm.playerAccessibleTextProperty(),
                () ->
                        MessageFormat.format(
                                I18n.INSTANCE.getValue("menu.player.button.player.accessibility"),
                                playerNameSupplier.get()));
        map.put(
                vm.playerTooltipProperty(),
                () ->
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.player.button.player.accessibility"),
                                        playerNameSupplier.get())
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.opened"));
        map.put(
                vm.playerRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.opened"));

        map.put(
                vm.maxiPlayerTooltipProperty(),
                () ->
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.player.button.player.accessibility"),
                                        playerNameSupplier.get())
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.closed"));
        map.put(
                vm.maxiPlayerRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.closed"));
        map.put(
                vm.editAccessibleTextProperty(),
                () ->
                        MessageFormat.format(
                                I18n.INSTANCE.getValue("menu.player.button.edit.accessibility"),
                                playerNameSupplier.get()));
        map.put(
                vm.editTooltipProperty(),
                () ->
                        MessageFormat.format(
                                        I18n.INSTANCE.getValue(
                                                "menu.player.button.edit.accessibility"),
                                        playerNameSupplier.get())
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.editRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.newAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility"));
        map.put(
                vm.newTooltipProperty(),
                () ->
                        I18n.INSTANCE.getValue("menu.player.button.new.player.accessibility")
                                + I18n.INSTANCE.getValue(
                                        "menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.newTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.player.button.new.player.text"));
        map.put(
                vm.newRoleDescriptionProperty(),
                () -> I18n.INSTANCE.getValue("menu.accessibility.role.description.submenu.option"));
        map.put(
                vm.reduceAccessibleTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        map.put(
                vm.reduceTooltipProperty(),
                () -> I18n.INSTANCE.getValue("menu.player.button.reduce.accessibility"));
        map.put(
                vm.reduceTextProperty(),
                () -> I18n.INSTANCE.getValue("menu.player.button.reduce.text"));
        map.put(
                vm.cellButtonAccessibleTextProperty(),
                () ->
                        I18n.INSTANCE.getValue(
                                "menu.player.button.new.player.cell.delete.accessibility"));
        map.put(
                vm.cellConfirmationTitleProperty(),
                () ->
                        I18n.INSTANCE.getValue(
                                "menu.player.button.new.player.dialog.confirmation.title"));
        map.put(
                vm.cellConfirmationMessageProperty(),
                () ->
                        I18n.INSTANCE.getValue(
                                "menu.player.button.new.player.dialog.confirmation.message"));
        return map;
    }

    @Test
    void givenViewModel_whenInitialized_thenPlayersLoadedAndBindingsNotNull() {
        ObservableList<PlayerDto> players = viewModel.getPlayers();
        assertEquals(51, players.size());
        assertNotNull(viewModel.playerAccessibleTextProperty());
        assertNotNull(viewModel.editTooltipProperty());
        assertNotNull(viewModel.newTextProperty());
        assertNotNull(viewModel.cellConfirmationMessageProperty());
    }

    @Test
    void givenCurrentPlayer_whenLocaleChanges_thenBindingUpdates() {
        String originalText = viewModel.playerAccessibleTextProperty().get();
        I18n.INSTANCE.setLocaleBundle("EN");
        String updatedText = viewModel.playerAccessibleTextProperty().get();
        assertNotEquals(originalText, updatedText);
    }

    @Test
    void givenNewSelectedPlayer_whenChanged_thenBindingsUpdate() {
        PlayerDto newPlayer =
                new PlayerDto(
                        999L,
                        new PlayerLanguageDto(2L, "EN"),
                        new OptionsDto(2L, "#123456", "", "", false, true, true),
                        new MenuDto((byte) 2, (byte) 2),
                        null,
                        "NewTestPlayer",
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now());
        playerStateHolder.currentPlayerProperty().set(newPlayer);
        String bindingValue = viewModel.playerAccessibleTextProperty().get();
        assertTrue(bindingValue.contains("NewTestPlayer"));
    }
}
