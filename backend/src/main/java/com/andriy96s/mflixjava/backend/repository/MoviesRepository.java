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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Component
public class MoviesRepository {
    private final MongoClient mongoClient;

    public MoviesRepository(@Qualifier("getMongoClient") MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        getTopMovies();
    }

    public List<DBObject> getTopMovies() {
        MongoDatabase db = mongoClient.getDatabase("mflix");
        MongoCollection<Document> movies = db.getCollection("movies");

        Bson query = Filters.and(
                nin("imdb.rating", null, ""),
                gte("year", 2000));

        FindIterable<Document> movieDocs = movies.find(query)
                .sort(Sorts.descending("imdb.rating"))
                .limit(10)
                .projection(fields(include("title", "year", "imdb.rating")));

        var dboObjects = new ArrayList<DBObject>();
        for (Document document : movieDocs) {
            System.out.println(document.toJson());
        }
//        limit.forEach(dboObjects::add);

        return null;
    }

}
