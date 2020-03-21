import MovieCard from "./MovieCard";
import Pagination from "./Pagination";
import React from "react";

class Home extends React.Component {
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
        fetch("http://localhost:8080/api/movies")
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        isLoaded: true,
                        movies: result
                    });
                },
                (error) => {
                    console.log("Error: " + error);
                    this.setState({
                        isLoaded: true,
                        error: error,
                    });
                }
            )
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
                <div className="py-5">
                    <div className="container">
                        <div className="row">
                            {movies.map(movie => this.renderMovieCard(movie))}
                        </div>
                    </div>
                    <div className="container py-3 align-content-center">
                        <Pagination onClick={(i) => this.handleClick(i)}
                                    currentPage={this.state.currentPage}/>
                    </div>
                </div>
            )
    }

    handleClick(i) {
        fetch("http://localhost:8080/api/movies/page/"+i)
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + result);
                    this.setState({
                        isLoaded: true,
                        movies: result,
                        currentPage: i,
                    });
                },
                // Note: it's important to handle errors here
                // instead of a catch() block so that we don't swallow
                // exceptions from actual bugs in components.
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error: error,
                        currentPage: i,
                    });
                }
            )
    }
}

export default Home;