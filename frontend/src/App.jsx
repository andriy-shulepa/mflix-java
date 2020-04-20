import React from 'react';
import './bootstrap.min.css'
import "./index.css"
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import Home from "./components/Home";
import MovieDetails from "./components/MovieDetails";
import Navbar from "./components/Navbar";

export default function App() {
    return (
        <div>
        <Navbar/>
        <Router>
            <Switch>
                <Route path="/movie">
                    <MovieDetails/>
                </Route>
                <Route path="/">
                    <Home/>
                </Route>
            </Switch>
        </Router>
        </div>
    );
}
