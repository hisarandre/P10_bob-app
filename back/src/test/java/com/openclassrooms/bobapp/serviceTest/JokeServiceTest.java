package com.openclassrooms.bobapp.serviceTest;

import com.openclassrooms.bobapp.data.JsonReader;
import com.openclassrooms.bobapp.model.Joke;
import com.openclassrooms.bobapp.service.JokeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JokeServiceTest {

    private JsonReader jsonReaderMock;
    private JokeService jokeService;

    @BeforeEach
    void setup() {
        jsonReaderMock = mock(JsonReader.class);
        jokeService = new JokeService(jsonReaderMock);
    }

    @Test
    void getRandomJoke_ShouldReturnJokeFromJsonReader() {
        // Given
        Joke joke1 = new Joke("Question 1", "Answer 1");
        Joke joke2 = new Joke("Question 2", "Answer 2");
        List<Joke> jokes = Arrays.asList(joke1, joke2);
        when(jsonReaderMock.getJokes()).thenReturn(jokes);

        // When
        Joke randomJoke = jokeService.getRandomJoke();

        // Then
        assertNotNull(randomJoke, "Random joke should not be null");
        assertTrue(jokes.contains(randomJoke), "Random joke should be from the list");
        verify(jsonReaderMock, times(1)).getJokes();
    }

    @Test
    void getRandomJoke_ShouldReturnDifferentJokesOverMultipleCalls() {
        // Given
        Joke joke1 = new Joke("Question 1", "Answer 1");
        Joke joke2 = new Joke("Question 2", "Answer 2");
        Joke joke3 = new Joke("Question 3", "Answer 3");
        List<Joke> jokes = Arrays.asList(joke1, joke2, joke3);
        when(jsonReaderMock.getJokes()).thenReturn(jokes);

        // When
        Joke first = jokeService.getRandomJoke();
        Joke second = jokeService.getRandomJoke();
        Joke third = jokeService.getRandomJoke();

        // Then
        assertNotNull(first);
        assertNotNull(second);
        assertNotNull(third);
        assertTrue(jokes.contains(first));
        assertTrue(jokes.contains(second));
        assertTrue(jokes.contains(third));
        verify(jsonReaderMock, times(3)).getJokes();
    }
}
