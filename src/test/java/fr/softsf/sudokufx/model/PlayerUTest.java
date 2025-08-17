/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;
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
    private Background mockBackground;
    private Menu mockMenu;
    private Game mockGame1;
    private Game mockGame2;
    private LocalDateTime testNow;

    @BeforeEach
    void setUp() {
        mockPlayerLanguage = mock(PlayerLanguage.class);
        mockBackground = mock(Background.class);
        mockMenu = mock(Menu.class);
        mockGame1 = mock(Game.class);
        mockGame2 = mock(Game.class);
        testNow = LocalDateTime.now();
        when(mockPlayerLanguage.getPlayerlanguageid()).thenReturn(1L);
        when(mockBackground.getBackgroundid()).thenReturn(1L);
        when(mockMenu.getMenuid()).thenReturn((byte) 1);
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {
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
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .games(games)
                            .name("John")
                            .isselected(true)
                            .createdat(testNow)
                            .updatedat(testNow)
                            .build();
            assertEquals(1L, player.getPlayerid());
            assertEquals(mockPlayerLanguage, player.getPlayerlanguageid());
            assertEquals(mockBackground, player.getBackgroundid());
            assertEquals(mockMenu, player.getMenuid());
            assertEquals(games, player.getGames());
            assertEquals("John", player.getName());
            assertTrue(player.getIsselected());
            assertEquals(testNow, player.getCreatedat());
            assertEquals(testNow, player.getUpdatedat());
        }

        @Test
        @DisplayName("Given minimal fields when building Player then fields are set correctly")
        void givenMinimalFields_whenBuildPlayer_thenFieldsAreSetCorrectly() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertEquals("John", player.getName());
            assertEquals(mockPlayerLanguage, player.getPlayerlanguageid());
            assertEquals(mockBackground, player.getBackgroundid());
            assertEquals(mockMenu, player.getMenuid());
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
                            .backgroundid(mockBackground)
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
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Object notAPlayer = new Object();
            assertNotEquals(p1, notAPlayer);
        }

        @Test
        @DisplayName("Given Player and null when comparing then they are not equal")
        void givenPlayerAndNull_whenComparing_thenTheyAreNotEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertNotEquals(null, p1);
        }

        @Test
        @DisplayName("Given two Players with same ID when comparing then they are equal")
        void givenTwoPlayersWithSameId_whenComparing_thenTheyAreEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Player p2 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
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
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Player p2 =
                    Player.builder()
                            .playerid(2L)
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("Jane")
                            .build();
            assertNotEquals(p1, p2);
        }

        @Test
        @DisplayName("Given Player and its copy when comparing then they are equal")
        void givenPlayerAndItsCopy_whenComparing_thenTheyAreEqual() {
            Player p1 =
                    Player.builder()
                            .playerid(1L)
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            Player copyOfP1 =
                    Player.builder()
                            .playerid(p1.getPlayerid())
                            .playerlanguageid(p1.getPlayerlanguageid())
                            .backgroundid(p1.getBackgroundid())
                            .menuid(p1.getMenuid())
                            .name(p1.getName())
                            .isselected(p1.getIsselected())
                            .createdat(p1.getCreatedat())
                            .updatedat(p1.getUpdatedat())
                            .build();
            assertEquals(p1, copyOfP1);
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        @Test
        @DisplayName("Given Player when setting name then name is updated")
        void givenPlayer_whenSettingName_thenNameIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            player.setName("Jane");
            assertEquals("Jane", player.getName());
        }

        @Test
        @DisplayName("Given Player when setting isselected then flag is updated")
        void givenPlayer_whenSettingIsselected_thenFlagIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .isselected(false)
                            .build();
            player.setIsselected(true);
            assertTrue(player.getIsselected());
        }

        @Test
        @DisplayName("Given Player when setting updatedat then updatedat is updated")
        void givenPlayer_whenSettingUpdatedat_thenUpdatedatIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            LocalDateTime now = LocalDateTime.now();
            player.setUpdatedat(now);
            assertEquals(now, player.getUpdatedat());
        }

        @Test
        @DisplayName("Given Player when setting games to null then games is empty")
        void givenPlayer_whenSettingGamesToNull_thenGamesIsEmpty() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            player.setGames(null);
            assertNotNull(player.getGames());
            assertTrue(player.getGames().isEmpty());
        }

        @Test
        @DisplayName("Given Player when adding and removing games then games collection is updated")
        void givenPlayer_whenAddingAndRemovingGames_thenGamesCollectionIsUpdated() {
            Player player =
                    Player.builder()
                            .playerlanguageid(mockPlayerLanguage)
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            assertTrue(player.getGames().isEmpty());
            player.getGames().add(mockGame1);
            assertTrue(player.getGames().contains(mockGame1));
            player.getGames().remove(mockGame1);
            assertFalse(player.getGames().contains(mockGame1));
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
                            .backgroundid(mockBackground)
                            .menuid(mockMenu)
                            .name("John")
                            .build();
            String str = player.toString();
            assertTrue(str.contains("John"));
            assertTrue(str.contains(String.valueOf(player.getPlayerid())));
            assertTrue(str.contains("1"));
        }
    }
}
