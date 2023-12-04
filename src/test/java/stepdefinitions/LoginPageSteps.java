package stepdefinitions;

import configs.DriverManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.LoginPage;

public class LoginPageSteps {
    LoginPage loginPage = new LoginPage();
    private final String LOGIN_PAGE_URL = "https://www.saucedemo.com/";
    protected static final Logger logger = LogManager.getLogger(LoginPageSteps.class);

    public LoginPageSteps() {
        loginPage = new LoginPage();
    }

    @When("the user goes to the login page")
    public void the_user_goes_to_the_login_page(){
        DriverManager.getDriver().get(LOGIN_PAGE_URL);
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

    @Then("the user {string} the element {string} has texts {string}")
    public void theUserTheElementHasTexts(String action, String element, String expectedTexts) {
        loginPage.verifyElementWithTexts(action, element, expectedTexts);
    }
}
