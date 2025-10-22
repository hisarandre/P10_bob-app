package com.openclassrooms.bobapp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.bobapp.model.Joke;

@Repository
public class JsonReader {

    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);
    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode jsonFile;

    private JsonReader() {
        try {
            this.getJsonFile();
        } catch (IOException e) {
            logger.error("Failed to load jokes from JSON file", e);
            throw new IllegalStateException("Unable to initialize JsonReader: jokes.json could not be loaded", e);
        }
    }

    private static class SingletonHolder {
        private final static JsonReader instance = new JsonReader();
    }

    public static JsonReader getInstance() {
        return SingletonHolder.instance;
    }

    public List<Joke> getJokes() {
        JsonNode jokeNode = this.jsonFile.get("jokes");
        Joke[] jokes = mapper.convertValue(jokeNode, Joke[].class);
        return Arrays.asList(jokes);
    }

    private void getJsonFile() throws IOException {
        if (this.jsonFile == null) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("json/jokes.json");
            if (is == null) {
                throw new IllegalArgumentException("file not found!");
            } else {
                this.jsonFile = mapper.readTree(is);
            }
        }
    }
}
