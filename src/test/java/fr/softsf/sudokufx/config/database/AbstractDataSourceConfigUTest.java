/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.config.os.IOsFolderFactory;
import fr.softsf.sudokufx.config.os.OsFolderFactoryManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class AbstractDataSourceConfigUTest {

    static class TestDataSourceConfig extends AbstractDataSourceConfig {}

    private IOsFolderFactory iCurrentIOsFolderFactory;
    private TestDataSourceConfig config;
    private final ApplicationKeystore keystore;

    @Autowired
    public AbstractDataSourceConfigUTest(ApplicationKeystore keystore) {
        this.keystore = keystore;
    }

    @BeforeEach
    void setUp() {
        OsFolderFactoryManager osFolderFactoryManager = new OsFolderFactoryManager();
        iCurrentIOsFolderFactory = osFolderFactoryManager.iOsFolderFactory();
        config = new TestDataSourceConfig();
    }

    @Test
    void givenBlankJdbcUrl_whenSetJdbcUrl_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> config.setJdbcUrl(" "));
        assertTrue(ex.getMessage().contains("JdbcUrl must not be null or blank"));
    }

    @Test
    void givenNullPoolName_whenSetPoolName_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> config.setPoolName(null));
        assertTrue(ex.getMessage().contains("PoolName must not be null or blank"));
    }

    @Test
    @SuppressWarnings("resource")
    void givenNullKeystore_whenHikariDataSourceCalled_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> config.hikariDataSource(null, iCurrentIOsFolderFactory));
        assertTrue(ex.getMessage().contains("iKeystore must not be null"));
    }

    @Test
    @SuppressWarnings("resource")
    void givenNullIOsFolderFactory_whenHikariDataSourceCalled_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> config.hikariDataSource(keystore, null));
        assertTrue(ex.getMessage().contains("iOsFolderFactory must not be null"));
    }

    @Test
    void givenNullHikariDataSource_whenFlywayCalled_thenThrowsIllegalArgumentException() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> config.flyway(null));
        assertTrue(ex.getMessage().contains("hikariDataSource must not be null"));
    }

    @Test
    void givenValidJdbcUrlAndPoolName_whenHikariDataSourceCreated_thenPropertiesAreSet() {
        config.setJdbcUrl("jdbc:hsqldb:mem:testdb");
        config.setPoolName("TestPool");

        try (HikariDataSource ds = config.hikariDataSource(keystore, iCurrentIOsFolderFactory)) {
            assertNotNull(ds);
            assertEquals("TestPool", ds.getPoolName());
            assertEquals("org.hsqldb.jdbc.JDBCDriver", ds.getDriverClassName());
            assertEquals("jdbc:hsqldb:mem:testdb", ds.getJdbcUrl());
            assertEquals(keystore.getUsername(), ds.getUsername());
            assertEquals(keystore.getPassword(), ds.getPassword());
        }
    }
}
