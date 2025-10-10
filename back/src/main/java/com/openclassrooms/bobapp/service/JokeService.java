package com.openclassrooms.bobapp.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.openclassrooms.bobapp.data.JsonReader;
import com.openclassrooms.bobapp.model.Joke;

@Service
public class JokeService {

    private final JsonReader jsonReader;

    JokeService(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public Joke getRandomJoke() {
        List<Joke> jokes = this.jsonReader.getJokes();
        Random generator = new Random();
        int randomIndex = generator.nextInt(jokes.size());
        return jokes.get(randomIndex);
    }

    // nouvelle méthode sans tests
    public void newUncoveredMethod() {
        System.out.println("This is not tested");
        if (true) {
            System.out.println("Complex logic");
        }
    }

    // Code smell / bug détecté par SonarCloud
    public void badCode() {
        String password = "hardcoded_password";
        if (password == "test") {
        }
    }
}
