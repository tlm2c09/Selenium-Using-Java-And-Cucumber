package stepdefinitions;

import configs.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

import static configs.DriverManager.CURRENT_DRIVER_NAME;

public class LoginPageSteps {
    LoginPage loginPage;

    public LoginPageSteps() {
        loginPage = new LoginPage();
    }

    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {
        loginPage.enterTextsInLoginPage("username", username);
        loginPage.enterTextsInLoginPage("password", password);
    }

    @When("the user clicks the {string} {string}")
    public void the_user_clicks_the_string_string(String elementType, String element) {
        loginPage.clickOnElement(elementType, element);
    }

    @When("the user logins with username {string} and password {string}")
    public void the_user_logins_with_username_and_password(String username, String password) {
        loginPage.enterTextsInLoginPage("username", username);
        loginPage.enterTextsInLoginPage("password", password);
        loginPage.clickOnElement("button", "login");
    }

    @Then("the user {string} the element {string} has texts {string}")
    public void theUserTheElementHasTexts(String action, String element, String expectedTexts) {
        loginPage.verifyElementWithTexts(action, element, expectedTexts);
    }

    @Then("the user {string} the following texts")
    public void theUserStringTheFollowingTexts(String action, DataTable data) {
        loginPage.verifyTextsInLoginPage(action, data.asList());
    }
}
