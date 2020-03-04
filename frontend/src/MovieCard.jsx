import React from "react";
import './bootstrap.min.css'

class MovieCard extends React.Component {
    render() {
        let imgUrl;
        if (this.props.movie.poster) {
            imgUrl = this.props.movie.poster
        } else {
            imgUrl = "https://media.comicbook.com/files/img/default-movie.png";
        }
        return (
            <div className="card text-white h-100">
                <img src={imgUrl} className="card-img h-100"/>
                <div className="card-img-overlay">
                    <h5 className="card-title">{this.props.movie.title}</h5>
                    <p className="card-text">{this.props.movie.imdbRating}</p>
                </div>
            </div>
        )
    }
}

export default MovieCard;