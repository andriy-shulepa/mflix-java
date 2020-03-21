package com.andriy96s.mflixjava.backend.dto;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MovieMapper {

    public static List<BasicMovieDto> toBasicMovieDtos(List<Document> moviesDocs) {
        List<BasicMovieDto> movieDtos = new ArrayList<>();
        moviesDocs.forEach(document -> {
            BasicMovieDto movieDto = toBasicMovieDto(document, new BasicMovieDto());

            movieDtos.add(movieDto);
        });

        return movieDtos;
    }

    public static BasicMovieDto toBasicMovieDto(Document fromMovieDoc, BasicMovieDto toMovieDto) {

        toMovieDto.setId(fromMovieDoc.getObjectId("_id").toString());
        toMovieDto.setTitle(fromMovieDoc.getString("title"));
        Object year = fromMovieDoc.get("year");
        if (year != null) {
            if (year instanceof String && !"".equals(year)) {
                try {
                    toMovieDto.setYear(Integer.parseInt((String) year));
                } catch (NumberFormatException e) {
                    System.err.println(e.getMessage());
                }
            } else if (year instanceof Integer) {
                toMovieDto.setYear((Integer) year);
            }
        }
        Integer runtime = fromMovieDoc.getInteger("runtime");
        if (runtime != null) {
            toMovieDto.setRuntime(runtime);
        }
        toMovieDto.setPoster(fromMovieDoc.getString("poster"));
        toMovieDto.setRated(fromMovieDoc.getString("rated"));
        toMovieDto.setShortDescription(fromMovieDoc.getString("plot"));
        Document imdb = (Document) fromMovieDoc.get("imdb");
        if (imdb != null) {
            Object rating = (imdb).get("rating");
            if (rating != null) {
                if (rating instanceof Double) {
                    toMovieDto.setImdbRating((Double) rating);
                } else if (rating instanceof Integer) {
                    toMovieDto.setImdbRating(Double.valueOf((Integer) rating));
                } else if (rating instanceof String && !"".equals(rating)) {
                    toMovieDto.setImdbRating(Double.valueOf((String) rating));
                }
            }
        }

        toMovieDto.setGenres(fromMovieDoc.getList("genres", String.class));

        return toMovieDto;
    }



    public static DetailMovieDto toDetailMovieDto(Document movieDoc) {
        DetailMovieDto movieDto = (DetailMovieDto) toBasicMovieDto(movieDoc, new DetailMovieDto());

        movieDto.setFullDescription(movieDoc.getString("fullPlot"));
        movieDto.setCast( movieDoc.getList("cast", String.class));
        movieDto.setLanguages(movieDoc.getList("languages", String.class));
        movieDto.setDirectors( movieDoc.getList("directors", String.class));
        movieDto.setWriters( movieDoc.getList("writers", String.class));
        movieDto.setCountries( movieDoc.getList("countries", String.class));

        return movieDto;
    }
}
