package stepdefinitions;

import configs.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.BasePage;
import utils.TestDataLoader;

public class BasePageSteps {
    BasePage basePage;

    public BasePageSteps() {
        basePage = new BasePage();
    }

    @And("the user starts a new browser {string}")
    public void the_user_starts_a_new_browser_string(String browserName){
        DriverManager.startNewBrowser(browserName);
    }

    @And("the user switches to the browser {string}")
    public void the_user_switches_to_the_browser_string(String browserName){
        DriverManager.switchToBrowser(browserName);
    }

    @Then("the user saves value {string} as test data {string}")
    public void theUserSavesValueAsTestData(String value, String key) {
        TestDataLoader.saveTestData(key, value);
    }

    @Then("the user generates random value for {string}")
    public void the_user_generates_value_string_as_test_data_string(String key) {
        TestDataLoader.generateRandomValueForKey(key);
    }
}
