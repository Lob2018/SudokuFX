/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.Objects;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.gluonhq.ignite.spring.SpringContext;

import fr.softsf.sudokufx.common.enums.I18n;
import fr.softsf.sudokufx.common.enums.LogBackTxt;
import fr.softsf.sudokufx.common.exception.ExceptionTools;
import fr.softsf.sudokufx.common.interfaces.IMainView;
import fr.softsf.sudokufx.common.interfaces.ISplashScreenView;
import fr.softsf.sudokufx.common.util.DynamicFontSize;
import fr.softsf.sudokufx.common.util.SpringContextInitializer;
import fr.softsf.sudokufx.navigation.Coordinator;
import fr.softsf.sudokufx.view.SplashScreenView;

/**
 * Main JavaFX application class launching the UI and initializing the Spring context.
 *
 * <p>Manages the splash screen, asynchronous Spring initialization, error handling, and view
 * transitions. Handles fatal errors by showing a crash screen or exiting.
 *
 * <p>Uses a Coordinator component for view management and leverages ExceptionTools for centralized
 * exception handling.
 *
 * <p>This class is the JavaFX entry point and controls the primary Stage lifecycle.
 */
@SpringBootApplication
@ComponentScan({
    "com.gluonhq.ignite.spring",
    "fr.softsf.sudokufx.*",
})
public class SudoMain extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(SudoMain.class);
    private static final long MINIMUM_TRANSITION_DELAY_MS = 1000L;
    private final SpringContext context = new SpringContext(this);
    private ISplashScreenView iSplashScreenView;
    private IMainView iMainView;
    private Stage stage;

    @Autowired private Coordinator coordinator;

    /**
     * Main entry point for the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Logs an SQLInvalidAuthorizationSpecException with optional authentication details.
     *
     * <p>Throws an {@link IllegalArgumentException} if any argument is {@code null}. Logs the
     * exception, and if the SQL state is {@code "28000"} or {@code "28501"}, logs a specific
     * authentication message.
     *
     * @param e the general exception (must not be {@code null})
     * @param sqlException the SQL authorization exception (must not be {@code null})
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    private static void logSqlInvalidAuthorization(
            Exception e, SQLInvalidAuthorizationSpecException sqlException) {
        if (Objects.isNull(e)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "sqlInvalidAuthorization argument e must not be null");
        }
        if (Objects.isNull(sqlException)) {
            throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                    "sqlInvalidAuthorization argument sqlException must not be null");
        }
        LOG.error("██ SQLInvalidAuthorizationSpecException catch : {}", e.getMessage(), e);
        String sqlState = sqlException.getSQLState();
        if ("28000".equals(sqlState) || "28501".equals(sqlState)) {
            LOG.error(
                    "██ SQLInvalidAuthorizationSpecException with sqlstate==(28000||28501) catch :"
                            + " {}",
                    e.getMessage(),
                    e);
            LOG.info(
                    "\n\n{}",
                    LogBackTxt.SQL_INVALID_AUTHORIZATION_SPEC_EXCEPTION.getLogBackMessage());
        }
    }

    /**
     * Initializes the application by:
     *
     * <ol>
     *   <li>Setting the UI language based on the host system
     *   <li>Displaying the splash screen
     *   <li>Recording the initialization start time
     *   <li>Asynchronously launching the Spring context initialization task
     *   <li>Handling success or failure outcomes via registered callbacks
     * </ol>
     *
     * @param stage the primary JavaFX stage used to display the UI
     */
    @Override
    public void start(final Stage stage) {
        try {
            this.stage = stage;
            I18n.INSTANCE.setLanguageBasedOnTheHostEnvironment();
            iSplashScreenView = new SplashScreenView(stage);
            long startTime = System.currentTimeMillis();

            SpringContextInitializer springInitializer = new SpringContextInitializer(context);
            Task<Void> springInitializeTask =
                    springInitializer.createInitializationTask(this.getClass());
            springInitializeTask.setOnSucceeded(event -> handleSpringContextTaskSuccess(startTime));
            springInitializeTask.setOnFailed(
                    event -> {
                        Throwable th = springInitializeTask.getException();
                        handleSpringContextTaskFailed(th);
                    });
            springInitializer.runInitializationTask(springInitializeTask);
        } catch (Exception ex) {
            LOG.error(
                    "██ Exception catch inside start() : {}, triggering Platform.exit()",
                    ex.getMessage(),
                    ex);
            Platform.exit();
        }
    }

    /**
     * Handles the success of the Spring context initialization task. This method is called when the
     * initialization task completes successfully.
     *
     * @param startTime The time (in milliseconds) when the initialization started. This is used to
     *     apply the minimum delay of 1s before starting the transition.
     */
    private void handleSpringContextTaskSuccess(long startTime) {
        try {
            initializeCoordinator();
            long minimumTimelapse =
                    Math.max(
                            0,
                            MINIMUM_TRANSITION_DELAY_MS - (System.currentTimeMillis() - startTime));
            createViewTransition("default-view", minimumTimelapse).play();
        } catch (Exception ex) {
            LOG.error(
                    "██ Exception caught after Spring Context initialization with FXML : {},"
                            + " triggering Platform.exit()",
                    ex.getMessage(),
                    ex);
            Platform.exit();
        }
    }

    /**
     * Handles failure of the Spring context initialization task.
     *
     * <p>If {@code throwable} is {@code null}, logs and throws an {@link IllegalArgumentException}.
     * Otherwise:
     *
     * <ul>
     *   <li>Initializes the Coordinator
     *   <li>Logs the exception
     *   <li>If it's an SQL authorization error, logs details and displays a crash screen
     *   <li>Otherwise, exits the application
     * </ul>
     *
     * <p>If any exception occurs during the handling itself, it is logged and the application
     * exits.
     *
     * @param throwable the exception thrown during initialization (must not be {@code null})
     */
    private void handleSpringContextTaskFailed(Throwable throwable) {
        try {
            if (Objects.isNull(throwable)) {
                throw ExceptionTools.INSTANCE.logAndInstantiateIllegalArgument(
                        "Spring context task failed but no exception provided");
            }
            initializeCoordinator();
            String msg = "██ Error in splash screen initialization thread {} : {}";
            SQLInvalidAuthorizationSpecException sqlInvalidAuthorizationSpecException =
                    ExceptionTools.INSTANCE.findSQLInvalidAuthException(throwable);
            if (Objects.isNull(sqlInvalidAuthorizationSpecException)) {
                LOG.error(msg, " – triggering Platform.exit()", throwable.getMessage(), throwable);
                Platform.exit();
            } else {
                LOG.error(msg, " – displaying crash screen", throwable.getMessage(), throwable);
                logSqlInvalidAuthorization(
                        (Exception) throwable, sqlInvalidAuthorizationSpecException);
                PauseTransition pause = createViewTransition("crashscreen-view", 0);
                pause.play();
            }
        } catch (Exception ex) {
            LOG.error(
                    "██ Exception caught while handling task failure: {}, triggering"
                            + " Platform.exit()",
                    ex.getMessage(),
                    ex);
            Platform.exit();
        }
    }

    /**
     * Ensures the Coordinator is initialized if not already (e.g., if Spring injection failed). In
     * all cases, sets the default scene, dynamic font size handler, and HostServices.
     */
    private void initializeCoordinator() {
        if (Objects.isNull(coordinator)) {
            coordinator = new Coordinator(new FXMLLoader());
        }
        coordinator.setDefaultScene(stage.getScene());
        coordinator.setDynamicFontSize(new DynamicFontSize(stage.getScene()));
        coordinator.setHostServices(getHostServices());
    }

    /**
     * Creates a pause before loading the specified FXML view.
     *
     * @param fxmlName the FXML file name to load (must not be null or blank)
     * @param minimumTimelapse the pause duration in milliseconds
     * @return the configured PauseTransition
     * @throws IllegalArgumentException if {@code fxmlName} is null or blank
     */
    private PauseTransition createViewTransition(String fxmlName, long minimumTimelapse) {
        ExceptionTools.INSTANCE.logAndThrowIllegalArgumentIfBlank(
                fxmlName, "FxmlName must not be null or blank, but was " + fxmlName);
        PauseTransition pause = new PauseTransition(Duration.millis(minimumTimelapse));
        pause.setOnFinished(
                e -> {
                    iMainView = coordinator.setRootByFXMLName(fxmlName);
                    iMainView.openingMainStage(iSplashScreenView);
                });
        return pause;
    }
}
