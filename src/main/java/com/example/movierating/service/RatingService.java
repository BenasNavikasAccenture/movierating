package com.example.movierating.service;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    RatingResponseDTO rateMovie(RatingRequestDTO dto);
    Double getAverageRatingByMovieId(Long movieId);
}