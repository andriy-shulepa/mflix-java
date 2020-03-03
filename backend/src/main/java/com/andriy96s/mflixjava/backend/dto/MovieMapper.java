package com.andriy96s.mflixjava.backend.dto;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<BasicMovieDto> toBasicMovieDto(List<Document> moviesDocs) {
        List<BasicMovieDto> movieDtos = new ArrayList<>();
        moviesDocs.forEach(document -> {
            BasicMovieDto movieDto = new BasicMovieDto();
            movieDto.setTitle(document.getString("title"));
            Object year = document.get("year");
            if (year != null) {
                if (year instanceof String && !"".equals(year)) {
                    try {
                        movieDto.setYear(Integer.parseInt((String) year));
                    } catch (NumberFormatException e) {
                        System.err.println(e.getMessage());
                    }
                } else if (year instanceof Integer) {
                    movieDto.setYear((Integer) year);
                }
            }
            Integer runtime = document.getInteger("runtime");
            if (runtime != null) {
                movieDto.setRuntime(runtime);
            }
            movieDto.setPoster(document.getString("poster"));
            movieDto.setRated(document.getString("rated"));
            Document imdb = (Document) document.get("imdb");
            if (imdb != null) {
                Object rating = (imdb).get("rating");
                if (rating != null) {
                    if (rating instanceof Double) {
                        movieDto.setImdbRating((Double) rating);
                    } else if (rating instanceof Integer) {
                        movieDto.setImdbRating(Double.valueOf((Integer) rating));
                    } else if (rating instanceof String && !"".equals(rating)) {
                        movieDto.setImdbRating(Double.valueOf((String) rating));
                    }
                }
            }


            movieDto.setGenres((List<String>) document.get("genres"));

            movieDtos.add(movieDto);
        });

        return movieDtos;
    }
}
