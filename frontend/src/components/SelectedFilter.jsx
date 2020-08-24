import * as React from "react";

class SelectedFilter extends React.Component {
    render() {
        return (


            <span className="badge badge-primary m-1">
            {this.props.filterName}
                <button type="button" className="close" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </span>
        )
    }
}

export default SelectedFilter;