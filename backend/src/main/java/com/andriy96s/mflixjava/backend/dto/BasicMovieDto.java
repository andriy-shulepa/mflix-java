package com.andriy96s.mflixjava.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasicMovieDto {
    private String id;
    private String title;
    private String shortDescription;
    private int year;
    private int runtime;
    private String poster;
    private String rated;
    private List<String> genres;
    private Double imdbRating;
}
