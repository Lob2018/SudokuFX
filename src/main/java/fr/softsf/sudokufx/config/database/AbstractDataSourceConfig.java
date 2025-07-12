/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import java.util.Objects;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.config.os.IOsFolderFactory;

import static fr.softsf.sudokufx.common.enums.Paths.DATABASE_MIGRATION_PATH;

/**
 * Abstract configuration class for setting up the application's data source. Provides configuration
 * for various data source implementations.
 */
abstract class AbstractDataSourceConfig {

    private String jdbcUrl;
    private String poolName;

    public void setJdbcUrl(String jdbcUrl) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                jdbcUrl, "JdbcUrl must not be null or blank, but was " + jdbcUrl);
        this.jdbcUrl = jdbcUrl;
    }

    public void setPoolName(String poolName) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                poolName, "PoolName must not be null or blank, but was " + poolName);
        this.poolName = poolName;
    }

    /**
     * Creates and configures the main HikariDataSource for the application. Depends on {@code
     * myLogbackConfig} to ensure logging is initialized first.
     *
     * @param iKeystore Application keystore for secure credentials; must not be null
     * @param iOsFolderFactory Factory for OS-specific folder paths; must not be null
     * @return fully configured HikariDataSource
     * @throws IllegalArgumentException if any parameter is null
     */
    @Bean
    @DependsOn("myLogbackConfig")
    HikariDataSource hikariDataSource(
            final IKeystore iKeystore, IOsFolderFactory iOsFolderFactory) {
        if (Objects.isNull(iKeystore)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The iKeystore must not be null");
        }
        if (Objects.isNull(iOsFolderFactory)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The iOsFolderFactory must not be null");
        }
        iKeystore.setupApplicationKeystore();
        return new HikariDataSource(getHikariConfig(iKeystore));
    }

    /**
     * Configure et retourne un {@link HikariConfig} avec les paramètres de connexion et pool.
     *
     * @param iKeystore fournit le nom d’utilisateur et mot de passe pour la base
     * @return la configuration Hikari prête à être utilisée
     */
    private HikariConfig getHikariConfig(IKeystore iKeystore) {
        final HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);
        config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(iKeystore.getUsername());
        config.setPassword(iKeystore.getPassword());
        config.setMaximumPoolSize(2);
        config.setMinimumIdle(1);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    /**
     * Configures Flyway database migration tool.
     *
     * @param hikariDataSource Data source used by Flyway; must not be null
     * @return configured Flyway instance
     * @throws IllegalArgumentException if {@code hikariDataSource} is null
     */
    @Bean(initMethod = "migrate")
    Flyway flyway(final HikariDataSource hikariDataSource) {
        if (Objects.isNull(hikariDataSource)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The hikariDataSource must not be null");
        }
        return Flyway.configure()
                .dataSource(hikariDataSource)
                .locations("classpath:" + DATABASE_MIGRATION_PATH.getPath())
                .load();
    }
}
