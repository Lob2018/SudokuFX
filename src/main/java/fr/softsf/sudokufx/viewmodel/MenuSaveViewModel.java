/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.dto.GameDto;
import fr.softsf.sudokufx.dto.GameLevelDto;
import fr.softsf.sudokufx.dto.GridDto;

/**
 * ViewModel for managing backup menu UI state and accessibility texts.
 *
 * <p>Holds an observable list of saved games, the currently selected backup, and provides localized
 * StringBindings for UI labels, accessibility, tooltips, and role descriptions.
 *
 * <p>Uses I18n singleton for localization with automatic updates on locale changes.
 */
@Component
public class MenuSaveViewModel {

    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED =
            "menu.accessibility.role.description.opened";
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";
    private static final int TEST_BACKUP_START = 31;
    private static final int TEST_BACKUP_END = 11;
    private static final int TEST_BACKUP_SELECTED_MINUTE = 25;
    private static final int TEST_GRID_STRING_LENGTH = 81;
    private static final int TEST_GRID_STRING_LENGTH_ALT = 810;
    private static final int TEST_GRID_BYTE_VALUE = 75;

    private final ObservableList<GameDto> backups = FXCollections.observableArrayList();
    private final ObjectProperty<GameDto> selectedBackup = new SimpleObjectProperty<>();

    private final StringBinding saveAccessibleText;
    private final StringBinding saveTooltip;
    private final StringBinding saveRoleDescription;
    private final StringBinding saveText;
    private final StringBinding reduceAccessibleText;
    private final StringBinding reduceTooltip;
    private final StringBinding reduceText;
    private final StringBinding backupAccessibleText;
    private final StringBinding backupRoleDescription;
    private final StringBinding backupText;
    private final StringBinding backupTooltip;
    private final StringBinding maxiBackupAccessibleText;
    private final StringBinding maxiBackupRoleDescription;
    private final StringBinding maxiBackupText;
    private final StringBinding maxiBackupTooltip;
    private final StringBinding cellDeleteAccessibleText;
    private final StringBinding cellConfirmationTitle;
    private final StringBinding cellConfirmationMessage;

