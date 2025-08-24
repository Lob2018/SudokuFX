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

    private static final String VALID_HEX_6 = "#FFFFFF";
    private static final String VALID_PATH = "/images/background.jpg";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        void
                givenValidParameters_whenCreatingOptionsWithConstructor_thenOptionsIsCreatedCorrectly() {
            Long id = 1L;
            String hexcolor = VALID_HEX_6;
            String imagepath = VALID_PATH;
            boolean isimage = true;
            boolean isopaque = true;
            Options options = new Options(id, hexcolor, imagepath, isimage, isopaque);
            assertNotNull(options);
            assertEquals(id, options.getOptionsid());
            assertEquals(hexcolor, options.getHexcolor());
            assertEquals(imagepath, options.getImagepath());
            assertTrue(options.getIsimage());
            assertTrue(options.getIsopaque());
        }

        @Test
        void
                givenNoParameters_whenCreatingOptionsWithDefaultConstructor_thenOptionsIsCreatedWithDefaults() {
            Options options = new Options();
            assertNotNull(options);
            assertNull(options.getOptionsid());
            assertEquals("#000000", options.getHexcolor());
            assertEquals("", options.getImagepath());
            assertFalse(options.getIsimage());
        }
    }

    @Nested
    @DisplayName("Hex Color Validation Tests")
    class HexColorValidationTests {
        @ParameterizedTest
        @ValueSource(strings = {"#FFFFFF", "#000000", "#123ABC", "#FFF", "#000", "#abc"})
        void givenValidHexColor_whenCreatingOptions_thenNoExceptionIsThrown(String validHexColor) {
            assertDoesNotThrow(() -> new Options(1L, validHexColor, VALID_PATH, false, true));
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
        void givenInvalidHexColor_whenCreatingOptions_thenIllegalArgumentExceptionIsThrown(
                String invalidHexColor) {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class,
                            () -> new Options(1L, invalidHexColor, VALID_PATH, false, true));
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
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
                            () -> new Options(1L, blankHexColor, VALID_PATH, false, true));
            assertEquals("hexcolor must not be null or blank", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Image Path Validation Tests")
    class ImagePathValidationTests {
        @Test
        void givenValidImagePath_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Options(1L, VALID_HEX_6, VALID_PATH, true, true));
        }

        @Test
        void givenEmptyImagePath_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Options(1L, VALID_HEX_6, "", false, true));
        }

        @Test
        @SuppressWarnings("ConstantConditions")
        void givenNullImagePath_whenCreatingOptions_thenNullPointerExceptionIsThrown() {
            NullPointerException exception =
                    assertThrows(
                            NullPointerException.class,
                            () -> new Options(1L, VALID_HEX_6, null, false, true));
            assertEquals("imagepath must not be null", exception.getMessage());
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
                            .hexcolor(VALID_HEX_6)
                            .imagepath(VALID_PATH)
                            .isimage(true)
                            .build();
            assertNotNull(options);
            assertEquals(1L, options.getOptionsid());
            assertEquals(VALID_HEX_6, options.getHexcolor());
            assertEquals(VALID_PATH, options.getImagepath());
            assertTrue(options.getIsimage());
        }

        @Test
        void givenNoParameters_whenUsingBuilder_thenOptionsIsCreatedWithDefaults() {
            Options options = Options.builder().build();
            assertNotNull(options);
            assertNull(options.getOptionsid());
            assertEquals("#000000", options.getHexcolor());
            assertEquals("", options.getImagepath());
            assertFalse(options.getIsimage());
        }

        @Test
        void givenInvalidHexColor_whenBuilding_thenIllegalArgumentExceptionIsThrown() {
            IllegalArgumentException exception =
                    assertThrows(
                            IllegalArgumentException.class, this::buildOptionsWithInvalidColor);
            assertEquals(
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
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
            String newHexColor = "#FF0000";
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
                    "hexcolor must be a valid hex color format (e.g., #FFFFFF or #FFF)",
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
            options.setIsimage(true);
            assertTrue(options.getIsimage());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {
        @Test
        void givenTwoOptionssWithSameProperties_whenComparingEquality_thenTheyAreEqual() {
            Options bg1 = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            Options bg2 = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            assertEquals(bg1, bg2);
            assertEquals(bg1.hashCode(), bg2.hashCode());
        }

        @Test
        void givenTwoOptionssWithDifferentProperties_whenComparingEquality_thenTheyAreNotEqual() {
            Options bg1 = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            Options bg2 = new Options(2L, VALID_HEX_6, VALID_PATH, true, true);
            assertNotEquals(bg1, bg2);
        }

        @Test
        @SuppressWarnings("EqualsWithItself")
        void givenSameOptionsInstance_whenComparingToItself_thenTheyAreEqual() {
            Options options = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            assertEquals(options, options);
        }

        @Test
        void givenOptionsAndNull_whenComparingEquality_thenTheyAreNotEqual() {
            Options options = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            assertNotEquals(null, options);
        }

        @Test
        void givenOptionsAndDifferentClass_whenComparingEquality_thenTheyAreNotEqual() {
            Options options = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            Object notOptions = new Object();
            assertNotEquals(options, notOptions);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        void givenOptionsInstance_whenCallingToString_thenFormattedStringIsReturned() {
            Options options = new Options(1L, VALID_HEX_6, VALID_PATH, true, true);
            String result = options.toString();
            assertNotNull(result);
            assertTrue(result.contains("Options{"));
            assertTrue(result.contains("optionsid=1"));
            assertTrue(result.contains("hexcolor='#FFFFFF'"));
            assertTrue(result.contains("imagepath='/images/background.jpg'"));
            assertTrue(result.contains("isimage=true"));
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        @Test
        void givenNullOptionsId_whenCreatingOptions_thenNoExceptionIsThrown() {
            assertDoesNotThrow(() -> new Options(null, VALID_HEX_6, VALID_PATH, false, true));
        }

        @Test
        void givenMaximumLengthStrings_whenCreatingOptions_thenNoExceptionIsThrown() {
            String maxHexColor = "#FFFFFF"; // 7 characters (max 8)
            String maxImagePath = "a".repeat(260); // Max 260 characters
            assertDoesNotThrow(() -> new Options(1L, maxHexColor, maxImagePath, false, true));
        }

        @Test
        void givenMixedCaseHexColors_whenCreatingOptions_thenNoExceptionIsThrown() {
            String lowerCase = "#ffffff";
            String upperCase = "#FFFFFF";
            String mixedCase = "#FfFfFf";
            assertDoesNotThrow(() -> new Options(1L, lowerCase, VALID_PATH, false, true));
            assertDoesNotThrow(() -> new Options(1L, upperCase, VALID_PATH, false, true));
            assertDoesNotThrow(() -> new Options(1L, mixedCase, VALID_PATH, false, true));
        }
    }
}
