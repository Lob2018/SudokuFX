package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.view.components.MyAlert;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Year;

/**
 * ViewModel responsible for displaying the help dialog.
 * It retrieves application and system information to show in an informational alert.
 */
@Component
public class HelpViewModel {

    private final IOsFolderFactory iOsFolderFactory;

    public HelpViewModel(IOsFolderFactory iOsFolderFactory) {
        this.iOsFolderFactory = iOsFolderFactory;
    }

    public void showHelp() {
        MyAlert informationAlert = new MyAlert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(I18n.INSTANCE.getValue("menu.button.help.dialog.information.title"));
        informationAlert.setHeaderText(null);
        informationAlert.setContentText(MessageFormat.format(
                I18n.INSTANCE.getValue("menu.button.help.dialog.information.message"),
                iOsFolderFactory.getOsLogsFolderPath(),
                JVMApplicationProperties.INSTANCE.getAppName(),
                JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty() ? "" : JVMApplicationProperties.INSTANCE.getAppVersion().substring(1),
                JVMApplicationProperties.INSTANCE.getAppOrganization(),
                Year.now() + "",
                JVMApplicationProperties.INSTANCE.getAppLicense()
        ));
        informationAlert.showAndWait();
    }
}
