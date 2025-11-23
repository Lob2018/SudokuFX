/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.testing.integration.service.external;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import javafx.concurrent.Task;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.common.util.MyDateTime;
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.service.external.VersionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class VersionServiceITest {
    private static final String JSON =
            """
            [
              {
                "name": "%s",
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v1.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v1.0.0",
                "commit": {
                  "sha": "e59cb3b14bd859250910d51985f50dabb89ee788",
                  "url": "https://api.github.com/repos/Lob2018/SudokuFX/commits/e59cb3b14bd859250910d51985f50dabb89ee788"
                },
                "node_id": "REF_kwDOLH8vKbByZWZzL3RhZ3MvdjEuMC4w"
              },
              {
                "name": "v0.9.8",
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v1.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v1.0.0",
                "commit": {
                  "sha": "e59cb3b14bd859250910d51985f50dabb89ee788",
                  "url": "https://api.github.com/repos/Lob2018/SudokuFX/commits/e59cb3b14bd859250910d51985f50dabb89ee788"
                },
                "node_id": "REF_kwDOLH8vKbByZWZzL3RhZ3MvdjEuMC4w"
              }
            ]
            """;
    private AutoCloseable closeable;
    private ListAppender<ILoggingEvent> logWatcher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock private HttpClient mockHttpClient;
    @Mock private MyDateTime myDateTime;
    @Mock private HttpResponse<String> mockResponse;

    @BeforeEach
    void eachSetup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(VersionService.class))
                .addAppender(logWatcher);
        closeable = MockitoAnnotations.openMocks(this);
        Mockito.reset(mockHttpClient, mockResponse);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(VersionService.class))
                .detachAndStopAllAppenders();
    }

    @Test
    void givenEmptyGitHubResponse_whenCheckLatestVersion_thenLatestVersionTrue() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("[]");
        when(mockHttpClient.send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any());
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("▓▓ Invalid or too short tag received from GitHub: ''"));
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 403, 404, 500})
    void givenNon200HttpStatus_whenCheckLatestVersion_thenLatestVersionTrue(int httpStatusCode)
            throws IOException, InterruptedException, ExecutionException {
        when(mockResponse.statusCode()).thenReturn(httpStatusCode);
        when(mockHttpClient.send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any());
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains(
                                "██ Exception GitHub API returned non 200 status code: "
                                        + httpStatusCode));
    }

    @Test
    void givenRealCurrentVersion_whenCheckLatestVersion_thenCompareWithNearbyVersions()
            throws Exception {
        String currentVersionRaw = JVMApplicationProperties.INSTANCE.getAppVersion();
        assertNotNull(currentVersionRaw, "Current version should not be null");
        assertTrue(currentVersionRaw.startsWith("v"), "Version must start with 'v'");
        String currentVersion = currentVersionRaw.substring(1);
        String[] currentParts = currentVersion.split("\\.");
        int currentMajor = Integer.parseInt(currentParts[0]);
        int currentMinor = Integer.parseInt(currentParts[1]);
        int currentPatch = Integer.parseInt(currentParts[2]);
        for (int major = Math.max(0, currentMajor - 1); major <= currentMajor + 1; major++) {
            for (int minor = Math.max(0, currentMinor - 1); minor <= currentMinor + 1; minor++) {
                for (int patch = Math.max(0, currentPatch - 1);
                        patch <= currentPatch + 1;
                        patch++) {
                    String onlineVersion = String.format("v%d.%d.%d", major, minor, patch);
                    String jsonResponse = String.format(JSON, onlineVersion);
                    when(mockResponse.statusCode()).thenReturn(200);
                    when(mockResponse.body()).thenReturn(jsonResponse);
                    when(mockHttpClient.send(
                                    any(HttpRequest.class),
                                    ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                            .thenReturn(mockResponse);
                    Task<Boolean> versionCheckTask =
                            new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
                    versionCheckTask.run();
                    boolean isLatestVersion = versionCheckTask.get();
                    int comparison = compareVersions(currentVersion, onlineVersion.substring(1));
                    if (comparison >= 0) {
                        assertTrue(
                                isLatestVersion,
                                () ->
                                        "Expected current version "
                                                + currentVersionRaw
                                                + " to be up to date compared to "
                                                + onlineVersion);
                    } else {
                        assertFalse(
                                isLatestVersion,
                                () ->
                                        "Expected current version "
                                                + currentVersionRaw
                                                + " to NOT be up to date compared to "
                                                + onlineVersion);
                    }
                }
            }
        }
    }

    /**
     * Compares two semantic versions (major.minor.patch).
     *
     * @param version1 the first version
     * @param version2 the second version
     * @return negative if version1 < version2, positive if version1 > version2, zero if equal
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

    @ParameterizedTest
    @ValueSource(strings = {"", "vv.v.v"})
    void givenInvalidGitHubVersions_whenCheckLatestVersion_thenLatestVersionTrue(
            String onLineVersion) throws ExecutionException, InterruptedException, IOException {
        when(mockResponse.statusCode()).thenReturn(200);
        String jsonResponse = String.format(JSON, onLineVersion);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any());
        String message = logWatcher.list.getFirst().getFormattedMessage();
        switch (onLineVersion) {
            case "vv.v.v" -> {
                assertTrue(
                        message.contains(
                                "▓▓ GitHub version 'v.v.v' does not match expected semantic"
                                        + " versioning format (X.Y.Z)."));
            }
            case "" -> {
                assertTrue(
                        message.contains("▓▓ Invalid or too short tag received from GitHub: ''"));
            }
            default -> throw new AssertionError("Unexpected version format: " + onLineVersion);
        }
    }

    @Test
    void givenInvalidJsonResponse_whenCheckLatestVersion_thenReturnsTrueAndLogsError()
            throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{ invalid json }"); // JSON invalide
        when(mockHttpClient.send(
                        any(HttpRequest.class),
                        ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockResponse);
        VersionService service = new VersionService(mockHttpClient, new ObjectMapper());
        Task<Boolean> task = service.checkLatestVersion();
        task.run();
        boolean result = task.get();
        assertTrue(result);
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("██ Exception error parsing GitHub API response:"));
    }
}
