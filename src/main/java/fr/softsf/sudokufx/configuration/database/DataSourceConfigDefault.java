package fr.softsf.sudokufx.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import fr.softsf.sudokufx.configuration.os.IOsFolderFactory;
import org.springframework.context.annotation.*;

import static fr.softsf.sudokufx.enums.Paths.DATABASE_NAME;

/**
 * Overrides Abstract DataSource hikariDataSource for the default profile.
 */
@Configuration
@Profile("default")
@PropertySource("classpath:fr/softsf/sudokufx/application.properties")
@ExcludedFromCoverageReportGenerated
class DataSourceConfigDefault extends AbstractDataSourceConfig {

    @Bean
    @DependsOn({"logbackInitialization"})
    @Override
    HikariDataSource hikariDataSource(final IKeystore iKeystore, final IOsFolderFactory iOsFolderFactory) {
        this.setJdbcUrl("jdbc:hsqldb:file:" + iOsFolderFactory.getOsDataFolderPath() + "/" + DATABASE_NAME.getPath() + ";shutdown=true");
        this.setPoolName("SudokuFXHikariConnection");
        return super.hikariDataSource(iKeystore, iOsFolderFactory);
    }
}
