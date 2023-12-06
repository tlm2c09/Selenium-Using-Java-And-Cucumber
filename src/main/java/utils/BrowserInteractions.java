package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Set;

import static configs.DriverManager.driver;
import static utils.WaitsUtil.waitForAlertToBePresent;

public class BrowserInteractions {
    private static final Logger logger = LogManager.getLogger(BrowserInteractions.class);
    private static final Actions actions = new Actions(driver);

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public void navigateForward() {
        driver.navigate().forward();
    }

    public void handleAlert(String action) {
        Assert.assertTrue(action.equalsIgnoreCase("accept") || action.equalsIgnoreCase("dismiss"));
        Alert alert = waitForAlertToBePresent();
        logger.info("Alert with texts '{}' is displayed", alert.getText());
        if (action.equalsIgnoreCase("accept")) {
            alert.accept();
        } else alert.dismiss();
    }

    public void switchToIframe(WebElement element) {
        driver.switchTo().frame(element);
    }

    public void switchToIframe(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToIframe(String frameNameOrId) {
        driver.switchTo().frame(frameNameOrId);
    }

    public void switchToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    public void switchToWindowHandle(int index) {
        ArrayList<String> allWindowHandles = (ArrayList<String>) getAllWindowHandles();
        driver.switchTo().window(allWindowHandles.get(index));
        logger.info("Switch to window handle '{}'", allWindowHandles.get(index));
    }

    public Set<String> getAllWindowHandles() {
        logger.info("All active window handles are: {}", getAllWindowHandles());
        return driver.getWindowHandles();
    }

    public void openANewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
    }

    public void sendKey(String key) {
        actions.sendKeys(key).perform();
    }

    public void sendKey(WebElement element, String key) {
        actions.sendKeys(element, key).perform();
    }

    public void hoverOverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public void dragAndDrop(WebElement dragTarget, WebElement dropTarget) {
        actions.dragAndDrop(dragTarget, dropTarget).perform();
    }

    public void scrollToElement(WebElement element) {
        actions.scrollToElement(element).perform();
    }
}
