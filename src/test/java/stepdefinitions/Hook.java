package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static configs.DriverManager.*;
import static utils.WaitsUtil.initializeNewWebDriverWait;

public class Hook {
    private static final Logger logger = LogManager.getLogger(Hook.class);
    final String BROWSER = System.getProperty("browser");
    @Before
    public void beforeScenario(){
        initializeDriver(BROWSER);
        initializeNewWebDriverWait();
    }

    @After
    public void afterScenario(){
        closeAllDrivers();
    }

}
