package pages;

import configs.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static utils.WaitsUtil.waitForElementToBeClickable;
import static utils.WaitsUtil.waitForElementToBeVisible;

public class BasePage {

    public static final Logger logger = LogManager.getLogger(BasePage.class);
    public WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
    }

    public void clickOnElement(By by) {
        WebElement element = waitForElementToBeClickable(by);
        element.click();
    }

    public void enterTexts(By by, String texts) {
        WebElement element = waitForElementToBeClickable(by);
        element.sendKeys(texts);
    }

    public String getTextOfElement(By by) {
        return waitForElementToBeVisible(by).getText();
    }

    public void verifyTextsInPage(String action, List<String> data) {
        String allTexts = getTextOfElement(By.xpath("//body"));
        if (action.equalsIgnoreCase("sees")) {
            data.forEach(text -> {
                Assert.assertTrue(allTexts.contains(text));
                logger.info("The page contains expected text '{}'", text);
            });
        } else {
            data.forEach(text -> {
                Assert.assertFalse(allTexts.contains(text));
                logger.info("The page does not contain expected text '{}'", text);
            });
        }
    }
}
