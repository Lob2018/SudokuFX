/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.viewmodel;

import java.io.File;
import java.text.MessageFormat;
import java.time.Year;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import org.apache.logging.log4j.internal.annotation.SuppressFBWarnings;
import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.AppPaths;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ScreenSize;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.component.MyAlert;

/**
 * ViewModel responsible for displaying the help dialog. It retrieves application and system
 * information to show in an informational alert.
 */
@Component
public class HelpViewModel {

    private static final double ALERT_SIZE_RATIO = 0.6;
    private final IOsFolder iOsFolder;
    private final Coordinator coordinator;

    /**
     * Constructs the HelpViewModel with required dependencies.
     *
     * @param iOsFolder service providing OS folder paths
     * @param coordinator navigation coordinator for external actions
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification =
                    "Injected infrastructure services must be stored by reference for"
                            + " cross-component coordination.")
    public HelpViewModel(IOsFolder iOsFolder, Coordinator coordinator) {
        this.iOsFolder = iOsFolder;
        this.coordinator = coordinator;
    }

    /**
     * Shows the help dialog with application and system information. Adds a "Become a sponsor!"
     * button to the alert.
     */
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
                        iOsFolder.getOsLogsFolderPath(),
                        JVMApplicationProperties.INSTANCE.getAppName(),
                        JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty()
                                ? ""
                                : JVMApplicationProperties.INSTANCE.getAppVersion().substring(1),
                        JVMApplicationProperties.INSTANCE.getAppOrganization(),
                        Year.now().toString(),
                        JVMApplicationProperties.INSTANCE.getAppLicense()));
        addLogFileButton(informationAlert);
        addWebsiteButton(informationAlert);
        displayAlert(informationAlert);
    }

    /**
     * Adds a "Visit my website" button to the given alert.
     *
     * <p>The button is aligned to the left and opens my website page when clicked.
     *
     * @param informationAlert the alert to which the website button will be added; must not be null
     * @throws NullPointerException if informationAlert is null
     */
    private void addWebsiteButton(MyAlert informationAlert) {
        Objects.requireNonNull(informationAlert, "informationAlert must not be null");
        ButtonType websiteButtonType =
                new ButtonType(
                        I18n.INSTANCE.getValue("menu.button.help.dialog.information.website"),
                        ButtonBar.ButtonData.LEFT);
        informationAlert.getButtonTypes().add(websiteButtonType);
        Button websiteButton =
                (Button) informationAlert.getDialogPane().lookupButton(websiteButtonType);
        websiteButton.setOnAction(e -> coordinator.openMyWebsiteUrl());
    }

    /**
     * Adds an "Open log file" button to the given alert.
     *
     * <p>The button is aligned to the left and opens the system log file when clicked. The file
     * path is resolved via {@code iOsFolder.getOsLogsFolderPath()} and validated beforehand.
     *
     * @param informationAlert the alert to which the log file button will be added; must not be
     *     null
     * @throws NullPointerException if {@code informationAlert} is null or if the resolved file is
     *     null
     * @throws IllegalArgumentException if the resolved path is blank
     */
    private void addLogFileButton(MyAlert informationAlert) {
        Objects.requireNonNull(informationAlert, "informationAlert must not be null");
        String logPath = iOsFolder.getOsLogsFolderPath() + AppPaths.LOGS_FILE_NAME_PATH.getPath();
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                logPath, "logPath must not be null or blank, but was " + logPath);
        File file = new File(logPath);
        Objects.requireNonNull(file, "file must not be null");
        ButtonType fileButtonType =
                new ButtonType(
                        I18n.INSTANCE.getValue("menu.button.help.dialog.information.logfile"),
                        ButtonBar.ButtonData.LEFT);
        informationAlert.getButtonTypes().add(fileButtonType);
        Button fileButton = (Button) informationAlert.getDialogPane().lookupButton(fileButtonType);
        fileButton.setOnAction(e -> coordinator.openLocalFile(file));
    }

    /**
     * Displays the given alert and waits for user interaction.
     *
     * @param alert the alert to display; must not be null
     * @throws NullPointerException if alert is null
     */
    void displayAlert(MyAlert alert) {
        Objects.requireNonNull(alert, "alert must not be null");
        alert.showAndWait();
    }
}
