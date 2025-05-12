/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;
import javafx.concurrent.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.dto.github.TagDto;
import fr.softsf.sudokufx.enums.I18n;
import fr.softsf.sudokufx.enums.MyRegex;
import fr.softsf.sudokufx.utils.MyDateTime;

import static fr.softsf.sudokufx.enums.Urls.GITHUB_API_REPOSITORY_TAGS_URL;
import static fr.softsf.sudokufx.enums.Urls.GITHUB_REPOSITORY_RELEASES_URL;

/**
 * Service for checking if the application version is up to date by querying GitHub. It retrieves
 * the latest release tag from the GitHub API and compares it with the current version. If an update
 * is available, the result can be used to notify the user.
 */
@Service
public class VersionService {

    private static final Logger log = LoggerFactory.getLogger(VersionService.class);

    private static final String CURRENT_VERSION =
            JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty()
                    ? ""
                    : JVMApplicationProperties.INSTANCE.getAppVersion().substring(1);
    private final HttpClient httpClient;
    private final MyDateTime myDateTime;
    private final ObjectMapper objectMapper;

    /**
     * Initializes the VersionService with the provided HttpClient. This service is responsible for
     * checking the latest version by making HTTP requests.
     *
     * @param httpClient the HttpClient used to perform HTTP requests.
     * @param myDateTime the MyDateTime used to date and time operations.
     */
    public VersionService(HttpClient httpClient, MyDateTime myDateTime, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.myDateTime = myDateTime;
        this.objectMapper = objectMapper;
    }

    /**
     * Gets the GitHub link to the repository releases page.
     *
     * @return the URL to the repository releases page.
     */
    public String getGitHubLinkToRepositoryReleases() {
        return GITHUB_REPOSITORY_RELEASES_URL.getUrl();
    }

    /**
     * Checks if the current application version is up-to-date by querying the GitHub API. This
     * method runs in the background using a JavaFX `Task` to avoid blocking the UI thread. It
     * retrieves the latest release version from the repository and compares it with the current
     * application version. In case of errors (e.g., timeout, interruption, or network issues), it
     * assumes the version is up-to-date and logs the exception details.
     *
     * @return A `Task<Boolean>` that returns `true` if the version is up-to-date or if an error
     *     occurs, and `false` if an update is available.
     */
    public Task<Boolean> checkLatestVersion() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                try {
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.checking")
                                    + myDateTime.getFormattedCurrentTime()
                                    + ")");
                    HttpRequest request =
                            HttpRequest.newBuilder()
                                    .uri(URI.create(GITHUB_API_REPOSITORY_TAGS_URL.getUrl()))
                                    .header("Accept", "application/json")
                                    .timeout(Duration.ofSeconds(5))
                                    .GET()
                                    .build();
                    HttpResponse<String> response =
                            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() != 200) {
                        log.error(
                                "██ GitHub API returned non 200 status code: {}",
                                response.statusCode());
                        updateMessage(
                                I18n.INSTANCE.getValue("githubrepositoryversion.error.statuscode"));
                        return true;
                    }
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.checked")
                                    + myDateTime.getFormattedCurrentTime()
                                    + ")");
                    return parseResponse(response.body());
                } catch (HttpTimeoutException ex) {
                    log.warn("▓▓ Timeout while checking GitHub version");
                    updateMessage(I18n.INSTANCE.getValue("githubrepositoryversion.warn.timeout"));
                } catch (InterruptedException ex) {
                    log.warn("▓▓ GitHub version check was interrupted", ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.warn.interrupted"));
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    log.error(
                            "██ Network error while retrieving GitHub version: {}",
                            ex.getMessage(),
                            ex);
                    updateMessage(I18n.INSTANCE.getValue("githubrepositoryversion.error.network"));
                } catch (Exception ex) {
                    log.error(
                            "██ Unexpected exception retrieving GitHub version: {}",
                            ex.getMessage(),
                            ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.error.unexpected"));
                }
                return true;
            }
        };
    }

    /**
     * Parses the JSON response from the GitHub API to extract the latest published version. The
     * method retrieves the latest tag name from the response, validates its format, and compares it
     * with the current application version.
     *
     * @param json The raw JSON response from the GitHub API.
     * @return true if the current version is up to date or if an error occurs, false if an update
     *     is available.
     */
    private boolean parseResponse(String json) {
        try {
            List<TagDto> list = objectMapper.readValue(json, new TypeReference<>() {});
            String tagName =
                    list.stream().findFirst().map(TagDto::name).map(String::trim).orElse("");
            if (tagName.length() < 6) {
                log.warn("▓▓ Invalid or too short tag received from GitHub: '{}'", tagName);
                return true;
            }
            String lastVersion = tagName.substring(1);
            if (!MyRegex.INSTANCE.isValidatedByRegex(
                    lastVersion, MyRegex.INSTANCE.getVersionPattern())) {
                log.warn(
                        "▓▓ GitHub version '{}' does not match expected semantic versioning format"
                                + " (X.Y.Z).",
                        lastVersion);
                return true;
            }
            boolean isLatest = compareVersions(CURRENT_VERSION, lastVersion) >= 0;
            log.info(
                    "▓▓ GitHub version check: currentVersion={}, lastVersion={}, result={}",
                    CURRENT_VERSION,
                    lastVersion,
                    isLatest);
            return isLatest;
        } catch (Exception e) {
            log.error("██ Error parsing GitHub API response: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * Compares two version strings in the format MAJOR.MINOR.PATCH (e.g., "1.2.3"). The comparison
     * is done based on the major, minor, and patch numbers.
     *
     * @param version1 the first version string.
     * @param version2 the second version string.
     * @return a negative integer if version1 is older, a positive integer if version1 is newer, or
     *     0 if both versions are equal.
     * @throws NumberFormatException if the version strings are not properly formatted.
     */
    private int compareVersions(final String version1, final String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int majorComparison = Integer.compare(Integer.parseInt(v1[0]), Integer.parseInt(v2[0]));
        if (majorComparison != 0) {
            return majorComparison;
        }
        int minorComparison = Integer.compare(Integer.parseInt(v1[1]), Integer.parseInt(v2[1]));
        if (minorComparison != 0) {
            return minorComparison;
        }
        return Integer.compare(Integer.parseInt(v1[2]), Integer.parseInt(v2[2]));
    }
}
