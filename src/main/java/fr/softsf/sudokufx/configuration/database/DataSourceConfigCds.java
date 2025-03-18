package fr.softsf.sudokufx.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.enums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the CDS profile.
 * CDS optimizes the startup and memory management of the application without the JRE.
 */
@Configuration
@Profile("cds")
@PropertySource("classpath:fr/softsf/sudokufx/application-cds.properties")
@ExcludedFromCoverageReportGenerated
class DataSourceConfigCds extends AbstractDataSourceConfig {
    @Bean
    @Override
    @DependsOn({"logbackInitialization"})
    HikariDataSource hikariDataSource(final IKeystore iKeystore, final IOsFolderFactory iOsFolderFactory) {
        this.setJdbcUrl("jdbc:hsqldb:mem:" + DATABASE_NAME.getPath() + "CDS;DB_CLOSE_DELAY=-1;shutdown=true");
        this.setPoolName("SudokuFXCDSHikariConnection");
        return super.hikariDataSource(iKeystore, iOsFolderFactory);
    }
}

