package com.example.movierating.mapper;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.model.Movie;

public class MovieMapper {
    public static MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDescription(movie.getDescription());
        return dto;
    }

    public static Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setGenre(dto.getGenre());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDescription(dto.getDescription());
        return movie;
    }

    private MovieMapper() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }
}
