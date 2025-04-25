package com.example.movierating.service;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMovie() {
        Movie movie = new Movie(1L, "Interstellar", "Sci-Fi", 2014, "Epic space movie");
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieDTO dto = new MovieDTO();
        dto.setTitle("Interstellar");
        dto.setGenre("Sci-Fi");
        dto.setReleaseYear(2014);
        dto.setDescription("Epic space movie");

        MovieDTO result = movieService.createMovie(dto);

        assertEquals("Interstellar", result.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = List.of(
                new Movie(1L, "Movie1", "Genre1", 2000, "Desc1"),
                new Movie(2L, "Movie2", "Genre2", 2010, "Desc2")
        );

        when(movieRepository.findAll()).thenReturn(movies);

        List<MovieDTO> result = movieService.getAllMovies();
        assertEquals(2, result.size());
        assertEquals("Movie1", result.get(0).getTitle());
        assertEquals("Movie2", result.get(1).getTitle());
    }

    @Test
    void testGetMovieById() {
        Movie movie = new Movie(1L, "Movie1", "Genre1", 2000, "Desc1");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        MovieDTO result = movieService.getMovieById(1L);
        assertEquals("Movie1", result.getTitle());
    }

    @Test
    void testDeleteMovie() {
        movieService.deleteMovie(1L);
        verify(movieRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateMovie() {
        Movie existingMovie = new Movie(1L, "Old Title", "Old Genre", 1990, "Old Description");
        when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(existingMovie); // saving the modified object

        MovieDTO dto = new MovieDTO();
        dto.setTitle("New Title");
        dto.setGenre("New Genre");
        dto.setReleaseYear(2020);
        dto.setDescription("New Description");

        MovieDTO result = movieService.updateMovie(1L, dto);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Genre", result.getGenre());
        assertEquals(2020, result.getReleaseYear());
        assertEquals("New Description", result.getDescription());

        verify(movieRepository).findById(1L);
        verify(movieRepository).save(existingMovie);
    }
}
