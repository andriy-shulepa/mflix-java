package com.andriy96s.mflixjava.backend.service;

import com.andriy96s.mflixjava.backend.dto.BasicMovieDto;
import com.andriy96s.mflixjava.backend.dto.DetailMovieDto;
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

    public List<BasicMovieDto> getAllMoviesPaginated(int moviesPerPage, int moviesToSkp) {
        return MovieMapper.toBasicMovieDtos(
                moviesRepository.getMovies(moviesPerPage, moviesToSkp, Sorts.descending("imdb.rating")));
    }

    public DetailMovieDto getMovieDetails(String movieId) {
        return MovieMapper.toDetailMovieDto(
                moviesRepository.getMovie(movieId)
        );
    }

    public List<BasicMovieDto> searchForMovie(String searchText, int moviesPerPage, int moviesToSkp) {
        return MovieMapper.toBasicMovieDtos(
                moviesRepository.searchForMovie(searchText, moviesPerPage, moviesToSkp, Sorts.descending("imdb.rating")));
    }
}
