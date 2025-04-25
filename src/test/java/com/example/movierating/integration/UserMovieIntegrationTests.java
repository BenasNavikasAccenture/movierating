package com.example.movierating.integration;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.dto.UserDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.model.User;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserMovieIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
        movieRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        UserDTO dto = new UserDTO(null, "john");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    @Order(2)
    void testGetUserById() throws Exception {
        User user = userRepository.save(new User(null, "anna"));

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("anna"));
    }

    @Test
    @Order(3)
    void testCreateMovie() throws Exception {
        MovieDTO movieDTO = new MovieDTO(null, "Matrix", "Action", 1999, "Sci-fi classic");

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Matrix"));
    }

    @Test
    @Order(4)
    void testGetMovieById() throws Exception {
        Movie movie = movieRepository.save(new Movie(null, "Inception", "Sci-Fi", 2010, "Dreams inside dreams"));

        mockMvc.perform(get("/api/movies/" + movie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }
}
