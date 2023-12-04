package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static configs.DriverManager.getDriver;
import static configs.DriverManager.quitDriver;

public class Hook {
    private static final Logger logger = LogManager.getLogger(Hook.class);

    @Before
    public void beforeScenario(){
        getDriver();
    }

    @After
    public void afterScenario(){
        quitDriver();
    }

}
