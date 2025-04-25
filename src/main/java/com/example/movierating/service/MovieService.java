package com.example.movierating.service;

import com.example.movierating.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    List<MovieDTO> getAllMovies();
    MovieDTO getMovieById(Long id);
    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO updateMovie(Long id, MovieDTO movieDTO);
    void deleteMovie(Long id);
}
