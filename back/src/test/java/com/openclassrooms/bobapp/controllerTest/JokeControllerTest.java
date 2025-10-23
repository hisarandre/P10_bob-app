package com.openclassrooms.bobapp.controllerTest;

import com.openclassrooms.bobapp.controller.JokeController;
import com.openclassrooms.bobapp.model.Joke;
import com.openclassrooms.bobapp.service.JokeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JokeController.class)
class JokeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokeService jokeService;

    @Test
    void shouldReturnRandomJoke_WhenGetRandomJokesIsCalled() throws Exception {
        Joke mockJoke = new Joke("Why did the developer go broke?", "Because he used up all his cache!");
        when(jokeService.getRandomJoke()).thenReturn(mockJoke);

        mockMvc.perform(get("/api/joke"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question", is("Why did the developer go broke?")))
                .andExpect(jsonPath("$.response", is("Because he used up all his cache!")));

        verify(jokeService, times(1)).getRandomJoke();
    }

    @Test
    void shouldReturnOkStatus_WhenJokeIsReturned() throws Exception {
        when(jokeService.getRandomJoke()).thenReturn(new Joke("Question", "Answer"));

        mockMvc.perform(get("/api/joke"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCallJokeServiceOnce_WhenEndpointIsCalled() throws Exception {
        when(jokeService.getRandomJoke()).thenReturn(new Joke("Test question", "Test answer"));

        mockMvc.perform(get("/api/joke"));

        verify(jokeService, times(1)).getRandomJoke();
    }

    @Test
    void shouldReturnJsonContent_WhenJokeIsReturned() throws Exception {
        when(jokeService.getRandomJoke()).thenReturn(new Joke("What do you call a programmer from Finland?", "Nerdic!"));

        mockMvc.perform(get("/api/joke"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question").exists())
                .andExpect(jsonPath("$.response").exists());
    }

    @Test
    void shouldReturnDifferentJokes_WhenCalledMultipleTimes() throws Exception {
        Joke firstJoke = new Joke("First question?", "First answer");
        Joke secondJoke = new Joke("Second question?", "Second answer");

        when(jokeService.getRandomJoke())
                .thenReturn(firstJoke)
                .thenReturn(secondJoke);

        mockMvc.perform(get("/api/joke"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is("First question?")))
                .andExpect(jsonPath("$.response", is("First answer")));

        mockMvc.perform(get("/api/joke"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question", is("Second question?")))
                .andExpect(jsonPath("$.response", is("Second answer")));

        verify(jokeService, times(2)).getRandomJoke();
    }
}
