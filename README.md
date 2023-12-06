# Selenium Using Java And Cucumber
This project is a Java-based automation framework using Selenium for web testing, Cucumber for behavior-driven testing, and Rest Assured for API testing
1. `/src/main`: This directory is meant for the main source code and resources of the framework. It contains the core functionalities of the framework.
2. `/src/test`: This directory is dedicated to storing the source code and resources for testing purposes. It contains test code that is used to verify and validate the functionality of the code.
## Test Runner ##
This is the class inside `src/test/java/runners` for:
- The `@CucumberOptions` annotation is used to specify the location of feature files, step definitions, and the location to store the reports
- Tags to run / ignore
- The Runner class is using cucumber-junit to run the tests

## Hook class file ##
The Hook class file contains the `@Before / @After` (Scenario) block of code to initialize the Web Driver before each scenario / feature and close it after each scenario / feature.
- Hook is placed in the same package / directory with the step definition files
- Cucumber will automatically detect and execute the hooks defined in the hook file based on their annotations. Ex: `@Before / @After`
- Need to initialize the Web Driver Wait for each scenario as methods / variables are all static, else it will fail (using the previous one).
- Take screenshot if the scenario fails and attach the screenshot to the Cucumber report 
## Driver Manager class file ##
to manage the WebDriver instances. It typically provides methods to initialize and close the WebDriver, retrieve the current WebDriver instance, and perform other related tasks.

## Wait Utils ##
- Reference: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html

## POM file ##
- Required dependencies:
  - `selenium-java`
  - `cucumber-java`
  - `cucumber-junit`
  - `rest-assured`
  - `testng`
  - `log4j-core`
  - `jackson-databind`
- The build tool used to run the Cucumber features is `maven-surefire-plugin`

## Logging framework Log4j configuration ##
- The `log4j2.xml` file in the `src/main/resources` directory contains the logging configurations.
- Using the `Logger` class from `org.apache.logging.log4j.Logger;` to print logs.
  - `Appenders` Element: Defines where the log messages will be output: `Console`.
  - `Loggers` Element: Configures Loggers, which define the logging behavior for specific classes or packages.
    - `Root` Logger: The default logger that applies to all classes unless overridden: `level = info`

## Step Definition class files ##
- Avoid using Static fields: Cucumber typically creates a new instance of the step definition class for each scenario. Using static fields may lead to unexpected behaviour.

## Test Data ##
- Load the Test Data JSON file based on the environment property 'env', and convert it into a Map<String, String> object
- To read test data in the feature file, the passed test data will need to start with '@TD:' following with key's name.
- Generate random test data using Faker library

## Locator ##
- If HTML IDs are available, unique, and consistently predictable, they are the preferred method for locating an element on a page.
- If unique IDs are unavailable, a well-written CSS selector is the preferred method of locating an element. XPath works as well as CSS selectors, but the syntax is complicated and frequently difficult to debug.
- Reference: https://www.selenium.dev/documentation/test_practices/encouraged/locators/