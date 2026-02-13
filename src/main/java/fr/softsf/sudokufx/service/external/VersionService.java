/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.external;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;
import javafx.concurrent.Task;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.MyRegex;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.dto.github.TagDto;
import fr.softsf.sudokufx.service.ui.SpinnerService;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_API_REPOSITORY_TAGS_URL;

/**
 * Service for checking if the application version is up to date by querying GitHub. It retrieves
 * the latest release tag from the GitHub API and compares it with the current version. If an update
 * is available, the result can be used to notify the user.
 *
 * <p>All method parameters and return values in this package are non-null by default, thanks to the
 * {@code @NonNullApi} annotation at the package level.
 */
@Service
public class VersionService {

    private static final Logger LOG = LoggerFactory.getLogger(VersionService.class);
    private static final int HTTP_STATUS_OK = 200;

    private static final String CURRENT_VERSION =
            JVMApplicationProperties.INSTANCE.getAppVersion().isEmpty()
                    ? ""
                    : JVMApplicationProperties.INSTANCE.getAppVersion().substring(1);
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final SpinnerService spinnerService;

    /**
     * Initializes the VersionService with the provided HttpClient. This service is responsible for
     * checking the latest version by making HTTP requests.
     *
     * @param httpClient the HttpClient used to perform HTTP requests.
     */
    public VersionService(
            HttpClient httpClient, ObjectMapper objectMapper, SpinnerService spinnerService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.spinnerService = spinnerService;
    }

    /**
     * Checks if the current application version is up-to-date by querying the GitHub API. This
     * method runs in the background using a JavaFX `Task`.
     *
     * @return A `Task<Boolean>` returns true if up-to-date or error, false if update available.
     */
    public Task<Boolean> checkLatestVersion() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                try {
                    spinnerService.startLoading();
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.checking")
                                    + MyDateTime.INSTANCE.getFormattedCurrentTime()
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
                    if (response.statusCode() != HTTP_STATUS_OK) {
                        LOG.error(
                                "██ Exception GitHub API returned non 200 status code: {}",
                                response.statusCode());
                        updateMessage(
                                I18n.INSTANCE.getValue("githubrepositoryversion.error.statuscode"));
                        return true;
                    }
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.checked")
                                    + MyDateTime.INSTANCE.getFormattedCurrentTime()
                                    + ")");
                    return parseResponse(response.body());
                } catch (HttpTimeoutException _) {
                    LOG.warn("▓▓ Timeout while checking GitHub version");
                    updateMessage(I18n.INSTANCE.getValue("githubrepositoryversion.warn.timeout"));
                } catch (InterruptedException ex) {
                    LOG.warn("▓▓ GitHub version check was interrupted", ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.warn.interrupted"));
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    LOG.error(
                            "██ Exception network error while retrieving GitHub version: {}",
                            ex.getMessage(),
                            ex);
                    updateMessage(I18n.INSTANCE.getValue("githubrepositoryversion.error.network"));
                } catch (Exception ex) {
                    LOG.error(
                            "██ Exception unexpected exception retrieving GitHub version: {}",
                            ex.getMessage(),
                            ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.error.unexpected"));
                }
                return true;
            }

            @Override
            protected void succeeded() {
                LOG.debug("▓▓ GitHub version check task finished successfully");
            }

            @Override
            protected void failed() {
                Throwable ex = getException();
                LOG.error("██ Exception Task failed: {}", ex.getMessage(), ex);
            }

            @Override
            protected void done() {
                spinnerService.stopLoading();
            }
        };
    }

    /**
     * Parses the JSON response from the GitHub API to extract the latest published version.
     * Validates the input, extracts the first tag, checks its format, and compares it with the
     * current application version.
     *
     * @param json The non-null, non-blank JSON response from the GitHub API.
     * @return true if the current version is up to date or if the response is invalid or an error
     *     occurs; false if a newer version is available.
     * @throws IllegalArgumentException if the JSON is null, empty or blank.
     */
    private boolean parseResponse(String json) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                json, "json must not be null or blank, but was " + json);
        try {
            List<TagDto> list = objectMapper.readValue(json, new TypeReference<>() {});
            String tagName =
                    list.stream().findFirst().map(TagDto::name).map(String::trim).orElse("");
            if (StringUtils.isBlank(tagName) || tagName.length() < 6) {
                LOG.warn("▓▓ Invalid or too short tag received from GitHub: '{}'", tagName);
                return true;
            }
            String lastVersion = tagName.substring(1);
            if (MyRegex.INSTANCE.isValidatedByRegex(
                    lastVersion, MyRegex.INSTANCE.getVersionPattern())) {
                boolean isLatest = compareVersions(CURRENT_VERSION, lastVersion) >= 0;
                LOG.info(
                        "▓▓ GitHub version check: currentVersion={}, lastVersion={}, result={}",
                        CURRENT_VERSION,
                        lastVersion,
                        isLatest);
                return isLatest;
            }
            LOG.warn(
                    "▓▓ GitHub version '{}' does not match expected semantic versioning format"
                            + " (X.Y.Z).",
                    lastVersion);
            return true;
        } catch (Exception e) {
            LOG.error("██ Exception error parsing GitHub API response: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * Compares two version strings in the format MAJOR.MINOR.PATCH (e.g., "1.2.3"). The comparison
     * is done based on the numeric values of major, minor, and patch components.
     *
     * @param version1 the first version string, non-null and non-blank.
     * @param version2 the second version string, non-null and non-blank.
     * @return a negative integer if version1 is older, a positive integer if version1 is newer, or
     *     0 if both versions are equal.
     * @throws IllegalArgumentException if either version string is null, empty, or blank.
     * @throws NumberFormatException if the version strings are not properly formatted (e.g., not
     *     "X.Y.Z").
     */
    private int compareVersions(final String version1, final String version2) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                version1, "version1 must not be null or blank, but was " + version1);
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                version2, "version2 must not be null or blank, but was " + version2);
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
