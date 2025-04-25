package com.example.movierating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingResponseDTO {
    private Long id;
    private String username;
    private String movieTitle;
    private int score;
    private String comment;

    public RatingResponseDTO(Long id, String username, String movieTitle, int score, String comment) {
        this.id = id;
        this.username = username;
        this.movieTitle = movieTitle;
        this.score = score;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getMovieTitle() { return movieTitle; }
    public int getScore() { return score; }
    public String getComment() { return comment; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    public void setScore(int score) { this.score = score; }
    public void setComment(String comment) { this.comment = comment; }
}
