package locators;

import java.util.HashMap;
import java.util.Map;

public class LoginPageLocators {

    public static Map<String, String> textFieldLocators() {
        Map<String, String> locator = new HashMap<>();
        locator.put("username", "//input[@id='user-name']");
        locator.put("password", "//input[@id='password']");
        return locator;
    }

    public static Map<String, String> buttonLocators() {
        Map<String, String> locator = new HashMap<>();
        locator.put("login", "//input[@id='login-button']");
        return locator;
    }
}
