package utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class FileUtils {

    public static Map<String, Object> loadYamlConfig(String filePath) {
        YAMLMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
