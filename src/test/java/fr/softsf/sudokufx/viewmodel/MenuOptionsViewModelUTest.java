/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.*;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.common.util.ImageMeta;
import fr.softsf.sudokufx.common.util.ImageUtils;
import fr.softsf.sudokufx.service.business.OptionsService;
import fr.softsf.sudokufx.service.ui.AsyncFileProcessorService;
import fr.softsf.sudokufx.service.ui.AudioService;
import fr.softsf.sudokufx.view.component.SpinnerGridPane;
import fr.softsf.sudokufx.view.component.toaster.ToasterVBox;
import fr.softsf.sudokufx.viewmodel.state.AbstractPlayerStateTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MenuOptionsViewModelUTest extends AbstractPlayerStateTest {

    private Locale originalLocale;
    private MenuOptionsViewModel viewModel;
    private GridPane sudokuFX;
    private ColorPicker colorPicker;
    private ToasterVBox toasterMock;
    private SpinnerGridPane spinnerMock;
    private AsyncFileProcessorService asyncServiceMock;
    private AudioService audioSpy;
    private OptionsService optionsService;

    @BeforeEach
    void givenDependencies_whenInitViewModel_thenViewModelInitialized() {
        originalLocale = I18n.INSTANCE.localeProperty().get();
        I18n.INSTANCE.setLocaleBundle("FR");
        sudokuFX = new GridPane();
        colorPicker = new ColorPicker();
        toasterMock = mock(ToasterVBox.class);
        spinnerMock = mock(SpinnerGridPane.class);
        asyncServiceMock = mock(AsyncFileProcessorService.class);
        audioSpy = spy(new AudioService());
        optionsService = mock(OptionsService.class);
        viewModel =
                spy(
                        new MenuOptionsViewModel(
                                audioSpy, asyncServiceMock, playerStateHolder, optionsService));
        viewModel.init(sudokuFX, colorPicker, toasterMock, spinnerMock);
    }

    @AfterEach
    void givenLocaleRestored_whenTestFinished_thenLocaleReset() {
        I18n.INSTANCE.localeProperty().set(originalLocale);
    }

    @Test
    void givenViewModel_whenAccessBindings_thenAllBindingsReturnNonNullAndNonBlank() {
        List<StringBinding> bindings =
                List.of(
                        viewModel.optionsMenuMaxiAccessibleTextProperty(),
                        viewModel.optionsMenuMaxiTooltipProperty(),
                        viewModel.optionsMenuMaxiRoleDescriptionProperty(),
                        viewModel.optionsMenuMaxiTextProperty(),
                        viewModel.optionsReduceAccessibleTextProperty(),
                        viewModel.optionsReduceTooltipProperty(),
                        viewModel.optionsReduceTextProperty(),
                        viewModel.optionsAccessibleTextProperty(),
                        viewModel.optionsTooltipProperty(),
                        viewModel.optionsRoleDescriptionProperty(),
                        viewModel.optionsTextProperty(),
                        viewModel.optionsImageAccessibleTextProperty(),
                        viewModel.optionsImageTooltipProperty(),
                        viewModel.optionsImageRoleDescriptionProperty(),
                        viewModel.optionsImageTextProperty(),
                        viewModel.optionsColorAccessibleTextProperty(),
                        viewModel.optionsColorTooltipProperty(),
                        viewModel.optionsColorRoleDescriptionProperty(),
                        viewModel.optionsOpacityAccessibleTextProperty(),
                        viewModel.optionsOpacityTooltipProperty(),
                        viewModel.optionsOpacityRoleDescriptionProperty(),
                        viewModel.optionsOpacityTextProperty(),
                        viewModel.optionsOpacityIconProperty(),
                        viewModel.optionsMuteAccessibleTextProperty(),
                        viewModel.optionsMuteTooltipProperty(),
                        viewModel.optionsMuteRoleDescriptionProperty(),
                        viewModel.optionsMuteTextProperty(),
                        viewModel.optionsMuteIconProperty(),
                        viewModel.optionsImageAccessibleTextProperty(),
                        viewModel.optionsImageTooltipProperty(),
                        viewModel.optionsImageRoleDescriptionProperty());
        for (StringBinding binding : bindings) {
            assertNotNull(binding.get(), "Binding should not return null");
            assertFalse(binding.get().isBlank(), "Binding should not be blank");
        }
    }

    @Test
    void givenViewModelAndColorPicker_whenInit_thenColorIsSetAndColorPickerUpdated() {
        Color expectedColor = Color.rgb(153, 179, 255, 0.803921568627451);
        assertEquals(expectedColor, colorPicker.getValue());
        BackgroundFill fill = sudokuFX.getBackground().getFills().getFirst();
        assertEquals(expectedColor, fill.getFill());
    }

    @Test
    void givenGridPaneAndColor_whenUpdateBackgroundColor_thenColorApplied() {
        Color color = Color.web("#12345678");
        viewModel.updateOptionsColorAndApply(sudokuFX, color);
        BackgroundFill fill = sudokuFX.getBackground().getFills().getFirst();
        assertEquals(color, fill.getFill());
    }

    @Nested
    @DisplayName("Background Image Error Handling")
    class BackgroundImageErrorTests {

        @Test
        void givenNullFile_whenLoadBackgroundImage_thenShowErrorToast() {
            doNothing().when(toasterMock).addToast(anyString(), anyString(), any(), anyBoolean());
            viewModel.loadBackgroundImage(null, spinnerMock, sudokuFX);
            verifyNoInteractions(asyncServiceMock);
            verify(toasterMock, atLeastOnce())
                    .addToast(
                            I18n.INSTANCE.getValue(
                                    "toast.error.optionsviewmodel.handlefileimagechooser"),
                            "",
                            ToastLevels.ERROR,
                            true);
        }

        @Test
        void givenInvalidFile_whenLoadBackgroundImage_thenShowErrorToast() {
            File invalidFile = new File("invalid.txt");
            doNothing().when(toasterMock).addToast(anyString(), anyString(), any(), anyBoolean());
            viewModel.loadBackgroundImage(invalidFile, spinnerMock, sudokuFX);
            verifyNoInteractions(asyncServiceMock);
            verify(toasterMock, atLeastOnce())
                    .addToast(
                            I18n.INSTANCE.getValue(
                                    "toast.error.optionsviewmodel.handlefileimagechooser"),
                            "",
                            ToastLevels.ERROR,
                            true);
        }
    }

    @Nested
    @DisplayName("Background Image Success Handling")
    class BackgroundImageSuccessTests {

        @Test
        void givenValidFile_whenLoadBackgroundImage_thenAsyncServiceCalled() throws Exception {
            File validFile = mock(File.class);
            doReturn(true).when(validFile).exists();
            ImageUtils imageUtilsSpy = spy(new ImageUtils());
            doReturn(true).when(imageUtilsSpy).isValidImage(validFile);
            Field field = MenuOptionsViewModel.class.getDeclaredField("imageUtils");
            field.setAccessible(true);
            field.set(viewModel, imageUtilsSpy);
            viewModel.loadBackgroundImage(validFile, spinnerMock, sudokuFX);
            verify(asyncServiceMock)
                    .processFileAsync(
                            eq(validFile), eq(spinnerMock), eq(toasterMock), any(), any());
        }

        @Test
        void givenNonExistentFile_whenLoadBackgroundImage_thenToastErrorAndNoAsyncCall()
                throws Exception {
            File nonExistentFile = mock(File.class);
            doReturn(false).when(nonExistentFile).exists(); // Fichier inexistant
            ImageUtils imageUtilsSpy = spy(new ImageUtils());
            doReturn(true).when(imageUtilsSpy).isValidImage(nonExistentFile);
            Field field = MenuOptionsViewModel.class.getDeclaredField("imageUtils");
            field.setAccessible(true);
            field.set(viewModel, imageUtilsSpy);
            viewModel.loadBackgroundImage(nonExistentFile, spinnerMock, sudokuFX);
            verify(asyncServiceMock, never()).processFileAsync(any(), any(), any(), any(), any());
            verify(toasterMock, atLeast(1))
                    .addToast(anyString(), anyString(), eq(ToastLevels.ERROR), eq(true));
        }
    }

    @Nested
    @DisplayName("Options Toggle Tests")
    class OptionsToggleTests {

        @Test
        void givenInitialGridOpacity_whenToggleGridOpacity_thenPropertyInverted() {
            boolean initial = viewModel.gridOpacityProperty().get();
            boolean toggled = viewModel.toggleGridOpacity();
            assertEquals(!initial, toggled);
            assertEquals(toggled, viewModel.gridOpacityProperty().get());
        }

        @Test
        void givenInitialMuteState_whenToggleMute_thenAudioServiceAndPropertyUpdated() {
            boolean initial = audioSpy.isMuted();
            viewModel.toggleMute();
            verify(audioSpy).setMuted(!initial);
            assertEquals(!initial, audioSpy.isMuted());
        }
    }

    @Nested
    @DisplayName("Utility Text Methods Tests")
    class UtilityTextMethodsTests {

        static Stream<Arguments> utilityTextMethodsProvider() {
            return Stream.of(
                    Arguments.of(
                            "gridOpacityText", true, "menu.options.button.opacity.text.opaque"),
                    Arguments.of(
                            "gridOpacityText",
                            false,
                            "menu.options.button.opacity.text.transparent"),
                    Arguments.of("muteText", true, "menu.options.button.mute.text.muted"),
                    Arguments.of("muteText", false, "menu.options.button.mute.text.unmuted"),
                    Arguments.of("muteInfo", true, "menu.options.button.mute.text.muted.info"),
                    Arguments.of("muteInfo", false, "menu.options.button.mute.text.unmuted.info"));
        }

        @ParameterizedTest(name = "{0}({1}) should return correct I18n value")
        @MethodSource("utilityTextMethodsProvider")
        void givenMethodAndInput_whenCallUtilityTextMethod_thenReturnExpectedValue(
                String methodName, boolean input, String expectedKey) throws Exception {
            var method = MenuOptionsViewModel.class.getDeclaredMethod(methodName, boolean.class);
            method.setAccessible(true);
            String result = (String) method.invoke(viewModel, input);
            assertEquals(I18n.INSTANCE.getValue(expectedKey), result);
        }
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void givenValidFile_whenLoadBackgroundImage_thenAsyncServiceCalledAndBackgroundSet()
            throws Exception {
        File imageFile = new File("src/test/resources/sample.jpg");
        SpinnerGridPane spinnerMockLocal = mock(SpinnerGridPane.class);
        GridPane gridMock = new GridPane();
        ToasterVBox toasterMockLocal = mock(ToasterVBox.class);
        ImageUtils imageUtilsSpy = spy(new ImageUtils());
        MenuOptionsViewModel vm =
                new MenuOptionsViewModel(
                        new AudioService(), asyncServiceMock, playerStateHolder, optionsService);
        Field imageUtilsField = MenuOptionsViewModel.class.getDeclaredField("imageUtils");
        imageUtilsField.setAccessible(true);
        imageUtilsField.set(vm, imageUtilsSpy);
        Field toasterField = MenuOptionsViewModel.class.getDeclaredField("toaster");
        toasterField.setAccessible(true);
        toasterField.set(vm, toasterMockLocal);
        doReturn(true).when(imageUtilsSpy).isValidImage(imageFile);
        ImageMeta metaMock = mock(ImageMeta.class);
        doReturn(metaMock).when(imageUtilsSpy).getImageMeta(imageFile);
        doReturn(100).when(metaMock).width();
        doReturn(50).when(metaMock).height();
        doReturn(2.0).when(metaMock).scaleFactor();
        ArgumentCaptor<Function<File, BackgroundImage>> processorCaptor =
                ArgumentCaptor.forClass((Class) Function.class);
        doNothing()
                .when(asyncServiceMock)
                .processFileAsync(
                        eq(imageFile),
                        eq(spinnerMockLocal),
                        eq(toasterMockLocal),
                        processorCaptor.capture(),
                        any());
        vm.loadBackgroundImage(imageFile, spinnerMockLocal, gridMock);
        verify(asyncServiceMock)
                .processFileAsync(
                        eq(imageFile), eq(spinnerMockLocal), eq(toasterMockLocal), any(), any());
        BackgroundImage backgroundImage = processorCaptor.getValue().apply(imageFile);
        assertNotNull(backgroundImage, "The BackgroundImage must be created correctly");
    }
}
