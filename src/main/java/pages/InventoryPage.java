package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static utils.WaitsUtil.waitForElementToBeVisible;


public class InventoryPage extends BasePage {

    protected static final Logger logger = LogManager.getLogger(InventoryPage.class);

    public void userIsAtInventoryPage(){
        waitForElementToBeVisible(By.xpath("//div[@id='inventory_container']"));
        logger.info("The user is at the Inventory page");
    }


}
