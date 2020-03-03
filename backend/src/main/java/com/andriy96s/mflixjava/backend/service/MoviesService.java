package com.andriy96s.mflixjava.backend.service;

import com.andriy96s.mflixjava.backend.dto.BasicMovieDto;
import com.andriy96s.mflixjava.backend.dto.MovieMapper;
import com.andriy96s.mflixjava.backend.repository.MoviesRepository;
import com.mongodb.client.model.Sorts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesService {
    private final MoviesRepository moviesRepository;

    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public List<BasicMovieDto> getAllMovies() {
        return MovieMapper.toBasicMovieDto(
                moviesRepository.getMovies(100, 0, Sorts.descending("imdb.rating")));
    }
}
