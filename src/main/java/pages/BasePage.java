package pages;

import io.cucumber.core.exception.CucumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static configs.DriverManager.*;
import static utils.WaitsUtil.*;

public class BasePage {

    public static final Logger logger = LogManager.getLogger(BasePage.class.getSimpleName());

    public void clickOnElement(By by) {
        WebElement element = waitForElementToBeClickable(by);
        element.click();
    }

    public void enterTexts(By by, String texts) {
        WebElement element = waitForElementToBeVisible(by);
        element.clear();
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

    public void uploadFile(By by, String absolutePath){
        WebElement fileInput = waitForElementToBeVisible(by);
        fileInput.sendKeys(absolutePath);
    }

    public void uploadFileToRemoteServer(By by, String absolutePath){
        ((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
        WebElement fileInput = waitForElementToBeVisible(by);
        fileInput.sendKeys(absolutePath);
    }

    public void selectOptionFromDropdownByValue(By dropdown, String selectType,String option) {
        Select select = new Select(waitForElementToBeVisible(dropdown));
        switch (selectType) {
            case "text" -> select.selectByVisibleText(option);
            case "value" -> select.selectByValue(option);
            case "index" -> select.selectByIndex(Integer.parseInt(option));
            default -> throw new CucumberException("Unknown option. Valid values are: {'text', 'value', 'index'}");
        }
    }

    public void selectCheckbox(String action, By by) {
        WebElement checkbox = waitForElementToBeVisible(by);
        Assert.assertTrue(action.equalsIgnoreCase("checks") || action.equalsIgnoreCase("unchecks"));
        boolean toCheck = action.equalsIgnoreCase("checks");
        boolean isChecked = checkbox.isSelected();
        if (toCheck != isChecked) {
            checkbox.click();
        } else logger.info("The checkbox '{}' is already in the expected state", by);
    }

    public String getCssValueOfElement(By by, String propertyName) {
        return waitForElementToBeVisible(by).getCssValue(propertyName);
    }
}
