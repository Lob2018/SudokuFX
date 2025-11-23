/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Player Entity Tests")
class PlayerUTest {
    private PlayerLanguage mockPlayerLanguage;
    private Options mockOptions;
    private Menu mockMenu;
    private Game mockGame1;
    private Game mockGame2;
    private Instant testNow;

    @BeforeEach
    void setUp() {
        mockPlayerLanguage = mock(PlayerLanguage.class);
        mockOptions = mock(Options.class);
        mockMenu = mock(Menu.class);
        mockGame1 = mock(Game.class);
        mockGame2 = mock(Game.class);
        testNow = Instant.now();

        when(mockPlayerLanguage.getPlayerlanguageid()).thenReturn(1L);
        when(mockOptions.getOptionsid()).thenReturn(1L);
        when(mockMenu.getMenuid()).thenReturn((byte) 1);
    }

    @Nested
    @DisplayName("Constructor and Builder Tests")
    class ConstructorAndBuilderTests {
        @Test
        @DisplayName("Given all fields when building Player then all fields are set")
        void givenAllFields_whenBuildPlayer_thenAllFieldsAreSet() {
            Set<Game> games = new LinkedHashSet<>();
            games.add(mockGame1);
            games.add(mockGame2);

            Player player =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .games(games)
                            .name("John")
                            .selected(true)
                            .createdat(testNow)
                            .updatedat(testNow)
                            .build();

            assertEquals(1L, player.getPlayerid());
            assertEquals(mockPlayerLanguage, player.getPlayerlanguageid());
            assertEquals(mockOptions, player.getOptionsid());
            assertEquals(mockMenu, player.getMenuid());
            assertEquals(games, player.getGames());
            assertEquals("John", player.getName());
            assertTrue(player.getSelected());
            assertEquals(testNow, player.getCreatedat());
            assertEquals(testNow, player.getUpdatedat());
        }

        @Test
        @DisplayName("Given null games when building Player then games is initialized as empty")
        void givenNullGames_whenBuildPlayer_thenGamesIsInitializedAsEmpty() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .games(null)
                            .build();
            assertNotNull(player.getGames());
            assertTrue(player.getGames().isEmpty());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        @Test
        @DisplayName("Given same Player reference when comparing then they are equal")
        @SuppressWarnings("EqualsWithItself")
        void givenSamePlayerReference_whenComparing_thenTheyAreEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertEquals(p1, p1);
        }

