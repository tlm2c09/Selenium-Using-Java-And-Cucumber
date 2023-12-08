package stepdefinitions;

import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static configs.AppiumServer.*;
import static configs.DriverManager.*;
import static utils.WaitsUtil.initializeNewWebDriverWait;

public class Hook {
    static final String BROWSER_NAME = System.getProperty("browser");

    @BeforeAll
    public static void beforeAll(){
        if (BROWSER_NAME.equalsIgnoreCase("android") || BROWSER_NAME.equalsIgnoreCase("ios")){
            startAppiumServer();
        }
    }

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
        return  ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @AfterAll
    public static void afterAll(){
        if (BROWSER_NAME.equalsIgnoreCase("android") || BROWSER_NAME.equalsIgnoreCase("ios")){
            stopAppiumServer();
        }
    }

}
