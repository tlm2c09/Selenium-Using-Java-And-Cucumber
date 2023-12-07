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
- Reference: [Expected Conditions](https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html)
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
- Avoid using Static fields: Cucumber typically creates a new instance of the step definition class (glue code classes) before each scenario. Using static fields may lead to unexpected behaviour.
## Test Data ##
- Load the Test Data JSON file based on the environment property 'env', and convert it into a Map<String, String> object
- To read test data in the feature file, the passed test data will need to start with '@TD:' following with key's name.
- Generate random test data using Faker library
## Locator ##
- If HTML IDs are available, unique, and consistently predictable, they are the preferred method for locating an element on a page.
- If unique IDs are unavailable, a well-written CSS selector is the preferred method of locating an element. XPath works as well as CSS selectors, but the syntax is complicated and frequently difficult to debug.
- Reference: [Locators Test Practices](https://www.selenium.dev/documentation/test_practices/encouraged/locators/)
## Running Tests in Parallel
1. Set up the Grid on different machines
- All machines will need to download the Selenium Server jar file from the [latest release](https://github.com/SeleniumHQ/selenium/releases/latest)
- Hub: is composed by the following [components](https://www.selenium.dev/documentation/grid/components/): Router, Distributor, Session Map, New Session Queue, and Event Bus.
  - `java -jar selenium-server-<version>.jar hub`
  - By default, the server will listen for RemoteWebDriver requests on http://localhost:4444
- Node: detect the available drivers that it can use from the System PATH else can use the parameter `--selenium-manger true` to use Selenium Manager library instead.
  - Also detect the number of processors available (corresponding to the number of concurrent sessions)
    - `java -jar selenium-server-<version>.jar node --hub http://<hub-ip>:4444 --selenium-manager true`
- Hub and Nodes talk to each other via HTTP and the [Event Bus](https://www.selenium.dev/documentation/grid/components/#event-bus) (the Event Bus lives inside the Hub).
- Modes:
  - Standalone:
- Reference: [Selenium Grid](https://www.selenium.dev/documentation/grid/)
2. Configure Cucumber to run tests in parallel with TestNG
- In `TestRunner.java`, the class needs to extend the class `AbstractTestNGCucumberTests`, and overrides the method `scenarios` with `@DataProvider(parallel=true)`.
- Add the Maven Surefire plugin configuration to the build section of the POM.
  - The default thread count of the `dataprovider` in parallel mode is 10.
- In TestNG, the scenarios and rows in a scenario outline are executed in multiple threads.
- To run tests in parallel by features, switch to use JUnit instead of TestNG in `TestRunner.java` class.
Reference: [Parallel Execution with TestNG](https://cucumber.io/docs/guides/parallel-execution/?lang=java#testng)

## Appium ##
1. Install [Node.js](https://nodejs.org/)
2. Install Appium 2.x: `npm install -g appium`. To start the Appium server: `appium`
    - Install the driver itself
      - Android: appium driver install uiautomator2
      - iOS: 
3. Setup Android SDK and virtual device
4. Add ANDROID_HOME to Environment to [Environment Variables](https://developer.android.com/tools/variables).
5. Add [Appium Java client](https://mvnrepository.com/artifact/io.appium/java-client/9.0.0) to POM file: this provides the `AppiumDriver` class
6. Get started with Appium Inspector with the basic capabilities
   - platformName: Android or iOS
   - platformVersion
   - automationName: uiautomator2 (Android)
   - udid: 
   - browserName: 
7. To start / stop the Appium server, use the class `AppiumServer.java`.
   - `AppiumServiceBuilder` to build the configurations for the Appium server like port, NodeJS location, Appium location etc
   - `AppiumDriverLocalService` to start / stop the server