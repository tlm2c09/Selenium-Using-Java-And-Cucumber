# SeleniumUsingJavaAndCucumber
This project is about automating web application using Selenium with Java and Cucumber

## Test Runner ##
This is the class (src/test/java/runners) to configure the following via @CucumberOptions:
- Features file folder
- Step definitions classes location
- Which reports to generate after the tests
- Tags to run / ignore
The Runner class is using cucumber-junit to run the tests

## Hook class file ##
The Hook class file contains the @Before / @After (Scenario) block of code to initialize the Web Driver before each scenario and close it after each scenario.
