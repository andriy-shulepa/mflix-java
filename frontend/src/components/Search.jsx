import React from "react";

class Search extends React.Component{

    constructor(props) {
        super(props);
        this.state = {text: ''}
    }

    myChangeHandler = (event) => {
        this.setState({text: event.target.value});
    };

    render() {
        return (

            <div className="" id="navbarColor02">
                <form className="justify-content-center form-inline my-2 my-lg-0 " onSubmit={(event) => {event.preventDefault(); this.props.onClick(this.state.text)}}>
                    <input className="form-control mr-sm-2" type="text" placeholder="Search" onChange={this.myChangeHandler}/>
                    <button className="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>

                </form>
            </div>
        );

    }


}

export default Search;