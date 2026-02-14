/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.service.external;

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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.dto.github.TagDto;
import fr.softsf.sudokufx.service.ui.SpinnerService;

import static fr.softsf.sudokufx.common.enums.Urls.GITHUB_API_REPOSITORY_TAGS_URL;

/**
 * Service for checking if the application version is up to date by querying the GitHub API. It
 * retrieves the latest release tag from the repository and compares it with the current application
 * version. This service manages UI feedback through a spinner and status messages.
 *
 * <p>All method parameters and return values in this package are non-null by default, thanks to the
 * {@code @NonNullApi} annotation at the package level.
 */
@Service
public class VersionService {

    private static final Logger LOG = LoggerFactory.getLogger(VersionService.class);
    private static final int HTTP_STATUS_OK = 200;

    /**
     * Current application version extracted from properties, stripped of its 'v' prefix if present.
     */
    private static final String CURRENT_VERSION =
            JVMApplicationProperties.INSTANCE.getAppVersion().replaceFirst("^v", "");

    public static final int MAX_BODY_RESPONSE_SIZE = 1_048_576;

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
            justification =
                    "Spring-managed ObjectMapper must be stored by reference; defensive copies are"
                            + " impossible and break JSON configuration consistency.")
    public VersionService(
            HttpClient httpClient, ObjectMapper objectMapper, SpinnerService spinnerService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.spinnerService = spinnerService;
    }

    /**
     * Checks if the current application version matches the latest release on GitHub. *
     *
     * <p>Runs asynchronously using a JavaFX {@link Task}. It manages the loading spinner through
     * the task's lifecycle and updates status messages for the UI. In case of network errors or
     * timeouts, it defaults to assuming the version is up-to-date to avoid blocking the user.
     *
     * @return a {@link Task} returning {@code true} if the version is up-to-date or if an error
     *     occurred, {@code false} if a new update is available
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
                    HttpResponse<String> response =
                            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() != HTTP_STATUS_OK) {
                        LOG.error(
                                "██ Exception GitHub API error: status {}", response.statusCode());
                        updateMessage(
                                I18n.INSTANCE.getValue("githubrepositoryversion.error.statuscode"));
                        return true;
                    }
                    String body = response.body();
                    if (body == null || body.length() > MAX_BODY_RESPONSE_SIZE) {
                        LOG.error(
                                "██ Exception GitHub response invalid or too large: {} chars",
                                body == null ? "null" : body.length());
                        return true;
                    }
                    updateMessage(
                            I18n.INSTANCE.getValue("githubrepositoryversion.checked")
                                    + MyDateTime.INSTANCE.getFormattedCurrentTime()
                                    + ")");
                    return parseResponse(body);
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
     * Parses the GitHub API JSON response to identify the latest version tag. *
     *
     * <p>The method extracts the first tag name, normalizes it by removing the 'v' prefix, and
     * performs a strict equality check against the current version.
     *
     * @param json the JSON response string from GitHub API
     * @return {@code true} if versions match or if parsing fails; {@code false} if a different
     *     version is detected
     * @throws IllegalArgumentException if the JSON input is blank
     */
    private boolean parseResponse(String json) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(json, "json must not be blank");
        try {
            List<TagDto> list = objectMapper.readValue(json, new TypeReference<>() {});
            return list.stream()
                    .findFirst()
                    .map(TagDto::name)
                    .map(name -> name.replaceFirst("^v", "").trim())
                    .map(
                            lastVersion -> {
                                boolean isLatest = CURRENT_VERSION.equals(lastVersion);
                                LOG.info(
                                        "▓▓ Version check: current={}, latest={}, match={}",
                                        CURRENT_VERSION,
                                        lastVersion,
                                        isLatest);
                                return isLatest;
                            })
                    .orElse(true);
        } catch (Exception e) {
            LOG.error("██ Exception parsing GitHub API response: {}", e.getMessage(), e);
            return true;
        }
    }
}
