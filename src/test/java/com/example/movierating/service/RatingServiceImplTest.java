package com.example.movierating.service;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.model.Rating;
import com.example.movierating.model.User;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.repository.RatingRepository;
import com.example.movierating.repository.UserRepository;
import com.example.movierating.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRateMovie() {
        // Given
        RatingRequestDTO requestDTO = new RatingRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setMovieId(10L);
        requestDTO.setScore(5);
        requestDTO.setComment("Amazing!");

        User user = new User(1L, "john_doe");
        Movie movie = new Movie(10L, "Inception", "Sci-Fi", 2010, "Dreams and stuff");

        Rating savedRating = new Rating(1L, user, movie, 5, "Amazing!");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));
        when(ratingRepository.save(any(Rating.class))).thenReturn(savedRating);

        // When
        RatingResponseDTO responseDTO = ratingService.rateMovie(requestDTO);

        // Then
        assertEquals("john_doe", responseDTO.getUsername());
        assertEquals("Inception", responseDTO.getMovieTitle());
        assertEquals(5, responseDTO.getScore());
        assertEquals("Amazing!", responseDTO.getComment());

        verify(userRepository).findById(1L);
        verify(movieRepository).findById(10L);
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void testGetAverageRatingByMovieId() {
        Movie movie = new Movie(10L, "Interstellar", "Sci-Fi", 2014, "Space adventure");
        when(movieRepository.findById(10L)).thenReturn(Optional.of(movie));

        List<Rating> ratings = List.of(
                new Rating(1L, new User(1L, "u1"), movie, 4, "Nice"),
                new Rating(2L, new User(2L, "u2"), movie, 5, "Awesome")
        );

        when(ratingRepository.findByMovie(movie)).thenReturn(ratings);

        Double average = ratingService.getAverageRatingByMovieId(10L);

        assertEquals(4.5, average);
        verify(movieRepository).findById(10L);
        verify(ratingRepository).findByMovie(movie);
    }

    @Test
    void testGetAverageRatingByMovieId_NoRatings() {
        Movie movie = new Movie(20L, "Unknown", "Drama", 2000, "Nothing much");
        when(movieRepository.findById(20L)).thenReturn(Optional.of(movie));
        when(ratingRepository.findByMovie(movie)).thenReturn(List.of());

        Double average = ratingService.getAverageRatingByMovieId(20L);

        assertEquals(0.0, average);
    }

    @Test
    void testGetAverageRatingByMovieId_MovieNotFound() {
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> ratingService.getAverageRatingByMovieId(999L));

        assertEquals("Movie with id 999 not found", exception.getMessage());
    }
}
