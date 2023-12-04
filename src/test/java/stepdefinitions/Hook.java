package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static configs.DriverManager.getDriver;
import static configs.DriverManager.quitDriver;
import static utils.WaitsUtil.initializeNewWebDriverWait;

public class Hook {
    private static final Logger logger = LogManager.getLogger(Hook.class);
    @Before
    public void beforeScenario(Scenario scenario){
        getDriver();
        initializeNewWebDriverWait();
        logger.info("Starting scenario '{}' with the following tags '{}'", scenario.getName(), scenario.getSourceTagNames());
    }

    @After
    public void afterScenario(Scenario scenario){
        quitDriver();
        logger.info("Finishing scenario '{}' with the following tags '{}'", scenario.getName(), scenario.getSourceTagNames());
    }

}
