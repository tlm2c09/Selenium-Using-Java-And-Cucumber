package utils;

import io.cucumber.core.exception.CucumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static configs.DriverManager.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class WaitsUtil {

    private static final Logger logger = LogManager.getLogger(WaitsUtil.class.getSimpleName());
    public static ThreadLocal<WebDriverWait> waitThread = new ThreadLocal<>();

    public WaitsUtil() {
        waitThread.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
    }

    public static void initializeNewWebDriverWait() {
        waitThread.set(new WebDriverWait(getDriver(), Duration.ofSeconds(10)));
    }

    public static WebElement waitForElementToBeVisible(By locator) {
        return waitThread.get().until(visibilityOfElementLocated(locator));
    }

    public static WebElement waitForElementToBeClickable(By locator) {
        return waitThread.get().until(elementToBeClickable(locator));
    }

    public static void waitForElementInvisibility(By locator) {
        waitThread.get().until(invisibilityOfElementLocated(locator));
    }

    public static void waitForPageTitleToBe(String title) {
        waitThread.get().until(titleIs(title));
    }

    public static void waitForUrlToContain(String partialUrl) {
        waitThread.get().until(urlContains(partialUrl));
    }

    public static void waitForElementToHaveExactTexts(By by, String expectedTexts) {
        waitThread.get().until(textToBe(by, expectedTexts));
    }

    public static void waitForElementNotToHaveExactTexts(By by, String expectedTexts) {
        waitThread.get().until(not(textToBe(by, expectedTexts)));
    }

    public static void waitForAttributeOfElementToContain(By by, String attribute, String value) {
        waitThread.get().until(attributeContains(by, attribute, value));
    }

    public static void waitForAttributeOfElementToNotContain(By by, String attribute, String value) {
        waitThread.get().until(not(attributeContains(by, attribute, value)));
    }

    public static Alert waitForAlertToBePresent() {
        return waitThread.get().until(alertIsPresent());
    }

    public static void waitForElementToStale(WebElement element) {
        waitThread.get().until(refreshed(stalenessOf(element)));
    }

    public static void waitFor(String unit, int number) {
        try {
            switch (unit) {
                case "seconds" -> Thread.sleep(Duration.ofSeconds(number));
                case "minutes" -> Thread.sleep(Duration.ofMinutes(number));
                default -> throw new CucumberException("Invalid unit " + unit);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
