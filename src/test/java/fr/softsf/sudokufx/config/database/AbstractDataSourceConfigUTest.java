/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zaxxer.hikari.HikariDataSource;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.config.os.IOsFolder;
import fr.softsf.sudokufx.config.os.OsFoldersConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class AbstractDataSourceConfigUTest {

    static class TestDataSourceConfig extends AbstractDataSourceConfig {}

    private IOsFolder iCurrentIOsFolder;
    private TestDataSourceConfig config;
    private final ApplicationKeystore keystore;

    @Autowired
    public AbstractDataSourceConfigUTest(ApplicationKeystore keystore) {
        this.keystore = keystore;
    }

    @BeforeEach
    void setUp() {
        OsFoldersConfig osFoldersConfig = new OsFoldersConfig();
        iCurrentIOsFolder = osFoldersConfig.iOsFolderFactory();
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
                        () -> config.hikariDataSource(null, iCurrentIOsFolder));
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

        try (HikariDataSource ds = config.hikariDataSource(keystore, iCurrentIOsFolder)) {
            assertNotNull(ds);
            assertEquals("TestPool", ds.getPoolName());
            assertEquals("org.hsqldb.jdbc.JDBCDriver", ds.getDriverClassName());
            assertEquals("jdbc:hsqldb:mem:testdb", ds.getJdbcUrl());
            assertEquals(keystore.getUsername(), ds.getUsername());
            assertEquals(keystore.getPassword(), ds.getPassword());
        }
    }
}
