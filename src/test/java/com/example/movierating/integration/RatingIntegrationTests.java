package com.example.movierating.integration;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.model.User;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.repository.RatingRepository;
import com.example.movierating.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Movie testMovie;

    @BeforeEach
    void setUp() {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User(null, "testuser");
        testUser = userRepository.save(testUser);

        testMovie = new Movie(null, "Test Movie", "Action", 2022, "Test Description");
        testMovie = movieRepository.save(testMovie);
    }

    @Test
    void testCreateRating() throws Exception {
        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setUserId(testUser.getId());
        dto.setMovieId(testMovie.getId());
        dto.setScore(4);
        dto.setComment("Great movie!");

        mockMvc.perform(post("/api/ratings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.movieTitle").value("Test Movie"))
                .andExpect(jsonPath("$.score").value(4))
                .andExpect(jsonPath("$.comment").value("Great movie!"));
    }

    @Test
    void testGetAverageRating_withRatings() throws Exception {
        ratingRepository.save(new com.example.movierating.model.Rating(testUser, testMovie, 4, "Great"));
        ratingRepository.save(new com.example.movierating.model.Rating(testUser, testMovie, 2, "Okay"));

        mockMvc.perform(get("/api/ratings/average/" + testMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("3.0"));
    }

    @Test
    void testGetAverageRating_noRatings() throws Exception {
        mockMvc.perform(get("/api/ratings/average/" + testMovie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}
