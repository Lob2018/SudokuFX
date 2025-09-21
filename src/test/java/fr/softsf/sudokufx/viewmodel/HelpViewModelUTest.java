/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.config.os.OsFoldersConfig;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.component.MyAlert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(ApplicationExtension.class)
class HelpViewModelUTest {

    private final IOsFolder iOsFolder;
    private final Coordinator mockCoordinator;

    public HelpViewModelUTest() {
        this.iOsFolder = new OsFoldersConfig().iOsFolderFactory();
        this.mockCoordinator = Mockito.mock(Coordinator.class);
    }

    @Test
    void whenShowHelp_thenAlertIsBuilt_withCorrectTitle_andWebsiteButton(FxRobot robot) {
        HelpViewModel spyViewModel = Mockito.spy(new HelpViewModel(iOsFolder, mockCoordinator));
        doAnswer(
                        invocation -> {
                            Platform.runLater(invocation.getArgument(0, MyAlert.class)::show);
                            return null;
                        })
                .when(spyViewModel)
                .displayAlert(any());
        robot.interact(spyViewModel::showHelp);
        ArgumentCaptor<MyAlert> captor = ArgumentCaptor.forClass(MyAlert.class);
        verify(spyViewModel).displayAlert(captor.capture());
        MyAlert capturedAlert = captor.getValue();
        assertEquals(
                I18n.INSTANCE.getValue("menu.button.help.dialog.information.title"),
                capturedAlert.getTitle());
        String websiteText = I18n.INSTANCE.getValue("menu.button.help.dialog.information.website");
        ButtonType websiteButtonType =
                capturedAlert.getButtonTypes().stream()
                        .filter(bt -> websiteText.equals(bt.getText()))
                        .findFirst()
                        .orElse(null);
        assertNotNull(websiteButtonType, "Website button should exist");
        Button websiteButton =
                (Button) capturedAlert.getDialogPane().lookupButton(websiteButtonType);
        websiteButton.getOnAction().handle(null);
        verify(mockCoordinator).openMyWebsiteUrl();
    }

    @Test
    void whenShowHelp_thenAlertIsBuilt_withCorrectTitle_andSupportButton(FxRobot robot) {
        HelpViewModel spyViewModel = Mockito.spy(new HelpViewModel(iOsFolder, mockCoordinator));
        doAnswer(
                        invocation -> {
                            Platform.runLater(invocation.getArgument(0, MyAlert.class)::show);
                            return null;
                        })
                .when(spyViewModel)
                .displayAlert(any());
        robot.interact(spyViewModel::showHelp);
        ArgumentCaptor<MyAlert> captor = ArgumentCaptor.forClass(MyAlert.class);
        verify(spyViewModel).displayAlert(captor.capture());
        MyAlert capturedAlert = captor.getValue();
        assertEquals(
                I18n.INSTANCE.getValue("menu.button.help.dialog.information.title"),
                capturedAlert.getTitle());
        String supportText = I18n.INSTANCE.getValue("menu.button.help.dialog.information.donation");
        ButtonType supportButtonType =
                capturedAlert.getButtonTypes().stream()
                        .filter(bt -> supportText.equals(bt.getText()))
                        .findFirst()
                        .orElse(null);
        assertNotNull(supportButtonType, "Donation button should exist");
        Button supportButton =
                (Button) capturedAlert.getDialogPane().lookupButton(supportButtonType);
        supportButton.getOnAction().handle(null);
        verify(mockCoordinator).openMyKoFiUrl();
    }

    @Test
    void givenMyAlert_whenApplyHandCursorToButton_thenAllButtonsHaveHandCursor(FxRobot robot) {
        robot.interact(
                () -> {
                    MyAlert alert = new MyAlert(Alert.AlertType.INFORMATION);
                    ButtonType okButton = ButtonType.OK;
                    ButtonType cancelButton = ButtonType.CANCEL;
                    alert.getButtonTypes().setAll(okButton, cancelButton);
                    alert.applyHandCursorToButton();
                    Button ok = (Button) alert.getDialogPane().lookupButton(okButton);
                    Button cancel = (Button) alert.getDialogPane().lookupButton(cancelButton);
                    assertNotNull(ok);
                    assertNotNull(cancel);
                    assertEquals("-fx-cursor: hand;", ok.getStyle());
                    assertEquals("-fx-cursor: hand;", cancel.getStyle());
                });
    }

    @Test
    void givenNullAlert_whenDisplayAlert_thenThrowsNPE() {
        HelpViewModel viewModel = new HelpViewModel(iOsFolder, mockCoordinator);
        assertThrows(NullPointerException.class, () -> viewModel.displayAlert(null));
    }

    @AfterEach
    void cleanup() throws Exception {
        FxToolkit.cleanupStages();
    }
}
