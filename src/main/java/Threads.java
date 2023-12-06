import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class Threads {

    public static final ThreadLocal<Map<String, WebDriver>> activeDriversThread = new ThreadLocal<>();
    public static final Map<String, WebDriver> activeDrivers = new HashMap<>();

    public static final ThreadLocal<String> scenarioThread = new ThreadLocal<>();
    public static final String scenario = null;
}
