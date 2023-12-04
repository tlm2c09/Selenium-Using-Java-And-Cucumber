package stepdefinitions;

import configs.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.InventoryPage;
import pages.LoginPage;

public class InventoryPageSteps {
    InventoryPage inventoryPage;

    public InventoryPageSteps() {
        inventoryPage = new InventoryPage();
    }

    @Then("the user is at the inventory page")
    public void the_user_is_at_the_inventory_page(){
        inventoryPage.userIsAtInventoryPage();
    }
}
