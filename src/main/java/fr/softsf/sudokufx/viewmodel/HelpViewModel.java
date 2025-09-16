/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.text.MessageFormat;
import java.time.Year;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.ScreenSize;
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
        addSponsorButton(informationAlert);
        displayAlert(informationAlert);
    }

    /**
     * Adds a "Become a sponsor!" button to the given alert.
     *
     * <p>The button is aligned to the left and opens the GitHub Sponsor page when clicked.
     *
     * @param informationAlert the alert to which the sponsor button will be added; must not be null
     * @throws NullPointerException if informationAlert is null
     */
    private void addSponsorButton(MyAlert informationAlert) {
        Objects.requireNonNull(informationAlert, "informationAlert must not be null");
        ButtonType sponsorButtonType =
                new ButtonType(
                        I18n.INSTANCE.getValue("menu.button.help.dialog.information.sponsor"),
                        ButtonBar.ButtonData.LEFT);
        informationAlert.getButtonTypes().add(sponsorButtonType);
        Button sponsorButton =
                (Button) informationAlert.getDialogPane().lookupButton(sponsorButtonType);
        sponsorButton.setOnAction(e -> coordinator.openGitHubSponsorUrl());
    }

    /**
     * Displays the given alert and waits for user interaction.
     *
     * <p>Applies the hand cursor style to all alert buttons before showing.
     *
     * @param alert the alert to display; must not be null
     * @throws NullPointerException if alert is null
     */
    void displayAlert(MyAlert alert) {
        Objects.requireNonNull(alert, "alert must not be null");
        alert.applyHandCursorToButton();
        alert.showAndWait();
    }
}
