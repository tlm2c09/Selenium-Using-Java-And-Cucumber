package locators;

import java.util.HashMap;
import java.util.Map;

public class InventoryPageLocators {

    public static Map<String, String> textFieldLocators() {
        Map<String, String> locator = new HashMap<>();

        return locator;
    }

    public static Map<String, String> buttonLocators() {
        Map<String, String> locator = new HashMap<>();
        locator.put("menu", "//button[@id='react-burger-menu-btn']");
        locator.put("shopping cart", "//div[@class='shopping_cart_container']");
        return locator;
    }

    public static Map<String, String> elementLocators() {
        Map<String, String> locator = new HashMap<>();

        return locator;
    }
}
