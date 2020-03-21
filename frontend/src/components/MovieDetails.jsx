import * as React from "react";

class MovieDetails extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isLoaded: false,
            movie: {},
            error: null,
        }
    }

    componentDidMount() {
        let path = window.location.pathname.split("/");
        console.log("Path: "+ path);
        let movieId = path[2];
        fetch("http://localhost:8080/api/movies/movie/"+movieId)
            .then(res => res.json())
            .then(
                (result) => {
                    console.log("Result: " + JSON.stringify(result));
                    this.setState({
                        isLoaded: true,
                        movie: result
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


    render() {
        const movie = this.state.movie;
        let imgUrl;
        if (movie.poster) {
            imgUrl = movie.poster
        } else {
            imgUrl = "https://media.comicbook.com/files/img/default-movie.png";
        }
        if(!this.state.isLoaded) {
            return <div className="text-info">Loading...</div>
        }
        return <div className="container">
            <div className="row">
                <div className="col-4">
                    <img src={imgUrl} className="card-img"/>
                </div>
                <div className="col-8 jumbotron">
                    <h1 className="display-3">{movie.title}</h1>
                    {movie.fullDescription ? <p className="lead">{movie.fullDescription}</p> : <p className="lead">{movie.shortDescription}</p>}
                    <hr className="my-4"/>

                    <p>Year: {movie.year}</p>
                    <p>Runtime: {movie.runtime} min</p>
                    {movie.imdbRating ?
                        <p>IMDB rating: {movie.imdbRating}</p>
                        : null
                    }
                    {movie.rated ?
                        <p>Rated: {movie.rated}</p>
                        : null
                    }

                    <hr className="my-4"/>

                    {movie.genres ?
                        <p>Genres: {movie.genres.join(", ")}</p>
                        : null
                    }
                    {movie.languages ?
                        <p>Languages: {movie.languages.join(", ")}</p>
                        : null
                    }
                    {movie.countries ?
                        <p>Countries: {movie.countries.join(", ")}</p>
                        : null
                    }
                    {movie.cast ?
                        <p>Cast: {movie.cast.join(", ")}</p>
                        : null
                    }
                    {movie.directors ?
                        <p>Directors: {movie.directors.join(", ")}</p>
                        : null
                    }
                    {movie.writers ?
                        <p>Writers: {movie.writers.join(", ")}</p>
                        : null
                    }
                </div>
            </div>
        </div>
    }
}

export default MovieDetails;