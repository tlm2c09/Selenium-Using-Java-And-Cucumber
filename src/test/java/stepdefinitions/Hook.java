package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static configs.DriverManager.*;
import static utils.WaitsUtil.initializeNewWebDriverWait;

public class Hook {
    final String BROWSER = System.getProperty("browser");
    @Before
    public void beforeScenario(){
        initializeDriver(BROWSER);
        initializeNewWebDriverWait();
    }

    @After
    public void afterScenario(Scenario scenario){
        if (scenario.isFailed()) {
            scenario.attach(takeScreenshot(), "image/png", scenario.getName());
        }
        closeAllDrivers();
    }

    public static byte[] takeScreenshot() {
        return  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
