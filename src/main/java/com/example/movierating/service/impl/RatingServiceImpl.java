package com.example.movierating.service.impl;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.model.Rating;
import com.example.movierating.model.User;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.repository.RatingRepository;
import com.example.movierating.repository.UserRepository;
import com.example.movierating.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public RatingResponseDTO rateMovie(RatingRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found"));

        Rating rating = new Rating(user, movie, dto.getScore(), dto.getComment());
        rating = ratingRepository.save(rating);

        return new RatingResponseDTO(
                rating.getId(),
                user.getUsername(),
                movie.getTitle(),
                rating.getScore(),
                rating.getComment()
        );
    }

    @Override
    public Double getAverageRatingByMovieId(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie with id " + movieId + " not found"));

        List<Rating> ratings = ratingRepository.findByMovie(movie);

        if (ratings.isEmpty()) {
            return 0.0;
        }

        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}