package configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void initializeDriver() {
        String browser = System.getProperty("browser");
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(getChromeOptions());
                break;
            case "firefox":
                driver = new FirefoxDriver(getFirefoxOptions());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        //Set capabilities
//        options.setCapability("timeouts", "600000");
        //Set arguments. Available arguments: https://github.com/GoogleChrome/chrome-launcher/blob/main/docs/chrome-flags-for-tools.md
        options.addArguments("--start-maximized");
//        options.addArguments("--headless=new");

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        return options;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}