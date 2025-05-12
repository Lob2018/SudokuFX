/* SudokuFX © 2025 Licensed under the MIT license (MIT) - present the owner Lob2018 - see https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme for details */
package fr.softsf.sudokufx.configuration.database;

import org.springframework.context.annotation.*;

import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;

import static fr.softsf.sudokufx.enums.Paths.DATABASE_NAME;

/** Overrides Abstract DataSource hikariDataSource for the test profile. */
@Configuration
@Profile("test")
@PropertySource("classpath:application-test.properties")
@ExcludedFromCoverageReportGenerated
public class DataSourceConfigTest extends AbstractDataSourceConfig {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(
            final IKeystore iKeystore, final IOsFolderFactory iOsFolderFactory) {
        this.setJdbcUrl(
                "jdbc:hsqldb:mem:"
                        + DATABASE_NAME.getPath()
                        + "Test;DB_CLOSE_DELAY=-1;shutdown=true");
        this.setPoolName("SudokuFXTestHikariConnection");
        return super.hikariDataSource(iKeystore, iOsFolderFactory);
    }
}
