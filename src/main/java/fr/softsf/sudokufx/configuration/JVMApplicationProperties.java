package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.enums.MyRegex;

import java.util.Objects;

/**
 * Utility enum for managing JVM application properties.
 * This enum provides methods to retrieve the application name
 * and version from system properties.
 */
public enum JVMApplicationProperties {
    INSTANCE;

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static final String ON_REFRESH = "onRefresh";
    private String springContextExitOnRefresh = "spring.context.exit";
    private String appName = "";
    private String appVersion = "";
    private String springContextExit;

    /**
     * Determines if the Spring context should exit on refresh.
     *
     * @return true if springContextExit is "onRefresh"; false otherwise.
     */
    public boolean isSpringContextExitOnRefresh() {
        if (springContextExit == null) {
            springContextExit = System.getProperty(springContextExitOnRefresh);
            return Objects.equals(springContextExit, ON_REFRESH);
        }
        return ON_REFRESH.equals(springContextExit);
    }

    /**
     * Sets the system property key to determine if the Spring context should exit on refresh.
     * Used for testing purposes.
     */
    void setSpringContextExitInRefresh() {
        springContextExitOnRefresh = APP_NAME_PROPERTY;
    }

    /**
     * Retrieves the current application name from system properties.
     *
     * @return The current application name if valid, or an empty string if invalid or not set.
     */
    public String getAppName() {
        if (appName.isEmpty()) {
            String systemValue = System.getProperty(APP_NAME_PROPERTY);
            appName = MyRegex.INSTANCE.isValidatedByRegex(systemValue, MyRegex.INSTANCE.getAlphanumericPattern()) ? systemValue : "";
        }
        return appName;
    }

    /**
     * Retrieves the current application version from system properties.
     *
     * @return The current application version prefixed with 'v' if valid, or an empty string if invalid or not set.
     */
    public String getAppVersion() {
        if (appVersion.isEmpty()) {
            String systemValue = System.getProperty(APP_VERSION_PROPERTY);
            appVersion = MyRegex.INSTANCE.isValidatedByRegex(systemValue, MyRegex.INSTANCE.getVersionPattern()) ? "v" + systemValue : "";
        }
        return appVersion;
    }

    /**
     * Initialize the Spring context exit behavior to null for testing purposes.
     */
    void setInitSpringContextExitForTests() {
        springContextExit = null;
    }

    /**
     * Sets the Spring context exit behavior to "onRefresh" for testing purposes.
     */
    void setOnRefreshSpringContextExitForTests() {
        springContextExit = ON_REFRESH;
    }

    /**
     * Sets the Spring context exit behavior to "never" for testing purposes.
     */
    void setNeverSpringContextExitForTests() {
        springContextExit = "never";
    }

    /**
     * Resets the application name for testing purposes.
     */
    void setEmptyAppNamePropertyForTests() {
        appName = "";
    }

    /**
     * Resets the application version for testing purposes.
     */
    void setEmptyAppVersionPropertyForTests() {
        appVersion = "";
    }
}
