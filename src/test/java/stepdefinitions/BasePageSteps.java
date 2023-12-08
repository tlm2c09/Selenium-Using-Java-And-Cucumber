package stepdefinitions;

import configs.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.BasePage;
import utils.TestDataLoader;

import static utils.WaitsUtil.waitFor;

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

    @Then("the user waits for {int} {string} in the current page")
    public void the_user_waits_for_int_string_in_the_current_page(int numberOf, String unit) {
        waitFor(unit, numberOf);
    }

    @When("the user goes to the url {string}")
    public void the_user_goes_to_the_url_string(String url){
        DriverManager.getDriver().get(TestDataLoader.getTestData(url));
    }
}
