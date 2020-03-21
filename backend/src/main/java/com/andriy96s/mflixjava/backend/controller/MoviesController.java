package com.andriy96s.mflixjava.backend.controller;

import com.andriy96s.mflixjava.backend.dto.BasicMovieDto;
import com.andriy96s.mflixjava.backend.dto.DetailMovieDto;
import com.andriy96s.mflixjava.backend.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("/api/movies")
public class MoviesController {

    private static final int MOVIES_PER_PAGE = 24;

    final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ResponseEntity<List<BasicMovieDto>> getAllMovies() {
        return new ResponseEntity<>(moviesService.getAllMoviesPaginated(MOVIES_PER_PAGE, 0), HttpStatus.OK);
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<BasicMovieDto>> getMoviesForPage(@PathVariable String pageNumber) {
        int pageNumerInt;
        try {
            pageNumerInt = Integer.parseInt(pageNumber);
            if(pageNumerInt<0) {
                throw new NumberFormatException("Page number should not be negative");
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(moviesService.getAllMoviesPaginated(MOVIES_PER_PAGE, MOVIES_PER_PAGE*(pageNumerInt-1)), HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<DetailMovieDto> getMovieDetails(@PathVariable String movieId) {
        return new ResponseEntity<>(moviesService.getMovieDetails(movieId), HttpStatus.OK);
    }
}
