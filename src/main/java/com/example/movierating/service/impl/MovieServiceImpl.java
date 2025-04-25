package com.example.movierating.service.impl;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.mapper.MovieMapper;
import com.example.movierating.model.Movie;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        return MovieMapper.toDTO(movie);
    }

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        movieDTO.setId(null);
        Movie movie = movieRepository.save(MovieMapper.toEntity(movieDTO));
        return MovieMapper.toDTO(movie);
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO movieDTO) {
        Movie movie = movieRepository.findById(id).orElseThrow();
        movie.setTitle(movieDTO.getTitle());
        movie.setGenre(movieDTO.getGenre());
        movie.setReleaseYear(movieDTO.getReleaseYear());
        movie.setDescription(movieDTO.getDescription());
        return MovieMapper.toDTO(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
