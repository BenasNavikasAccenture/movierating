package com.example.movierating.controller;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.service.RatingService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public RatingResponseDTO rateMovie(@Valid @RequestBody RatingRequestDTO dto) {
        return ratingService.rateMovie(dto);
    }

    @GetMapping("/average/{id}")
    public double getAverageRating(@PathVariable("id") Long movieId) {
        return ratingService.getAverageRatingByMovieId(movieId);
    }
}
