package com.andriy96s.mflixjava.backend.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.nin;

@Component
public class MoviesRepository {
    private final MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> moviesCollection;
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
        moviesCollection = db.getCollection(moviesCollectionName);
    }

    public List<Document> getMovies(int limit, int skip, Bson sort) {
        FindIterable<Document> movieDocs = moviesCollection
                .find()
                .sort(sort)
                .skip(skip)
                .limit(limit);

        return toDocumentsList(movieDocs.iterator());
    }

    public Document getMovie(String movieId) {
        Bson query = Filters.eq("_id", new ObjectId(movieId));

        return moviesCollection.find(query).first();
    }

    public List<Document> searchForMovie(String searchText, int limit, int skip, Bson sort) {
        Bson query = Filters.text(searchText);

        FindIterable<Document> movieDocs = moviesCollection
                .find(query)
                .sort(sort)
                .skip(skip)
                .limit(limit);

        return toDocumentsList(movieDocs.iterator());
    }

    public long getMoviesCount(String searchText) {
        return searchText == null ?
                moviesCollection.countDocuments() :
                moviesCollection.countDocuments(Filters.text(searchText));
    }

    public List<Document> getFacetInfo() {
        Bson facets = buildFacets();

        AggregateIterable<Document> aggregateResult = moviesCollection.aggregate(List.of(facets));

        return toDocumentsList(aggregateResult.iterator());
    }

    private List<Document> toDocumentsList(MongoCursor<Document> iterator) {
        List<Document> results = new ArrayList<>();
        iterator.forEachRemaining(results::add);
        return results;
    }

    private Bson buildFacets() {
        return facet(
                new Facet("genres",
                        unwind("$genres"),
                        sortByCount("$genres")),
                new Facet("rated",
                        sortByCount("$rated")),
                new Facet("countries",
                        unwind("$countries"),
                        sortByCount("$countries"),
                        match(gte("count", 3))),

                new Facet("languages",
                        unwind("$languages"),
                        sortByCount("$languages"),
                        match(gte("count", 3))));
    }

}
