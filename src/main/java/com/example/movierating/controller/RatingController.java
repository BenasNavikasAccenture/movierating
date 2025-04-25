package com.example.movierating.controller;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.model.Movie;
import com.example.movierating.model.Rating;
import com.example.movierating.repository.MovieRepository;
import com.example.movierating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping
    public RatingResponseDTO rateMovie(@Valid @RequestBody RatingRequestDTO dto) {
        return ratingService.rateMovie(dto);
    }

    @GetMapping("/average/{id}")
    public double getAverageRating(@PathVariable("id") Long movieId) {
        return ratingService.getAverageRatingByMovieId(movieId);
    }
}
