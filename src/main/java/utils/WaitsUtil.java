package utils;

import configs.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WaitsUtil {

    private static WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    private static final Logger logger = LogManager.getLogger(WaitsUtil.class);

//    static {
//        initializeNewWebDriverWait();
//    }

//    public static void initializeNewWebDriverWait() {
//        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
//        logger.info("Wait Manger: " + wait);
//    }

    public WaitsUtil(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebElement waitForElementToBeVisible(By locator) {
        return wait.until(visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        return wait.until(elementToBeClickable(locator));
    }

    public static void waitForElementInvisibility(By locator) {
        wait.until(invisibilityOfElementLocated(locator));
    }


    public static void waitForPageTitleToBe(String title) {
        wait.until(titleIs(title));
    }

    public static void waitForUrlToContain(String partialUrl) {
        wait.until(urlContains(partialUrl));
    }

    public static void waitForElementToHaveExactTexts(By by, String expectedTexts) {
        wait.until(textToBe(by, expectedTexts));
    }

    public static void waitForElementNotToHaveExactTexts(By by, String expectedTexts) {
        wait.until(not(textToBe(by, expectedTexts)));
    }
}
