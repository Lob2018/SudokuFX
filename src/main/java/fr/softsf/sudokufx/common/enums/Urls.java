/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.common.enums;

/** Utility enum for various application-specific url. */
public enum Urls {
    REPOSITORY_OWNER("Lob2018"),
    REPOSITORY_NAME("SudokuFX"),
    GITHUB_URL("https://github.com/"),
    GITHUB_API_URL("https://api.github.com/"),
    GITHUB_REPOSITORY_RELEASES_URL(
            GITHUB_URL.getUrl()
                    + REPOSITORY_OWNER.getUrl()
                    + "/"
                    + REPOSITORY_NAME.getUrl()
                    + "/releases"),
    GITHUB_API_REPOSITORY_TAGS_URL(
            GITHUB_API_URL.getUrl()
                    + "repos/"
                    + REPOSITORY_OWNER.getUrl()
                    + "/"
                    + REPOSITORY_NAME.getUrl()
                    + "/tags"),
    MY_WEBSITE_URL("https://soft64.fr"),
    MY_KOFI_URL("https://ko-fi.com/lob2018");

    private final String url;

    Urls(final String url) {
        this.url = url;
    }

    public final String getUrl() {
        return url;
    }
}
