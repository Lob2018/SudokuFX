/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.springframework.stereotype.Component;

import fr.softsf.sudokufx.enums.I18n;

/** MenuMiniViewModel with business logic (not final) */
@Component
public class MenuMiniViewModel {
    private static final String MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED =
            "menu.accessibility.role.description.closed";
    // Accessible texts
    private final StringProperty menuMiniButtonShowAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonPlayerAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonSolveAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonBackupAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonBackgroundAccessibleText =
            new SimpleStringProperty();
    private final StringProperty menuMiniButtonLanguageAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonLanguageIso = new SimpleStringProperty();
    private final StringProperty menuMiniButtonHelpAccessibleText = new SimpleStringProperty();
    private final StringProperty menuMiniButtonNewAccessibleText = new SimpleStringProperty();

    // Tooltips
    private final StringProperty menuMiniButtonShowTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonPlayerTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonSolveTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonBackupTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonBackgroundTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonLanguageTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonHelpTooltip = new SimpleStringProperty();
    private final StringProperty menuMiniButtonNewTooltip = new SimpleStringProperty();

    // Initialize with I18n values
    public MenuMiniViewModel() {
        updateTexts();
    }

    public void updateTexts() {
        menuMiniButtonShowAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonPlayerAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.player.accessibility"));
        menuMiniButtonSolveAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility"));
        menuMiniButtonBackupAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility"));
        menuMiniButtonBackgroundAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.background.accessibility"));
        menuMiniButtonLanguageAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonLanguageIso.set(I18n.INSTANCE.getValue("menu.mini.button.language.iso"));
        menuMiniButtonHelpAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonNewAccessibleText.set(
                I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));

        menuMiniButtonShowTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.show.accessibility"));
        menuMiniButtonPlayerTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.player.accessibility")
                        + I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonSolveTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.solve.accessibility")
                        + I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackupTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.backup.accessibility")
                        + I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonBackgroundTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.background.accessibility")
                        + I18n.INSTANCE.getValue(MENU_ACCESSIBILITY_ROLE_DESCRIPTION_CLOSED));
        menuMiniButtonLanguageTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.language.accessibility"));
        menuMiniButtonHelpTooltip.set(
                I18n.INSTANCE.getValue("menu.mini.button.help.accessibility"));
        menuMiniButtonNewTooltip.set(I18n.INSTANCE.getValue("menu.mini.button.new.accessibility"));
    }

    // Getter methods for binding
    public StringProperty menuMiniButtonShowAccessibleTextProperty() {
        return menuMiniButtonShowAccessibleText;
    }

    public StringProperty menuMiniButtonPlayerAccessibleTextProperty() {
        return menuMiniButtonPlayerAccessibleText;
    }

    public StringProperty menuMiniButtonSolveAccessibleTextProperty() {
        return menuMiniButtonSolveAccessibleText;
    }

    public StringProperty menuMiniButtonBackupAccessibleTextProperty() {
        return menuMiniButtonBackupAccessibleText;
    }

    public StringProperty menuMiniButtonBackgroundAccessibleTextProperty() {
        return menuMiniButtonBackgroundAccessibleText;
    }

    public StringProperty menuMiniButtonLanguageAccessibleTextProperty() {
        return menuMiniButtonLanguageAccessibleText;
    }

    public StringProperty menuMiniButtonHelpAccessibleTextProperty() {
        return menuMiniButtonHelpAccessibleText;
    }

    public StringProperty menuMiniButtonNewAccessibleTextProperty() {
        return menuMiniButtonNewAccessibleText;
    }

    public StringProperty menuMiniButtonLanguageIsoTextProperty() {
        return menuMiniButtonLanguageIso;
    }

    // Getter methods for tooltips
    public StringProperty menuMiniButtonShowTooltipProperty() {
        return menuMiniButtonShowTooltip;
    }

    public StringProperty menuMiniButtonPlayerTooltipProperty() {
        return menuMiniButtonPlayerTooltip;
    }

    public StringProperty menuMiniButtonSolveTooltipProperty() {
        return menuMiniButtonSolveTooltip;
    }

    public StringProperty menuMiniButtonBackupTooltipProperty() {
        return menuMiniButtonBackupTooltip;
    }

    public StringProperty menuMiniButtonBackgroundTooltipProperty() {
        return menuMiniButtonBackgroundTooltip;
    }

    public StringProperty menuMiniButtonLanguageTooltipProperty() {
        return menuMiniButtonLanguageTooltip;
    }

    public StringProperty menuMiniButtonHelpTooltipProperty() {
        return menuMiniButtonHelpTooltip;
    }

    public StringProperty menuMiniButtonNewTooltipProperty() {
        return menuMiniButtonNewTooltip;
    }
}
