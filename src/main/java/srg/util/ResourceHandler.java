package srg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceHandler {

    public static JsonObject convertYamlToJsonObject(String fileName) throws JsonProcessingException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        File yamlFile = new File("src/main/resources/" + fileName);
        JsonObject jsonObject = null;
        try {
            // Read YAML file into JsonNode
            JsonNode yamlNode = yamlReader.readTree(yamlFile);

            // Convert JsonNode to JSON string
            String jsonString = jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(yamlNode);

            jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Properties getPropertiesFile(String fileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = ResourceHandler.class.getClassLoader().getResourceAsStream(fileName);) {
            // Load the properties file
            properties.load(input);
        } catch (IOException ex) {
            throw ex;
        }
        return properties;
    }
}
