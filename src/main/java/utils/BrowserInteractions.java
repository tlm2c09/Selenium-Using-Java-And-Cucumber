package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Set;

import static configs.DriverManager.*;
import static utils.WaitsUtil.waitForAlertToBePresent;

public class BrowserInteractions {
    private static final Logger logger = LogManager.getLogger(BrowserInteractions.class.getSimpleName());
    private static final Actions actions = new Actions(activeDriversThread.get().get(CURRENT_DRIVER_NAME));

    public String getTitle() {
        return activeDriversThread.get().get(CURRENT_DRIVER_NAME).getTitle();
    }

    public String getCurrentUrl() {
        return activeDriversThread.get().get(CURRENT_DRIVER_NAME).getCurrentUrl();
    }

    public void navigateToUrl(String url) {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).get(url);
    }

    public void refreshPage() {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).navigate().refresh();
    }

    public void navigateBack() {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).navigate().back();
    }

    public void navigateForward() {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).navigate().forward();
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
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().frame(element);
    }

    public void switchToIframe(int index) {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().frame(index);
    }

    public void switchToIframe(String frameNameOrId) {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().frame(frameNameOrId);
    }

    public void switchToDefaultFrame() {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().defaultContent();
    }

    public void switchToWindowHandle(int index) {
        ArrayList<String> allWindowHandles = (ArrayList<String>) getAllWindowHandles();
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().window(allWindowHandles.get(index));
        logger.info("Switch to window handle '{}'", allWindowHandles.get(index));
    }

    public Set<String> getAllWindowHandles() {
        logger.info("All active window handles are: {}", getAllWindowHandles());
        return activeDriversThread.get().get(CURRENT_DRIVER_NAME).getWindowHandles();
    }

    public void openANewTab() {
        activeDriversThread.get().get(CURRENT_DRIVER_NAME).switchTo().newWindow(WindowType.TAB);
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