        @Test
        @DisplayName("Given Player and non-Player object when comparing then they are not equal")
        void givenPlayerAndNonPlayerObject_whenComparing_thenTheyAreNotEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Object notAPlayer = new Object();
            boolean result = p1.equals(notAPlayer);
            assertFalse(result, "Player.equals should return false for non-Player objects");
        }

        @Test
        @DisplayName("Given two Players with same ID when comparing then they are equal")
        void givenTwoPlayersWithSameId_whenComparing_thenTheyAreEqual() {
            Instant fixedTime = LocalDateTime.of(1, 1, 1, 1, 1, 1, 1).toInstant(ZoneOffset.UTC);
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .createdat(fixedTime)
                            .updatedat(fixedTime)
                            .build();
            Player p2 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .createdat(fixedTime)
                            .updatedat(fixedTime)
                            .build();
            assertEquals(p1, p2);
            assertEquals(p1.hashCode(), p2.hashCode());
        }

        @Test
        @DisplayName("Given two Players with different IDs when comparing then they are not equal")
        void givenTwoPlayersWithDifferentIds_whenComparing_thenTheyAreNotEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Player p2 =
                    Player.builder()
                            .playerid(2L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("Jane")
                            .build();
            assertNotEquals(p1, p2);
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        @Test
        @DisplayName(
                "Given null PlayerLanguage when setting playerlanguageid then NullPointerException"
                        + " is thrown")
        @SuppressWarnings("ConstantConditions")
        void
                givenNullPlayerLanguage_whenSettingPlayerlanguageid_thenNullPointerExceptionIsThrown() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertThrows(NullPointerException.class, () -> player.setPlayerlanguageid(null));
        }

        @Test
        @DisplayName(
                "Given valid PlayerLanguage when setting playerlanguageid then field is updated")
        void givenValidPlayerLanguage_whenSettingPlayerlanguageid_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            PlayerLanguage newPlayerLanguage = mock(PlayerLanguage.class);
            when(newPlayerLanguage.getPlayerlanguageid()).thenReturn(2L);
            player.setPlayerlanguageid(newPlayerLanguage);
            assertEquals(newPlayerLanguage, player.getPlayerlanguageid());
        }

        @Test
        @DisplayName(
                "Given null Options when setting optionsid then NullPointerException is"
                        + " thrown")
        @SuppressWarnings("ConstantConditions")
        void givenNullOptions_whenSettingOptionsid_thenNullPointerExceptionIsThrown() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertThrows(NullPointerException.class, () -> player.setOptionsid(null));
        }

        @Test
        @DisplayName("Given null Menu when setting menuid then NullPointerException is thrown")
        @SuppressWarnings("ConstantConditions")
        void givenNullMenu_whenSettingMenuid_thenNullPointerExceptionIsThrown() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertThrows(NullPointerException.class, () -> player.setMenuid(null));
        }

        @Test
        @DisplayName("Given null name when setting name then NullPointerException is thrown")
        @SuppressWarnings("ConstantConditions")
        void givenNullName_whenSettingName_thenNullPointerExceptionIsThrown() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertThrows(NullPointerException.class, () -> player.setName(null));
        }

        @Test
        @DisplayName(
                "Given null updatedat when setting updatedat then NullPointerException is thrown")
        @SuppressWarnings("ConstantConditions")
        void givenNullUpdatedat_whenSettingUpdatedat_thenNullPointerExceptionIsThrown() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertThrows(NullPointerException.class, () -> player.setUpdatedat(null));
        }

        @Test
        @DisplayName("Given null games when setting games then games is initialized as empty")
        void givenNullGames_whenSettingGames_thenGamesIsInitializedAsEmpty() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            player.setGames(null);
            assertNotNull(player.getGames());
            assertTrue(player.getGames().isEmpty());
        }

        @Test
        @DisplayName("Given valid Options when setting optionsid then field is updated")
        void givenValidOptions_whenSettingOptionsid_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Options newOptions = mock(Options.class);
            when(newOptions.getOptionsid()).thenReturn(2L);
            player.setOptionsid(newOptions);
            assertEquals(newOptions, player.getOptionsid());
        }

        @Test
        @DisplayName("Given valid Menu when setting menuid then field is updated")
        void givenValidMenu_whenSettingMenuid_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Menu newMenu = mock(Menu.class);
            when(newMenu.getMenuid()).thenReturn((byte) 2);
            player.setMenuid(newMenu);
            assertEquals(newMenu, player.getMenuid());
        }

        @Test
        @DisplayName("Given valid name when setting name then field is updated")
        void givenValidName_whenSettingName_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            player.setName("Alice");
            assertEquals("Alice", player.getName());
        }

        @Test
        @DisplayName("Given selected flag when setting selected then field is updated")
        void givenIsselectedFlag_whenSettingIsselected_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .selected(false)
                            .build();
            player.setSelected(true);
            assertTrue(player.getSelected());
        }

        @Test
        @DisplayName("Given valid updatedat when setting updatedat then field is updated")
        void givenValidUpdatedat_whenSettingUpdatedat_thenFieldIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Instant newUpdatedAt =
                    LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
            player.setUpdatedat(newUpdatedAt);
            assertEquals(newUpdatedAt, player.getUpdatedat());
        }
    }

    @Nested
    @DisplayName("Games Collection Tests")
    class GamesCollectionTests {
        @Test
        @DisplayName("Given Player when constructed then games is never null")
        void givenPlayer_whenConstructed_thenGamesIsNeverNull() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertNotNull(player.getGames());
        }

        @Test
        @DisplayName("Given Player when adding a game then games collection is updated")
        void givenPlayer_whenAddingGame_thenGamesCollectionIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertTrue(player.getGames().isEmpty());
            player.getGames().add(mockGame1);
            assertEquals(1, player.getGames().size());
            assertTrue(player.getGames().contains(mockGame1));
        }

        @Test
        @DisplayName("Given Player when removing a game then games collection is updated")
        void givenPlayer_whenRemovingGame_thenGamesCollectionIsUpdated() {
            Set<Game> games = new LinkedHashSet<>();
            games.add(mockGame1);
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .games(games)
                            .build();
            assertEquals(1, player.getGames().size());
            player.getGames().remove(mockGame1);
            assertTrue(player.getGames().isEmpty());
        }

        @Test
        @DisplayName("Given Player when adding null to games then null is added")
        void givenPlayer_whenAddingNullToGames_thenNullIsAdded() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertDoesNotThrow(() -> player.getGames().add(null));
            assertTrue(player.getGames().contains(null));
        }

        @Test
        @DisplayName(
                "Given Player when setting games with a new collection then collection is replaced")
        void givenPlayer_whenSettingGamesWithNewCollection_thenCollectionIsReplaced() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Set<Game> newGames = new LinkedHashSet<>();
            newGames.add(mockGame2);
            player.setGames(newGames);
            assertEquals(newGames, player.getGames());
            assertEquals(1, player.getGames().size());
            assertTrue(player.getGames().contains(mockGame2));
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        @DisplayName("Given Player when calling toString then all fields are included")
        void givenPlayer_whenCallingToString_thenAllFieldsAreIncluded() {
            Player player =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .optionsid(mockOptions)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            String str = player.toString();
            assertTrue(str.contains("John"));
            assertTrue(str.contains(String.valueOf(player.getPlayerid())));
        }
    }
}
