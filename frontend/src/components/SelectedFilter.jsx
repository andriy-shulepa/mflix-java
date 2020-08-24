import * as React from "react";

class SelectedFilter extends React.Component {

    constructor(props) {
        super(props);
        this.handleCloseClick = this.handleCloseClick.bind(this);
    }

    render() {
        return (
            <span className="badge badge-primary m-1">
            {this.props.filterName}
                <button type="button"
                        className="close"
                        aria-label="Close"
                        onClick={this.handleCloseClick}>
                    <span aria-hidden="true">&times;</span>
                </button>
            </span>
        )
    }

    handleCloseClick() {
        let filterCategory = this.props.filterCategory;
        let filterName = this.props.filterName;
        this.props.onClick(filterCategory, filterName);
    }
}

export default SelectedFilter;