/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.unit.common.enums;

import java.util.Map;

import org.junit.jupiter.api.Test;

import fr.softsf.sudokufx.common.enums.FxmlView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FxmlViewUTest {
    @Test
    void testAllViewPaths() {
        Map<FxmlView, String> expectedPaths =
                Map.of(
                        FxmlView.MAIN, "main/main-view.fxml",
                        FxmlView.CRASH_SCREEN, "crash-screen-view.fxml");
        expectedPaths.forEach(
                (view, expectedPath) ->
                        assertEquals(
                                expectedPath,
                                view.getPath(),
                                "Unexpected path for " + view.name()));
    }
}
