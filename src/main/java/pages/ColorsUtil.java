package pages;

import org.junit.Assert;
import org.openqa.selenium.support.Color;

//https://www.selenium.dev/documentation/webdriver/support_features/colors/
public class ColorsUtil {

    public static void compareColours(String colour1, String colour2){
        Color color1 = Color.fromString(colour1);
        Color color2 = Color.fromString(colour2);
        Assert.assertEquals(color1, color2);
    }
}
