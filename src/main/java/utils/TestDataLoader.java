package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.cucumber.core.exception.CucumberException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestDataLoader {

    private static final String TEST_ENVIRONMENT = System.getProperty("env");
    private static final String TEST_DATA_FILE_PATH = String.format("src/main/resources/testdata/%s_ENV_DATA.json", TEST_ENVIRONMENT);
    private static final Logger logger = LogManager.getLogger(TestDataLoader.class);
    static Faker faker = new Faker();
    static Map<String, String> testData;

    static {
        readTestData();
    }
    public static void readTestData(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            testData = objectMapper.readValue(new File(TEST_DATA_FILE_PATH), Map.class);
            logger.info("Test data loaded from environment {}:", TEST_ENVIRONMENT);
            testData.forEach((key, value) -> logger.info("{} = {}", key, value));
        } catch (IOException e) {
            throw new CucumberException(e.getMessage());
        }
    }

    public static String getTestData(String key){
        String value;
        if (key.startsWith("@TD:")) {
            key = key.replace("@TD:", "");
            value = testData.get(key);
        } else if (key.startsWith("@Date:")) {
            key = key.replace("@Date:", "");
            String[] dateAndFormat = key.split("_");
            String date = dateAndFormat[0];
            String format = dateAndFormat[1];
            logger.info("Get date '{}' with format '{}'", date, format);
            value = DateUtil.formatDate(date, format);
        } else value = key;
        return value;
    }

    public static void saveTestData(String key, String value){
        testData.put(key, value);
    }

    public static void generateRandomValueForKey(String key){
        String value;
        switch (key){
            case "RandomFirstName" -> value = faker.name().firstName();
            case "RandomLastName" -> value = faker.name().lastName();
            case "RandomPhoneNumber" -> value = faker.phoneNumber().phoneNumber();
            case "RandomEmail" -> value = faker.internet().emailAddress();
            default -> throw new CucumberException("Unknown key: " + key);
        }
        logger.info("Generated value '{}' for key '{}'", value, key);
        saveTestData(key, value);
    }
}
