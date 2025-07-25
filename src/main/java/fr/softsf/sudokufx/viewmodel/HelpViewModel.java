/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.text.MessageFormat;
import java.time.Year;
import javafx.scene.control.Alert;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ScreenSize;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.config.os.IOsFolderFactory;
import fr.softsf.sudokufx.view.component.MyAlert;

/**
 * ViewModel responsible for displaying the help dialog. It retrieves application and system
 * information to show in an informational alert.
 */
@Component
public class HelpViewModel {

    private static final double ALERT_SIZE_RATIO = 0.6;
    private final IOsFolderFactory iOsFolderFactory;

    public HelpViewModel(IOsFolderFactory iOsFolderFactory) {
        this.iOsFolderFactory = iOsFolderFactory;
    }

    public void showHelp() {
        MyAlert informationAlert =
                new MyAlert(
                        Alert.AlertType.INFORMATION,
                        ScreenSize.DISPOSABLE_SIZE.getSize() * ALERT_SIZE_RATIO);
        informationAlert.setTitle(
                I18n.INSTANCE.getValue("menu.button.help.dialog.information.title"));
        informationAlert.setHeaderText(null);
        informationAlert.setContentText(
                MessageFormat.format(
                        I18n.INSTANCE.getValue("menu.button.help.dialog.information.message"),
                        iOsFolderFactory.getOsLogsFolderPath(),
                        JVMApplicationProperties.INSTANCE.getAppName(),
                        JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty()
                                ? ""
                                : JVMApplicationProperties.INSTANCE.getAppVersion().substring(1),
                        JVMApplicationProperties.INSTANCE.getAppOrganization(),
                        Year.now() + "",
                        JVMApplicationProperties.INSTANCE.getAppLicense()));
        displayAlert(informationAlert);
    }

    void displayAlert(Alert alert) {
        alert.showAndWait();
    }
}
