/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.external;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import javafx.concurrent.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.service.ui.SpinnerService;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_API_REPOSITORY_TAGS_URL;

/**
 * Service for checking if the application version is up to date by querying the GitHub API. It
 * retrieves the latest release tag from the repository and compares it with the current application
 * version.
 */
@Service
public class VersionService {

    private static final Logger LOG = LoggerFactory.getLogger(VersionService.class);
    private static final int HTTP_STATUS_OK = 200;
    private static final String CURRENT_VERSION =
            JVMApplicationProperties.INSTANCE.getAppVersion().replaceFirst("^v", "");
    public static final int MAX_TAGS_RESPONSE_SIZE = 65_536;
    public static final int MAX_TAG_NAME_LENGTH = 256;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final SpinnerService spinnerService;

    /**
     * Constructs a new VersionService with required infrastructure services.
     *
     * @param httpClient the {@link HttpClient} used for API requests
     * @param objectMapper the {@link ObjectMapper} for JSON deserialization
     * @param spinnerService the {@link SpinnerService} for UI loading feedback
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Spring-managed ObjectMapper must be stored by reference.")
    public VersionService(
            HttpClient httpClient, ObjectMapper objectMapper, SpinnerService spinnerService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.spinnerService = spinnerService;
    }

    /**
     * Checks if the current application version matches the latest release on GitHub.
     *
     * @return a {@link Task} returning {@code true} if up-to-date, {@code false} if update
     *     available
     */
    public Task<Boolean> checkLatestVersion() {
        return new Task<>() {
            @Override
            protected Boolean call() {
                spinnerService.startLoading();
                updateMessage(
                        I18n.INSTANCE.getValue("githubrepositoryversion.checking")
                                + MyDateTime.INSTANCE.getFormattedCurrentTime()
                                + ")");
                try {
                    HttpRequest request =
                            HttpRequest.newBuilder()
                                    .uri(URI.create(GITHUB_API_REPOSITORY_TAGS_URL.getUrl()))
                                    .header("Accept", "application/json")
                                    .timeout(Duration.ofSeconds(5))
                                    .GET()
                                    .build();
                    HttpResponse<InputStream> response =
                            httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());
                    if (response.statusCode() != HTTP_STATUS_OK) {
                        LOG.error(
                                "██ Exception GitHub API error: status {}", response.statusCode());
                        updateMessage(
                                I18n.INSTANCE.getValue("githubrepositoryversion.error.statuscode"));
                        return true;
                    }
                    try (InputStream is = response.body()) {
                        updateMessage(
                                I18n.INSTANCE.getValue("githubrepositoryversion.checked")
                                        + MyDateTime.INSTANCE.getFormattedCurrentTime()
                                        + ")");
                        byte[] data = is.readNBytes(MAX_TAGS_RESPONSE_SIZE);
                        return parseResponse(data);
                    }
                } catch (HttpTimeoutException _) {
                    LOG.warn("▓▓ Timeout while checking GitHub version");
                    updateMessage(I18n.INSTANCE.getValue("githubrepositoryversion.warn.timeout"));
                } catch (InterruptedException ex) {
                    LOG.warn("▓▓ Interrupted", ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.warn.interrupted"));
                    Thread.currentThread().interrupt();
                } catch (Exception ex) {
                    LOG.error("██ Exception version check failed: {}", ex.getMessage(), ex);
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.error.unexpected"));
                }
                return true;
            }

            @Override
            protected void succeeded() {
                LOG.debug("▓▓ GitHub version check success");
            }

            @Override
            protected void failed() {
                LOG.error("██ Exception Task failed: {}", getException().getMessage());
            }

            @Override
            protected void done() {
                spinnerService.stopLoading();
            }
        };
    }

    /**
     * Parses the byte array directly.
     *
     * @param data the byte array containing the JSON response, limited by MAX_TAGS_RESPONSE_SIZE
     * @return {@code true} if versions match or if parsing fails; {@code false} if a different
     *     version is detected
     */
    private boolean parseResponse(byte[] data) {
        if (data.length == 0) {
            LOG.warn("▓▓ Version check: GitHub returned an empty response body");
            return true;
        }
        try {
            JsonNode root = objectMapper.readTree(data);
            if (root == null || !root.isArray() || root.isEmpty()) {
                LOG.warn("▓▓ Version check: GitHub returned an empty or invalid tag list");
                return true;
            }
            JsonNode firstTag = root.get(0).path("name");
            if (!firstTag.isTextual()) {
                LOG.error("██ Version check error: tag name field is not textual");
                return true;
            }
            String rawName = firstTag.asText();
            if (rawName.length() > MAX_TAG_NAME_LENGTH) {
                LOG.error(
                        "██ Version check error: tag name too large ({} chars)", rawName.length());
                return true;
            }
            String latestVersion = rawName.replaceFirst("^v", "").trim();
            if (latestVersion.isEmpty()) {
                LOG.warn("▓▓ Version check: tag name is empty after normalization");
                return true;
            }
            boolean isLatest = CURRENT_VERSION.equals(latestVersion);
            LOG.info(
                    "▓▓ Version check: current={}, latest={}, match={}",
                    CURRENT_VERSION,
                    latestVersion,
                    isLatest);
            return isLatest;
        } catch (Exception e) {
            LOG.error("██ Exception during GitHub JSON parsing: {}", e.getMessage());
            return true;
        }
    }
}
