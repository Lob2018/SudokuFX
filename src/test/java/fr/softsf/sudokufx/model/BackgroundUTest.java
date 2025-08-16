/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Background Entity Tests")
class BackgroundUTest {

    private static final String VALID_HEX_6 = "#FFFFFF";
    private static final String VALID_PATH = "/images/background.jpg";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        void
                givenValidParameters_whenCreatingBackgroundWithConstructor_thenBackgroundIsCreatedCorrectly() {
            Long id = 1L;
            String hexcolor = VALID_HEX_6;
            String imagepath = VALID_PATH;
            boolean isimage = true;
            Background background = new Background(id, hexcolor, imagepath, isimage);
            assertNotNull(background);
            assertEquals(id, background.getBackgroundid());
            assertEquals(hexcolor, background.getHexcolor());
            assertEquals(imagepath, background.getImagepath());
            assertTrue(background.getIsimage());
        }

        @Test
        void
                givenNoParameters_whenCreatingBackgroundWithDefaultConstructor_thenBackgroundIsCreatedWithDefaults() {
            Background background = new Background();
            assertNotNull(background);
            assertNull(background.getBackgroundid());
            assertEquals("#000000", background.getHexcolor());
            assertEquals("", background.getImagepath());
            assertFalse(background.getIsimage());
        }
    }

    @Nested
    @DisplayName("Hex Color Validation Tests")
    class HexColorValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {"#FFFFFF", "#000000", "#123ABC", "#FFF", "#000", "#abc"})
        void givenValidHexColor_whenCreatingBackground_thenNoExceptionIsThrown(
                String validHexColor) {
            assertDoesNotThrow(() -> new Background(1L, validHexColor, VALID_PATH, false));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {
                    "FFFFFF", // Missing #
                    "#GGGGGG", // Invalid characters
                    "#12345", // Invalid length (5)
                    "#1234567", // Invalid length (7)
                    "#GG", // Invalid length (2) and characters
                    "red", // Not hex format
                    "rgb(255,255,255)" // RGB format instead of hex
                })
        void givenInvalidHexColor_whenCreatingBackground_thenIllegalArgumentExceptionIsThrown(
                String invalidHexColor) {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> new Background(1L, invalidHexColor, VALID_PATH, false));
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
                    exception.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        void
                givenNullEmptyOrBlankHexColor_whenCreatingBackground_thenIllegalArgumentExceptionIsThrown(
                        String blankHexColor) {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> new Background(1L, blankHexColor, VALID_PATH, false));
            assertEquals("hexcolor must not be null or blank", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Image Path Validation Tests")
    class ImagePathValidationTests {
        @Test
        void givenValidImagePath_whenCreatingBackground_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Background(1L, VALID_HEX_6, VALID_PATH, true));
        }

        @Test
        void givenEmptyImagePath_whenCreatingBackground_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Background(1L, VALID_HEX_6, "", false));
        }

        @Test
        void givenNullImagePath_whenCreatingBackground_thenNullPointerExceptionIsThrown() {
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> new Background(1L, VALID_HEX_6, null, false));
            assertEquals("imagepath must not be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Builder Pattern Tests")
    class BuilderPatternTests {
        @Test
        void givenAllParameters_whenUsingBuilder_thenBackgroundIsCreatedCorrectly() {
            Background background =
                    Background.builder()
                            .backgroundid(1L)
                            .hexcolor(VALID_HEX_6)
                            .imagepath(VALID_PATH)
                            .isimage(true)
                            .build();
            assertNotNull(background);
            assertEquals(1L, background.getBackgroundid());
            assertEquals(VALID_HEX_6, background.getHexcolor());
            assertEquals(VALID_PATH, background.getImagepath());
            assertTrue(background.getIsimage());
        }

        @Test
        void givenNoParameters_whenUsingBuilder_thenBackgroundIsCreatedWithDefaults() {
            Background background = Background.builder().build();
            assertNotNull(background);
            assertNull(background.getBackgroundid());
            assertEquals("#000000", background.getHexcolor());
            assertEquals("", background.getImagepath());
            assertFalse(background.getIsimage());
        }

        @Test
        void givenInvalidHexColor_whenBuilding_thenIllegalArgumentExceptionIsThrown() {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, this::buildBackgroundWithInvalidColor);
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
                    exception.getMessage());
        }

        private void buildBackgroundWithInvalidColor() {
            Background.builder().hexcolor("invalid").build();
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        @Test
        void givenValidHexColor_whenSettingHexColor_thenHexColorIsUpdated() {
            Background background = new Background();
            String newHexColor = "#FF0000";
            background.setHexcolor(newHexColor);
            assertEquals(newHexColor, background.getHexcolor());
        }

        @Test
        void givenInvalidHexColor_whenSettingHexColor_thenIllegalArgumentExceptionIsThrown() {
            Background background = new Background();
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> background.setHexcolor("invalid"));
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
                    exception.getMessage());
        }

        @Test
        void givenValidImagePath_whenSettingImagePath_thenImagePathIsUpdated() {
            Background background = new Background();
            String newImagePath = "/new/path/image.png";
            background.setImagepath(newImagePath);
            assertEquals(newImagePath, background.getImagepath());
        }

        @Test
        void givenNullImagePath_whenSettingImagePath_thenNullPointerExceptionIsThrown() {
            Background background = new Background();
            NullPointerException exception =
                    assertThrows(NullPointerException.class, () -> background.setImagepath(null));
            assertEquals("imagepath must not be null", exception.getMessage());
        }

        @Test
        void givenBooleanValue_whenSettingIsImageFlag_thenIsImageFlagIsUpdated() {
            Background background = new Background();
            background.setIsimage(true);
            assertTrue(background.getIsimage());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        @Test
        void givenTwoBackgroundsWithSameProperties_whenComparingEquality_thenTheyAreEqual() {
            Background bg1 = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            Background bg2 = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            assertEquals(bg1, bg2);
            assertEquals(bg1.hashCode(), bg2.hashCode());
        }

        @Test
        void
                givenTwoBackgroundsWithDifferentProperties_whenComparingEquality_thenTheyAreNotEqual() {
            Background bg1 = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            Background bg2 = new Background(2L, VALID_HEX_6, VALID_PATH, true);
            assertNotEquals(bg1, bg2);
        }

        @Test
        void givenSameBackgroundInstance_whenComparingToItself_thenTheyAreEqual() {
            Background background = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            assertEquals(background, background);
        }

        @Test
        void givenBackgroundAndNull_whenComparingEquality_thenTheyAreNotEqual() {
            Background background = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            assertNotEquals(null, background);
        }

        @Test
        void givenBackgroundAndDifferentClass_whenComparingEquality_thenTheyAreNotEqual() {
            Background background = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            Object notBackground = new Object();
            assertNotEquals(background, notBackground);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        void givenBackgroundInstance_whenCallingToString_thenFormattedStringIsReturned() {
            Background background = new Background(1L, VALID_HEX_6, VALID_PATH, true);
            String result = background.toString();
            assertNotNull(result);
            assertTrue(result.contains("Background{"));
            assertTrue(result.contains("backgroundid=1"));
            assertTrue(result.contains("hexcolor='#FFFFFF'"));
            assertTrue(result.contains("imagepath='/images/background.jpg'"));
            assertTrue(result.contains("isimage=true"));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        void givenNullBackgroundId_whenCreatingBackground_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Background(null, VALID_HEX_6, VALID_PATH, false));
        }

        @Test
        void givenMaximumLengthStrings_whenCreatingBackground_thenNoExceptionIsThrown() {
            String maxHexColor = "#FFFFFF"; // 7 characters (max 8)
            String maxImagePath = "a".repeat(260); // Max 260 characters
            assertDoesNotThrow(() -> new Background(1L, maxHexColor, maxImagePath, false));
        }

        @Test
        void givenMixedCaseHexColors_whenCreatingBackground_thenNoExceptionIsThrown() {
            String lowerCase = "#ffffff";
            String upperCase = "#FFFFFF";
            String mixedCase = "#FfFfFf";
            assertDoesNotThrow(() -> new Background(1L, lowerCase, VALID_PATH, false));
            assertDoesNotThrow(() -> new Background(1L, upperCase, VALID_PATH, false));
            assertDoesNotThrow(() -> new Background(1L, mixedCase, VALID_PATH, false));
        }
    }
}
