import React from "react";

class Search extends React.Component {

    render() {
        return (
            <div className="" id="navbarColor02">
                <form className="justify-content-center form-inline my-2 my-lg-0 " onSubmit={(event) => {
                    event.preventDefault();
                    this.props.onClick()
                }}>
                    <input className="form-control mr-sm-2" type="text" placeholder="Search"
                           onChange={this.props.onTextChange}/>
                    <button className="btn btn-secondary my-2 my-sm-0" type="submit">Search</button>
                </form>
            </div>
        );

    }


}

export default Search;