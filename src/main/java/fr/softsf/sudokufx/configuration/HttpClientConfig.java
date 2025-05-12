/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.configuration;

import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import javax.net.ssl.SSLContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;

/** Configures a secure HttpClient bean with TLS 1.2, no redirects, and a 5-second timeout. */
@Configuration
@ExcludedFromCoverageReportGenerated
public class HttpClientConfig {

    /**
     * Provides a secure HttpClient with TLS 1.2.
     *
     * @return Configured HttpClient instance.
     * @throws NoSuchAlgorithmException if TLS 1.2 is unsupported.
     * @throws KeyManagementException if SSL context initialization fails.
     */
    @Bean
    HttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException {
        final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null);
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .sslContext(sslContext)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }
}
