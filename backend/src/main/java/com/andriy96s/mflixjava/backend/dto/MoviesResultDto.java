package com.andriy96s.mflixjava.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class MoviesResultDto {
    private List<BasicMovieDto> moviesPaginated;
    private long totalMoviesCount;

}
