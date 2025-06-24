/*
 * SudokuFX - Copyright © 2024-present SOFT64.FR Lob2018
 * Licensed under the MIT License (MIT).
 * See the full license at: https://github.com/Lob2018/SudokuFX?tab=License-1-ov-file#readme
 */
package fr.softsf.sudokufx;

import java.sql.SQLInvalidAuthorizationSpecException;
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
 * Entry point of the Sudo application. Responsible for initializing the JavaFX UI and the Spring
 * context.
 *
 * <p>This class orchestrates the application startup process, including:
 *
 * <ul>
 *   <li>Displaying a splash screen
 *   <li>Initializing the Spring context asynchronously
 *   <li>Handling startup exceptions, including SQL authorization errors
 *   <li>Managing FXML view transitions (e.g., SplashScreen, CrashScreen, DefaultScreen)
 * </ul>
 *
 * <p>Annotations:
 *
 * <ul>
 *   <li>{@code @SpringBootApplication} – Bootstraps the Spring context.
 *   <li>{@code @ComponentScan} – Scans the specified packages for Spring components.
 * </ul>
 */
@SpringBootApplication
@ComponentScan({
    "com.gluonhq.ignite.spring",
    "fr.softsf.sudokufx.*",
})
public class SudoMain extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(SudoMain.class);

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
     * Handles SQL invalid authorization exceptions.
     *
     * @param e The general exception
     * @param sqlException The specific SQL invalid authorization exception
     */
    private static void sqlInvalidAuthorization(
            Exception e, SQLInvalidAuthorizationSpecException sqlException) {
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
            Task<Void> springInitializeTask = springInitializer.createInitializationTask();
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
            long minimumTimelapse = Math.max(0, 1000 - (System.currentTimeMillis() - startTime));
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
     * Called when the Spring context initialization task fails.
     *
     * <p>Performs the following:
     *
     * <ul>
     *   <li>Attempts to initialize the Coordinator
     *   <li>Logs the exception
     *   <li>If the error is related to SQL authorization, displays an error screen
     *   <li>Otherwise, exits the application
     * </ul>
     *
     * @param throwable the exception thrown during initialization
     */
    private void handleSpringContextTaskFailed(Throwable throwable) {
        initializeCoordinator();
        String msg = "██ Error in splash screen initialization thread {} : {}";
        SQLInvalidAuthorizationSpecException sqlInvalidAuthorizationSpecException =
                ExceptionTools.INSTANCE.getSQLInvalidAuthorizationSpecException(throwable);
        if (sqlInvalidAuthorizationSpecException == null) {
            LOG.error(msg, " – triggering Platform.exit()", throwable.getMessage(), throwable);
            Platform.exit();
        } else {
            LOG.error(msg, " – displaying crash screen", throwable.getMessage(), throwable);
            sqlInvalidAuthorization((Exception) throwable, sqlInvalidAuthorizationSpecException);
            PauseTransition pause = createViewTransition("crashscreen-view", 0);
            pause.play();
        }
    }

    /**
     * Ensures the Coordinator is initialized if not already (e.g., if Spring injection failed). In
     * all cases, sets the default scene, dynamic font size handler, and HostServices.
     */
    private void initializeCoordinator() {
        if (coordinator == null) {
            coordinator = new Coordinator(new FXMLLoader());
        }
        coordinator.setDefaultScene(stage.getScene());
        coordinator.setDynamicFontSize(new DynamicFontSize(stage.getScene()));
        coordinator.setHostServices(getHostServices());
    }

    /**
     * Creates a PauseTransition to delay loading of the next view.
     *
     * @param fxmlName The name of the FXML file to load
     * @param minimumTimelapse The minimum time to pause
     * @return A PauseTransition object
     */
    private PauseTransition createViewTransition(String fxmlName, long minimumTimelapse) {
        PauseTransition pause = new PauseTransition(Duration.millis(minimumTimelapse));
        pause.setOnFinished(
                e -> {
                    iMainView = coordinator.setRootByFXMLName(fxmlName);
                    iMainView.openingMainStage(iSplashScreenView);
                });
        return pause;
    }
}
