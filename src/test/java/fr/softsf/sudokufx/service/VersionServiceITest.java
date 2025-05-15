/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.service;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.testfx.framework.junit5.ApplicationExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import fr.softsf.sudokufx.utils.MyDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class VersionServiceITest {
    private static final String GITHUB_LINK_TO_REPOSITORY_RELEASES =
            "https://github.com/Lob2018/SudokuFX/releases";
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
    void givenVersionService_whenGetGitHubLinkToRepositoryReleases_thenCorrectLink() {
        VersionService versionService = new VersionService(mockHttpClient, objectMapper);
        assertEquals(
                GITHUB_LINK_TO_REPOSITORY_RELEASES,
                versionService.getGitHubLinkToRepositoryReleases(),
                "The GitHub link to repository releases must be "
                        + GITHUB_LINK_TO_REPOSITORY_RELEASES);
    }

    @Test
    void givenEmptyGitHubResponse_whenCheckLatestVersion_thenLatestVersionTrue() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("[]");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
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
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(Mockito.any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("██ GitHub API returned non 200 status code: " + httpStatusCode));
    }

    @ParameterizedTest
    @ValueSource(strings = {"v0.0.0", "v0.0.1", "v0.1.1", "v2147483647.2147483647.2147483647"})
    void givenValidGitHubVersions_whenCheckLatestVersion_thenCorrectResultReturned(
            String onLineVersion) throws ExecutionException, InterruptedException, IOException {
        when(mockResponse.statusCode()).thenReturn(200);
        String jsonResponse = String.format(JSON, onLineVersion);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        if (onLineVersion.equals("v2147483647.2147483647.2147483647")) {
            assertFalse(isLatestVersion);
        } else {
            assertTrue(isLatestVersion);
        }
        verify(mockHttpClient, times(1))
                .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("▓▓ GitHub version check: currentVersion="));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "vv.v.v"})
    void givenInvalidGitHubVersions_whenCheckLatestVersion_thenLatestVersionTrue(
            String onLineVersion) throws ExecutionException, InterruptedException, IOException {
        when(mockResponse.statusCode()).thenReturn(200);
        String jsonResponse = String.format(JSON, onLineVersion);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> versionCheckTask =
                new VersionService(mockHttpClient, objectMapper).checkLatestVersion();
        versionCheckTask.run();
        boolean isLatestVersion = versionCheckTask.get();
        assertTrue(isLatestVersion);
        verify(mockHttpClient, times(1))
                .send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
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
}
