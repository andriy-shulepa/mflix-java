package com.andriy96s.mflixjava.backend.controller;

import com.andriy96s.mflixjava.backend.dto.BasicMovieDto;
import com.andriy96s.mflixjava.backend.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping("/api/movies")
public class MoviesController {

    final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
        moviesService.getAllMovies();
    }

    @GetMapping
    public ResponseEntity<List<BasicMovieDto>> getAllMovies() {
        return new ResponseEntity<>(moviesService.getAllMovies(), HttpStatus.OK);
    }
}
