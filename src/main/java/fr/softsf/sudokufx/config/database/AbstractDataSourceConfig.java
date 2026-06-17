/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import java.sql.SQLException;
import java.util.Objects;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.common.exception.DatabaseIntegrityException;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.config.os.IOsFolder;

import static fr.softsf.sudokufx.common.enums.AppPaths.DATABASE_MIGRATION_PATH;

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
     * @param iOsFolder Factory for OS-specific folder paths; must not be null
     * @return fully configured HikariDataSource
     * @throws IllegalArgumentException if any parameter is null
     */
    @Bean
    @DependsOn("myLogbackConfig")
    HikariDataSource hikariDataSource(final IKeystore iKeystore, IOsFolder iOsFolder) {
        if (Objects.isNull(iKeystore)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The iKeystore must not be null");
        }
        if (Objects.isNull(iOsFolder)) {
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
     * @param hikariDataSource Data source used by Flyway; must not be null (can be a proxy-wrapped
     *     instance)
     * @return configured Flyway instance
     * @throws IllegalArgumentException if {@code hikariDataSource} is null
     */
    @Bean(initMethod = "migrate")
    Flyway flyway(final DataSource hikariDataSource) {
        if (Objects.isNull(hikariDataSource)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "The hikariDataSource must not be null");
        }
        Flyway flyway =
                Flyway.configure()
                        .dataSource(hikariDataSource)
                        .locations("classpath:" + DATABASE_MIGRATION_PATH.getPath())
                        .load();
        flyway.migrate();
        validateDatabaseState(hikariDataSource);
        return flyway;
    }

    /**
     * Validates {@code gamelevel} table integrity post-Flyway migration.
     *
     * <p>Ensures exactly 3 distinct levels exist in the database.
     *
     * <p><b>Note:</b> Throws {@link DatabaseIntegrityException} to trigger the {@code SudoMain}
     * crash handler, which displays the recovery screen for data restoration when the integrity
     * check fails or a SQL exception occurs.
     *
     * @param dataSource the data source to validate
     * @throws DatabaseIntegrityException if the level count is incorrect or a SQL system error
     *     occurs
     */
    private void validateDatabaseState(DataSource dataSource) {
        String query = "SELECT COUNT(DISTINCT LEVEL) FROM gamelevel";
        try (var connection = dataSource.getConnection();
                var preparedStatement = connection.prepareStatement(query);
                var resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count != 3) {
                    throw new DatabaseIntegrityException(
                            "DATABASE INTEGRITY ERROR: Expected 3 distinct levels, found " + count,
                            null);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseIntegrityException(
                    "Critical failure accessing database during validation", e);
        }
    }
}