    public MenuSaveViewModel() {
        saveAccessibleText = createStringBinding("menu.save.button.save.accessibility");
        saveTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.save.button.save.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        saveRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_OPENED);
        saveText = createStringBinding("menu.save.button.save.text");
        reduceAccessibleText = createStringBinding("menu.save.button.reduce.accessibility");
        reduceTooltip = createStringBinding("menu.save.button.reduce.accessibility");
        reduceText = createStringBinding("menu.save.button.reduce.text");
        backupAccessibleText = createStringBinding("menu.save.button.backup.accessibility");
        backupRoleDescription =
                createStringBinding("menu.accessibility.role.description.submenu.option");
        backupText = createStringBinding("menu.save.button.backup.text");
        backupTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.save.button.backup.accessibility",
                        "menu.accessibility.role.description.submenu.option");
        maxiBackupAccessibleText = createStringBinding("menu.maxi.button.backup.accessibility");
        maxiBackupRoleDescription = createStringBinding(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        maxiBackupText = createStringBinding("menu.maxi.button.backup.text");
        maxiBackupTooltip =
                createFormattedAndConcatenatedBinding(
                        "menu.maxi.button.backup.accessibility",
                        MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED);
        cellDeleteAccessibleText =
                createStringBinding("menu.save.button.backup.cell.delete.accessibility");
        cellConfirmationTitle =
                createStringBinding("menu.save.button.backup.dialog.confirmation.title");
        cellConfirmationMessage =
                createStringBinding("menu.save.button.backup.dialog.confirmation.message");
        loadBackups();
        setSelectedBackup();
    }

    /**
     * Creates a simple localized StringBinding for the given key. Updates automatically when the
     * locale changes.
     *
     * @param key the i18n key
     * @return a StringBinding for the localized value
     */
    private StringBinding createStringBinding(String key) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key), I18n.INSTANCE.localeProperty());
    }

    /**
     * Creates a localized StringBinding by concatenating the value of two keys. Updates
     * automatically when the locale changes.
     *
     * @param key the base key
     * @param suffixKey the key for the suffix to append
     * @return a StringBinding for the combined localized value
     */
    private StringBinding createFormattedAndConcatenatedBinding(String key, String suffixKey) {
        return Bindings.createStringBinding(
                () -> I18n.INSTANCE.getValue(key) + I18n.INSTANCE.getValue(suffixKey),
                I18n.INSTANCE.localeProperty());
    }

    /**
     * Loads test backups into the observable list. The backup at minute +25 is marked as selected.
     */
    private void loadBackups() {
        backups.clear();
        for (int i = TEST_BACKUP_START; i >= TEST_BACKUP_END; i--) {
            if (i == TEST_BACKUP_SELECTED_MINUTE) {
                backups.add(
                        createBackupGameDto(
                                LocalDateTime.now()
                                        .plusMinutes(i)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant(),
                                true));
            } else {
                backups.add(
                        createBackupGameDto(
                                LocalDateTime.now()
                                        .plusMinutes(i)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant(),
                                false));
            }
        }
    }

    /**
     * Sets the selected backup to the one marked as selected, or defaults to the first in the list
     * if none are selected.
     */
    private void setSelectedBackup() {
        if (backups.isEmpty()) {
            return;
        }
        backups.stream()
                .filter(GameDto::selected)
                .findFirst()
                .ifPresentOrElse(selectedBackup::set, () -> selectedBackup.set(backups.getFirst()));
    }

    /**
     * Generates a sample GameDto instance for testing purposes.
     *
     * @param date the timestamp of the backup
     * @param isSelected whether the backup is selected
     * @return a GameDto populated with mock data
     */
    private GameDto createBackupGameDto(Instant date, boolean isSelected) {
        return new GameDto(
                0L,
                new GridDto(
                        0L,
                        "0".repeat(TEST_GRID_STRING_LENGTH),
                        "1".repeat(TEST_GRID_STRING_LENGTH_ALT),
                        (byte) TEST_GRID_BYTE_VALUE),
                0L,
                new GameLevelDto((byte) 1, (byte) 1),
                isSelected,
                Instant.now(),
                date);
    }

    public ObservableList<GameDto> getBackups() {
        return backups;
    }

    public ObjectProperty<GameDto> selectedBackupProperty() {
        return selectedBackup;
    }

    public StringBinding saveAccessibleTextProperty() {
        return saveAccessibleText;
    }

    public StringBinding saveTooltipProperty() {
        return saveTooltip;
    }

    public StringBinding saveRoleDescriptionProperty() {
        return saveRoleDescription;
    }

    public StringBinding saveTextProperty() {
        return saveText;
    }

    public StringBinding reduceAccessibleTextProperty() {
        return reduceAccessibleText;
    }

    public StringBinding reduceTooltipProperty() {
        return reduceTooltip;
    }

    public StringBinding reduceTextProperty() {
        return reduceText;
    }

    public StringBinding backupAccessibleTextProperty() {
        return backupAccessibleText;
    }

    public StringBinding backupTooltipProperty() {
        return backupTooltip;
    }

    public StringBinding backupRoleDescriptionProperty() {
        return backupRoleDescription;
    }

    public StringBinding backupTextProperty() {
        return backupText;
    }

    public StringBinding maxiBackupAccessibleTextProperty() {
        return maxiBackupAccessibleText;
    }

    public StringBinding maxiBackupTooltipProperty() {
        return maxiBackupTooltip;
    }

    public StringBinding maxiBackupRoleDescriptionProperty() {
        return maxiBackupRoleDescription;
    }

    public StringBinding maxiBackupTextProperty() {
        return maxiBackupText;
    }

    public StringBinding cellDeleteAccessibleTextProperty() {
        return cellDeleteAccessibleText;
    }

    public StringBinding cellConfirmationTitleProperty() {
        return cellConfirmationTitle;
    }

    public StringBinding cellConfirmationMessageProperty() {
        return cellConfirmationMessage;
    }
}
