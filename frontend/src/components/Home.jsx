import MovieCard from "./MovieCard";
import Pagination from "./Pagination";
import React from "react";
import Search from "./Search";
import Facets from "./Facets";

class Home extends React.Component {

    MOVIES_PER_PAGE = 24;

    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            movies: [],
            error: null,
            currentPage: 1
        }
    }

    componentDidMount() {
        this.getMovies(null, 1);
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
            return <div className="text-danger">Error: {error}</div>
        }

        if (!isLoaded) {
            return <div className="text-info">Loading...</div>
        } else
            return (
                <div className="p-4">
                    <div className="mb-2">
                        <Search onClick={(text) => this.getMovies(text, 1)}/>
                    </div>
                    <div className="container">
                        <div className="row">
                            <div className="col-lg-3">
                                <Facets/>
                            </div>
                            <div className="col-lg-9">
                                <h4>Total movies found: {this.state.totalCount}</h4>
                                <div className="row">
                                    {movies.map(movie => this.renderMovieCard(movie))}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="container py-3 align-content-center">
                        <Pagination onClick={(i) => this.getMovies(this.state.search, i)}
                                    currentPage={this.state.currentPage}
                                    totalCount={this.state.totalCount}
                                    moviesPerPage={this.MOVIES_PER_PAGE}/>
                    </div>
                </div>
            )
    }

    getMovies(search, page) {
        this.fetchMovies(search, page)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        movies: result.moviesPaginated,
                        totalCount: result.totalMoviesCount,
                        currentPage: page,
                        search: search,
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error: error,
                        currentPage: page,
                        search: search,
                    });
                }
            )
    }

    fetchMovies(search, page) {
        if (search) {
            return fetch("http://localhost:8080/api/movies/search?text=" + search + "&page=" + page);
        } else {
            return fetch("http://localhost:8080/api/movies/page/" + page);
        }
    }
}

export default Home;