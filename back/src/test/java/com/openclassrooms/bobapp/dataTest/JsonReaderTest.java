package com.openclassrooms.bobapp.dataTest;

import com.openclassrooms.bobapp.data.JsonReader;
import com.openclassrooms.bobapp.model.Joke;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testGetJokesReturnsNonEmptyList() {
        JsonReader reader = JsonReader.getInstance();
        List<Joke> jokes = reader.getJokes();

        assertNotNull(jokes, "Jokes list should not be null");
        assertFalse(jokes.isEmpty(), "There should be at least one joke");

        Joke firstJoke = jokes.get(0);
        assertNotNull(firstJoke.getJoke(), "Joke question should not be null");
        assertNotNull(firstJoke.getResponse(), "Joke answer should not be null");
    }
}
