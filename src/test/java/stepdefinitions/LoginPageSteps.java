package stepdefinitions;

import configs.DriverManager;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginPageSteps {
    LoginPage loginPage;
    private final String LOGIN_PAGE_URL = "https://www.saucedemo.com/";

    public LoginPageSteps() {
        loginPage = new LoginPage();
    }

    @When("the user goes to the login page")
    public void the_user_goes_to_the_login_page(){
        DriverManager.getDriver().get(LOGIN_PAGE_URL);
    }

    @When("the user logins with username {string} and password {string}")
    public void the_user_logins_with_username_and_password(String username, String password) {
        loginPage.enterTextsInLoginPage("username", username);
        loginPage.enterTextsInLoginPage("password", password);
    }

    @When("the user clicks the {string} {string}")
    public void the_user_clicks_the(String elementType, String element) {
        loginPage.clickOnElement(elementType, element);
    }
}
