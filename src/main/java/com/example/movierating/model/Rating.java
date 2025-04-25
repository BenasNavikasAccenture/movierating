package com.example.movierating.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Movie movie;

    private int score;
    private String comment;

    public Rating() {}

    public Rating(User user, Movie movie, int score, String comment) {
        this.user = user;
        this.movie = movie;
        this.score = score;
        this.comment = comment;
    }
    public Rating(Long id, User user, Movie movie, int score, String comment) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.score = score;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

}

