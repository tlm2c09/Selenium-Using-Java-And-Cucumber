package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.ArrayList;

import static configs.DriverManager.getDriver;
import static utils.WaitsUtil.waitForAlertToBePresent;

public class BrowserInteractions {
    private static final Logger logger = LogManager.getLogger(BrowserInteractions.class.getSimpleName());
    private static final Actions actions = new Actions(getDriver());

    public String getTitle() {
        return getDriver().getTitle();
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public void navigateToUrl(String url) {
        getDriver().get(url);
    }

    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    public void navigateBack() {
        getDriver().navigate().back();
    }

    public void navigateForward() {
        getDriver().navigate().forward();
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
        getDriver().switchTo().frame(element);
    }

    public void switchToIframe(int index) {
        getDriver().switchTo().frame(index);
    }

    public void switchToIframe(String frameNameOrId) {
        getDriver().switchTo().frame(frameNameOrId);
    }

    public void switchToDefaultFrame() {
        getDriver().switchTo().defaultContent();
    }

    public void switchToWindowHandle(int index) {
        ArrayList<String> allWindowHandles = (ArrayList<String>) getDriver().getWindowHandles();
        getDriver().switchTo().window(allWindowHandles.get(index));
        logger.info("Switch to window handle '{}'", allWindowHandles.get(index));
    }

    public void openANewTab() {
        getDriver().switchTo().newWindow(WindowType.TAB);
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
