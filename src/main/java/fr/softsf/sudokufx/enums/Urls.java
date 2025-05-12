/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.enums;

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
                    + "/tags");

    private final String url;

    Urls(final String url_) {
        url = url_;
    }

    public final String getUrl() {
        return url;
    }
}
