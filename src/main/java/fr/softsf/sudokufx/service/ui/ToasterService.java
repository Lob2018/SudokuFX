/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service.ui;

import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private final BooleanProperty removeToastRequest = new SimpleBooleanProperty(false);

    /** Returns a read-only observable property for toast notifications. */
    public ReadOnlyObjectProperty<ToastData> toastRequestProperty() {
        return toastRequest;
    }

    /** Returns a read-only observable property that signals requests to remove a toast. */
    public ReadOnlyBooleanProperty removeToastRequestProperty() {
        return removeToastRequest;
    }

    /**
     * Requests the removal of the currently displayed toast.
     *
     * <p>This triggers the {@link #removeToastRequestProperty()} listener in the UI to remove the
     * toast.
     */
    public void requestRemoveToast() {
        removeToastRequest.set(!removeToastRequest.get());
    }

    /**
     * Sends an informational toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showInfo(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.INFO);
    }

    /**
     * Sends a warning toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showWarning(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.WARN);
    }

    /**
     * Sends an error toast.
     *
     * @throws NullPointerException if visibleText or detailedText is null
     */
    public void showError(String visibleText, String detailedText) throws NullPointerException {
        sendToast(visibleText, detailedText, ToastLevels.ERROR);
    }

    /**
     * Publishes a ToastData to toastRequest, resetting it to null first to ensure listeners fire
     * even if identical to the previous value.
     *
     * @param visibleText The text displayed in the toast
     * @param detailedText The detailed text for clipboard copy
     * @param level The severity level of the toast
     * @throws NullPointerException if visibleText or detailedText is null
     */
    private void sendToast(String visibleText, String detailedText, ToastLevels level) {
        toastRequest.set(null);
        toastRequest.set(
                new ToastData(
                        Objects.requireNonNull(visibleText, "visibleText must not be null"),
                        Objects.requireNonNull(detailedText, "detailedText must not be null"),
                        level,
                        false));
    }
}
