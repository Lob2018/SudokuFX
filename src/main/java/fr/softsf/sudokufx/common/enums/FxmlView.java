/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/**
 * Enumerates FXML view identifiers mapped to their relative file paths.
 *
 * <p>Centralizes view references to improve type safety, reduce duplication, and support consistent
 * loading.
 */
public enum FxmlView {
    MAIN("main/main-view.fxml"),
    CRASH_SCREEN("crash-screen-view.fxml");

    private final String fxmlPath;

    FxmlView(final String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public final String getPath() {
        return fxmlPath;
    }
}
