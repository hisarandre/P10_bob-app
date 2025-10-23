package com.openclassrooms.bobapp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.bobapp.model.Joke;

@Repository
public class JsonReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode jsonFile;

    JsonReader(String resourcePath) {
        try {
            getJsonFile(resourcePath);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to initialize JsonReader: failed to load " + resourcePath, e
            );
        }
    }

    @Autowired
    public JsonReader() {
        this("json/jokes.json");
    }

    public List<Joke> getJokes() {
        JsonNode jokeNode = this.jsonFile.get("jokes");
        Joke[] jokes = mapper.convertValue(jokeNode, Joke[].class);
        return Arrays.asList(jokes);
    }

    private void getJsonFile(String resourcePath) throws IOException {
        if (this.jsonFile == null) {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (is == null) {
                throw new IllegalArgumentException("file not found!");
            } else {
                this.jsonFile = mapper.readTree(is);
            }
        }
    }
}