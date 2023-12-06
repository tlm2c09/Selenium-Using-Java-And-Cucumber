package configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static utils.WaitsUtil.*;

public class DriverManager {

    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DriverManager.class.getSimpleName());
    static String GRID_HUB_URL = "http://localhost:4444/wd/hub";
    public static final ThreadLocal<Map<String, WebDriver>> activeDriversThread = new ThreadLocal<>();
    public static final ThreadLocal<String> scenarioThread = new ThreadLocal<>();
    public static String CURRENT_DRIVER_NAME;

    public static WebDriver initializeBrowser(String browserName) {
        WebDriver driver;
        URL url;
        try {
            url = new URI(GRID_HUB_URL).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
        switch (browserName.toLowerCase()) {
            case "chrome" -> driver = new RemoteWebDriver(url, getChromeOptions());
            case "firefox" -> driver = new RemoteWebDriver(url, getFirefoxOptions());
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        if (activeDriversThread.get() == null) {
            activeDriversThread.set(new HashMap<>());
            activeDriversThread.get().put("initial", driver);
            CURRENT_DRIVER_NAME = "initial";
        }
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //Set capabilities
        options.setCapability("se:name", scenarioThread.get());
        options.setCapability("platformName", System.getProperty("os.name"));
        //Set arguments. Available arguments: https://github.com/GoogleChrome/chrome-launcher/blob/main/docs/chrome-flags-for-tools.md
        options.addArguments("--start-maximized");
        options.addArguments("--headless=new");

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    public static void closeAllBrowsers() {
        activeDriversThread.get().forEach((session, driver) -> {
            logger.info("Closing session '{}' with the driver '{}'", session, driver);
            driver.quit();
        });
        activeDriversThread.remove();
    }

    public static void startNewBrowser(String sessionName) {
        String browser = System.getProperty("browser");
        logger.info("Starting new browser '{}'", sessionName);
        activeDriversThread.get().put(sessionName, initializeBrowser(browser));
        CURRENT_DRIVER_NAME = sessionName;
        logger.info("CURRENT_DRIVER_NAME: {}", CURRENT_DRIVER_NAME);
        logger.info("List of active browsers: " + activeDriversThread.get());
        initializeNewWebDriverWait();
    }

    public static void switchToBrowser(String sessionName) {
        CURRENT_DRIVER_NAME = sessionName;
        waitThread.set(new WebDriverWait(activeDriversThread.get().get(sessionName), Duration.ofSeconds(10)));
    }

}