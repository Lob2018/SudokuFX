/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.application.Platform;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.config.os.IOsFolderFactory;
import fr.softsf.sudokufx.config.os.OsFolderFactoryManager;
import fr.softsf.sudokufx.view.component.MyAlert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(ApplicationExtension.class)
class HelpViewModelUTest {

    private final IOsFolderFactory iOsFolderFactory;

    public HelpViewModelUTest() {
        this.iOsFolderFactory = new OsFolderFactoryManager().iOsFolderFactory();
    }

    @Test
    void whenShowHelp_thenAlertIsBuilt_andDisplayed_nonBlocking(FxRobot robot) {
        HelpViewModel spyViewModel = Mockito.spy(new HelpViewModel(iOsFolderFactory));
        doAnswer(
                        invocation -> {
                            MyAlert alert = invocation.getArgument(0);
                            Platform.runLater(alert::show);
                            return null;
                        })
                .when(spyViewModel)
                .displayAlert(any());
        robot.interact(spyViewModel::showHelp);
        ArgumentCaptor<MyAlert> captor = ArgumentCaptor.forClass(MyAlert.class);
        verify(spyViewModel).displayAlert(captor.capture());
        MyAlert alert = captor.getValue();
        assertEquals(
                I18n.INSTANCE.getValue("menu.button.help.dialog.information.title"),
                alert.getTitle());
    }

    @AfterEach
    void cleanup() throws Exception {
        FxToolkit.cleanupStages();
    }
}
