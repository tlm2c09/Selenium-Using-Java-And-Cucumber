package configs;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.core.exception.CucumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class AppiumServer {

    private static final Logger logger = LogManager.getLogger(AppiumServer.class.getSimpleName());
    private static final String NODE_PATH = "YOUR_NODE_PATH";
    private static final String APPIUM_PATH = "YOUR_APPIUM_PATH";
    static AppiumDriverLocalService service;

    public static void startAppiumServer() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        if (service == null || !service.isRunning()) {
            throw new CucumberException("Could not start Appium server. Please check the logs for more information");
        }
        logger.info("Appium server started.");
    }

    public static void stopAppiumServer() {
        if (service.isRunning()) {
            service.stop();
            logger.info("The Appium server is stopped.");
        } else logger.info("No Appium server is running.");
    }

    public static AppiumServiceBuilder getDefaultAppiumServiceBuilder() {
        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        serviceBuilder.usingDriverExecutable(new File(NODE_PATH));
        serviceBuilder.withAppiumJS(new File(APPIUM_PATH));
        return serviceBuilder;
    }
}