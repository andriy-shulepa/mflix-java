package com.andriy96s.mflixjava.backend.config;


import com.andriy96s.mflixjava.backend.repository.MoviesRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfig {
    @Bean
    public MongoClient getMongoClient() {
        return new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
    }
}
