package com.openclassrooms.bobapp.dataTest;

import com.openclassrooms.bobapp.data.JsonReader;
import com.openclassrooms.bobapp.model.Joke;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void shouldReturnNonEmptyList_WhenGetJokesIsCalled() {
        // Arrange
        JsonReader reader = JsonReader.getInstance();

        // Act
        List<Joke> jokes = reader.getJokes();

        // Assert
        assertNotNull(jokes);
        assertFalse(jokes.isEmpty());

        Joke firstJoke = jokes.get(0);
        assertNotNull(firstJoke.getJoke());
        assertNotNull(firstJoke.getResponse());
    }

    @Test
    void shouldReturnNewListInstance_WhenGetJokesIsCalledMultipleTimes() {
        // Arrange
        JsonReader reader = JsonReader.getInstance();

        // Act
        List<Joke> jokes1 = reader.getJokes();
        List<Joke> jokes2 = reader.getJokes();

        // Assert
        assertNotSame(jokes1, jokes2);
        assertEquals(jokes1.size(), jokes2.size());
    }

    @Test
    void shouldHaveRequiredFields_WhenAllJokesAreRetrieved() {
        // Arrange
        JsonReader reader = JsonReader.getInstance();

        // Act
        List<Joke> jokes = reader.getJokes();

        // Assert
        assertFalse(jokes.isEmpty());

        for (Joke joke : jokes) {
            assertNotNull(joke);
            assertNotNull(joke.getJoke());
            assertNotNull(joke.getResponse());
            assertFalse(joke.getJoke().trim().isEmpty());
            assertFalse(joke.getResponse().trim().isEmpty());
        }
    }

    @Test
    void shouldNotReturnNull_WhenJsonNodeIsValid() {
        JsonReader reader = JsonReader.getInstance();
        List<Joke> jokes = reader.getJokes();

        assertNotNull(jokes);
    }
}