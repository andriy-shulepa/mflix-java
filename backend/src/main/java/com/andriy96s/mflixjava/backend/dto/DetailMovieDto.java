package com.andriy96s.mflixjava.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetailMovieDto extends BasicMovieDto {
    private String fullDescription;
    private List<String> cast;
    private List<String> languages;
    private List<String> directors;
    private List<String> writers;
    private List<String> countries;
}