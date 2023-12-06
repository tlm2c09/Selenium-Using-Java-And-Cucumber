package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static configs.DriverManager.*;
import static utils.WaitsUtil.initializeNewWebDriverWait;

public class Hook {
    final String BROWSER_NAME = System.getProperty("browser");
    @Before
    public void beforeScenario(Scenario scenario){
        scenarioThread.set(scenario.getName());
        initializeBrowser(BROWSER_NAME);
        initializeNewWebDriverWait();
    }

    @After
    public void afterScenario(Scenario scenario){
        if (scenario.isFailed()) {
            scenario.attach(takeScreenshot(), "image/png", scenario.getName());
        }
        closeAllBrowsers();
    }

    public static byte[] takeScreenshot() {
        return  ((TakesScreenshot) activeDriversThread.get().get(CURRENT_DRIVER_NAME)).getScreenshotAs(OutputType.BYTES);
    }

}
