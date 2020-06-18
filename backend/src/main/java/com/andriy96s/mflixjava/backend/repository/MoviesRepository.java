package com.andriy96s.mflixjava.backend.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Facet;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;

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

    public List<Document> searchForMovie(String searchText, Map<String, List<String>> facets, int limit, int skip, Bson sort) {
        Bson query = getBsonQuery(searchText, facets);

        FindIterable<Document> movieDocs = moviesCollection
                .find(query)
                .sort(sort)
                .skip(skip)
                .limit(limit);

        return toDocumentsList(movieDocs.iterator());
    }

    private Bson getBsonQuery(String searchText, @NotNull Map<String, List<String>> facets) {
        Bson query = new BsonDocument();
        if (!StringUtils.isEmpty(searchText)) {
            query = Filters.text(searchText);
        }

        for (String facet : facets.keySet()) {
            switch (facet) {
                case "genres":
                    query = Filters.and(query, in("genres", facets.get("genres")));
                    break;
                case "rated":
                    query = Filters.and(query, in("rated", facets.get("rated")));
                    break;
                case "countries":
                    query = Filters.and(query, in("countries", facets.get("countries")));
                    break;
                case "languages":
                    query = Filters.and(query, in("languages", facets.get("languages")));
                    break;
            }
        }
        return query;
    }


    public long getMoviesCount(String searchText, Map<String, List<String>> facets) {
        return moviesCollection.countDocuments(getBsonQuery(searchText, facets));
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
