/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLevelUTest {

    @Test
    @DisplayName("Given valid parameters when creating GameLevel then should set correct values")
    void givenValidParameters_whenCreatingGameLevel_thenShouldSetCorrectValues() {
        Byte levelId = 1;
        byte level = 2;
        GameLevel gameLevel = new GameLevel(levelId, level);
        assertEquals(levelId, gameLevel.getLevelid());
        assertEquals(level, gameLevel.getLevel());
    }

    @Test
    @DisplayName(
            "Given default constructor when creating GameLevel then should have default values")
    void givenDefaultConstructor_whenCreatingGameLevel_thenShouldHaveDefaultValues() {
        GameLevel gameLevel = new GameLevel();
        assertNull(gameLevel.getLevelid());
        assertEquals(0, gameLevel.getLevel()); // byte default value
    }

    @Test
    @DisplayName("Given existing GameLevel when setting new level then should update correctly")
    void givenExistingGameLevel_whenSettingNewLevel_thenShouldUpdateCorrectly() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 1);
        byte newLevel = 3;
        gameLevel.setLevel(newLevel);
        assertEquals(newLevel, gameLevel.getLevel());
    }

    @Test
    @DisplayName(
            "Given builder with all fields when building then should create complete GameLevel")
    void givenBuilderWithAllFields_whenBuilding_thenShouldCreateCompleteGameLevel() {
        Byte levelId = 5;
        byte level = 2;
        GameLevel gameLevel = GameLevel.builder().levelid(levelId).level(level).build();
        assertEquals(levelId, gameLevel.getLevelid());
        assertEquals(level, gameLevel.getLevel());
    }

    @Test
    @DisplayName(
            "Given builder with partial fields when building then should create GameLevel with"
                    + " defaults")
    void givenBuilderWithPartialFields_whenBuilding_thenShouldCreateGameLevelWithDefaults() {
        byte level = 1;
        GameLevel gameLevel = GameLevel.builder().level(level).build();
        assertNull(gameLevel.getLevelid());
        assertEquals(level, gameLevel.getLevel());
    }

    @Test
    @DisplayName("Given builder methods when chaining then should not throw exception")
    void givenBuilderMethods_whenChaining_thenShouldNotThrowException() {
        assertDoesNotThrow(
                () -> {
                    GameLevel.GameLevelBuilder builder =
                            GameLevel.builder().levelid((byte) 1).level((byte) 2);
                    assertNotNull(builder);
                });
    }

    @Test
    @DisplayName(
            "Given two GameLevel objects with same values when comparing with equals then should"
                    + " return true")
    void givenTwoGameLevelObjectsWithSameValues_whenComparingWithEquals_thenShouldReturnTrue() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 2);
        GameLevel gameLevel2 = new GameLevel((byte) 1, (byte) 2);
        assertEquals(gameLevel, gameLevel2);
    }

    @Test
    @DisplayName("Given null object when comparing with equals then should return false")
    void givenNullObject_whenComparingWithEquals_thenShouldReturnFalse() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 2);
        assertNotEquals(null, gameLevel);
    }

    @Test
    @DisplayName("Given different type object when comparing with equals then should return false")
    void givenDifferentTypeObject_whenComparingWithEquals_thenShouldReturnFalse() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 2);
        boolean result = gameLevel.equals(new Object());
        assertFalse(result);
    }

    @Test
    @DisplayName(
            "Given two GameLevels with same values when comparing with equals then should return"
                    + " true")
    void givenTwoGameLevelsWithSameValues_whenComparingWithEquals_thenShouldReturnTrue() {
        GameLevel gameLevel1 = new GameLevel((byte) 1, (byte) 2);
        GameLevel gameLevel2 = new GameLevel((byte) 1, (byte) 2);
        assertEquals(gameLevel1, gameLevel2);
    }

    @Test
    @DisplayName(
            "Given two GameLevels with different values when comparing with equals then should"
                    + " return false")
    void givenTwoGameLevelsWithDifferentValues_whenComparingWithEquals_thenShouldReturnFalse() {
        GameLevel gameLevel1 = new GameLevel((byte) 1, (byte) 2);
        GameLevel gameLevel2 = new GameLevel((byte) 1, (byte) 3);
        GameLevel gameLevel3 = new GameLevel((byte) 2, (byte) 2);
        assertNotEquals(gameLevel1, gameLevel2);
        assertNotEquals(gameLevel1, gameLevel3);
    }

    @Test
    @DisplayName(
            "Given same GameLevel reference when comparing with equals then should return true")
    @SuppressWarnings("EqualsWithItself")
    void givenSameGameLevelReference_whenComparingWithEquals_thenShouldReturnTrue() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 2);
        assertEquals(gameLevel, gameLevel);
    }

    @Test
    @DisplayName(
            "Given same GameLevel when calling hashCode multiple times then should return"
                    + " consistent values")
    void givenSameGameLevel_whenCallingHashCodeMultipleTimes_thenShouldReturnConsistentValues() {
        GameLevel gameLevel = new GameLevel((byte) 1, (byte) 2);
        int hash1 = gameLevel.hashCode();
        int hash2 = gameLevel.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Given two equal GameLevels when calling hashCode then should return equal values")
    void givenTwoEqualGameLevels_whenCallingHashCode_thenShouldReturnEqualValues() {
        GameLevel gameLevel1 = new GameLevel((byte) 1, (byte) 2);
        GameLevel gameLevel2 = new GameLevel((byte) 1, (byte) 2);
        assertEquals(gameLevel1.hashCode(), gameLevel2.hashCode());
    }

    @Test
    @DisplayName(
            "Given GameLevel with values when calling toString then should contain all field"
                    + " values")
    void givenGameLevelWithValues_whenCallingToString_thenShouldContainAllFieldValues() {
        Byte levelId = 5;
        byte level = 3;
        GameLevel gameLevel = new GameLevel(levelId, level);
        String result = gameLevel.toString();
        assertTrue(result.contains("levelid=5"));
        assertTrue(result.contains("level=3"));
        assertTrue(result.contains("GameLevel"));
    }

    @Test
    @DisplayName(
            "Given GameLevel with null levelid when calling toString then should handle null"
                    + " correctly")
    void givenGameLevelWithNullLevelid_whenCallingToString_thenShouldHandleNullCorrectly() {
        GameLevel gameLevel = new GameLevel(null, (byte) 2);
        String result = gameLevel.toString();
        assertTrue(result.contains("levelid=null"));
        assertTrue(result.contains("level=2"));
    }

    @Test
    @DisplayName(
            "Given valid boundary values when creating GameLevel then should not throw exception")
    void givenValidBoundaryValues_whenCreatingGameLevel_thenShouldNotThrowException() {
        assertDoesNotThrow(
                () -> {
                    new GameLevel((byte) 1, (byte) 1);
                    new GameLevel((byte) 2, (byte) 2);
                    new GameLevel((byte) 3, (byte) 3);
                });
    }

    @Test
    @DisplayName("Given builder with extreme values when building then should handle correctly")
    void givenBuilderWithExtremeValues_whenBuilding_thenShouldHandleCorrectly() {
        GameLevel gameLevel =
                GameLevel.builder().levelid(Byte.MAX_VALUE).level(Byte.MAX_VALUE).build();
        assertEquals(Byte.MAX_VALUE, gameLevel.getLevelid());
        assertEquals(Byte.MAX_VALUE, gameLevel.getLevel());
    }

    @Test
    @DisplayName(
            "Given constructed GameLevel when trying to access levelid setter then should not"
                    + " exist")
    @SuppressWarnings("JavaReflectionMemberAccess")
    void givenConstructedGameLevel_whenTryingToAccessLevelidSetter_thenShouldNotExist() {
        Byte originalId = 10;
        GameLevel gameLevel = new GameLevel(originalId, (byte) 2);
        assertEquals(originalId, gameLevel.getLevelid());
        assertThrows(
                NoSuchMethodException.class,
                () -> {
                    GameLevel.class.getMethod("setLevelid", Byte.class);
                });
    }
}
