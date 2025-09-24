/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.ui;

import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.springframework.stereotype.Service;

import fr.softsf.sudokufx.common.enums.ToastLevels;
import fr.softsf.sudokufx.dto.ToastData;

/**
 * A centralized service for sending toast notifications to the UI. ViewModels can inject this
 * service to trigger toasts.
 */
@Service
public class ToasterService {

    private final ObjectProperty<ToastData> toastRequest = new SimpleObjectProperty<>();

    /** Returns a read-only observable property for toast notifications. */
    public ReadOnlyObjectProperty<ToastData> toastRequestProperty() {
        return toastRequest;
    }

    /**
     * Sends an informational toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showInfo(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.INFO, false);
    }

    /**
     * Sends a warning toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showWarning(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.WARN, false);
    }

    /**
     * Sends an error toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showError(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.ERROR, true);
    }

    /**
     * Creates a ToastData and publishes it to the toastRequest property.
     *
     * @param visibleText The text displayed in the toast
     * @param detailedText The detailed text for clipboard copy
     * @param level The severity level of the toast
     * @param requestFocus Whether the toast should request focus
     * @throws NullPointerException if visibleText or detailedText is null
     */
    private void sendToast(
            String visibleText, String detailedText, ToastLevels level, boolean requestFocus) {
        toastRequest.set(
                new ToastData(
                        Objects.requireNonNull(visibleText, "visibleText must not be null"),
                        Objects.requireNonNull(detailedText, "detailedText must not be null"),
                        level,
                        requestFocus));
    }
}
