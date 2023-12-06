package pages;

import io.cucumber.core.exception.CucumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.openqa.selenium.By;

import java.util.List;

import static locators.InventoryPageLocators.*;
import static utils.WaitsUtil.waitForElementInvisibility;
import static utils.WaitsUtil.waitForElementToBeVisible;


public class InventoryPage extends BasePage {

    protected static final Logger logger = LogManager.getLogger(InventoryPage.class);

    public void userIsAtInventoryPage() {
        waitForElementToBeVisible(By.xpath("//div[@id='inventory_container']"));
        logger.info("The user is at the Inventory page");
    }

    public void verifyListOfElements(String action, String elementType, List<String> elementsList) {
        Assert.assertTrue(action.equalsIgnoreCase("sees") || action.equalsIgnoreCase("does not see"));
        elementsList.forEach(element -> {
            String locator;
            switch (elementType) {
                case "elements" -> locator = elementLocators().get(element);
                case "buttons" -> locator = buttonLocators().get(element);
                case "text fields" -> locator = textFieldLocators().get(element);
                default -> throw new CucumberException("Unknown element type: " + element);
            }
            if (action.equalsIgnoreCase("sees")) {
                waitForElementToBeVisible(By.xpath(locator));
            } else waitForElementInvisibility(By.xpath(locator));
        });
        logger.info("The user {} the following {}: {}", action, elementType, elementsList);
    }
}
