package configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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

import static utils.WaitsUtil.initializeNewWebDriverWait;
import static utils.WaitsUtil.wait;

public class DriverManager {

    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    public static Map<String, WebDriver> activeDrivers = new HashMap<>();
    static String GRID_HUB_URL = "http://localhost:4444/wd/hub";

    public static WebDriver initializeDriver(String browser) {
        URL url;
        try {
            url = new URI(GRID_HUB_URL).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
        switch (browser.toLowerCase()) {
            case "chrome" -> driver = new RemoteWebDriver(url, getChromeOptions());
            case "firefox" -> driver = new RemoteWebDriver(url, getFirefoxOptions());
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        if (activeDrivers.size() == 0) {
            activeDrivers.put("initial", driver);
        }
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //Set capabilities

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

    public static void closeAllDrivers() {
        activeDrivers.forEach((session, driver) -> {
            logger.info("Closing session '{}' with the driver '{}'", session, driver);
            driver.quit();
        });
        activeDrivers = new HashMap<>();
    }

    public static void startNewBrowser(String sessionName) {
        String browser = System.getProperty("browser");
        DriverManager.driver = initializeDriver(browser);
        initializeNewWebDriverWait();
        activeDrivers.put(sessionName, DriverManager.driver);
    }

    public static void switchToBrowser(String sessionName) {
        DriverManager.driver = activeDrivers.get(sessionName);
        wait = new WebDriverWait(DriverManager.driver, Duration.ofSeconds(10));
    }

}