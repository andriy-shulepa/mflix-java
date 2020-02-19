package com.andriy96s.mflixjava.backend;

import com.andriy96s.mflixjava.backend.repository.MoviesRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.beans.beancontext.BeanContext;

@SpringBootApplication
public class MflixApplication {

    public static void main(String[] args) {
        SpringApplication.run(MflixApplication.class, args);
    }

}
