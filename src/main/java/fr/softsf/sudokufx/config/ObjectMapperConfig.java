/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Configuration class for Jackson's ObjectMapper. This bean provides a pre-configured ObjectMapper
 * with support for Java 8 date/time API, relying on Jackson 3.x native ISO-8601 serialization.
 */
@Configuration
public class ObjectMapperConfig {

    /**
     * Creates and configures an ObjectMapper bean using the JsonMapper builder. - Automatically
     * registers available Jackson modules (e.g., JavaTimeModule for Instant). - Uses native
     * ISO-8601 format for date/time serialization as per Jackson 3.x defaults.
     *
     * @return a pre-configured ObjectMapper instance.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }
}
