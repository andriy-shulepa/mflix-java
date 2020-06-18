package com.andriy96s.mflixjava.backend.controller;

import com.andriy96s.mflixjava.backend.dto.DetailMovieDto;
import com.andriy96s.mflixjava.backend.dto.MoviesResultDto;
import com.andriy96s.mflixjava.backend.service.MoviesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RequestMapping("/api/movies")
public class MoviesController {

    private static final int MOVIES_PER_PAGE = 24;

    final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ResponseEntity<MoviesResultDto> getAllMovies() {
        return new ResponseEntity<>(moviesService.getAllMoviesPaginated(MOVIES_PER_PAGE, 0), HttpStatus.OK);
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<MoviesResultDto> getMoviesForPage(@PathVariable @Min(0) Integer pageNumber) {

        return new ResponseEntity<>(moviesService.getAllMoviesPaginated(MOVIES_PER_PAGE, MOVIES_PER_PAGE * (pageNumber - 1)), HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<DetailMovieDto> getMovieDetails(@PathVariable String movieId) {
        return new ResponseEntity<>(moviesService.getMovieDetails(movieId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<MoviesResultDto> searchMovie(@RequestParam(required = false) String text,
                                                       @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                                       @RequestParam(required = false) List<String> countries,
                                                       @RequestParam(required = false) List<String> genres,
                                                       @RequestParam(required = false) List<String> languages,
                                                       @RequestParam(required = false) List<String> rated) {
        return new ResponseEntity<>(moviesService.searchForMovie(text, getFacetsMap(countries, genres, languages, rated), MOVIES_PER_PAGE, MOVIES_PER_PAGE * (page - 1)), HttpStatus.OK);
    }

    private Map<String, List<String>> getFacetsMap(@RequestParam(required = false) List<String> countries, @RequestParam(required = false) List<String> genres, @RequestParam(required = false) List<String> languages, @RequestParam(required = false) List<String> rated) {
        Map<String, List<String>> facets = new HashMap<>();
        if (countries != null) {
            facets.put("countries", countries);
        }
        if (genres != null) {
            facets.put("genres", genres);
        }
        if (languages != null) {
            facets.put("languages", languages);
        }
        if (rated != null) {
            facets.put("rated", rated);
        }
        return facets;
    }

    @GetMapping("/facet-info")
    public ResponseEntity<Map<String, ?>> getFacetInfo() {
        return ResponseEntity.ok(moviesService.getFacetInfo());
    }
}
