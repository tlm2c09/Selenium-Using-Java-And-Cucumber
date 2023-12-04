package pages;

import configs.DriverManager;
import io.cucumber.core.exception.CucumberException;
import locators.LoginPageLocators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Map;

import static utils.WaitsUtil.waitForElementNotToHaveExactTexts;
import static utils.WaitsUtil.waitForElementToHaveExactTexts;


public class LoginPage extends BasePage {

    protected static final Logger logger = LogManager.getLogger(LoginPage.class);
    Map<String, String> textFieldLocators = LoginPageLocators.textFieldLocators();
    Map<String, String> buttonLocators = LoginPageLocators.buttonLocators();
    Map<String, String> elementLocators = LoginPageLocators.elementLocators();

    public void enterTextsInLoginPage(String element, String textsToEnter) {
        String locator = textFieldLocators.get(element);
        enterTexts(By.xpath(locator), textsToEnter);
        logger.info("Entered texts '{}' for element '{}'", textsToEnter, element);
    }

    public void clickOnElement(String elementType, String element){
        String locator = switch (elementType) {
            case "button" -> buttonLocators.get(element);
            case "text field" -> textFieldLocators.get(element);
            default -> throw new CucumberException("Invalid element type.");
        };
        clickOnElement(By.xpath(locator));
    }

    public void verifyElementWithTexts(String action, String element, String expectedTexts) {
        String locator = elementLocators.get(element);
        if (action.equalsIgnoreCase("sees")) {
            waitForElementToHaveExactTexts(By.xpath(locator), expectedTexts);
        } else waitForElementNotToHaveExactTexts(By.xpath(locator), expectedTexts);
        logger.info("The user '{}' the element '{}' to have exact texts '{}'", action, element, expectedTexts);
    }
}