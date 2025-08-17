/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Game Entity Tests")
class GameUTest {

    private Grid mockGrid;
    private Player mockPlayer;
    private GameLevel mockGameLevel;
    private LocalDateTime testCreatedAt;
    private LocalDateTime testUpdatedAt;

    @BeforeEach
    void setUp() {
        mockGrid = mock(Grid.class);
        mockPlayer = mock(Player.class);
        mockGameLevel = mock(GameLevel.class);
        testCreatedAt = LocalDateTime.now().minusDays(1);
        testUpdatedAt = LocalDateTime.now();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Given valid parameters when creating Game then Game is created successfully")
        void givenValidParameters_whenCreatingGame_thenGameIsCreatedSuccessfully() {
            Long expectedGameId = 1L;
            boolean expectedIsSelected = true;
            Game game =
                    new Game(
                            expectedGameId,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            expectedIsSelected,
                            testCreatedAt,
                            testUpdatedAt);
            assertNotNull(game);
            assertEquals(expectedGameId, game.getGameid());
            assertEquals(mockGrid, game.getGridid());
            assertEquals(mockPlayer, game.getPlayerid());
            assertEquals(mockGameLevel, game.getLevelid());
            assertEquals(expectedIsSelected, game.getIsselected());
            assertEquals(testCreatedAt, game.getCreatedat());
            assertEquals(testUpdatedAt, game.getUpdatedat());
        }

        @Test
        @DisplayName("Given null Grid when creating Game then NullPointerException is thrown")
        void givenNullGrid_whenCreatingGame_thenNullPointerExceptionIsThrown() {
            Grid nullGrid = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                new Game(
                                        1L,
                                        nullGrid,
                                        mockPlayer,
                                        mockGameLevel,
                                        false,
                                        testCreatedAt,
                                        testUpdatedAt);
                            });
            assertEquals("gridid must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given null Player when creating Game then NullPointerException is thrown")
        void givenNullPlayer_whenCreatingGame_thenNullPointerExceptionIsThrown() {
            Player nullPlayer = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                new Game(
                                        1L,
                                        mockGrid,
                                        nullPlayer,
                                        mockGameLevel,
                                        false,
                                        testCreatedAt,
                                        testUpdatedAt);
                            });
            assertEquals("playerid must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given null GameLevel when creating Game then NullPointerException is thrown")
        void givenNullGameLevel_whenCreatingGame_thenNullPointerExceptionIsThrown() {
            GameLevel nullGameLevel = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                new Game(
                                        1L,
                                        mockGrid,
                                        mockPlayer,
                                        nullGameLevel,
                                        false,
                                        testCreatedAt,
                                        testUpdatedAt);
                            });
            assertEquals("levelid must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given null CreatedAt when creating Game then NullPointerException is thrown")
        void givenNullCreatedAt_whenCreatingGame_thenNullPointerExceptionIsThrown() {
            LocalDateTime nullCreatedAt = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                new Game(
                                        1L,
                                        mockGrid,
                                        mockPlayer,
                                        mockGameLevel,
                                        false,
                                        nullCreatedAt,
                                        testUpdatedAt);
                            });
            assertEquals("createdat must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given null UpdatedAt when creating Game then NullPointerException is thrown")
        void givenNullUpdatedAt_whenCreatingGame_thenNullPointerExceptionIsThrown() {
            LocalDateTime nullUpdatedAt = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                new Game(
                                        1L,
                                        mockGrid,
                                        mockPlayer,
                                        mockGameLevel,
                                        false,
                                        testCreatedAt,
                                        nullUpdatedAt);
                            });
            assertEquals("updatedat must not be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Getter Tests")
    class GetterTests {
        private Game game;

        @BeforeEach
        void setUp() {
            game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
        }

        @Test
        @DisplayName("Given Game exists when getting GameId then GameId is returned")
        void givenGameExists_whenGettingGameId_thenGameIdIsReturned() {
            Long expectedGameId = 1L;
            Long actualGameId = game.getGameid();
            assertEquals(expectedGameId, actualGameId);
        }

        @Test
        @DisplayName("Given Game exists when getting Grid then Grid is returned")
        void givenGameExists_whenGettingGrid_thenGridIsReturned() {
            Grid actualGrid = game.getGridid();
            assertEquals(mockGrid, actualGrid);
        }

        @Test
        @DisplayName("Given Game exists when getting Player then Player is returned")
        void givenGameExists_whenGettingPlayer_thenPlayerIsReturned() {
            Player actualPlayer = game.getPlayerid();
            assertEquals(mockPlayer, actualPlayer);
        }

        @Test
        @DisplayName("Given Game exists when getting GameLevel then GameLevel is returned")
        void givenGameExists_whenGettingGameLevel_thenGameLevelIsReturned() {
            GameLevel actualGameLevel = game.getLevelid();
            assertEquals(mockGameLevel, actualGameLevel);
        }

        @Test
        @DisplayName("Given Game exists when getting IsSelected then IsSelected is returned")
        void givenGameExists_whenGettingIsSelected_thenIsSelectedIsReturned() {
            boolean actualIsSelected = game.getIsselected();
            assertTrue(actualIsSelected);
        }

        @Test
        @DisplayName("Given Game exists when getting CreatedAt then CreatedAt is returned")
        void givenGameExists_whenGettingCreatedAt_thenCreatedAtIsReturned() {
            LocalDateTime actualCreatedAt = game.getCreatedat();
            assertEquals(testCreatedAt, actualCreatedAt);
        }

        @Test
        @DisplayName("Given Game exists when getting UpdatedAt then UpdatedAt is returned")
        void givenGameExists_whenGettingUpdatedAt_thenUpdatedAtIsReturned() {
            LocalDateTime actualUpdatedAt = game.getUpdatedat();
            assertEquals(testUpdatedAt, actualUpdatedAt);
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        private Game game;

        @BeforeEach
        void setUp() {
            game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            false,
                            testCreatedAt,
                            testUpdatedAt);
        }

        @Test
        @DisplayName("Given valid Player when setting Player then Player is updated")
        void givenValidPlayer_whenSettingPlayer_thenPlayerIsUpdated() {
            Player newPlayer = mock(Player.class);
            game.setPlayerid(newPlayer);
            assertEquals(newPlayer, game.getPlayerid());
        }

        @Test
        @DisplayName("Given null Player when setting Player then NullPointerException is thrown")
        void givenNullPlayer_whenSettingPlayer_thenNullPointerExceptionIsThrown() {
            Player nullPlayer = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                game.setPlayerid(nullPlayer);
                            });
            assertEquals("playerid must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given valid boolean when setting IsSelected then IsSelected is updated")
        void givenValidBoolean_whenSettingIsSelected_thenIsSelectedIsUpdated() {
            boolean newIsSelected = true;
            game.setIsselected(newIsSelected);
            assertTrue(game.getIsselected());
        }

        @Test
        @DisplayName("Given valid UpdatedAt when setting UpdatedAt then UpdatedAt is updated")
        void givenValidUpdatedAt_whenSettingUpdatedAt_thenUpdatedAtIsUpdated() {
            LocalDateTime newUpdatedAt = LocalDateTime.now().plusDays(1);
            game.setUpdatedat(newUpdatedAt);
            assertEquals(newUpdatedAt, game.getUpdatedat());
        }

        @Test
        @DisplayName(
                "Given null UpdatedAt when setting UpdatedAt then NullPointerException is thrown")
        void givenNullUpdatedAt_whenSettingUpdatedAt_thenNullPointerExceptionIsThrown() {
            LocalDateTime nullUpdatedAt = null;
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> {
                                game.setUpdatedat(nullUpdatedAt);
                            });
            assertEquals("updatedat must not be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {
        @Test
        @DisplayName("Given valid parameters when using Builder then Game is built successfully")
        void givenValidParameters_whenUsingBuilder_thenGameIsBuiltSuccessfully() {
            Long expectedGameId = 2L;
            boolean expectedIsSelected = true;
            Game game =
                    Game.builder()
                            .gameid(expectedGameId)
                            .gridid(mockGrid)
                            .playerid(mockPlayer)
                            .levelid(mockGameLevel)
                            .isselected(expectedIsSelected)
                            .createdat(testCreatedAt)
                            .updatedat(testUpdatedAt)
                            .build();
            assertNotNull(game);
            assertEquals(expectedGameId, game.getGameid());
            assertEquals(mockGrid, game.getGridid());
            assertEquals(mockPlayer, game.getPlayerid());
            assertEquals(mockGameLevel, game.getLevelid());
            assertEquals(expectedIsSelected, game.getIsselected());
            assertEquals(testCreatedAt, game.getCreatedat());
            assertEquals(testUpdatedAt, game.getUpdatedat());
        }

        @Test
        @DisplayName("Given missing required Grid when building then NullPointerException is thrown")
        void givenMissingRequiredGrid_whenBuilding_thenNullPointerExceptionIsThrown() {
            Game.GameBuilder builder = Game.builder()
                    .gameid(1L)
                    .playerid(mockPlayer)
                    .levelid(mockGameLevel);
            NullPointerException exception = assertThrows(
                    NullPointerException.class,
                    builder::build
            );
            assertEquals("gridid must not be null", exception.getMessage());
        }

        @Test
        @DisplayName("Given default values when using Builder then default values are applied")
        void givenDefaultValues_whenUsingBuilder_thenDefaultValuesAreApplied() {
            Game game =
                    Game.builder()
                            .gridid(mockGrid)
                            .playerid(mockPlayer)
                            .levelid(mockGameLevel)
                            .build();
            assertNotNull(game);
            assertFalse(game.getIsselected());
            assertNotNull(game.getCreatedat());
            assertNotNull(game.getUpdatedat());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        @Test
        @DisplayName("Given same Game objects when comparing then they are equal")
        void givenSameGameObjects_whenComparing_thenTheyAreEqual() {
            Game game1 =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            Game game2 =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            assertEquals(game1, game2);
            assertEquals(game1.hashCode(), game2.hashCode());
        }

        @Test
        @DisplayName("Given different Game objects when comparing then they are not equal")
        void givenDifferentGameObjects_whenComparing_thenTheyAreNotEqual() {
            Game game1 =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            Game game2 =
                    new Game(
                            2L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            assertNotEquals(game1, game2);
        }

        @Test
        @DisplayName("Given Game and null when comparing then they are not equal")
        void givenGameAndNull_whenComparing_thenTheyAreNotEqual() {
            Game game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            assertNotEquals(null, game);
        }

        @Test
        @DisplayName("Given Game and different type when comparing then they are not equal")
        void givenGameAndDifferentType_whenComparing_thenTheyAreNotEqual() {
            Game game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            String differentType = "not a game";
            assertNotEquals(game, differentType);
        }

        @Test
        @DisplayName("Given same Game reference when comparing then they are equal")
        void givenSameGameReference_whenComparing_thenTheyAreEqual() {
            Game game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);
            assertEquals(game, game);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        @DisplayName("Given Game object when calling toString then formatted string is returned")
        void givenGameObject_whenCallingToString_thenFormattedStringIsReturned() {
            Game game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            true,
                            testCreatedAt,
                            testUpdatedAt);

            when(mockGrid.getGridid()).thenReturn(1L);
            when(mockPlayer.getPlayerid()).thenReturn(1L);
            when(mockGameLevel.getLevelid()).thenReturn((byte) 1);

            String result = game.toString();
            assertNotNull(result);
            assertTrue(result.contains("Game{"));
            assertTrue(result.contains("gameid=1"));
            assertTrue(result.contains("isselected=true"));
            assertTrue(result.contains("gridid=" + mockGrid.getGridid()));
            assertTrue(result.contains("playerid=" + mockPlayer.getPlayerid()));
            assertTrue(result.contains("levelid=" + mockGameLevel.getLevelid()));
        }
    }

    @Nested
    @DisplayName("Default Constructor Tests")
    class DefaultConstructorTests {

        @Test
        @DisplayName(
                "Given default constructor when creating Game then Game is created with default"
                        + " values")
        void givenDefaultConstructor_whenCreatingGame_thenGameIsCreatedWithDefaultValues() {
            Game game = new Game();
            assertNotNull(game);
            assertNull(game.getGameid());
            assertNull(game.getGridid());
            assertNull(game.getPlayerid());
            assertNull(game.getLevelid());
            assertFalse(game.getIsselected());
            assertNotNull(game.getCreatedat());
            assertNotNull(game.getUpdatedat());
        }
    }

    @Nested
    @DisplayName("DetachFromPlayer Tests")
    class DetachFromPlayerTests {
        private Game game;

        @BeforeEach
        void setUp() {
            game =
                    new Game(
                            1L,
                            mockGrid,
                            mockPlayer,
                            mockGameLevel,
                            false,
                            testCreatedAt,
                            testUpdatedAt);
        }

        @Test
        @DisplayName("Given Game with Player when detaching from Player then Player is set to null")
        void givenGameWithPlayer_whenDetachingFromPlayer_thenPlayerIsSetToNull() {
            assertEquals(mockPlayer, game.getPlayerid());
            game.detachFromPlayer();
            assertNull(game.getPlayerid());
        }
    }
}
