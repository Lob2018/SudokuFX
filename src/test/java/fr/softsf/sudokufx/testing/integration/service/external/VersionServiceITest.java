/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.testing.integration.service.external;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    @SuppressWarnings("unchecked")
    void givenEmptyGitHubResponse_whenCheckLatestVersion_thenLatestVersionTrue() throws Exception {
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("[]");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertTrue(task.get());
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
        String onlineVersion = "v9.9.9.9";
        String jsonResponse = String.format(JSON, onlineVersion);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        Task<Boolean> task =
                new VersionService(mockHttpClient, objectMapper, spinnerService)
                        .checkLatestVersion();
        task.run();
        assertFalse(task.get(), "Should return false as 9.9.9.9 != current version");
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenMatchingVersionOnGitHub_whenCheckLatestVersion_thenReturnsTrue() throws Exception {
        String currentVersionRaw = JVMApplicationProperties.INSTANCE.getAppVersion();
        String jsonResponse = String.format(JSON, currentVersionRaw);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(jsonResponse);
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
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{ invalid json }");
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
                        .contains("██ Exception parsing GitHub API response:"));
    }
}
