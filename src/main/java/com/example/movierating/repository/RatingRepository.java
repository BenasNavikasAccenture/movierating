package com.example.movierating.repository;

import com.example.movierating.model.Movie;
import com.example.movierating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovie(Movie movie);
}