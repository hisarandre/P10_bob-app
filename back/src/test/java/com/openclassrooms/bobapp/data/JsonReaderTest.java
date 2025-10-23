package com.openclassrooms.bobapp.data;
import com.openclassrooms.bobapp.model.Joke;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void shouldReturnNonEmptyList_WhenGetJokesIsCalled() {
        // Arrange
        JsonReader reader = new JsonReader();

        // Act
        List<Joke> jokes = reader.getJokes();

        // Assert
        assertNotNull(jokes);
        assertFalse(jokes.isEmpty());

        Joke firstJoke = jokes.get(0);
        assertNotNull(firstJoke.getQuestion());
        assertNotNull(firstJoke.getResponse());
    }

    @Test
    void shouldThrowIllegalStateException_WhenJsonFileIsNotFound() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new JsonReader("nonexistent/file.json")
        );

        assertEquals("file not found!", exception.getMessage());
    }

    @Test
    void shouldThrowIllegalStateException_WhenJsonFileIsCorrupted(){
        // Act & Assert
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new JsonReader("json/invalid.json")
        );

        assertTrue(exception.getMessage().contains("Unable to initialize JsonReader"));
    }
}