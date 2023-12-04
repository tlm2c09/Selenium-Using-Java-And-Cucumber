package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static utils.WaitsUtil.waitForElementToBeClickable;
import static utils.WaitsUtil.waitForElementToBeVisible;

public class BasePage {

    public static final Logger logger = LogManager.getLogger(BasePage.class);

    public void clickOnElement(By by) {
        WebElement element = waitForElementToBeClickable(by);
        element.click();
    }

    public void enterTexts(By by, String texts) {
        WebElement element = waitForElementToBeClickable(by);
        element.sendKeys(texts);
    }

    public String getTextOfElement(By by) {
        String actualTexts = waitForElementToBeVisible(by).getText();
        return actualTexts;
    }
}
