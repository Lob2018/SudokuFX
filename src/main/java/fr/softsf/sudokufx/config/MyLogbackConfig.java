/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter2;
import fr.softsf.sudokufx.config.os.IOsFolder;

import static fr.softsf.sudokufx.common.enums.LogBackTxt.ASCII_LOGO;
import static fr.softsf.sudokufx.common.enums.LogBackTxt.OPTIMIZING;
import static fr.softsf.sudokufx.common.enums.Paths.CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS;
import static fr.softsf.sudokufx.common.enums.Paths.CONFIG_LOGBACK_PATH;
import static fr.softsf.sudokufx.common.enums.Paths.LOGS_FILE_NAME_PATH;

/**
 * Configuration class for Logback logging framework. This class sets up and configures Logback for
 * the application.
 */
@Configuration
public class MyLogbackConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MyLogbackConfig.class);
    private final String logsFolderPath;
    private String logBackPath = CONFIG_LOGBACK_PATH.getPath();

    /**
     * Constructor that initializes the logging configuration.
     *
     * @param iOsFolder Factory for creating OS-specific folder paths.
     */
    public MyLogbackConfig(IOsFolder iOsFolder) {
        logsFolderPath = iOsFolder.getOsLogsFolderPath();
        System.setProperty("logs", logsFolderPath + LOGS_FILE_NAME_PATH.getPath());
        LoggerContext context = configureLogback();
        printLogStatus(context);
        printLogEntryMessage();
    }

    public String getLogsFolderPath() {
        return logsFolderPath;
    }

    /**
     * Prints the logger status to the log in case of errors or warnings.
     *
     * @param context The current logger context
     */
    private void printLogStatus(final LoggerContext context) {
        StatusPrinter2 statusPrinter2 = new StatusPrinter2();
        statusPrinter2.printInCaseOfErrorsOrWarnings(context);
    }

    /**
     * Logs the ASCII logo (with app name and version), an optional optimization message at startup.
     */
    public void printLogEntryMessage() {
        LOG.info(
                ASCII_LOGO.getLogBackMessage(),
                JVMApplicationProperties.INSTANCE.getAppName(),
                JVMApplicationProperties.INSTANCE.getAppVersion());
        if (JVMApplicationProperties.INSTANCE.isSpringContextExitOnRefresh()) {
            LOG.info(OPTIMIZING.getLogBackMessage());
        }
    }

    /**
     * Configures Logback using the specified configuration file.
     *
     * @return The configured LoggerContext
     * @throws IllegalArgumentException if the configuration resource cannot be found (inputStream
     *     is null)
     * @throws RuntimeException if there's an error during configuration
     */
    LoggerContext configureLogback() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try (InputStream inputStream = MyLogbackConfig.class.getResourceAsStream(logBackPath)) {
            if (Objects.isNull(inputStream)) {
                throw new IllegalArgumentException("The inputStream must not be null");
            }
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(inputStream);
        } catch (JoranException | IOException e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    /**
     * Sets an invalid logback configuration path for testing purposes. This method should only be
     * used in test scenarios.
     */
    void setLogBackPathForTests() {
        logBackPath = CONFIG_LOGBACK_INVALID_PATH_FOR_TESTS.getPath();
    }
}
