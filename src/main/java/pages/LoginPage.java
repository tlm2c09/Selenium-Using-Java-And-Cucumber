package pages;

import configs.DriverManager;
import io.cucumber.core.exception.CucumberException;
import locators.LoginPageLocators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.Map;

public class LoginPage extends BasePage {

    protected static final Logger logger = LogManager.getLogger(LoginPage.class);
    Map<String, String> textFieldLocators = LoginPageLocators.textFieldLocators();
    Map<String, String> buttonLocators = LoginPageLocators.buttonLocators();

    public void enterTextsInLoginPage(String element, String textsToEnter) {
        String locator = textFieldLocators.get(element);
        enterTexts(By.xpath(locator), textsToEnter);
        logger.info("Entered texts '{}' for element '{}'", textsToEnter, element);
    }

    public void clickOnElement(String elementType, String element){
        String locator;
        switch (elementType) {
            case "button":
                locator = buttonLocators.get(element);
                break;
            case "text field":
                locator = textFieldLocators.get(element);
                break;
            default:
                throw new CucumberException("Invalid element type.");
        }
        clickOnElement(By.xpath(locator));
    }
}
