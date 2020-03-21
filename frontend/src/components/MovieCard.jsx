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
            <Link to={"/movie/"+this.props.movie.id}>
            <div className="card text-white h-100" >
                <img src={imgUrl} className="card-img h-100"/>
                <div className="card-img-overlay">
                    <h5 className="card-title">{this.props.movie.title}</h5>
                    <p className="card-text">{this.props.movie.imdbRating}</p>
                </div>
            </div>
            </Link>
        )
    }
}

export default MovieCard;