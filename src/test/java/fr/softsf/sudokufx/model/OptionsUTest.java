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

@DisplayName("Options Entity Tests")
class OptionsUTest {

    private static final String VALID_HEX_8 = "FFFFFFFF";
    private static final String VALID_PATH = "/images/background.jpg";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        void
                givenValidParameters_whenCreatingOptionsWithConstructor_thenOptionsIsCreatedCorrectly() {
            Long id = 1L;
            String hexcolor = VALID_HEX_8;
            String imagepath = VALID_PATH;
            String songpath = VALID_PATH;
            boolean isimage = true;
            boolean isopaque = true;
            boolean ismuted = true;
            Options options =
                    new Options(id, hexcolor, imagepath, songpath, isimage, isopaque, ismuted);
            assertNotNull(options);
            assertEquals(id, options.getOptionsid());
            assertEquals(hexcolor, options.getHexcolor());
            assertEquals(imagepath, options.getImagepath());
            assertEquals(songpath, options.getSongpath());
            assertTrue(options.getImage());
            assertTrue(options.getOpaque());
            assertTrue(options.getMuted());
        }

        @Test
        void
                givenNoParameters_whenCreatingOptionsWithDefaultConstructor_thenOptionsIsCreatedWithDefaults() {
            Options options = new Options();
            assertNotNull(options);
            assertNull(options.getOptionsid());
            assertEquals("FFFFFFFF", options.getHexcolor());
            assertEquals("", options.getImagepath());
            assertFalse(options.getImage());
        }
    }

    @Nested
    @DisplayName("Hex Color Validation Tests")
    class HexColorValidationTests {
        @ParameterizedTest
        @ValueSource(
                strings = {"FFFFFFFF", "00000000", "123ABC00", "FFFFFF00", "ffffff00", "00abcdef"})
        void givenValidHexColor_whenCreatingOptions_thenNoExceptionIsThrown(String validHexColor) {
            assertDoesNotThrow(
                    () ->
                            new Options(
                                    1L, validHexColor, VALID_PATH, VALID_PATH, false, true, true));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {
                    "#FFFFFFF", // Invalid #
                    "GGGGGGGG", // Invalid characters
                    "1234567", // Invalid length (7)
                    "#123456789", // Invalid length (9)
                    "#GG", // Invalid length (2) and characters
                    "red", // Not hex format
                    "rgba(255,255,255,0)" // RGBA format instead of hex
                })
        void givenInvalidHexColor_whenCreatingOptions_thenIllegalArgumentExceptionIsThrown(
                String invalidHexColor) {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () ->
                                    new Options(
                                            1L,
                                            invalidHexColor,
                                            VALID_PATH,
                                            VALID_PATH,
                                            false,
                                            true,
                                            true));
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., FFFFFFFF)",
                    exception.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        void givenNullEmptyOrBlankHexColor_whenCreatingOptions_thenIllegalArgumentExceptionIsThrown(
                String blankHexColor) {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () ->
                                    new Options(
                                            1L,
                                            blankHexColor,
                                            VALID_PATH,
                                            VALID_PATH,
                                            false,
                                            true,
                                            true));
            assertEquals("hexcolor must not be null or blank", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Image Path Validation Tests")
    class ImagePathValidationTests {
        @Test
        void givenValidImagePath_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(
                    () -> new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true));
        }

        @Test
        void givenEmptyImagePath_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(
                    () -> new Options(1L, VALID_HEX_8, "", VALID_PATH, false, true, true));
        }

        @Test
        void givenEmptySongPath_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(
                    () -> new Options(1L, VALID_HEX_8, VALID_PATH, "", false, true, true));
        }

        @Test
        @SuppressWarnings("ConstantConditions")
        void givenNullImagePath_whenCreatingOptions_thenNullPointerExceptionIsThrown() {
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () ->
                                    new Options(
                                            1L, VALID_HEX_8, null, VALID_PATH, false, true, true));
            assertEquals("imagepath must not be null", exception.getMessage());
        }

        @Test
        @SuppressWarnings("ConstantConditions")
        void givenNullSongPath_whenCreatingOptions_thenNullPointerExceptionIsThrown() {
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () ->
                                    new Options(
                                            1L, VALID_HEX_8, VALID_PATH, null, false, true, true));
            assertEquals("songpath must not be null", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Builder Pattern Tests")
    class BuilderPatternTests {
        @Test
        void givenAllParameters_whenUsingBuilder_thenOptionsIsCreatedCorrectly() {
            Options options =
                    Options.builder()
                            .optionsid(1L)
                            .hexcolor(VALID_HEX_8)
                            .imagepath(VALID_PATH)
                            .songpath(VALID_PATH)
                            .image(true)
                            .opaque(true)
                            .muted(true)
                            .build();
            assertNotNull(options);
            assertEquals(1L, options.getOptionsid());
            assertEquals(VALID_HEX_8, options.getHexcolor());
            assertEquals(VALID_PATH, options.getImagepath());
            assertEquals(VALID_PATH, options.getSongpath());
            assertTrue(options.getImage());
            assertTrue(options.getOpaque());
            assertTrue(options.getMuted());
        }

        @Test
        void givenNoParameters_whenUsingBuilder_thenOptionsIsCreatedWithDefaults() {
            Options options = Options.builder().build();
            assertNotNull(options);
            assertNull(options.getOptionsid());
            assertEquals("FFFFFFFF", options.getHexcolor());
            assertEquals("", options.getImagepath());
            assertFalse(options.getImage());
        }

        @Test
        void givenInvalidHexColor_whenBuilding_thenIllegalArgumentExceptionIsThrown() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class, this::buildOptionsWithInvalidColor);
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., FFFFFFFF)",
                    exception.getMessage());
        }

        private void buildOptionsWithInvalidColor() {
            Options.builder().hexcolor("invalid").build();
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        @Test
        void givenValidHexColor_whenSettingHexColor_thenHexColorIsUpdated() {
            Options options = new Options();
            String newHexColor = "FF000000";
            options.setHexcolor(newHexColor);
            assertEquals(newHexColor, options.getHexcolor());
        }

        @Test
        void givenInvalidHexColor_whenSettingHexColor_thenIllegalArgumentExceptionIsThrown() {
            Options options = new Options();
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class, () -> options.setHexcolor("invalid"));
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., FFFFFFFF)",
                    exception.getMessage());
        }

        @Test
        void givenValidImagePath_whenSettingImagePath_thenImagePathIsUpdated() {
            Options options = new Options();
            String newImagePath = "/new/path/image.png";
            options.setImagepath(newImagePath);
            assertEquals(newImagePath, options.getImagepath());
        }

        @Test
        @SuppressWarnings("ConstantConditions")
        void givenNullImagePath_whenSettingImagePath_thenNullPointerExceptionIsThrown() {
            Options options = new Options();
            NullPointerException exception =
                    assertThrows(NullPointerException.class, () -> options.setImagepath(null));
            assertEquals("imagepath must not be null", exception.getMessage());
        }

        @Test
        void givenBooleanValue_whenSettingIsImageFlag_thenIsImageFlagIsUpdated() {
            Options options = new Options();
            options.setImage(true);
            assertTrue(options.getImage());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        @Test
        void givenTwoOptionssWithSameProperties_whenComparingEquality_thenTheyAreEqual() {
            Options bg1 = new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            Options bg2 = new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            assertEquals(bg1, bg2);
            assertEquals(bg1.hashCode(), bg2.hashCode());
        }

        @Test
        void givenTwoOptionssWithDifferentProperties_whenComparingEquality_thenTheyAreNotEqual() {
            Options bg1 = new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            Options bg2 = new Options(2L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            assertNotEquals(bg1, bg2);
        }

        @Test
        @SuppressWarnings("EqualsWithItself")
        void givenSameOptionsInstance_whenComparingToItself_thenTheyAreEqual() {
            Options options =
                    new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            assertEquals(options, options);
        }

        @Test
        void givenOptionsAndNull_whenComparingEquality_thenTheyAreNotEqual() {
            Options options =
                    new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            assertNotEquals(null, options);
        }

        @Test
        void givenOptionsAndDifferentClass_whenComparingEquality_thenTheyAreNotEqual() {
            Options options =
                    new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            Object notOptions = new Object();
            assertNotEquals(options, notOptions);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        void givenOptionsInstance_whenCallingToString_thenFormattedStringIsReturned() {
            Options options =
                    new Options(1L, VALID_HEX_8, VALID_PATH, VALID_PATH, true, true, true);
            String result = options.toString();
            assertNotNull(result);
            assertTrue(result.contains("Options{"));
            assertTrue(result.contains("optionsid=1"));
            assertTrue(result.contains("hexcolor='FFFFFFFF'"));
            assertTrue(result.contains("imagepath='/images/background.jpg'"));
            assertTrue(result.contains("songpath='/images/background.jpg'"));
            assertTrue(result.contains("image=true"));
            assertTrue(result.contains("opaque=true"));
            assertTrue(result.contains("muted=true"));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        void givenNullOptionsId_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(
                    () ->
                            new Options(
                                    null, VALID_HEX_8, VALID_PATH, VALID_PATH, false, true, true));
        }

        @Test
        void givenImageMaximumLengthStrings_whenCreatingOptions_thenNoExceptionIsThrown() {
            String maxHexColor = "FFFFFFFF";
            String maxImagePath = "a".repeat(260); // Max 260 characters
            assertDoesNotThrow(
                    () ->
                            new Options(
                                    1L, maxHexColor, maxImagePath, VALID_PATH, false, true, true));
        }

        @Test
        void givenSongMaximumLengthStrings_whenCreatingOptions_thenNoExceptionIsThrown() {
            String maxHexColor = "FFFFFFFF";
            String maxSongPath = "a".repeat(260); // Max 260 characters
            assertDoesNotThrow(
                    () -> new Options(1L, maxHexColor, VALID_PATH, maxSongPath, false, true, true));
        }

        @Test
        void givenMixedCaseHexColors_whenCreatingOptions_thenNoExceptionIsThrown() {
            String lowerCase = "ffffffff";
            String upperCase = "FFFFFFFF";
            String mixedCase = "FfFfFfFf";
            assertDoesNotThrow(
                    () -> new Options(1L, lowerCase, VALID_PATH, VALID_PATH, false, true, true));
            assertDoesNotThrow(
                    () -> new Options(1L, upperCase, VALID_PATH, VALID_PATH, false, true, true));
            assertDoesNotThrow(
                    () -> new Options(1L, mixedCase, VALID_PATH, VALID_PATH, false, true, true));
        }
    }
}
