import MovieCard from "./MovieCard";
import Pagination from "./Pagination";
import React from "react";
import Search from "./Search";
import Facets from "./Facets";
import {isNonEmptyArray} from "../utils/CommonUtils";
import SelectedFilter from "./SelectedFilter";


class Home extends React.Component {

    MOVIES_PER_PAGE = 24;

    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            movies: [],
            error: null,
            currentPage: 1,
            selectedItems: {},
            searchText: ""
        };

        this.updateSelectedItems = this.updateSelectedItems.bind(this);
        this.removeFromCurrentFilters = this.removeFromCurrentFilters.bind(this);
    }

    componentDidMount() {
        this.getMovies(1);
    }

    renderMovieCard(movie) {
        return (
            <div className="col-md-4 pt-3">
                <MovieCard movie={movie}/>
            </div>
        )
    }

    render() {
        const {error, isLoaded, movies} = this.state;
        if (error) {
            return <div className="text-danger">Error: {error.toString()}</div>
        }

        if (!isLoaded) {
            return <div className="text-info">Loading...</div>
        } else
            return (
                <div className="p-4">
                    <div className="mb-2">
                        <Search onClick={() => this.getMovies(1)}
                                onTextChange={(event) => this.setState({searchText: event.target.value})}/>
                    </div>
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-3">
                                <Facets onClick={() => this.getMovies(1)}
                                        selectedItems={this.state.selectedItems}
                                        updateSelectedItems={this.updateSelectedItems}/>
                            </div>
                            <div className="col-lg-9">
                                <div>
                                    {this.renderCurrentFilters()}
                                </div>
                                <h4>Total movies found: {this.state.totalCount}</h4>
                                <div className="row">
                                    {movies.map(movie => this.renderMovieCard(movie))}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="container py-3 align-content-center">
                        <Pagination onClick={(i) => this.getMovies(i)}
                                    currentPage={this.state.currentPage}
                                    totalCount={this.state.totalCount}
                                    moviesPerPage={this.MOVIES_PER_PAGE}/>
                    </div>
                </div>
            )
    }

    renderCurrentFilters() {
        if (!this.state.selectedItems || !this.state.isLoaded) {
            return;
        }

        let selectedFilters = [];

        for (const filterCategory in this.state.selectedItems) {
            this.state.selectedItems[filterCategory].map(
                filter => selectedFilters.push(
                    <SelectedFilter
                        filterName={filter}
                        filterCategory={filterCategory}
                        onClick={this.removeFromCurrentFilters}/>))
        }
        return selectedFilters;
    }

    removeFromCurrentFilters(filterCategory, filterName) {
        let newSelectedItems = this.state.selectedItems;
        let selectedCategory = newSelectedItems[filterCategory];
        let index = selectedCategory.indexOf(filterName);
        selectedCategory.splice(index, 1);

        newSelectedItems[filterCategory] = selectedCategory;

        this.setState({
            selectedItems: newSelectedItems
        });
        this.getMovies(1);
    }

    getMovies(page) {
        this.fetchMovies(this.state.searchText, page, this.state.selectedItems)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        movies: result.moviesPaginated,
                        totalCount: result.totalMoviesCount,
                        currentPage: page,
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error: error,
                        currentPage: page,
                    });
                }
            )
    }

    fetchMovies(search, page, facets) {
        let facetString = this.buildFacetQuery(facets);
        console.log("facetString: " + facetString);
        let searchString = search ? "&text=" + search : "";
        console.log("searchString: " + searchString);
        if (search || facets) {
            return fetch("http://localhost:8080/api/movies/search?page=" + page + searchString + facetString);
        } else {
            return fetch("http://localhost:8080/api/movies/page/" + page);
        }
    }

    updateSelectedItems(newSelectedItems) {
        this.setState({
            selectedItems: newSelectedItems
        });
    }

    buildFacetQuery(facets) {
        if (!facets) return "";
        let facetQuery = "";
        facetQuery += isNonEmptyArray(facets.countries) ? "&countries=" + facets.countries : "";
        facetQuery += isNonEmptyArray(facets.genres) ? "&genres=" + facets.genres : "";
        facetQuery += isNonEmptyArray(facets.languages) ? "&languages=" + facets.languages : "";
        facetQuery += isNonEmptyArray(facets.rated) ? "&rated=" + facets.rated : "";
        return facetQuery;
    }
}

export default Home;