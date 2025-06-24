/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import org.springframework.context.annotation.*;

import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.common.annotation.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.config.os.IOsFolderFactory;

import static fr.softsf.sudokufx.common.enums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the CDS profile. CDS optimizes the startup and
 * memory management of the application without the JRE.
 */
@Configuration
@Profile("cds")
@PropertySource("classpath:fr/softsf/sudokufx/application-cds.properties")
@ExcludedFromCoverageReportGenerated
class DataSourceConfigCds extends AbstractDataSourceConfig {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(
            final IKeystore iKeystore, final IOsFolderFactory iOsFolderFactory) {
        this.setJdbcUrl(
                "jdbc:hsqldb:mem:"
                        + DATABASE_NAME.getPath()
                        + "CDS;DB_CLOSE_DELAY=-1;shutdown=true");
        this.setPoolName("SudokuFXCDSHikariConnection");
        return super.hikariDataSource(iKeystore, iOsFolderFactory);
    }
}
