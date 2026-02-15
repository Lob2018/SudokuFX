/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.integration.service.external;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
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
import fr.softsf.sudokufx.config.JVMApplicationProperties;
import fr.softsf.sudokufx.service.external.VersionService;
import fr.softsf.sudokufx.service.ui.SpinnerService;

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
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v1.0.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v1.0.0.0",
                "commit": {
                  "sha": "e59cb3b14bd859250910d51985f50dabb89ee788",
                  "url": "https://api.github.com/repos/Lob2018/SudokuFX/commits/e59cb3b14bd859250910d51985f50dabb89ee788"
                },
                "node_id": "REF_kwDOLH8vKbByZWZzL3RhZ3MvdjEuMC4w"
              },
              {
                "name": "v0.9.0.0",
                "zipball_url": "https://api.github.com/repos/Lob2018/SudokuFX/zipball/refs/tags/v0.9.0.0",
                "tarball_url": "https://api.github.com/repos/Lob2018/SudokuFX/tarball/refs/tags/v0.9.0.0",
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
    @Mock private SpinnerService spinnerService;
    @Mock private HttpResponse<InputStream> mockResponse;

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
    @SuppressWarnings("unchecked")
    void givenEmptyGitHubResponse_whenCheckLatestVersion_thenLatestVersionTrue() throws Exception {
        String emptyArray = "[]";
        InputStream inputStream =
                new ByteArrayInputStream(
                        emptyArray.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get(), "Should return true (up-to-date) when GitHub returns an empty list");
    }

    @ParameterizedTest
    @ValueSource(ints = {403, 404, 500})
    @SuppressWarnings("unchecked")
    void givenNon200HttpStatus_whenCheckLatestVersion_thenLatestVersionTrue(int httpStatusCode)
            throws Exception {
        when(mockResponse.statusCode()).thenReturn(httpStatusCode);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get());
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("██ Exception GitHub API error: status " + httpStatusCode));
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenDifferentVersionOnGitHub_whenCheckLatestVersion_thenReturnsFalse() throws Exception {
        HttpResponse<InputStream> localMockResponse = mock(HttpResponse.class);
        String onlineVersion = "v9.9.9.9";
        String jsonResponse = String.format(JSON, onlineVersion);
        InputStream inputStream =
                new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));
        when(localMockResponse.statusCode()).thenReturn(200);
        when(localMockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(localMockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertFalse(task.get());
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenMatchingVersionOnGitHub_whenCheckLatestVersion_thenReturnsTrue() throws Exception {
        String currentVersionRaw = JVMApplicationProperties.INSTANCE.getAppVersion();
        String jsonResponse = String.format(JSON, currentVersionRaw);
        InputStream inputStream =
                new ByteArrayInputStream(
                        jsonResponse.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get(), "Should return true as versions match");
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenInvalidJsonResponse_whenCheckLatestVersion_thenReturnsTrueAndLogsError()
            throws Exception {
        String invalidJson = "{ invalid json }";
        InputStream inputStream =
                new ByteArrayInputStream(
                        invalidJson.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get(), "Should return true on parsing failure");
        assertTrue(
                logWatcher
                        .list
                        .getFirst()
                        .getFormattedMessage()
                        .contains("██ Exception during GitHub JSON parsing:"),
                "Error log should be present");
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenEmptyResponseBody_whenCheckLatestVersion_thenReturnsTrueAndLogsWarn()
            throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get());
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains(
                                                        "▓▓ Version check: GitHub returned an empty"
                                                                + " response body")));
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenTagTooLong_whenCheckLatestVersion_thenReturnsTrueAndLogsError() throws Exception {
        String longTagName = "v" + "1".repeat(257);
        String jsonResponse = String.format(JSON, longTagName);
        InputStream inputStream =
                new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get());
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains(
                                                        "██ Version check error: tag name too"
                                                                + " large")));
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenEmptyNormalizedTagName_whenCheckLatestVersion_thenReturnsTrueAndLogsWarn()
            throws Exception {
        String jsonResponse = String.format(JSON, "v ");
        InputStream inputStream =
                new ByteArrayInputStream(jsonResponse.getBytes(StandardCharsets.UTF_8));
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(inputStream);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get());
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(
                                event ->
                                        event.getFormattedMessage()
                                                .contains(
                                                        "▓▓ Version check: tag name is empty after"
                                                                + " normalization")));
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenInterruptedThread_whenCheckLatestVersion_thenReturnsTrueAndLogsWarn()
            throws Exception {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Interrupted"));
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get(), "Should return true on interruption");
        assertTrue(
                logWatcher.list.stream()
                        .anyMatch(event -> event.getFormattedMessage().contains("▓▓ Interrupted")));
    }
}
