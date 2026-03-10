/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the GNU General Public License v3.0 (GPL-3.0).
 * See the full license at: https://github.com/Lob2018/SudokuFX/blob/main/LICENSE.txt
 */
package fr.softsf.sudokufx.config;

import java.util.Objects;

import fr.softsf.sudokufx.common.util.MyRegex;

/**
 * Utility enum for managing JVM application properties. Provides methods to retrieve and validate
 * the application name, version, organization, and license from system properties, and manage the
 * Spring context exit behavior.
 */
public enum JVMApplicationProperties {
    INSTANCE;

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";
    private static final String APP_ORGANIZATION_PROPERTY = "app.organization";
    private static final String APP_LICENSE_PROPERTY = "app.license";
    private static final String ON_REFRESH = "onRefresh";
    private String springContextExitOnRefresh = "spring.context.exit";
    private String appName = "";
    private String appVersion = "";
    private String appOrganization = "";
    private String appLicense = "";
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
     * Sets the system property key to determine if the Spring context should exit on refresh. Used
     * for testing purposes.
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
            appName =
                    MyRegex.INSTANCE.isValidatedByRegex(
                                    systemValue, MyRegex.INSTANCE.getAlphanumericPattern())
                            ? systemValue
                            : "";
        }
        return appName;
    }

    /**
     * Retrieves the current application version from system properties.
     *
     * @return The current application version prefixed with 'v' if valid, or an empty string if
     *     invalid or not set.
     */
    public String getAppVersion() {
        if (appVersion.isEmpty()) {
            String systemValue = System.getProperty(APP_VERSION_PROPERTY);
            appVersion =
                    MyRegex.INSTANCE.isValidatedByRegex(
                                    systemValue, MyRegex.INSTANCE.getVersionPattern())
                            ? "v" + systemValue
                            : "";
        }
        return appVersion;
    }

    /**
     * Retrieves the current application organization from system properties.
     *
     * @return The current application organization if valid, or an empty string if invalid or not
     *     set.
     */
    public String getAppOrganization() {
        if (appOrganization.isEmpty()) {
            String systemValue = System.getProperty(APP_ORGANIZATION_PROPERTY);
            appOrganization =
                    MyRegex.INSTANCE.isValidatedByRegex(
                                    systemValue, MyRegex.INSTANCE.getAlphanumericPattern())
                            ? systemValue
                            : "";
        }
        return appOrganization;
    }

    /**
     * Retrieves the current application license from system properties.
     *
     * @return The current application license if valid, or an empty string if invalid or not set.
     */
    public String getAppLicense() {
        if (appLicense.isEmpty()) {
            String systemValue = System.getProperty(APP_LICENSE_PROPERTY);
            appLicense =
                    MyRegex.INSTANCE.isValidatedByRegex(
                                    systemValue, MyRegex.INSTANCE.getAlphanumericPattern())
                            ? systemValue
                            : "";
        }
        return appLicense;
    }

    /**
     * Generates the standardized window title for the application.
     *
     * <p>The title follows the pattern: {@code "AppName Version • Organization"}. If the version
     * string is empty, the leading space and version segment are omitted.
     *
     * @return a formatted {@code String} representing the full application identity
     */
    public String getWindowTitle() {
        final String name = getAppName();
        final String version = getAppVersion();
        final String organization = getAppOrganization();
        final String versionSuffix = version.isEmpty() ? "" : " " + version;
        return String.format("%s%s • %s", name, versionSuffix, organization);
    }

    /** Initialize the Spring context exit behavior to null for testing purposes. */
    void setInitSpringContextExitForTests() {
        springContextExit = null;
    }

    /** Sets the Spring context exit behavior to "onRefresh" for testing purposes. */
    void setOnRefreshSpringContextExitForTests() {
        springContextExit = ON_REFRESH;
    }

    /** Sets the Spring context exit behavior to "never" for testing purposes. */
    void setNeverSpringContextExitForTests() {
        springContextExit = "never";
    }

    /** Resets the application name for testing purposes. */
    void setEmptyAppNamePropertyForTests() {
        appName = "";
    }

    /** Resets the application version for testing purposes. */
    void setEmptyAppVersionPropertyForTests() {
        appVersion = "";
    }

    /** Resets the application organization for testing purposes. */
    void setEmptyAppOrganizationPropertyForTests() {
        appOrganization = "";
    }

    /** Resets the application license for testing purposes. */
    void setEmptyAppLicensePropertyForTests() {
        appLicense = "";
    }

    /** Sets the application name for testing purposes. */
    void setAppNameForTests() {
        this.appName = "SudokuFX1";
    }

    /** Sets the application version for testing purposes. */
    void setAppVersionForTests() {
        this.appVersion = "v0.0.0";
    }

    /** Sets the application organization for testing purposes. */
    void setAppOrganizationForTests() {
        this.appOrganization = "MySoft64.fr";
    }
}
