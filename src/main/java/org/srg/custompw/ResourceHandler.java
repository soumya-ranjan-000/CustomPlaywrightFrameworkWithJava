package org.srg.custompw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;

public class ResourceHandler {

    static JsonObject convertYamlToJsonObject(String fileName) throws JsonProcessingException {
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
}
