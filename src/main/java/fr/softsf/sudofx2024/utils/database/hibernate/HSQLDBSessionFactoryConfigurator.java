package fr.softsf.sudofx2024.utils.database.hibernate;

import fr.softsf.sudofx2024.model.*;
import fr.softsf.sudofx2024.utils.database.keystore.ApplicationKeystore;
import fr.softsf.sudofx2024.utils.os.OsDynamicFolders;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

@Slf4j
public final class HSQLDBSessionFactoryConfigurator implements HibernateSessionFactoryManager.IHibernateConfiguration {
    private static final String SUDOFX_DB_NAME = "sudofx2024db";
    private final String dataFolderPath;
    private final ApplicationKeystore iKeystore;
    private SessionFactory sessionFactory;

    /**
     * HSQLDB SessionFactory configurator
     *
     * @param iKeystore        The current Keystore
     * @param iOsFolderFactory The folder factory
     */
    public HSQLDBSessionFactoryConfigurator(ApplicationKeystore iKeystore, final OsDynamicFolders.IOsFoldersFactory iOsFolderFactory) {
        log.info("\n▓▓ Start of SessionFactory build");
        this.iKeystore = iKeystore;
        dataFolderPath = iOsFolderFactory.getOsDataFolderPath() + String.format("/%s", SUDOFX_DB_NAME);
    }

    @Override
    public SessionFactory getHibernateSessionFactory() {
        if (sessionFactory != null) return sessionFactory;
        try {
            final Properties properties = getProperties();
            final StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
            MetadataSources metadataSources = new MetadataSources(standardRegistry).addAnnotatedClass(Software.class).addAnnotatedClass(Menu.class).addAnnotatedClass(PlayerLanguage.class).addAnnotatedClass(Background.class).addAnnotatedClass(GameLevel.class).addAnnotatedClass(Grid.class).addAnnotatedClass(Player.class).addAnnotatedClass(Game.class);
            Metadata metadata = metadataSources.buildMetadata();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            log.error(String.format("██ Exception HSQLDBSessionFactoryConfigurator getSessionFactory : %s", e.getMessage()), e);
            throw e;
        } finally {
            log.info("\n▓▓ SessionFactory is successfully build");
        }
        return sessionFactory;
    }

    /**
     * Get Hibernate properties
     *
     * @return The Hibernate properties
     */
    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        properties.setProperty("hibernate.connection.url", "jdbc:hsqldb:file:" + getDataFolderPathForTests() + ";shutdown=true");
        properties.setProperty("hibernate.connection.username", iKeystore.getUsername());
        properties.setProperty("hibernate.connection.password", iKeystore.getPassword());
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
        return properties;
    }

    /**
     * Stubbing getter for database path
     *
     * @return The database path
     */
    String getDataFolderPathForTests() {
        return dataFolderPath;
    }
}
