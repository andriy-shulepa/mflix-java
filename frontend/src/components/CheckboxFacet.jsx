import * as React from "react";
import {capitalizeFirstLetter} from "../utils/CommonUtils";

const INITIAL_ELEMENTS_COUNT = 5;

class CheckboxFacet extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            expanded: false,
            itemsToShow: INITIAL_ELEMENTS_COUNT,
        };

        this.showMore = this.showMore.bind(this);
        this.handleCheckboxClick = this.handleCheckboxClick.bind(this);
    }

    render() {
        return (
            <div>
                <fieldset>
                    <legend>{capitalizeFirstLetter(this.props.facetName)}</legend>

                    {this.props.facetItems.slice(0, this.state.itemsToShow).map(item => this.buildCheckboxItem(item))}
                </fieldset>
                <p>
                    <button className="btn btn-outline-secondary" onClick={this.showMore}>
                        {this.state.expanded ? (
                            <span>Show less</span>
                        ) : (
                            <span>Show {this.calculateRemainingItems()} more</span>
                        )
                        }
                    </button>
                </p>
            </div>
        )

    }

    buildCheckboxItem(item) {
        return (
            <div className="form-check" key={item._id}>
                <label className="form-check-label">
                    <input className="form-check-input" type="checkbox" value={item._id}
                           onChange={this.handleCheckboxClick}/>
                    {item._id}
                </label>
            </div>
        )
    }

    handleCheckboxClick(event) {
        this.props.handleCheckboxClick(event, this.props.facetName);
    }

    showMore(event) {
        event.preventDefault();
        this.state.itemsToShow === INITIAL_ELEMENTS_COUNT ? (this.setState({
            itemsToShow: this.props.facetItems.length / 2,
            expanded: false
        })) : this.state.itemsToShow === this.props.facetItems.length ? (
            this.setState({itemsToShow: 5, expanded: false})
        ) : (this.setState({itemsToShow: this.props.facetItems.length, expanded: true}));
    }

    calculateRemainingItems() {
        return this.state.itemsToShow === INITIAL_ELEMENTS_COUNT ?
            this.props.facetItems.length / 2 - INITIAL_ELEMENTS_COUNT
            : this.props.facetItems.length - this.state.itemsToShow;
    }
}

export default CheckboxFacet;