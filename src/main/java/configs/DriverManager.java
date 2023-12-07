package configs;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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

    private static final Logger logger = LogManager.getLogger(DriverManager.class.getSimpleName());
    static String GRID_HUB_URL = "http://localhost:4444/wd/hub";
    static String APPIUM_SERVER_URL = "http://127.0.0.1:4723/";
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
            case "chrome" -> driver = new RemoteWebDriver(url, setOptions(getChromeOptions()));
            case "firefox" -> driver = new RemoteWebDriver(url, setOptions(getFirefoxOptions()));
            case "edge" -> driver = new RemoteWebDriver(url, setOptions(getEdgeOptions()));
            case "android" -> driver = initializeAndroidDriver();
            case "ios" -> driver = initializeIOSDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        if (activeDriversThread.get() == null) {
            activeDriversThread.set(new HashMap<>());
            activeDriversThread.get().put("initial", driver);
            CURRENT_DRIVER_NAME = "initial";
        }
        return driver;
    }

    private static MutableCapabilities setOptions(MutableCapabilities options){
        //common capabilities for all browsers: https://w3c.github.io/webdriver/#capabilities
        options.setCapability("se:screenResolution", "1920x1080");
        options.setCapability("se:name", scenarioThread.get());
        options.setCapability(CapabilityType.PLATFORM_NAME, System.getProperty("os.name"));
        logger.info("The browser's capabilities: " + options);
        return options;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //Set specified capabilities for Chrome

        //Set arguments
        // Available arguments: https://github.com/GoogleChrome/chrome-launcher/blob/main/docs/chrome-flags-for-tools.md
        options.addArguments("--start-maximized");
        options.addArguments("--headless=new");

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        //Set specified capabilities for Firefox

        //Set arguments
        options.addArguments("-headless");
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        //Set specified capabilities for Edge

        //Set arguments
        options.addArguments("--start-maximized");
        options.addArguments("--headless=new");

        return options;
    }

    public static AndroidDriver initializeAndroidDriver(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "13.0");
        caps.setCapability("automationName", "uiautomator2");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("chromedriver_autodownload", true);
//        caps.setCapability("autoWebview", true);
        AndroidDriver driver;
        try {
            URL url = new URI(APPIUM_SERVER_URL).toURL();
            driver = new AndroidDriver(url, caps);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return driver;
    }

    public static IOSDriver initializeIOSDriver(){
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "iOS");
        caps.setCapability("platformVersion", "TBD");
        caps.setCapability("automationName", "XCUITest");
        caps.setCapability("udid", "TBD");
        caps.setCapability("autoWebview", true);
        IOSDriver driver;
        try {
            URL url = new URI(APPIUM_SERVER_URL).toURL();
            driver = new IOSDriver(url, caps);
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return driver;
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