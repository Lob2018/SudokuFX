/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.common.annotation.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.config.os.IOsFolder;

import static fr.softsf.sudokufx.common.enums.AppPaths.DATABASE_NAME;

/** Overrides Abstract DataSource hikariDataSource for the default profile. */
@Configuration
@Profile("default")
@PropertySource("classpath:fr/softsf/sudokufx/application.properties")
@ExcludedFromCoverageReportGenerated
class DataSourceConfigDefault extends AbstractDataSourceConfig {
    @Bean
    @DependsOn({"myLogbackConfig"})
    @Override
    HikariDataSource hikariDataSource(final IKeystore iKeystore, final IOsFolder iOsFolder) {
        this.setJdbcUrl(
                "jdbc:hsqldb:file:"
                        + iOsFolder.getOsDataFolderPath()
                        + "/"
                        + DATABASE_NAME.getPath()
                        + ";shutdown=true");
        this.setPoolName("SudokuFXHikariConnection");
        return super.hikariDataSource(iKeystore, iOsFolder);
    }
}
