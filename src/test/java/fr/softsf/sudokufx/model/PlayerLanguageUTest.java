/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLanguageUTest {

    @Test
    @DisplayName("Should create PlayerLanguage with constructor")
    void
            givenPlayerlanguageidAndIso_whenCreatePlayerLanguageWithConstructor_thenPlayerLanguageIsCreatedCorrectly() {
        Long playerlanguageid = 1L;
        String iso = "EN";
        PlayerLanguage playerLanguage = new PlayerLanguage(playerlanguageid, iso);
        assertEquals(playerlanguageid, playerLanguage.getPlayerlanguageid());
        assertEquals(iso, playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should create PlayerLanguage with valid FR iso")
    void givenValidFrIso_whenCreatePlayerLanguage_thenPlayerLanguageIsCreatedCorrectly() {
        Long playerlanguageid = 1L;
        String iso = "FR";
        PlayerLanguage playerLanguage = new PlayerLanguage(playerlanguageid, iso);
        assertEquals(playerlanguageid, playerLanguage.getPlayerlanguageid());
        assertEquals(iso, playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should create PlayerLanguage with builder")
    void
            givenPlayerlanguageidAndValidIso_whenCreatePlayerLanguageWithBuilder_thenPlayerLanguageIsCreatedCorrectly() {
        Long playerlanguageid = 5L;
        String iso = "EN";
        PlayerLanguage playerLanguage =
                PlayerLanguage.builder().playerlanguageid(playerlanguageid).iso(iso).build();
        assertEquals(playerlanguageid, playerLanguage.getPlayerlanguageid());
        assertEquals(iso, playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should create PlayerLanguage with default constructor")
    void
            givenNothing_whenCreatePlayerLanguageWithDefaultConstructor_thenPlayerLanguageHasDefaultValues() {
        PlayerLanguage playerLanguage = new PlayerLanguage();
        assertNull(playerLanguage.getPlayerlanguageid());
        assertEquals("FR", playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should set iso using setter with valid value")
    void givenExistingPlayerLanguage_whenSetValidIsoUsingSetter_thenIsoIsUpdated() {
        PlayerLanguage playerLanguage = new PlayerLanguage();
        String newIso = "EN";
        playerLanguage.setIso(newIso);
        assertEquals(newIso, playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should throw NullPointerException when iso is null in constructor")
    @SuppressWarnings("DataFlowIssue")
    void givenNullIso_whenCreatePlayerLanguage_thenThrowsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new PlayerLanguage(1L, null));
        assertEquals("iso must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NullPointerException when setting null iso")
    @SuppressWarnings("DataFlowIssue")
    void givenExistingPlayerLanguage_whenSetNullIso_thenThrowsNullPointerException() {
        PlayerLanguage playerLanguage = new PlayerLanguage(1L, "FR");
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> playerLanguage.setIso(null));
        assertEquals("iso must not be null", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Should throw IllegalArgumentException for invalid ISO values")
    @ValueSource(
            strings = {"ES", "", "fr", "Fr", "FRA", " FR ", "DE", "IT", "en", "ABC", "123", " EN"})
    void givenInvalidIsoValue_whenCreatePlayerLanguage_thenThrowsIllegalArgumentException(
            String invalidIso) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> new PlayerLanguage(1L, invalidIso));
        assertEquals("iso must be either 'FR' or 'EN'", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when setting invalid iso")
    void givenExistingPlayerLanguage_whenSetInvalidIso_thenThrowsIllegalArgumentException() {
        PlayerLanguage playerLanguage = new PlayerLanguage(1L, "FR");
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> playerLanguage.setIso("DE"));
        assertEquals("iso must be either 'FR' or 'EN'", exception.getMessage());
    }

    @Test
    @DisplayName("Should handle builder with partial fields")
    void givenBuilderWithPartialFields_whenBuild_thenCreatesPlayerLanguageWithDefaults() {
        PlayerLanguage playerLanguage = PlayerLanguage.builder().playerlanguageid(10L).build();
        assertEquals(10L, playerLanguage.getPlayerlanguageid());
        assertEquals("FR", playerLanguage.getIso());
    }

    @Test
    @DisplayName("Should throw exception when builder has null iso")
    @SuppressWarnings("DataFlowIssue")
    void givenBuilderWithNullIso_whenBuild_thenThrowsNullPointerException() {
        PlayerLanguage.PlayerLanguageBuilder builder =
                PlayerLanguage.builder().playerlanguageid(1L).iso(null);
        assertThrows(NullPointerException.class, builder::build);
    }

    @Test
    @DisplayName("Should throw exception when builder has invalid iso")
    void givenBuilderWithInvalidIso_whenBuild_thenThrowsIllegalArgumentException() {
        PlayerLanguage.PlayerLanguageBuilder builder =
                PlayerLanguage.builder().playerlanguageid(1L).iso("IT");
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, builder::build);
        assertEquals("iso must be either 'FR' or 'EN'", exception.getMessage());
    }

    @Test
    @DisplayName("Should implement equals correctly")
    @SuppressWarnings({"EqualsWithItself"})
    void givenTwoPlayerLanguages_whenCompareWithEquals_thenEqualsWorksCorrectly() {
        PlayerLanguage playerLanguage1 = new PlayerLanguage(1L, "FR");
        PlayerLanguage playerLanguage2 = new PlayerLanguage(1L, "FR");
        PlayerLanguage playerLanguage3 = new PlayerLanguage(1L, "EN");
        PlayerLanguage playerLanguage4 = new PlayerLanguage(2L, "FR");
        assertEquals(playerLanguage1, playerLanguage2);
        assertNotEquals(playerLanguage1, playerLanguage3);
        assertNotEquals(playerLanguage1, playerLanguage4);
        assertEquals(playerLanguage1, playerLanguage1);
        assertNotEquals(null, playerLanguage1);
        boolean equalsObject = playerLanguage1.equals(new Object());
        assertFalse(equalsObject);
    }

    @Test
    @DisplayName("Should implement hashCode correctly")
    void givenPlayerLanguagesWithSameAndDifferentValues_whenGetHashCode_thenHashCodeIsConsistent() {
        PlayerLanguage playerLanguage1 = new PlayerLanguage(1L, "FR");
        PlayerLanguage playerLanguage2 = new PlayerLanguage(1L, "FR");
        PlayerLanguage playerLanguage3 = new PlayerLanguage(1L, "EN");
        assertEquals(playerLanguage1.hashCode(), playerLanguage2.hashCode());
        assertNotEquals(playerLanguage1.hashCode(), playerLanguage3.hashCode());
        assertEquals(playerLanguage1.hashCode(), playerLanguage1.hashCode());
    }

    @Test
    @DisplayName("Should implement toString correctly")
    void
            givenPlayerLanguageWithValidValues_whenCallToString_thenCorrectStringRepresentationIsReturned() {
        PlayerLanguage playerLanguage =
                new PlayerLanguage(10L, "EN"); // Changed from "ES" to valid value
        String result = playerLanguage.toString();
        assertEquals("PlayerLanguage{playerlanguageid=10, iso='EN'}", result);
    }

    @Test
    @DisplayName("Should handle null playerlanguageid")
    void givenNullPlayerlanguageid_whenCreatePlayerLanguage_thenNullPlayerlanguageidIsHandled() {
        PlayerLanguage playerLanguage =
                new PlayerLanguage(null, "FR"); // Changed from "IT" to valid value
        assertNull(playerLanguage.getPlayerlanguageid());
        assertEquals("FR", playerLanguage.getIso());
        assertEquals("PlayerLanguage{playerlanguageid=null, iso='FR'}", playerLanguage.toString());
    }

    @Test
    @DisplayName("Should work with extreme Long values")
    void givenExtremeLongValues_whenCreatePlayerLanguage_thenExtremeValuesAreHandled() {
        PlayerLanguage playerLanguage1 = new PlayerLanguage(Long.MAX_VALUE, "FR");
        PlayerLanguage playerLanguage2 = new PlayerLanguage(Long.MIN_VALUE, "EN");
        assertEquals(Long.MAX_VALUE, playerLanguage1.getPlayerlanguageid());
        assertEquals("FR", playerLanguage1.getIso());
        assertEquals(Long.MIN_VALUE, playerLanguage2.getPlayerlanguageid());
        assertEquals("EN", playerLanguage2.getIso());
    }

    @Test
    @DisplayName("Should reject boundary values for iso that are not FR or EN")
    void givenInvalidBoundaryValues_whenCreatePlayerLanguage_thenThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerLanguage(1L, "A"));
        assertThrows(IllegalArgumentException.class, () -> new PlayerLanguage(2L, "AB"));
        assertThrows(IllegalArgumentException.class, () -> new PlayerLanguage(3L, ""));
    }

    @Test
    @DisplayName("Should accept only valid FR and EN values")
    void givenValidIsoValues_whenCreatePlayerLanguage_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> new PlayerLanguage(1L, "FR"));
        assertDoesNotThrow(() -> new PlayerLanguage(2L, "EN"));
    }

    @Test
    @DisplayName("Builder should be fluent")
    void givenBuilder_whenChainMultipleCalls_thenBuilderIsFluent() {
        PlayerLanguage playerLanguage =
                PlayerLanguage.builder()
                        .playerlanguageid(5L)
                        .iso("FR")
                        .playerlanguageid(10L)
                        .build();
        assertEquals(10L, playerLanguage.getPlayerlanguageid());
        assertEquals("FR", playerLanguage.getIso());
    }

    @Test
    @DisplayName("Builder should validate iso when building")
    void givenBuilderWithValidIsos_whenBuild_thenBothFRAndENAreAccepted() {
        PlayerLanguage playerLanguageFR =
                PlayerLanguage.builder().playerlanguageid(1L).iso("FR").build();
        assertEquals("FR", playerLanguageFR.getIso());
        PlayerLanguage playerLanguageEN =
                PlayerLanguage.builder().playerlanguageid(2L).iso("EN").build();
        assertEquals("EN", playerLanguageEN.getIso());
    }
}
