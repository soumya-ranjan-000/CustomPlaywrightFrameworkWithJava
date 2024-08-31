package srg.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import srg.playwright.custom.LocateElementFromPage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class ResourceHandler {
    private static final Logger logger = LoggerFactory.getLogger(LocateElementFromPage.class);

    public static JsonObject convertYamlToJsonObject(String fileName) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        ObjectMapper jsonWriter = new ObjectMapper();
        File yamlFile = new File("src/main/resources/" + fileName);
        JsonObject jsonObject;
        try {
            // Read YAML file into JsonNode
            JsonNode yamlNode = yamlReader.readTree(yamlFile);
            // Convert JsonNode to JSON string
            String jsonString = jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(yamlNode);
            jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        } catch (JsonProcessingException f) {
            logger.error("Not able to convert from YML to JSON. File Name: {}", fileName, f);
            throw f;
        } catch (IOException e) {
            logger.error("YML file not found. File Name: {}", fileName, e);
            throw e;
        }
        return jsonObject;
    }

    public static Properties getPropertiesFile(String fileName) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = ResourceHandler.class.getClassLoader().getResourceAsStream(fileName)) {
            // Load the properties file
            properties.load(input);
        } catch (IOException ex) {
            logger.error("Not able to read properties file. Name: {}", fileName, ex);
            throw ex;
        }
        return properties;
    }

    public static String getYmlFilePath(String fileName) {
        return Objects.requireNonNull(ResourceHandler.class.getClassLoader().getResource(fileName)).getPath();
    }

    public static Map<String, Object> convertYamlFileToMap(File yamlFile, Map<String, Object> map) {
        try {
            InputStream inputStream = Files.newInputStream(yamlFile.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            map.putAll(config);
        } catch (Exception e) {
            logger.error("Not able to convert yml file to Map. Name: {}", yamlFile, e);
            throw new RuntimeException(String.format("Malformed " + yamlFile.getName() + " file - %s.", e));
        }
        return map;
    }
}
