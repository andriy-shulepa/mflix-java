import React from "react";
import '../bootstrap.min.css'
import {Link} from "react-router-dom";

class MovieCard extends React.Component {
    render() {
        let imgUrl;
        if (this.props.movie.poster) {
            imgUrl = this.props.movie.poster
        } else {
            imgUrl = "https://media.comicbook.com/files/img/default-movie.png";
        }
        return (
            <Link to={"/movie/" + this.props.movie.id}>
                <div className="card text-white h-100">
                    <img src={imgUrl} className="card-img h-100"/>
                    <div className="card-img-overlay card-info">
                        <h5 className="card-title">{this.props.movie.title}</h5>
                        {this.props.movie.imdbRating ?
                            <p className="card-text">IMDb: {this.props.movie.imdbRating}</p> : null}
                        {this.props.movie.genres ?
                            <p className="card-text text-sm-left">Genres: {this.props.movie.genres.join(", ")}</p> : null}
                    </div>
                </div>
            </Link>
        )
    }
}

export default MovieCard;