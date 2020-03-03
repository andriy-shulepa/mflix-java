package com.andriy96s.mflixjava.backend.repository;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Component
public class MoviesRepository {
    private final MongoClient mongoClient;
    MongoDatabase db;
    @Value("${spring.data.mongodb.database}")
    private String dbName;
    @Value("${mongodb.collection.movies}")
    private String moviesCollectionName;

    public MoviesRepository(@Qualifier("getMongoClient") MongoClient mongoClient) {
        this.mongoClient = mongoClient;

    }
    @PostConstruct
    public void init() {
        db = mongoClient.getDatabase(dbName);
    }

    public List<DBObject> getTopMovies() {

        MongoCollection<Document> movies = db.getCollection(moviesCollectionName);

        Bson query = Filters.and(
                nin("imdb.rating", null, ""),
                gte("year", 2000));

        FindIterable<Document> movieDocs = movies.find(query)
                .sort(Sorts.descending("imdb.rating"))
                .limit(10);
//                .projection(fields(include("title", "year", "imdb.rating")));

        var dboObjects = new ArrayList<DBObject>();
        for (Document document : movieDocs) {
            System.out.println(document.toJson());
        }
//        limit.forEach(dboObjects::add);

        return null;
    }


    public List<Document> getMovies(int limit, int skip, Bson sort) {
        MongoCollection<Document> moviesCollection = db.getCollection(moviesCollectionName);

        FindIterable<Document> movieDocs = moviesCollection
                .find()
                .sort(sort)
                .skip(skip)
                .limit(limit);

        List<Document> movies = new ArrayList<>();
        movieDocs.iterator().forEachRemaining(movies::add);
        return movies;
    }

}
