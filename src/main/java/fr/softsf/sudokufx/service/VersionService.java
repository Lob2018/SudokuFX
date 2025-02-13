package fr.softsf.sudokufx.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.softsf.sudokufx.configuration.JVMApplicationProperties;
import fr.softsf.sudokufx.dto.github.TagDto;
import fr.softsf.sudokufx.utils.MyRegex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Service for checking if the application version is up to date by querying GitHub.
 * <p>
 * It retrieves the latest release tag from the GitHub API and compares it with the current version.
 * If an update is available, the result can be used to notify the user.
 */
@Slf4j
@Service
public class VersionService {

    private static final String OWNER = "Lob2018";
    private static final String REPO = "SudokuFX";
    private static final String GITHUB_URL = "https://github.com/";
    private static final String GITHUB_API_URL = "https://api.github.com/";
    private static final String GITHUB_API_URL_REPO_TAGS = GITHUB_API_URL + "repos/" + OWNER + "/" + REPO + "/tags";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final HttpClient httpClient;
    private final String currentVersion = JVMApplicationProperties.getAppVersion().isEmpty() ? "" : JVMApplicationProperties.getAppVersion().substring(1);

    /**
     * Constructs a VersionService with the provided HttpClient.
     *
     * @param httpClient the HttpClient to use for HTTP requests.
     */
    @Autowired
    public VersionService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Gets the GitHub link to the repository releases page.
     *
     * @return the URL to the repository releases page.
     */
    public String getGitHubLinkToRepositoryReleases() {
        return GITHUB_URL + OWNER + "/" + REPO + "/releases";
    }

    /**
     * Asynchronously checks if the current application version is up-to-date by querying the GitHub API.
     * It compares the latest release version from the repository with the current application version.
     * <p>
     * In case of errors (e.g., timeout, interruption, or network issues), it assumes the version is up-to-date
     * and logs the exception details.
     *
     * @return A `CompletableFuture<Boolean>` that completes with `true` if the version is up-to-date,
     * or `false` if an update is available. On error, it defaults to `true`.
     */
    public CompletableFuture<Boolean> checkLatestVersion() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GITHUB_API_URL_REPO_TAGS))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .orTimeout(5, TimeUnit.SECONDS)  // Temps d'attente avant un timeout
                    .thenApply(response -> {
                        if (response.statusCode() != 200) {
                            log.error("██ GitHub API returned non 200 status code: {}", response.statusCode());
                            return true;
                        }
                        return parseResponse(response.body());
                    })
                    .exceptionally(ex -> {
                        if (ex.getCause() instanceof InterruptedException) {
                            log.warn("▓▓ GitHub version check was interrupted", ex);
                            Thread.currentThread().interrupt();  // Réinitialisation de l'état d'interruption
                        } else if (ex.getCause() instanceof TimeoutException) {
                            log.warn("▓▓ Timeout while checking GitHub version");
                        } else {
                            log.error("██ Exception retrieving GitHub version: {}", ex.getMessage(), ex);
                        }
                        return true;
                    });
        } catch (Exception ex) {
            log.error("██ Exception retrieving GitHub version: {}", ex.getMessage(), ex);
            return CompletableFuture.completedFuture(true);
        }
    }


    /**
     * Parses the JSON response from the GitHub API to extract the latest published version.
     * <p>
     * The method retrieves the latest tag name from the response, validates its format,
     * and compares it with the current application version.
     *
     * @param json The raw JSON response from the GitHub API.
     * @return true if the current version is up to date or if an error occurs, false if an update is available.
     */
    private boolean parseResponse(String json) {
        try {
            List<TagDto> list = OBJECT_MAPPER.readValue(json, new TypeReference<>() {
            });
            String tagName = list.stream()
                    .findFirst()
                    .map(TagDto::name)
                    .map(String::trim)
                    .orElse("");
            if (tagName.length() < 6) {
                log.warn("▓▓ Invalid or too short tag received from GitHub: '{}'", tagName);
                return true;
            }
            String lastVersion = tagName.substring(1);
            if (!MyRegex.isValidatedByRegex(lastVersion, MyRegex.getVERSION())) {
                log.warn("▓▓ GitHub version '{}' does not match expected semantic versioning format (X.Y.Z).", lastVersion);
                return true;
            }
            boolean isLatest = compareVersions(currentVersion, lastVersion) >= 0;
            log.info("▓▓ Version check: currentVersion={}, lastVersion={}, result={}", currentVersion, lastVersion, isLatest);
            return isLatest;
        } catch (Exception e) {
            log.error("██ Error parsing GitHub API response: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * Compares two version strings in the format MAJOR.MINOR.PATCH.
     *
     * @param version1 the first version string (e.g., "1.2.3").
     * @param version2 the second version string (e.g., "1.3.0").
     * @return a negative integer if version1 is older,
     * a positive integer if version1 is newer,
     * or 0 if both versions are equal.
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
